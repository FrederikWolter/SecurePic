package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

public class ChooseEncryptionSend {
    private JButton noEncryption;
    private JButton symmetric;
    private JButton asymmetric;
    private JPanel encryptionPanel;
    private JLabel noEncryptionInf;
    private JLabel symmetricInf;
    private JLabel asymmetricInf;
    private JPanel choosePanel;

    public JButton getNoEncryption() {
        return noEncryption;
    }

    public JButton getSymmetric() {
        return symmetric;
    }

    public JButton getAsymmetric() {
        return asymmetric;
    }

    public ChooseEncryptionSend(){
        encryptionPanel = new JPanel();
        choosePanel = new JPanel();
        noEncryption = new JButton("No Encryption");
        symmetric = new JButton("Symmetric");
        asymmetric = new JButton("Asymmetric");
        noEncryptionInf = new JLabel("Send information no Encryption");
        symmetricInf = new JLabel("Send Information symmetric");
        asymmetricInf = new JLabel("Send Information asymmetric");

        //frame.setTitle("Choose Type");
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setSize(800,500);
        //frame.setLocationRelativeTo(null);

        choosePanel.setLayout(new GridLayout(2,3, 10, 10));
        JPanel buttonp1 = new JPanel();
        JPanel buttonp2 = new JPanel();
        JPanel buttonp3 = new JPanel();
        buttonp1.setLayout(new GridBagLayout());
        buttonp2.setLayout(new GridBagLayout());
        buttonp3.setLayout(new GridBagLayout());

        noEncryption.setSize(new Dimension(200,100));
        buttonp1.add(noEncryption);
        choosePanel.add(buttonp1);
        symmetric.setSize(new Dimension(200,100));
        buttonp2.add(symmetric);
        choosePanel.add(buttonp2);
        asymmetric.setSize(new Dimension(200,100));
        buttonp3.add(asymmetric);
        choosePanel.add(buttonp3);
        noEncryptionInf.setHorizontalAlignment(CENTER);
        choosePanel.add(noEncryptionInf);
        asymmetricInf.setHorizontalAlignment(CENTER);
        choosePanel.add(asymmetricInf);
        symmetricInf.setHorizontalAlignment(CENTER);
        choosePanel.add(symmetricInf);

        encryptionPanel.add(choosePanel);
    }

    public JPanel getChoosePanel() {
        return choosePanel;
    }
}
