package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Send_NoEncryption extends Component {
    private JPanel MainPanel_SN;
    private JPanel LeftPanel;
    private JButton backButton;
    private JRadioButton imageRadioButton;
    private JRadioButton textmessageRadioButton;
    private JTextArea Message;
    private JComboBox comboBox_CodAlg;
    private JButton encodeButton;
    private JButton uploadButton2;
    private JPanel RightPanel;
    private JSlider slider1;
    private JButton uploadButton;
    private JProgressBar progressBar1;

    final FileSelect fs = new FileSelect();

    public Send_NoEncryption() {
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton) {
                    File file = fs.SelectFile(Send_NoEncryption.this);
                }

            }
        });
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton2) {
                    File file = fs.SelectFile(Send_NoEncryption.this);
                }

            }
        });
    }
}
