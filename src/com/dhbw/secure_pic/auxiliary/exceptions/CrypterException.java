package com.dhbw.secure_pic.auxiliary.exceptions;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;

/**
 * Class representing a custom exception for signaling that there was a problem in the classes extending Crypter.
 *
 * @author Kirolis Eskondis
 */
public class CrypterException extends Exception {

    public CrypterException(){
        super();
    }

    public CrypterException(String message){
        super(message);
    }

    public void throwException(Class<? extends GeneralSecurityException> exceptionClass, String message) throws CrypterException {
        //Switch case doesnt work with class idk why
        if (NoSuchPaddingException.class.equals(exceptionClass)) {
            throw new CrypterException("The requested Padding Mechanism is not available. Please try again or contact support with this detailed message: ".concat("'").concat(message).concat("'"));
        }
        else if(InvalidKeyException.class.equals(exceptionClass)) {
            throw new CrypterException("The given key could not be processed. Please try a new key or contact support with this message: ".concat("'").concat(message).concat("'"));
        }
        else if(IllegalBlockSizeException.class.equals(exceptionClass)) {
            throw new CrypterException("The length of the given data could not match the length needed for encryption. Please try again or contact support with this detailed message: ".concat("'").concat(message).concat("'"));
        }
        else if(BadPaddingException.class.equals(exceptionClass)) {
            throw new CrypterException("The data was not padded using the expected mechanism. Please try again or contact support with this detailed message: ".concat("'").concat(message).concat("'"));
        } else{
            throw new CrypterException("Oops, looks like something went wrong. Please try again or contact the support with this detailed message:".concat("'").concat(message).concat("'"));
        }
    }
}
