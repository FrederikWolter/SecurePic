package com.dhbw.secure_pic.crypter;

// TODO COMMENT
// TODO implement

import com.dhbw.secure_pic.data.Information;

/**
 * This class implements the RSA encryption method used to encrypt/decrypt messages.<br>
 * It extends the Crypter class.
 * Author: Kirolis Eskondis
 */

public class RSA extends Crypter {

    private final String privateKey;
    private final String publicKey;

    public RSA(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    private AES getNew() {
        return null;
    }

    @Override
    public Information encrypt(Information information) {


        return information;
    }

    @Override
    public Information decrypt(Information information) {

        return information;
    }
}
