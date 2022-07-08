package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.coder.LeastSignificantBit;
import com.dhbw.secure_pic.coder.PlusMinusOne;
import com.dhbw.secure_pic.crypter.AES;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.handler.DecodeFinishedHandler;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.pipelines.DecodeTask;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO show text not in label but Textarea for automatic line breaks!

public class ReceiveSymmetrical extends GuiViewReceive {
    // region swing attributes
    private JProgressBar progressBar;
    private JButton backButton;
    private JButton decodeButton;
    private JLabel messageOutput;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JComboBox<String> encryptComboBox;
    private JPasswordField passwordField;
    private JPanel contentPane;
    private JButton uploadContainerImg;
    private JPanel uploadPanelContainer;
    private JLabel containerImg;
    private JComboBox<String> codeComboBox;
    // endregion

    public ReceiveSymmetrical(Gui parent) {

        LoadImageFinishedHandler finishedContainerImageLoad = new LoadImageFinishedHandler() {
            @Override
            public void finishedImageLoad(ContainerImage image) {
                containerImage = image;

                containerImg.setText("");
                containerImg.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(),
                        IMAGE_WIDTH_1,
                        IMAGE_HEIGHT_4)));

                decodeButton.setEnabled(true);
            }
        };

        uploadPanelContainer.setDropTarget(getDropTargetListener(finishedContainerImageLoad, progressBar));


        // region listener
        backButton.addActionListener(e -> parent.showView(Gui.View.START_CHOOSE_ENCRYPTION));

        uploadContainerImg.addActionListener(getImageUploadListener(this, finishedContainerImageLoad, progressBar));

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

                if (encryptComboBox.getSelectedItem() == "AES"){
                    String password = new String(passwordField.getPassword());
                    if(password.length() > 0){
                        crypter = new AES(password);
                    }else{
                        JOptionPane.showMessageDialog(null, "Bitte gebe ein Passwort ein, mit dem die Information entschlüsselt werden soll.", "Warnung", WARNING_MESSAGE);
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
                                        IMAGE_WIDTH_5,
                                        IMAGE_HEIGHT_5)));
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

        // endregion
    }

    // region getter
    public JPanel getContentPane() {
        return contentPane;
    }
    // endregion

}
