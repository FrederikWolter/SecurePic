package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.data.ContainerImage;
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

// FIXME comment (normal comments + JDocs) # only delete if final#
//ToDo ganzes Form überarbeiten und nochmal aktualisieren

public class ReceiveAsymmetrical extends Component {
    private JPanel contentPane;
    private JLabel descrPblImg;
    private JLabel descrRecImg;
    private JButton uploadButtonKeyImage;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JButton uploadButtonConatainerImg;
    private JCheckBox encodePublicKeyIntoCheckBox;
    private JButton generateKeyButton;
    private JTextField privateKeyOutput;
    private JTextField publicKeyOutput;
    private JTextField privateKeyInput;
    private JButton decodeButton;
    private JButton backButton;
    private JLabel KeyImg;
    private JLabel ContainerImage;
    private JPanel OutputMessage;
    private JLabel MessageOutput;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JLabel OutputKeyImage;
    private JButton KeyExport;
    private JButton ctcbKey;
    private JLabel KeyImageOutput;
    private JProgressBar progressBar;
    private JPanel uploadPanel1;
    private JPanel uploadPanel2;
    private JButton CtcbKeyImage;
    private JButton exportKeyImageButton;

    final FileSelect fs = new FileSelect();
    private transient com.dhbw.secure_pic.data.ContainerImage containerImage;
    private transient ContainerImage contentImage;

    public ReceiveAsymmetrical(Gui parent) {


        uploadPanel1.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // FIXME cleanup cast?

                    for (File file : droppedFiles) { // TODO allow multiple files? no? GENERAL

                        //ToDo nochmal anpassen

                    }
                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
            }
        });
        uploadPanel2.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // FIXME cleanup cast?

                    for (File file : droppedFiles) { // TODO allow multiple files? no? GENERAL

                        //ToDo nochmal anpassen
                    }

                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("3");
            }
        });

        uploadButtonKeyImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                File file = new FileSelect().selectFile(ReceiveAsymmetrical.this);
                // TODO use load task for that
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                KeyImg.setText("");
                KeyImg.setIcon(imageIcon);
            }
        });
        uploadButtonConatainerImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                File file = new FileSelect().selectFile(ReceiveAsymmetrical.this);
                // TODO use load task for that
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                ContainerImage.setText("");
                ContainerImage.setIcon(imageIcon);
                decodeButton.setEnabled(true);
            }
        });
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ToDo Key generation
                String privateKey = "";
                String publicKey = "";
                privateKeyOutput.setText(privateKey);
                privateKeyInput.setText(privateKey);
                publicKeyOutput.setText(publicKey);
                exportKeyImageButton.setEnabled(true);
                ctcbKey.setEnabled(true);
                //ToDo Bildanzeige
            }
        });
        encodePublicKeyIntoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(encodePublicKeyIntoCheckBox.isSelected()){
                    uploadPanel2.setVisible(true);
                    OutputKeyImage.setVisible(true);
                    descrPblImg.setVisible(true);
                }
                else{
                    uploadPanel2.setVisible(false);
                    OutputKeyImage.setVisible(false);
                    descrPblImg.setVisible(false);
                }
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        copyToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ctcbKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        KeyExport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        progressBar.addPropertyChangeListener(new PropertyChangeListener() {
            //ToDo Progress anbinden
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress" == evt.getPropertyName()) {
                    int progress = (Integer) evt.getNewValue();
                    progressBar.setValue(progress);
                }
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
