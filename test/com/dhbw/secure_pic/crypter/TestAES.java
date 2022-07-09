package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the functionality of the AES class.<br>
 * A given message is encrypted and decrypted with a given password.
 * It is asserted that the decrypted message matches the one given above while the encrypted message doesn't.
 *
 * @author Kirolis Eskondis supported by Frederik Wolter
 */
public class TestAES {

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testAESEncryptDecrypt() throws CrypterException {
        String message = "Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!";
        Information information = Information.getInformationFromString(message);

        // Multiple AES are used to simulate the communication between multiple devices

        AES encryptAES = new AES("password");

        encryptAES.encrypt(information, progress -> { /* empty */ });
        assertNotEquals(information.toText(), message);

        AES decryptAES = new AES("password");
        decryptAES.decrypt(information, progress -> { /* empty */});
        assertEquals(information.toText(), message);
    }

}