package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.crypter.EmptyCrypter;
import com.dhbw.secure_pic.crypter.RSA;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.FileFilter;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.pipelines.EncodeTask;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

// TODO comment (normal comments + JDocs) # only delete if final#

public class ReceiveAsymmetrical extends GuiViewReceive {

    // region swing attributes
    private JPanel contentPane;
    private JCheckBox encodePublicKeyIntoCheckBox;
    private JButton generateKeyButton;
    private JTextField privateKeyOutput;
    private JTextField publicKeyOutput;
    private JButton decodeButton;
    private JButton copyToClipboardContent;
    private JButton exportButton;
    private JLabel outputKeyImage;
    private JButton keyExport;
    private JButton copyToClipboardKey;
    private JProgressBar progressBar;
    private JPanel uploadPanelContainer;
    private JLabel containerImg;
    private JButton uploadContainerImg;
    private JPanel uploadPanelKey;
    private JButton backButton;
    private JButton uploadButtonKeyImg;
    private JLabel keyImg;
    private JLabel messageOutput;
    private JComboBox<String> codeComboBox;
    private JComboBox<String> encryptComboBox;
    private JPasswordField privateKeyInput;
    private JScrollPane textOutputScroll;
    private JTextArea textOutput;
    // endregion

    // region attributes
    private transient ContainerImage keyImage;
    // endregion

    public ReceiveAsymmetrical(Gui parent) {
        // region finished handler
        LoadImageFinishedHandler finishedContainerImageLoad = image -> {
            containerImage = image;

            containerImg.setText("");
            containerImg.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(), IMAGE_WIDTH_3, IMAGE_HEIGHT_2)));

            decodeButton.setEnabled(true);
        };

        LoadImageFinishedHandler finishedKeyImageLoad = image -> {
            keyImage = image;

            keyImg.setText("");
            keyImg.setIcon(new ImageIcon(getScaledImage(keyImage.getImage(), IMAGE_WIDTH_3, IMAGE_HEIGHT_2)));

            encodePublicKeyIntoCheckBox.setSelected(true);
        };
        // endregion

        // region drop targets
        uploadPanelContainer.setDropTarget(getDropTargetListener(finishedContainerImageLoad, progressBar));
        uploadPanelKey.setDropTarget(getDropTargetListener(finishedKeyImageLoad, progressBar));
        // endregion

        // region listener
        backButton.addActionListener(e -> parent.showView(Gui.View.START_CHOOSE_ENCRYPTION));

        uploadContainerImg.addActionListener(getImageUploadListener(this, finishedContainerImageLoad, progressBar));
        uploadButtonKeyImg.addActionListener(getImageUploadListener(this, finishedKeyImageLoad, progressBar));

        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Crypter crypter;
                String privateKey;
                String publicKey;

                if (encryptComboBox.getSelectedItem() == "RSA") {
                    try {
                        crypter = new RSA();
                        privateKey = ((RSA) crypter).getPrivateKeyString();
                        publicKey = ((RSA) crypter).getPublicKeyString();
                    } catch (CrypterException ex) {
                        throw new RuntimeException(ex);     // TODO error handling?
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Der ausgewählte Verschlüsselung-Algorithmus entspricht keinem gültigen Wert: " + encryptComboBox.getSelectedItem(), "Fehler", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                privateKeyOutput.setText(privateKey);
                publicKeyOutput.setText(publicKey);
                privateKeyInput.setText(privateKey);
                privateKeyInput.setEditable(false);

                keyExport.setEnabled(true);
                copyToClipboardKey.setEnabled(true);

                // region encode public key
                if (encodePublicKeyIntoCheckBox.isSelected()) {
                    if (keyImage != null) {
                        Coder coder = getCoder(codeComboBox, keyImage);
                        Crypter noOpCrypter = new EmptyCrypter();
                        Information info = Information.getInformationFromString(publicKey);

                        if (coder == null) return;      // error massage done in getCoder

                        EncodeTask task = new EncodeTask(coder, noOpCrypter, info, image -> {
                            keyImage = image;

                            keyExport.setEnabled(true);
                            copyToClipboardKey.setEnabled(true);
                            outputKeyImage.setIcon(
                                    new ImageIcon(getScaledImage(keyImage.getImage(), IMAGE_WIDTH_3, IMAGE_HEIGHT_2))
                            );
                        });
                        task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                        task.execute();

                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte lade einen Bild, in das der öffentliche Schlüssel codiert werden soll.", "Warnung", WARNING_MESSAGE);
                    }
                    // endregion
                }
            }
        });

        decodeButton.addActionListener(getDecodeListener(codeComboBox, encryptComboBox, privateKeyInput, messageOutput, textOutputScroll, textOutput, IMAGE_WIDTH_4, IMAGE_HEIGHT_3, exportButton, copyToClipboardContent, decodeButton, progressBar));

        exportButton.addActionListener(getExportInformationListener(this));
        copyToClipboardContent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    contentInformation.copyToClipboard();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Beim Kopieren des Inhalts ist ein Fehler aufgetreten: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        copyToClipboardKey.addActionListener(e -> keyImage.copyToClipboard());

        keyExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().select(ReceiveAsymmetrical.this, true, new FileFilter(new FileFilter.Extension[]{
                        FileFilter.Extension.PNG // TODO ?
                }));

                if (file == null) return;

                try {
                    keyImage.exportImg(file.getPath());
                    JOptionPane.showMessageDialog(null, "Das Bild wurde erfolgreich exportiert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | IllegalTypeException ex) {
                    throw new RuntimeException(ex); // TODO error handling
                }
            }
        });
        // endregion
    }

    // region getter

    public JPanel getContentPane() {
        return contentPane;
    }

    // endregion

}
