package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.crypter.Crypter;

import javax.swing.*;
import java.util.List;


// TODO comment

/**
 * @author Thu Giang Tran supported by Frederik Wolter
 */
public class GenerateCrypterTask extends SwingWorker<Crypter, Void> {

    @Override
    protected Crypter doInBackground() throws Exception {
        return null;
        // TODO implement
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
