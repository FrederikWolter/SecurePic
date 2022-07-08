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
import com.dhbw.secure_pic.gui.utility.handler.LoadFinishedHandler;
import com.dhbw.secure_pic.pipelines.ContainerImageLoadTask;
import com.dhbw.secure_pic.pipelines.DecodeTask;
import com.dhbw.secure_pic.pipelines.EncodeTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO show text not in label but Textarea for automatic line breaks!

public class ReceiveAsymmetrical extends GuiView {

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
    private transient ContainerImage containerImage;
    private transient ContainerImage keyImage;
    private transient Information contentInformation;
    // endregion

    public ReceiveAsymmetrical(Gui parent) {

        PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress".equals(evt.getPropertyName())) {
                    int progress = (Integer) evt.getNewValue();
                    progressBar.setValue(progress);
                }
            }
        };

        LoadFinishedHandler finishedContainerImageLoad = new LoadFinishedHandler() {
            @Override
            public void finishedContainerImageLoad(ContainerImage image) {
                containerImage = image;

                containerImg.setText("");
                containerImg.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(),
                        IMAGE_WIDTH_3,
                        IMAGE_HEIGHT_2)));
            }
        };

        LoadFinishedHandler finishedKeyImageLoad = new LoadFinishedHandler() {
            @Override
            public void finishedContainerImageLoad(ContainerImage image) {
                keyImage = image;

                keyImg.setText("");
                keyImg.setIcon(new ImageIcon(getScaledImage(keyImage.getImage(),
                        IMAGE_WIDTH_3,
                        IMAGE_HEIGHT_2)));

                encodePublicKeyIntoCheckBox.setSelected(true);
            }
        };

        uploadPanelContainer.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // TODO cleanup cast?

                    for (File file : droppedFiles) { // TODO allow multiple files? no? GENERAL
                        ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContainerImageLoad);
                        task.addPropertyChangeListener(propertyChangeListener);
                        task.execute();
                    }
                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
                decodeButton.setEnabled(true);
            }
        });

        uploadPanelKey.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // TODO cleanup cast?

                    for (File file : droppedFiles) { // TODO allow multiple files? no? GENERAL
                        ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedKeyImageLoad);
                        task.addPropertyChangeListener(propertyChangeListener);
                        task.execute();
                    }
                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
            }
        });

        // region listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showView(Gui.View.START_CHOOSE_ENCRYPTION);
            }
        });

        uploadButtonKeyImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(ReceiveAsymmetrical.this);

                if(file == null){
                    return;
                }

                ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedKeyImageLoad);
                task.addPropertyChangeListener(propertyChangeListener);
                task.execute();
            }
        });

        uploadContainerImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(ReceiveAsymmetrical.this);

                if(file == null){
                    return;
                }

                ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContainerImageLoad);
                task.addPropertyChangeListener(propertyChangeListener);
                task.execute();

                decodeButton.setEnabled(true);
            }
        });

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
                        task.addPropertyChangeListener(propertyChangeListener);
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
                            exportButton.setEnabled(true);
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

                        copyToClipboardButton.setEnabled(true);
                        decodeButton.setEnabled(true);
                    }
                });
                task.addPropertyChangeListener(propertyChangeListener);
                task.execute();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new SaveSelect().selectDir(ReceiveAsymmetrical.this);

                if(file == null){
                    return;
                }

                if(contentInformation.getType() == Information.Type.TEXT)
                    return;

                try {
                    ImageIO.write(contentInformation.toImage(), "png", file);
                    JOptionPane.showMessageDialog(null, "Das decodierte Bild wurde erfolgreich exportiert.", "Erfolg",  JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex); // TODO error handling
                }
            }
        });

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
                File file = new SaveSelect().selectDir(ReceiveAsymmetrical.this);

                if(file == null)
                    return;

                try {
                    keyImage.exportImg(file.getPath());
                    JOptionPane.showMessageDialog(null, "Das codierte Bild wurde erfolgreich exportiert.", "Erfolg",  JOptionPane.INFORMATION_MESSAGE);
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
