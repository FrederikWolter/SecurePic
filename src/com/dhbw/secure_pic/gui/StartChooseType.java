package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class StartChooseType {
    private JTextPane loremIpsumTextPane;
    private JTextPane loremIpsumTextPane1;
    private JButton closeButton;
    private JButton sendButton;
    private JButton receiveButton;
    private JPanel contentPane;
    private JComboBox comboBox1;

    public StartChooseType(Gui parent) {
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.close();
            }
        });

        receiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setType(Gui.Type.RECEIVE);
                parent.show("3");
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.setType(Gui.Type.SEND);
                parent.show("3");
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

    public JPanel getContentPane() {
        return contentPane;
    }
}
