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

// TO DO
// Setup doSave to save all values to the proplist
// Create pointers to JTextFields
// Fill INFO and ICON tabs
// Setup JPasswordField and make Hide/Unhide button work


class SettingsDialog extends JDialog
                        implements ActionListener
{
    public static void main (String[] args)
    {
        System.out.println("Starting application...");
        
        new SettingsDialog(null);
        
        System.out.println("Done.");
        
    }//End of main(...)
    
    EmailNotifier   notifier;
    
    JRadioButton    audioOne;
    JRadioButton    audioTwo;
    JRadioButton    audioThree;
    JRadioButton    audioFour;
    
    JSpinner        intervalSpinner;
    JCheckBox       soundCheckBox;
    JCheckBox       visionCheck;
    
    JTextField      usernameField;
    JPasswordField  passwordField;
    JTextField      serverField;
    JTextField      portField;
    
    boolean         passIsHidden;
    
    SettingsDialog(EmailNotifier notifier)
    {
        this.notifier = notifier;
        
        
        //      /$$$$$ /$$$$$$$$        /$$       /$$$$$$$
        //     |__  $$|__  $$__/       | $$      | $$__  $$
        //        | $$   | $$  /$$$$$$ | $$$$$$$ | $$  \ $$ /$$$$$$  /$$$$$$$   /$$$$$$
        //        | $$   | $$ |____  $$| $$__  $$| $$$$$$$/|____  $$| $$__  $$ /$$__  $$
        //   /$$  | $$   | $$  /$$$$$$$| $$  \ $$| $$____/  /$$$$$$$| $$  \ $$| $$$$$$$$
        //  | $$  | $$   | $$ /$$__  $$| $$  | $$| $$      /$$__  $$| $$  | $$| $$_____/
        //  |  $$$$$$/   | $$|  $$$$$$$| $$$$$$$/| $$     |  $$$$$$$| $$  | $$|  $$$$$$$
        //   \______/    |__/ \_______/|_______/ |__/      \_______/|__/  |__/ \_______/
        
        
        JTabbedPane settingsTabs;
        settingsTabs = new JTabbedPane();
        settingsTabs.addTab("Account", createAccountPanel());
        settingsTabs.addTab("Notification", createNotifPanel());
        settingsTabs.addTab("Sound", createSoundPanel());
        
        
        //      /$$$$$ /$$$$$$$              /$$     /$$
        //     |__  $$| $$__  $$            | $$    | $$
        //        | $$| $$  \ $$ /$$   /$$ /$$$$$$ /$$$$$$    /$$$$$$  /$$$$$$$
        //        | $$| $$$$$$$ | $$  | $$|_  $$_/|_  $$_/   /$$__  $$| $$__  $$
        //   /$$  | $$| $$__  $$| $$  | $$  | $$    | $$    | $$  \ $$| $$  \ $$
        //  | $$  | $$| $$  \ $$| $$  | $$  | $$ /$$| $$ /$$| $$  | $$| $$  | $$
        //  |  $$$$$$/| $$$$$$$/|  $$$$$$/  |  $$$$/|  $$$$/|  $$$$$$/| $$  | $$
        //   \______/ |_______/  \______/    \___/   \___/   \______/ |__/  |__/
        
        JButton saveButton;
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("SAVE");
        
        JButton saveCloseButton;
        saveCloseButton = new JButton("Save & Close");
        saveCloseButton.addActionListener(this);
        saveCloseButton.setActionCommand("SAVECLOSE");
        
        JButton closeButton;
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        closeButton.setActionCommand("CLOSE");
        
        
        //      /$$$$$ /$$$$$$$$                    /$$
        //     |__  $$|__  $$__/                   | $$
        //        | $$   | $$  /$$$$$$  /$$   /$$ /$$$$$$
        //        | $$   | $$ /$$__  $$|  $$ /$$/|_  $$_/
        //   /$$  | $$   | $$| $$$$$$$$ \  $$$$/   | $$
        //  | $$  | $$   | $$| $$_____/  >$$  $$   | $$ /$$
        //  |  $$$$$$/   | $$|  $$$$$$$ /$$/\  $$  |  $$$$/
        //   \______/    |__/ \_______/|__/  \__/   \___/
        
        
        
        
        //      /$$$$$ /$$$$$$$                               /$$
        //     |__  $$| $$__  $$                             | $$
        //        | $$| $$  \ $$ /$$$$$$  /$$$$$$$   /$$$$$$ | $$
        //        | $$| $$$$$$$/|____  $$| $$__  $$ /$$__  $$| $$
        //   /$$  | $$| $$____/  /$$$$$$$| $$  \ $$| $$$$$$$$| $$
        //  | $$  | $$| $$      /$$__  $$| $$  | $$| $$_____/| $$
        //  |  $$$$$$/| $$     |  $$$$$$$| $$  | $$|  $$$$$$$| $$
        //   \______/ |__/      \_______/|__/  |__/ \_______/|__/
        
        
        JPanel buttonPanel;
        buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(saveButton);
        buttonPanel.add(saveCloseButton);
        buttonPanel.add(closeButton);
        
        
        //      /$$$$$ /$$$$$$$  /$$           /$$
        //     |__  $$| $$__  $$|__/          | $$
        //        | $$| $$  \ $$ /$$  /$$$$$$ | $$  /$$$$$$   /$$$$$$
        //        | $$| $$  | $$| $$ |____  $$| $$ /$$__  $$ /$$__  $$
        //   /$$  | $$| $$  | $$| $$  /$$$$$$$| $$| $$  \ $$| $$  \ $$
        //  | $$  | $$| $$  | $$| $$ /$$__  $$| $$| $$  | $$| $$  | $$
        //  |  $$$$$$/| $$$$$$$/| $$|  $$$$$$$| $$|  $$$$$$/|  $$$$$$$
        //   \______/ |_______/ |__/ \_______/|__/ \______/  \____  $$
        //                                                   /$$  \ $$
        //                                                  |  $$$$$$/
        //                                                   \______/
        
        
        add(buttonPanel, BorderLayout.SOUTH);
        add(settingsTabs, BorderLayout.CENTER);
        
        setupMainFrame();
    } // end of SettingsDialog() constructor
    
    void setupMainFrame()
    {
        Toolkit tk;
        Dimension d;
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        pack();
        setLocation(d.width/3, d.height/3);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle("Application Settings");
        setVisible(true);
        setResizable(false);
    }
    
    JPanel createAccountPanel()
    {
        JPanel accountPanel = new JPanel(new GridLayout(4, 1));
        
        //***************************************************************************
        
        JPanel tempPanel;
        
        //***************************************************************************
        
        tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(new JLabel("Username:     "));
        usernameField = new JTextField(22);
        tempPanel.add(usernameField);
        accountPanel.add(tempPanel);
        
        //***************************************************************************
        
        tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(new JLabel("Password:      "));
        passwordField = new JPasswordField(15);
        tempPanel.add(passwordField);
        
        visionCheck = new JCheckBox("Reveal");
        visionCheck.addActionListener(this);
        visionCheck.setActionCommand("VISIBLE");
        tempPanel.add(visionCheck);
        
        accountPanel.add(tempPanel);
        
        //***************************************************************************
        
        tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(new JLabel("Server Name: "));
        serverField = new JTextField(22);
        tempPanel.add(serverField);
        accountPanel.add(tempPanel);
        
        //***************************************************************************
        
        tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(new JLabel("Port Number: "));
        portField = new JTextField(22);
        tempPanel.add(portField);
        accountPanel.add(tempPanel);
        
        //***************************************************************************
        
        return accountPanel;
    }
    
    JPanel createNotifPanel()
    {
        JPanel notificationPanel = new JPanel();
        
        //***************************************************************************
        
        JPanel intervalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        intervalPanel.add(new JLabel("Check Interval: "));
        intervalSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 60, 1));
        intervalPanel.add(intervalSpinner);
        
        //***************************************************************************
        
        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        //soundPanel.add(new JLabel("Alert?: "));
        soundCheckBox = new JCheckBox("Alert?");
        soundPanel.add(soundCheckBox);
        
        //***************************************************************************
        
        notificationPanel.add(intervalPanel);
        notificationPanel.add(soundPanel);
        
        return notificationPanel;
    }
    
    JPanel createSoundPanel()
    {
        JPanel soundPanel = new JPanel(new GridLayout(4, 1));
        soundPanel.add(new JLabel());
        ButtonGroup soundGroup = new ButtonGroup();
        
        //***************************************************************************
        
        JPanel  tempPanel;
        JButton tempButton;
        
        //***************************************************************************
        
        tempPanel = new JPanel();
        
        tempButton = new JButton("Alert 1");
        tempButton.addActionListener(this);
        tempButton.setActionCommand("AUDIO1");
        tempPanel.add(tempButton);
        
        tempButton = new JButton("Alert 2");
        tempButton.addActionListener(this);
        tempButton.setActionCommand("AUDIO2");
        tempPanel.add(tempButton);
        
        tempButton = new JButton("Alert 3");
        tempButton.addActionListener(this);
        tempButton.setActionCommand("AUDIO3");
        tempPanel.add(tempButton);
        
        tempButton = new JButton("Alert 4");
        tempButton.addActionListener(this);
        tempButton.setActionCommand("AUDIO4");
        tempPanel.add(tempButton);
        
        soundPanel.add(tempPanel);
        
        //***************************************************************************
        
        tempPanel = new JPanel();

        audioOne = new JRadioButton();
        audioOne.setSelected(true);
        audioTwo = new JRadioButton();
        audioThree = new JRadioButton();
        audioFour = new JRadioButton();

        soundGroup.add(audioOne);
        soundGroup.add(audioTwo);
        soundGroup.add(audioThree);
        soundGroup.add(audioFour);

        tempPanel.add(audioOne);
        tempPanel.add(new JLabel("              "));
        tempPanel.add(audioTwo);
        tempPanel.add(new JLabel("             "));
        tempPanel.add(audioThree);
        tempPanel.add(new JLabel("              "));
        tempPanel.add(audioFour);

        soundPanel.add(tempPanel);
        
        //***************************************************************************
        
        return soundPanel;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("VISIBLE"))
            doVisible();
        if(e.getActionCommand().equals("SAVE"))
            doSave();
        else if(e.getActionCommand().equals("SAVECLOSE"))
        {
            doSave();
            doClose();
        }
        else if(e.getActionCommand().equals("CLOSE"))
            doClose();
        else if(e.getActionCommand().equals("AUDIO1"))
            new AlertSound("Alert 1.wav");
        else if(e.getActionCommand().equals("AUDIO2"))
            new AlertSound("Alert 2.wav");
        else if(e.getActionCommand().equals("AUDIO3"))
            new AlertSound("Alert 3.wav");
        else if(e.getActionCommand().equals("AUDIO4"))
            new AlertSound("Alert 4.wav");
        
    }
    
    void doVisible()
    {
        if(visionCheck.isSelected())
            passwordField.setEchoChar((char)0);
        else
            passwordField.setEchoChar('•');
    }
    
    void doSave()
    {
        if(EmailVerifier.verify(usernameField))
        {
            String      username = usernameField.getText().trim();
            String      password = passwordField.getText();
            String      server = serverField.getText().trim();
            int         port = Integer.parseInt(portField.getText().trim());
            int         interval = (int)intervalSpinner.getValue();
            boolean     sound = soundCheckBox.isSelected();
            
            notifier.saveProperties(username, password, server, port, interval, sound);
        }
    }
    
    void doClose()
    {
        dispose();
    }
    
}
