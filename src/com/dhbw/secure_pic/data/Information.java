package com.dhbw.secure_pic.data;

public class Information {

    private byte[] data;
    private Enum type;

    public Information(byte[] data){
        this.data = data;
    }

    public Information(byte[] data, Enum type){
        this.data = data;
        this.type = type;
    }

    private byte[] toBeBytes(){
        return null;
    }

    private void toContent(){

    }

    private Information getInformationFromString(String text){
        return null;
    }

    private Information getInformationFromImage(String path){
        return null;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}
