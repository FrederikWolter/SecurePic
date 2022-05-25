package com.dhbw.secure_pic.data;

// TODO implement
// TODO comment

/**
 * This class implements the Information data type.
 * Information is used as the primary carrier of the sent/received messages turned to Arrays of Bytes.
 *
 * @author Kriolis Eskondis, Frederik Wolter
 */
public class Information {

    // region attributes
    private byte[] data;
    private Type type;
    // endregion

    enum Type{
        TEXT,
        IMAGE
    }

    /**
     * @param data is the sent/received message turned into an Array of bytes
     * @param type describes the type the message was before the conversion to Array e.g. text, image
     */
    private Information(byte[] data, Type type){
        this.data = data;
        this.type = type;
    }

    private Information(byte[] data){
        this.data = data;
        // TODO implement type recognition and other rwa data analysis
    }

    private byte[] toBeBytes(){ // TODO check if necessary
        return null;
    }

    private void toContent(){

    }

    public static Information getInformationFromString(String text){
        return null;
    }

    public static Information getInformationFromImage(String path){
        // TODO implement information generation
        return null;
    }

    // region getter & setter
    public void setData(byte[] data) {
        this.data = data;
    }
    // endregion
}
