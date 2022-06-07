package com.dhbw.secure_pic.gui;


import com.dhbw.secure_pic.gui.functions.FileSelect;
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

    private JPanel MainPanel_SS;
    private JPanel RightPanel;
    private JButton uploadButton;
    private JPanel LeftPanel;
    private JButton backButton;
    private JRadioButton imageRadioButton;
    private JRadioButton textmessageRadioButton;
    private JTextArea Message;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JPasswordField passwordField1;
    private JButton encodeButton;
    private JButton uploadButton2;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JProgressBar progressBar1;
    private JLabel AnzeigeConatinerBild;
    private JPanel Uploadpanel;
    private JLabel MessageImg;

    final FileSelect fs = new FileSelect();

    public SendSymmetrical() {
        Uploadpanel.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        BufferedImage bufferedImage = null;
                        try {
                            bufferedImage = ImageIO.read(file);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        ImageIcon imageIcon = new ImageIcon(bufferedImage);
                        AnzeigeConatinerBild.setIcon(imageIcon);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton) {
                    File file = fs.SelectFile(SendSymmetrical.this);
                    //ToDo Bildanzeige über das buffered Img aus dem ConatainerImg
                    BufferedImage bufferedImage = null;
                    try {
                        bufferedImage = ImageIO.read(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon imageIcon = new ImageIcon(bufferedImage);
                    AnzeigeConatinerBild.setIcon(imageIcon);

                    //ToDo Frederik noch mal anschauen lassen ob die pipeline anbindung passt
                    ContainerImageLoadTask loadImage = new ContainerImageLoadTask(file.getPath());

                }
            }
        });
        uploadButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Handle open button action.
            if (e.getSource() == uploadButton) {
                File file = fs.SelectFile(SendSymmetrical.this);
                //ToDo Bildanzeige über das buffered Img aus dem ConatainerImg
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                AnzeigeConatinerBild.setIcon(imageIcon);


                //ToDo Frederik noch mal anschauen lassen ob die pipeline anbindung passt
                ContainerImageLoadTask loadImage = new ContainerImageLoadTask(file.getPath());

            }
        }
    });
        imageRadioButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Message.setVisible(false);
            uploadButton2.setVisible(true);
            MessageImg.setVisible(true);

        }
    });
        textmessageRadioButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Message.setVisible(true);
            uploadButton2.setVisible(false);
            MessageImg.setVisible(false);
        }
    });
        encodeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            encodeButton.setEnabled(false);
            //ToDo Encode Pipeline
            //ToDo Logik zur Vollständigkeit und Korrektheit der ausgewählten Parameter und deren Verwendung
            //ToDo nach Beendingung des Vorgangs wird das fertige Bild angezeigt

        }
    });
}

    public JPanel getMainPanel_SS() {
        return MainPanel_SS;
    }
    public JButton getBackButton() {
        return backButton;
    }
}
