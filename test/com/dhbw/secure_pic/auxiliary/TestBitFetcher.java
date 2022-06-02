package com.dhbw.secure_pic.auxiliary;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Frederik Wolter
 */
public class TestBitFetcher {

    @Test
    public void next() {
        byte[] data = {(byte) 0b00110100, (byte) 0b11100101, (byte) 0b10111010};
        int[] control = {0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0};

        BitFetcher fetcher = new BitFetcher(data);

        int counter = 0;

        assertEquals(0, fetcher.next());
        assertEquals(0, fetcher.next());
        assertEquals(1, fetcher.next());
        assertEquals(1, fetcher.next());
        assertEquals(0, fetcher.next());

//        for (int i: control) {
//            assertEquals(i, fetcher.next());
//            assertTrue(fetcher.hasNext());
//
//            counter += 1;
//        }
    }
}