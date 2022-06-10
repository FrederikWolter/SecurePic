package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

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

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ImageConverter extends Component {
    private JPanel contentPane;
    private JButton secretButton;
    private JProgressBar progressBar;
    private JButton convertButton;
    private JPanel MainPanel_ImgCon;
    private JButton buttonStartApp;
    private JProgressBar progressBar1;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JButton button2;
    private JButton uploadButton;
    private JPanel RightPanel;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JPanel Uploadpanel;
    private JButton uploadContainerImg;
    private JLabel AnzeigeConatinerBild;

    final FileSelect fs = new FileSelect();

    public ImageConverter(Gui parent) {
        // region listener
        secretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set new window title
                ((JFrame) SwingUtilities.getWindowAncestor(contentPane)).setTitle("SecurePic");
                parent.show("2");
            }
        });

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
                        AnzeigeConatinerBild.setText("");
                        AnzeigeConatinerBild.setIcon(imageIcon);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        uploadContainerImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadContainerImg) {
                    File file = fs.selectFile(ImageConverter.this);
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
