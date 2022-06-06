package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.data.ContainerImage;
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
    /** Optional ContainerImage to encode the public key into */
    private final ContainerImage image;
    /** Calling gui class must be a GenerateCrypterFinishedHandler to handle when generate finishes */
    private final GenerateCrypterFinishedHandler caller;
    // endregion

    public GenerateCrypterTask(Crypter crypter, GenerateCrypterFinishedHandler caller) {
        this.crypter = crypter;
        this.caller = caller;
        this.image = null;
    }

    public GenerateCrypterTask(ContainerImage image, GenerateCrypterFinishedHandler caller) {
        this.crypter = null;
        this.caller = caller;
        this.image = image;
    }

    public GenerateCrypterTask(Crypter crypter, ContainerImage image, GenerateCrypterFinishedHandler caller) {
        this.crypter = crypter;
        this.caller = caller;
        this.image = image;
    }

    @Override
    protected Crypter doInBackground() {

        // TODO work in progress

        this.crypter = this.crypter.generateKey();

        if (image != null) {

        }

        return this.crypter;
        // TODO use setProgress(): https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ProgressBarDemoProject/src/components/ProgressBarDemo.java
        // TODO use design pattern for setProgress from called method https://stackoverflow.com/a/24946032/13777031
    }

    @Override
    protected void done() {
        try {
            Crypter crypterWithKeys = get();
            this.caller.finishedGenerateCrypter(crypterWithKeys, this.image);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        // TODO error handling: https://stackoverflow.com/a/6524300/13777031
    }
}
