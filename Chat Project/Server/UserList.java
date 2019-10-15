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
import java.util.Hashtable;//<K,V>;



class UserList extends Hashtable<String, User>
{
    
    final File userFile = new File("users.txt");
    
    UserList()
    {
        DataInputStream dis;
        DataOutputStream dos;
        int numUsers;
        User tempUser;
        
        try
        {
            dis = new DataInputStream(new FileInputStream(userFile));
            
            numUsers = dis.readInt();
            
            for(int i = 0; i < numUsers; i++)
            {
                tempUser = new User(dis, this);
                put(tempUser.getUsername(), tempUser);
            }
        }
        catch(FileNotFoundException e)
        {
            try
            {
                userFile.createNewFile();
                dos = new DataOutputStream(new FileOutputStream(userFile));
                dos.writeInt(0);
            }
            catch(IOException ioe)
            {
                System.out.println("Error creating file");
                ioe.printStackTrace();
            }
        }
        catch(IOException ioe)
        {
            System.out.println("Error reading from file.");
            ioe.printStackTrace();
            
        }
    }
    
    void store()
    {
        DataOutputStream dos;
        
        try
        {
            Enumeration<User> e;
            
            dos = new DataOutputStream(new FileOutputStream(userFile));
            
            e = this.elements();
            
            dos.writeInt(this.size());
            
            while(e.hasMoreElements())
                e.nextElement().store(dos);
        }
        catch(Exception e)
        {
            System.out.println("Exception found.");
        }
        
    }
}
