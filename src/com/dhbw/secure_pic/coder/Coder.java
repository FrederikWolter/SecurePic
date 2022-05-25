package com.dhbw.secure_pic.coder;

import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;

// TODO COMMENT
// TODO implement

public abstract class Coder {

    // region attributes
    private ContainerImage img;
    // endregion


    public Coder(ContainerImage img){
        this.img = img;
    }


    public abstract ContainerImage encode(Information info);

    public abstract Information decode();

}
