package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.LoadFinishedHandler;
import com.dhbw.secure_pic.pipelines.ContainerImageLoadTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class SendNoEncryption extends Component {

    // region swing attributes
    private JPanel contentPane;
    private JButton backButton;
    private JRadioButton imageRadio;
    private JRadioButton textRadio;
    private JTextArea messageText;
    private JComboBox codeComboBox;
    private JButton encodeButton;
    private JButton uploadButton2;
    private JButton uploadButton;
    private JProgressBar progressBar;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JPanel uploadPanel;
    private JLabel showImageLabel;
    private JLabel messageImgLabel;

    // endregion

    // region attributes
    private ContainerImage containerImage;
    private ContainerImage contentImage;
    // endregion


    public SendNoEncryption(Gui parent) {

        LoadFinishedHandler finishedContainerImageLoad = new LoadFinishedHandler() {
            @Override
            public void finishedContainerImageLoad(ContainerImage image) {
                containerImage = image;

                showImageLabel.setText("");
                showImageLabel.setIcon(new ImageIcon(containerImage.getImage()));
            }
        };

        LoadFinishedHandler finishedContentImageLoad = new LoadFinishedHandler() {
            @Override
            public void finishedContainerImageLoad(ContainerImage image) {
                contentImage = image;

                messageImgLabel.setText("");
                messageImgLabel.setIcon(new ImageIcon(contentImage.getImage()));
            }
        };

        uploadPanel.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);        // FIXME cleanup cast?

                    for (File file : droppedFiles) {    // TODO allow multiple files? no? GENERAL
                        new ContainerImageLoadTask(file.getPath(), finishedContainerImageLoad).execute();
                    }
                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
            }
        });

        // region listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("3");
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                File file = new FileSelect().selectFile(SendNoEncryption.this);
                //TODO error handling?
                new ContainerImageLoadTask(file.getPath(), finishedContainerImageLoad).execute();
            }
        });

        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                File file = new FileSelect().selectFile(SendNoEncryption.this);
                //TODO error handling?
                new ContainerImageLoadTask(file.getPath(), finishedContentImageLoad).execute();
            }
        });

        imageRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageText.setVisible(false);
                uploadButton2.setVisible(true);
                messageImgLabel.setVisible(true);
            }
        });

        textRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageText.setVisible(true);
                uploadButton2.setVisible(false);
                messageImgLabel.setVisible(false);
            }
        });

        encodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                encodeButton.setEnabled(false);
                //ToDo Encode Pipeline
                //ToDo Logik zur Vollständigkeit und Korrektheit der ausgewählten Parameter und deren Verwendung
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ToDo Exportfunktion schreiben
            }
        });

        copyToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ToDo Exportfunktion schreiben

            }
        });

        progressBar.addPropertyChangeListener(new PropertyChangeListener() {
            //ToDo Progress anbinden
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress" == evt.getPropertyName()) {
                    int progress = (Integer) evt.getNewValue();
                    progressBar.setValue(progress);
                    /*
                    taskOutput.append(String.format(
                            "Completed %d%% of task.\n", task.getProgress()));
                     */
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
