package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.gui.utility.GenerateCrypterFinishedHandler;

import javax.swing.*;
import java.util.concurrent.ExecutionException;


// TODO comment

/**
 * @author Thu Giang Tran, Frederik Wolter
 */
public class GenerateCrypterTask extends SwingWorker<Crypter, Void> {

    // region attributes
    /** Crypter to work with */
    private final Crypter crypter;
    /** Calling gui class must be a GenerateCrypterFinishedHandler to handle when generate finishes */
    private final GenerateCrypterFinishedHandler caller;
    // endregion

    // empty crypter > generate keys
    public GenerateCrypterTask(Crypter crypter, GenerateCrypterFinishedHandler caller) {
        this.crypter = crypter;
        this.caller = caller;
    }

    @Override
    protected Crypter doInBackground() throws CrypterException {
        // initialize progress property.
        setProgress(0);

        // generate Keys of Crypter
        //this.crypter.generateKey(progress -> setProgress(progress));

        // update progress
        setProgress(100);

        return this.crypter;
    }

    @Override
    protected void done() {
        try {
            Crypter crypterWithKeys = get();
            this.caller.finishedGenerateCrypter(crypterWithKeys);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            e.getCause().printStackTrace();
            String msg = String.format("Fehler beim Erzeugen der Verschlüsselung:%n'%s'", e.getMessage().split(":", 2)[1]);
            JOptionPane.showMessageDialog(null, msg, "Fehler", JOptionPane.ERROR_MESSAGE);
        }
        // TODO error handling: https://stackoverflow.com/a/6524300/13777031
        // see https://stackoverflow.com/questions/18462826/split-string-only-on-first-instance-java
    }
}
