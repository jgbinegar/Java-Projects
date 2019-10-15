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



class InitialSetup extends JDialog
                        implements ActionListener
{
    EmailNotifier notifier;
    
    JLabel          usernameLabel;
    JLabel          passwordLabel;
    JLabel          serverLabel;
    JLabel          portLabel;
    
    JTextField      usernameField;
    JPasswordField  passwordField;
    JTextField      serverField;
    JTextField      portField;
    
    JSpinner        intervalSpinner;
    JCheckBox       soundCheckBox;
    JCheckBox       visionCheck;
    
    boolean         passIsHidden;
    
    
    //   /$$$$$$           /$$   /$$     /$$           /$$
    //  |_  $$_/          |__/  | $$    |__/          | $$
    //    | $$   /$$$$$$$  /$$ /$$$$$$   /$$  /$$$$$$ | $$
    //    | $$  | $$__  $$| $$|_  $$_/  | $$ |____  $$| $$
    //    | $$  | $$  \ $$| $$  | $$    | $$  /$$$$$$$| $$
    //    | $$  | $$  | $$| $$  | $$ /$$| $$ /$$__  $$| $$
    //   /$$$$$$| $$  | $$| $$  |  $$$$/| $$|  $$$$$$$| $$
    //  |______/|__/  |__/|__/   \___/  |__/ \_______/|__/
    
    InitialSetup(EmailNotifier notifier)
    {
        this.notifier = notifier;
        
        //      /$$$$$ /$$$$$$$              /$$     /$$
        //     |__  $$| $$__  $$            | $$    | $$
        //        | $$| $$  \ $$ /$$   /$$ /$$$$$$ /$$$$$$    /$$$$$$  /$$$$$$$
        //        | $$| $$$$$$$ | $$  | $$|_  $$_/|_  $$_/   /$$__  $$| $$__  $$
        //   /$$  | $$| $$__  $$| $$  | $$  | $$    | $$    | $$  \ $$| $$  \ $$
        //  | $$  | $$| $$  \ $$| $$  | $$  | $$ /$$| $$ /$$| $$  | $$| $$  | $$
        //  |  $$$$$$/| $$$$$$$/|  $$$$$$/  |  $$$$/|  $$$$/|  $$$$$$/| $$  | $$
        //   \______/ |_______/  \______/    \___/   \___/   \______/ |__/  |__/
        
        JButton saveButton = new JButton("Save & Close");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("SAVE");
        this.getRootPane().setDefaultButton(saveButton);
        
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setActionCommand("EXIT");
        
        //      /$$$$$ /$$                 /$$                 /$$
        //     |__  $$| $$                | $$                | $$
        //        | $$| $$        /$$$$$$ | $$$$$$$   /$$$$$$ | $$
        //        | $$| $$       |____  $$| $$__  $$ /$$__  $$| $$
        //   /$$  | $$| $$        /$$$$$$$| $$  \ $$| $$$$$$$$| $$
        //  | $$  | $$| $$       /$$__  $$| $$  | $$| $$_____/| $$
        //  |  $$$$$$/| $$$$$$$$|  $$$$$$$| $$$$$$$/|  $$$$$$$| $$
        //   \______/ |________/ \_______/|_______/  \_______/|__/
        
        usernameLabel   = new JLabel("Username:     ");
        passwordLabel   = new JLabel("Password:      ");
        serverLabel     = new JLabel("Server Name: ");
        portLabel       = new JLabel("Port Number: ");
        
        //      /$$$$$  /$$$$$$            /$$
        //     |__  $$ /$$__  $$          |__/
        //        | $$| $$  \__/  /$$$$$$  /$$ /$$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$
        //        | $$|  $$$$$$  /$$__  $$| $$| $$__  $$| $$__  $$ /$$__  $$ /$$__  $$
        //   /$$  | $$ \____  $$| $$  \ $$| $$| $$  \ $$| $$  \ $$| $$$$$$$$| $$  \__/
        //  | $$  | $$ /$$  \ $$| $$  | $$| $$| $$  | $$| $$  | $$| $$_____/| $$
        //  |  $$$$$$/|  $$$$$$/| $$$$$$$/| $$| $$  | $$| $$  | $$|  $$$$$$$| $$
        //   \______/  \______/ | $$____/ |__/|__/  |__/|__/  |__/ \_______/|__/
        //                      | $$
        //                      | $$
        //                      |__/
        
        intervalSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 60, 1));
        intervalSpinner.setValue(10);
        
        //      /$$$$$  /$$$$$$  /$$                           /$$
        //     |__  $$ /$$__  $$| $$                          | $$
        //        | $$| $$  \__/| $$$$$$$   /$$$$$$   /$$$$$$$| $$   /$$
        //        | $$| $$      | $$__  $$ /$$__  $$ /$$_____/| $$  /$$/
        //   /$$  | $$| $$      | $$  \ $$| $$$$$$$$| $$      | $$$$$$/
        //  | $$  | $$| $$    $$| $$  | $$| $$_____/| $$      | $$_  $$
        //  |  $$$$$$/|  $$$$$$/| $$  | $$|  $$$$$$$|  $$$$$$$| $$ \  $$
        //   \______/  \______/ |__/  |__/ \_______/ \_______/|__/  \__/
        
        soundCheckBox = new JCheckBox("Alert?");
        visionCheck = new JCheckBox("Reveal");
        visionCheck.addActionListener(this);
        visionCheck.setActionCommand("VISIBLE");
        
        //      /$$$$$ /$$$$$$$$                    /$$
        //     |__  $$|__  $$__/                   | $$
        //        | $$   | $$  /$$$$$$  /$$   /$$ /$$$$$$
        //        | $$   | $$ /$$__  $$|  $$ /$$/|_  $$_/
        //   /$$  | $$   | $$| $$$$$$$$ \  $$$$/   | $$
        //  | $$  | $$   | $$| $$_____/  >$$  $$   | $$ /$$
        //  |  $$$$$$/   | $$|  $$$$$$$ /$$/\  $$  |  $$$$/
        //   \______/    |__/ \_______/|__/  \__/   \___/
        
        usernameField   = new JTextField(22);
        passwordField   = new JPasswordField(15);
        serverField     = new JTextField(22);
        portField       = new JTextField(22);
        
        // Used for quick testing
        usernameField.setText("jbinegar1@gmx.us");
        passwordField.setText("pswdJGB94");
        serverField.setText("imap.gmx.com");
        portField.setText("993");
        
        //      /$$$$$ /$$$$$$$                               /$$
        //     |__  $$| $$__  $$                             | $$
        //        | $$| $$  \ $$ /$$$$$$  /$$$$$$$   /$$$$$$ | $$
        //        | $$| $$$$$$$/|____  $$| $$__  $$ /$$__  $$| $$
        //   /$$  | $$| $$____/  /$$$$$$$| $$  \ $$| $$$$$$$$| $$
        //  | $$  | $$| $$      /$$__  $$| $$  | $$| $$_____/| $$
        //  |  $$$$$$/| $$     |  $$$$$$$| $$  | $$|  $$$$$$$| $$
        //   \______/ |__/      \_______/|__/  |__/ \_______/|__/
        
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        
//===========================================================================
        
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        passwordPanel.add(visionCheck);
        
