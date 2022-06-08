package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class Gui {

    // region attributes
    private final JFrame frame;
    private final CardLayout cards;
    private final JPanel contentPane;

    private Type type;

    private JPanel imageConverterPanel = new ImageConverter(this).getMainPanel_ImgCon();
    private JPanel startChooseTypePanel =  new StartChooseType(this).getMainPanel_ST();
    private JPanel startChooserEncryptionPanel = new StartChooseEncryption(this).getMainPanel_SCE();
    private JPanel receiveNoEncryptionPanel = new ReceiveNoEncryption(this).getMainPanel_RN();
    private JPanel receiveAsymmetricalPanel = new ReceiveAsymmetrical(this).getMainPanel_RA();
    private JPanel receiveSymmetricalPanel = new ReceiveSymmetrical(this).getMainPanel_RS();
    private JPanel sendNoEncryptionPanel = new SendNoEncryption(this).getMainPanel_SN();
    private JPanel sendAsymmetricalPanel = new SendAsymmetrical(this).getMainPanel_SA();
    private JPanel sendSymmetricalPanel = new SendSymmetrical(this).getMainPanel_SS();
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

        // TODO make sub panels extend Jpanel to simplify this construct > https://stackoverflow.com/questions/45677253/manage-java-cardlayout-jpanels-created-with-different-classes

        // TODO use static final string for identifiers
        contentPane.add (imageConverterPanel, "1");
        contentPane.add (startChooseTypePanel, "2");
        contentPane.add (startChooserEncryptionPanel, "3");
        contentPane.add (receiveNoEncryptionPanel, "4");
        contentPane.add (receiveAsymmetricalPanel, "5");
        contentPane.add (receiveSymmetricalPanel, "6");
        contentPane.add (sendNoEncryptionPanel, "7");
        contentPane.add (sendAsymmetricalPanel, "8");
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