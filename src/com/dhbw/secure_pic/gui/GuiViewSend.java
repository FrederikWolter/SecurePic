package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.auxiliary.exceptions.IllegalTypeException;
import com.dhbw.secure_pic.data.ContainerImage;
import com.dhbw.secure_pic.gui.utility.FileFilter;
import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// TODO comment

public class GuiViewSend extends GuiView {

    // region attributes
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
                JOptionPane.showMessageDialog(null, "Das Bild wurde erfolgreich exportiert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException | IllegalTypeException ex) {
                throw new RuntimeException(ex); // TODO error handling
            }
        };
    }

}
