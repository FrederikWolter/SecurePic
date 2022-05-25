package com.dhbw.secure_pic.data;

/**
 * This class implements the Information data type
 * Information is used as the primary carrier of the sent/received messages turned to Arrays of Bytes
 */

// TODO implement

public class Information {

    // region attributes
    private byte[] data;
    private Type type;
    // endregion

    enum Type{
        TEXT,
        IMAGE
    }

    private Information(byte[] data){
        this.data = data;
        // TODO implement type recognition and other rwa data analysis
    }

    private Information(byte[] data, Type type){
        this.data = data;
        this.type = type;
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
