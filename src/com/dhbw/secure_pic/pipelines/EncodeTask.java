package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.auxiliary.exceptions.InsufficientCapacityException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.handler.EncodeFinishedHandler;

import javax.swing.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;


// TODO comment

/**
 * Background task for encrypting and encoding an information into a container image.
 *
 * @author Thu Giang Tran, Frederik Wolter
 */
public class EncodeTask extends SwingWorker<ContainerImage, Void> {

    // region attributes
    /** Coder for encoding the information into the container image. */
    private final Coder coder;
    /** Crypter for encrypting the information. */
    private final Crypter crypter;
    /** Calling gui class must be a EncodeFinishedHandler to handle when encode finishes */
    private final EncodeFinishedHandler caller;
    /** Information to work with. */
    private Information information;
    // endregion

    public EncodeTask(Coder coder, Crypter crypter, Information information, EncodeFinishedHandler caller) {
        this.coder = coder;
        this.crypter = crypter;
        this.information = information;
        this.caller = caller;
    }

    @Override
    protected ContainerImage doInBackground()
            throws CrypterException, InsufficientCapacityException, InterruptedException {
        // initialize progress property.
        setProgress(0);

        // encrypt information
        this.information = this.crypter.encrypt(this.information,
                                                progress -> setProgress((int) (progress * 0.5))     // progress 0 - 50
        );

        // encode information
        ContainerImage encodedImage = this.coder.encode(this.information,
                                                        progress -> setProgress((int) (progress * 0.5 + 50))    // progress 50 - 100
        );

        // update progress
        setProgress(100);

        return encodedImage;
    }

    @Override
    protected void done() {
        try {
            ContainerImage image = get();
            this.caller.finishedEncode(image);
        } catch (InterruptedException e) {
            // this should not happen due to no code interrupting the pipeline
            Logger.getLogger("EncodeTask")
                    .log(Level.WARNING, String.format("InterruptedException: '%s'", e.getMessage()));
            Thread.currentThread().interrupt(); // see SolarLint
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
            String msg = String.format("Fehler beim Codieren:%n'%s'", e.getMessage().split(":", 2)[1]);
            JOptionPane.showMessageDialog(null, msg, "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
