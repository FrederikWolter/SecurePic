package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

// TODO COMMENT
// TODO implement

/**
 * This class implements the RSA encryption method used to encrypt/decrypt messages.<br>
 * It extends the Crypter class.
 * @author Kirolis Eskondis
 */

public class RSA extends Crypter {

    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String algorithm;
    private final CrypterException crypterException = new CrypterException();

    /**
     * This constructor is used for generating the KeyPair.
     * The receiver uses this to create private and public key
     */
    public RSA() throws CrypterException {
        KeyPair pair = null;
        try{
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            pair = generator.generateKeyPair();

        } catch (NoSuchAlgorithmException e){
            crypterException.throwException(e.getClass(),e.getMessage());
        }
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
        this.algorithm = "RSA";
    }

    /**
     * This constructor is used for RSA Encryption as it is used by the sender
     * @param publicKey is the only key needed for encryption so privateKey is set to NULL
     */
    public RSA(PublicKey publicKey) {
        this.privateKey = null;
        this.publicKey = publicKey;
        this.algorithm = "RSA";
    }

    /**
     * This constructor is used for RSA Decryption as it is used by the receiver
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


    /**
     * @param information contains the message to encrypt
     */
    @Override
    public Information encrypt(Information information) throws CrypterException {
        try{
            Cipher encryptCipher = Cipher.getInstance(algorithm);
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedBytes = encryptCipher.doFinal(information.getData());
            byte[] outPutBytes = Base64.getEncoder().encode(encryptedBytes);
            information.setEncryptedData(outPutBytes);

        }  catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            crypterException.throwException(e.getClass(),e.getMessage());
        }
        return information;

    }

    /**
     * @param information contains the encrypted message to decrypt
     */
    @Override
    public Information decrypt(Information information) throws CrypterException {
        try {
            Cipher decryptionCipher = Cipher.getInstance(algorithm);
            decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = decryptionCipher.doFinal(Base64.getDecoder().decode(information.toText()));

            information.setEncryptedData(decryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            crypterException.throwException(e.getClass(),e.getMessage());
        }
        return information;
    }



    // region getter
    // Getters are used to output the keys to the user
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
    // endregion
}
