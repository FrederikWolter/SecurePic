package com.dhbw.secure_pic.crypter;

import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;

// TODO comment

/**
 * This class implements a no-op encryption to use if the user wishes to NOT encrypt the data at all.
 * It is used to simplify the pipelines, hence the program always encrypts/decrypts no matter if encryption was enabled.
 * So an empty crypter is needed for the program sequence to run as planned.
 * It extends the Crypter class.
 *
 * @author Kirolis Eskondis
 */
public class EmptyCrypter extends Crypter {

    @Override
    public Information encrypt(Information information, ProgressMonitor monitor) {
        monitor.updateProgress(100);    // update progress to 100
        return information;
    }

    @Override
    public Information decrypt(Information information, ProgressMonitor monitor) {
        monitor.updateProgress(100);    // update progress to 100
        return information;
    }


}
