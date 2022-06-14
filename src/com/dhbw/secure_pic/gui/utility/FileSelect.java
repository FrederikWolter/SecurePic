package com.dhbw.secure_pic.gui.utility;

import javax.swing.*;
import java.awt.*;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

// see https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html

/**
 *
 * @author Kai Schwab, Frederik WOlter
 */

public class FileSelect {
    private final JFileChooser fileChooser = new JFileChooser();

    public File selectFile(Component parent) { // parent in der Form fenster.this angeben
        fileChooser.addChoosableFileFilter(new ImageFilter());
        fileChooser.setAcceptAllFileFilterUsed(false);
        int returnVal = fileChooser.showOpenDialog(parent);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            // FIXME error handling?
            return null;
        }
    }
}
