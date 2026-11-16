import java.awt.*;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import java.awt.event.*;
import java.io.*;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.undo.UndoManager;
import javax.swing.text.DefaultHighlighter;

class NotePad  extends JFrame implements ActionListener {
    JFrame frame;
    JMenuBar menubar;
    JMenu file,edit,format,view;
    JMenuItem newfile,open,save,undo,redo,copy,paste,find,replace;
    JTextArea textArea;
    JScrollPane pane;
    JDialog dialog;
    JLabel label1,label2;
    JTextField t1,t2;
    JButton replacebutton,replaceAll,cancel;
    JFileChooser fileChooser=new JFileChooser();
    String data;
    UndoManager uManager = new UndoManager();
    NotePad(){
        frame = new JFrame("Notepad");
        //add menubar
        menubar = new JMenuBar(); 
        //add menu on menubar
        file = new JMenu("File");
        menubar.add(file);
 
        edit = new JMenu("Edit");
        menubar.add(edit);
        
        format = new JMenu("Format");
        menubar.add(format);
        
        view = new JMenu("View");
        menubar.add(view);

        
        newfile = new JMenuItem("New");
        newfile.setForeground(new Color(51, 51, 51));
        //Interect with menu item(new file) using keyboard keys
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        //Add action on create new file menu item
        newfile.addActionListener(this);
        file.add(newfile);

        open = new JMenuItem("Open");
        open.setForeground(new Color(51, 51, 51));
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);
        file.add(open);


