package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ReceiveNoEncryption extends Component {
    private JPanel contentPane;
    private JProgressBar progressBar;
    private JButton backButton;
    private JComboBox comboBox_CodAlg;
    private JButton decodeButton;
    private JButton uploadButton2;
    private JLabel ConatainerImg;
    private JPanel RightPanel;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JLabel MessageOutput;
    private JTextPane outTextPane;

    final FileSelect fs = new FileSelect();

    public ReceiveNoEncryption(Gui parent) {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("3");
            }
        });

        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.selectFile(ReceiveNoEncryption.this);   // TODO unused
            }
        });

    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
