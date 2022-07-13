package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Class representing Choose-Type {@link GuiView}.<br>
 * User can choose between send and receive functionality. The layout of the view is defined in 'StartChooseType.form'.
 *
 * @author Hassan El-Khalil, Kai Schwab, Frederik Wolter
 */
public class StartChooseType extends GuiView {

    /** get resource bundle managing strings */
    private static final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));

    // region swing attributes
    private JButton backButton;
    private JButton sendButton;
    private JButton receiveButton;
    private JPanel contentPane;
    // endregion

    /**
     * Constructor of {@link StartChooseType}.
     *
     * @param parent parent Gui object
     */
    public StartChooseType(Gui parent) {
        // region listener
        backButton.addActionListener(e -> {
            parent.getFrame().setTitle(bundle.getString("image_con.title"));  // change window title
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
     * Due to a constraint by the GUI designer a form can not be a {@link JPanel} therefore a {@link JPanel} is placed
     * directly inside a form and can be retrieved through this getter.
     *
     * @return ContentPane
     */
    public JPanel getContentPane() {
        return contentPane;
    }

    // endregion
}
