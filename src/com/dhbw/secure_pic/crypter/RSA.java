package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.util.Base64;

// FIXME comment
// TODO do more work self instead of handing it over to library?

/**
 * This class implements the RSA encryption method used to encrypt/decrypt messages.<br>
 * It extends the Crypter class.
 *
 * @author Kirolis Eskondis
 */
public class RSA extends Crypter {

    // region attributes
    private final PrivateKey privateKey;
    private final PublicKey publicKey;
    private final String algorithm;
    // endregion

    /**
     * This constructor is used for generating the KeyPair.
     * The receiver uses this to create private and public key
     */
    public RSA() throws CrypterException {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
            KeyPair pair = generator.generateKeyPair();

            this.privateKey = pair.getPrivate();
            this.publicKey = pair.getPublic();
            this.algorithm = "RSA";
        } catch (NoSuchAlgorithmException e) {
            throw CrypterException.handleException(e);  // wrap exceptions thrown by crypter to CrypterException
        }
    }

    /**
     * This constructor is used for RSA Encryption as it is used by the sender
     *
     * @param publicKey is the only key needed for encryption so privateKey is set to NULL
     */
    public RSA(PublicKey publicKey) {
        this.privateKey = null;
        this.publicKey = publicKey;
        this.algorithm = "RSA";
    }

    /**
     * This constructor is used for RSA Decryption as it is used by the receiver
     *
     * @param privateKey is the only key needed for decryption so publicKey is set to NULL
     */
    public RSA(PrivateKey privateKey) {
        this.privateKey = privateKey;
        this.publicKey = null;
        this.algorithm = "RSA";
    }

    /**
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

            byte[] encryptedBytes = encryptCipher.doFinal(information.getData());
            byte[] outPutBytes = Base64.getEncoder().encode(encryptedBytes);
            information.setEncryptedData(outPutBytes);

            monitor.updateProgress(100);

            return information;

        }  catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            throw CrypterException.handleException(e);    // wrap exceptions thrown by crypter to CrypterException
        }
    }

    /**
     * @param information contains the message to decrypt
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

            byte[] decryptedBytes = decryptionCipher.doFinal(Base64.getDecoder().decode(information.toText()));
            information.setEncryptedData(decryptedBytes);

            monitor.updateProgress(100);

            return information;

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            throw CrypterException.handleException(e);  // wrap exceptions thrown by crypter to CrypterException
        }
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
