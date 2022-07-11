package com.dhbw.secure_pic.auxiliary.exceptions;

/**
 * Class representing a custom exception for signaling that the given length does not fit to the expected length.
 *
 * @author Frederik Wolter
 * @see <a href="https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java">Idea</a>
 */
public class IllegalLengthException extends Exception {

    /**
     * Custom exception for signaling that the given length does not fit to the expected length.
     *
     * @param message message
     */
    public IllegalLengthException(String message) {
        super(message);
    }

}
