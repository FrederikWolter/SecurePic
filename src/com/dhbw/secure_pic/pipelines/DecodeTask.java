package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.DecodeFinishedHandler;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

// TODO comment

/**
 * @author Thu Giang Tran, Frederik Wolter
 */
public class DecodeTask extends SwingWorker<Information, Void> {

    // region attributes
    /** Coder for decoding the information from the container image. */
    private final Coder coder;
    /** Crypter for decrypting the information. */
    private final Crypter crypter;
    /** Calling gui class must be a DecodeFinishedHandler to handle when decode finishes */
    private final DecodeFinishedHandler caller;
    /** Information to work with. */
    private Information information;
    // endregion

    public DecodeTask(Coder coder, Crypter crypter, DecodeFinishedHandler caller) {
        this.coder = coder;
        this.crypter = crypter;
        this.caller = caller;
    }

    @Override
    protected Information doInBackground() throws IllegalTypeException, IllegalLengthException, CrypterException {
        // initialize progress property.
        setProgress(0);

        // decode information from container image
        this.information = this.coder.decode();

        // decrypt information
        this.information = this.crypter.decrypt(this.information);

        // update progress
        setProgress(100);

        return this.information;
        // TODO use setProgress(): https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ProgressBarDemoProject/src/components/ProgressBarDemo.java
        // TODO use design pattern for setProgress from called method https://stackoverflow.com/a/24946032/13777031
    }

    @Override
    protected void done() {
        try {
            Information info = get();
            this.caller.finishedDecode(info);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        // TODO error handling: https://stackoverflow.com/a/6524300/13777031
    }
}
