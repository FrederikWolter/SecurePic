package com.dhbw.secure_pic.auxiliary.exceptions;

/**
 * Class representing a custom exception for signaling that there was a problem in the classes extending Crypter.
 *
 * @author Kirolis Eskondis
 */

public class CrypterException extends Exception {

    public CrypterException(String message){
        super(message);
    }
}
