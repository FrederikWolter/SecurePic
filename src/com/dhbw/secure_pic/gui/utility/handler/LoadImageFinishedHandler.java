package com.dhbw.secure_pic.gui.utility.handler;

import com.dhbw.secure_pic.data.ContainerImage;


/**
 * Interface representing a loadImageFinishedHandler.
 * Classes implementing this can handle a finishedImageLoad "event".
 *
 * @author Fredeirk Wolter
 */

public interface LoadImageFinishedHandler {

    /**
     * Method called if image load finished.
     *
     * @param image return value of task
     */
    void finishedImageLoad(ContainerImage image);

}
