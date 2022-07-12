package com.dhbw.secure_pic.coder;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.auxiliary.exceptions.InsufficientCapacityException;
import com.dhbw.secure_pic.coder.utility.BitAssembler;
import com.dhbw.secure_pic.coder.utility.BitFetcher;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.pipelines.utility.ProgressMonitor;

import java.text.MessageFormat;

// TODO comment

/**
 * Class representing the interpretation of the encoding algorithm 'PlusMinusOne'.
 *
 * @author Frederik Wolter
 */
public class PlusMinusOne extends Coder {

    /**
     * Constructor for class {@link PlusMinusOne}.
     *
     * @param image container image the coder works with.
     */
    public PlusMinusOne(ContainerImage image) {
        super(image);
    }

    /**
     * Encode in the already set container image the given information.
     *
     * @param info information be encoded into container image.
     *
     * @return encoded container image.
     *
     * @throws InsufficientCapacityException
     */
    @Override
    public ContainerImage encode(Information info, ProgressMonitor monitor) throws InsufficientCapacityException {
        // test whether information will fit into container image
        int infoLength = info.getTotalLength();
        int imageCapacity = this.getCapacity();
        if (infoLength > imageCapacity) {
            throw new InsufficientCapacityException(MessageFormat.format(bundle.getString("except_does_not_fit"), infoLength, imageCapacity));
        }

        // get fetcher from information
        BitFetcher fetcher = info.toBitFetcher();

        // get size of image
        int width = super.image.getWidth();
        int height = super.image.getHeight();

        // save if there is a next bit available
        boolean hasNext = true;

        // save last bit
        boolean lastBit = false;

        // encode information into image by iterating over each pixel
        for (int x = 0; x < width && hasNext; x++) {
            for (int y = 0; y < height && hasNext; y++) {
                // get pixel data from x , y
                byte[] pixel = super.image.getARGB(x, y);   // [alpha, red, green, blue]

                // encode bits to the least significant bit of red, green, blue channel - leave alpha untouched
                for (int i = 1; i < 4 && hasNext; i++) {
                    // is there another bit in fetcher?
                    if (fetcher.hasNext()) {
                        byte nextBit = fetcher.next();              // get next bit from fetcher
                        pixel[i] = (byte) (pixel[i] & ~0b1);  // keep pixel value except last bit

                        // calculate nextBit according to lastBit:
                        // lastBit = 0 & nextBit = 0 -> bit = 0
                        // lastBit = 0 & nextBit = 1 -> bit = 1
                        // lastBit = 1 & nextBit = 0 -> bit = 1
                        // lastBit = 1 & nextBit = 1 -> bit = 0
                        if (lastBit) {
                            nextBit = (byte) ((nextBit == 0) ? 0b1 : 0b0);
                        }
                        pixel[i] |= nextBit;    // set last bit with next bit

                        lastBit = (nextBit == 0b1);  // save last bit
                    } else {
                        hasNext = false;    // save if there is no next bit -> breaks all for-loops
                    }
                }

                // set calculated argb value in image
                super.image.setARGB(x, y, pixel[1], pixel[2], pixel[3]);

                // update progress
                monitor.updateProgress((int) (fetcher.getPosition() / (infoLength) * 8));
            }
        }

        // return reference of coded image
        return super.image;
    }

    /**
     * Decode the information from the already set container image.
     *
     * @return decoded information.
     *
     * @throws IllegalTypeException
     * @throws IllegalLengthException
     */
    @Override
    public Information decode(ProgressMonitor monitor) throws IllegalTypeException, IllegalLengthException {
        // make space for result
        Information information = null;

        // get a BitAssembler
        BitAssembler assembler = new BitAssembler();

        // get size of image
        int width = super.image.getWidth();
        int height = super.image.getHeight();

        // save last bit
        boolean lastBit = false;

        // decode information from image by iterating over each pixel
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // get pixel data from x , y
                byte[] pixel = super.image.getARGB(x, y);   // [alpha, red, green, blue]

                // get LSB from red, green, blue channel - scip alpha
                for (int i = 1; i < 4 && (information == null || (information.getLength()) * 8L > assembler.getPosition()); i++) {
                    // test whether enough data is decoded for analyzing metadata
                    if (information == null && assembler.getPosition() == Information.META_LENGTH * 8) {
                        byte[] meta = assembler.toByteArray();                  // get data from assembler
                        assembler.clear();                                      // clear assembler
                        information = Information.getInformationFromData(meta); // create information object from metadata
                    }

                    byte nextBit = (byte) (pixel[i] & 0b1);     // get bit value from current pixel chanel

                    // calculate nextBit value according to lastBit:
                    // lastBit = 0 & nextBit = 0 -> bit = 0
                    // lastBit = 0 & nextBit = 1 -> bit = 1
                    // lastBit = 1 & nextBit = 0 -> bit = 1
                    // lastBit = 1 & nextBit = 1 -> bit = 0
                    if (lastBit) {
                        lastBit = (nextBit == 0b1);     // save new lastBit
                        nextBit = (byte) ((nextBit == 0) ? 0b1 : 0b0);
                    } else {
                        lastBit = (nextBit == 0b1);     // save new lastBit
                    }
                    assembler.append(nextBit);      // append masked LSB from pixel chanel to bit assembler
                }

                // update progress
                if (information != null) {
                    monitor.updateProgress((int) (assembler.getPosition() / (information.getLength()) * 8));
                }
            }
        }

        // validate length of data
        if (information != null && information.getLength() * 8L != assembler.getPosition()) {    // for loop ended before length was reached
            throw new IllegalLengthException(bundle.getString("except.length_mismatch"));
        } else if (information != null) {     // length not long enough to get all metadata
            information.setData(assembler.toByteArray());
        }

        // return gather information
        return information;
    }

    /**
     * Utility method for determine the capacity of set container image.
     *
     * @return capacity of container image in bytes.
     */
    @Override
    public int getCapacity() {
        // get size of image
        int width = super.image.getWidth();
        int height = super.image.getHeight();

        int bitsPerPixel = 3;       // red, green, blue get modified

        return (width * height * bitsPerPixel) / 8;
    }
}
