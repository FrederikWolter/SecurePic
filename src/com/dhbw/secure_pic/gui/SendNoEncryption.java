package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;

import javax.swing.*;


/**
 * Class representing Send-NoEncryption {@link GuiView}.<br>
 *
 * @author Kai Schwab, Frederik Wolter
 */
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

    /**
     * Constructor of {@link SendNoEncryption}.
     *
     * @param parent parent Gui object
     */
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

        uploadContainer.addActionListener(getContainerImageUploadListener(this, finishedContainerImageLoad, progressBar));
        uploadMessageImg.addActionListener(getMessageImageUploadListener(this, finishedContentImageLoad, progressBar));

        imageRadio.addActionListener(getInformationTypeListener(1, messageTextScroll, uploadPanelMessage));
        textRadio.addActionListener(getInformationTypeListener(0, messageTextScroll, uploadPanelMessage));

        encodeButton.addActionListener(getEncodeListener(textRadio, imageRadio, messageText, codeComboBox, null, null,
                                                         exportButton, copyToClipboardButton, encodeButton, progressBar));

        exportButton.addActionListener(getExportImageListener(this));
        copyToClipboardButton.addActionListener(e -> containerImage.copyToClipboard());
        // endregion
    }

    // region getter

    /**
     * Due to a constraint by the GUI designer a form can not be a {@link JPanel} therefore a {@link JPanel} is placed
     * directly inside a form and can be retrieved through this getter.
     *
     * @return ContentPane
     */
    public JPanel getContentPane() {
        return contentPane;
    }

    // endregion

}
