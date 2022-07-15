package com.dhbw.secure_pic.auxiliary.exceptions;


/**
 * Class representing a custom exception for signaling that the given {@link com.dhbw.secure_pic.data.Information} will
 * not fit into the {@link com.dhbw.secure_pic.data.ContainerImage}.
 *
 * @author Frederik Wolter+
 * @see <a href="https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java">Idea</a>
 */
public class InsufficientCapacityException extends Exception {

    /**
     * Custom exception for signaling that the {@link com.dhbw.secure_pic.data.Information} will not fit into the
     * {@link com.dhbw.secure_pic.data.ContainerImage}.
     *
     * @param message message
     */
    public InsufficientCapacityException(String message) {
        super(message);
    }

}
