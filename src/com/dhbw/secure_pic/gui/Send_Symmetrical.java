package com.dhbw.secure_pic.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Send_Symmetrical extends Component {
    private JPanel MainPanel_SS;
    private JProgressBar progressBar1;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JButton backButton;
    private JRadioButton imageRadioButton;
    private JRadioButton textmessageRadioButton;
    private JTextArea textArea1;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JPasswordField passwordField1;
    private JButton encodeButton;
    private JSlider slider1;
    private JButton uploadButton2;
    private JButton uploadButton;

    final JFileChooser fc = new JFileChooser();

    public Send_Symmetrical() {
        uploadButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton2) {
                    int returnVal = fc.showOpenDialog(Send_Symmetrical.this);

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
        fenster.setContentPane(new Send_Symmetrical().MainPanel_SS);
        fenster.setMinimumSize(new Dimension(400,200));
        fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenster.pack();
        fenster.setVisible(true);
    }
}
