package com.dhbw.secure_pic.auxiliary.exceptions;

// FIXME comment

public class InsufficientCapacityException extends Exception {
    // see https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java

    public InsufficientCapacityException(String message) {
        super(message);
    }
}
