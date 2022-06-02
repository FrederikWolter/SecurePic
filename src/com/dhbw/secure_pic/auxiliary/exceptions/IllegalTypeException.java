package com.dhbw.secure_pic.auxiliary.exceptions;

/**
 * Class representing a custom exception for signaling that the given type index does not correspond to a valid type.
 *
 * @author Frederik Wolter
 */
public class IllegalTypeException extends Exception {
    // see https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java

    /**
     * Custom exception for signaling that the given type index does not correspond to a valid type.
     *
     * @param message message
     */
    public IllegalTypeException(String message) {
        super(message);
    }
}
