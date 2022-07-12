package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;

import javax.swing.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Background task for loading selected image from the drive and forming it into a container image.
 * Implemented as a {@link SwingWorker}.
 *
 * @author Thu Giang Tran, Frederik Wolter
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html">Oracle</a>
 */
public class ContainerImageLoadTask extends SwingWorker<ContainerImage, Void> {

    // region attributes
    /** Path to image which should be loaded. */
    private final String path;
    /** Calling gui class must be a LoadFinishedHandler to handle when containerImageLoad finishes. */
    private final LoadImageFinishedHandler caller;
    // endregion

    /**
     * Constructor for {@link ContainerImage}.
     *
     * @param path   Path to image which should be loaded
     * @param caller Calling gui class must be a LoadFinishedHandler to handle when containerImageLoad finishes
     */
    public ContainerImageLoadTask(String path, LoadImageFinishedHandler caller) {
        this.path = path;
        this.caller = caller;
    }

    /**
     * Called if 'task.execute()' is run.<br>
     * Executed in the background task.
     *
     * @return Loaded ContainerImage
     *
     * @throws IllegalTypeException thrown if load path does not match a type
     */
    @Override
    protected ContainerImage doInBackground() throws IllegalTypeException {
        // initialize progress property.
        setProgress(0);

        // create new ContainerImage instance from path
        ContainerImage containerImage = new ContainerImage(this.path);

        // update progress
        setProgress(100);

        return containerImage;
    }

    /**
     * Executed when 'doInBackground' is done.<br>
     * Executed in Event Dispatch Thread.
     */
    @Override
    protected void done() {
        try {
            ContainerImage image = get();
            this.caller.finishedImageLoad(image);
        } catch (InterruptedException e) {
            // this should not happen due to no code interrupting the pipeline
            Logger.getLogger("ContainerImageLoadTask")
                    .log(Level.WARNING, String.format("InterruptedException: '%s'", e.getMessage()));
            Thread.currentThread().interrupt(); // see SolarLint
        } catch (ExecutionException e) {
            String msg = String.format("Fehler beim Laden des Bildes:%n'%s'", e.getMessage().split(":", 2)[1]);
            JOptionPane.showMessageDialog(null, msg, "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
