package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.data.Information;
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

public class GuiViewReceive extends GuiView{

    // region attributes
    protected transient Information contentInformation;
    // endregion

    protected ActionListener getExportListener(Component parent){
        return e -> {
            File file = new FileSelect().selectFile(parent);

            if (file == null) return;   // if no destination selected -> simply stop export process

            try {
                if(contentInformation.getType() == Information.Type.TEXT) { // TEXT
                    new BufferedWriter(new FileWriter(file.getPath() + ".txt", true))
                        .append("\n")
                        .append(contentInformation.toText())
                        .close();
                    return; // TODO
                } else {    // IMAGE
                    ImageIO.write(contentInformation.toImage(), "png", file);   // TODO type?
                    JOptionPane.showMessageDialog(null, "Das Bild wurde erfolgreich exportiert.", "Erfolg",  JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex); // TODO error handling
            }
        };
    }

}
