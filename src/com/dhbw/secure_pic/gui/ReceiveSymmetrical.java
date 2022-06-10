package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ReceiveSymmetrical extends Component {
    private JProgressBar progressBar;
    private JButton backButton;
    private JComboBox codeComboBox;
    private JButton decodeButton;
    private JButton uploadButton2;
    private JComboBox comboBox_EncAlg;
    private JPasswordField publicKeyPasswordField;
    private JPanel RightPanel;
    private JLabel MessageOutput;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JLabel ContainerImgAnzeige;
    private JTextPane outTextPane;
    private JComboBox encryptComboBox;
    private JPasswordField passwordField;
    private JPanel contentPane;
    private JPanel uploadPanel;
    private JButton uploadContainerImg;

    final FileSelect fs = new FileSelect();

    public ReceiveSymmetrical(Gui parent) {
        uploadPanel.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // FIXME cleanup cast?

                    for (File file : droppedFiles) { // TODO allow multiple files? no? GENERAL

                        //ToDo noch anppasen

                    }
                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
            }
        });

        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File file = fs.selectFile(ReceiveSymmetrical.this);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("3");
            }
        });

        uploadContainerImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ToDO Uploadfunction

                decodeButton.setEnabled(true);
            }
        });
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ToDo Start und Überprüfung encodeTask

                exportButton.setEnabled(true);
                copyToClipboardButton.setEnabled(true);
            }
        });
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ToDo export Funktion

            }
        });
        copyToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ToDO copy to Clipboard

            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
