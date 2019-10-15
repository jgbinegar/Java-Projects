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

class MessengerServer
                implements Runnable
{
    
    UserList                    usersList;
    ServerSocket                serverSocket;
    ConnectionToClient          tempCTC;
    
    MessengerServer()
    {
        clearScreen();
        
        System.out.println("        /$$$$$$");
        System.out.println("       /$$__  $$");
        System.out.println("      | $$  \\__/  /$$$$$$   /$$$$$$  /$$    /$$ /$$$$$$   /$$$$$$");
        System.out.println("      |  $$$$$$  /$$__  $$ /$$__  $$|  $$  /$$//$$__  $$ /$$__  $$");
        System.out.println("       \\____  $$| $$$$$$$$| $$  \\__/ \\  $$/$$/| $$$$$$$$| $$  \\__/");
        System.out.println("       /$$  \\ $$| $$_____/| $$        \\  $$$/ | $$_____/| $$");
        System.out.println("      |  $$$$$$/|  $$$$$$$| $$         \\  $/  |  $$$$$$$| $$");
        System.out.println("       \\______/  \\_______/|__/          \\_/    \\_______/|__/");
        
        System.out.println("************************************************************************");
        System.out.println("************************************************************************");
        System.out.println("************************************************************************");
        System.out.println("\0");
        
        try
        {
            serverSocket = new ServerSocket(2257);
        }
        catch(IOException ioe)
        {
            System.out.println("MessengerServer: Fatal Error! Unable to create ServerSocket.");
            System.exit(1);
        }
        
        usersList = new UserList();
        
        Thread temp = new Thread(this);
        
        temp.start();
    }
    
    @Override
    public void run()
    {
        Socket clientSocket;
        
        while(true)
        {
            try
            {
                tempCTC = new ConnectionToClient(this, serverSocket.accept(), usersList);
            }
            catch(IOException ioe)
            {
                System.out.println("MessengerServer: Error connecting client.");
            }
            tempCTC = null;
        }
    }
    
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    void incomingTransmission(String message, ConnectionToClient ctc)
    {
        String clientMessage[] = message.split("\0");
        
        if(clientMessage[0].equals("CONNECT"))
        {
            handleConnect(clientMessage[1], clientMessage[2], clientMessage[3], ctc);
        }
        else if(clientMessage[0].equals("BUDDY"))
        {
            handleBuddy(clientMessage);
        }
        else if(clientMessage[0].equals("DISCONNECT"))
        {
            disconnectUser(clientMessage);
        }
        else if(clientMessage[0].equals("MESSAGE"))
        {
            handleMessage(clientMessage);
        }
            
    }
    
    void handleConnect(String verb, String userID, String password, ConnectionToClient ctc)
    {
        if(verb.equals("LOGIN"))
        {
            if(usersList.containsKey(userID))
            {
                User tempUser = usersList.get(userID);
                
                if(tempUser.checkPassword(password) && !tempUser.isOnline())
                {
                    ctc.sendMessage("CONNECT" + "\0" + "APPROVED");
                    tempUser.setCTC(ctc);
                    ctc = null;
                    return;
                }
            }
            ctc.sendMessage("CONNECT" + "\0" + "DENIED" + "\0" + "LOGIN_INVALID");
        }
        else if(verb.equals("REGISTER"))
        {
            if(!usersList.containsKey(userID))
            {
                usersList.put(userID, new User(userID, password, ctc, usersList));
                usersList.store();
                ctc.sendMessage("CONNECT" + "\0" + "APPROVED");
                ctc = null;
                return;
            }
            ctc.sendMessage("CONNECT" + "\0" + "DENIED" + "\0" + "USER_EXISTS");
        }
        else if(verb.equals("DISCONNECTED"))
        {
            User tempUser = usersList.get(userID);
        }
    }
    
    void handleBuddy(String clientMessage[])
    {
        if(clientMessage[1].equals("REQUEST_ADD"))
        {
            User tempUser = usersList.get(clientMessage[2]);
            
            if(tempUser != null)
            {
                tempUser.sendMessage("BUDDY" + "\0" + "REQUEST_ADD" + "\0" + clientMessage[3]);
            }
        }
        else if(clientMessage[1].equals("REQUEST_ANSWER"))
        {
            String answer = clientMessage[2];
            String receiver = clientMessage[3];
            String sender = clientMessage[4];
            
            //User tempUser = usersList.get(receiver);
            
            //tempUser.sendMessage("BUDDY" + "\0" + "REQUEST_ANSWER" + "\0" + answer + "\0" + sender);
            
            usersList.get(receiver).sendMessage("BUDDY" + "\0" + "REQUEST_ANSWER" + "\0" + answer + "\0" + sender);
            
            if(clientMessage[2].equals("ACCEPTED"))
            {
                usersList.get(receiver).addBuddy(sender);
                usersList.get(sender).addBuddy(receiver);
            }
        }
        
        usersList.store();
    }
    
    void disconnectUser(String clientMessage[])
    {
        User tempUser = usersList.get(clientMessage[1]);
        
        tempUser.disconnected();
        
        
    }
    
    void handleMessage(String message[])
    {
        User tempUser = usersList.get(message[1]);
        
        tempUser.sendMessage("MESSAGE" + "\0" + message[2] + "\0" + message[3]); // verb <To> <Message>
    }
}
