package com.dhbw.secure_pic.auxiliary;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestBitAssembler {

    @Test
    public void appendToByteArray() {
        byte[] data = {(byte) 0b00110100, (byte) 0b11100101, (byte) 0b10111010};

        BitFetcher fetcher = new BitFetcher(data);
        BitAssembler assembler = new BitAssembler();

        while (fetcher.hasNext()) {
            assembler.append(fetcher.next());
        }

        assertArrayEquals(data, assembler.toByteArray());
    }

}