package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.data.Information;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

// TODO COMMENT
// TODO implement

/**
 * This class implements the AES encryption method used to encrypt/decrypt messages.<br>
 * It extends the Crypter class.
 * Author: Kirolis Eskondis
 */
public class AES extends Crypter {

    private final String password;
    private final SecretKey key;
    private final IvParameterSpec iv;
    private final String algorithm;

    /**
     * @param password is the password entered by the user
     */
    public AES(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.password = password;
        this.key = getKeyFromPassword(password);
        this.iv = generateIv();
        this.algorithm = "AES/CBC/PKCS5Padding";
    }

    /**
     * @param information contains the message to encrypt
     * @return new Information containing the encrypted message
     */
    @Override
    public Information encrypt(Information information)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);

        byte[] encryptedBytes = cipher.doFinal(information.getData());
        byte[] outPutBytes = Base64.getEncoder().encode(encryptedBytes);

        return new Information(outPutBytes, Information.Type.TEXT);
    }


    /**
     * @param information contains the encrypted message to decrypt
     * @return new Information containing the decrypted message
     */
    @Override
    public Information decrypt(Information information) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder()
                .decode(information.toText()));

        return new Information(decryptedBytes, Information.Type.TEXT);
    }


    /**
     *
     * @param password is the password given by the user
     * @return secret is the generated key
     */
    private SecretKey getKeyFromPassword(String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        String salt = generateSaltValue();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
        return secret;
    }

    /**
     * @return randomly generated 7-character String
     */
    private String generateSaltValue() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }

    /**
     * @return randomly generated Initialization Vector
     */
    private IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }


}
