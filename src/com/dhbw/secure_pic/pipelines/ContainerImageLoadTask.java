package com.dhbw.secure_pic.pipelines;

import com.dhbw.secure_pic.data.ContainerImage;

import javax.swing.*;
import java.util.List;

// TODO comment

// TODO see in general https://docs.oracle.com/javase/tutorial/uiswing/concurrency/index.html

/**
 * Background task for loading selected image from the drive and forming it into a container image.
 *
 * @author Thu Giang Tran, Frederik Wolter
 */
public class ContainerImageLoadTask extends SwingWorker<ContainerImage, Void> {

    // region attributes
    /** Path to image which should be loaded. */
    private final String path;
    // endregion


    public ContainerImageLoadTask(String path) {
        this.path = path;
    }

    @Override
    protected ContainerImage doInBackground() throws Exception {
        // initialize progress property.
        setProgress(0);

        // create new ContainerImage instance from path
        ContainerImage containerImage = new ContainerImage(this.path);

        // update progress
        setProgress(100);
        // TODO use progress inside method?

        return containerImage;
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
