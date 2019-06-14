/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment3;

import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

/**
 *
 * @author jsaunders
 */
public class FileSystem extends JFrame {

    //addclass for creating files
    JDialog jdialog = new JDialog();
    File file;

    public JFileChooser addJFileChooser(JFileChooser fileChooser) {
        jdialog.add(fileChooser);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        return fileChooser;

    }

    public void checkForAction(JMenuItem item) {
        item.addItemListener((e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {

            }
        });

    }

    public File getFile() throws FileNotFoundException {
        JFileChooser picker = new JFileChooser();
        picker = addJFileChooser(picker);

        int status = picker.showOpenDialog(this);
        if (status == picker.APPROVE_OPTION) {
            file = picker.getSelectedFile();
            return file;
        } else if (status == picker.CANCEL_OPTION) {
            System.out.println("User Canceled File Selection");
        }
        return null;

    }

    public String recentFiles() throws FileNotFoundException {

        if (file.exists()) {
            String fileName = file.getPath();
            System.out.println(fileName + "\n" + file.getName());
            return fileName;
        } else {
            System.out.println("File doesn't exist");
        }
        return null;

    }

}
