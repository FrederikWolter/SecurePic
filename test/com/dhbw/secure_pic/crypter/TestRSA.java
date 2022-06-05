package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.data.Information;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestRSA {

    @Test
    public void testRSAEncryptDecrypt() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        String message = "Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!";
        Information information = Information.getInformationFromString(message);

        RSA encryptRSA = new RSA();

        encryptRSA.encrypt(information);
        assertNotEquals(information.toText(), message);

        //A second AES is created to simulate two different devices
        RSA decryptRSA = new RSA(encryptRSA.getPrivateKey());
        decryptRSA.decrypt(information);
        assertEquals(information.toText(), message);
    }

    @Test
    public void getNew() {
        assert false; // TODO
    }
}