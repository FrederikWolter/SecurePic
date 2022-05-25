package com.dhbw.secure_pic.crypter;


/**
 *  This class implements the RSA encryption method used to encrypt/decrypt messages
 *  It extends the Crypter class
 */

public class RSA {

    private final String privK;
    private final String pubK;

    public RSA(String privK, String pubK){
        this.privK = privK;
        this.pubK = pubK;
    }

    private AES getNew(){
        return null;
    }
}
