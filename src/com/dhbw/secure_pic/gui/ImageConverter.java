package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;

import javax.swing.*;

/**
 * Class representing the tarn-window/'image-converter' {@link GuiView}.<br>
 *
 * @author Kai Schwab, Frederik Wolter
 */
public class ImageConverter extends GuiView {
    // region swing attributes
    private JPanel contentPane;
    private JButton secretButton;
    private JProgressBar progressBar;
    private JButton convertButton;
    private JButton uploadButton;
    private JPanel uploadPanel;
    private JLabel showImageLabel;
    // endregion

    public ImageConverter(Gui parent) {
        LoadImageFinishedHandler finishedImageLoad = new LoadImageFinishedHandler() {
            @Override
            public void finishedImageLoad(ContainerImage image) {
                containerImage = image;

                showImageLabel.setText("");
                showImageLabel.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(),
                        IMAGE_WIDTH_5,
                        IMAGE_HEIGHT_5)));

                convertButton.setEnabled(false); // the converter functionality is not implemented
            }
        };

        // region listener
        uploadPanel.setDropTarget(getDropTargetListener(finishedImageLoad, progressBar));

        uploadButton.addActionListener(getImageUploadListener(this, finishedImageLoad, progressBar));

        secretButton.addActionListener(e -> {
            parent.getFrame().setTitle("SecurePic"); // change window title
            parent.showView(Gui.View.START_CHOOSE_TYPE);
        });
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
