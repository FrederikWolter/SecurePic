package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

// TODO comment (normal comments + JDocs) # only delete if final#

/**
 * Class to combine every GUI Window and creating the main Frame and its settings
 *
 * @author Hassan El-Khalil
 */

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

    /**
     * Constructor setting up the main Frame and combining the different windows as ContentPanes
     *
     */
    public Gui(){
        //Frame settings
        frame = new JFrame("Image Converter");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize((int) (frame.getHeight()*1.78),750);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(1200, 750));
        frame.setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/icon.jpg"))).getImage());

        //Setting the CardLayout
        cards = new CardLayout(6,6);
        contentPane = new JPanel(cards);
        frame.add(contentPane);

        // TODO make sub panels extend JPanel to simplify this construct > https://stackoverflow.com/questions/45677253/manage-java-cardlayout-jpanels-created-with-different-classes

        // TODO use static final string for identifiers
        //Adding each Frame to the JPanel
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

        frame.setVisible(true);
    }

    public void show(String name){
        cards.show(contentPane, name);
    }

    public void close(){
        frame.dispose();
    }

    // see https://stackoverflow.com/a/6714381/13777031, https://stackoverflow.com/a/10245583/13777031
    public static BufferedImage getScaledImage(BufferedImage srcImg, int maxWidth, int maxHeight){
        int originalWidth = srcImg.getWidth();
        int originalHeight = srcImg.getHeight();
        int newWidth = originalWidth;
        int newHeight = originalHeight;

        // first check if we need to scale width
        if (originalWidth > maxWidth) {
            //scale width to fit
            newWidth = maxWidth;
            //scale height to maintain aspect ratio
            newHeight = (newWidth * originalHeight) / originalWidth;
        }

        // then check if we need to scale even with the new height
        if (newHeight > maxHeight) {
            //scale height to fit instead
            newHeight = maxHeight;
            //scale width to maintain aspect ratio
            newWidth = (newHeight * originalWidth) / originalHeight;
        }

        BufferedImage resizedImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, newWidth, newHeight, null);
        g2.dispose();

        return resizedImg;
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