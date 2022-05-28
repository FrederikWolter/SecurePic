package com.dhbw.secure_pic.data;

// TODO COMMENT
// TODO implement

/**
 * This class implements the functionality of the ContainerImage.<br>
 * The ContainerImage describes the image the message will be encoded to/decoded from.
 *
 * @author Kirolis Eskondis, Frederik Wolter
 */
public class ContainerImage {

    // region attributes
    private final String path;
    private byte[] data;
    private Type type;
    // endregion

    enum Type {
        PNG,
        JPEG
    }

    public ContainerImage(String path) {
        this.path = path;
        // TODO implement loading
    }

    /**
     * Utility method extracting file extension from path.
     *
     * @param path with file extension to be extracted.
     * @return file extension
     */
    public static String getFileExtension(String path) {
        String extension = "";
        int i = path.lastIndexOf('.');
        if (i > 0) extension = path.substring(i+1);

        return extension;
    }

    private byte[] toBEBytes() {
        // TODO check if necessary
        return null;
    }

    private void exportImg() {
        // TODO implement exportImg
    }

    // region getter & setter
    private void setData(byte[] data) {
        this.data = data;
    }
    // endregion
}
