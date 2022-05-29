package com.dhbw.secure_pic.auxiliary;

// TODO comment

public class IllegalLengthException extends Exception {
    // see https://stackoverflow.com/questions/8423700/how-to-create-a-custom-exception-type-in-java

    public IllegalLengthException(String message) {
        super(message);
    }
}
