package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// TODO comment (normal comments + JDocs) # only delete if final#

public class ReceiveNoEncryption extends Component {
    private JPanel MainPanel_RN;
    private JProgressBar progressBar1;
    private JPanel LeftPanel;
    private JButton backButton;
    private JComboBox comboBox_CodAlg;
    private JButton decodeButton;
    private JButton uploadButton2;
    private JTextPane textPane1;

    final FileSelect fs = new FileSelect();

    public ReceiveNoEncryption() {
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.SelectFile(ReceiveNoEncryption.this);   // TODO unused
            }
        });

    }

    public JPanel getMainPanel_RN() {
        return MainPanel_RN;
    }
    public JButton getBackButton() {
        return backButton;
    }
}
