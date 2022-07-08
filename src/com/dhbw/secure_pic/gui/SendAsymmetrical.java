package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.coder.LeastSignificantBit;
import com.dhbw.secure_pic.coder.PlusMinusOne;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.crypter.EmptyCrypter;
import com.dhbw.secure_pic.crypter.RSA;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.*;
import com.dhbw.secure_pic.gui.utility.handler.DecodeFinishedHandler;
import com.dhbw.secure_pic.gui.utility.handler.EncodeFinishedHandler;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.pipelines.ContainerImageLoadTask;
import com.dhbw.secure_pic.pipelines.DecodeTask;
import com.dhbw.secure_pic.pipelines.EncodeTask;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// TODO comment (normal comments + JDocs) # only delete if final#

public class SendAsymmetrical extends GuiView {

    // region swing attributes
    private JPanel contentPane;
    private JProgressBar progressBar;
    private JButton backButton;
    private JRadioButton imageRadio;
    private JRadioButton textRadio;
    private JTextArea messageText;
    private JButton encodeButton;
    private JButton uploadContainer;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JPanel uploadPanelContainer;
    private JLabel showImageLabel;
    private JPanel uploadPanelMessage;
    private JButton uploadMessageImg;
    private JComboBox codeComboBox;
    private JComboBox encryptComboBox;
    private JPasswordField publicKeyInput;
    private JLabel messageImg;
    private JPanel uploadPanelKey;
    private JButton uploadPrivateKey;
    private JLabel keyImg;
    private JScrollPane messageTextScroll;
    // endregion

    // region attributes
    private transient ContainerImage containerImage;
    private transient ContainerImage contentImage;
    private transient ContainerImage keyImage;
    // endregion

    public SendAsymmetrical(Gui parent) {

        LoadImageFinishedHandler finishedContainerImageLoad = new LoadImageFinishedHandler() {
            @Override
            public void finishedImageLoad(ContainerImage image) {
                containerImage = image;

                showImageLabel.setText("");
                showImageLabel.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(),
                        IMAGE_WIDTH_5,
                        IMAGE_HEIGHT_5)));

                encodeButton.setEnabled(true);
            }
        };

        LoadImageFinishedHandler finishedContentImageLoad = new LoadImageFinishedHandler() {
            @Override
            public void finishedImageLoad(ContainerImage image) {
                contentImage = image;

                messageImg.setText("");
                messageImg.setIcon(new ImageIcon(getScaledImage(contentImage.getImage(),
                        IMAGE_WIDTH_1,
                        IMAGE_HEIGHT_1)));
            }
        };

        LoadImageFinishedHandler finishedKeyImageLoad = new LoadImageFinishedHandler() {
            @Override
            public void finishedImageLoad(ContainerImage image) {
                keyImage = image;

                keyImg.setText("");
                keyImg.setIcon(new ImageIcon(getScaledImage(keyImage.getImage(),
                        IMAGE_WIDTH_1,
                        IMAGE_HEIGHT_1)));

                // region decode key
                Coder coderPublicKey;
                Crypter crypterPublicKey = new EmptyCrypter();

                if (codeComboBox.getSelectedItem() == "LSB"){
                    coderPublicKey = new LeastSignificantBit(keyImage);
                } else if(codeComboBox.getSelectedItem() == "PM1"){
                    coderPublicKey = new PlusMinusOne(keyImage);
                } else {
                    // TODO error handling
                    return;
                }

                DecodeTask task = new DecodeTask(coderPublicKey, crypterPublicKey, new DecodeFinishedHandler() {
                    @Override
                    public void finishedDecode(Information info) {
                        Information.Type type = info.getType();
                        if (type == Information.Type.TEXT) {    // TODO check if plausible key?
                            publicKeyInput.setText(info.toText());
                        } else {
                            JOptionPane.showMessageDialog(null, "Etwas ist schiefgelaufen, das Bild für den öffentlichen Schlüssel enthält keinen Schlüssel.", "Fehler", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                });
                task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                task.execute();
                // endregion
            }
        };

        uploadPanelContainer.setDropTarget(getDropTargetListener(finishedContainerImageLoad, progressBar));

        uploadPanelMessage.setDropTarget(getDropTargetListener(finishedContentImageLoad, progressBar));

        uploadPanelKey.setDropTarget(getDropTargetListener(finishedKeyImageLoad, progressBar));


        // region listeners
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showView(Gui.View.START_CHOOSE_ENCRYPTION);
            }
        });

        uploadContainer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(SendAsymmetrical.this);

                if(file == null){
                    return;
                }

                ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContainerImageLoad);
                task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                task.execute();

                encodeButton.setEnabled(true);
            }
        });

        uploadMessageImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(SendAsymmetrical.this);

                if(file == null){
                    return;
                }

                ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContentImageLoad);
                task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                task.execute();
            }
        });

        uploadPrivateKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(SendAsymmetrical.this);

                if(file == null){
                    return;
                }

                ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedKeyImageLoad);
                task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                task.execute();
            }
        });

        imageRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageTextScroll.setVisible(false);
                uploadPanelMessage.setVisible(true);
            }
        });

        textRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageTextScroll.setVisible(true);
                uploadPanelMessage.setVisible(false);

            }
        });

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Information info;
                Coder coder;
                Crypter crypter;

                if (containerImage == null){
                    // TODO error handling
                    return;
                }

                if (textRadio.isSelected()){
                    if (messageText.getText().length() > 0){
                        info = Information.getInformationFromString(messageText.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte gebe einen Text ein, der in das Bild codiert werden soll.", "Warnung", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else if(imageRadio.isSelected()){
                    if (contentImage != null){
                        try {
                            info = Information.getInformationFromImage(contentImage.getPath());
                        } catch (IllegalTypeException ex) {
                            throw new RuntimeException(ex);
                            // TODO error handling
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte lade einen Bild, das in das Trägerbild codiert werden soll.", "Warnung", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    // TODO error handling
                    return;
                }

                if (codeComboBox.getSelectedItem() == "LSB"){
                    coder = new LeastSignificantBit(containerImage);
                } else if(codeComboBox.getSelectedItem() == "PM1"){
                    coder = new PlusMinusOne(containerImage);
                } else {
                    // TODO error handling
                    return;
                }

                if (encryptComboBox.getSelectedItem() == "RSA"){
                    String publicKey = new String(publicKeyInput.getPassword());
                    if(publicKey.length() > 0){
                        try {
                            crypter = new RSA(publicKey, RSA.keyType.PUBLIC);
                        } catch (CrypterException ex) {
                            throw new RuntimeException(ex);
                            // TODO error handling?
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Bitte gebe einen öffentlichen Schlüssel ein in Form des erhaltenen Bildes oder als Text, mit dem die Information verschlüsselt werden soll.", "Warnung", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } else {
                    // TODO error handling
                    return;
                }

//                encodeButton.setEnabled(false);

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
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new SaveSelect().selectDir(SendAsymmetrical.this);

                if(file == null){
                    return;
                }

                try {
                    containerImage.exportImg(file.getPath());
                    JOptionPane.showMessageDialog(null, "Das codierte Bild wurde erfolgreich exportiert.", "Erfolg",  JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | IllegalTypeException ex) {
                    throw new RuntimeException(ex); // TODO error handling
                }
            }
        });

        copyToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                containerImage.copyToClipboard();
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
