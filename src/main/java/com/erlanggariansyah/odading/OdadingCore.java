/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.erlanggariansyah.odading;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Lenovo
 */
public class OdadingCore extends JFrame implements ActionListener, WindowListener {
    JTextArea jTextArea = new JTextArea();
    File fileNameContainer;
    
    public OdadingCore() {
        Font font = new Font("Arial", Font.PLAIN, 15);
        Container container = getContentPane();
        JMenuBar jMenuBar = new JMenuBar();
        JMenu jFile = new JMenu("File");
        JMenu jEdit = new JMenu("Edit");
        JMenu jRun = new JMenu("Run");
        JMenu jExample = new JMenu("Example");
        
        container.setLayout(new BorderLayout());
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setVisible(true);
        
        jTextArea.setFont(font);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        
        container.add(jScrollPane);
        
        createMenuItem(jFile, "New");
        createMenuItem(jFile, "Open");
        createMenuItem(jFile, "Save");
        jFile.addSeparator();
	createMenuItem(jFile,"Exit");
        
	createMenuItem(jEdit,"Cut");
	createMenuItem(jEdit,"Copy");
	createMenuItem(jEdit,"Paste");
		
	createMenuItem(jExample, "Syntax Example");
        
        createMenuItem(jRun, "Run");
	
        jMenuBar.add(jFile);
        jMenuBar.add(jEdit);
	jMenuBar.add(jExample);
        jMenuBar.add(jRun);
		
	setJMenuBar(jMenuBar);
//	setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
	addWindowListener(this);
	setSize(500,500);
	setTitle("Untitled.txt - Notepad");
				
	setVisible(true);
    }
    
    public void createMenuItem(JMenu jMenu, String txt) {
	JMenuItem jMenuItem = new JMenuItem(txt);
	jMenuItem.addActionListener(this);
	
        jMenu.add(jMenuItem);
    }
    
    public void actionPerformed(ActionEvent e){	
	JFileChooser jfc = new JFileChooser();
		
	if (e.getActionCommand().equals("New")) { 
            // NEW COMMAND
            this.setTitle("Untitled.txt - Notepad");
            jTextArea.setText("");
            fileNameContainer = null;
	} else if (e.getActionCommand().equals("Open")) {
            // OPEN COMMAND
            int ret = jfc.showDialog(null,"Open");
			
            if (ret == JFileChooser.APPROVE_OPTION) {
		try {
                    File fyl = jfc.getSelectedFile();
                    OpenFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName()+ " - Notepad");
                    
                    fileNameContainer = fyl;
		} catch (IOException ers) {}
            }		
	} else if (e.getActionCommand().equals("Save")) {
            // SAVE COMMAND
            if (fileNameContainer != null) {
		jfc.setCurrentDirectory(fileNameContainer);		
                jfc.setSelectedFile(fileNameContainer);
            } else {
                // jfc.setCurrentDirectory(new File("."));
		jfc.setSelectedFile(new File("Untitled.txt"));
            }
			
            int ret = jfc.showSaveDialog(null);	
            if (ret == JFileChooser.APPROVE_OPTION) {
		try {		
                    File fyl = jfc.getSelectedFile();
                    SaveFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName()+ " - Notepad");
                    fileNameContainer = fyl;
		} catch (Exception ers2) {}
            }
			
	} else if(e.getActionCommand().equals("Exit")) {
            // EXIT COMMAND
            Exiting();
	} else if (e.getActionCommand().equals("Copy")) {
            // COPT COMMAND
            jTextArea.copy();
	} else if (e.getActionCommand().equals("Paste")) {
            // PASTE COMMAND
            jTextArea.paste();
	} else if (e.getActionCommand().equals("Syntax Example")) { 
            // ABOUT COMMAND
            JOptionPane.showMessageDialog(this,"Thanks for Baeldung, StackOverflow & Geeksforgeeks.","Notepad",JOptionPane.INFORMATION_MESSAGE);
	} else if (e.getActionCommand().equals("Cut")) {
            // CUT COMMAND
            jTextArea.cut();
	}
    }
	
    public void OpenFile(String fname) throws IOException {	
	// OPEN FILE OPERATIONS
	BufferedReader d = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
	String l;
	
        // CLEAR TEXT AREA
	jTextArea.setText("");
	setCursor(new Cursor(Cursor.WAIT_CURSOR));
        
	while ((l=d.readLine())!= null) {
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
    public void windowDeactivated(WindowEvent e){}
    @Override
    public void windowActivated(WindowEvent e){}
    @Override
    public void windowDeiconified(WindowEvent e){}
    @Override
    public void windowIconified(WindowEvent e){}
    @Override
    public void windowClosed(WindowEvent e){}
    @Override
    public void windowClosing(WindowEvent e) {
	Exiting();
    }
    
    @Override
    public void windowOpened(WindowEvent e){}
	
    public void Exiting() {
	System.exit(0);
    }
}
