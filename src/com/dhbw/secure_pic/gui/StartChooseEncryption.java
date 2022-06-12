package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class StartChooseEncryption {
    private JPanel contentPane;
    private JButton backButton;
    private JLabel noEncryptDesc;
    private JLabel asymmetricalDesc;
    private JLabel symmetricalDesc;
    private JButton noEncryptionButton;
    private JButton symmetricalButton;
    private JButton asymmetricalButton;

    public StartChooseEncryption(Gui parent){

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