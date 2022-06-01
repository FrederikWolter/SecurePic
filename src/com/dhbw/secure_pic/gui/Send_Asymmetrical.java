package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Send_Asymmetrical extends Component {
    private JPanel MainPanel_SA;
    private JProgressBar progressBar1;
    private JPanel LeftPanel;
    private JButton backButton;

    public JButton getBackButton() {
        return backButton;
    }

    private JRadioButton imageRadioButton;
    private JRadioButton textmessageRadioButton;
    private JTextArea Message;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JPasswordField passwordField1;
    private JButton encodeButton;
    private JButton uploadButton2;
    private JPanel RightPanel;
    private JSlider slider1;
    private JButton uploadButton;

    final FileSelect fs = new FileSelect();

    public Send_Asymmetrical() {
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton) {
                    File file = fs.SelectFile(Send_Asymmetrical.this);
                }
            }
        });
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton2) {
                    File file = fs.SelectFile(Send_Asymmetrical.this);
                }
            }
        });
        imageRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message.setVisible(false);
                uploadButton2.setVisible(true);

            }
        });
    }

    public JPanel getMainPanel_SA() {
        return MainPanel_SA;
    }
}
