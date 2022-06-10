package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ReceiveNoEncryption extends Component {
    private JPanel MainPanel_RN;
    private JProgressBar progressBar1;
    private JPanel LeftPanel;
    private JButton backButton;
    private JComboBox comboBox_CodAlg;
    private JButton decodeButton;
    private JButton uploadButton2;
    private JLabel ConatainerImg;
    private JPanel RightPanel;
    private JLabel MessageOutput;
    private JButton copyToClipboardButton;
    private JButton exportButton;

    final FileSelect fs = new FileSelect();

    public ReceiveNoEncryption(Gui parent) {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("3");
            }
        });

        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.selectFile(ReceiveNoEncryption.this);   // TODO unused
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
