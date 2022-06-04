package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO comment (normal comments + JDocs) # only delete if final#

public class Collected {
    private Start_ChooseType chooseType = new Start_ChooseType();
    private Start_SendChooseEncryption chooseEncryptionSend = new Start_SendChooseEncryption();
    private Start_ReceiveChooseEncryption chooseEncryptionReceive = new Start_ReceiveChooseEncryption();
    private Receive_NoEncryption receive_noEncryption = new Receive_NoEncryption();
    private Receive_Assymmetrical receive_assymmetrical = new Receive_Assymmetrical();
    private Receive_Symmetrical receive_symmetrical = new Receive_Symmetrical();
    private Send_NoEncryption send_noEncryption = new Send_NoEncryption();
    private Send_Asymmetrical send_asymmetrical = new Send_Asymmetrical();
    private Send_Symmetrical send_symmetrical = new Send_Symmetrical();
    private index index = new index();
    private CardLayout cl;
    private JPanel panelCont;
    private JFrame frame;

    public Collected(){
        frame = new JFrame();
        panelCont = new JPanel();
        cl = new CardLayout();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(800, 500));
        panelCont.setLayout(cl);

        // TODO Simplify to component instead of jpanel? should work
        JPanel imgCon = index.getMainPanel_ImgCon();
        JPanel typeChooser = chooseType.getMainPanel_ST();
        JPanel encryptionChooserSend = chooseEncryptionSend.getMainPanel_SCE();
        JPanel encryptionChooserReceive = chooseEncryptionReceive.getMainPanel_RCE();
        JPanel recNoEnc = receive_noEncryption.getMainPanel_RN();
        JPanel recAss = receive_assymmetrical.getMainPanel_RA();
        JPanel recSym = receive_symmetrical.getMainPanel_RS();
        JPanel sendNoEnc = send_noEncryption.getMainPanel_SN();
        JPanel sendAss = send_asymmetrical.getMainPanel_SA();
        JPanel sendSym = send_symmetrical.getMainPanel_SS();



        panelCont.add (imgCon, "1");
        panelCont.add (typeChooser, "2");
        panelCont.add (encryptionChooserSend, "3");
        panelCont.add (encryptionChooserReceive, "4");
        panelCont.add (recNoEnc, "5");
        panelCont.add (recAss, "6");
        panelCont.add (recSym, "7");
        panelCont.add (sendNoEnc, "8");
        panelCont.add (sendAss, "9");
        panelCont.add (sendSym, "10");
        //receiveOptions.setBackground(Color.PINK);
        //panelCont.add (receiveOptions, "3");

        frame.add(panelCont);
    }

    public void show(){
        frame.setVisible(true);

        // TODO do this in each class?
        JButton secretButton = index.getButtonStartApp();
        JButton typeSend = chooseType.getSendButton();
        JButton typeReceive = chooseType.getReceiveButton();
        JButton typeClose = chooseType.getCloseButton();
        JButton recNoEnc = chooseEncryptionReceive.getNoEncryptionButton();
        JButton recAss = chooseEncryptionReceive.getAsymmetricalButton();
        JButton recSym = chooseEncryptionReceive.getSymmetricalButton();
        JButton recBack = chooseEncryptionReceive.getBackButton();
        JButton sendNoEnc = chooseEncryptionSend.getNoEncryptionButton();
        JButton sendAss = chooseEncryptionSend.getAsymmetricalButton();
        JButton sendSym = chooseEncryptionSend.getSymmetricalButton();
        JButton sendBack = chooseEncryptionSend.getBackButton();
        JButton backRN = receive_noEncryption.getBackButton();
        JButton backRA = receive_assymmetrical.getBackButton();
        JButton backRS = receive_symmetrical.getBackButton();
        JButton backSN = send_noEncryption.getBackButton();
        JButton backSA = send_asymmetrical.getBackButton();
        JButton backSS = send_symmetrical.getBackButton();

        secretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "2");
            }
        });
        typeSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont,"3");
            }
        });
        typeReceive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont,"4");
            }
        });
        typeClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        recNoEnc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "5");
            }
        });
        recAss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "6");
            }
        });
        recSym.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "7");
            }
        });
        recBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "2");
            }
        });
        sendNoEnc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "8");
            }
        });
        sendAss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "9");
            }
        });
        sendSym.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "10");
            }
        });
        sendBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "2");
            }
        });
        backRN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "4");
            }
        });
        backRA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "4");
            }
        });
        backRS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "4");
            }
        });
        backSN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "3");
            }
        });
        backSA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "3");
            }
        });
        backSS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "3");
            }
        });
    }
}
