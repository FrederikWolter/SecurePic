package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ReceiveAsymmetrical extends Component {
    private JPanel MainPanel_RA;
    private JButton uploadButton1;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JButton uploadButton2;
    private JCheckBox encodePublicKeyIntoCheckBox;
    private JButton generateKeyButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton decodeButton;
    private JButton backButton;
    private JLabel path1;
    private JLabel path2;
    private JLabel descrRecImg;
    private JLabel descrPblImg;

    public ReceiveAsymmetrical(Gui parent) {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("3");
            }
        });

        uploadButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(ReceiveAsymmetrical.this);  // TODO unused?
            }
        });
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(ReceiveAsymmetrical.this);  // TODO unused?
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
