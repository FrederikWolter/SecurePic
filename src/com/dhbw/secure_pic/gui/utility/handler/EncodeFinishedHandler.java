package com.dhbw.secure_pic.gui.utility.handler;

import com.dhbw.secure_pic.data.ContainerImage;


/**
 * Interface representing a encodeFinishedHandler.
 * Classes implementing this can handle a finishedEncode "event".
 *
 * @author Fredeirk Wolter
 */

public interface EncodeFinishedHandler {

    /**
     * Method called if encode finished.
     *
     * @param image return value of task
     */
    void finishedEncode(ContainerImage image);

}
