package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.auxiliary.exceptions.CrypterException;
import com.dhbw.secure_pic.data.Information;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests the functionality of the RSA class.<br>
 * A given message is encrypted and decrypted with generated public and private keys.
 * For the test to pass, it is necessary that the decrypted message matches the one given above while the encrypted message doesn't.
 *
 * @author Kirolis Eskondis
 */
public class TestRSA {

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testRSAEncryptDecrypt() throws CrypterException {
        String message = "Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!Testing «ταБЬℓσ»: 1<2 & 4+1>3, now 20% off!";
        Information information = Information.getInformationFromString(message);

        // Multiple RSA are used to simulate the communication between multiple devices
        RSA generateKeyRSA = new RSA();
        String publicKey = generateKeyRSA.getPublicKeyString();
        String privateKey = generateKeyRSA.getPrivateKeyString();

        RSA encryptRSA = new RSA(publicKey, RSA.keyType.PUBLIC);
        encryptRSA.encrypt(information, progress -> { /* empty */ });
        assertNotEquals(information.toText(), message);

        RSA decryptRSA = new RSA(privateKey, RSA.keyType.PRIVATE);
        decryptRSA.decrypt(information, progress -> { /* empty */ });
        assertEquals(information.toText(), message);
    }

}