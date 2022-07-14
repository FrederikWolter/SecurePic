package com.dhbw.secure_pic.gui.utility;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

// TODO no JPEG?

// see https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html

/**
 * Class implementing the functionality of a FileChooser to select and return files
 *
 * @author Kai Schwab, Frederik WOlter
 */
public class FileSelect {
    private final JFileChooser fileChooser = new JFileChooser();

    public File select(Component parent, boolean type, FileFilter filter) {
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Downloads"));

        //Evaluate if a file is to be opened or to be saved
        int returnVal;
        if (type) {
            returnVal = fileChooser.showSaveDialog(parent);
        } else {
            returnVal = fileChooser.showOpenDialog(parent);
        }

        //Return the file if a valid file was opened
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;    // if no file selected just close selector
        }
    }
}
