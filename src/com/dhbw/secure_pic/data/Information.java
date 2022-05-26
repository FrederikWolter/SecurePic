package com.dhbw.secure_pic.data;

// TODO implement
// TODO comment

/**
 * This class implements the Information data type.
 * Information is used as the primary carrier of the received/sent information embedded in the image.
 *
 * @author Kriolis Eskondis, Frederik Wolter
 */
public class Information {

    public static final int TYPE_LENGTH = Integer.BYTES;
    public static final int LENGTH_LENGTH = Long.BYTES;
    public static final int META_LENGTH = TYPE_LENGTH + LENGTH_LENGTH;

    // region attributes
    /**
     * Raw data saved in information as a byte array.
     */
    private byte[] data;

    /**
     * Data type of data saved in information.
     */
    private Type type;

    /**
     * (Brutto) data length of data saved in information in bytes.
     */
    private long length;
    // endregion

    /**
     * Enum representing the available Information types.
     */
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
    private Information(byte[] data, Type type){
        this.data = data;
        // TODO implement type encoding into data
        this.type = type;
        // TODO implement length analysis
    }

    /**
     * Plain Constructor for Information.<br>
     * Used for <i>internally</i> creating an information object for <b>receiving</b> a message - hence private.
     *
     * @param data raw data received embedded in a image (with metadata).
     */
    private Information(byte[] data){
        this.data = data;
        // TODO implement type recognition and other raw data analysis
    }

    private byte[] toBeBytes(){ // TODO check if necessary
        return null;
    }

    private void toContent(){

    }

    public static Information getInformationFromString(String text){
        // TODO implement information generation
        return null;
    }

    public static Information getInformationFromImage(String path){
        // TODO implement information generation
        return null;
    }

    public static Information getInformationFromData(byte[] data){
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
