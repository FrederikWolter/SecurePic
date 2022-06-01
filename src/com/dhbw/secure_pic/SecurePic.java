package com.dhbw.secure_pic;

import com.dhbw.secure_pic.gui.Collected;

import javax.swing.*;

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
                Collected start = new Collected();
                start.show();
            }
        });
    }
}