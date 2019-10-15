import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.text.*;
import java.lang.*;
import java.awt.event.*;
import java.util.*;
import java.awt.dnd.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JFileChooser.*;
import java.awt.Color.*;
import java.awt.event.MouseEvent.*;
import java.awt.datatransfer.*;

Macintosh HD⁩ ▸ ⁨Users⁩ ▸ ⁨jeremiah⁩ ▸ ⁨Desktop⁩

class MyFrameClass extends JFrame
                    implements ActionListener, MouseListener, ListSelectionListener, WindowListener, DropTargetListener
{
    JButton openButton;
    JButton saveButton;
    JButton addButton;
    JButton editButton;
    JButton deleteButton;
    JButton exitButton;
    

    
    JMenuItem deleteMenu;
    JMenuItem editMenu;
    
    AssignmentListModel myList;
    JList myListBox;
    JScrollPane myScrollPane;
    
    Properties propList;
    
    Color color;
    
    JFileChooser chooser;
    
    JPanel horizontalButtons;
    
    DropTarget dropTarget;
    
    String tempColor;
    
    AddEditDialog myDialogBox;

    MyFrameClass() // constructor
    {   // beginning of MyFrameClass constructor

        Container cp;
        
        cp = getContentPane();
        
        
        
        //   /$$$$$$$                                                     /$$     /$$
        //  | $$__  $$                                                   | $$    |__/
        //  | $$  \ $$ /$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$  /$$$$$$   /$$  /$$$$$$   /$$$$$$$
        //  | $$$$$$$//$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$|_  $$_/  | $$ /$$__  $$ /$$_____/
        //  | $$____/| $$  \__/| $$  \ $$| $$  \ $$| $$$$$$$$| $$  \__/  | $$    | $$| $$$$$$$$|  $$$$$$
        //  | $$     | $$      | $$  | $$| $$  | $$| $$_____/| $$        | $$ /$$| $$| $$_____/ \____  $$
        //  | $$     | $$      |  $$$$$$/| $$$$$$$/|  $$$$$$$| $$        |  $$$$/| $$|  $$$$$$$ /$$$$$$$/
        //  |__/     |__/       \______/ | $$____/  \_______/|__/         \___/  |__/ \_______/|_______/
        //                               | $$
        //                               | $$
        //                               |__/
        
        
        
        setupProperties();
        
        
        
        //   /$$       /$$             /$$
        //  | $$      |__/            | $$
        //  | $$       /$$  /$$$$$$$ /$$$$$$    /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$$
        //  | $$      | $$ /$$_____/|_  $$_/   /$$__  $$| $$__  $$ /$$__  $$ /$$__  $$ /$$_____/
        //  | $$      | $$|  $$$$$$   | $$    | $$$$$$$$| $$  \ $$| $$$$$$$$| $$  \__/|  $$$$$$
        //  | $$      | $$ \____  $$  | $$ /$$| $$_____/| $$  | $$| $$_____/| $$       \____  $$
        //  | $$$$$$$$| $$ /$$$$$$$/  |  $$$$/|  $$$$$$$| $$  | $$|  $$$$$$$| $$       /$$$$$$$/
        //  |________/|__/|_______/    \___/   \_______/|__/  |__/ \_______/|__/      |_______/

        
        
        this.addWindowListener(this);
        this.addMouseListener(this);
        
        
        
        //   /$$       /$$             /$$           /$$$$$$$
        //  | $$      |__/            | $$          | $$__  $$
        //  | $$       /$$  /$$$$$$$ /$$$$$$        | $$  \ $$  /$$$$$$  /$$   /$$
        //  | $$      | $$ /$$_____/|_  $$_/        | $$$$$$$  /$$__  $$|  $$ /$$/
        //  | $$      | $$|  $$$$$$   | $$          | $$__  $$| $$  \ $$ \  $$$$/
        //  | $$      | $$ \____  $$  | $$ /$$      | $$  \ $$| $$  | $$  >$$  $$
        //  | $$$$$$$$| $$ /$$$$$$$/  |  $$$$/      | $$$$$$$/|  $$$$$$/ /$$/\  $$
        //  |________/|__/|_______/    \___/        |_______/  \______/ |__/  \__/

        

        myList = new AssignmentListModel();
        
        try
        {
            if(propList.getProperty("LAST FILE") != "")
                myList.setDLM(new DataInputStream(new FileInputStream(propList.getProperty("LAST FILE"))));
        }
        catch(IOException e)
        {
            System.out.println("No File Found.");
        }
        
        myListBox = new JList(myList.getDLM());
        
        myListBox.addMouseListener(this);
        myListBox.addListSelectionListener(this);
        myListBox.setFont(new Font("Courier", Font.PLAIN, 12));
        
        myScrollPane = new JScrollPane(myListBox);
        
        cp.add(myScrollPane, BorderLayout.CENTER);
        myListBox.setBackground(new Color(Integer.parseInt(propList.getProperty("FOREGROUND"))));
        


        //      /$$$$$ /$$$$$$$              /$$       /$$
        //     |__  $$| $$__  $$            | $$      | $$
        //        | $$| $$  \ $$ /$$   /$$ /$$$$$$   /$$$$$$    /$$$$$$  /$$$$$$$   /$$$$$$$
        //        | $$| $$$$$$$ | $$  | $$|_  $$_/  |_  $$_/   /$$__  $$| $$__  $$ /$$_____/
        //   /$$  | $$| $$__  $$| $$  | $$  | $$      | $$    | $$  \ $$| $$  \ $$|  $$$$$$
        //  | $$  | $$| $$  \ $$| $$  | $$  | $$ /$$  | $$ /$$| $$  | $$| $$  | $$ \____  $$
        //  |  $$$$$$/| $$$$$$$/|  $$$$$$/  |  $$$$/  |  $$$$/|  $$$$$$/| $$  | $$ /$$$$$$$/
        //   \______/ |_______/  \______/    \___/     \___/   \______/ |__/  |__/|_______/
        
        

        // Open Button
        //==============================================
        openButton = new JButton("Open");
        openButton.setActionCommand("OPEN");
        openButton.addActionListener(this);
        //==============================================
        
        // Save Button
        //==============================================
        saveButton = new JButton("Save");
        saveButton.setActionCommand("SAVE");
        saveButton.addActionListener(this);
        //==============================================
        
        // Add Button
        //==============================================
        addButton = new JButton("Add");
        addButton.setActionCommand("ADD");
        addButton.addActionListener(this);
        //==============================================
        
        // Edit Button
        //==============================================
        editButton = new JButton("Edit");
        editButton.setActionCommand("EDIT");
        editButton.addActionListener(this);
        editButton.setEnabled(false);
        //==============================================
        
        // Delete Button
        //==============================================
        deleteButton = new JButton("Delete");
        deleteButton.setActionCommand("DELETE");
        deleteButton.addActionListener(this);
        deleteButton.setEnabled(false);
        //==============================================
        
        // Exit Button
        //==============================================
        exitButton = new JButton("Exit");
        exitButton.setActionCommand("EXIT");
        exitButton.addActionListener(this);
        //==============================================

        
        
        //      /$$$$$ /$$$$$$$                                /$$
        //     |__  $$| $$__  $$                              | $$
        //        | $$| $$  \ $$  /$$$$$$  /$$$$$$$   /$$$$$$ | $$  /$$$$$$$
        //        | $$| $$$$$$$/ |____  $$| $$__  $$ /$$__  $$| $$ /$$_____/
        //   /$$  | $$| $$____/   /$$$$$$$| $$  \ $$| $$$$$$$$| $$|  $$$$$$
        //  | $$  | $$| $$       /$$__  $$| $$  | $$| $$_____/| $$ \____  $$
        //  |  $$$$$$/| $$      |  $$$$$$$| $$  | $$|  $$$$$$$| $$ /$$$$$$$/
        //   \______/ |__/       \_______/|__/  |__/ \_______/|__/|_______/
        
        
        
        horizontalButtons = new JPanel(new GridLayout(1, 6));
        
        horizontalButtons.add(openButton);
        horizontalButtons.add(saveButton);
        horizontalButtons.add(addButton);
        horizontalButtons.add(editButton);
        horizontalButtons.add(deleteButton);
        horizontalButtons.add(exitButton);
        
        cp.add(horizontalButtons, BorderLayout.SOUTH);
        horizontalButtons.setBackground(new Color(Integer.parseInt(propList.getProperty("BACKGROUND"))));
        
        
        
        //   /$$      /$$ /$$                               /$$ /$$
        //  | $$$    /$$$|__/                              | $$| $$
        //  | $$$$  /$$$$ /$$  /$$$$$$$  /$$$$$$$  /$$$$$$ | $$| $$  /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$  /$$   /$$  /$$$$$$$
        //  | $$ $$/$$ $$| $$ /$$_____/ /$$_____/ /$$__  $$| $$| $$ |____  $$| $$__  $$ /$$__  $$ /$$__  $$| $$  | $$ /$$_____/
        //  | $$  $$$| $$| $$|  $$$$$$ | $$      | $$$$$$$$| $$| $$  /$$$$$$$| $$  \ $$| $$$$$$$$| $$  \ $$| $$  | $$|  $$$$$$
        //  | $$\  $ | $$| $$ \____  $$| $$      | $$_____/| $$| $$ /$$__  $$| $$  | $$| $$_____/| $$  | $$| $$  | $$ \____  $$
        //  | $$ \/  | $$| $$ /$$$$$$$/|  $$$$$$$|  $$$$$$$| $$| $$|  $$$$$$$| $$  | $$|  $$$$$$$|  $$$$$$/|  $$$$$$/ /$$$$$$$/
        //  |__/     |__/|__/|_______/  \_______/ \_______/|__/|__/ \_______/|__/  |__/ \_______/ \______/  \______/ |_______/
        
        dropTarget = new DropTarget(myScrollPane, this);
        
        chooser = new JFileChooser(".");
        
        setJMenuBar(newMenuBar());
        
        setupMainFrame();
    }   // end of MyFrameClass constructor       // calls setupMainFrame
    
    void setupMainFrame()
    {
        Toolkit     tk;
        Dimension    d;
        
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();                 // Get screen resolution
        setSize(d.width/2, d.height/2);         // Set size and location based
        setLocation(d.width/4, d.height/4);     // on the resolution
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setTitle("Assignment Tracker");     // For the title bar
        
        setVisible(true);
    }
    
    
    
    //      /$$$$$ /$$      /$$                               /$$$$$$$
    //     |__  $$| $$$    /$$$                              | $$__  $$
    //        | $$| $$$$  /$$$$  /$$$$$$  /$$$$$$$  /$$   /$$| $$  \ $$  /$$$$$$   /$$$$$$
    //        | $$| $$ $$/$$ $$ /$$__  $$| $$__  $$| $$  | $$| $$$$$$$  |____  $$ /$$__  $$
    //   /$$  | $$| $$  $$$| $$| $$$$$$$$| $$  \ $$| $$  | $$| $$__  $$  /$$$$$$$| $$  \__/
    //  | $$  | $$| $$\  $ | $$| $$_____/| $$  | $$| $$  | $$| $$  \ $$ /$$__  $$| $$
    //  |  $$$$$$/| $$ \/  | $$|  $$$$$$$| $$  | $$|  $$$$$$/| $$$$$$$/|  $$$$$$$| $$
    //   \______/ |__/     |__/ \_______/|__/  |__/ \______/ |_______/  \_______/|__/

    
    
    private JMenuBar newMenuBar()
    {
        JMenuBar menuBar;
        
        JMenu    subMenu;
        
        
        menuBar = new JMenuBar();
        //--------------------------------------------
        subMenu = new JMenu("File", true);
        subMenu.setMnemonic('F');
        
        subMenu.add(newItem("New...", "NEW", this, KeyEvent.VK_N,
                            KeyEvent.VK_N, "Creates new file."));
        
        subMenu.add(newItem("Open...", "OPEN", this, KeyEvent.VK_O,
                            KeyEvent.VK_O, "Opens file selector."));
        
        subMenu.add(newItem("Save...", "SAVE", this, KeyEvent.VK_S,
                            KeyEvent.VK_S, "Saves to file."));
        
        subMenu.add(newItem("saVe as...", "SAVE AS", this, KeyEvent.VK_V,
                            KeyEvent.VK_V, "Creates new save file."));
        
        menuBar.add(subMenu);
        
        //---------------------------------------------
        subMenu = new JMenu("Assignment");
        subMenu.setMnemonic('E');
        
        subMenu.add(newItem("Add...", "ADD", this, KeyEvent.VK_A,
                            KeyEvent.VK_A, "Adds a new assignment."));
        
        editMenu = newItem("Edit...", "EDIT", this, KeyEvent.VK_E,
                           KeyEvent.VK_E, "Edits selected Assignment");
        subMenu.add(editMenu);
        editMenu.setEnabled(false);
        
        deleteMenu = newItem("Delete...", "DELETE", this, KeyEvent.VK_D,
                             KeyEvent.VK_D, "Deletes selected Assignment(s)");
        subMenu.add(deleteMenu);
        deleteMenu.setEnabled(false);
        
        
        menuBar.add(subMenu);
        
        //---------------------------------------------
        subMenu = new JMenu("Settings");
        subMenu.setMnemonic('E');
        
        subMenu.add(newItem("Background Color", "BACKGROUND", this, KeyEvent.VK_B,
                            KeyEvent.VK_B, "Allows selection of background color."));
        
        subMenu.add(newItem("Foreground Color", "FOREGROUND", this, KeyEvent.VK_F,
                            KeyEvent.VK_F, "Allows selection of foreground color."));
        
        menuBar.add(subMenu);
        
        //---------------------------------------------
        
        return menuBar;
    }
    
    private JMenuItem newItem(String         label,
                              String         actionCommand,
                              ActionListener menuListener,
                              int             mnemonic,
                              int             keyCode,
                              String         toolTipText)
    {
        JMenuItem m;
        
        m = new JMenuItem(label, mnemonic);
        m.setAccelerator(KeyStroke.getKeyStroke(keyCode, KeyEvent.ALT_MASK));
        m.setToolTipText(toolTipText);
        m.setActionCommand(actionCommand);
        m.addActionListener(menuListener);
        
        return m;
    }
    
    
    
    //   /$$$$$$$                                                      /$$     /$$
    //  | $$__  $$                                                    | $$    |__/
    //  | $$  \ $$  /$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$  /$$$$$$   /$$  /$$$$$$   /$$$$$$$
    //  | $$$$$$$/ /$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$|_  $$_/  | $$ /$$__  $$ /$$_____/
    //  | $$____/ | $$  \__/| $$  \ $$| $$  \ $$| $$$$$$$$| $$  \__/  | $$    | $$| $$$$$$$$|  $$$$$$
    //  | $$      | $$      | $$  | $$| $$  | $$| $$_____/| $$        | $$ /$$| $$| $$_____/ \____  $$
    //  | $$      | $$      |  $$$$$$/| $$$$$$$/|  $$$$$$$| $$        |  $$$$/| $$|  $$$$$$$ /$$$$$$$/
    //  |__/      |__/       \______/ | $$____/  \_______/|__/         \___/  |__/ \_______/|_______/
    //                                | $$
    //                                | $$
    //                                |__/
    
    
    
    public void setupProperties()
    {
        propList = new Properties();
        
        try
        {
            propList.load(new InputStreamReader(new FileInputStream("p.txt")));
        }
        catch(Exception e)
        {
            System.out.println("Loading defaults...");
            
            propList.setProperty("BACKGROUND", "" + Color.WHITE.getRGB());
            propList.setProperty("FOREGROUND", "" + Color.WHITE.getRGB());
            propList.setProperty("LAST FILE", "");
            try
            {
                propList.store(new OutputStreamWriter(new FileOutputStream("props.txt")),"Writing properties to a file");
            }
            catch(IOException ex)
            {
                System.out.println("Unable to store properties file.");
            }
        }
    }
    
    
    
    //    /$$$$$$              /$$     /$$                           /$$$$$$$$                                  /$$
    //   /$$__  $$            | $$    |__/                          | $$_____/                                 | $$
    //  | $$  \ $$  /$$$$$$$ /$$$$$$   /$$  /$$$$$$  /$$$$$$$       | $$       /$$    /$$  /$$$$$$  /$$$$$$$  /$$$$$$
    //  | $$$$$$$$ /$$_____/|_  $$_/  | $$ /$$__  $$| $$__  $$      | $$$$$   |  $$  /$$/ /$$__  $$| $$__  $$|_  $$_/
    //  | $$__  $$| $$        | $$    | $$| $$  \ $$| $$  \ $$      | $$__/    \  $$/$$/ | $$$$$$$$| $$  \ $$  | $$
    //  | $$  | $$| $$        | $$ /$$| $$| $$  | $$| $$  | $$      | $$        \  $$$/  | $$_____/| $$  | $$  | $$ /$$
    //  | $$  | $$|  $$$$$$$  |  $$$$/| $$|  $$$$$$/| $$  | $$      | $$$$$$$$   \  $/   |  $$$$$$$| $$  | $$  |  $$$$/
    //  |__/  |__/ \_______/   \___/  |__/ \______/ |__/  |__/      |________/    \_/     \_______/|__/  |__/   \___/
    
    
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("OPEN"))
            doOpen();
        else if(e.getActionCommand().equals("SAVE"))
            doSave();
        else if(e.getActionCommand().equals("SAVE AS"))
            doSaveAs();
        else if(e.getActionCommand().equals("ADD"))
            doAdd();
        else if(e.getActionCommand().equals("EDIT"))
            doEdit();
        else if(e.getActionCommand().equals("DELETE"))
            doDelete();
        else if(e.getActionCommand().equals("EXIT"))
            doExit();
        else if(e.getActionCommand().equals("BACKGROUND"))
            doBackgroundColor();
        else if(e.getActionCommand().equals("FOREGROUND"))
            doForegroundColor();
        else if(e.getActionCommand().equals("COMPLETE"))
            doCompleted();
        else if(e.getActionCommand().equals("NEW"))
            doNew();
    }
    
    void doOpen()
    {
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                myList.setDLM(new DataInputStream(new FileInputStream(chooser.getSelectedFile())));
                myListBox.setModel(myList.getDLM());
                propList.setProperty("LAST FILE", chooser.getSelectedFile().getPath());
                propList.store(new OutputStreamWriter(new FileOutputStream("props.txt")), "Writing properties to a file");
            }
            catch(IOException pooop)
            {
                System.out.println("File Input Error");
            }
        }
    }
    
    void doSave()
    {
        if(propList.getProperty("LAST FILE") == null)
            doSaveAs();
        else
        {
            try
            {
                myList.store(new DataOutputStream(new FileOutputStream(propList.getProperty("LAST FILE"))));
            }
            catch(IOException e)
            {
                System.out.println("File Output Error");
            }
        }
    }
    
    void doSaveAs()
    {
        if(chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                myList.store(new DataOutputStream(new FileOutputStream(chooser.getSelectedFile())));
                propList.setProperty("LAST FILE", chooser.getSelectedFile().getPath());
                propList.store(new OutputStreamWriter(new FileOutputStream("props.txt")), "Writing properties to a file");
            }
            catch(IOException e)
            {
                System.out.println("File Output Error");
            }
        }
    }
    
    void doAdd()
    {
        myDialogBox = new AddEditDialog(myList);
    }
    
    void doEdit()
    {
            if(myListBox.getSelectedIndices().length == 1)
                myDialogBox = new AddEditDialog(myList, myListBox.getSelectedIndex(), myList.returnAssignment(myListBox.getSelectedIndex()));
            else
                JOptionPane.showMessageDialog(this.getParent(), "Please select one assignment to be edited", "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    void doDelete()
    {
        myList.removeAssignment(myListBox.getSelectedIndices());
    }
    
    void doExit()
    {
        checkSaveState();
        
        System.exit(0);
    }
    
    void doBackgroundColor()
    {
        color = JColorChooser.showDialog(null, "Background Color", color);
        
        if(color != null)
        {
            horizontalButtons.setBackground(color);
            
            try
            {
                propList.setProperty("BACKGROUND", "" + color.getRGB());
                propList.store(new OutputStreamWriter(new FileOutputStream("props.txt")),"Writing properties to a file");
            }
            catch(IOException ex)
            {
                System.out.println("Error while storing Background Color");
            }
        }
    }
    
    void doForegroundColor()
    {
        color = JColorChooser.showDialog(null, "Foreground Color", color);
        
        if(color != null)
        {
            myListBox.setBackground(color);
            
            try
            {
                propList.setProperty("FOREGROUND", "" + color.getRGB());
                propList.store(new OutputStreamWriter(new FileOutputStream("props.txt")),"Writing properties to a file");
            }
            catch(IOException ex)
            {
                System.out.println("Error while storing Foreground Color");
            }
        }
    }
    
    void doCompleted()
    {
        myList.completeAssignment(myListBox.getSelectedIndex());
    }
    
    void doNew()
    {
        if(JOptionPane.showConfirmDialog(null, "Would you like to create a new file?", "New File", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
        {
            checkSaveState();
            myListBox.setModel(myList.newDLM());
            
            try
            {
                propList.setProperty("LAST FILE", "");
                propList.store(new OutputStreamWriter(new FileOutputStream("props.txt")),"Writing properties to a file");
            }
            catch(IOException ex)
            {
                System.out.println("Error while storing Last File");
            }
        }
    }
    
    
    //   /$$      /$$                                               /$$$$$$$$                                  /$$
    //  | $$$    /$$$                                              | $$_____/                                 | $$
    //  | $$$$  /$$$$  /$$$$$$  /$$   /$$  /$$$$$$$  /$$$$$$       | $$       /$$    /$$  /$$$$$$  /$$$$$$$  /$$$$$$
    //  | $$ $$/$$ $$ /$$__  $$| $$  | $$ /$$_____/ /$$__  $$      | $$$$$   |  $$  /$$/ /$$__  $$| $$__  $$|_  $$_/
    //  | $$  $$$| $$| $$  \ $$| $$  | $$|  $$$$$$ | $$$$$$$$      | $$__/    \  $$/$$/ | $$$$$$$$| $$  \ $$  | $$
    //  | $$\  $ | $$| $$  | $$| $$  | $$ \____  $$| $$_____/      | $$        \  $$$/  | $$_____/| $$  | $$  | $$ /$$
    //  | $$ \/  | $$|  $$$$$$/|  $$$$$$/ /$$$$$$$/|  $$$$$$$      | $$$$$$$$   \  $/   |  $$$$$$$| $$  | $$  |  $$$$/
    //  |__/     |__/ \______/  \______/ |_______/  \_______/      |________/    \_/     \_______/|__/  |__/   \___/
    
    

    public void mouseClicked​(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON3)
        {
            if(myListBox.locationToIndex(e.getPoint()) >= 0)
            {
                JMenuItem editPopup = new JMenuItem("Edit");
                editPopup.setActionCommand("EDIT");
                editPopup.addActionListener(this);
            
                JMenuItem deletePopup = new JMenuItem("Delete");
                deletePopup.setActionCommand("DELETE");
                deletePopup.addActionListener(this);
            
                JMenuItem completePopup = new JMenuItem("Set as Complete");
                completePopup.setActionCommand("COMPLETE");
                completePopup.addActionListener(this);
                
                JPopupMenu myPopupMenu = new JPopupMenu();
                myPopupMenu.add(editPopup);
                myPopupMenu.add(deletePopup);
                myPopupMenu.add(completePopup);
                
                myPopupMenu.show(this, e.getX(), e.getY());
            }
        }
    }

    public void mouseEntered​(MouseEvent e){}
    public void mouseExited​(MouseEvent e){}
    public void mousePressed​(MouseEvent e){}
    public void mouseReleased​(MouseEvent e){}
    
    
    
    //   /$$       /$$             /$$           /$$$$$$$$                                  /$$
    //  | $$      |__/            | $$          | $$_____/                                 | $$
    //  | $$       /$$  /$$$$$$$ /$$$$$$        | $$       /$$    /$$  /$$$$$$  /$$$$$$$  /$$$$$$
    //  | $$      | $$ /$$_____/|_  $$_/        | $$$$$   |  $$  /$$/ /$$__  $$| $$__  $$|_  $$_/
    //  | $$      | $$|  $$$$$$   | $$          | $$__/    \  $$/$$/ | $$$$$$$$| $$  \ $$  | $$
    //  | $$      | $$ \____  $$  | $$ /$$      | $$        \  $$$/  | $$_____/| $$  | $$  | $$ /$$
    //  | $$$$$$$$| $$ /$$$$$$$/  |  $$$$/      | $$$$$$$$   \  $/   |  $$$$$$$| $$  | $$  |  $$$$/
    //  |________/|__/|_______/    \___/        |________/    \_/     \_______/|__/  |__/   \___/
    
    
    
    public void valueChanged​(ListSelectionEvent e)
    {
        if(myListBox.getSelectedIndices().length == 0)
        {
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            editMenu.setEnabled(false);
            deleteMenu.setEnabled(false);
        }
        else if(myListBox.getSelectedIndices().length == 1)
        {
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            editMenu.setEnabled(true);
            deleteMenu.setEnabled(true);
        }
        else if(myListBox.getSelectedIndices().length > 1)
        {
            editButton.setEnabled(false);
            deleteButton.setEnabled(true);
            editMenu.setEnabled(false);
            deleteMenu.setEnabled(true);
        }
    }
    
    
    
    //   /$$      /$$ /$$                 /$$                               /$$$$$$$$                                  /$$
    //  | $$  /$ | $$|__/                | $$                              | $$_____/                                 | $$
    //  | $$ /$$$| $$ /$$ /$$$$$$$   /$$$$$$$  /$$$$$$  /$$  /$$  /$$      | $$       /$$    /$$  /$$$$$$  /$$$$$$$  /$$$$$$
    //  | $$/$$ $$ $$| $$| $$__  $$ /$$__  $$ /$$__  $$| $$ | $$ | $$      | $$$$$   |  $$  /$$/ /$$__  $$| $$__  $$|_  $$_/
    //  | $$$$_  $$$$| $$| $$  \ $$| $$  | $$| $$  \ $$| $$ | $$ | $$      | $$__/    \  $$/$$/ | $$$$$$$$| $$  \ $$  | $$
    //  | $$$/ \  $$$| $$| $$  | $$| $$  | $$| $$  | $$| $$ | $$ | $$      | $$        \  $$$/  | $$_____/| $$  | $$  | $$ /$$
    //  | $$/   \  $$| $$| $$  | $$|  $$$$$$$|  $$$$$$/|  $$$$$/$$$$/      | $$$$$$$$   \  $/   |  $$$$$$$| $$  | $$  |  $$$$/
    //  |__/     \__/|__/|__/  |__/ \_______/ \______/  \_____/\___/       |________/    \_/     \_______/|__/  |__/   \___/

    
    
    public void windowClosing​(WindowEvent e)
    {
        checkSaveState();
    }
    
    public void windowActivated​(WindowEvent e){}
    public void windowClosed​(WindowEvent e){}
    public void windowDeactivated​(WindowEvent e){}
    public void windowDeiconified​(WindowEvent e){}
    public void windowIconified​(WindowEvent e){}
    public void windowOpened​(WindowEvent e){}
    
    
    
    //   /$$$$$$$                                      /$$$$$$$$                                  /$$
    //  | $$__  $$                                    | $$_____/                                 | $$
    //  | $$  \ $$  /$$$$$$   /$$$$$$   /$$$$$$       | $$       /$$    /$$  /$$$$$$  /$$$$$$$  /$$$$$$
    //  | $$  | $$ /$$__  $$ |____  $$ /$$__  $$      | $$$$$   |  $$  /$$/ /$$__  $$| $$__  $$|_  $$_/
    //  | $$  | $$| $$  \__/  /$$$$$$$| $$  \ $$      | $$__/    \  $$/$$/ | $$$$$$$$| $$  \ $$  | $$
    //  | $$  | $$| $$       /$$__  $$| $$  | $$      | $$        \  $$$/  | $$_____/| $$  | $$  | $$ /$$
    //  | $$$$$$$/| $$      |  $$$$$$$|  $$$$$$$      | $$$$$$$$   \  $/   |  $$$$$$$| $$  | $$  |  $$$$/
    //  |_______/ |__/       \_______/ \____  $$      |________/    \_/     \_______/|__/  |__/   \___/
    //                                 /$$  \ $$
    //                                |  $$$$$$/
    //                                 \______/
    
    
    


    public void drop​(DropTargetDropEvent dtde)
    {
        java.util.List<File>     fileList;
        Transferable         transferableData;
        
        transferableData = dtde.getTransferable();
        
        try
        {
            checkSaveState();
            
            if (transferableData.isDataFlavorSupported(DataFlavor.javaFileListFlavor))
            {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                fileList = (java.util.List<File>)(transferableData.getTransferData(DataFlavor.javaFileListFlavor));
                
                if(fileList.size() == 1)
                    open(fileList.get(0));

            }
        }
        catch (UnsupportedFlavorException ufe)
        {
            System.out.println("Unsupported file type!");
            ufe.printStackTrace();
        }
        catch (IOException ioe)
        {
            System.out.println("IOException found getting transerable data!");
            ioe.printStackTrace();
        }
    }
    
    public void dragEnter​(DropTargetDragEvent dtde){}
    public void dropActionChanged​(DropTargetDragEvent dtde){}
    public void dragExit​(DropTargetEvent dte){}
    public void dragOver​(DropTargetDragEvent dtde){}
        
    void open(File inFile)
    {
        try
        {
            myList.setDLM(new DataInputStream(new FileInputStream(inFile)));
            myListBox.setModel(myList.getDLM());
            propList.setProperty("LAST FILE", inFile.getCanonicalPath());
            propList.store(new OutputStreamWriter(new FileOutputStream("props.txt")), "Writing properties to a file");
        }
        catch(IOException e)
        {
            if(e.getMessage().equals("Unsupported file type!"))
                System.out.println(e.getMessage());
            else
            {
                System.out.println("Error opening file.");
                e.printStackTrace();
            }
        }
    }

//   /$$      /$$ /$$                               /$$ /$$
//  | $$$    /$$$|__/                              | $$| $$
//  | $$$$  /$$$$ /$$  /$$$$$$$  /$$$$$$$  /$$$$$$ | $$| $$  /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$  /$$   /$$  /$$$$$$$
//  | $$ $$/$$ $$| $$ /$$_____/ /$$_____/ /$$__  $$| $$| $$ |____  $$| $$__  $$ /$$__  $$ /$$__  $$| $$  | $$ /$$_____/
//  | $$  $$$| $$| $$|  $$$$$$ | $$      | $$$$$$$$| $$| $$  /$$$$$$$| $$  \ $$| $$$$$$$$| $$  \ $$| $$  | $$|  $$$$$$
//  | $$\  $ | $$| $$ \____  $$| $$      | $$_____/| $$| $$ /$$__  $$| $$  | $$| $$_____/| $$  | $$| $$  | $$ \____  $$
//  | $$ \/  | $$| $$ /$$$$$$$/|  $$$$$$$|  $$$$$$$| $$| $$|  $$$$$$$| $$  | $$|  $$$$$$$|  $$$$$$/|  $$$$$$/ /$$$$$$$/
//  |__/     |__/|__/|_______/  \_______/ \_______/|__/|__/ \_______/|__/  |__/ \_______/ \______/  \______/ |_______/

    void checkSaveState()
    {
        if(myList.needsSaved)
        {
            int optionChoice = JOptionPane.showConfirmDialog(null, "Would you like to save your file?", "Save", JOptionPane.YES_NO_OPTION);
            if(optionChoice == JOptionPane.YES_OPTION)
                doSave();
        }
    }
}
