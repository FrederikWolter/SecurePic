package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.functions.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO rename to a more descriptive name

public class index extends Component {
    private JPanel MainPanel_ImgCon;
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
    private JPanel ImagePanel;

    public index() {
        final FileSelect fs = new FileSelect();

        buttonStartApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //.setTitle("SecurePic");

            }
        });
        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                if (e.getSource() == uploadButton) {
                    File file =fs.SelectFile(index.this);   // TODO unused statement

                }
            }
        });
    }

    public JPanel getMainPanel_ImgCon() {
        return MainPanel_ImgCon;
    }

    public JButton getButtonStartApp(){
        return buttonStartApp;
    }
}
