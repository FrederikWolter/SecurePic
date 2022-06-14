package com.dhbw.secure_pic.auxiliary.exceptions;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;

// FIXME comment

/**
 * Class representing a custom exception for signaling that there was a problem in the classes extending Crypter.
 *
 * @author Kirolis Eskondis, supported by Frederik Wolter
 */
public class CrypterException extends Exception {

    public CrypterException(String message) {
        super(message);
    }

    public static CrypterException handleException(Exception e) {
        if (e instanceof NoSuchPaddingException) {
            return new CrypterException("The requested Padding Mechanism is not available. Please try again or contact support with this detailed message: '" + e.getMessage() + "'");
        }
        if (e instanceof InvalidKeyException) {
            return new CrypterException("The given key could not be processed. Please try a new key or contact support with this message: '" + e.getMessage() + "'");
        }
        if (e instanceof IllegalBlockSizeException) {
            return new CrypterException("The length of the given data could not match the length needed for encryption. Please try again or contact support with this detailed message: '" + e.getMessage() + "'");
        }
        if (e instanceof BadPaddingException) {
            return new CrypterException("The data was not padded using the expected mechanism. Please try again or contact support with this detailed message: '" + e.getMessage() + "'");
        }
        if(e instanceof IOException){
            return new CrypterException("An I/O Error occured. Please try again or contact support with this detailed message: '" + e.getMessage() + "'");
        }
        // default
        return new CrypterException("Oops, looks like something went wrong. Please try again or contact the support with this detailed message: '" + e.getMessage() + "'");
    }
}
