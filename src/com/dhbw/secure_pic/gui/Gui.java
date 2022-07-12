package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * Class representing the main GUI class creating the Swing {@link JFrame}, creating the {@link GuiView}
 * and managing the navigation between the views.
 *
 * @author Hassan El-Khalil, Frederik Wolter
 */
public class Gui {

    // region constants
    /**
     * Used for internationalization.<br>
     * Change the locale here and in the IntelliJ Form Designer and the application is in a different language.
     */
    public static final String LOCALE = "en"; //NON-NLS
    /** Path to languages bundle file */
    public static final String LOCALE_PATH = "com/dhbw/secure_pic/gui/languages"; //NON-NLS
    //

    // region swing attributes
    private final JFrame frame;
    private final CardLayout cards;
    private final JPanel contentPane;
    // endregion

    // region attributes
    /** {@link Type} chosen by the user */
    private Type type;
    /** get resource bundle managing strings */
    private final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));
    // endregion

    /**
     * Enum with types of usage.
     */
    public enum Type {
        SEND,
        RECEIVE
    }

    /**
     * Enum with valid {@link GuiView} identifier for navigation.
     */
    public enum View {
        IMAGE_CONVERTER(),
        START_CHOOSE_TYPE,
        START_CHOOSE_ENCRYPTION,
        SEND_NO_ENCRYPTION,
        SEND_SYMMETRICAL,
        SEND_ASYMMETRICAL,
        RECEIVE_NO_ENCRYPTION,
        RECEIVE_SYMMETRICAL,
        RECEIVE_ASYMMETRICAL
    }

    /**
     * Constructor of {@link Gui} creating the Swing {@link JFrame} and the {@link GuiView}s.
     */
    public Gui() {
        // main frame
        frame = new JFrame(bundle.getString("image_con.title"));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1200, 750);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(1200, 750));     // there is a bug in swing,
        // related to Windows scaling displays and minimum windows size, this can lead to problems
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icon.jpg"))).getImage());

        // CardLayout
        cards = new CardLayout(6, 6);
        contentPane = new JPanel(cards);
        frame.add(contentPane);

        // adding each views to contentpane
        contentPane.add(new ImageConverter(this).getContentPane(), View.IMAGE_CONVERTER.toString());
        contentPane.add(new StartChooseType(this).getContentPane(), View.START_CHOOSE_TYPE.toString());
        contentPane.add(new StartChooseEncryption(this).getContentPane(), View.START_CHOOSE_ENCRYPTION.toString());
        contentPane.add(new ReceiveNoEncryption(this).getContentPane(), View.RECEIVE_NO_ENCRYPTION.toString());
        contentPane.add(new ReceiveAsymmetrical(this).getContentPane(), View.RECEIVE_ASYMMETRICAL.toString());
        contentPane.add(new ReceiveSymmetrical(this).getContentPane(), View.RECEIVE_SYMMETRICAL.toString());
        contentPane.add(new SendNoEncryption(this).getContentPane(), View.SEND_NO_ENCRYPTION.toString());
        contentPane.add(new SendAsymmetrical(this).getContentPane(), View.SEND_ASYMMETRICAL.toString());
        contentPane.add(new SendSymmetrical(this).getContentPane(), View.SEND_SYMMETRICAL.toString());

        frame.setVisible(true); // make windows visible after gui has been prepared
    }

    /**
     * Change the shown {@link GuiView}.
     *
     * @param id View to be shown
     */
    public void showView(View id) {
        cards.show(contentPane, id.toString());
    }


    // region getter & setter

    /**
     * @return chosen type
     */
    public Type getType() {
        return this.type;
    }

    /**
     * @param type type of usage
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * @return main frame
     */
    public JFrame getFrame() {
        return frame;
    }

    // endregion
}