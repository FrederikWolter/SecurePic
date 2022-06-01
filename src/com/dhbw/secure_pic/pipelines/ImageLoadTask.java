package com.dhbw.secure_pic.pipelines;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.List;

// TODO comment

// TODO see in general https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html

/**
 * @author Thu Giang Tran supported by Frederik Wolter
 */
public class ImageLoadTask extends SwingWorker<BufferedImage, Void> {

    // region attributes
    private final String path;
    // endregion


    public ImageLoadTask(String path) {
        this.path = path;
    }

    @Override
    protected BufferedImage doInBackground() throws Exception {
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

    /** currently not in use */
    @Override
    protected void process(List<Void> chunks) {
        super.process(chunks);
    }
}
