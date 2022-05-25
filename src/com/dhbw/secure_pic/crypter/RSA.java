package com.dhbw.secure_pic.crypter;


import com.dhbw.secure_pic.data.Information;

/**
 *  This class implements the RSA encryption method used to encrypt/decrypt messages
 *  It extends the Crypter class
 */

public class RSA extends Crypter {

    private final String privK;
    private final String pubK;

    public RSA(String privK, String pubK){
        this.privK = privK;
        this.pubK = pubK;
    }

    private AES getNew(){
        return null;
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
