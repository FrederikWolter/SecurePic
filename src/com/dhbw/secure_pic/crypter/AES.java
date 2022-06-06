package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.data.Information;

// TODO COMMENT
// TODO implement

/**
 * This class implements the AES encryption method used to encrypt/decrypt messages.<br>
 * It extends the Crypter class.
 */
public class AES extends Crypter {

    private final String password;

    /**
     * @param password is the password entered by the user
     */
    public AES(String password) {
        this.password = password;
    }

    @Override
    public Information encrypt(Information information) {
        return null;
    }

    @Override
    public Information decrypt(Information information) {
        return null;
    }
}
