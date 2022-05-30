package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class index extends Component {
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
    private JButton uploadButton;

    public index() {
        final JFileChooser fc = new JFileChooser();

        buttonStartApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //.setTitle("SecurePick");

            }
        });
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton) {
                    int returnVal = fc.showOpenDialog(index.this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        //This is where a real application would open the file.
                    } else {
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame fenster = new JFrame("Image Converter"); //neues Frame bzw. Fenster
        fenster.setContentPane(new index().MainPanelImgCon);
        fenster.setMinimumSize(new Dimension(400,200));
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.pack();
        fenster.setVisible(true);
    }
}
