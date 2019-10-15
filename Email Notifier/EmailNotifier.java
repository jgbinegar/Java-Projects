import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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

public class EmailNotifier
                    implements ActionListener
{
    
    public static void main(String[] args)
    {
        new EmailNotifier();
    }
    
    Properties propList;
    
//================================================
    String          username;
    String          password;
    String          serverName;
    String          audioClip;
    int             portNumber;
    int             checkInterval;
    boolean         soundToggle;
    //================================================
    
    SystemTray      systemTray;
    TrayIcon        trayIcon;
    
    EmailChecker    checker;
    
    MenuItem        alertMenuItem;
    
    Timer           emailTimer;
    
    public EmailNotifier()
    {
        retrieveProperties();
        
        systemTray = SystemTray.getSystemTray();
        
        alertMenuItem = creatMenuItem("", this, "TOGGLE");
        
        PopupMenu popupMenu = new PopupMenu();
        popupMenu.add(creatMenuItem("Settings", this, "SETTINGS"));
        
        // Used as a debug to test if able to successfully retrieve new mail.
        //popupMenu.add(creatMenuItem("Check Email", this, "CHECK"));
        
        popupMenu.add(alertMenuItem);
        popupMenu.add(creatMenuItem("Exit", this, "EXIT"));
        
        Image image = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource(File.separator + "Icons" + File.separator + "Icon.png"));
        
        trayIcon = new TrayIcon(image, "Email Notifier", popupMenu);
        trayIcon.setImageAutoSize(true);
        
        try
        {
            systemTray.add(trayIcon);
        }
        catch(AWTException e)
        {
            System.out.println("TrayIcon could not be added.");
            return;
        }
        
        setupChecker();
    }
    
    public MenuItem creatMenuItem(String text, ActionListener al, String command)
    {
        MenuItem temp = new MenuItem(text);
        temp.addActionListener(al);
        temp.setActionCommand(command);
        
        return temp;
    }
    
    public void setupChecker()
    {
        if(propList.getProperty("INITIALSTART").equals("YES"))
            new InitialSetup(this);
        else
        {
            username = propList.getProperty("USERNAME");
            password = propList.getProperty("PASSWORD");
            serverName = propList.getProperty("SERVERNAME");
            audioClip = propList.getProperty("AUDIOCLIP");
            portNumber = Integer.parseInt(propList.getProperty("PORTNUMBER"));
            checkInterval = Integer.parseInt(propList.getProperty("INTERVAL"));
            soundToggle = Boolean.parseBoolean(propList.getProperty("SOUNDTOGGLE"));
            
            setAlertLabel();
            
            emailTimer = new Timer(checkInterval * 60 * 1000, this);
            emailTimer.setActionCommand("CHECK");
            emailTimer.start();
            
            checker = new EmailChecker(serverName, username, password, "imaps", portNumber);
        }
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("CHECK")) // Used for timer to check
            doEmailCheck();
        else if(e.getActionCommand().equals("SETTINGS")) // Used is user selects settings option
            doSettings();
        else if(e.getActionCommand().equals("TOGGLE")) // Used to toggle sound on and off
            doToggle();
        else if(e.getActionCommand().equals("EXIT")) // Used to exit the program
            doExit();
    }
    
    void doEmailCheck()
    {
        checker.checkMail(soundToggle, audioClip);
    }
    
    void doSettings()
    {
        new SettingsDialog(this);
    }
    
    void doToggle()
    {
        if(soundToggle)
            soundToggle = false;
        else
            soundToggle = true;
        
        propList.setProperty("SOUNDTOGGLE", "" + soundToggle);
        
        setAlertLabel();
    }
    
    void doExit()
    {
        try
        {
            propList.store(new OutputStreamWriter(new FileOutputStream("EmailProps.txt")),"Creating Properties File...");
        }
        catch(IOException ex)
        {
            System.out.println("Unable to store properties file.");
        }
            systemTray.remove(trayIcon);
            System.exit(0);
    }
    
    public void retrieveProperties()
    {
        propList = new Properties();
        
        try
        {
            propList.load(
                    new InputStreamReader(
                            new FileInputStream("EmailProps.txt")));
        }
        catch(Exception e)
        {
            System.out.println("Loading defaults...");
            
            propList.setProperty("INITIALSTART", "YES");
            propList.setProperty("USERNAME", "");
            propList.setProperty("PASSWORD", "");
            propList.setProperty("SERVERNAME", "");
            propList.setProperty("PORTNUMBER", "");
            propList.setProperty("INTERVAL", "");
            propList.setProperty("SOUNDTOGGLE", "");
            propList.setProperty("AUDIOCLIP", "Alert 1.wav");
            try
            {
                propList.store(new OutputStreamWriter(new FileOutputStream("EmailProps.txt")),"Creating Properties File...");
            }
            catch(IOException ex)
            {
                System.out.println("Unable to store properties file.");
            }
        }
    }
    
    public void saveProperties(String username, String password, String serverName, int portNumber, int checkInterval, boolean soundToggle)
    {
        this.username        = username;
        this.password        = password;
        this.serverName      = serverName;
        this.portNumber      = portNumber;
        this.checkInterval   = checkInterval;
        this.soundToggle     = soundToggle;
        
        propList.setProperty("INITIALSTART", "NO");
        propList.setProperty("USERNAME", username);
        propList.setProperty("PASSWORD", password);
        propList.setProperty("SERVERNAME", serverName);
        propList.setProperty("PORTNUMBER", "" + portNumber);
        propList.setProperty("INTERVAL", "" + checkInterval);
        propList.setProperty("SOUNDTOGGLE", "" + soundToggle);
        try
        {
            propList.store(new OutputStreamWriter(new FileOutputStream("EmailProps.txt")),"Creating Properties File...");
        }
        catch(IOException ex)
        {
            System.out.println("Unable to store properties file.");
        }
        
        setupChecker();
    }
    
    public void setAlertLabel()
    {
        if(soundToggle)
            alertMenuItem.setLabel("Alert:   ✓");
        else
            alertMenuItem.setLabel("Alert:   ✖");
    }
}
