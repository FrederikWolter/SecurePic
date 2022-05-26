package com.dhbw.secure_pic.data;

import org.junit.Test;
import java.nio.charset.StandardCharsets;
import static junit.framework.TestCase.assertEquals;

public class TestInformation {

    @Test
    public void getInformationFromString() {
        String testString = "Test !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~öäüÖÄÜ€©§¼Äÿ";

        Information info =  Information.getInformationFromString(testString);
        assertEquals(testString, new String(info.getData(), StandardCharsets.UTF_8));
    }

    @Test
    public void getInformationFromImage() {
    }

    @Test
    public void getInformationFromData() {
    }
}