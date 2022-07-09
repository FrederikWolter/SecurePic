package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.coder.LeastSignificantBit;
import com.dhbw.secure_pic.coder.PlusMinusOne;
import com.dhbw.secure_pic.crypter.AES;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.crypter.EmptyCrypter;
import com.dhbw.secure_pic.crypter.RSA;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.FileFilter;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.handler.EncodeFinishedHandler;
import com.dhbw.secure_pic.pipelines.EncodeTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

// TODO comment

public class GuiViewSend extends GuiView {

    // region attributes
    protected transient ContainerImage contentImage;
    // endregion

    protected static ActionListener getInformationTypeListener(int visible, JScrollPane text, JPanel image) {
        return e -> {
            text.setVisible(false);
            image.setVisible(false);

            if (visible == 1) {
                image.setVisible(true);
            } else {
                text.setVisible(true);
            }
        };
    }

    protected ActionListener getExportImageListener(Component parent) {
        return e -> {
            File file = new FileSelect().select(parent, true, new FileFilter(new FileFilter.Extension[]{
                    FileFilter.Extension.PNG // TODO ?
            }));

            if (file == null) return;   // if no destination selected -> simply stop export process

            try {
                containerImage.exportImg(file.getPath());
                JOptionPane.showMessageDialog(null, "Das Bild wurde erfolgreich exportiert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | IllegalTypeException ex) {
                throw new RuntimeException(ex); // TODO error handling
            }
        };
    }

    protected ActionListener getEncodeListener(JRadioButton textRadio, JRadioButton imageRadio, JTextArea messageText,
                                               JComboBox<String> codeComboBox, JComboBox<String> encryptComboBox,
                                               JPasswordField passwordField, JButton exportButton,
                                               JButton copyToClipboardButton, JButton encodeButton, JProgressBar progressBar) {
        return e -> {
            Information info;
            Coder coder;
            Crypter crypter;

            if (containerImage == null) {
                // TODO error handling
                return;
            }

            if (textRadio.isSelected()) {
                if (messageText.getText().length() > 0) {
                    info = Information.getInformationFromString(messageText.getText());
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte gebe einen Text ein, der in das Bild codiert werden soll.", "Warnung", WARNING_MESSAGE);
                    return;
                }
            } else if (imageRadio.isSelected()) {
                if (contentImage != null) {
                    try {
                        info = Information.getInformationFromImage(contentImage.getPath());
                    } catch (IllegalTypeException ex) {
                        throw new RuntimeException(ex);
                        // TODO error handling
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Bitte lade einen Bild, das in das Trägerbild codiert werden soll.", "Warnung", WARNING_MESSAGE);
                    return;
                }
            } else {
                // TODO error handling
                return;
            }

            if (codeComboBox.getSelectedItem() == "LSB") {
                coder = new LeastSignificantBit(containerImage);
            } else if (codeComboBox.getSelectedItem() == "PM1") {
                coder = new PlusMinusOne(containerImage);
            } else {
                // TODO error handling
                return;
            }

            if (encryptComboBox != null) {
                if (encryptComboBox.getSelectedItem() == "AES") {
                    String password = new String(passwordField.getPassword());
                    if (password.length() > 0) {
                        crypter = new AES(password);
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte gebe ein Passwort ein, mit dem die Information verschlüsselt werden soll.", "Warnung", WARNING_MESSAGE);
                        return;
                    }
                } else if (encryptComboBox.getSelectedItem() == "RSA") {
                    String publicKey = new String(passwordField.getPassword());
                    if (publicKey.length() > 0) {
                        try {
                            crypter = new RSA(publicKey, RSA.keyType.PUBLIC);
                        } catch (CrypterException ex) {
                            throw new RuntimeException(ex);
                            // TODO error handling?
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte gebe einen öffentlichen Schlüssel ein in Form des erhaltenen Bildes oder als Text, mit dem die Information verschlüsselt werden soll.", "Warnung", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    // TODO error handling
                    return;
                }
            } else {    // no encryption
                crypter = new EmptyCrypter();
            }


//            encodeButton.setEnabled(false);

            EncodeTask task = new EncodeTask(coder, crypter, info, new EncodeFinishedHandler() {
                @Override
                public void finishedEncode(ContainerImage image) {
                    containerImage = image;

                    exportButton.setEnabled(true);
                    copyToClipboardButton.setEnabled(true);
                    encodeButton.setEnabled(true);
                }
            });
            task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
            task.execute();
        };
    }

}
