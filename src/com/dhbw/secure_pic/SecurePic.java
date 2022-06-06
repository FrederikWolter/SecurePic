package com.dhbw.secure_pic;

import com.dhbw.secure_pic.gui.Gui;

import javax.swing.*;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO run code reformat & cleanup before pull request to main

// TODO extract Strings into string resource

// TODO keep in mind the copy to clip board functionality: buttons for that

// TODO universal naming theme with descriptive names for all swing elements

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
                new Gui().show();
            }
        });
    }
}