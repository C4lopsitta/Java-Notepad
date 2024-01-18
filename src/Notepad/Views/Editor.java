package Notepad.Views;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Editor extends JFrame implements ActionListener, DocumentListener {
    private boolean isSaved = false;
    private String filename = "untitled";
    private JTextArea textArea = null;


    private JMenuItem mniNew = null;
    private JMenuItem mniSave = null;
    private JMenuItem mniSaveAs = null;
    private JMenuItem mniOpen = null;
    private JMenuItem mniExit = null;
    private JMenuItem mniAbout = null;

    public Editor() {
        setMinimumSize(new Dimension(400, 300));
        setTitle("Notepad");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();

        setVisible(true);
    }

    private void initUI() {
        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(this);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu mnuFile = new JMenu("File");
        mniNew = new JMenuItem("New");
        mniNew.addActionListener(this);
        mniOpen = new JMenuItem("Open...");
        mniOpen.addActionListener(this);
        mniSave = new JMenuItem("Save");
        mniSave.addActionListener(this);
        mniSaveAs = new JMenuItem("Save as...");
        mniSaveAs.addActionListener(this);
        mniExit = new JMenuItem("Exit");
        mniExit.addActionListener(this);

        mnuFile.add(mniNew);
        mnuFile.add(mniOpen);
        mnuFile.add(mniSave);
        mnuFile.add(mniSaveAs);
        mnuFile.addSeparator();
        mnuFile.add(mniExit);

        JMenu mnuHelp = new JMenu("Help");
        mniAbout = new JMenuItem("About...");
        mniAbout.addActionListener(this);
        mnuHelp.add(mniAbout);



        menuBar.add(mnuFile);
        menuBar.add(mnuHelp);

        setJMenuBar(menuBar);

        WindowAdapter adapter = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                actionExit();
            }
        };
        addWindowListener(adapter);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mniNew)     actionNew();
        if(e.getSource() == mniOpen)    actionOpen();
        if(e.getSource() == mniSave)    actionSave();
        if(e.getSource() == mniSaveAs)  actionSaveAs();
        if(e.getSource() == mniExit)    actionExit();
        if(e.getSource() == mniAbout)   actionAbout();
    }

    //region actions
    private void actionNew() {}
    private void actionOpen() {


        JFileChooser chooser = new JFileChooser("~");
        int openResult = chooser.showOpenDialog(this);
        if(openResult != JFileChooser.APPROVE_OPTION) return;

        setFilename(chooser.getSelectedFile().getName());

        try {
            BufferedReader br = new BufferedReader(new FileReader(chooser.getSelectedFile()));
            textArea.setText("");

            String line = br.readLine();
            while(line != null) {
                textArea.append(line + "\n");
                line = br.readLine();
            }

            isSaved = true;

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void actionSave() {
        JFileChooser chooser = new JFileChooser("~");
        int result = chooser.showOpenDialog(this);
        if(result != JFileChooser.APPROVE_OPTION) return;

        try {
            PrintWriter pr = new PrintWriter(chooser.getSelectedFile());
            pr.print(textArea.getText());
            isSaved = true;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    private void actionSaveAs() {}
    private void actionExit() {
        if(!isSaved) {
            int result = JOptionPane.showOptionDialog(
                    this,
                    "Do you want to save " + filename + "?",
                    "Warning",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    null,
                    null
            );
            if(result == JOptionPane.YES_OPTION) actionSave();
            if(result == JOptionPane.CANCEL_OPTION) return;
        }
        System.exit(0);
    }

    private void actionAbout() {
        new About(this);
    }
    //endregion actions


    //region DocumentListener
    @Override
    public void insertUpdate(DocumentEvent documentEvent) { isSaved = false; }
    @Override
    public void removeUpdate(DocumentEvent documentEvent) { isSaved = false; }
    @Override
    public void changedUpdate(DocumentEvent documentEvent) { isSaved = false; }
    //endregion DocumentListener

    private void setFilename(String file) {
        filename = file;
        setTitle("Notepad - " + file);
    }
}
