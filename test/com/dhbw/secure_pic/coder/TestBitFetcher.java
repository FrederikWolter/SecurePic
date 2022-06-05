package com.dhbw.secure_pic.coder;

import com.dhbw.secure_pic.coder.utility.BitFetcher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Frederik Wolter
 */
public class TestBitFetcher {

    @Test
    public void testNext() {
        byte[] data = {(byte) 0b00110100, (byte) 0b11100101, (byte) 0b10111010};
        int[] control = {0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0};

        BitFetcher fetcher = new BitFetcher(data);

        int counter = 0;

        for (int i : control) {
            assertEquals(i, fetcher.next());

            if (counter < control.length - 1) assertTrue(fetcher.hasNext());
            counter += 1;
        }
    }
}