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
import com.dhbw.secure_pic.gui.utility.handler.DecodeFinishedHandler;
import com.dhbw.secure_pic.gui.utility.handler.EncodeFinishedHandler;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.pipelines.DecodeTask;
import com.dhbw.secure_pic.pipelines.EncodeTask;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO comment (normal comments + JDocs) # only delete if final#

public class SendAsymmetrical extends GuiViewSend {

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
    private JComboBox<String> codeComboBox;
    private JComboBox<String> encryptComboBox;
    private JPasswordField publicKeyInput;
    private JLabel messageImg;
    private JPanel uploadPanelKey;
    private JButton uploadPrivateKey;
    private JLabel keyImg;
    private JScrollPane messageTextScroll;
    // endregion

    // region attributes
    private transient ContainerImage keyImage;
    // endregion

    public SendAsymmetrical(Gui parent) {
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
            messageImg.setIcon(new ImageIcon(getScaledImage(contentImage.getImage(), IMAGE_WIDTH_1, IMAGE_HEIGHT_1)));
        };

        LoadImageFinishedHandler finishedKeyImageLoad = image -> {
            keyImage = image;

            keyImg.setText("");
            keyImg.setIcon(new ImageIcon(getScaledImage(keyImage.getImage(), IMAGE_WIDTH_1, IMAGE_HEIGHT_1)));

            // region decode key
            Coder coderPublicKey;
            Crypter crypterPublicKey = new EmptyCrypter();

            // TODO extract getCoder & getCrypter?
            if (codeComboBox.getSelectedItem() == "LSB") {
                coderPublicKey = new LeastSignificantBit(keyImage);
            } else if (codeComboBox.getSelectedItem() == "PM1") {
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
                    }
                }
            });
            task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
            task.execute();
            // endregion
        };
        // endregion

        // region drop targets
        uploadPanelContainer.setDropTarget(getDropTargetListener(finishedContainerImageLoad, progressBar));
        uploadPanelMessage.setDropTarget(getDropTargetListener(finishedContentImageLoad, progressBar));
        uploadPanelKey.setDropTarget(getDropTargetListener(finishedKeyImageLoad, progressBar));
        // endregion


        // region listeners
        backButton.addActionListener(e -> parent.showView(Gui.View.START_CHOOSE_ENCRYPTION));

        uploadContainer.addActionListener(getImageUploadListener(this, finishedContainerImageLoad, progressBar));
        uploadMessageImg.addActionListener(getImageUploadListener(this, finishedContentImageLoad, progressBar));
        uploadPrivateKey.addActionListener(getImageUploadListener(this, finishedKeyImageLoad, progressBar));

        imageRadio.addActionListener(getInformationTypeListener(1, messageTextScroll, uploadPanelMessage));
        textRadio.addActionListener(getInformationTypeListener(0, messageTextScroll, uploadPanelMessage));

        encodeButton.addActionListener(getEncodeListener(textRadio, imageRadio, messageText, codeComboBox, encryptComboBox, publicKeyInput, exportButton, copyToClipboardButton, encodeButton, progressBar));

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
