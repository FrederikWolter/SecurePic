package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO comment (normal comments + JDocs) # only delete if final#

public class Gui {
    private StartChooseType chooseType = new StartChooseType();
    private StartSendChooseEncryption chooseEncryptionSend = new StartSendChooseEncryption();
    private StartReceiveChooseEncryption chooseEncryptionReceive = new StartReceiveChooseEncryption();
    private ReceiveNoEncryption receive_noEncryption = new ReceiveNoEncryption();
    private ReceiveAsymmetrical receive_asymmetrical = new ReceiveAsymmetrical();
    private ReceiveSymmetrical receive_symmetrical = new ReceiveSymmetrical();
    private SendNoEncryption send_noEncryption = new SendNoEncryption();
    private SendAsymmetrical send_asymmetrical = new SendAsymmetrical();
    private SendSymmetrical send_symmetrical = new SendSymmetrical();
    private ImageConverter ImageConverter = new ImageConverter();
    private CardLayout cl;
    private JPanel panelCont;
    private JFrame frame;

    public Gui(){
        frame = new JFrame();
        // TODO set Frame title
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(800, 500));

        cl = new CardLayout();
        panelCont = new JPanel(cl);

        // TODO make sub panels extend Jpanel to simplify this construct > https://stackoverflow.com/questions/45677253/manage-java-cardlayout-jpanels-created-with-different-classes
        // Best Practice -> Panels
        JPanel imgCon = ImageConverter.getMainPanel_ImgCon();
        JPanel typeChooser = chooseType.getMainPanel_ST();
        JPanel encryptionChooserSend = chooseEncryptionSend.getMainPanel_SCE();
        JPanel encryptionChooserReceive = chooseEncryptionReceive.getMainPanel_RCE();
        JPanel recNoEnc = receive_noEncryption.getMainPanel_RN();
        JPanel recAss = receive_asymmetrical.getMainPanel_RA();
        JPanel recSym = receive_symmetrical.getMainPanel_RS();
        JPanel sendNoEnc = send_noEncryption.getMainPanel_SN();
        JPanel sendAss = send_asymmetrical.getMainPanel_SA();
        JPanel sendSym = send_symmetrical.getMainPanel_SS();

        // TODO use static final string for identifiers
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

        // TODO do this in each class? > https://stackoverflow.com/questions/45677253/manage-java-cardlayout-jpanels-created-with-different-classes
        // nö, geht nicht wegen Card Layout
        JButton secretButton = ImageConverter.getButtonStartApp();
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
        JButton backRA = receive_asymmetrical.getBackButton();
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
