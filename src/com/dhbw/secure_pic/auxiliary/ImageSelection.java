package com.dhbw.secure_pic.auxiliary;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

/**
 * Utility class making images able to copy to clip board, like StringSelection does for Strings.
 * @see <a href="http://www.java2s.com/Book/Java/Examples/Copy_and_paste_image_to_and_from_System_Clipboard.htm">Link</a>
 * <a href="https://stackoverflow.com/a/4552081/13777031">Link</a>
 *
 * @author Frederik Wolter
 */
public class ImageSelection implements Transferable {
    // region attributes
    private Image image;
    // endregion

    /**
     * Constructor of ImageSelection
     *
     * @param image image to be copied.
     */
    public ImageSelection(Image image) {
        this.image = image;
    }

    // region methods for interface
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{DataFlavor.imageFlavor};
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.imageFlavor.equals(flavor);
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (!DataFlavor.imageFlavor.equals(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return image;
    }
    // endregion
}