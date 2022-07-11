package com.dhbw.secure_pic.data;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;


/**
 * Some test methods for testing {@link Information}.
 *
 * @author Frederik Wolter
 */
public class TestInformation {

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testGetInformationFromString() {
        String testString = "Test !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~öäüÖÄÜ€©§¼Äÿ";

        // create information from test string
        Information info = Information.getInformationFromString(testString);

        assertEquals(info.toText(), testString);
        assertEquals(info.getLength(), testString.getBytes(StandardCharsets.UTF_8).length);
        assertEquals(info.getType(), Information.Type.TEXT);
    }

    @SuppressWarnings({"HardCodedStringLiteral", "UnusedAssignment", "unused"})
    @Test
    public void testGetInformationFromImage() throws IllegalTypeException, IOException {
        Information info;
        BufferedImage image1;
        BufferedImage image2;

        // test image JPG
        info = Information.getInformationFromImage("test/com/dhbw/secure_pic/data/JPG_Test.jpg");
        image1 = info.toImage();

        image2 = ImageIO.read(new File("test/com/dhbw/secure_pic/data/JPG_Test.jpg"));
        // due to a missing implementation of BufferedImage there is NO 'equals' method.
        // hence determining whether two images are the same is not an easy task.
        // assertEquals(image1.toString(), image2.toString());

        // test image PNG
        info = Information.getInformationFromImage("test/com/dhbw/secure_pic/data/PNG_Test.png");
        image1 = info.toImage();

        image2 = ImageIO.read(new File("test/com/dhbw/secure_pic/data/PNG_Test.png"));
        // assertEquals(image1.toString(), image2.toString());
        // FIXME add automatic test?
    }

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testGetInformationFromData() throws IllegalLengthException, IllegalTypeException {
        String testString = "Test !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~öäüÖÄÜ€©§¼Äÿ";

        Information info = Information.getInformationFromString(testString);
        byte[] rawData = info.toBEBytes();

        Information info2 = Information.getInformationFromData(rawData);
        info2.setData(info.getData());

        assertEquals(info.getLength(), info2.getLength());
        assertEquals(info.getType(), info2.getType());
        assertArrayEquals(info.getData(), info2.getData());
    }

    @Test
    public void testToBEBytes() {
        String testString = "0123456789";

        Information info = Information.getInformationFromString(testString);

        @SuppressWarnings("unused")
        byte[] result = info.toBEBytes();
        // FIXME add automatic test?
    }

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testCopyContentToClipboardText() throws IOException {
        String testString = "Test !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~öäüÖÄÜ€©§¼Äÿ";

        Information info = Information.getInformationFromString(testString);
        info.copyToClipboard();
        // FIXME add automatic test?
    }

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testCopyContentToClipboardImage() throws IOException, IllegalTypeException {
        Information info = Information.getInformationFromImage("test/com/dhbw/secure_pic/data/PNG_Test.png");
        info.copyToClipboard();
        // FIXME add automatic test?
    }
}