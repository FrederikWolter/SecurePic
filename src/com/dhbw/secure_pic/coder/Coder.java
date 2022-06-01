package com.dhbw.secure_pic.coder;

import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;

// TODO COMMENT
// TODO implement

/**
 * This class implements the Coder used to code/decode information into container images.<br>
 * Class is abstract, hence its functionality could only be used through its children.
 *
 * @author Frederik Wolter
 */
public abstract class Coder {

    // region attributes
    private final ContainerImage img;
    // endregion


    // protected to only be able to call constructor from inherited classes
    protected Coder(ContainerImage img) {
        this.img = img;
    }


    public abstract ContainerImage encode(Information info);

    public abstract Information decode();

}
