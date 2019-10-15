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


class User
{
    String              username;
    String              password;
    
    Vector<String>      buddies;
    
    Vector<String>      offlineRequests;
    
    Vector<String>      offlineMessages;
    
    UserList            userList;
    
    ConnectionToClient  ctc;

    User(String username, String password, ConnectionToClient ctc, UserList userList)
    {
        this.username = username;
        this.password = password;
        this.userList = userList;
        
        buddies = new Vector<String>();
        offlineRequests = new Vector<String>();
        
        offlineMessages = new Vector<String>();
        
        this.ctc = ctc;
    }
    
    User(DataInputStream dis, UserList userList) throws IOException
    {
        this.userList = userList;
        
        username = dis.readUTF();
        password = dis.readUTF();
        
        int buddiesSize = dis.readInt();
        
        buddies = new Vector<String>();
        
        for(int i = 0; i < buddiesSize; i++)
        {
            buddies.addElement(dis.readUTF());
            System.out.println("User: " + buddies.elementAt(i));
        }
        
        offlineRequests = new Vector<String>();
        
        offlineMessages = new Vector<String>();
    }
    
    void store(DataOutputStream dos) throws IOException
    {
        dos.writeUTF(username);
        dos.writeUTF(password);
        
        
        
        dos.writeInt(buddies.size());
        
        for(int i = 0; i < buddies.size(); i++)
            dos.writeUTF(buddies.elementAt(i));
    }
    
    boolean checkPassword(String password)
    {
        if(this.password.equals(password))
            return true;
        else
            return false;
    }
    
    void setCTC(ConnectionToClient ctc)
    {
        this.ctc = ctc;
        ctc.setUser(this);
        
        User tempBud;
        
        for(int b = 0; b < buddies.size(); b++)
        {
            tempBud = userList.get(buddies.elementAt(b));
            
            ctc.sendMessage("BUDDY" + "\0" + "POPULATE" + "\0" + tempBud.getUsername() + "\0" + tempBud.isOnline());
            
            if(tempBud.isOnline())
                tempBud.sendMessage("BUDDY" + "\0" + "STATUS" + "\0" + username);
        }
        
        if(offlineMessages.size() != 0)
        {
            for(int i = 0; i < offlineMessages.size(); i++)
            {
                sendMessage(offlineMessages.elementAt(i));
            }
            
            offlineMessages = new Vector<String>();
        }
    }
    
    boolean isOnline()
    {
        if(ctc != null)
            return true;
        
        return false;
    }
    
    void disconnected()
    {
        ctc = null;
        
        User tempBud;
        
        for(int b = 0; b < buddies.size(); b++)
        {
            tempBud = userList.get(buddies.elementAt(b));
            
            if(tempBud.isOnline())
                tempBud.sendMessage("BUDDY" + "\0" + "STATUS" + "\0" + username);
        }
    }
    
    void addBuddy(String buddy)
    {
        buddies.addElement(buddy);
    }
    
    void sendMessage(String message)
    {
        
        if(ctc != null)
            ctc.sendMessage(message);
        else
            offlineMessages.addElement(message);
            
    }
    
    String getUsername()
    {
        return username;
    }

}
