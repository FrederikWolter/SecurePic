package com.dhbw.secure_pic.gui.utility;

import javax.swing.*;
import java.awt.*;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class FileSelect {
    final JFileChooser fc = new JFileChooser();

    public File SelectFile(Component parent) { // parent in der Form fenster.this angeben
        fc.addChoosableFileFilter(new ImageFilter());
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showOpenDialog(parent);

        File file = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            //This is where a real application would open the file.
        } else {
        }
        return file;

    }
}