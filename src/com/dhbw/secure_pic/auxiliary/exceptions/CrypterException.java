package com.dhbw.secure_pic.auxiliary.exceptions;

import com.dhbw.secure_pic.gui.Gui;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Class representing a custom exception for signaling that there was a problem in the <b>classes extending Crypter</b>.
 *
 * @author Kirolis Eskondis supported by Frederik Wolter
 */
public class CrypterException extends Exception {

    /** get resource bundle managing strings */
    private static final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));

    /**
     * Inherited Constructor.
     *
     * @param message message of exception
     */
    public CrypterException(String message) {
        super(message);
    }

    /**
     * Converter from several possible exception which can occur in the crypter, to {@link CrypterException}.
     *
     * @param e given Exception
     *
     * @return converted Exception
     */
    public static CrypterException handleException(Exception e) {
        if (e instanceof NoSuchPaddingException) {
            return new CrypterException(MessageFormat.format(bundle.getString("except.crypter.invalid_padding_algorithm"), e.getMessage()));
        }
        if (e instanceof InvalidKeyException) {
            return new CrypterException(MessageFormat.format(bundle.getString("except.crypter.invalid_key"), e.getMessage()));
        }
        if (e instanceof IllegalBlockSizeException) {
            return new CrypterException(MessageFormat.format(bundle.getString("except.crypter.invalid_data_length"), e.getMessage()));
        }
        if (e instanceof BadPaddingException) {
            return new CrypterException(MessageFormat.format(bundle.getString("except.crypter.invalid_padding"), e.getMessage()));
        }
        if (e instanceof IOException) {
            return new CrypterException(MessageFormat.format(bundle.getString("except.crypter.io_error"), e.getMessage()));
        }
        // default
        return new CrypterException(MessageFormat.format(bundle.getString("except.crypter.general_error"), e.getMessage()));
    }
}
