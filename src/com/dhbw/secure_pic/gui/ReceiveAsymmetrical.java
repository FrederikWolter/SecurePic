package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// FIXME comment (normal comments + JDocs) # only delete if final#
//ToDo ganzes Form überarbeiten und nochmal aktualisieren

public class ReceiveAsymmetrical extends Component {
    private JPanel contentPane;
    private JLabel descrPblImg;
    private JLabel descrRecImg;
    private JButton uploadButtonKeyImage;
    private JComboBox comboBox_CodAlg;
    private JComboBox comboBox_EncAlg;
    private JButton uploadButtonConatainerImg;
    private JCheckBox encodePublicKeyIntoCheckBox;
    private JButton generateKeyButton;
    private JTextField privateKeyOutput;
    private JTextField publicKeyOutput;
    private JTextField privateKeyInput;
    private JButton decodeButton;
    private JButton backButton;
    private JLabel KeyImg;
    private JLabel ContainerImage;
    private JPanel OutputMessage;
    private JLabel MessageOutput;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JLabel OutputKeyImage;
    private JButton KeyExport;
    private JButton ctcbKey;
    private JLabel KeyImageOutput;
    private JProgressBar progressBar;
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
                //Handle open button action.
                File file = new FileSelect().selectFile(ReceiveAsymmetrical.this);
                // TODO use load task for that
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                KeyImg.setText("");
                KeyImg.setIcon(imageIcon);
            }
        });
        uploadButtonConatainerImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                File file = new FileSelect().selectFile(ReceiveAsymmetrical.this);
                // TODO use load task for that
                BufferedImage bufferedImage = null;
                try {
                    bufferedImage = ImageIO.read(file);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                ContainerImage.setText("");
                ContainerImage.setIcon(imageIcon);
                decodeButton.setEnabled(true);
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
                publicKeyOutput.setText(publicKey);
                exportKeyImageButton.setEnabled(true);
                ctcbKey.setEnabled(true);
                //ToDo Bildanzeige
            }
        });
        encodePublicKeyIntoCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(encodePublicKeyIntoCheckBox.isSelected()){
                    KeyImg.setVisible(true);
                    uploadButtonKeyImage.setVisible(true);
                    OutputKeyImage.setVisible(true);
                    descrPblImg.setVisible(true);
                }
                else{
                    KeyImg.setVisible(false);
                    uploadButtonKeyImage.setVisible(false);
                    OutputKeyImage.setVisible(false);
                    descrPblImg.setVisible(false);
                }
            }
        });
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
