package com.dhbw.secure_pic.crypter;

// TODO COMMENT
// TODO implement

import com.dhbw.secure_pic.data.Information;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

/**
 * This class implements the RSA encryption method used to encrypt/decrypt messages.<br>
 * It extends the Crypter class.
 * Author: Kirolis Eskondis
 */

public class RSA extends Crypter {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String algorithm;

    /**
     * This constructor is used for RSA Encryption
     */
    public RSA() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        KeyPair pair = generator.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
        this.algorithm = "RSA";

    }

    /**
     * This constructor is used for RSA Decryption
     * @param privateKey is the only key needed for decryption so publicKey is set to NULL
     */
    public RSA(PrivateKey privateKey) {
        this.privateKey = privateKey;
        this.publicKey = null;
        this.algorithm = "RSA";
    }

    //TODO not sure what was planned here
    private AES getNew() {
        return null;
    }


    @Override
    public Information encrypt(Information information) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher encryptCipher = Cipher.getInstance(algorithm);
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedBytes = encryptCipher.doFinal(information.getData());
        byte[] outPutBytes = Base64.getEncoder().encode(encryptedBytes);
        information.setEncryptedData(outPutBytes);

        return information;
    }

    @Override
    public Information decrypt(Information information) throws IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        Cipher decryptionCipher = Cipher.getInstance(algorithm);
        decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = decryptionCipher.doFinal(Base64.getDecoder().decode(information.toText()));

        information.setEncryptedData(decryptedBytes);
        return information;
    }


    /**
     * Getters are used to output the keys to the user
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
