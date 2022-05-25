package com.dhbw.secure_pic.data;

// TODO COMMENT
// TODO implement

public class ContainerImage {

    // region attributes
    private byte[] data;
    private final String path;
    private Type type;
    // endregion

    enum Type{
        PNG,
        JPEG
    }

    public ContainerImage(String path) {
        this.path = path;
        // TODO implement loading
    }

    private byte[] toBEBytes(){
        // TODO check if necessary
        return null;
    }

    private void exportImg(){
        // TODO implement exportImg
    }

    // region getter & setter
    private void setData(byte[] data){
        this.data = data;
    }
    // endregion
}
