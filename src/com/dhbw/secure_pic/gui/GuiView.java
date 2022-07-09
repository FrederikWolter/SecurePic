package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.coder.LeastSignificantBit;
import com.dhbw.secure_pic.coder.PlusMinusOne;
import com.dhbw.secure_pic.crypter.AES;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.crypter.EmptyCrypter;
import com.dhbw.secure_pic.crypter.RSA;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.gui.utility.FileFilter;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.pipelines.ContainerImageLoadTask;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

// TODO comment

// TODO hand attributes over to super in custom constructor?

public class GuiView extends Component {

    // region constants
    public static final int IMAGE_HEIGHT_5 = 550;
    public static final int IMAGE_HEIGHT_4 = 200;
    public static final int IMAGE_HEIGHT_3 = 150;
    public static final int IMAGE_HEIGHT_2 = 130;
    public static final int IMAGE_HEIGHT_1 = 110;

    public static final int IMAGE_WIDTH_5 = 550;
    public static final int IMAGE_WIDTH_4 = 400;
    public static final int IMAGE_WIDTH_3 = 300;
    public static final int IMAGE_WIDTH_2 = 250;
    public static final int IMAGE_WIDTH_1 = 200;
    // endregion

    // region attributes
    protected transient ContainerImage containerImage;
    // endregion

    // see https://stackoverflow.com/a/6714381/13777031, https://stackoverflow.com/a/10245583/13777031
    protected static BufferedImage getScaledImage(BufferedImage srcImg, int maxWidth, int maxHeight) {
        int originalWidth = srcImg.getWidth();
        int originalHeight = srcImg.getHeight();
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        // first check if we need to scale width
        if (originalWidth > maxWidth) {
            //scale width to fit
            newWidth = maxWidth;
            //scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        }

        // then check if we need to scale even with the new height
        if (newHeight > maxHeight) {
            //scale height to fit instead
            newHeight = maxHeight;
            //scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }

        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, newWidth, newHeight, null);
        g2.dispose();

        return resizedImg;
    }

    protected static PropertyChangeListener getPropertyChangeListener(JProgressBar progressBar) {
        return evt -> {
            if ("progress".equals(evt.getPropertyName())) { // update progress event?
                int progress = (Integer) evt.getNewValue(); // get progress value from event
                progressBar.setValue(progress);             // update progressbar
            }
        };
    }

    protected static DropTarget getDropTargetListener(LoadImageFinishedHandler handler, JProgressBar progressBar) {
        return new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
                    List<File> files = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    // start load task
                    ContainerImageLoadTask task = new ContainerImageLoadTask(files.get(0).getPath(), handler);  // only handle first file
                    task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                    task.execute();
                } catch (IOException | UnsupportedFlavorException ex) {    // this errors should be not critical, user can try again
                    Logger.getLogger("GUI").log(Level.WARNING, String.format("DropException: '%s'", ex.getMessage()));
                }
            }
        };
    }

    protected static ActionListener getImageUploadListener(Component parent, LoadImageFinishedHandler handler, JProgressBar progressBar) {
        return e -> {
            File file = new FileSelect().select(parent, false, new FileFilter(new FileFilter.Extension[]{
                    FileFilter.Extension.JPEG,
                    FileFilter.Extension.JPG,
                    FileFilter.Extension.PNG
            }));

            if (file == null) return;    // if no file selected -> simply stop load process

            // start load task
            ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), handler);
            task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
            task.execute();
        };
    }


    protected static Coder getCoder(JComboBox<String> codeComboBox, ContainerImage image) {
        Coder coder;

        if (codeComboBox.getSelectedItem() == "LSB") {
            coder = new LeastSignificantBit(image);
        } else if (codeComboBox.getSelectedItem() == "PM1") {
            coder = new PlusMinusOne(image);
        } else {
            JOptionPane.showMessageDialog(null, "Der ausgewählte Codierung-Algorithmus entspricht keinem gültigen Wert:\n" + codeComboBox.getSelectedItem(), "Fehler", JOptionPane.ERROR_MESSAGE);
            coder = null;
        }
        return coder;
    }

    protected static Crypter getCrypter(JComboBox<String> encryptComboBox, JPasswordField passwordField, RSA.keyType keyType) {
        Crypter crypter;

        if (encryptComboBox != null) {
            if (encryptComboBox.getSelectedItem() == "AES") {
                String password = new String(passwordField.getPassword());
                if (password.length() > 0) {
                    crypter = new AES(password);
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte gebe ein Passwort ein, mit dem die Information verschlüsselt werden soll.", "Warnung", WARNING_MESSAGE);
                    crypter = null;
                }
            } else if (encryptComboBox.getSelectedItem() == "RSA") {
                String publicKey = new String(passwordField.getPassword());
                if (publicKey.length() > 0) {
                    try {
                        crypter = new RSA(publicKey, keyType);
                    } catch (CrypterException ex) {
                        JOptionPane.showMessageDialog(null, "Es ist ein Fehler beim Verarbeiten des Schlüssels aufgetreten:\n" + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                        crypter = null;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte gebe einen öffentlichen Schlüssel ein in Form des erhaltenen Bildes oder als Text, mit dem die Information verschlüsselt werden soll.", "Warnung", JOptionPane.WARNING_MESSAGE);
                    crypter = null;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Der ausgewählte Verschlüsselung-Algorithmus entspricht keinem gültigen Wert:\n" + encryptComboBox.getSelectedItem(), "Fehler", JOptionPane.ERROR_MESSAGE);
                crypter = null;
            }
        } else {    // no encryption
            crypter = new EmptyCrypter();
        }
        return crypter;
    }

}
