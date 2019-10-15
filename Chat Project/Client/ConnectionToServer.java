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

class ConnectionToServer
                implements Runnable
{
    Messenger           messenger;
    
    Client              client;
    
    ConnectionToServer(Client client)
    {
        this.client = client;
        
        messenger = new Messenger("localhost", 2257);
        
        System.out.println("ConnectionToServer: Connection Made");
        
        Thread tempThread = new Thread(this);
        tempThread.start();
    }

    public void sendMessage(String message)
    {
        try
        {
            messenger.send(message);
        }
        catch (IOException ioe)
        {
            System.out.println("ConnectionToServer: Error Sending Message");
            ioe.printStackTrace();
        }
    }
    
    @Override
    public void run()
    {
        String message;
        
        try
        {
            while(true)
            {
                message = messenger.receive();
                
                client.handleMessage(message);
            }
        }
        catch(IOException ioe)
        {
            System.out.println("ConnectionToServer: Server Closed");
            ioe.printStackTrace();
        }
    }
}
