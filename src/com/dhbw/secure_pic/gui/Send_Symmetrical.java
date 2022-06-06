package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;
import com.dhbw.secure_pic.pipelines.ContainerImageLoadTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// TODO comment (normal comments + JDocs) # only delete if final#

public class Send_Symmetrical extends Component {
    private JPanel MainPanel_SS;
    private JProgressBar progressBar1;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JButton backButton;
    private JRadioButton imageRadioButton;
    private JRadioButton textmessageRadioButton;
    private JTextArea Message;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JPasswordField passwordField1;
    private JButton encodeButton;
    private JButton uploadButton2;
    private JButton uploadButton;
    private JLabel AnzeigeConatinerBild;
    private JLabel AnzeigeFertigesBild;
    private JLabel MessageImg;
    private JButton copyToClipBoardButton;
    private JButton exportButton;

    final FileSelect fs = new FileSelect();

    public Send_Symmetrical() {
        MyDragDropListener myDragDropListener = new MyDragDropListener();

        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton2) {
                    File file = fs.SelectFile(Send_Symmetrical.this);
                    //ToDo klären ob sich dies gleich verhält wie

                }
            }
        });
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton) {
                    File file = fs.SelectFile(Send_Symmetrical.this);
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
