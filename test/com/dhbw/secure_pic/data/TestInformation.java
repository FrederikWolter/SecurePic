package com.dhbw.secure_pic.data;

import com.dhbw.secure_pic.auxiliary.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.IllegalTypeException;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class TestInformation {

    @Test
    public void getInformationFromString() {
        String testString = "Test !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~öäüÖÄÜ€©§¼Äÿ";

        Information info = Information.getInformationFromString(testString);

        assertEquals(info.toText(), testString);
        assertEquals(info.getLength(), testString.getBytes(StandardCharsets.UTF_8).length);
        assertEquals(info.getType(), Information.Type.TEXT);
    }

    @Test
    public void getInformationFromImage() {
    }

    @Test
    public void getInformationFromData() throws IllegalLengthException, IllegalTypeException {
        String testString = "Test !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~öäüÖÄÜ€©§¼Äÿ";

        Information info = Information.getInformationFromString(testString);
        byte[] rawData = info.toBEBytes();

        Information info2 = Information.getInformationFromData(rawData);

        assertEquals(info.getLength(), info2.getLength());
        assertEquals(info.getType(), info2.getType());
        assertArrayEquals(info.getData(), info2.getData());
    }

    @Test
    public void toBEBytes() {
        String testString = "0123456789";

        Information info = Information.getInformationFromString(testString);

        byte[] result = info.toBEBytes();
        // TODO add automatic test?
    }
}