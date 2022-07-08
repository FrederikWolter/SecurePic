package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// TODO comment (normal comments + JDocs) # only delete if final#

public class ImageConverter extends GuiView {
    private JPanel contentPane;
    private JButton secretButton;
    private JProgressBar progressBar;
    private JButton convertButton;
    private JButton uploadButton;
    private JPanel uploadPanel;
    private JLabel showImageLabel;

    public ImageConverter(Gui parent) {
        LoadImageFinishedHandler finishedImageLoad = new LoadImageFinishedHandler() {
            @Override
            public void finishedImageLoad(ContainerImage image) {
                containerImage = image;

                showImageLabel.setText("");
                showImageLabel.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(),
                        IMAGE_WIDTH_5,
                        IMAGE_HEIGHT_5)));
            }
        };

        // region listener
        uploadPanel.setDropTarget(getDropTargetListener(finishedImageLoad, progressBar));

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton) {
                    File file = new FileSelect().selectFile(ImageConverter.this);
                    // TODO Bildanzeige über das buffered Img aus dem ContainerImg
                    BufferedImage bufferedImage = null;
                    try {
                        bufferedImage = ImageIO.read(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon imageIcon = new ImageIcon(getScaledImage(bufferedImage, 600, 600));
                    showImageLabel.setText("");
                    showImageLabel.setIcon(imageIcon);
                }
            }
        });
        secretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set new window title
                ((JFrame) SwingUtilities.getWindowAncestor(contentPane)).setTitle("SecurePic");
                parent.showView(Gui.View.START_CHOOSE_TYPE);
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
