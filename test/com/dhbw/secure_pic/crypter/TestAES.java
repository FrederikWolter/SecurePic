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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the functionality of the AES class
 * A given message is encrypted and decrypted with a given password.
 * It is asserted that the decrypted message matches the one given above while the encrypted message doesn't.
 *
 * @author Kirolis Eskondis
 */
public class TestAES {

    @Test
    public void testEncryptDecrypt() throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {

        String message = "Hello World";
        Information information = Information.getInformationFromString(message);

        AES encryptAES = new AES("password");

        encryptAES.encrypt(information);
        assertNotEquals(information.toText(), message);

        //A second AES is created to simulate two different devices
        AES decryptAES = new AES("password");
        decryptAES.decrypt(information);
        assertEquals(information.toText(), message);
    }

}