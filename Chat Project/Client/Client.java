
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.lang.Math;
import java.text.*;
import java.awt.dnd.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JFileChooser.*;
import java.awt.Color.*;
import java.awt.event.MouseEvent.*;
import java.awt.datatransfer.*;
import javax.swing.*;
import javax.swing.Timer;
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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import javax.swing.plaf.metal.MetalBorders.ButtonBorder;
import javax.swing.border.MatteBorder;



class Client extends JFrame
                implements ActionListener, MouseListener
{
    JList                       buddyList;
    DefaultListModel<Buddy>     buddies;
    
    JButton                     addBuddyBtn;
    JButton                     connectBtn;
    JButton                     exitBtn;
    
    ConnectionToServer          cts;
    
    String                      userID;
    
    JLabel                      userLabel;
    
    ConnectDialog               connect;

    
    Client()
    {
        createUserPanel();
        createBuddyPanel();
        createButtonPanel();
        
        setupMainFrame();
    }
    
    void setupMainFrame()
    {
        Toolkit    tk;
        Dimension   d;
        
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        pack();
        setLocation(d.width/3, d.height/3);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Poslanie");
        setVisible(true);
        setResizable(false);
    }
    
    void createUserPanel()
    {
        JPanel userPanel = new JPanel();
        
        userLabel = new JLabel("Connect...");
        userPanel.add(userLabel);
        
        this.add(userPanel, BorderLayout.NORTH);
    }
    
    void createBuddyPanel()
    {
        JPanel buddyPanel = new JPanel();
        
        buddies = new DefaultListModel<Buddy>();
        buddyList = new JList(buddies);
        buddyList.addMouseListener(this);
        JScrollPane scrollPane = new JScrollPane(buddyList);
        scrollPane.setPreferredSize(new Dimension(120, 300));
        
        buddyPanel.add(scrollPane);
        
        this.add(buddyPanel, BorderLayout.CENTER);
    }
    
    void createButtonPanel()
    {
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
        
        addBuddyBtn = createButton("Add Buddy", this, "ADD_BUDDY");
        
        buttonPanel.add(addBuddyBtn);
        
        connectBtn = createButton("Connect", this, "CONNECT");
        buttonPanel.add(connectBtn);
        
        exitBtn = createButton("Exit", this, "EXIT");
        buttonPanel.add(exitBtn);
        
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    JButton createButton(String bt, ActionListener al, String ac)
    {
        JButton btn = new JButton(bt);
        btn.addActionListener(al);
        btn.setActionCommand(ac);
        return btn;
    }
    
    //   /$$       /$$             /$$
    //  | $$      |__/            | $$
    //  | $$       /$$  /$$$$$$$ /$$$$$$    /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$$
    //  | $$      | $$ /$$_____/|_  $$_/   /$$__  $$| $$__  $$ /$$__  $$ /$$__  $$ /$$_____/
    //  | $$      | $$|  $$$$$$   | $$    | $$$$$$$$| $$  \ $$| $$$$$$$$| $$  \__/|  $$$$$$
    //  | $$      | $$ \____  $$  | $$ /$$| $$_____/| $$  | $$| $$_____/| $$       \____  $$
    //  | $$$$$$$$| $$ /$$$$$$$/  |  $$$$/|  $$$$$$$| $$  | $$|  $$$$$$$| $$       /$$$$$$$/
    //  |________/|__/|_______/    \___/   \_______/|__/  |__/ \_______/|__/      |_______/
    
    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getActionCommand().equals("ADD_BUDDY"))
            doAddBuddy();
        else if(ae.getActionCommand().equals("CONNECT"))
            doConnect();
        else if(ae.getActionCommand().equals("LOGOUT"))
            doLogout();
        else if(ae.getActionCommand().equals("EXIT"))
            doExit();
    }
    
    void doAddBuddy()
    {
        String buddyID = JOptionPane.showInputDialog("Enter buddyID:");
        
        if(buddyID != null)
        {
            cts.sendMessage("BUDDY" + "\0" + "REQUEST_ADD" + "\0" + buddyID + "\0" + userID);
        }
    }
    
    void doConnect()
    {
        cts = new ConnectionToServer(this);
        connect = new ConnectDialog(this);
    }
    
    void doLogout()
    {
        cts.sendMessage("DISCONNECT" + "\0" + userID);
        cts = null;
        connectBtn.setText("Connect");
        connectBtn.setActionCommand("CONNECT");
        
        userLabel.setText("Connect...");
        
        buddies = new DefaultListModel<Buddy>();
        buddyList.setModel(buddies);
        buddyList.revalidate();
    }
    
    void doExit()
    {
        System.exit(1);
    }
    
    public void mouseClicked(MouseEvent me)
    {
        if(me.getClickCount() == 2 && me.getButton() == MouseEvent.BUTTON1)
        {
            if(buddyList.locationToIndex(me.getPoint()) >= 0)
            {
                Buddy tempBud = buddies.getElementAt(buddyList.getSelectedIndex());
                
                tempBud.openChatWindow(userID, cts);
            }
        }
    }
    
    public void mouseEntered(MouseEvent me){}
    public void mouseExited(MouseEvent me){}
    public void mousePressed(MouseEvent me){}
    public void mouseReleased(MouseEvent me){}
    
    void handleMessage(String messageFromServer)
    {
        String splitMessage[] = messageFromServer.split("\0");
        
        switch(splitMessage[0])
        {
            case "CONNECT":
                connectResponse(splitMessage);
                break;
            case "BUDDY":
                buddyRequest(splitMessage);
                break;
            case "MESSAGE":
                messageReceived(splitMessage);
                break;
            case "FILE":
                break;
        }
    }
    
    //    /$$$$$$   /$$$$$$  /$$   /$$ /$$   /$$ /$$$$$$$$  /$$$$$$  /$$$$$$$$
    //   /$$__  $$ /$$__  $$| $$$ | $$| $$$ | $$| $$_____/ /$$__  $$|__  $$__/
    //  | $$  \__/| $$  \ $$| $$$$| $$| $$$$| $$| $$      | $$  \__/   | $$
    //  | $$      | $$  | $$| $$ $$ $$| $$ $$ $$| $$$$$   | $$         | $$
    //  | $$      | $$  | $$| $$  $$$$| $$  $$$$| $$__/   | $$         | $$
    //  | $$    $$| $$  | $$| $$\  $$$| $$\  $$$| $$      | $$    $$   | $$
    //  |  $$$$$$/|  $$$$$$/| $$ \  $$| $$ \  $$| $$$$$$$$|  $$$$$$/   | $$
    //   \______/  \______/ |__/  \__/|__/  \__/|________/ \______/    |__/
    
    void connectToServer(String verb, String username, String password)
    {
        cts.sendMessage("CONNECT" + "\0" + verb + "\0" + username + "\0" + password);
        
        userID = username;
    }
    
    void connectResponse(String serverMessage[])
    {
        String answer = serverMessage[1];
        
        if(answer.equals("APPROVED"))
        {
            userLabel.setText(userID);
            userLabel.revalidate();
            connect.dispose();
            connectBtn.setText("Logout");
            connectBtn.setActionCommand("LOGOUT");
        }
        else if(answer.equals("DENIED"))
        {
            String reason = serverMessage[2];
            
            if(reason.equals("LOGIN_INVALID"))
                JOptionPane.showMessageDialog(null, "Username/Password is incorrect. Please try again."
                                                    , "Invalid Login", JOptionPane.ERROR_MESSAGE);
            else if(reason.equals("USER_EXISTS"))
                JOptionPane.showMessageDialog(null, "Username exists. Please try another username."
                                                    , "User Exists", JOptionPane.ERROR_MESSAGE);
            
            userID = null;
        }
    }
    
    //   /$$$$$$$  /$$   /$$ /$$$$$$$  /$$$$$$$  /$$     /$$
    //  | $$__  $$| $$  | $$| $$__  $$| $$__  $$|  $$   /$$/
    //  | $$  \ $$| $$  | $$| $$  \ $$| $$  \ $$ \  $$ /$$/
    //  | $$$$$$$ | $$  | $$| $$  | $$| $$  | $$  \  $$$$/
    //  | $$__  $$| $$  | $$| $$  | $$| $$  | $$   \  $$/
    //  | $$  \ $$| $$  | $$| $$  | $$| $$  | $$    | $$
    //  | $$$$$$$/|  $$$$$$/| $$$$$$$/| $$$$$$$/    | $$
    //  |_______/  \______/ |_______/ |_______/     |__/
    
    void buddyRequest(String message[])
    {
        String verb = message[1];
        
        if(verb.equals("REQUEST_ADD"))
        {
            String from = message[2];
            
            int buddyConfirmation = JOptionPane.showConfirmDialog(null, from + " would like to be your friend.", "Buddy Request", JOptionPane.YES_NO_OPTION);
            
            if(buddyConfirmation == JOptionPane.YES_OPTION)
            {
                cts.sendMessage("BUDDY" + "\0" + "REQUEST_ANSWER" + "\0" + "ACCEPTED" + "\0" + from + "\0" + userID);
                buddies.addElement(new Buddy(from, "true"));
                buddyList.revalidate();
            }
            else
            {
                cts.sendMessage("BUDDY" + "\0" + "REQUEST_ANSWER" + "\0" + "DENIED" + "\0" + from + "\0" + userID);
            }
        }
        else if(verb.equals("REQUEST_ANSWER"))
        {
            String answer = message[2];
            
            if(answer.equals("ACCEPTED"))
            {
                String buddyID = message[3];
                buddies.addElement(new Buddy(buddyID, "true"));
            }
        }
        else if(verb.equals("POPULATE"))
        {
            buddies.addElement(new Buddy(message[2], message[3]));
            buddyList.setModel(buddies);
            buddyList.revalidate();
        }
        else if(verb.equals("STATUS"))
        {
            String myBuddy = message[2];
            
            Buddy tempBud;
            
            for(int i = 0; i < buddies.getSize(); i++)
            {
                // search for user
                tempBud = buddies.elementAt(i);
                
                // if found, create chat window and add message
                if(myBuddy.equals(tempBud.getBuddyID()))
                {
                    tempBud.changeStatus();
                }
            }
            buddyList.setModel(buddies);
            buddyList.revalidate();
        }
    }
    
    //   /$$      /$$ /$$$$$$$$  /$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$  /$$$$$$$$
    //  | $$$    /$$$| $$_____/ /$$__  $$ /$$__  $$ /$$__  $$ /$$__  $$| $$_____/
    //  | $$$$  /$$$$| $$      | $$  \__/| $$  \__/| $$  \ $$| $$  \__/| $$
    //  | $$ $$/$$ $$| $$$$$   |  $$$$$$ |  $$$$$$ | $$$$$$$$| $$ /$$$$| $$$$$
    //  | $$  $$$| $$| $$__/    \____  $$ \____  $$| $$__  $$| $$|_  $$| $$__/
    //  | $$\  $ | $$| $$       /$$  \ $$ /$$  \ $$| $$  | $$| $$  \ $$| $$
    //  | $$ \/  | $$| $$$$$$$$|  $$$$$$/|  $$$$$$/| $$  | $$|  $$$$$$/| $$$$$$$$
    //  |__/     |__/|________/ \______/  \______/ |__/  |__/ \______/ |________/
    
    void messageReceived(String message[])
    {
        String buddyID = message[1];
        String textMessage = message[2];
        
        Buddy tempBud;
        
        for(int i = 0; i < buddies.getSize(); i++)
        {
            // search for user
            tempBud = buddies.elementAt(i);
            
            // if found, create chat window and add message
            if(buddyID.equals(tempBud.getBuddyID()))
            {
                tempBud.displayMessage(userID, textMessage, cts);
            }
        }
    }
    
}
