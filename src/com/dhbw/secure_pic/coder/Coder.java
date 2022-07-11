package com.dhbw.secure_pic.coder;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.auxiliary.exceptions.InsufficientCapacityException;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;


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
     * Constructor of {@link Coder}.<br>
     * It is protected, so it can only be called by its children.
     *
     * @param image container image to work with
     */
    protected Coder(ContainerImage image) {
        this.image = image;
    }

    /**
     * General encoding method for encoding the {@link Information} into the given {@link ContainerImage}.
     *
     * @param info    to be encoded into the image
     * @param monitor for monitoring the progress
     *
     * @return Encoded ContainerImage
     *
     * @throws InsufficientCapacityException thrown if information does not fit into image
     */
    public abstract ContainerImage encode(Information info, ProgressMonitor monitor)
            throws InsufficientCapacityException;

    /**
     * General decoding method for decoding an {@link Information} from the given {@link ContainerImage}.
     *
     * @param monitor for monitoring the progress
     *
     * @return Decoded Information
     *
     * @throws IllegalTypeException   thrown if the type in the data does not match any known type
     * @throws IllegalLengthException thrown if the length of data does not match the meta-data "length"
     */
    public abstract Information decode(ProgressMonitor monitor) throws IllegalTypeException, IllegalLengthException;

    /**
     * Necessary getter for determining the {@link ContainerImage} capacity with each individual algorithm.
     *
     * @return Capacity in bytes.
     */
    public abstract int getCapacity();

}
