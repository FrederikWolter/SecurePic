package com.dhbw.secure_pic.gui.functions;

import javax.swing.filechooser.FileFilter;
import java.io.File;

// TODO comment (normal comments + JDocs) # only delete if final#

public class ImageFilter extends FileFilter {

    //Accept all directories and all gif, jpg or png files.
    public boolean accept(File f) {
        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.gif) ||
                    extension.equals(Utils.jpeg) ||
                    extension.equals(Utils.jpg) ||
                    extension.equals(Utils.png)) {
                return true;
            }
        }
        return false;
    }

    //The description of this filter
    public String getDescription() {
        return "Just Images";
    }
}

class Utils {

    // TODO reason for extra class? maybe include in main class for overview purposes?

    // TODO rename to fit static naming theme
    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String png = "png";

    /*
     * Get the extension of a file.
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}