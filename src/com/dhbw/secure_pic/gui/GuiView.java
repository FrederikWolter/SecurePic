package com.dhbw.secure_pic.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;

// TODO comment

public class GuiView extends Component {

    // region constants
    public static final int IMAGE_HEIGHT_5 = 550;
    public static final int IMAGE_HEIGHT_4 = 200;
    public static final int IMAGE_HEIGHT_3 = 150;
    public static final int IMAGE_HEIGHT_2 = 130;
    public static final int IMAGE_HEIGHT_1 = 110;

    public static final int IMAGE_WIDTH_5 = 550;
    public static final int IMAGE_WIDTH_4 = 400;
    public static final int IMAGE_WIDTH_3 = 300;
    public static final int IMAGE_WIDTH_2 = 250;
    public static final int IMAGE_WIDTH_1 = 200;

    // endregion


    // see https://stackoverflow.com/a/6714381/13777031, https://stackoverflow.com/a/10245583/13777031
    public static BufferedImage getScaledImage(BufferedImage srcImg, int maxWidth, int maxHeight) {
        int originalWidth = srcImg.getWidth();
        int originalHeight = srcImg.getHeight();
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        // first check if we need to scale width
        if (originalWidth > maxWidth) {
            //scale width to fit
            newWidth = maxWidth;
            //scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        }

        // then check if we need to scale even with the new height
        if (newHeight > maxHeight) {
            //scale height to fit instead
            newHeight = maxHeight;
            //scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }

        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, newWidth, newHeight, null);
        g2.dispose();

        return resizedImg;
    }

    protected static PropertyChangeListener getPropertyChangeListener(JProgressBar progressBar){
        return evt -> {
            if ("progress".equals(evt.getPropertyName())) { // update progress event?
                int progress = (Integer) evt.getNewValue(); // get progress value from event
                progressBar.setValue(progress);             // update progressbar
            }
        };
    }

}
