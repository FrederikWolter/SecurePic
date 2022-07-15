package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.crypter.EmptyCrypter;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.pipelines.DecodeTask;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Class representing Send-Asymmetrical {@link GuiView}.<br>
 *
 * @author Kai Schwab, Frederik Wolter
 */
public class SendAsymmetrical extends GuiViewSend {

    /** get resource bundle managing strings */
    private static final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));

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
    /** (Container-)Image for public key */
    private transient ContainerImage keyImage;
    // endregion

    /**
     * Constructor of {@link SendAsymmetrical}.
     *
     * @param parent parent Gui object
     */
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
            Coder coderPublicKey = getCoder(codeComboBox, keyImage);
            Crypter crypterPublicKey = new EmptyCrypter();

            if (coderPublicKey == null) return;     // error massage done in getCoder

            DecodeTask task = new DecodeTask(coderPublicKey, crypterPublicKey, info -> {
                if (info.getType() == Information.Type.TEXT && info.toText().length() >= 600) {
                    publicKeyInput.setText(info.toText());
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("popup.msg.empty_key_image"),
                                                  bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
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

        uploadContainer.addActionListener(getContainerImageUploadListener(this, finishedContainerImageLoad, progressBar));
        uploadMessageImg.addActionListener(getMessageImageUploadListener(this, finishedContentImageLoad, progressBar));
        uploadPrivateKey.addActionListener(getContainerImageUploadListener(this, finishedKeyImageLoad, progressBar));

        imageRadio.addActionListener(getInformationTypeListener(1, messageTextScroll, uploadPanelMessage));
        textRadio.addActionListener(getInformationTypeListener(0, messageTextScroll, uploadPanelMessage));

        encodeButton.addActionListener(getEncodeListener(textRadio, imageRadio, messageText, codeComboBox, encryptComboBox,
                                                         publicKeyInput, exportButton, copyToClipboardButton, encodeButton, progressBar));

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
