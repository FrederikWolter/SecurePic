package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.coder.Coder;
import com.dhbw.secure_pic.crypter.Crypter;
import com.dhbw.secure_pic.crypter.RSA;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.FileFilter;
import com.dhbw.secure_pic.gui.utility.FileSelect;
import com.dhbw.secure_pic.pipelines.EncodeTask;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 * This class represents the superclass of all views shown in the context of the Sending-Tab  .<br>
 * It inherits the {@link GuiView} class
 *
 * @author Frederik Wolter
 */
public class GuiViewSend extends GuiView {

    // region attributes
    /** get resource bundle managing strings */
    private static final ResourceBundle bundle = ResourceBundle.getBundle(Gui.LOCALE_PATH, new Locale(Gui.LOCALE));
    protected transient ContainerImage contentImage;
    // endregion

    protected static ActionListener getInformationTypeListener(int visible, JScrollPane text, JPanel image) {
        return e -> {
            text.setVisible(false);
            image.setVisible(false);

            if (visible == 1) {
                image.setVisible(true);
            } else {
                text.setVisible(true);
            }
        };
    }

    protected ActionListener getExportImageListener(Component parent) {
        return e -> {
            File file = new FileSelect().select(parent, true, new FileFilter(new FileFilter.Extension[]{
                    FileFilter.Extension.PNG // TODO ?
            }));

            if (file == null) return;   // if no destination selected -> simply stop export process

            try {
                containerImage.exportImg(file.getPath());
                JOptionPane.showMessageDialog(null, bundle.getString("popup.msg.export_success"), bundle.getString("popup.title.success"), JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | IllegalTypeException ex) {
                JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("popup.msg.image_save_error"), ex.getMessage()), bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
            }
        };
    }

    protected ActionListener getEncodeListener(JRadioButton textRadio, JRadioButton imageRadio, JTextArea messageText,
                                               JComboBox<String> codeComboBox, JComboBox<String> encryptComboBox,
                                               JPasswordField passwordField, JButton exportButton,
                                               JButton copyToClipboardButton, JButton encodeButton, JProgressBar progressBar) {
        return e -> {
            if (containerImage == null) {
                JOptionPane.showMessageDialog(null, bundle.getString("popup.msg.no_valid_container_img"), bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }

            Information info;
            Coder coder = getCoder(codeComboBox, containerImage);
            Crypter crypter = getCrypter(encryptComboBox, passwordField, RSA.keyType.PUBLIC);

            if (coder == null) return;      // error massage done in getCoder
            if (crypter == null) return;    // error massage done in getCrypter


            if (textRadio.isSelected()) {
                if (messageText.getText().length() > 0) {
                    info = Information.getInformationFromString(messageText.getText());
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("popup.msg.error_no_text"), bundle.getString("popup.title.warning"), WARNING_MESSAGE);
                    return;
                }
            } else if (imageRadio.isSelected()) {
                if (contentImage != null) {
                    try {
                        info = Information.getInformationFromImage(contentImage.getPath());
                    } catch (IllegalTypeException ex) {
                        JOptionPane.showMessageDialog(null, MessageFormat.format(bundle.getString("popup.msg.error_image_load"), ex.getMessage()), bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, bundle.getString("popup.msg.error_no_container"), bundle.getString("popup.title.warning"), WARNING_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(null, bundle.getString("popup.msg.error_no_selection"), bundle.getString("popup.title.error"), JOptionPane.ERROR_MESSAGE);
                return;
            }

//            encodeButton.setEnabled(false);

            EncodeTask task = new EncodeTask(coder, crypter, info, image -> {
                containerImage = image;

                exportButton.setEnabled(true);
                copyToClipboardButton.setEnabled(true);
                encodeButton.setEnabled(true);
            });
            task.addPropertyChangeListener(getPropertyChangeListener(progressBar));
            task.execute();
        };
    }

}
