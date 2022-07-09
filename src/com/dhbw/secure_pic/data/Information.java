package com.dhbw.secure_pic.data;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalLengthException;
import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.coder.utility.BitFetcher;
import com.dhbw.secure_pic.data.utility.ImageSelection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

import static com.dhbw.secure_pic.data.Information.Type.*;

/**
 * This class implements the Information data type.<br>
 * Information is used as the primary carrier of the received/sent information embedded in the image.
 *
 * @author Frederik Wolter supported by Kirolis Eskondis
 */
public class Information {

    // region public static
    /** Length of data type in bytes. */
    public static final int TYPE_LENGTH = Integer.BYTES;
    /** Length of data length in bytes. */
    public static final int LENGTH_LENGTH = Integer.BYTES;
    /** Length of all metadata total in bytes. */
    public static final int META_LENGTH = TYPE_LENGTH + LENGTH_LENGTH;
    // endregion

    // region attributes
    /** Data type of data saved in information. */
    private final Type type;

    /** Raw data saved in information as a byte array. */
    private byte[] data;

    /** (Brutto) data length of data saved in information in bytes. */
    private int length;
    // endregion

    /** Enum representing the available Information types. */
    public enum Type {
        TEXT,
        IMAGE_PNG,
        IMAGE_JPG,
        IMAGE_GIF
    }

    /**
     * Plain Constructor of Information - <b>Sender</b><br>
     * Used for <i>internally</i> creating an information object by static methods - hence private.
     *
     * @param data raw content data (without any metadata).
     * @param type type of data saved in information.
     */
    private Information(byte[] data, Type type) {
        this.data = data;
        this.type = type;
        this.length = data.length;
    }

    /**
     * Plain Constructor of Information - <b>Receiver</b><br>
     * Only saving metadata, actual data is later added through setData().
     * Used for <i>internally</i> creating an information object by static methods - hence private.
     *
     * @param type   type of data saved in information.
     * @param length length of data saved in information.
     */
    private Information(Type type, int length) {
        this.data = null;
        this.type = type;
        this.length = length;
    }

    /**
     * Get information object from content string.<br>
     * Internally uses private Constructor for creating an information.
     *
     * @param text text given by the user.
     *
     * @return created information.
     */
    public static Information getInformationFromString(String text) {
        // create information by converting string to byte array with UTF-8.
        // see https://stackoverflow.com/a/18571348/13777031

        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        return new Information(data, TEXT);
    }

    /**
     * Get information object from image path.<br>
     * Internally uses private Constructor for creating an information.
     *
     * @param path path to be used for information.
     *
     * @return created information.
     *
     * @throws IllegalTypeException
     */
    public static Information getInformationFromImage(String path) throws IllegalTypeException {
        // see https://mkyong.com/java/how-to-convert-bufferedimage-to-byte-in-java/

        // get file extension from path
        String extension = ContainerImage.getFileExtension(path);

        // get image type
        Type type = switch (extension) {
            case "png" -> IMAGE_PNG;
            case "jpg" -> IMAGE_JPG;
            case "gif" -> IMAGE_GIF;
            default -> null;
        };
        if (type == null)
            throw new IllegalTypeException("Invalid path given for image file. Recognized extension '" + extension + "'.");

        // read in image from path
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new IllegalTypeException("An error occurred loading the selected image: '" + e.getMessage() + "'");
        }

