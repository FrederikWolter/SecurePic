package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

// TODO comment
// see e.g. https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html

/**
 * Background task for loading selected image from the drive and forming it into a container image.
 *
 * @author Thu Giang Tran, Frederik Wolter
 */
public class ContainerImageLoadTask extends SwingWorker<ContainerImage, Void> {

    // region attributes
    /** Path to image which should be loaded. */
    private final String path;
    /** Calling gui class must be a LoadFinishedHandler to handle when containerImageLoad finishes */
    private final LoadImageFinishedHandler caller;
    // endregion


    public ContainerImageLoadTask(String path, LoadImageFinishedHandler caller) {
        this.path = path;
        this.caller = caller;
    }

    @Override
    protected ContainerImage doInBackground() throws IllegalTypeException {
        // initialize progress property.
        setProgress(0);

        // create new ContainerImage instance from path
        ContainerImage containerImage = new ContainerImage(this.path);
        // TODO use progress inside method? use design pattern for setProgress from called method https://stackoverflow.com/a/24946032/13777031

        // update progress
        setProgress(100);

        return containerImage;
    }

    @Override
    protected void done() {
        try {
            ContainerImage image = get();
            this.caller.finishedImageLoad(image);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
            String msg = String.format("Fehler beim Laden des Bildes:%n'%s'", e.getMessage().split(":", 2)[1]);
            JOptionPane.showMessageDialog(null, msg, "Fehler", JOptionPane.ERROR_MESSAGE);
        }
        // TODO error handling: https://stackoverflow.com/a/6524300/13777031
    }
}
