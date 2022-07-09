package com.dhbw.secure_pic.coder;

import com.dhbw.secure_pic.coder.utility.BitAssembler;
import com.dhbw.secure_pic.coder.utility.BitFetcher;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


/**
 * Some test methods for testing {@link LeastSignificantBit}.
 *
 * @author Frederik Wolter
 */
public class TestBitAssembler {

    @Test
    public void testAppendToByteArray() {
        byte[] data = {(byte) 0b00110100, (byte) 0b11100101, (byte) 0b10111010};

        BitFetcher fetcher = new BitFetcher(data);
        BitAssembler assembler = new BitAssembler();

        while (fetcher.hasNext()) {
            assembler.append(fetcher.next());
        }

        assertArrayEquals(data, assembler.toByteArray());
    }

}