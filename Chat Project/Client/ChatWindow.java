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
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;

public class ChatWindow extends JDialog
                            implements ActionListener//, DocumentListener
{
    ChatPanel               messagePanel;
    
    ConnectionToServer      cts;
    
    JTextArea               messageBox;
    
    JButton                 sendButton;
    JButton                 exitButton;
    
    String                  buddyID;
    String                  userID;
    
    ChatWindow(String userID, String buddyID, ConnectionToServer cts)
    {
        this.userID = userID;
        this.buddyID = buddyID;
        this.cts = cts;
        
        messagePanel = new ChatPanel(400, 300);
        
        this.add(messagePanel, BorderLayout.CENTER);
        
        this.add(createSendPanel(), BorderLayout.SOUTH);
        
        setupMainFrame();
    }
    
    JPanel createSendPanel()
    {
        JPanel sendPanel = new JPanel();//new GridLayout(1, 2));
        
        messageBox = new JTextArea(4, 30);
        messageBox.setLineWrap(true);
        messageBox.setWrapStyleWord(true);
        messageBox.setBorder(new LineBorder(Color.BLACK));
        //TextPrompt mBox = new TextPrompt("Type a message or drop a file here.", messageBox);
        //mBox.changeAlpha(128);
        sendPanel.add(messageBox);
        
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        
        sendButton = new JButton("Send");
        sendButton.addActionListener(this);
        sendButton.setActionCommand("SEND");
        buttonPanel.add(sendButton);
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setActionCommand("EXIT");
        buttonPanel.add(exitButton);
        
        sendPanel.add(buttonPanel);
        
        return sendPanel;
    }
    
    void setupMainFrame()
    {
        Toolkit tk;
        Dimension d;
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        pack();
        setLocation(d.width/3, d.height/3);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Messenger - " + buddyID);
        setVisible(true);
        setResizable(false);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("SEND"))
            doSendMessage();
        else if(e.getActionCommand().equals("EXIT"))
            doExit();
    }
    
    void doSendMessage()
    {
        String message = messageBox.getText().trim();
        
        cts.sendMessage("MESSAGE" + "\0" + buddyID + "\0" + userID + "\0" + message);
        
        messagePanel.addMessage(message, "LEFT");
        
        messageBox.setText("");
    }
    
    void receiveMessage(String message)
    {
        messagePanel.addMessage(message, "RIGHT");
    }
    
    void doExit()
    {
        dispose();
    }
    
}
