package com.dhbw.secure_pic.coder;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.auxiliary.exceptions.InsufficientCapacityException;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 *
 * @author Frederik Wolter
 */
public class TestLeastSignificantBit {

    @Test
    public void encodeDecode() throws IllegalTypeException, IOException, InsufficientCapacityException, IllegalLengthException {
        // send
        String in = "This is a Test 123456öäü?0§";
        Information info = Information.getInformationFromString(in);
        ContainerImage image = new ContainerImage("test/com/dhbw/secure_pic/data/PNG_Test.png");

        LeastSignificantBit coder = new LeastSignificantBit(image);
        ContainerImage encoded = coder.encode(info);
        // encoded.copyToClipboard();

        // receive
        LeastSignificantBit coder2 = new LeastSignificantBit(encoded);
        Information info2 = coder2.decode();

        String out = info2.toText();

        assertEquals(in, out);
    }

    @Test
    public void getCapacity() throws IllegalTypeException, IOException {
        ContainerImage image = new ContainerImage("test/com/dhbw/secure_pic/data/PNG_Test.png");
        LeastSignificantBit coder = new LeastSignificantBit(image);

        assertEquals(123456, coder.getCapacity());
    }
}