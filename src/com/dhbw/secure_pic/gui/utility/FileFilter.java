package com.dhbw.secure_pic.gui.utility;

import java.io.File;


/**
 * Class inheriting {@link javax.swing.filechooser.FileFilter}.<br>
 * Used for filtering {@link FileSelect}.
 *
 * @author Kai Schwab, Frederik Wolter
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html">Idea</a>
 */
public class FileFilter extends javax.swing.filechooser.FileFilter {

    // region attributes
    /** Array of allowed file extensions */
    private final Extension[] extensions;
    // endregion

    /** Enum with supported extensions */
    public enum Extension {
        JPEG,
        JPG,
        GIF,
        PNG,
        TXT
    }

    /**
     * Constructor of {@link FileFilter}.
     *
     * @param extensions Array of allowed extensions
     */
    public FileFilter(Extension[] extensions) {
        this.extensions = extensions;
    }

    /**
     * Helper method for getting the extension of a file.
     *
     * @param f File
     *
     * @return file extension
     */
    public static String getExtension(File f) {
        String extension = "";
        String s = f.getName();
        int i = s.lastIndexOf(".");
        if (i > 0 && i < s.length() - 1) extension = s.substring(i + 1);

        return extension.toUpperCase();
    }

    /**
     * Show all directories and files with allowed extensions.
     *
     * @param f the File to test
     *
     * @return visible
     */
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

    /**
     * description of this filter.
     *
     * @return description
     */
    @Override
    public String getDescription() {
        String desc = "";

        for (Extension ex : extensions) {
            desc += ex.toString() + ", ";
        }
        return desc;
    }
}