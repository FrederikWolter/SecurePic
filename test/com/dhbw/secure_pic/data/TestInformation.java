package com.dhbw.secure_pic.data;

import com.dhbw.secure_pic.auxiliary.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.IllegalTypeException;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class TestInformation {

    @Test
    public void getInformationFromString() {
        @SuppressWarnings("SpellCheckingInspection")
        String testString = "Test !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~öäüÖÄÜ€©§¼Äÿ";

        // create information from test string
        Information info = Information.getInformationFromString(testString);

        assertEquals(info.toText(), testString);
        assertEquals(info.getLength(), testString.getBytes(StandardCharsets.UTF_8).length);
        assertEquals(info.getType(), Information.Type.TEXT);
    }

    @Test
    public void getInformationFromImage() throws IllegalTypeException, IOException {
        // TODO add automatic test?

        Information info;
        BufferedImage image1;
        BufferedImage image2;

        // test image JPG
        info = Information.getInformationFromImage("test/com/dhbw/secure_pic/data/JPG_Test.jpg");
        image1 = info.toImage();

        image2 = ImageIO.read(new File("test/com/dhbw/secure_pic/data/JPG_Test.jpg"));
//        assertEquals(image1.toString(), image2.toString());

        // test image PNG
        info = Information.getInformationFromImage("test/com/dhbw/secure_pic/data/PNG_Test.png");
        image1 = info.toImage();

        image2 = ImageIO.read(new File("test/com/dhbw/secure_pic/data/PNG_Test.png"));
//        assertEquals(image1.toString(), image2.toString());
    }

    @Test
    public void getInformationFromData() throws IllegalLengthException, IllegalTypeException {
        @SuppressWarnings("SpellCheckingInspection")
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