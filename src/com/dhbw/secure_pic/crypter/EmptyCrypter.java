package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.data.Information;

/**
 * This class implements a "fake encryption" to use if the user wishes to not encrypt the data at all.
 * Since the program always encrypts/decrypts no matter if an algorithm was chosen, an empty crypter is needed for the program sequence to run as planned.
 * It extends the Crypter class.
 *
 * @author Kirolis Eskondis
 */

public class EmptyCrypter extends Crypter{

    @Override
    public Information encrypt(Information information) {
        return information;
    }

    @Override
    public Information decrypt(Information information) {
        return information;
    }
}
