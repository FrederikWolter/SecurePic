package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO comment (normal comments + JDocs) # only delete if final#
/**
 * Class for the Choose-Type window. In this window one can choose between the send-Function and the receive-Function.
 * The structure and the components are managed in the StartChooseType.form
 *
 * @author Hassan El-Khalil
 */
public class StartChooseType {
    private JButton backButton;
    private JButton sendButton;
    private JButton receiveButton;
    private JPanel contentPane;
    private JTextPane sendDesc;
    private JTextPane receiveDesc;

    public StartChooseType(Gui parent) {
        // Set an ActionListener for each Button sending the user to the corresponding window
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("1");
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
