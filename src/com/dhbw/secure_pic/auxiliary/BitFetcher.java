package com.dhbw.secure_pic.auxiliary;

/**
 * Utility class for fetching bits one by one out of a byte array.
 *
 * @author Jonas Lauschke, Frederik Wolter
 */
public class BitFetcher {

    // region attributes
    private final byte[] bytes;
    private long position = 0;
    // endregion

    /**
     * Constructor for BitFetcher class.
     *
     * @param bytes array from wich to fetch the bits.
     */
    public BitFetcher(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * Get next bit value from fetcher.<br>
     * Returns bit values according to the order in which they are written in the array, starting with the MSB of the first byte.
     *
     * @return byte either 0b00000000 or 0b00000001.
     *
     * @throws IndexOutOfBoundsException if hasNext is false.
     */
    public byte next() throws IndexOutOfBoundsException {
        if (hasNext()) {
            int byteValue = bytes[(int) (position / 8)]; // get current byte
            int bitPosition = (int) (7 - (position % 8));
            int bitValue = byteValue >> bitPosition & 0b00000001; // get current bit

            position += 1;

            return (byte) bitValue;
        }
        throw new IndexOutOfBoundsException();
    }

    /**
     * For testing whether fetcher has another bit to get through next().
     * @return true if more bits available.
     */
    public boolean hasNext() {
        return bytes.length > position / 8;
    }

}
