package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;


/**
 * This interface represents the Crypter used to encrypt/decrypt messages.
 *
 * @author Kirolis Eskondis, Frederik Wolter
 */
public interface Crypter {

    /**
     * General encrypt method for encrypting a given Information.
     *
     * @param information Information to be encrypted
     * @param monitor     Monitor to monitor progress
     *
     * @return Encrypted Information
     *
     * @throws CrypterException Thrown if something went wrong encrypting
     */
    Information encrypt(Information information, ProgressMonitor monitor) throws CrypterException;

    /**
     * General decrypt method for decrypting a given information.
     *
     * @param information Information to be decrypted
     * @param monitor     Monitor to monitor the progress
     *
     * @return Decrypted Information
     *
     * @throws CrypterException Thrown if something went wrong encrypting
     */
    Information decrypt(Information information, ProgressMonitor monitor) throws CrypterException;

}
