package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO comment (normal comments + JDocs) # only delete if final#

public class StartChooseType {
    private JLabel loremIpsumLabel;
    private JLabel loremIpsumLabel1;
    private JButton closeButton;
    private JButton sendButton;
    private JButton receiveButton;
    private JPanel MainPanel_ST;
    private JComboBox comboBox1;

    public StartChooseType() {
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public JButton getReceiveButton() {
        return receiveButton;
    }

    public JPanel getMainPanel_ST() {
        return MainPanel_ST;
    }
}
