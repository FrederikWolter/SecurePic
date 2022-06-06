package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;

// TODO COMMENT
// TODO implement

// TODO reduce number of specific exceptions? more general?

/**
 * This class implements the Crypter used to encrypt/decrypt messages.<br>
 * Class is abstract, hence its functionality could only be used through its children.
 *
 * @author Kirolis Eskondis supported by Frederik Wolter
 */
public abstract class Crypter {

    /**
     * @param information contains the message to encrypt/decrypt
     */
    public abstract Information encrypt(Information information) throws CrypterException;

    public abstract Information decrypt(Information information) throws CrypterException;

}