//===========================================================================
        
        JPanel serverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        serverPanel.add(serverLabel);
        serverPanel.add(serverField);
        
//===========================================================================
        
        JPanel portPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        portPanel.add(portLabel);
        portPanel.add(portField);
        
//===========================================================================
        
        JPanel intervalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        intervalPanel.add(new JLabel("Check Interval: "));
        intervalPanel.add(intervalSpinner);
        
//===========================================================================
        
        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        soundPanel.add(soundCheckBox);
        
//===========================================================================
        
        JPanel settingsPanel = new JPanel(new GridLayout(1, 2));
        settingsPanel.add(intervalPanel);
        settingsPanel.add(soundPanel);
        
//===========================================================================
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(saveButton);
        buttonPanel.add(exitButton);
        
//===========================================================================
        
        JPanel centerPanel = new JPanel(new GridLayout(5, 1));
        centerPanel.add(usernamePanel);
        centerPanel.add(passwordPanel);
        centerPanel.add(serverPanel);
        centerPanel.add(portPanel);
        centerPanel.add(settingsPanel);
        
//===========================================================================
        
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setupMainFrame();
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
        setTitle("Notifier Settings");
        setVisible(true);
        setResizable(false);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("SAVE"))
            doSave();
        else if(e.getActionCommand().equals("VISIBLE"))
        {
            doVisible();
        }
        else if(e.getActionCommand().equals("EXIT"))
            doExit();
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
            
            // Check to see if each JTextField is filled properly. If not, display an error message,
            // and make JLabel correlating to the JTextField red with a star next to it.
            if(username.equals("") || password.equals("") || server.equals("") ||
               portField.getText().trim().equals(""))
            {
                JOptionPane.showMessageDialog(this, "Error: Fill in all text fields.");
            }
            else
            {
                notifier.saveProperties(username, password, server, port, interval, sound);
                
                dispose();
            }
        }
    }
    
    void doVisible()
    {
        if(visionCheck.isSelected())
            passwordField.setEchoChar((char)0);
        else
            passwordField.setEchoChar('•');
    }
    
    void doExit()
    {
        dispose();
        System.exit(0);
    }
}


