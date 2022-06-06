package com.dhbw.secure_pic.gui;

import javax.swing.*;

// TODO comment (normal comments + JDocs) # only delete if final#

public class StartSendChooseEncryption {
    private JPanel MainPanel_SCE;
    private JButton backButton;
    private JTextPane textPane1;
    private JTextPane textPane2;
    private JTextPane textPane3;
    private JButton noEncryptionButton;
    private JButton symmetricalButton;
    private JButton asymmetricalButton;

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
