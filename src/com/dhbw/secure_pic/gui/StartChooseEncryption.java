package com.dhbw.secure_pic.gui;

import javax.swing.*;

/**
 * Class representing Choose-Encryption {@link GuiView}.<br>
 * User can choose between the types of encryption. The layout of the view is defined in 'StartChooseEncryption.form'.
 *
 * @author Hassan El-Khalil, Kai Schwab, Frederik Wolter
 */
public class StartChooseEncryption extends GuiView {
    // region swing attributes
    private JPanel contentPane;
    private JButton backButton;
    private JButton noEncryptionButton;
    private JButton symmetricalButton;
    private JButton asymmetricalButton;
    // endregion

    /**
     * Constructor of {@link StartChooseEncryption}.
     *
     * @param parent parent Gui object
     */
    public StartChooseEncryption(Gui parent) {
        // region listener

        backButton.addActionListener(e ->
                parent.showView(Gui.View.START_CHOOSE_TYPE)
        );

        noEncryptionButton.addActionListener(e -> {
            if (parent.getType() == Gui.Type.RECEIVE) {     // which type was chosen before?
                parent.showView(Gui.View.RECEIVE_NO_ENCRYPTION);
            } else {
                parent.showView(Gui.View.SEND_NO_ENCRYPTION);
            }
        });

        symmetricalButton.addActionListener(e -> {
            if (parent.getType() == Gui.Type.RECEIVE) {     // which type was chosen before?
                parent.showView(Gui.View.RECEIVE_SYMMETRICAL);
            } else {
                parent.showView(Gui.View.SEND_SYMMETRICAL);
            }
        });

        asymmetricalButton.addActionListener(e -> {
            if (parent.getType() == Gui.Type.RECEIVE) {     // which type was chosen before?
                parent.showView(Gui.View.RECEIVE_ASYMMETRICAL);
            } else {
                parent.showView(Gui.View.SEND_ASYMMETRICAL);
            }
        });

        // endregion
    }

    // region getter

    /**
     * @return ContentPane
     */
    public JPanel getContentPane() {
        return contentPane;
    }

    // endregion
}
