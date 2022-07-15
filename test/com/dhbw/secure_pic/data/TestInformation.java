package com.dhbw.secure_pic.data;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;


/**
 * Some test methods for testing {@link Information}.
 *
 * @author Frederik Wolter supported by Kirolis Eskondis
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

        Information info = Information.getInformationFromImage("test/com/dhbw/secure_pic/data/PNG_Test.png");
        DataBufferByte image1Array = (DataBufferByte) info.toImage().getData().getDataBuffer();

        BufferedImage image2 = ImageIO.read(new File("test/com/dhbw/secure_pic/data/PNG_Test.png"));
        DataBufferByte image2Array = (DataBufferByte) image2.getData().getDataBuffer();
        assertArrayEquals(image1Array.getData(),image2Array.getData());
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

        byte[] BEBytes = info.toBEBytes();

        //Delete MetaData Bytes to make sure that data Bytes are equal to testString.getBytes()
        byte[] infoBytes = new byte[testString.length()];
        int startingPoint = BEBytes.length-testString.length();
        for(int i = startingPoint;i<BEBytes.length;i++){
            infoBytes[i-startingPoint] = BEBytes[i];
        }

        //Assert that both arrays are equal
        assertArrayEquals(infoBytes,testString.getBytes(StandardCharsets.UTF_8));
    }

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testCopyContentToClipboardText() throws IOException, UnsupportedFlavorException {
        String testString = "Test !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~öäüÖÄÜ€©§¼Äÿ";

        Information info = Information.getInformationFromString(testString);
        info.copyToClipboard();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipboardText = clipboard.getContents(null);

        //Assert that a string is saved in the clipboard
        assertTrue(clipboardText.isDataFlavorSupported(DataFlavor.stringFlavor));

        //Assert that string saved in clipboard is the same as string given above
        String clipBoardString = (String) clipboardText.getTransferData(DataFlavor.stringFlavor);
        assertEquals(testString,clipBoardString);
    }

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testCopyContentToClipboardImage() throws IOException, IllegalTypeException, UnsupportedFlavorException {
        Information informationFromImage = Information.getInformationFromImage("test/com/dhbw/secure_pic/data/PNG_Test.png");
        informationFromImage.copyToClipboard();
        DataBufferByte byteArray1 = (DataBufferByte) informationFromImage.toImage().getData().getDataBuffer();


        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable clipboardContents = clipboard.getContents(null);

        //Assert that an image is saved in the clipboard
        assertTrue(clipboardContents.isDataFlavorSupported(DataFlavor.imageFlavor));

        //Assert that image saved in clipboard is the same as image given above
        BufferedImage image2 = (BufferedImage) clipboardContents.getTransferData(DataFlavor.imageFlavor);
        DataBufferByte byteArray2 = (DataBufferByte) image2.getData().getDataBuffer();
        assertArrayEquals(byteArray1.getData(),byteArray2.getData());

    }
}