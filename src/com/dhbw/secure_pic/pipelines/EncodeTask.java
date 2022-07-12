package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.auxiliary.exceptions.InsufficientCapacityException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.Gui;
import com.dhbw.secure_pic.gui.utility.handler.EncodeFinishedHandler;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Background task for encrypting and encoding an information into a container image.
 * Implemented as a {@link SwingWorker}.
 *
 * @author Thu Giang Tran, Frederik Wolter
 * @see <a href="https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html">Oracle-Doku</a>
 */
public class EncodeTask extends SwingWorker<ContainerImage, Void> {

    // region attributes
    /** Coder for encoding the information into the container image. */
    private final Coder coder;
    /** Crypter for encrypting the information. */
    private final Crypter crypter;
    /** Calling gui class must be a EncodeFinishedHandler to handle when encode finishes. */
    private final EncodeFinishedHandler caller;
    /** Information to work with. */
    private Information information;
    /** get resource bundle managing strings */
    private final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));
    // endregion

    /**
     * Constructor for {@link EncodeTask}
     *
     * @param coder       Coder for encoding the information into the container image
     * @param crypter     Crypter for encrypting the information
     * @param information Information to work with
     * @param caller      Calling gui class must be a EncodeFinishedHandler to handle when encode finishes
     */
    public EncodeTask(Coder coder, Crypter crypter, Information information, EncodeFinishedHandler caller) {
        this.coder = coder;
        this.crypter = crypter;
        this.information = information;
        this.caller = caller;
    }

    /**
     * Called if 'task.execute()' is run.<br>
     * Executed in the background task.
     *
     * @return Finished ContainerImage
     *
     * @throws CrypterException              thrown if encryption goes wrong
     * @throws InsufficientCapacityException thrown if container image capacity is insufficient
     */
    @Override
    protected ContainerImage doInBackground() throws CrypterException, InsufficientCapacityException {
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

    /**
     * Executed when 'doInBackground' is done.<br>
     * Executed in Event Dispatch Thread.
     */
    @Override
    protected void done() {
        try {
            ContainerImage image = get();   // get result of task
            this.caller.finishedEncode(image);  // call finished method
        } catch (InterruptedException e) {
            // this should not happen due to no code interrupting this pipeline
            Logger.getLogger("EncodeTask")
                    .log(Level.WARNING, String.format(bundle.getString("log.interrupted_exception"), e.getMessage()));
            Thread.currentThread().interrupt(); // see SolarLint rule
        } catch (ExecutionException e) {
            String msg = String.format(bundle.getString("popup.msg.error_encoding"), e.getMessage().split(":", 2)[1]);
            JOptionPane.showMessageDialog(null, msg, bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
        }
    }
}
