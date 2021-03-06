package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;

import javax.swing.*;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Class representing Receive-Symmetrical {@link GuiView}.<br>
 *
 * @author Kai Schwab, Frederik Wolter
 */
public class ReceiveSymmetrical extends GuiViewReceive {

    /** get resource bundle managing strings */
    private static final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));

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

    /**
     * Constructor of {@link ReceiveSymmetrical}.
     *
     * @param parent parent Gui object
     */
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

        uploadContainerImg.addActionListener(getContainerImageUploadListener(this, finishedContainerImageLoad, progressBar));

        decodeButton.addActionListener(getDecodeListener(codeComboBox, encryptComboBox, passwordField, messageOutput,
                                                         textOutputScroll, textOutput, IMAGE_WIDTH_5, IMAGE_HEIGHT_5,
                                                         exportButton, copyToClipboardButton, decodeButton, progressBar));

        exportButton.addActionListener(getExportInformationListener(this));
        copyToClipboardButton.addActionListener(e -> {
            try {
                contentInformation.copyToClipboard();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("popup.msg.copy_error"), ex.getMessage()),
                                              bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
            }
        });
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
