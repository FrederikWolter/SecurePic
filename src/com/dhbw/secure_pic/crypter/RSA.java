package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Arrays;
import java.util.Base64;


/**
 * This class implements the RSA encryption method used to encrypt/decrypt messages.<br>
 * It extends the Crypter class.
 *
 * @author Kirolis Eskondis
 */
public class RSA implements Crypter {

    // region attributes
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String algorithm;
    // endregion

    /** Enum representing the available key types. */
    public enum keyType {
        PUBLIC,
        PRIVATE
    }

    /**
     * This constructor is used for generating the KeyPair.
     * The receiver uses this to create private and public key
     */
    public RSA() throws CrypterException {
        try {
            this.algorithm = "RSA";
            KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();

            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
        } catch (NoSuchAlgorithmException e) {
            throw CrypterException.handleException(e);  // wrap exceptions thrown by crypter to CrypterException
        }
    }

    /**
     * This constructor is used for RSA Encryption/Decryption depending on its usage as a sender or receiver
     *
     * @param password is the password given by the user. This could be a public or private key the user entered as a String
     * @param type     the type of the given password. Either private or public.
     */
    public RSA(String password, keyType type) throws CrypterException {
        this.algorithm = "RSA";
        switch (type) {
            case PUBLIC -> {
                this.privateKey = null;
                this.publicKey = (PublicKey) getKeyFromString(password, type);
            }
            case PRIVATE -> {
                this.publicKey = null;
                this.privateKey = (PrivateKey) getKeyFromString(password, type);
            }
            default -> {
                this.privateKey = null;
                this.publicKey = null;
            }
        }

    }

    /**
     * This function encrypts the data given using the RSA algorithm <br>
     * The RSA algorithm can only encrypt data that has a maximum byte length of the RSA key length in bits divided by eight minus eleven padding bytes, i.e. number of maximum bytes = key length in bits / 8 - 11. <br>
     * Because of this length limitation, this function decrypts the data by dividing it in blocks with a maximum length of 200 bytes. Those will be decrypted to Byte-Arrays with a length of 344 which will be put together
     *
     * @param information contains the message to encrypt
     *
     * @return overwritten {@link Information}
     *
     * @throws CrypterException
     */
    @Override
    public Information encrypt(Information information, ProgressMonitor monitor) throws CrypterException {
        try {
            Cipher encryptCipher = Cipher.getInstance(algorithm);
            encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            monitor.updateProgress(50);

            byte[] informationToEncrypt = information.getData();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i = 0; i < informationToEncrypt.length; i += 200) {
                byte[] subArray;
                if (informationToEncrypt.length - i > i + 200) {
                    subArray = Arrays.copyOfRange(informationToEncrypt, i, Math.min(i + 200, informationToEncrypt.length - i));
                } else {
                    subArray = Arrays.copyOfRange(informationToEncrypt, i, Math.min(i + 200, informationToEncrypt.length));
                }
                byte[] encryptedBlock = encryptCipher.doFinal(subArray);
                byte[] outPutBytes = Base64.getEncoder().encode(encryptedBlock);

                byteArrayOutputStream.write(outPutBytes);
            }

            information.setEncryptedData(byteArrayOutputStream.toByteArray());

            monitor.updateProgress(100);

            return information;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw CrypterException.handleException(e);    // wrap exceptions thrown by crypter to CrypterException
        }
    }

    /**
     * This function encrypts the data given using the RSA algorithm <br>
     * The encrypt method encrypts data blocks of 200 bytes into decrypted data blocks of 344 bytes <br>
     * Because of this length limitation, this function encrypts the data by dividing it in blocks of 344 bytes first. Those will be encrypted to Byte-Arrays with a maximum length of 200.
     *
     * @param information contains the message to decrypt <br>
     *
     * @return overwritten {@link Information}
     *
     * @throws CrypterException
     */
    @Override
    public Information decrypt(Information information, ProgressMonitor monitor) throws CrypterException {
        try {
            Cipher decryptionCipher = Cipher.getInstance(algorithm);
            decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey);

            monitor.updateProgress(50);

            byte[] informationToDecrypt = information.getData();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            for (int i = 0; i < informationToDecrypt.length; i += 344) {

                byte[] subArray;

                if (informationToDecrypt.length - i > i + 344) {
                    subArray = Arrays.copyOfRange(informationToDecrypt, i, Math.min(i + 344, informationToDecrypt.length - i));
                } else {
                    subArray = Arrays.copyOfRange(informationToDecrypt, i, Math.min(i + 344, informationToDecrypt.length));
                }

                byte[] decryptedBlock = decryptionCipher.doFinal(Base64.getDecoder().decode(subArray));
                byteArrayOutputStream.write(decryptedBlock);
            }

            information.setEncryptedData(byteArrayOutputStream.toByteArray());

            monitor.updateProgress(100);

            return information;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                 IOException | BadPaddingException e) {
            throw CrypterException.handleException(e);  // wrap exceptions thrown by crypter to CrypterException
        }
    }

    /**
     * Converts a public/private Key entered as a String into a valid Type of Key which can be used by {@link RSA} for encryption/decryption
     *
     * @param password is the password given by the user. This could be a public or private key the user entered as a String
     * @param type     the type of the given password. Either private or public.
     *
     * @return A valid Key
     *
     * @throws CrypterException
     */
    private Key getKeyFromString(String password, keyType type) throws CrypterException {

        Key key;
        int i = password.lastIndexOf("/");

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            BigInteger m = new BigInteger(password.substring(0, i));
            BigInteger e = new BigInteger(password.substring(i + 1));
            if (type.equals(keyType.PRIVATE)) {
                key = keyFactory.generatePrivate(new RSAPrivateKeySpec(m, e));
            } else {
                key = keyFactory.generatePublic(new RSAPublicKeySpec(m, e));
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw CrypterException.handleException(e);
        }

        return key;
    }

    /**
     * Converts a public key into a String which can be saved by the user to use for encryption.
     *
     * @return A String version of the public Key which can only be used if split  at the correct position
     *
     * @throws CrypterException
     */
    public String getPublicKeyString() throws CrypterException {

        String keyString;

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            RSAPublicKeySpec publicKeySpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            keyString = publicKeySpec.getModulus().toString() + "/" + publicKeySpec.getPublicExponent().toString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw CrypterException.handleException(e);
        }

        return keyString;
    }

    /**
     * Converts a private key into a String which can be saved by the user to use for decryption.
     *
     * @return A String version of the private Key which can only be used if split  at the correct position
     *
     * @throws CrypterException
     */
    public String getPrivateKeyString() throws CrypterException {

        String keyString;

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            RSAPrivateKeySpec privateKeySpec = keyFactory.getKeySpec(privateKey, RSAPrivateCrtKeySpec.class);
            keyString = privateKeySpec.getModulus().toString() + "/" + privateKeySpec.getPrivateExponent().toString();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw CrypterException.handleException(e);
        }

        return keyString;
    }

}
