package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// FIXME comment (normal comments + JDocs) # only delete if final#

/**
 * Class for the Choose-Encryption window. In this window one can choose the type of encryption to encrypt the message with.
 * The structure and the components are managed in the StartChooseEncryption.form
 *
 * @author Hassan El-Khalil
 */
public class StartChooseEncryption {
    private JPanel contentPane;
    private JButton backButton;
    private JButton noEncryptionButton;
    private JButton symmetricalButton;
    private JButton asymmetricalButton;
    private JTextPane noencDesc;
    private JTextPane symmetricalDesc;
    private JTextPane asymmetricalDesc;

    public StartChooseEncryption(Gui parent){
        // Set an ActionListener for the different encryption-Buttons. When pressed you get transferred to the corresponding window.
        noEncryptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(parent.getType() == Gui.Type.RECEIVE){
                    parent.show("4");
                } else {
                    parent.show("7");
                }
            }
        });
        symmetricalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(parent.getType() == Gui.Type.RECEIVE){
                    parent.show("6");
                } else {
                    parent.show("9");
                }
            }
        });
        asymmetricalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(parent.getType() == Gui.Type.RECEIVE){
                    parent.show("5");
                } else {
                    parent.show("8");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("2");
            }
        });
    }

    // region getter
    public JPanel getContentPane() {
        return contentPane;
    }
    // endregion
}
