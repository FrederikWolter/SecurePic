package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.CrypterKey;
import com.dhbw.secure_pic.data.Information;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

// TODO COMMENT

// TODO reduce number of thrown exceptions by wrapping into own more general
// TODO do more work self instead of handing it over to library?

/**
 * This class implements the AES encryption method used to encrypt/decrypt messages.<br>
 * It extends the Crypter class.
 *
 * @author Kirolis Eskondis supported by Frederik Wolter
 */
public class AES extends Crypter {

    // region attributes
    private final SecretKey key;
    private final String algorithm;
    // endregion

    /**
     * @param password is the password entered by the user
     */
    public AES(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.key = getKeyFromPassword(password);
        this.algorithm = "AES";
    }

    /**
     * @param information contains the message to encrypt
     */
    @Override
    public Information encrypt(Information information)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher encryptionCipher = Cipher.getInstance(algorithm);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedBytes = encryptionCipher.doFinal(information.getData());
        byte[] outPutBytes = Base64.getEncoder().encode(encryptedBytes);

        information.setEncryptedData(outPutBytes);
        return information;
    }


    /**
     * @param information contains the encrypted message to decrypt
     */
    @Override
    public Information decrypt(Information information) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher decryptionCipher = Cipher.getInstance(algorithm);
        decryptionCipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedBytes = decryptionCipher.doFinal(Base64.getDecoder().decode(information.toText()));

        information.setEncryptedData(decryptedBytes);
        return information;
    }


    /**
     *
     * @param password is the password given by the user turned into a 32-byte array
     *
     * @return secret is the generated key
     */
    private SecretKey getKeyFromPassword(String password){

        byte[] keyBytes = new byte[32];
        for(int i= 0;i<password.length() && i<32;i++){
            keyBytes[i] = password.getBytes()[i];
        }
        return new SecretKeySpec(keyBytes, "AES");
    }

}
