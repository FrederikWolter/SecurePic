package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.coder.LeastSignificantBit;
import com.dhbw.secure_pic.coder.PlusMinusOne;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.crypter.EmptyCrypter;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.handler.DecodeFinishedHandler;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.gui.utility.handler.LoadImageFinishedHandler;
import com.dhbw.secure_pic.gui.utility.SaveSelect;
import com.dhbw.secure_pic.pipelines.ContainerImageLoadTask;
import com.dhbw.secure_pic.pipelines.DecodeTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

// TODO comment (normal comments + JDocs) # only delete if final#

// TODO show text not in label but Textarea for automatic line breaks!

public class ReceiveNoEncryption extends GuiView {
    // region swing attributes
    private JPanel contentPane;
    private JProgressBar progressBar;
    private JButton backButton;
    private JButton decodeButton;
    private JButton uploadContainerImg;
    private JButton copyToClipboardButton;
    private JButton exportButton;
    private JLabel messageOutput;
    private JComboBox codeComboBox;
    private JPanel uploadPanelContainer;
    private JLabel containerImg;
    // endregion

    // region attributes
    private transient ContainerImage containerImage;
    private transient Information contentInformation;

    // endregion

    public ReceiveNoEncryption(Gui parent) {

        LoadImageFinishedHandler finishedContainerImageLoad = new LoadImageFinishedHandler() {
            @Override
            public void finishedImageLoad(ContainerImage image) {
                containerImage = image;

                containerImg.setText("");
                containerImg.setIcon(new ImageIcon(getScaledImage(containerImage.getImage(),
                        IMAGE_WIDTH_1,
                        IMAGE_HEIGHT_4)));
            }
        };

        uploadPanelContainer.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);    // TODO cleanup cast?

                    for (File file : droppedFiles) { // TODO allow multiple files? no? GENERAL
                        ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContainerImageLoad);
                        task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                        task.execute();
                    }
                } catch (Exception ex) {    // TODO error handling?
                    ex.printStackTrace();
                }
                decodeButton.setEnabled(true);
            }
        });

        // region listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showView(Gui.View.START_CHOOSE_ENCRYPTION);
            }
        });

        uploadContainerImg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new FileSelect().selectFile(ReceiveNoEncryption.this);

                if(file == null){
                    return;
                }

                ContainerImageLoadTask task = new ContainerImageLoadTask(file.getPath(), finishedContainerImageLoad);
                task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                task.execute();

                decodeButton.setEnabled(true);
            }
        });

        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Information info;
                Coder coder;
                Crypter crypter;

                if (containerImage == null){
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

                crypter = new EmptyCrypter();

//                decodeButton.setEnabled(false);

                DecodeTask task = new DecodeTask(coder, crypter, new DecodeFinishedHandler() {
                    @Override
                    public void finishedDecode(Information info) {
                        contentInformation = info;

                        Information.Type type = info.getType();
                        if (type == Information.Type.TEXT) {
                            messageOutput.setText(info.toText());
                        } else if (type == Information.Type.IMAGE_PNG || type == Information.Type.IMAGE_GIF || type == Information.Type.IMAGE_JPG){
                            exportButton.setEnabled(true);
                            try{
                                messageOutput.setText("");
                                messageOutput.setIcon(new ImageIcon(getScaledImage(info.toImage(),
                                        IMAGE_WIDTH_5,
                                        IMAGE_HEIGHT_5)));
                            }catch (IOException e){
                                System.out.println(e);
                                // TODO error handling?
                            }
                        } else {
                            // TODO error handling?
                        }

                        copyToClipboardButton.setEnabled(true);
                        decodeButton.setEnabled(true);
                    }
                });
                task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
                task.execute();
            }
        });

        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new SaveSelect().selectDir(ReceiveNoEncryption.this);

                if(file == null){
                    return;
                }

                if(contentInformation.getType() == Information.Type.TEXT)
                    return;

                try {
                    ImageIO.write(contentInformation.toImage(), "png", file);
                    JOptionPane.showMessageDialog(null, "Das decodierte Bild wurde erfolgreich exportiert.", "Erfolg",  JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    throw new RuntimeException(ex); // TODO error handling
                }
            }
        });

        copyToClipboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    contentInformation.copyToClipboard();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                    // TODO error handing?
                }
            }
        });

        // endregion
    }

    public JPanel getContentPane() {
        return contentPane;
    }

}
