package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.coder.LeastSignificantBit;
import com.dhbw.secure_pic.coder.PlusMinusOne;
import com.dhbw.secure_pic.crypter.AES;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.handler.EncodeFinishedHandler;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.gui.utility.SaveSelect;
import com.dhbw.secure_pic.pipelines.ContainerImageLoadTask;
import com.dhbw.secure_pic.pipelines.EncodeTask;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

// TODO comment (normal comments + JDocs) # only delete if final#

public class SendSymmetrical extends GuiView {

    // region swing attributes
    private JPanel contentPane;
    private JButton backButton;
    private JRadioButton imageRadio;
    private JRadioButton textRadio;
    private JTextArea messageText;
    private JComboBox codeComboBox;
    private JComboBox encryptComboBox;
    private JPasswordField passwordField;
    private JButton encodeButton;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JProgressBar progressBar;
    private JLabel showImageLabel;
    private JPanel uploadPanel;
    private JPanel uploadPanelMessage;
    private JLabel messageImg;
    private JButton uploadMessageImg;
    private JButton uploadContainer;
    private JScrollPane messageTextScroll;
    // endregion

    // region attributes
    private transient ContainerImage containerImage;
    private transient ContainerImage contentImage;

    // endregion

    public SendSymmetrical(Gui parent) {

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
                        IMAGE_WIDTH_2,
                        IMAGE_HEIGHT_4)));
            }
        };

        uploadPanel.setDropTarget(getDropTargetListener(finishedContainerImageLoad, progressBar));

        uploadPanelMessage.setDropTarget(getDropTargetListener(finishedContentImageLoad, progressBar));


        // region listener
        backButton.addActionListener(e -> parent.showView(Gui.View.START_CHOOSE_ENCRYPTION));

        uploadContainer.addActionListener(getImageUploadListener(this, finishedContainerImageLoad, progressBar));

        uploadMessageImg.addActionListener(getImageUploadListener(this, finishedContentImageLoad, progressBar));

        imageRadio.addActionListener(getInformationTypeListener(1, messageTextScroll, uploadPanelMessage));

        textRadio.addActionListener(getInformationTypeListener(0, messageTextScroll, uploadPanelMessage));

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
                        JOptionPane.showMessageDialog(null, "Bitte gebe einen Text ein, der in das Bild codiert werden soll.", "Warnung", WARNING_MESSAGE);
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
                        JOptionPane.showMessageDialog(null, "Bitte lade einen Bild, das in das Trägerbild codiert werden soll.", "Warnung", WARNING_MESSAGE);
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

                if (encryptComboBox.getSelectedItem() == "AES"){
                    String password = new String(passwordField.getPassword());
                    if(password.length() > 0){
                        crypter = new AES(password);
                    }else{
                        JOptionPane.showMessageDialog(null, "Bitte gebe ein Passwort ein, mit dem die Information verschlüsselt werden soll.", "Warnung", WARNING_MESSAGE);
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
                File file = new SaveSelect().selectDir(SendSymmetrical.this);

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
