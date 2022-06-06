package com.dhbw.secure_pic.auxiliary.exceptions;

/**
 * Class representing a custom exception for signaling that the given length does not fit to the expected length.
 *
 * @author Frederik Wolter
 */
public class IllegalLengthException extends Exception {
    // see https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java

    /**
     * Custom exception for signaling that the given length does not fit to the expected length.
     *
     * @param message message
     */
    public IllegalLengthException(String message) {
        super(message);
    }
}
