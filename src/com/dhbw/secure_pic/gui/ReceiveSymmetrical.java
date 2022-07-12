package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;

import javax.swing.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

// TODO comment (normal comments + JDocs) # only delete if final#

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
    private JScrollPane textOutputScroll;
    private JTextArea textOutput;
    // endregion

    /** get resource bundle managing strings */
    private static final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));

    public ReceiveSymmetrical(Gui parent) {
        // region finished handler
        LoadImageFinishedHandler finishedContainerImageLoad = image -> {
            containerImage = image;

            containerImg.setText("");
            containerImg.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(), IMAGE_WIDTH_1, IMAGE_HEIGHT_4)));

            decodeButton.setEnabled(true);
        };
        // endregion

        // region drop targets
        uploadPanelContainer.setDropTarget(getDropTargetListener(finishedContainerImageLoad, progressBar));
        // endregion

        // region listener
        backButton.addActionListener(e -> parent.showView(Gui.View.START_CHOOSE_ENCRYPTION));

        uploadContainerImg.addActionListener(getImageUploadListener(this, finishedContainerImageLoad, progressBar));

        decodeButton.addActionListener(getDecodeListener(codeComboBox, encryptComboBox, passwordField, messageOutput, textOutputScroll, textOutput, IMAGE_WIDTH_5, IMAGE_HEIGHT_5, exportButton, copyToClipboardButton, decodeButton, progressBar));

        exportButton.addActionListener(getExportInformationListener(this));
        copyToClipboardButton.addActionListener(e -> {
            try {
                contentInformation.copyToClipboard();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("popup.msg.copy_error"), ex.getMessage()), bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
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
