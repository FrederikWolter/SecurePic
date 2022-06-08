package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ImageConverter extends Component {
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

    public ImageConverter() {
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
                    File file =fs.SelectFile(ImageConverter.this);   // TODO unuse?

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
