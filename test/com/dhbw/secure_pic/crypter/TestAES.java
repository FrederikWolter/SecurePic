package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.data.Information;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.Assert.*;

/**
 * Tests the functionality of the AES class
 * A given message is encrypted and decrypted with a given password.
 * It is asserted that the decrypted message matches the one given above while the encrypted message doesn't.
 * Author: Kirolis Eskondis
 */
public class TestAES {

    @Test
    public void testEncryptDecrypt() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {

        String message = "Hello World";
        Information information = Information.getInformationFromString(message);

        AES aes = new AES("password");

        Information encryptedInformation = aes.encrypt(information);
        assertNotEquals(encryptedInformation.toText(),message);

        Information decryptedInformation = aes.decrypt(encryptedInformation);
        assertEquals(decryptedInformation.toText(), message);
    }

}