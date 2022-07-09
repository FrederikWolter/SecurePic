package com.dhbw.secure_pic.data;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;


/**
 * Some test methods for testing {@link ContainerImage}.
 *
 * @author Frederik Wolter
 */
public class TestContainerImage {

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testGetFileExtension() {
        String path;

        path = "test/com/dhbw/secure_pic/data/PNG_Test.png";
        assertEquals("png", ContainerImage.getFileExtension(path));

        path = "E:\\User\\Horst\\Team Picture\\main.py";
        assertEquals("py", ContainerImage.getFileExtension(path));

        path = "./test/game.c";
        assertEquals("c", ContainerImage.getFileExtension(path));
    }

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testCopyToClipboard() throws IllegalTypeException {
        ContainerImage image = new ContainerImage("test/com/dhbw/secure_pic/data/PNG_Test.png");
        image.copyToClipboard();
        // FIXME add automatic test?
    }

    @SuppressWarnings("HardCodedStringLiteral")
    @Test
    public void testARGB() throws IllegalTypeException {
        ContainerImage image = new ContainerImage("test/com/dhbw/secure_pic/data/PNG_Test.png");

        byte[] result;

        image.setARGB(0, 0, (byte) 255, (byte) 0, (byte) 0);
        result = image.getARGB(0, 0);
        assertArrayEquals(result, new byte[]{(byte) 255, (byte) 255, (byte) 0, (byte) 0});

        image.setARGB(0, 0, (byte) 0, (byte) 255, (byte) 0);
        result = image.getARGB(0, 0);
        assertArrayEquals(result, new byte[]{(byte) 255, (byte) 0, (byte) 255, (byte) 0});

        image.setARGB(0, 0, (byte) 0, (byte) 0, (byte) 255);
        result = image.getARGB(0, 0);
        assertArrayEquals(result, new byte[]{(byte) 255, (byte) 0, (byte) 0, (byte) 255});

        image.setARGB(0, 0, (byte) 125, (byte) 106, (byte) 55);
        result = image.getARGB(0, 0);
        assertArrayEquals(result, new byte[]{(byte) 255, (byte) 125, (byte) 106, (byte) 55});

        image.copyToClipboard();
    }
}