package com.dhbw.secure_pic.auxiliary;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

// TODO comment

// see http://www.java2s.com/Book/Java/Examples/Copy_and_paste_image_to_and_from_System_Clipboard.htm, https://stackoverflow.com/a/4552081/13777031
public class ImageSelection implements Transferable {
    private Image image;

    public ImageSelection(Image image) {
        this.image = image;
    }

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
}