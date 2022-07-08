package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO comment (normal comments + JDocs) # only delete if final#

/**
 * Class for the Choose-Encryption window. In this window one can choose the type of encryption to encrypt the message with.
 * The structure and the components are managed in the StartChooseEncryption.form
 *
 * @author Hassan El-Khalil
 */
public class StartChooseEncryption extends GuiView {
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
                    parent.showView(Gui.View.RECEIVE_NO_ENCRYPTION);
                } else {
                    parent.showView(Gui.View.SEND_NO_ENCRYPTION);
                }
            }
        });
        symmetricalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(parent.getType() == Gui.Type.RECEIVE){
                    parent.showView(Gui.View.RECEIVE_SYMMETRICAL);
                } else {
                    parent.showView(Gui.View.SEND_SYMMETRICAL);
                }
            }
        });
        asymmetricalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(parent.getType() == Gui.Type.RECEIVE){
                    parent.showView(Gui.View.RECEIVE_ASYMMETRICAL);
                } else {
                    parent.showView(Gui.View.SEND_ASYMMETRICAL);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showView(Gui.View.START_CHOOSE_TYPE);
            }
        });
    }

    // region getter
    public JPanel getContentPane() {
        return contentPane;
    }
    // endregion
}
