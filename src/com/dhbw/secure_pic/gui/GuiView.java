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
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.WARNING_MESSAGE;


/**
 * Class representing the superclass of all views shown in the {@link Gui}.<br>
 * Inherits from {@link Component} so the forms classes can inherit from {@link GuiView}.<br>
 * <br>
 * Due to the used <i>IntelliJ Designer Forms</i> implementing a clean inheritance seems difficult:<br>
 * <ul>
 *     <li>a xml form can only be bound to exactly one java class</li>
 *     <li>the swing variables, which are automatically initialized, must be in that bound class</li>
 *     <li>trying to pass the variables to a super constructor throws an error: "can only be used after super
 *     constructor was called"</li>
 *     <li>trying to pass the whole view to the super methods fails due to not being able to cast the different views
 *     to one type easily</li>
 * </ul>
 * This causes the centralized methods in the super classes to have lots of parameters, to be able to modify the
 * view without having access to its attributes.
 *
 * @author Frederik Wolter
 */
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
    /** get resource bundle managing strings */
    private static final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));
    /** {@link ContainerImage} worked with in the subclasses. */
    protected transient ContainerImage containerImage;
    // endregion

    /**
     * Helper method for scaling images shown in the gui.
     *
     * @param srcImg    image to be scaled
     * @param maxWidth  image max width
     * @param maxHeight image max height
     *
     * @return scaled image
     *
     * @see <a href="https://stackoverflow.com/a/6714381/13777031">Stackoverflow</a>
     * @see <a href="https://stackoverflow.com/a/10245583/13777031">Stackoverflow</a>
     */
    protected static BufferedImage getScaledImage(BufferedImage srcImg, int maxWidth, int maxHeight) {
        // get original size
        int originalWidth = srcImg.getWidth();
        int originalHeight = srcImg.getHeight();
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        // check if we need to scale width
        if (originalWidth > maxWidth) {
            // scale width to fit
            newWidth = maxWidth;
            // scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        }

        // check if we need to scale even with the new height
        if (newHeight > maxHeight) {
            // scale height to fit instead
            newHeight = maxHeight;
            // scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }

        // resize image
        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, newWidth, newHeight, null);
        g2.dispose();

        return resizedImg;
    }

    /**
     * Centralized method for building a {@link PropertyChangeListener}.
     *
     * @param progressBar {@link JProgressBar} to be updated
     *
     * @return configured {@link PropertyChangeListener}
     */
    protected static PropertyChangeListener getPropertyChangeListener(JProgressBar progressBar) {
        return evt -> {
            if ("progress".equals(evt.getPropertyName())) { // update progress event?
                int progress = (Integer) evt.getNewValue(); // get progress value from event
                progressBar.setValue(progress);             // update progressbar
            }
        };
    }

    /**
     * Centralized method for building a {@link DropTarget}.
     *
     * @param handler     Handler for 'LoadIMageFinished' event
     * @param progressBar {@link JProgressBar} to be updated
     *
     * @return configured {@link DropTarget}
     */
    protected static DropTarget getDropTargetListener(LoadImageFinishedHandler handler, JProgressBar progressBar) {
        return new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    // get dropped files
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    @SuppressWarnings("unchecked")
                    List<File> files = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    // start load task
                    ContainerImageLoadTask task = new ContainerImageLoadTask(files.get(0).getPath(), handler);  // only handle first file
                    task.addPropertyChangeListener(getPropertyChangeListener(progressBar)); // start load tasked
                    task.execute();
                } catch (IOException |
                         UnsupportedFlavorException ex) {    // errors are not critical, user can try again
                    Logger.getLogger("GUI").log(Level.WARNING, String.format(bundle.getString("log.drop_exception"), ex.getMessage()));
                }
            }
        };
    }

    /**
     * Centralized method for building an image upload {@link ActionListener}.
     *
     * @param parent      caller view
     * @param handler     Handler for 'LoadIMageFinished' event
     * @param progressBar {@link JProgressBar} to be updated
     *
     * @return configured {@link ActionListener}
     */
    protected static ActionListener getImageUploadListener(Component parent, LoadImageFinishedHandler handler,
                                                           JProgressBar progressBar) {
        return e -> {
            File file = new FileSelect().select(parent, false, new FileFilter(new FileFilter.Extension[]{
                    FileFilter.Extension.JPEG,  // TODO ?
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

    /**
     * Centralized getter for the selected {@link Coder}.
     *
     * @param codeComboBox Coder selection drop-down
     * @param image        {@link ContainerImage} for coder
     *
     * @return configured {@link Coder}
     */
    protected static Coder getCoder(JComboBox<String> codeComboBox, ContainerImage image) {
        Coder coder;

        if (codeComboBox.getSelectedItem() == "LSB") {
            coder = new LeastSignificantBit(image);
        } else if (codeComboBox.getSelectedItem() == "PM1") {
            coder = new PlusMinusOne(image);
        } else {
            JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("popup.msg.error_invalid_coding_algorithm"), codeComboBox.getSelectedItem()), bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
            coder = null;
        }
        return coder;
    }

    /**
     * Centralized getter for the selected {@link Crypter}.
     *
     * @param encryptComboBox Crypter selection drop-down
     * @param passwordField   {@link JPasswordField} for key
     * @param keyType         {@link RSA.keyType} (für AES not relevant)
     *
     * @return configured {@link Crypter}
     */
    protected static Crypter getCrypter(JComboBox<String> encryptComboBox, JPasswordField passwordField, RSA.keyType keyType) {
        Crypter crypter;

        if (encryptComboBox != null) {
            if (encryptComboBox.getSelectedItem() == "AES") {
                String password = new String(passwordField.getPassword());
                if (password.length() > 0) {
                    crypter = new AES(password);
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("popup.msg.error_no_password"), bundle.getString("popup.title.warning"), WARNING_MESSAGE);
                    crypter = null;
                }
            } else if (encryptComboBox.getSelectedItem() == "RSA") {
                String publicKey = new String(passwordField.getPassword());
                if (publicKey.length() > 0) {
                    try {
                        crypter = new RSA(publicKey, keyType);
                    } catch (CrypterException ex) {
                        JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("popup.msg.error_invalid_ke"), ex.getMessage()), bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
                        crypter = null;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("popup.msg.error_no_valid_public_key"), bundle.getString("popup.title.warning"), JOptionPane.WARNING_MESSAGE);
                    crypter = null;
                }
            } else {
                JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("popup.msg.error_invalid_encrypt_algorithm"), encryptComboBox.getSelectedItem()), bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
                crypter = null;
            }
        } else {    // no encryption
            crypter = new EmptyCrypter();
        }
        return crypter;
    }

}
