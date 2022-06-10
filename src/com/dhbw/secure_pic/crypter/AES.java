package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.CrypterKey;
import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

// FIXME comment
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
    public AES(String password) {
        this.key = getKeyFromPassword(password);
        this.algorithm = "AES";
    }

    /**
     * @param information contains the message to encrypt
     */
    @Override
    public Information encrypt(Information information, ProgressMonitor monitor) throws CrypterException {
        // TODO use progressMonitor
        try {
            Cipher encryptionCipher = Cipher.getInstance(algorithm);
            encryptionCipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] encryptedBytes = encryptionCipher.doFinal(information.getData());
            byte[] outPutBytes = Base64.getEncoder().encode(encryptedBytes);

            information.setEncryptedData(outPutBytes);
            return information;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            throw CrypterException.handleException(e);    // wrap exceptions thrown by crypter to CrypterException
        }
    }


    /**
     * @param information contains the encrypted message to decrypt
     */
    @Override
    public Information decrypt(Information information, ProgressMonitor monitor) throws CrypterException {
        // TODO use progressMonitor
        try {
            Cipher decryptionCipher = Cipher.getInstance(algorithm);
            decryptionCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = decryptionCipher.doFinal(Base64.getDecoder().decode(information.toText()));

            information.setEncryptedData(decryptedBytes);
            return information;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            throw CrypterException.handleException(e);  // wrap exceptions thrown by crypter to CrypterException
        }
    }

    @Override
    public void generateKey(ProgressMonitor monitor) {
        // TODO extract key generation to here & use progressMonitor
    }

    /**
     * @param password is the password given by the user turned into a 32-byte array
     *
     * @return secret is the generated key
     */
    private SecretKey getKeyFromPassword(String password) {

        byte[] keyBytes = new byte[32];
        for (int i = 0; i < password.length() && i < 32; i++) {
            keyBytes[i] = password.getBytes()[i];
        }
        return new CrypterKey(keyBytes, "AES");
    }

}
