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



class AlertPopup extends JDialog
                        implements ActionListener
{
    AlertPopup(int numOfEmails)
    {
        
        //      /$$$$$ /$$$$$$$              /$$     /$$
        //     |__  $$| $$__  $$            | $$    | $$
        //        | $$| $$  \ $$ /$$   /$$ /$$$$$$ /$$$$$$    /$$$$$$  /$$$$$$$
        //        | $$| $$$$$$$ | $$  | $$|_  $$_/|_  $$_/   /$$__  $$| $$__  $$
        //   /$$  | $$| $$__  $$| $$  | $$  | $$    | $$    | $$  \ $$| $$  \ $$
        //  | $$  | $$| $$  \ $$| $$  | $$  | $$ /$$| $$ /$$| $$  | $$| $$  | $$
        //  |  $$$$$$/| $$$$$$$/|  $$$$$$/  |  $$$$/|  $$$$/|  $$$$$$/| $$  | $$
        //   \______/ |_______/  \______/    \___/   \___/   \______/ |__/  |__/
        
        
        //===========================================================================
        
        JButton okButton = new JButton("OK");
        okButton.addActionListener(this);
        okButton.setActionCommand("OK");
        this.getRootPane().setDefaultButton(okButton);
        
        //===========================================================================
        
        
        //      /$$$$$ /$$$$$$$                               /$$
        //     |__  $$| $$__  $$                             | $$
        //        | $$| $$  \ $$ /$$$$$$  /$$$$$$$   /$$$$$$ | $$
        //        | $$| $$$$$$$/|____  $$| $$__  $$ /$$__  $$| $$
        //   /$$  | $$| $$____/  /$$$$$$$| $$  \ $$| $$$$$$$$| $$
        //  | $$  | $$| $$      /$$__  $$| $$  | $$| $$_____/| $$
        //  |  $$$$$$/| $$     |  $$$$$$$| $$  | $$|  $$$$$$$| $$
        //   \______/ |__/      \_______/|__/  |__/ \_______/|__/
        
        
        //===========================================================================
        
        JPanel numberPanel = new JPanel();
        numberPanel.add(new JLabel("You have (" + numOfEmails + ") new email(s)."));
        
        //===========================================================================
        
        JPanel messagesPanel = new JPanel();
        messagesPanel.add(numberPanel);
        
        //===========================================================================
        
        
        add(messagesPanel, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
        
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
        setTitle("Email Received");
        setVisible(true);
        setResizable(false);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("OK"))
            dispose();
    }
}


