package com.dhbw.secure_pic;

import com.dhbw.secure_pic.gui.Gui;

import javax.swing.*;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO extract Strings into string resource

// TODO extract hardcoded strings: https://www.jetbrains.com/help/idea/hard-coded-string-literals.html#extract

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
        SwingUtilities.invokeLater(Gui::new);
    }
}