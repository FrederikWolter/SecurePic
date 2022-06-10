package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.Locale;
import java.util.ResourceBundle;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ReceiveAsymmetrical extends Component {
    private JPanel MainPanel_RA;
    private JButton uploadButtonKeyImage;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JButton uploadButtonConatainerImg;
    private JCheckBox encodePublicKeyIntoCheckBox;
    private JButton generateKeyButton;
    private JTextField privateKeyOutput;
    private JTextField puplicKeyOutput;
    private JTextField privateKeyInput;
    private JButton decodeButton;
    private JButton backButton;
    private JLabel KeyImg;
    private JLabel ContainerImage;
    private JPanel OutputMessage;
    private JLabel MessageOutput;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JLabel KeyImageOutput;
    private JPanel OutputKey;
    private JProgressBar progressBar1;
    private JButton CtcbKeyImage;
    private JButton exportKeyImageButton;

    final FileSelect fs = new FileSelect();
    public ReceiveAsymmetrical(Gui parent) {

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("3");
            }
        });

        uploadButtonKeyImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.SelectFile(ReceiveAsymmetrical.this);  // TODO unused?
            }
        });
        uploadButtonConatainerImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = fs.SelectFile(ReceiveAsymmetrical.this);
            }
        });
        generateKeyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ToDo Key generation
                String privateKey = "";
                String publicKey = "";
                privateKeyOutput.setText(privateKey);
                privateKeyInput.setText(privateKey);
                puplicKeyOutput.setText(publicKey);
                //ToDo Bildanzeige
            }
        });
        encodePublicKeyIntoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(encodePublicKeyIntoCheckBox.isSelected()){
                    OutputKey.setVisible(true);
                }
                else{
                    OutputKey.setVisible(false);
                }
            }
        });
    }



    public JPanel getMainPanel_RA() {
        return MainPanel_RA;
    }
    public JButton getBackButton() {
        return backButton;
    }
}
