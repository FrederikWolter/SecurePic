package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// TODO comment (normal comments + JDocs) # only delete if final#

public class Send_Symmetrical extends Component {
    private JPanel MainPanel_SS;
    private JProgressBar progressBar1;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JButton backButton;
    private JRadioButton imageRadioButton;
    private JRadioButton textmessageRadioButton;
    private JTextArea Message;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JPasswordField passwordField1;
    private JButton encodeButton;
    private JSlider slider1;
    private JButton uploadButton2;
    private JButton uploadButton;

    final FileSelect fs = new FileSelect();

    public Send_Symmetrical() {
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton2) {
                    File file = fs.SelectFile(Send_Symmetrical.this);
                }
            }
        });
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton) {
                    File file = fs.SelectFile(Send_Symmetrical.this);
                }
            }
        });
    }

    public JPanel getMainPanel_SS() {
        return MainPanel_SS;
    }
    public JButton getBackButton() {
        return backButton;
    }
}
