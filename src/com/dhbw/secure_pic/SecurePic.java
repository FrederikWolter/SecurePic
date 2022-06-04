package com.dhbw.secure_pic;

import com.dhbw.secure_pic.gui.Collected;

import javax.swing.*;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO run code reformat & cleanup before pull request to main

// TODO extract Strings into string resource

/**
 * Main entry point to application - providing the main method.
 *
 * @author Frederik Wolter
 */
public class SecurePic {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Collected start = new Collected();  // TODO inline?
                start.show();
            }
        });
    }
}