        // convert BufferedImage to byte[]
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, extension, byteArrayOutputStream);
        } catch (IOException e) {
            throw new IllegalTypeException("An error occurred loading the selected image: '" + e.getMessage() + "'");
        }
        byte[] data = byteArrayOutputStream.toByteArray();

        return new Information(data, type);
    }

    /**
     * Get information object from raw data array read from encoded image.<br>
     * Internally uses private Constructor for creating an information.
     *
     * @param dataRaw raw data array read from container image.
     *
     * @return created information.
     *
     * @throws IllegalTypeException if type id is not legal.
     */
    public static Information getInformationFromData(byte[] dataRaw)
            throws IllegalTypeException, IllegalLengthException {
        // using ByteBuffer for handling binary data
        // see https://stackoverflow.com/a/1936865/13777031, https://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html
        ByteBuffer buffer = ByteBuffer.wrap(dataRaw)
                .order(ByteOrder.BIG_ENDIAN);   // set buffer to use big-endian

        // placeholder variables to be initialized
        Type type;

        // readout data from buffer
        int length = buffer.getInt();
        int typeRaw = buffer.getInt();

        if (0 <= typeRaw && typeRaw < Type.values().length) {   // validate type
            type = Type.values()[typeRaw];
        } else {
            throw new IllegalTypeException("Invalid content type in received data: " + typeRaw);
        }

        if (length == 0) {
            throw new IllegalLengthException("Invalid information length: " + length);
        }

        // build data object
        return new Information(type, length);
    }

    /**
     * Get the information as a big-endian byte array.<br>
     * Therefor first length then type and data is placed as big-endian bytes in one array.
     *
     * @return created big endian array.
     */
    public byte[] toBEBytes() {
        // using ByteBuffer for handling binary data
        // see https://stackoverflow.com/a/1936865/13777031, https://docs.oracle.com/javase/7/docs/api/java/nio/ByteBuffer.html
        ByteBuffer buffer = ByteBuffer.allocate(META_LENGTH + this.length)
                .order(ByteOrder.BIG_ENDIAN);   // set buffer to use big-endian

        // insert data to array
        buffer.putInt(this.length);
        buffer.putInt(this.type.ordinal()); // convert enum value to int through ordinal()
        buffer.put(this.data);

        return buffer.array();
    }

    /**
     * Copy the content of information to Windows clip board. <br>
     * Works for images and text.
     *
     * @throws IOException
     */
    public void copyToClipboard() throws IOException {
        // get clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // get content and save to clipboard
        if (this.type == TEXT) {
            StringSelection content = new StringSelection(toText());
            clipboard.setContents(content, null);
        } else if (this.type == IMAGE_PNG || type == IMAGE_JPG || type == IMAGE_GIF) {
            ImageSelection content = new ImageSelection(toImage());
            clipboard.setContents(content, null);
        }
    }

    // region converts

    /**
     * Converter to get the text from an information object <b>IF</b> a text is saved in it.
     *
     * @return text saved in information OR null if no text saved.
     */
    public String toText() {
        if (this.type == TEXT) {
            return new String(this.getData(), StandardCharsets.UTF_8);
        }
        return null;
    }

    /**
     * Converter to get the image from an information object <b>IF</b> an image is saved in it.
     *
     * @return image saved in information OR null if no image saved.
     */
    public BufferedImage toImage() throws IOException {
        if (this.type == IMAGE_PNG || type == IMAGE_JPG || type == IMAGE_GIF) {
            // convert data to BufferedImage
            InputStream inputStream = new ByteArrayInputStream(data);
            return ImageIO.read(inputStream);   // TODO catch exception
        }
        return null;
    }

    /**
     * Get the data stored information as a BitFetcher to encode it into a container image.
     *
     * @return BitFetcher representing the information.
     */
    public BitFetcher toBitFetcher() {
        return new BitFetcher(toBEBytes());
    }

    // endregion

    // region getter & setter
    public byte[] getData() {
        return data;
    }

    /**
     * Method for setting the data after precess finished reading if from received container image.<br>
     * (Information length should match already defined length - or exception occurs)
     *
     * @param data data to be saved in information.
     *
     * @throws IllegalLengthException
     */
    public void setData(byte[] data) throws IllegalLengthException {
        if (data.length == this.length) {
            this.data = data;
        } else {
            throw new IllegalLengthException("Invalid content length: should=" + length + " new=" + data.length);
        }
    }

    /**
     * Method for setting the data of an information after it was encrypted. <br>
     * (Information length can be changed through this setter)
     *
     * @param data encrypted data to be saved in information.
     */
    public void setEncryptedData(byte[] data) {
        this.data = data;
        this.length = data.length;
    }

    public Type getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    /**
     * Get the total length of information including metadata.
     *
     * @return total length of information including metadata in byte.
     */
    public int getTotalLength() {
        return length + META_LENGTH;
    }

    // endregion
}
