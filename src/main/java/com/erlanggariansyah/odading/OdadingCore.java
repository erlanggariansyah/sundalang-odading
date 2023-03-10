/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.erlanggariansyah.odading;
import com.erlanggariansyah.odading.sundalangcore.SundaLang;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;

/**
 *
 * @author Erlangga Riansyah
 */
public class OdadingCore extends JFrame implements ActionListener, WindowListener {
    private static final String COMMIT_ACTION = "commit";
    JTextArea jTextArea = new JTextArea();
    JTextArea jLineArea = new JTextArea("1");
    File fileNameContainer;
    SundaLang sundaLang = new SundaLang();

    public OdadingCore() {
        Font font = new Font("Consolas", Font.PLAIN, 16);
        Font fontBold = new Font("Consolas", Font.BOLD, 15);
        Container container = getContentPane();

        JMenuBar jMenuBar = new JMenuBar();
        JMenu jFile = new JMenu("File");
        JMenu jEdit = new JMenu("Edit");
        JMenu jRun = new JMenu("Run");
        JMenu jSyntax = new JMenu("Syntax");
        JMenu jWindow = new JMenu("Window");
        JMenu jAbout = new JMenu("About");

        container.setLayout(new BorderLayout());

        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVisible(true);

        jTextArea.setFont(fontBold);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setMargin(new Insets(10, 10, 0, 10));
        jTextArea.setLineWrap(true);
        jTextArea.setBackground(Color.decode("#252526"));
        jTextArea.setForeground(Color.WHITE);
        jTextArea.setCaretColor(Color.WHITE);
        jTextArea.setFocusTraversalKeysEnabled(false);

        // AUTO COMPLETE
        List<String> keywords = sundaLang.getSyntax();
        Autocomplete autoComplete = new Autocomplete(jTextArea, keywords);
        jTextArea.getDocument().addDocumentListener(autoComplete);
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke("TAB"), COMMIT_ACTION);
        jTextArea.getActionMap().put(COMMIT_ACTION, autoComplete.new CommitAction());
        //

        container.add(jScrollPane);

        createMenuItem(jFile, "New");
        createMenuItem(jFile, "Open");
        createMenuItem(jFile, "Save");
        jFile.addSeparator();
        createMenuItem(jFile, "Exit");

        createMenuItem(jEdit, "Cut");
        createMenuItem(jEdit, "Copy");
        createMenuItem(jEdit, "Paste");

        createMenuItem(jSyntax, "Syntax List");

        createMenuItem(jRun, "Run as Java");
        createMenuItem(jRun, "Run as SundaLang");
        jRun.addSeparator();
        createMenuItem(jRun, "Convert");

        createMenuItem(jAbout, "SundaLang");

        jMenuBar.add(jFile);
        jMenuBar.add(jEdit);
        jMenuBar.add(jSyntax);
        jMenuBar.add(jRun);
        jMenuBar.add(jWindow);
        jMenuBar.add(jAbout);

        setJMenuBar(jMenuBar);
        //	setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
        addWindowListener(this);
        setSize(1280, 720);
        setTitle("Test.westjava - Odading");

        jLineArea.setBackground(Color.decode("#3E3E42"));
        jLineArea.setForeground(Color.LIGHT_GRAY);
        jLineArea.setEditable(false);
        jLineArea.setFont(font);
        jLineArea.setColumns(5);
        jLineArea.setSelectedTextColor(Color.BLACK);
        jLineArea.setMargin(new Insets(10, 5,0,0));

        jTextArea.getDocument().addDocumentListener(new DocumentListener() {
            public String getText() {
                int caretPosition = jTextArea.getDocument().getLength();
                Element root = jTextArea.getDocument().getDefaultRootElement();
                StringBuilder text = new StringBuilder("1" + System.getProperty("line.separator"));

                for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
                    text.append(i).append(System.getProperty("line.separator"));
                }

                return text.toString();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                jLineArea.setText(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                jLineArea.setText(getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                jLineArea.setText(getText());
            }
        });

        jScrollPane.setRowHeaderView(jLineArea);

        setVisible(true);
    }

    public void createMenuItem(JMenu jMenu, String txt) {
        JMenuItem jMenuItem = new JMenuItem(txt);
        jMenuItem.addActionListener(this);

        jMenu.add(jMenuItem);
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc = new JFileChooser();

        if (e.getActionCommand().equals("New")) {
            // NEW COMMAND
            this.setTitle("Test.westjava - Odading");
            jTextArea.setText("");
            fileNameContainer = null;
        } else if (e.getActionCommand().equals("Open")) {
            // OPEN COMMAND
            int ret = jfc.showDialog(null, "Open");

            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    File fyl = jfc.getSelectedFile();
                    OpenFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName() + " - Odading");

                    fileNameContainer = fyl;
                } catch (IOException ignored) {}
            }
        } else if (e.getActionCommand().equals("Save")) {
            // SAVE COMMAND
            if (fileNameContainer != null) {
                jfc.setCurrentDirectory(fileNameContainer);
                jfc.setSelectedFile(fileNameContainer);
            } else {
                // jfc.setCurrentDirectory(new File("."));
                jfc.setSelectedFile(new File("Test.westjava"));
            }

            int ret = jfc.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    File fyl = jfc.getSelectedFile();
                    SaveFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName() + " - Odading");
                    fileNameContainer = fyl;
                } catch (Exception ignored) {}
            }

        } else if (e.getActionCommand().equals("Exit")) {
            // EXIT COMMAND
            Exiting();
        } else if (e.getActionCommand().equals("Copy")) {
            // COPT COMMAND
            jTextArea.copy();
        } else if (e.getActionCommand().equals("Paste")) {
            // PASTE COMMAND
            jTextArea.paste();
        } else if (e.getActionCommand().equals("Cut")) {
            // CUT COMMAND
            jTextArea.cut();
        } else if (e.getActionCommand().equals("Syntax List")) {
            // SYNTAX LIST
        } else if (e.getActionCommand().equals("SundaLang")) {
            // ABOUT COMMAND
            JOptionPane.showMessageDialog(this, "Created by Sundanese. Thanks for Baeldung, StackOverflow, Geeksforgeeks & Pecinta Komputer Indonesia.", "Odading", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getActionCommand().equals("Run as SundaLang")) {
            StringBuilder result = new StringBuilder();
            String code;

            try {
                code = sundaLang.convert(jTextArea.getText());
                sundaLang.generateJava(code);
                String command = "cmd /C \"" + "javac SundaLangOutput.java && java SundaLangOutput" + "\"";

                Process process = Runtime.getRuntime().exec(command);
                InputStream inputStream = process.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                    result.append(line);
                }

                JOptionPane.showMessageDialog(this, result, "Odading", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals("Convert")) {
            try {
                sundaLang.generateJava(jTextArea.getText());
                JOptionPane.showMessageDialog(this, "Convert Success: SundaLangOutput.java.", "Odading", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void OpenFile(String fname) throws IOException {
        // OPEN FILE OPERATIONS
        BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
        String l;

        // CLEAR TEXT AREA
        jTextArea.setText("");
        setCursor(new Cursor(Cursor.WAIT_CURSOR));

        while ((l = d.readLine()) != null) {
            jTextArea.setText(jTextArea.getText() + l + "\r\n");
        }

        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        d.close();
    }

    public void SaveFile(String fname) throws IOException {
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream o = new DataOutputStream(new FileOutputStream(fname));
        o.writeBytes(jTextArea.getText());
        o.close();

        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void windowDeactivated(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {
        Exiting();
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    public void Exiting() {
        System.exit(0);
    }
}
