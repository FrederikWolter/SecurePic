package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class StartChooseType {
    private JLabel sendDesc;
    private JLabel receiveDesc;
    private JButton closeButton;
    private JButton sendButton;
    private JButton receiveButton;
    private JPanel contentPane;

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

    // region getter
    public JPanel getContentPane() {
        return contentPane;
    }
    // endregion
}
