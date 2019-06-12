package assignment3;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.Position;
import javax.swing.text.StyledDocument;

public class SimpleNotePad extends JFrame implements ActionListener {

    JFrame jframe = new JFrame();
    JTextPane jTxtP = new JTextPane();

    private SimpleNotePad() {

        //Refactored the items in the menu to represent betternames
        //tweaked layout of items being called to be more userfriendly
        JMenu menu1, menu2, submenu;
        JMenuItem open, print, save, newF, replace, paste, undo, copy, file1, file2, file3, file4, file5;
        file1 = new JMenuItem("File 1");
        file2 = new JMenuItem("File 2");
        file3 = new JMenuItem("File 3");
        file4 = new JMenuItem("File 4");
        file5 = new JMenuItem("File 5");

        JMenuBar mb = new JMenuBar();
        menu1 = new JMenu("File");
        menu2 = new JMenu("Edit");
        submenu = new JMenu("recent");
        open = new JMenuItem("Open");
        print = new JMenuItem("Print File");
        save = new JMenuItem("Save File");
        newF = new JMenuItem("New File");
        replace = new JMenuItem("Replace");
        paste = new JMenuItem("Paste");
        copy = new JMenuItem("Copy");
        undo = new JMenuItem("Undo");

        menu1.add(open);
        menu1.add(print);
        menu1.add(save);
        menu1.add(newF);
        submenu.add(file1);
        submenu.add(file2);
        submenu.add(file3);
        submenu.add(file4);
        submenu.add(file5);
        menu1.add(submenu);
        menu2.add(replace);
        menu2.add(paste);
        menu2.add(copy);
        menu2.add(undo);
        mb.add(menu1);
        mb.add(menu2);

        jframe.setJMenuBar(mb);

        jframe.add(new JScrollPane(jTxtP));
        jframe.setTitle("A Simple Notepad Tool");
        jframe.setPreferredSize(new Dimension(600, 600));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setVisible(true);

        newF.addActionListener((ActionListener) this);
        newF.setActionCommand("new");
        save.addActionListener((ActionListener) this);
        save.setActionCommand("save");
        print.addActionListener((ActionListener) this);
        print.setActionCommand("print");
        copy.addActionListener((ActionListener) this);

        copy.setActionCommand("copy");
        paste.addActionListener((ActionListener) this);
        paste.setActionCommand("paste");
        open.addActionListener(this);
        open.setActionCommand("Open");
        replace.addActionListener(this);
        replace.setActionCommand("Replace");
        undo.addActionListener((ActionListener) this);
        undo.setActionCommand("undo");

    }

    public void openClicked(ActionEvent e) throws FileNotFoundException {
        if (e.getActionCommand().equals("Open")) {
            FileSystem browser = new FileSystem();

            browser.getFile();

        }

    }

    //adds a replace option to load the jDialog. User can then add text and replace the selected text on the text pane
    public void replaceClicked(ActionEvent e) {
        if (e.getActionCommand().equals("Replace")) {
            JDialog replaceD = new JDialog();
            jTxtP.getSelectedText();
            JTextField replaceTF = new JTextField();
            replaceD.add(replaceTF);
            replaceD.setVisible(true);
            jTxtP.replaceSelection(replaceTF.getText());
        }

    }

    public void recentClicked(ActionEvent e) throws FileNotFoundException {
        FileSystem browser = new FileSystem();
        browser.recentFiles();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Open")) {

            try {
                openClicked(e);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(SimpleNotePad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getActionCommand().equals("new")) {
            jTxtP.setText("");
        } else if (e.getActionCommand().equals("save")) {
            File fileToWrite = null;
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showSaveDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                fileToWrite = fc.getSelectedFile();
            }
            try {
                try (PrintWriter out = new PrintWriter(new FileWriter(fileToWrite))) {
                    out.println(jTxtP.getText());
                    JOptionPane.showMessageDialog(null, "File is saved successfully...");
                }
            } catch (IOException ex) {
            }
        } else if (e.getActionCommand().equals("print")) {
            try {
                PrinterJob pjob = PrinterJob.getPrinterJob();
                pjob.setJobName("Sample Command Pattern");
                pjob.setCopies(1);
                pjob.setPrintable(new Printable() {
                    public int print(Graphics pg, PageFormat pf, int pageNum) {
                        if (pageNum > 0) {
                            return Printable.NO_SUCH_PAGE;
                        }
                        pg.drawString(jTxtP.getText(), 500, 500);
                        paint(pg);
                        return Printable.PAGE_EXISTS;
                    }
                });

                if (pjob.printDialog() == false) {
                    return;
                }
                pjob.print();
            } catch (PrinterException pe) {
                JOptionPane.showMessageDialog(null,
                        "Printer error" + pe, "Printing error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals("copy")) {
            jTxtP.copy();
        } else if (e.getActionCommand().equals("paste")) {
            StyledDocument doc = jTxtP.getStyledDocument();
            Position position = doc.getEndPosition();
            System.out.println("offset" + position.getOffset());
            jTxtP.paste();
        } else if (e.getActionCommand().equals("undo")) {

        }
    }

    public static void main(String[] args) {
        SimpleNotePad app = new SimpleNotePad();

    }

}
