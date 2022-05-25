package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.data.Information;

// TODO COMMENT
// TODO implement

/**
 *  This class implements the Crypter class used to encrypt/decrypt messages
 */

public abstract class Crypter {

    /**
     * @param information includes the message to encrypt/decrypt
     * @return
     */
    public abstract Information encrypt(Information information);

    public abstract Information decrypt(Information information);

}
