package com.dhbw.secure_pic.auxiliary;

import java.util.ArrayList;

/**
 * Utility class for assembling byte arrays from sequential bit values.
 *
 * @author Jonas Lauschke, Frederik Wolter
 */
public class BitAssembler {
    // region attributes
    private final ArrayList<Byte> data;
    private long position = 0;
    // endregion

    /**
     * Constructor for BitAssembler class.
     */
    public BitAssembler() {
        data = new ArrayList<>();
    }

    /**
     * Append a bit to the data stored in BitAssembler object.
     * Adds bit values to the data in the order they were coded into the image, starting with the MSB of the first byte.
     *
     * @param value bit to be added to curren position.
     */
    public void append(byte value) {
        // add new byte to array list if there is no more space left
        if (data.size() <= position / 8) {
            data.add((byte) 0);
        }
        // calculate current index in array list
        int idx = (int) (position / 8);

        // get current byte value
        byte currentByte = data.get(idx);
        int bitPosition = (int) (7 - (position % 8));
        byte valueByte = (byte) (value << bitPosition);

        // calculate new byte value
        byte newByte = (byte) (currentByte + valueByte);

        // set new byte value
        data.set(idx, newByte);

        // increment position
        position += 1;
    }

    /**
     * Export result of assembler in form of a byte array.
     *
     * @return result byte array.
     */
    public byte[] toByteArray() {
        // array for return
        byte[] array = new byte[data.size()];

        // copy each byte over to array - unfortunately there is no method for that.
        for (int i = 0; i < data.size(); i++) {
            array[i] = data.get(i);
        }

        return array;
    }

    /** Clear data in BitAssembler. */
    public void clear() {
        data.clear();
        position = 0;
    }

    // region getter

    public long getPosition() {
        return position;
    }

    // endregion
}
