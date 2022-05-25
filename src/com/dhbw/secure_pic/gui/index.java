package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class index {
    private JPanel MainPanelImgCon;
    private JButton buttonStartApp;
    private JProgressBar progressBar1;
    private JRadioButton radioButton1;
    private JRadioButton radioButton2;
    private JRadioButton radioButton3;
    private JRadioButton radioButton4;
    private JRadioButton radioButton5;
    private JRadioButton radioButton6;
    private JButton button2;

    public index() {
        buttonStartApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //.setTitle("SecurePick");

            }
        });
    }

    public static void main(String[] args) {
        JFrame fenster = new JFrame("Image Converter"); //neues Frame bzw. Fenster
        fenster.setContentPane(new index().MainPanelImgCon);
        //fenster.setSize(600, 400);
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.pack();
        fenster.setVisible(true);
    }
}
