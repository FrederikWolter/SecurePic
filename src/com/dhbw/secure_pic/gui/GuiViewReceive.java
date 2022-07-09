package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.crypter.RSA;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.FileFilter;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.handler.DecodeFinishedHandler;
import com.dhbw.secure_pic.pipelines.DecodeTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// TODO comment

public class GuiViewReceive extends GuiView {

    // region attributes
    protected transient Information contentInformation;
    // endregion

    protected ActionListener getExportInformationListener(Component parent) {
        return e -> {
            FileFilter filter;
            if (contentInformation.getType() == Information.Type.TEXT) {
                filter = new FileFilter(new FileFilter.Extension[]{
                        FileFilter.Extension.TXT
                });
            } else {
                filter = new FileFilter(new FileFilter.Extension[]{
                        FileFilter.Extension.PNG
                });
            }

            File file = new FileSelect().select(parent, true, filter);

            if (file == null) return;   // if no destination selected -> simply stop export process

            try {
                if (contentInformation.getType() == Information.Type.TEXT) { // TEXT
                    new BufferedWriter(new FileWriter(file.getPath(), true))   // TODO missing extension autocomplete?
                            .append("\n")
                            .append(contentInformation.toText())
                            .close();
                } else {    // IMAGE
                    ImageIO.write(contentInformation.toImage(), "png", file);   // TODO type?
                    JOptionPane.showMessageDialog(null, "Das Bild wurde erfolgreich exportiert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex); // TODO error handling
            }
        };
    }


    protected ActionListener getDecodeListener(JComboBox<String> codeComboBox, JComboBox<String> encryptComboBox,
                                               JPasswordField passwordField, JLabel messageOutput, int imageWidth,
                                               int imageHeight, JButton exportButton, JButton copyToClipboardButton,
                                               JButton decodeButton, JProgressBar progressBar) {
        return e -> {
            if (containerImage == null) {
                // TODO error handling
                return;
            }

            Coder coder = getCoder(codeComboBox, containerImage);
            Crypter crypter = getCrypter(encryptComboBox, passwordField, RSA.keyType.PRIVATE);

            if (coder == null) return;      // error massage done in getCoder
            if (crypter == null) return;    // error massage done in getCrypter

//                decodeButton.setEnabled(false);

            DecodeTask task = new DecodeTask(coder, crypter, new DecodeFinishedHandler() {
                @Override
                public void finishedDecode(Information info) {
                    contentInformation = info;

                    Information.Type type = info.getType();
                    if (type == Information.Type.TEXT) {
                        messageOutput.setText(info.toText());
                    } else if (type == Information.Type.IMAGE_PNG || type == Information.Type.IMAGE_GIF || type == Information.Type.IMAGE_JPG) {
                        try {
                            messageOutput.setText("");
                            messageOutput.setIcon(new ImageIcon(getScaledImage(info.toImage(),
                                                                               imageWidth,
                                                                               imageHeight)));
                        } catch (IOException e) {
                            System.out.println(e);
                            // TODO error handling?
                        }
                    } else {
                        // TODO error handling?
                    }

                    exportButton.setEnabled(true);
                    copyToClipboardButton.setEnabled(true);
                    decodeButton.setEnabled(true);
                }
            });
            task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
            task.execute();
        };
    }

}
