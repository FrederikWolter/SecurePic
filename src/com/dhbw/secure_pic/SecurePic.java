package com.dhbw.secure_pic;

/**
 * Main entry point to application - providing the main method.
 *
 * @author Frederik Wolter
 */
public class SecurePic {

    // TODO add clipboard functionality

    /**
     * MAIN entry point of application.
     *
     * @param args currently not used.
     */
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