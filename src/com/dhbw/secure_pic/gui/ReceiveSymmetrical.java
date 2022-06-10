package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ReceiveSymmetrical extends Component {
    private JProgressBar progressBar;
    private JButton backButton;
    private JComboBox codeComboBox;
    private JButton decodeButton;
    private JButton uploadButton2;
    private JComboBox comboBox_EncAlg;
    private JPasswordField passwordField1;
    private JPanel MainPanel_RS;
    private JPanel RightPanel;
    private JLabel MessageOutput;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JLabel ContainerImgAnzeige;
    private JTextPane outTextPane;
    private JComboBox encryptComboBox;
    private JPasswordField passwordField;
    private JPanel contentPane;

    final FileSelect fs = new FileSelect();

    public ReceiveSymmetrical(Gui parent) {
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.selectFile(ReceiveSymmetrical.this);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("3");
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }
    public JButton getBackButton() {
        return backButton;
    }
}
