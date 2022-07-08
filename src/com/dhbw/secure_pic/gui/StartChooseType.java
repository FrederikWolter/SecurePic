package com.dhbw.secure_pic.gui;

import javax.swing.*;


/**
 * Class representing Choose-Type {@link GuiView}.<br>
 * User can choose between send and receive functionality. The layout of the view is defined in 'StartChooseType.form'.
 *
 * @author Hassan El-Khalil, Kai Schwab, Frederik Wolter
 */
public class StartChooseType extends GuiView {

    // region swing attributes
    private JButton backButton;
    private JButton sendButton;
    private JButton receiveButton;
    private JPanel contentPane;
    // endregion

    /**
     * Constructor of {@link StartChooseType}.
     * @param parent parent Gui object
     */
    public StartChooseType(Gui parent) {
        // region listener
        backButton.addActionListener(e -> {
            parent.getFrame().setTitle("Image Converter");  // change window title
            parent.showView(Gui.View.IMAGE_CONVERTER);
        });

        receiveButton.addActionListener(e -> {
            parent.setType(Gui.Type.RECEIVE);   // save chosen type in gui
            parent.showView(Gui.View.START_CHOOSE_ENCRYPTION);
        });

        sendButton.addActionListener(e -> {
            parent.setType(Gui.Type.SEND);      // save chosen type in gui
            parent.showView(Gui.View.START_CHOOSE_ENCRYPTION);
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
