package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.handler.DecodeFinishedHandler;

import javax.swing.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Background task for decrypting and decoding an information into a container image.
 * Implemented as a {@link SwingWorker}.
 *
 * @author Thu Giang Tran, Frederik Wolter
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html">Oracle</a>
 */
public class DecodeTask extends SwingWorker<Information, Void> {

    // region attributes
    /** Coder for decoding the information from the container image. */
    private final Coder coder;
    /** Crypter for decrypting the information. */
    private final Crypter crypter;
    /** Calling gui class must be a DecodeFinishedHandler to handle when decode finishes. */
    private final DecodeFinishedHandler caller;
    // endregion

    /**
     * Constructor for {@link DecodeTask}.
     *
     * @param coder   Coder for decoding the information from the container image
     * @param crypter Crypter for decrypting the information
     * @param caller  Calling gui class must be a DecodeFinishedHandler to handle when decode finishes
     */
    public DecodeTask(Coder coder, Crypter crypter, DecodeFinishedHandler caller) {
        this.coder = coder;
        this.crypter = crypter;
        this.caller = caller;
    }

    /**
     * Called if 'task.execute()' is run.<br>
     * Executed in the background task.
     *
     * @return Gained Information
     *
     * @throws IllegalTypeException   thrown if information type does not match data
     * @throws IllegalLengthException thrown if received data does not match given meta-data "length"
     * @throws CrypterException       thrown if decryption goes wrong
     */
    @Override
    protected Information doInBackground() throws IllegalTypeException, IllegalLengthException, CrypterException {
        // initialize progress property.
        setProgress(0);

        // decode information from container image
        Information information = this.coder.decode(
                progress -> setProgress((int) (progress * 0.5))   // progress 0 - 50
        );

        // decrypt information
        information = this.crypter.decrypt(information,
                                           progress -> setProgress((int) (progress * 0.5 + 50))    // progress 50 - 100
        );

        // update progress
        setProgress(100);

        return information;
    }

    /**
     * Executed when 'doInBackground' is done.<br>
     * Executed in Event Dispatch Thread.
     */
    @Override
    protected void done() {
        try {
            Information info = get();
            this.caller.finishedDecode(info);
        } catch (InterruptedException e) {
            // this should not happen due to no code interrupting the pipeline
            Logger.getLogger("DecodeTask")
                    .log(Level.WARNING, String.format("InterruptedException: '%s'", e.getMessage()));
            Thread.currentThread().interrupt(); // see SolarLint
        } catch (ExecutionException e) {
            String msg = String.format("Fehler beim Decodieren:%nStelle sicher, dass das Bild mit den gleichen Einstellungen codiert wurde.%n'%s'", e.getMessage().split(":", 2)[1]);
            JOptionPane.showMessageDialog(null, msg, "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }
}
