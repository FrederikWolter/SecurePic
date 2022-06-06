package com.dhbw.secure_pic.gui;

import javax.swing.*;

// TODO comment (normal comments + JDocs) # only delete if final#

public class StartReceiveChooseEncryption {
    private JButton backButton;
    private JTextPane textPane1;
    private JTextPane textPane2;
    private JTextPane textPane3;
    private JButton noEncryptionButton;
    private JButton symmetricalButton;
    private JButton asymmetricalButton;
    private JPanel MainPanel_RCE;

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

    public JPanel getMainPanel_RCE() {
        return MainPanel_RCE;
    }
}
