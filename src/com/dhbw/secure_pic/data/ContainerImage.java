package com.dhbw.secure_pic.data;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.data.utility.ImageSelection;
import com.dhbw.secure_pic.gui.Gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import static com.dhbw.secure_pic.data.ContainerImage.Type.JPG;
import static com.dhbw.secure_pic.data.ContainerImage.Type.PNG;

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
    /** get resource bundle managing strings */
    private static final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));
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
     * @throws IllegalTypeException either the given file extension does not match any type
     *                              or an error occurred during load.
     */
    public ContainerImage(String path) throws IllegalTypeException {
        // set attributes
        this.path = path;

        // get file extension from path
        String extension = ContainerImage.getFileExtension(path);

        // get image type
        this.type = switch (extension) {
            case "png" -> PNG; //NON-NLS
            case "jpg" -> JPG; //NON-NLS
            case "jpeg" -> JPG; //NON-NLS
            default -> null;
        };
        if (this.type == null)
            throw new IllegalTypeException(MessageFormat.format(bundle.getString("except.invalid_path_type"), extension));

        // read in image from path
        try {
            this.image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new IllegalTypeException(MessageFormat.format(bundle.getString("except.error_loading_img"), e.getMessage()));
        }
    }

    /**
     * Utility method extracting file extension from path.<br>
     *
     * @param path with file extension to be extracted.
     *
     * @return file extension
     */
    public static String getFileExtension(String path) {
        String extension = "";
        int i = path.lastIndexOf(".");
        if (i > 0) extension = path.substring(i + 1);

        return extension.toLowerCase();
    }

    /**
     * Export current saved buffered image as a file to given path.
     *
     * @param destPath destination path for image to be saved to.
     *
     * @throws IOException
     * @throws IllegalTypeException given destination path file extension does not match the file type.
     */
    public void exportImg(String destPath) throws IOException, IllegalTypeException {
        String format = switch (this.type) {
            case PNG -> "png"; //NON-NLS
            case JPG -> "jpg"; //NON-NLS
        };

        if (!format.equals(ContainerImage.getFileExtension(destPath)))
            throw new IllegalTypeException(bundle.getString("except.invalid_path_extension"));

        ImageIO.write(this.image, format, new File(destPath));
    }

    /**
     * Copy container image to Windows clip board. <br>
     * Works for images and text.
     * <p>
     * This may throw a warning when coping a png, see <a href="https://stackoverflow.com/a/64598247/13777031">here</a>.
     */
    public void copyToClipboard() {
        // get clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // get content and save to clipboard
        ImageSelection content = new ImageSelection(this.image);
        clipboard.setContents(content, null);
    }

    // region getter & setter

    /**
     * Setter for single pixel's rgb values of buffered image.
     *
     * @param x x value of pixel.
     * @param y y value of pixel.
     * @param r red value to be set.
     * @param g green value to be set.
     * @param b blue value to be set.
     */
    public void setARGB(int x, int y, byte r, byte g, byte b) {
        // create empty int (4 byte)
        int argbValue = 0;

        // set alpha to 100% due to library not reacting to alpha values correctly
        byte a = (byte) 255;

        // build argb value
        argbValue += (a & 0xff) << 24;   // alpha value
        argbValue += (r & 0xff) << 16;   // red value
        argbValue += (g & 0xff) << 8;    // green value
        argbValue += (b & 0xff);         // blue value

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

    /**
     * @return width
     */
    public int getWidth() {
        return this.image.getWidth();
    }

    /**
     * @return height
     */
    public int getHeight() {
        return this.image.getHeight();
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @return image
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return type
     */
    public Type getType() {
        return type;
    }

    // endregion
}
