package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;

import static javax.swing.SwingConstants.CENTER;

// TODO comment (normal comments + JDocs) # only delete if final#

public class ChooseType {
    private JButton send;
    private JButton receive;

    public JPanel getTypePanel() {
        return typePanel;
    }

    private JPanel typePanel;
    private JLabel receiveInf;
    private JLabel sendInf;
    private JPanel choosePanel;

    public JButton getSend() {
        return send;
    }

    public JButton getReceive() {
        return receive;
    }

    public ChooseType() {
        // TODO why placing swing elements in code instead of xml forms? DUPLICATE WITH Start_ Forms!

        typePanel = new JPanel();
        //frame.setTitle("Choose Type");
        typePanel.setLayout(new GridLayout(1,1));
        choosePanel = new JPanel();
        //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //frame.setSize(800,500);
        //frame.setLocationRelativeTo(null);

        send = new JButton("Send");
        receive = new JButton("Receive");
        sendInf = new JLabel("Information to Send");
        receiveInf = new JLabel("Information to Receive");

        choosePanel.setLayout(new GridLayout(2,2,10,20));
        JPanel buttonp1 = new JPanel();
        JPanel buttonp2 = new JPanel();
        buttonp1.setLayout(new GridBagLayout());
        buttonp2.setLayout(new GridBagLayout());

        send.setSize(new Dimension(200,100));
        buttonp1.add(send);
        choosePanel.add(buttonp1);
        receive.setSize(200,100);
        buttonp2.add(receive);
        choosePanel.add(buttonp2);
        sendInf.setHorizontalAlignment(CENTER);
        choosePanel.add(sendInf);
        receiveInf.setHorizontalAlignment(CENTER);
        choosePanel.add(receiveInf);

        typePanel.add(choosePanel);
    }
}