        save = new JMenuItem("Save");
        save.setForeground(new Color(51, 51, 51));
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);
        file.add(save);

        //items in edit
        undo = new JMenuItem("Undo");
        undo.setForeground(new Color(51, 51, 51));
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undo.addActionListener(this);
        edit.add(undo);

        redo = new JMenuItem("Redo");
        redo.setForeground(new Color(51, 51, 51));
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        redo.addActionListener(this);
        edit.add(redo);

        copy = new JMenuItem("Copy");
        copy.setForeground(new Color(51, 51, 51));
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);
        edit.add(copy);

        paste = new JMenuItem("Paste");
        paste.setForeground(new Color(51, 51, 51));
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);
        edit.add(paste);

        find = new JMenuItem("Find");
        find.setForeground(new Color(51, 51, 51));
        find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        find.addActionListener(this);
        edit.add(find);

        replace = new JMenuItem("Replace");
        replace.setForeground(new Color(51, 51, 51));
        replace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        replace.addActionListener(this);
        edit.add(replace);
        //Add Text Area
        textArea = new JTextArea();
        textArea.setFont(new Font("Dialog",Font.PLAIN,20));
        textArea.setLineWrap(true);
        textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                uManager.addEdit(e.getEdit());
            }
        });
        //add Scroll Pane
        pane = new JScrollPane(textArea);
        pane.setBorder(BorderFactory.createEmptyBorder());
        frame.add(pane, BorderLayout.CENTER);
        
        //Set menubar on frame
        frame.setJMenuBar(menubar);
        //set frame Icon
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("E:/3rd semester/Assighment01/notepad.JPEG"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //Set Frame Size
        frame.setSize(800, 600);
        //Set Frame in Center
        frame.setLocationRelativeTo(null);
    }
    public void actionPerformed(ActionEvent event){
        if(event.getSource().equals(newfile)){
            int user = JOptionPane.showConfirmDialog(this, "Are you sure?", "Do you want to change?",JOptionPane.YES_NO_CANCEL_OPTION);
            if(user == JOptionPane.YES_OPTION)
                textArea.setText("");
        }
        else if(event.getSource().equals(open)){
            open();
        }
        else if(event.getSource().equals(save)){
            save();
        }
        else if(event.getSource().equals(copy)){
            textArea.copy();
        }
        else if(event.getSource().equals(paste)){
            textArea.paste();
        }
        else if(event.getSource().equals(undo)){
            // String text = textArea.getText();
            // StackDemo st = new StackDemo(text);
            // textArea.setText(st.undo());
            uManager.undo();
        }
        else if(event.getSource().equals(redo)){
            // String text = textArea.getText();
            // StackDemo st = new StackDemo(text);
            // textArea.setText(st.redo());
            uManager.redo();
        }
        else if(event.getSource().equals(find)){
            String name = JOptionPane.showInputDialog(null,"Enter Word", "Find",JOptionPane.INFORMATION_MESSAGE);
            data = textArea.getText();
            if(name !=null) {
                try {
                    find(data,name);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        else if(event.getSource().equals(replace)){
            data = textArea.getText();
            setReplaceDialog();
        }
        else if(event.getSource().equals(cancel)){
            dialog.setVisible(false);
        }
        else if(event.getSource().equals(replacebutton)){
            String str1 = t1.getText();
            String str2 = t2.getText();
            replace(str1,str2);
        }
        else if(event.getSource().equals(replaceAll)){
            String str1 = t1.getText();
            String str2 = t2.getText();
            replace(str1,str2);
        }
    }
    //for find a word
    public void find(String data, String name) throws Exception {
        String word[]= data.split("\\s"); 
        int n = word.length;  
        for(int i=0;i<n;i++)
        {
            if(word[i].equals(name)) {
                int start = data.indexOf(name);
                int end = start + name.length();
                Highlighter hilite = textArea.getHighlighter();
                HighlightPainter painter =
                        new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                hilite.addHighlight(start, end, painter);
            }  
        }
    }
    //for open a new file
    public void open() {
        int action = fileChooser.showOpenDialog(this);
        if(action != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        File file = fileChooser.getSelectedFile();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getPath()));
            String input;
            while((input = reader.readLine())!= null)
            {
                textArea.append(input + "\n");
            }
        } catch (FileNotFoundException e) {
            //TODO: handle exception
            JOptionPane.showMessageDialog(null, "sorry file,not found","" ,JOptionPane.WARNING_MESSAGE);
        }
        catch(IOException e) {
            System.out.println(file.getAbsoluteFile());
        }
    }
    //for save file
    public void save() {
        
        fileChooser.setApproveButtonText("Save");
        int action = fileChooser.showOpenDialog(this);
        if(action != JFileChooser.APPROVE_OPTION)
        {
            return;
        }
        File filename = new File(fileChooser.getSelectedFile()+"txt");
        BufferedWriter outfile = null;
        try
        {
            outfile=new BufferedWriter(new FileWriter(filename));
            textArea.write(outfile);
            setTitle(filename.getName());
            outfile.close();
        }
        catch(Exception e)
        {
            System.out.println(filename.getAbsoluteFile());
        }
    }
    public void setReplaceDialog() {
       
         dialog = new JDialog(frame,"Replace",true);
        //Set label 1
        label1 = new JLabel("Word: ");
        label1.setBounds(10,15,80,20);
        dialog.add(label1);
        //set label2
        label2 = new JLabel("Replace with: ");
        label2.setBounds(10,45,80,20);
        dialog.add(label2);
        //replace Button in  
        replacebutton = new JButton("Replace");
        replacebutton.setBounds(5,90,80,20);
        replacebutton.setFocusable(false);
        replacebutton.addActionListener(this);
        dialog.add(replacebutton);
        //replaceAll Button in  
        replaceAll = new JButton("Replace All");
        replaceAll.setBounds(95,90,100,20);
        replaceAll.setFocusable(false);
        replaceAll.addActionListener(this);
        dialog.add(replaceAll);
        // //Cancel Button in  
        cancel = new JButton("Cancel");
        cancel.setBounds(200,90,80,20);
        cancel.setFocusable(false);
        cancel.addActionListener(this);
        dialog.add(cancel);
        //set text fields
        t1 = new JTextField();
        t1.setBounds(60,15,100,20);
        dialog.add(t1); 
        t2 = new JTextField();
        t2.setBounds(100,45,100,20);
        dialog.add(t2);
        //Dialog Settings
        dialog.setLayout(null);
        dialog.setSize(300,200);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
    public void replace(String str, String str2)
    { 
        String word[]= data.split("\\s"); 
        String store="";
        int n = word.length;  
        for(int i=0;i<n;i++)
        {
            if(word[i].equals(str))
            {   
			    word[i]=str2;
		    }
            store = store+word[i]+" ";
            //JOptionPane.showMessageDialog(null, "sorry Word,not found","" ,JOptionPane.WARNING_MESSAGE);
        }
        textArea.setText(store);
    }
    public static void main(String [] args) {
        NotePad note = new NotePad();
    }
}