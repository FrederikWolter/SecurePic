package com.dhbw.secure_pic.gui.utility;

import javax.swing.filechooser.FileFilter;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ImageFilter extends FileFilter {

    // FIXME static necessary?
    public static final String JPEG = "jpeg";
    public static final String JPG = "jpg";
    public static final String GIF = "gif";
    public static final String PNG = "png";


    //Accept all directories and all gif, jpg, or png files.
    public boolean accept(File f) {
        if (f.isDirectory()) {  // show subdirectories for navigation in file select
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
            return extension.equals(GIF)
                    || extension.equals(JPEG)
                    || extension.equals(JPG)
                    || extension.equals(PNG);
        }
        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "*jpg, *png, *gif";
    }

    // Get the extension of a file.
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}