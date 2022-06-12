package com.dhbw.secure_pic;

import com.dhbw.secure_pic.gui.Gui;

import javax.swing.*;

// FIXME comment (normal comments + JDocs) # only delete if final#

// FIXME run code reformat & cleanup before pull request to main

// FIXME extract Strings into string resource

// FIXME universal naming theme with descriptive names for all swing elements

// FIXME generalize functionality of views to one class?

/**
 * Main entry point to application - providing the main method.
 *
 * @author Frederik Wolter
 */
public class SecurePic {

    /**
     * MAIN entry point of application.
     *
     * @param args currently not used.
     */
    public static void main(String[] args) {
        // TODO Maybe use new Look and Feel for a better design and user experience?
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
////            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }
        SwingUtilities.invokeLater(Gui::new);
    }
}