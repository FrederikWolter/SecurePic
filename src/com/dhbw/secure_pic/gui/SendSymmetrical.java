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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class SendSymmetrical extends Component {

    private JPanel contentPane;
    private JPanel RightPanel;
    private JButton uploadContainerImg;
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
    private JLabel anzeigeContainerBild;
    private JPanel uploadPanel;
    private JLabel MessageImg;

    final FileSelect fs = new FileSelect();

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
                        anzeigeContainerBild.setText("");
                        anzeigeContainerBild.setIcon(imageIcon);
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

        uploadContainerImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadContainerImg) {
                    File file = fs.SelectFile(SendSymmetrical.this);
                    //ToDo Bildanzeige über das buffered Img aus dem ConatainerImg
                    BufferedImage bufferedImage = null;
                    try {
                        bufferedImage = ImageIO.read(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon imageIcon = new ImageIcon(bufferedImage);
                    AnzeigeConatinerBild.setText("");
                    AnzeigeConatinerBild.setIcon(imageIcon);

                    //ToDo Frederik noch mal anschauen lassen ob die pipeline anbindung passt
                    ContainerImageLoadTask loadImage = new ContainerImageLoadTask(file.getPath());

                }
            }
        });
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton2) {
                    File file = fs.SelectFile(SendSymmetrical.this);
                    //ToDo Bildanzeige über das buffered Img aus dem ConatainerImg
                    BufferedImage bufferedImage = null;
                    try {
                        bufferedImage = ImageIO.read(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ImageIcon imageIcon = new ImageIcon(bufferedImage);
                    MessageImg.setText("");
                    MessageImg.setIcon(imageIcon);

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
        progressBar1.addPropertyChangeListener(new PropertyChangeListener() {
            //ToDo Progress anbinden
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress" == evt.getPropertyName()) {
                    int progress = (Integer) evt.getNewValue();
                    progressBar1.setValue(progress);
                    /*
                    taskOutput.append(String.format(
                            "Completed %d%% of task.\n", task.getProgress()));
                     */
                }
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
