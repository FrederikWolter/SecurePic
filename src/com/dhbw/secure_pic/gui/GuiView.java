package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.pipelines.ContainerImageLoadTask;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;

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

    protected static DropTarget getDropTargetListener(LoadImageFinishedHandler handler, JProgressBar progressBar){
        return new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // TODO cleanup cast?

                    for (File file : droppedFiles) { // TODO allow multiple files? no? GENERAL
                        // start load task
                        ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), handler);
                        task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                        task.execute();
                    }
                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
            }
        };
    }


    protected static ActionListener getImageUploadListener(Component parent, LoadImageFinishedHandler handler, JProgressBar progressBar){
        return e -> {
            File file = new FileSelect().selectFile(parent);

            if(file == null) return;    // if no file selected -> simply stop load process

            // start load task
            ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), handler);
            task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
            task.execute();
        };
    }



}
