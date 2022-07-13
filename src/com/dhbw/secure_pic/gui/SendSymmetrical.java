package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

// TODO comment (normal comments + JDocs) # only delete if final#

/**
 * Class representing Send-Symmetrical {@link GuiView}.<br>
 *
 * @author Kai Schwab, Frederik Wolter
 */
public class SendSymmetrical extends GuiViewSend {

    /** get resource bundle managing strings */
    private static final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));

    // region swing attributes
    private JPanel contentPane;
    private JButton backButton;
    private JRadioButton imageRadio;
    private JRadioButton textRadio;
    private JTextArea messageText;
    private JComboBox<String> codeComboBox;
    private JComboBox<String> encryptComboBox;
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

    /**
     * Constructor of {@link SendSymmetrical}.
     *
     * @param parent parent Gui object
     */
    public SendSymmetrical(Gui parent) {
        // region finished handler
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

        encodeButton.addActionListener(getEncodeListener(textRadio, imageRadio, messageText, codeComboBox, encryptComboBox, passwordField, exportButton, copyToClipboardButton, encodeButton, progressBar));

        exportButton.addActionListener(getExportImageListener(this));
        copyToClipboardButton.addActionListener(e -> containerImage.copyToClipboard());

        // endregion
    }

    // region getter

    /**
     * @return ContentPane
     */
    public JPanel getContentPane() {
        return contentPane;
    }

    // endregion
}
