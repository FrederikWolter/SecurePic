package com.dhbw.secure_pic.data;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.auxiliary.ImageSelection;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.dhbw.secure_pic.data.ContainerImage.Type.*;

// TODO COMMENT

/**
 * This class implements the functionality of the ContainerImage.<br>
 * The ContainerImage describes the image the message will be encoded to/decoded from.
 *
 * @author Frederik Wolter supported by Kirolis Eskondis
 */
public class ContainerImage {

    // region attributes
    /** Path to original image loaded from drive. */
    private final String path;
    /** BufferedImage loaded from path. */
    private final BufferedImage image;
    /** Type of image. */
    private final Type type;
    // endregion

    /** Enum representing the available image types. */
    enum Type {
        PNG,
        JPG
    }

    /**
     * Plain Constructor of ContainerImage.<br>
     *
     * @param path path to container image to be loaded given by user.
     *
     * @throws IllegalTypeException
     * @throws IOException
     */
    public ContainerImage(String path) throws IllegalTypeException, IOException {
        // TODO wrap ioexception
        // set attributes
        this.path = path;

        // get file extension from path
        String extension = ContainerImage.getFileExtension(path);

        // get image type
        this.type = switch (extension) {
            case "png" -> PNG;
            case "jpg" -> JPG;
            default -> null;
        };
        if (this.type == null)
            throw new IllegalTypeException("Invalid path given for image file. Recognized extension '" + extension + "'.");

        // read in image from path
        this.image = ImageIO.read(new File(path));
    }

    /**
     * Utility method extracting file extension from path.<br>
     * Not really related to ContainerImage.
     *
     * @param path with file extension to be extracted.
     *
     * @return file extension
     */
    public static String getFileExtension(String path) {
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) extension = path.substring(i + 1);

        return extension;
    }

    /**
     * Export current saved buffered image as a file to given path.
     *
     * @param destPath destination path for image to be saved to.
     *
     * @throws IOException
     * @throws IllegalTypeException
     */
    public void exportImg(String destPath) throws IOException, IllegalTypeException {
        String format = switch (this.type) {
            case PNG -> "png";
            case JPG -> "jpg";
        };

        if (!format.equals(ContainerImage.getFileExtension(destPath)))
            throw new IllegalTypeException("Given extension of path does not match the type of image.");

        ImageIO.write(this.image, format, new File(path));
    }

    /**
     * Copy container image to Windows clip board. <br>
     * Works for images and text.
     */
    public void copyToClipboard(){
        // get clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // get content and save to clipboard
        ImageSelection content = new ImageSelection(this.image);
        clipboard.setContents(content, null);
    }

    // region getter & setter

    /**
     * Setter for single pixel values of buffered image.
     *
     * @param x x value of pixel.
     * @param y y value of pixel.
     * @param a alpha value to be set.
     * @param r red value to be set.
     * @param g green value to be set.
     * @param b blue value to be set.
     */
    public void setARGB(int x, int y, byte a, byte r, byte g, byte b) {
        // create empty int (4 byte)
        int argbValue = 0;

        // build argb value
        argbValue += a << 24;   // alpha value
        argbValue += r << 16;   // red value
        argbValue += g << 8;    // green value
        argbValue += b;         // blue value

        this.image.setRGB(x, y, argbValue);
    }

    /**
     * Getter for single pixel values from buffered image.<br>
     * Returned array has form: [alpha, red, green, blue]
     *
     * @param x x value of pixel.
     * @param y y value of pixel.
     *
     * @return calculated values in form af array.
     */
    public byte[] getARGB(int x, int y) {
        // get argb value from buffered image
        int argbValue = this.image.getRGB(x, y);

        // extract single values
        byte[] values = new byte[4];

        values[0] = (byte) (argbValue >> 24);   // alpha value
        values[1] = (byte) (argbValue >> 16);   // red value
        values[2] = (byte) (argbValue >> 8);    // green value
        values[3] = (byte) (argbValue);         // blue value

        return values;
    }

    public String getPath() {
        return path;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Type getType() {
        return type;
    }

    // endregion
}
