package com.dhbw.secure_pic.gui;

import com.dhbw.secure_pic.gui.utility.FileSelect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

// FIXME comment (normal comments + JDocs) # only delete if final#

public class ImageConverter extends Component {
    private JPanel contentPane;
    private JButton secretButton;
    private JProgressBar progressBar;
    private JButton convertButton;
    private JButton uploadButton;
    private JPanel ImagePanel;

    public ImageConverter(Gui parent) {
        // region listener
        secretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // set new window title
                ((JFrame) SwingUtilities.getWindowAncestor(contentPane)).setTitle("SecurePic");
                parent.show("2");
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Handle open button action.
                File file =new FileSelect().selectFile(ImageConverter.this);   // TODO unused?
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
