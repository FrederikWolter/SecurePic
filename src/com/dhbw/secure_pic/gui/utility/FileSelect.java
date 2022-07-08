package com.dhbw.secure_pic.gui.utility;

import javax.swing.*;
import java.awt.*;
import java.io.File;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO no JPEG?

// see https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html

/**
 *
 * @author Kai Schwab, Frederik WOlter
 */
public class FileSelect {
    private final JFileChooser fileChooser = new JFileChooser();

    public File select(Component parent, boolean type) {
        fileChooser.addChoosableFileFilter(new ImageFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnVal;
        if(type){
            returnVal = fileChooser.showSaveDialog(parent);
        } else {
            returnVal = fileChooser.showOpenDialog(parent);
        }

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;    // if no file selected just close selector
        }
    }
}
