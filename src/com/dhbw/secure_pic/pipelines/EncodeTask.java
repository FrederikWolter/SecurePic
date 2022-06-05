package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;

import javax.swing.*;
import java.util.List;


// TODO comment

/**
 * Background task for encrypting and encoding an information into a container image.
 *
 * @author Thu Giang Tran, Frederik Wolter
 */
public class EncodeTask extends SwingWorker<ContainerImage, Void> {

    // region attributes
    /** Coder for encoding the information into the container image. */
    private Coder coder;
    /** Crypter for encrypting the information. */
    private final Crypter crypter;
    /** Information to work with. */
    private Information information;
    // endregion

    public EncodeTask(Coder coder, Crypter crypter, Information information) {
        this.coder = coder;
        this.crypter = crypter;
        this.information = information;
    }

    @Override
    protected ContainerImage doInBackground() throws Exception {
        // initialize progress property.
        setProgress(0);

        // encrypt information
        this.information = this.crypter.encrypt(this.information);

        // encode information
        ContainerImage encodedImage = this.coder.encode(this.information);

        // update progress
        setProgress(100);

        return encodedImage;
        // TODO use setProgress(): https://docs.oracle.com/javase/tutorial/uiswing/examples/components/ProgressBarDemoProject/src/components/ProgressBarDemo.java
        // TODO use design pattern for setProgress from called method https://stackoverflow.com/a/24946032/13777031
    }

    @Override
    protected void done() {
        super.done();
        // TODO implement
        // TODO error handling: https://stackoverflow.com/a/6524300/13777031
    }
}
