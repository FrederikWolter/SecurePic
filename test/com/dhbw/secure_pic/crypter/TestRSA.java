package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the functionality of the RSA class
 * A given message is encrypted and decrypted with generated public and private keys.
 * It is asserted that the decrypted message matches the one given above while the encrypted message doesn't.
 *
 * @author Kirolis Eskondis
 */
public class TestRSA {

    @Test
    public void testRSAEncryptDecrypt() throws CrypterException {

        String message = "Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!";
        Information information = Information.getInformationFromString(message);

        //Multiple RSA are used to simulate the communication between multiple devices
        RSA generateKeyRSA = new RSA();

        RSA encryptRSA = new RSA(generateKeyRSA.getPublicKey());
        encryptRSA.encrypt(information);
        assertNotEquals(information.toText(), message);

        RSA decryptRSA = new RSA(generateKeyRSA.getPrivateKey());
        decryptRSA.decrypt(information);
        assertEquals(information.toText(), message);
    }

    @Test
    public void getNew() {
        assert false; // TODO
    }
}