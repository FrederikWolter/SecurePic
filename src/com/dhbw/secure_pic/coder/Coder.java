package com.dhbw.secure_pic.coder;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.auxiliary.exceptions.InsufficientCapacityException;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;

// FIXME comment
// TODO fill up with random data til capacity?

/**
 * This class implements the Coder used to code/decode information into container images.<br>
 * Class is abstract, hence its functionality could only be used through its children.
 *
 * @author Frederik Wolter
 */
public abstract class Coder {

    // region attributes
    protected final ContainerImage image;
    // endregion

    /**
     * Constructor of Coder.<br>
     * It is protected, so it can only be called by its children.
     *
     * @param image container image to work with
     */
    protected Coder(ContainerImage image) {
        this.image = image;
    }


    public abstract ContainerImage encode(Information info, ProgressMonitor monitor) throws InsufficientCapacityException;

    public abstract Information decode(ProgressMonitor monitor) throws IllegalTypeException, IllegalLengthException;

    public abstract int getCapacity();

}
