package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// TODO comment (normal comments + JDocs) # only delete if final#

public class ReceiveSymmetrical extends Component {
    private JProgressBar progressBar1;
    private JPanel LeftPanel;
    private JButton backButton;
    private JComboBox comboBox_CodAlg;
    private JButton decodeButton;
    private JButton uploadButton2;
    private JTextPane textPane1;
    private JComboBox comboBox_EncAlg;
    private JPasswordField passwordField1;
    private JPanel MainPanel_RS;

    final FileSelect fs = new FileSelect();

    public ReceiveSymmetrical() {
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.SelectFile(ReceiveSymmetrical.this);
            }
        });
    }

    public JPanel getMainPanel_RS() {
        return MainPanel_RS;
    }
    public JButton getBackButton() {
        return backButton;
    }
}