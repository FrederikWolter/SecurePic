package com.dhbw.secure_pic.gui.utility.handler;

import com.dhbw.secure_pic.data.Information;


/**
 * Interface representing a decodeFinishedHandler.
 * Classes implementing this can handle a finishedDecode "event".
 *
 * @author Fredeirk Wolter
 */
public interface DecodeFinishedHandler {

    /**
     * Method called if decode task finished.
     *
     * @param info return value of task
     */
    void finishedDecode(Information info);

}
