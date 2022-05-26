package com.dhbw.secure_pic.data;

import java.nio.charset.StandardCharsets;

// TODO implement
// TODO comment

/**
 * This class implements the Information data type.<br>
 * Information is used as the primary carrier of the received/sent information embedded in the image.
 *
 * @author Kriolis Eskondis, Frederik Wolter
 */
public class Information {

    // region public static
    /** Length of data type in bytes. */
    public static final int TYPE_LENGTH = Integer.BYTES;
    /** Length of data length in bytes. */
    public static final int LENGTH_LENGTH = Long.BYTES;
    /** Length of all metadata total in bytes. */
    public static final int META_LENGTH = TYPE_LENGTH + LENGTH_LENGTH;
    // endregion

    // region attributes
    /** Raw data saved in information as a byte array. */
    private byte[] data;

    /** Data type of data saved in information. */
    private Type type;

    /** (Brutto) data length of data saved in information in bytes. */
    private long length;
    // endregion

    /** Enum representing the available Information types.  */
    enum Type{
        TEXT,
        IMAGE
    }

    /**
     * Plain Constructor of Information.<br>
     * Used for <i>internally</i> creating an information object for <b>sending</b> a message - hence private.
     *
     * @param data raw content data to be sent embedded in the image (without any metadata).
     * @param type type of data saved in information.
     */
    private Information(byte[] data, Type type) {
        this.data = data;
        this.type = type;
        this.length = data.length;
    }

    /**
     * Plain Constructor for Information.<br>
     * Used for <i>internally</i> creating an information object for <b>receiving</b> a message - hence private.
     *
     * @param data raw data received embedded in a image (with metadata).
     */
    private Information(byte[] data) {
        this.data = data;
        // TODO implement type recognition and other raw data analysis
    }

    private byte[] toBEBytes(){ // TODO check if necessary
        return null;
        // TODO implement type encoding into data
    }

    private void toContent(){
        // TODO multiple return types? > String or image
    }

    /**
     * Get information object from content string. Internally uses private Constructor for creating an information.
     *
     * @param text text given by the user.
     * @return created information.
     */
    public static Information getInformationFromString(String text){
        // see https://stackoverflow.com/a/18571348/13777031
        return new Information(text.getBytes(StandardCharsets.UTF_8), Type.TEXT);
    }

    public static Information getInformationFromImage(String path) {
        // TODO implement information generation
        return null;
    }

    public static Information getInformationFromData(byte[] data) {
        // TODO implement information generation
        return null;
    }

    // region getter & setter
    public void setData(byte[] data) {  // TODO change to more restrict access?
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public Type getType() {
        return type;
    }

    public long getLength() {
        return length;
    }

    // endregion
}
