package com.dhbw.secure_pic.gui.utility;

import java.io.File;

// TODO comment (normal comments + JDocs) # only delete if final#

// see https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html

/**
 * @author Kai Schwab, Frederik Wolter
 */

public class FileFilter extends javax.swing.filechooser.FileFilter {
    // region attributes
    private final Extension[] extensions;
    // endregion

    public enum Extension {
        JPEG,
        JPG,
        GIF,
        PNG,
        TXT
    }

    public FileFilter(Extension[] extensions) {
        this.extensions = extensions;
    }

    // Get the extension of a file.
    public static String getExtension(File f) {
        String extension = "";
        String s = f.getName();
        int i = s.lastIndexOf(".");
        if (i > 0 && i < s.length() - 1) extension = s.substring(i + 1);

        return extension.toUpperCase();
    }

    // Accept all directories and all gif, jpg, or png files.
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;   // show subdirectories for navigation in file select

        String extension = getExtension(f);
        for (Extension ex : extensions) {
            if (ex.toString().equals(extension))
                return true;
        }
        return false;
    }

    // The description of this filter
    @Override
    public String getDescription() {
        String desc = "";

        for (Extension ex : extensions) {
            desc += ex.toString() + ", ";
        }
        return desc;
    }
}