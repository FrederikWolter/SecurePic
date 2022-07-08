package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.data.Information;
import com.dhbw.secure_pic.gui.utility.FileFilter;
import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// TODO comment

public class GuiViewReceive extends GuiView {

    // region attributes
    protected transient Information contentInformation;
    // endregion

    protected ActionListener getExportInformationListener(Component parent) {
        return e -> {
            FileFilter filter;
            if (contentInformation.getType() == Information.Type.TEXT) {
                filter = new FileFilter(new FileFilter.Extension[]{
                        FileFilter.Extension.TXT
                });
            } else {
                filter = new FileFilter(new FileFilter.Extension[]{
                        FileFilter.Extension.PNG
                });
            }

            File file = new FileSelect().select(parent, true, filter);

            if (file == null) return;   // if no destination selected -> simply stop export process

            try {
                if (contentInformation.getType() == Information.Type.TEXT) { // TEXT
                    new BufferedWriter(new FileWriter(file.getPath(), true))   // TODO missing extension autocomplete?
                            .append("\n")
                            .append(contentInformation.toText())
                            .close();
                } else {    // IMAGE
                    ImageIO.write(contentInformation.toImage(), "png", file);   // TODO type?
                    JOptionPane.showMessageDialog(null, "Das Bild wurde erfolgreich exportiert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex); // TODO error handling
            }
        };
    }

}
