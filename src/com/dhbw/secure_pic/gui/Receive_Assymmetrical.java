package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO Assymmetrical -> Asymmetrical

public class Receive_Assymmetrical extends Component {
    private JPanel MainPanel_RA;
    private JButton uploadButton2;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JButton uploadButton3;
    private JCheckBox encodePublicKeyIntoCheckBox;
    private JButton generateKeyButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton decodeButton;
    private JButton backButton;

    final FileSelect fs = new FileSelect();

    public Receive_Assymmetrical() {
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.SelectFile(Receive_Assymmetrical.this);  // TODO unused?
            }
        });
        uploadButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.SelectFile(Receive_Assymmetrical.this);
            }
        });
    }

    public JPanel getMainPanel_RA() {
        return MainPanel_RA;
    }
    public JButton getBackButton() {
        return backButton;
    }
}
