package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Receive_Assymmetrical extends Component {
    private JPanel MainPanel_RA;
    private JButton uploadButton2;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JButton uploadButton3;
    private JCheckBox encodePuplicKeyIntoCheckBox;
    private JButton generateKaeyButton;
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
                File file = fs.SelectFile(Receive_Assymmetrical.this);
            }
        });
        uploadButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.SelectFile(Receive_Assymmetrical.this);
            }
        });
    }
}
