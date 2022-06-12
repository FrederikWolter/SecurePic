package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.LoadFinishedHandler;
import com.dhbw.secure_pic.pipelines.ContainerImageLoadTask;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class SendAsymmetrical extends Component {

    // region swing attributes
    private JPanel contentPane;
    private JProgressBar progressBar;
    private JButton backButton;
    private JRadioButton imageRadio;
    private JRadioButton textRadio;
    private JTextArea messageText;
    private JButton encodeButton;
    private JButton uploadContainer;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JPanel uploadPanel;
    private JLabel showImageLabel;
    private JPanel uploadPanelMessage;
    private JButton uploadMessageImg;
    private JComboBox codeComboBox;
    private JComboBox encryptComboBox;
    private JPasswordField publicKey;
    private JLabel messageImg;
    // endregion

    // region attributes
    private transient ContainerImage containerImage;
    private transient ContainerImage contentImage;

    private final int containerImageDisplayHeight = 550;
    private final int containerImageDisplayWidth = 550;
    private final int messageImageDisplayHeight = 250;
    private final int messageImageDisplayWidth = 250;
    // endregion

    public SendAsymmetrical(Gui parent) {

        PropertyChangeListener propertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress".equals(evt.getPropertyName())) {
                    int progress = (Integer) evt.getNewValue();
                    progressBar.setValue(progress);
                }
            }
        };

        LoadFinishedHandler finishedContainerImageLoad = new LoadFinishedHandler() {
            @Override
            public void finishedContainerImageLoad(ContainerImage image) {
                containerImage = image;

                showImageLabel.setText("");
                showImageLabel.setIcon(new ImageIcon(Gui.getScaledImage(containerImage.getImage(),
                        containerImageDisplayWidth,
                        containerImageDisplayHeight)));
            }
        };

        LoadFinishedHandler finishedContentImageLoad = new LoadFinishedHandler() {
            @Override
            public void finishedContainerImageLoad(ContainerImage image) {
                contentImage = image;

                messageImg.setText("");
                messageImg.setIcon(new ImageIcon(Gui.getScaledImage(contentImage.getImage(),
                        messageImageDisplayWidth,
                        messageImageDisplayHeight)));
            }
        };

        uploadPanel.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // FIXME cleanup cast?

                    for (File file : droppedFiles) { // TODO allow multiple files? no? GENERAL
                        ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContainerImageLoad);
                        task.addPropertyChangeListener(propertyChangeListener);
                        task.execute();
                    }
                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
                encodeButton.setEnabled(true);
            }
        });
        uploadPanelMessage.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // FIXME cleanup cast?

                    for (File file : droppedFiles) {    // TODO allow multiple files? no? GENERAL
                        ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContentImageLoad);
                        task.addPropertyChangeListener(propertyChangeListener);
                        task.execute();
                    }
                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
            }
        });

        // region listeners
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.show("3");
            }
        });

        uploadContainer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(SendAsymmetrical.this);

                if(file == null){
                    return;
                }

                ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContainerImageLoad);
                task.addPropertyChangeListener(propertyChangeListener);
                task.execute();

                encodeButton.setEnabled(true);
            }
        });

        uploadMessageImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(SendAsymmetrical.this);

                if(file == null){
                    return;
                }

                ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContentImageLoad);
                task.addPropertyChangeListener(propertyChangeListener);
                task.execute();
            }
        });

        imageRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageText.setVisible(false);
                uploadPanelMessage.setVisible(true);
            }
        });

        textRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageText.setVisible(true);
                uploadPanelMessage.setVisible(false);

            }
        });

        encodeButton.addActionListener(new ActionListener() {
            //ToDo Encode Pipeline
            //ToDo load Public key from Image

            @Override
            public void actionPerformed(ActionEvent e) {

                Information info;
                Coder coder;
                Crypter crypter;

                if (containerImage == null){
                    // TODO error handling
                    return;
                }

                if (textRadio.isSelected()){
                    if (messageText.getText().length() > 0){
                        info = Information.getInformationFromString(messageText.getText());
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte gebe einen Text ein, der in das Bild codiert werden soll.");
                        return;
                    }
                } else if(imageRadio.isSelected()){
                    if (contentImage != null){
                        try {
                            info = Information.getInformationFromImage(contentImage.getPath());
                        } catch (IllegalTypeException ex) {
                            throw new RuntimeException(ex);
                            // TODO error handling
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte lade einen Bild, das in das Trägerbild codiert werden soll.");
                        return;
                    }
                } else {
                    // TODO error handling
                    return;
                }

                if (codeComboBox.getSelectedItem() == "LSB"){
                    coder = new LeastSignificantBit(containerImage);
                } else if(codeComboBox.getSelectedItem() == "PM1"){
                    coder = new PlusMinusOne(containerImage);
                } else {
                    // TODO error handling
                    return;
                }

                if (encryptComboBox.getSelectedItem() == "RSA"){
                    String publicKey = new String(publicKey.getPassword());
                    if(publicKey.length() > 0){
//                        crypter = new RSA(publicKey); // TODO get public key?
                        crypter = new AES(publicKey);
                    }else{
                        JOptionPane.showMessageDialog(null, "Bitte gebe einen Öffentlichen Schlüssel ein, mit dem die Information verschlüsselt werden soll.");
                        return;
                    }
                } else {
                    // TODO error handling
                    return;
                }

//                encodeButton.setEnabled(false);

                EncodeTask task = new EncodeTask(coder, crypter, info, new EncodeFinishedHandler() {
                    @Override
                    public void finishedEncode(ContainerImage image) {
                        contentImage = image;

                        exportButton.setEnabled(true);
                        copyToClipboardButton.setEnabled(true);
                        encodeButton.setEnabled(true);
                    }
                });
                task.addPropertyChangeListener(propertyChangeListener);
                task.execute();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new SaveSelect().selectDir(SendAsymmetrical.this);

                if(file == null){
                    return;
                }

                try {
                    containerImage.exportImg(file.getPath());
                } catch (IOException | IllegalTypeException ex) {
                    throw new RuntimeException(ex); // TODO error handling
                }
            }
        });

        copyToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                containerImage.copyToClipboard();
            }
        });

        // endregion
    }

    // region getter
    public JPanel getContentPane() {
        return contentPane;
    }
    // endregion
}
