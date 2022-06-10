package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;


// FIXME comment (normal comments + JDocs) # only delete if final#

public class Gui {

    // region attributes
    private final JFrame frame;
    private final CardLayout cards;
    private final JPanel contentPane;

    private Type type;
    // endregion

    public enum Type{
        SEND, RECEIVE
    }

    public Gui(){
        frame = new JFrame("Image Converter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setIconImage(new ImageIcon("SecurePic/com/dhbw/secure_pic/gui/images/icon.jpg").getImage());

        cards = new CardLayout(5,5);
        contentPane = new JPanel(cards);
        frame.add(contentPane);

        // TODO make sub panels extend JPanel to simplify this construct > https://stackoverflow.com/questions/45677253/manage-java-cardlayout-jpanels-created-with-different-classes

        // TODO use static final string for identifiers

        JPanel imageConverterPanel = new ImageConverter(this).getContentPane();
        contentPane.add (imageConverterPanel, "1");
        JPanel startChooseTypePanel = new StartChooseType(this).getContentPane();
        contentPane.add (startChooseTypePanel, "2");
        JPanel startChooserEncryptionPanel = new StartChooseEncryption(this).getContentPane();
        contentPane.add (startChooserEncryptionPanel, "3");
        JPanel receiveNoEncryptionPanel = new ReceiveNoEncryption(this).getContentPane();
        contentPane.add (receiveNoEncryptionPanel, "4");
        JPanel receiveAsymmetricalPanel = new ReceiveAsymmetrical(this).getContentPane();
        contentPane.add (receiveAsymmetricalPanel, "5");
        JPanel receiveSymmetricalPanel = new ReceiveSymmetrical(this).getContentPane();
        contentPane.add (receiveSymmetricalPanel, "6");
        JPanel sendNoEncryptionPanel = new SendNoEncryption(this).getContentPane();
        contentPane.add (sendNoEncryptionPanel, "7");
        JPanel sendAsymmetricalPanel = new SendAsymmetrical(this).getContentPane();
        contentPane.add (sendAsymmetricalPanel, "8");
        JPanel sendSymmetricalPanel = new SendSymmetrical(this).getContentPane();
        contentPane.add (sendSymmetricalPanel, "9");

        //receiveOptions.setBackground(Color.PINK); // TODO remove test code
        //panelCont.add (receiveOptions, "3");

        frame.setVisible(true);
    }

    public void show(String name){
        cards.show(contentPane, name);
    }

    public void close(){
        frame.dispose();
    }

    // region getter & setter
    public void setType(Type type){
        this.type = type;
    }

    public Type getType(){
        return this.type;
    }
    // endregion
}