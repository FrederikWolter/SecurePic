package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.coder.LeastSignificantBit;
import com.dhbw.secure_pic.coder.PlusMinusOne;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.crypter.EmptyCrypter;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.handler.EncodeFinishedHandler;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.pipelines.EncodeTask;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

// TODO comment (normal comments + JDocs) # only delete if final#

public class SendNoEncryption extends GuiViewSend {

    // region swing attributes
    private JPanel contentPane;
    private JButton backButton;
    private JRadioButton imageRadio;
    private JRadioButton textRadio;
    private JTextArea messageText;
    private JComboBox<String> codeComboBox;
    private JButton encodeButton;
    private JButton uploadMessageImg;
    private JButton uploadContainer;
    private JProgressBar progressBar;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JPanel uploadPanel;
    private JLabel showImageLabel;
    private JPanel uploadPanelMessage;
    private JLabel messageImg;
    private JScrollPane messageTextScroll;
    // endregion


    public SendNoEncryption(Gui parent) {
        // region finished listener
        LoadImageFinishedHandler finishedContainerImageLoad = image -> {
            containerImage = image;

            showImageLabel.setText("");
            showImageLabel.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(), IMAGE_WIDTH_5, IMAGE_HEIGHT_5)));

            encodeButton.setEnabled(true);
        };

        LoadImageFinishedHandler finishedContentImageLoad = image -> {
            contentImage = image;

            messageImg.setText("");
            messageImg.setIcon(new ImageIcon(getScaledImage(contentImage.getImage(), IMAGE_WIDTH_2, IMAGE_HEIGHT_4)));
        };
        // endregion

        // region drop targets
        uploadPanel.setDropTarget(getDropTargetListener(finishedContainerImageLoad, progressBar));

        uploadPanelMessage.setDropTarget(getDropTargetListener(finishedContentImageLoad, progressBar));
        // endregion

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

                crypter = new EmptyCrypter();

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

        exportButton.addActionListener(getExportImageListener(this));

        copyToClipboardButton.addActionListener(e -> containerImage.copyToClipboard());

        // endregion
    }

    // region getter
    public JPanel getContentPane() {
        return contentPane;
    }
    // endregion

}
