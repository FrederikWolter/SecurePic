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
        // TODO set icon?

        cards = new CardLayout(5,5);
        contentPane = new JPanel(cards);
        frame.add(contentPane);

        // TODO make sub panels extend JPanel to simplify this construct > https://stackoverflow.com/questions/45677253/manage-java-cardlayout-jpanels-created-with-different-classes

        // TODO use static final string for identifiers

        JPanel imageConverterPanel = new ImageConverter(this).getContentPane();
        contentPane.add (imageConverterPanel, "1");
        JPanel startChooseTypePanel = new StartChooseType(this).getMainPanel_ST();
        contentPane.add (startChooseTypePanel, "2");
        JPanel startChooserEncryptionPanel = new StartChooseEncryption(this).getMainPanel_SCE();
        contentPane.add (startChooserEncryptionPanel, "3");
        JPanel receiveNoEncryptionPanel = new ReceiveNoEncryption(this).getMainPanel_RN();
        contentPane.add (receiveNoEncryptionPanel, "4");
        JPanel receiveAsymmetricalPanel = new ReceiveAsymmetrical(this).getMainPanel_RA();
        contentPane.add (receiveAsymmetricalPanel, "5");
        JPanel receiveSymmetricalPanel = new ReceiveSymmetrical(this).getMainPanel_RS();
        contentPane.add (receiveSymmetricalPanel, "6");
        JPanel sendNoEncryptionPanel = new SendNoEncryption(this).getMainPanel_SN();
        contentPane.add (sendNoEncryptionPanel, "7");
        JPanel sendAsymmetricalPanel = new SendAsymmetrical(this).getMainPanel_SA();
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