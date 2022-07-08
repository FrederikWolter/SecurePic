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
import com.dhbw.secure_pic.pipelines.DecodeTask;
import com.dhbw.secure_pic.pipelines.EncodeTask;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO show text not in label but Textarea for automatic line breaks!

public class ReceiveAsymmetrical extends GuiViewReceive {

    // region swing attributes
    private JPanel contentPane;
    private JLabel descrPblImg;
    private JLabel descrRecImg;
    private JCheckBox encodePublicKeyIntoCheckBox;
    private JButton generateKeyButton;
    private JTextField privateKeyOutput;
    private JTextField publicKeyOutput;
    private JButton decodeButton;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JLabel outputKeyImage;
    private JButton keyExport;
    private JButton ctcbKey;
    private JProgressBar progressBar;
    private JPanel uploadPanelContainer;
    private JLabel containerImg;
    private JButton uploadContainerImg;
    private JPanel uploadPanelKey;
    private JButton backButton;
    private JButton uploadButtonKeyImg;
    private JLabel keyImg;
    private JLabel messageOutput;
    private JComboBox codeComboBox;
    private JComboBox encryptComboBox;
    private JTextField privateKeyInput;
    // endregion

    // region attributes
    private transient ContainerImage keyImage;
    // endregion

    public ReceiveAsymmetrical(Gui parent) {

        LoadImageFinishedHandler finishedContainerImageLoad = new LoadImageFinishedHandler() {
            @Override
            public void finishedImageLoad(ContainerImage image) {
                containerImage = image;

                containerImg.setText("");
                containerImg.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(),
                        IMAGE_WIDTH_3,
                        IMAGE_HEIGHT_2)));

                decodeButton.setEnabled(true);
            }
        };

        LoadImageFinishedHandler finishedKeyImageLoad = new LoadImageFinishedHandler() {
            @Override
            public void finishedImageLoad(ContainerImage image) {
                keyImage = image;

                keyImg.setText("");
                keyImg.setIcon(new ImageIcon(getScaledImage(keyImage.getImage(),
                        IMAGE_WIDTH_3,
                        IMAGE_HEIGHT_2)));

                encodePublicKeyIntoCheckBox.setSelected(true);
            }
        };

        uploadPanelContainer.setDropTarget(getDropTargetListener(finishedContainerImageLoad, progressBar));

        uploadPanelKey.setDropTarget(getDropTargetListener(finishedKeyImageLoad, progressBar));


        // region listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showView(Gui.View.START_CHOOSE_ENCRYPTION);
            }
        });


        uploadContainerImg.addActionListener(getImageUploadListener(this, finishedContainerImageLoad, progressBar));

        uploadButtonKeyImg.addActionListener(getImageUploadListener(this, finishedKeyImageLoad, progressBar));


        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Crypter crypter;
                String privateKey;
                String publicKey;

                if(encryptComboBox.getSelectedItem() == "RSA"){
                    try {
                        crypter = new RSA();

                        privateKey = ((RSA) crypter).getPrivateKeyString();
                        publicKey = ((RSA) crypter).getPublicKeyString();

                    } catch (CrypterException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    // TODO error handling?
                    return;
                }

                privateKeyOutput.setText(privateKey);
                publicKeyOutput.setText(publicKey);

                privateKeyInput.setText(privateKey);
                privateKeyInput.setEditable(false);

                keyExport.setEnabled(true);
                ctcbKey.setEnabled(true);

                if(encodePublicKeyIntoCheckBox.isSelected()){
                    if(keyImage != null){
                        // region encode public key
                        Coder coder;
                        Crypter noOpCrypter = new EmptyCrypter();
                        Information info = Information.getInformationFromString(publicKey);

                        if (codeComboBox.getSelectedItem() == "LSB"){
                            coder = new LeastSignificantBit(keyImage);
                        } else if(codeComboBox.getSelectedItem() == "PM1"){
                            coder = new PlusMinusOne(keyImage);
                        } else {
                            // TODO error handling
                            return;
                        }

                        EncodeTask task = new EncodeTask(coder, noOpCrypter, info, new EncodeFinishedHandler() {
                            @Override
                            public void finishedEncode(ContainerImage image) {
                                keyImage = image;

                                keyExport.setEnabled(true);
                                ctcbKey.setEnabled(true);
                                outputKeyImage.setIcon(new ImageIcon(getScaledImage(keyImage.getImage(),
                                        IMAGE_WIDTH_3,
                                        IMAGE_HEIGHT_2)));
                            }
                        });
                        task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                        task.execute();
                        // endregion

                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte lade einen Bild, in das der öffentliche Schlüssel codiert werden soll.", "Warnung", WARNING_MESSAGE);
                        return;
                    }
                }
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Coder coder;
                Crypter crypter;

                if (containerImage == null){
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
                    String privateKey = privateKeyInput.getText();
                    if(privateKey.length() > 20){
                        try {
                            crypter = new RSA(privateKey, RSA.keyType.PRIVATE);
                        } catch (CrypterException ex) {
                            throw new RuntimeException(ex);
                            // TODO error handling
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Bitte gebe den privaten Schlüssel ein, mit dem die Information entschlüsselt werden soll.", "Warnung", WARNING_MESSAGE);
                        return;
                    }
                } else {
                    // TODO error handling
                    return;
                }

//                decodeButton.setEnabled(false);

                DecodeTask task = new DecodeTask(coder, crypter, new DecodeFinishedHandler() {
                    @Override
                    public void finishedDecode(Information info) {
                        contentInformation = info;

                        Information.Type type = info.getType();
                        if (type == Information.Type.TEXT) {
                            messageOutput.setText(info.toText());
                        } else if (type == Information.Type.IMAGE_PNG || type == Information.Type.IMAGE_GIF || type == Information.Type.IMAGE_JPG){
                            try{
                                messageOutput.setText("");
                                messageOutput.setIcon(new ImageIcon(getScaledImage(info.toImage(),
                                        IMAGE_WIDTH_4,
                                        IMAGE_HEIGHT_3)));
                            }catch (IOException e){
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
            }
        });

        exportButton.addActionListener(getExportInformationListener(this));

        copyToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    contentInformation.copyToClipboard();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                    // TODO error handing?
                }
            }
        });

        ctcbKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyImage.copyToClipboard();
            }
        });

        keyExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().select(ReceiveAsymmetrical.this, true, new FileFilter(new FileFilter.Extension[]{
                        FileFilter.Extension.PNG // TODO ?
                }));

                if(file == null) return;

                try {
                    keyImage.exportImg(file.getPath());
                    JOptionPane.showMessageDialog(null, "Das Bild wurde erfolgreich exportiert.", "Erfolg",  JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | IllegalTypeException ex) {
                    throw new RuntimeException(ex); // TODO error handling
                }
            }
        });

        // endregion
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
