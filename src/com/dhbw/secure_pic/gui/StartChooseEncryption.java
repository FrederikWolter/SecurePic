package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class StartChooseEncryption {
    private JPanel MainPanel_SCE;
    private JButton backButton;
    private JTextPane textPane1;
    private JTextPane textPane2;
    private JTextPane textPane3;
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

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getNoEncryptionButton() {
        return noEncryptionButton;
    }

    public JButton getSymmetricalButton() {
        return symmetricalButton;
    }

    public JButton getAsymmetricalButton() {
        return asymmetricalButton;
    }

    public JPanel getMainPanel_SCE() {
        return MainPanel_SCE;
    }
}