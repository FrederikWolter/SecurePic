package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

// TODO comment (normal comments + JDocs) # only delete if final#

public class ReceiveNoEncryption extends GuiViewReceive {

    // region swing attributes
    private JPanel contentPane;
    private JProgressBar progressBar;
    private JButton backButton;
    private JButton decodeButton;
    private JButton uploadContainerImg;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JLabel messageOutput;
    private JComboBox<String> codeComboBox;
    private JPanel uploadPanelContainer;
    private JLabel containerImg;
    private JTextArea textOutput;
    private JScrollPane textOutputScroll;
    // endregion

    public ReceiveNoEncryption(Gui parent) {
        // region finished handler
        LoadImageFinishedHandler finishedContainerImageLoad = image -> {
            containerImage = image;

            containerImg.setText("");
            containerImg.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(), IMAGE_WIDTH_1, IMAGE_HEIGHT_4)));

            decodeButton.setEnabled(true);
        };
        // endregion

        // region drop target
        uploadPanelContainer.setDropTarget(getDropTargetListener(finishedContainerImageLoad, progressBar));
        // endregion

        // region listener
        backButton.addActionListener(e -> parent.showView(Gui.View.START_CHOOSE_ENCRYPTION));

        uploadContainerImg.addActionListener(getImageUploadListener(this, finishedContainerImageLoad, progressBar));

        decodeButton.addActionListener(getDecodeListener(codeComboBox, null, null, messageOutput, textOutputScroll, textOutput, IMAGE_WIDTH_5, IMAGE_HEIGHT_5, exportButton, copyToClipboardButton, decodeButton, progressBar));

        exportButton.addActionListener(getExportInformationListener(this));
        copyToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    contentInformation.copyToClipboard();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Beim Kopieren des Inhalts ist ein Fehler aufgetreten:\n" + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
                }
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
