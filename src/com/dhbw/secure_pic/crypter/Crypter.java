package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;

// TODO comment

/**
 * This interface represents the Crypter used to encrypt/decrypt messages.
 *
 * @author Kirolis Eskondis, Frederik Wolter
 */
public interface Crypter {

    Information encrypt(Information information, ProgressMonitor monitor) throws CrypterException;

    Information decrypt(Information information, ProgressMonitor monitor) throws CrypterException;

}
