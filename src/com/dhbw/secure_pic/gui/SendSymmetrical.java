package com.dhbw.secure_pic.gui;


import com.dhbw.secure_pic.gui.utility.FileSelect;
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
import java.io.File;
import java.io.IOException;

public class SendSymmetrical extends Component {

    private JPanel contentPane;
    private JButton uploadButton;
    private JButton backButton;
    private JRadioButton imageRadio;
    private JRadioButton textRadio;
    private JTextArea messageText;
    private JComboBox codeComboBox;
    private JComboBox encryptComboBox;
    private JPasswordField passwordField;
    private JButton encodeButton;
    private JButton uploadButton2;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JProgressBar progressBar;
    private JLabel showImageLabel;
    private JPanel uploadPanel;
    private JLabel messageImgLabel;

    public SendSymmetrical(Gui parent) {

        uploadPanel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // FIXME cleanup cast?

                    for (File file : droppedFiles) {
                        // TODO use load task for that
                        BufferedImage bufferedImage = null;
                        try {
                            bufferedImage = ImageIO.read(file);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        ImageIcon imageIcon = new ImageIcon(bufferedImage);
                        showImageLabel.setText("");
                        showImageLabel.setIcon(imageIcon);
                    }
                } catch (Exception ex) {
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
                File file = new FileSelect().selectFile(SendSymmetrical.this);
                // TODO use load task for that
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                //ToDo Frederik noch mal anschauen lassen ob die pipeline anbindung passt
                // ContainerImageLoadTask loadImage = new ContainerImageLoadTask(file.getPath());

                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                showImageLabel.setText("");
                showImageLabel.setIcon(imageIcon);
            }
        });
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                File file = new FileSelect().selectFile(SendSymmetrical.this);
                //ToDo Bildanzeige über das buffered Img aus dem ContainerImg
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                messageImgLabel.setText("");
                messageImgLabel.setIcon(imageIcon);
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
                //ToDo nach Beendigung des Vorgangs wird das fertige Bild angezeigt

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

        // endregion
    }

    public JPanel getContentPane() {
        return contentPane;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
