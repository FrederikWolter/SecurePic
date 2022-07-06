package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;

// TODO comment

/**
 * This class implements the Crypter used to encrypt/decrypt messages.<br>
 * Class is abstract, hence its functionality could only be used through its children.
 *
 * @author Kirolis Eskondis supported by Frederik Wolter
 */
public abstract class Crypter {
    // TODO interface

    /**
     * @param information contains the message to encrypt/decrypt
     */
    public abstract Information encrypt(Information information, ProgressMonitor monitor) throws CrypterException;

    public abstract Information decrypt(Information information, ProgressMonitor monitor) throws CrypterException;

}
