package com.dhbw.secure_pic.gui;

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
    private JLabel imageLabel;
    // endregion

    public ImageConverter(Gui parent) {
        // region finished listener
        LoadImageFinishedHandler finishedImageLoad = image -> {
            containerImage = image;

            imageLabel.setText("");
            imageLabel.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(), IMAGE_WIDTH_5, IMAGE_HEIGHT_5)));

            convertButton.setEnabled(false); // the converter functionality is not implemented
        };
        // endregion

        // region drop target
        uploadPanel.setDropTarget(getDropTargetListener(finishedImageLoad, progressBar));
        // endregion

        // region listener
        uploadButton.addActionListener(getMessageImageUploadListener(this, finishedImageLoad, progressBar));

        secretButton.addActionListener(e -> {
            parent.getFrame().setTitle("SecurePic"); // change window title
            parent.showView(Gui.View.START_CHOOSE_TYPE);
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
