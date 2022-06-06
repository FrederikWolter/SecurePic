package com.dhbw.secure_pic.auxiliary;

import javax.crypto.spec.SecretKeySpec;

// TODO comment

public class CrypterKey extends SecretKeySpec {

    public CrypterKey(byte[] key, String algorithm) {
        super(key, algorithm);
    }

    private void createNewKey(){
        // TODO
    }

}
