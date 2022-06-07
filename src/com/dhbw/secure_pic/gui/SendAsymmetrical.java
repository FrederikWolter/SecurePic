package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// TODO comment (normal comments + JDocs) # only delete if final#

public class SendAsymmetrical extends Component {
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

    public SendAsymmetrical() {
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton) {
                    File file = fs.SelectFile(SendAsymmetrical.this);
                }
            }
        });
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton2) {
                    File file = fs.SelectFile(SendAsymmetrical.this);
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
        textmessageRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message.setVisible(true);
                uploadButton2.setVisible(false);
            }
        });
    }

    public JPanel getMainPanel_SA() {
        return MainPanel_SA;
    }
}
