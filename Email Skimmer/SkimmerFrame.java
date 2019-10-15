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


class SkimmerFrame extends JFrame
                    implements ActionListener, DocumentListener
{
    JTextField urlTextField;

//==========================================================================
    
    JSpinner expansionMinutes;
    JSpinner maximumMinutes;
    JSpinner radiusSpinner;
    
//==========================================================================
    
    JComboBox<Integer> expansionSeconds;
    JComboBox<Integer> maximumSeconds;
    
//==========================================================================
    
    JButton startButton; // Needs declared here to enable and disable
    
//==========================================================================
    
    final Integer[] secondsList = new Integer[] {0, 15, 30, 45};
    
    final int EXPANSION_TIME = 300;
    final int MAXIMUM_TIME = 600;
    
    SkimmerFrame()
    {
//      /$$$$$ /$$$$$$$$                    /$$     /$$$$$$$$ /$$           /$$       /$$
//     |__  $$|__  $$__/                   | $$    | $$_____/|__/          | $$      | $$
//        | $$   | $$  /$$$$$$  /$$   /$$ /$$$$$$  | $$       /$$  /$$$$$$ | $$  /$$$$$$$
//        | $$   | $$ /$$__  $$|  $$ /$$/|_  $$_/  | $$$$$   | $$ /$$__  $$| $$ /$$__  $$
//   /$$  | $$   | $$| $$$$$$$$ \  $$$$/   | $$    | $$__/   | $$| $$$$$$$$| $$| $$  | $$
//  | $$  | $$   | $$| $$_____/  >$$  $$   | $$ /$$| $$      | $$| $$_____/| $$| $$  | $$
//  |  $$$$$$/   | $$|  $$$$$$$ /$$/\  $$  |  $$$$/| $$      | $$|  $$$$$$$| $$|  $$$$$$$
//   \______/    |__/ \_______/|__/  \__/   \___/  |__/      |__/ \_______/|__/ \_______/
        
        urlTextField = new JTextField(22);
        urlTextField.getDocument().addDocumentListener(this);
        
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
        
        radiusSpinner    = new JSpinner(new SpinnerNumberModel(0, 0, 25, 1));
        radiusSpinner.setValue(3);
        expansionMinutes = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));
        expansionMinutes.setValue(2);
        maximumMinutes   = new JSpinner(new SpinnerNumberModel(0, 0, 60, 1));
        maximumMinutes.setValue(5);
        // Find out how to make JSpinner not editable
        
//      /$$$$$  /$$$$$$                          /$$                 /$$$$$$$
//     |__  $$ /$$__  $$                        | $$                | $$__  $$
//        | $$| $$  \__/  /$$$$$$  /$$$$$$/$$$$ | $$$$$$$   /$$$$$$ | $$  \ $$  /$$$$$$  /$$   /$$
//        | $$| $$       /$$__  $$| $$_  $$_  $$| $$__  $$ /$$__  $$| $$$$$$$  /$$__  $$|  $$ /$$/
//   /$$  | $$| $$      | $$  \ $$| $$ \ $$ \ $$| $$  \ $$| $$  \ $$| $$__  $$| $$  \ $$ \  $$$$/
//  | $$  | $$| $$    $$| $$  | $$| $$ | $$ | $$| $$  | $$| $$  | $$| $$  \ $$| $$  | $$  >$$  $$
//  |  $$$$$$/|  $$$$$$/|  $$$$$$/| $$ | $$ | $$| $$$$$$$/|  $$$$$$/| $$$$$$$/|  $$$$$$/ /$$/\  $$
//   \______/  \______/  \______/ |__/ |__/ |__/|_______/  \______/ |_______/  \______/ |__/  \__/

        expansionSeconds = new JComboBox(secondsList); // add secondsList as array
        expansionSeconds.setSelectedIndex(2);
        maximumSeconds   = new JComboBox(secondsList); // add secondsList as array
        
//      /$$$$$ /$$$$$$$              /$$     /$$
//     |__  $$| $$__  $$            | $$    | $$
//        | $$| $$  \ $$ /$$   /$$ /$$$$$$ /$$$$$$    /$$$$$$  /$$$$$$$   /$$$$$$$
//        | $$| $$$$$$$ | $$  | $$|_  $$_/|_  $$_/   /$$__  $$| $$__  $$ /$$_____/
//   /$$  | $$| $$__  $$| $$  | $$  | $$    | $$    | $$  \ $$| $$  \ $$|  $$$$$$
//  | $$  | $$| $$  \ $$| $$  | $$  | $$ /$$| $$ /$$| $$  | $$| $$  | $$ \____  $$
//  |  $$$$$$/| $$$$$$$/|  $$$$$$/  |  $$$$/|  $$$$/|  $$$$$$/| $$  | $$ /$$$$$$$/
//   \______/ |_______/  \______/    \___/   \___/   \______/ |__/  |__/|_______/
        
        startButton = new JButton("Start");
        startButton.setEnabled(false);
        startButton.addActionListener(this);
        startButton.setActionCommand("START");
        
//==========================================================================
        
        JButton exitButton;
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setActionCommand("EXIT");
        
//      /$$$$$ /$$$$$$$                               /$$
//     |__  $$| $$__  $$                             | $$
//        | $$| $$  \ $$ /$$$$$$  /$$$$$$$   /$$$$$$ | $$
//        | $$| $$$$$$$/|____  $$| $$__  $$ /$$__  $$| $$
//   /$$  | $$| $$____/  /$$$$$$$| $$  \ $$| $$$$$$$$| $$
//  | $$  | $$| $$      /$$__  $$| $$  | $$| $$_____/| $$
//  |  $$$$$$/| $$     |  $$$$$$$| $$  | $$|  $$$$$$$| $$
//   \______/ |__/      \_______/|__/  |__/ \_______/|__/
        
        JPanel urlPanel;
        urlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        urlPanel.add(new JLabel("Seed URL:"));
        urlPanel.add(urlTextField);
        
//==========================================================================
        
        JPanel expansionPanel;
        expansionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        expansionPanel.add(new JLabel("Expansion Time: "));
        expansionPanel.add(expansionMinutes);
        expansionPanel.add(new JLabel("minutes,"));
        expansionPanel.add(expansionSeconds);
        expansionPanel.add(new JLabel("seconds."));
        
//==========================================================================
        
        JPanel maximumPanel;
        maximumPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maximumPanel.add(new JLabel("Maximum Time:  "));
        maximumPanel.add(maximumMinutes);
        maximumPanel.add(new JLabel("minutes,"));
        maximumPanel.add(maximumSeconds);
        maximumPanel.add(new JLabel("seconds."));
        
//==========================================================================
        
        JPanel radPanel;
        radPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radPanel.add(new JLabel("Radius:"));
        radPanel.add(radiusSpinner);
        radPanel.add(startButton);
        radPanel.add(exitButton);
        
//==========================================================================
        
        JPanel centerPanel;
        centerPanel = new JPanel(new GridLayout(4,1));
        centerPanel.add(urlPanel);
        centerPanel.add(expansionPanel);
        centerPanel.add(maximumPanel);
        centerPanel.add(radPanel);
        
//    /$$$$$$    /$$     /$$
//   /$$__  $$  | $$    | $$
//  | $$  \ $$ /$$$$$$  | $$$$$$$   /$$$$$$   /$$$$$$
//  | $$  | $$|_  $$_/  | $$__  $$ /$$__  $$ /$$__  $$
//  | $$  | $$  | $$    | $$  \ $$| $$$$$$$$| $$  \__/
//  | $$  | $$  | $$ /$$| $$  | $$| $$_____/| $$
//  |  $$$$$$/  |  $$$$/| $$  | $$|  $$$$$$$| $$
//   \______/    \___/  |__/  |__/ \_______/|__/
        
        add(centerPanel, BorderLayout.WEST);
        
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
        setTitle("Email Skimmer");
        setVisible(true);
        setResizable(false);
    }

//    /$$$$$$              /$$     /$$                     /$$$$$$$$                              /$$
//   /$$__  $$            | $$    |__/                    | $$_____/                             | $$
//  | $$  \ $$  /$$$$$$$ /$$$$$$   /$$  /$$$$$$  /$$$$$$$ | $$    /$$    /$$ /$$$$$$  /$$$$$$$  /$$$$$$
//  | $$$$$$$$ /$$_____/|_  $$_/  | $$ /$$__  $$| $$__  $$| $$$$$|  $$  /$$//$$__  $$| $$__  $$|_  $$_/
//  | $$__  $$| $$        | $$    | $$| $$  \ $$| $$  \ $$| $$__/ \  $$/$$/| $$$$$$$$| $$  \ $$  | $$
//  | $$  | $$| $$        | $$ /$$| $$| $$  | $$| $$  | $$| $$     \  $$$/ | $$_____/| $$  | $$  | $$ /$$
//  | $$  | $$|  $$$$$$$  |  $$$$/| $$|  $$$$$$/| $$  | $$| $$$$$$$$\  $/  |  $$$$$$$| $$  | $$  |  $$$$/
//  |__/  |__/ \_______/   \___/  |__/ \______/ |__/  |__/|________/ \_/    \_______/|__/  |__/   \___/

    public void actionPerformed(ActionEvent e)
    {
        // Write code for when startButton is clicked
        if(e.getActionCommand().equals("START"))
            doStart();
        else if(e.getActionCommand().equals("EXIT"))
            doExit();
    }
    
    void doStart()
    {
        String seedURL = urlTextField.getText().trim();
        int maximumRadius = (Integer)radiusSpinner.getValue();
        int expansionTime = EXPANSION_TIME * 1000;
        int maximumTime = MAXIMUM_TIME * 1000;
        
        Queue queue = new Queue(maximumRadius, expansionTime);
    
        new WebCrawler(seedURL, maximumTime, queue);
        
        new ListDialog(queue);
    }
    
    void doExit()
    {
        System.exit(0);
    }
    
//   /$$$$$$$                      /$$$$$$$$                              /$$
//  | $$__  $$                    | $$_____/                             | $$
//  | $$  \ $$  /$$$$$$   /$$$$$$$| $$    /$$    /$$ /$$$$$$  /$$$$$$$  /$$$$$$
//  | $$  | $$ /$$__  $$ /$$_____/| $$$$$|  $$  /$$//$$__  $$| $$__  $$|_  $$_/
//  | $$  | $$| $$  \ $$| $$      | $$__/ \  $$/$$/| $$$$$$$$| $$  \ $$  | $$
//  | $$  | $$| $$  | $$| $$      | $$     \  $$$/ | $$_____/| $$  | $$  | $$ /$$
//  | $$$$$$$/|  $$$$$$/|  $$$$$$$| $$$$$$$$\  $/  |  $$$$$$$| $$  | $$  |  $$$$/
//  |_______/  \______/  \_______/|________/ \_/    \_______/|__/  |__/   \___/  

    public void insertUpdate(DocumentEvent e)
    {
        checkTextField(); // Checks if urlTextField is empty
    }

    public void removeUpdate(DocumentEvent e)
    {
        checkTextField(); // Checks if urlTextField is empty
    }

    public void changedUpdate(DocumentEvent e){}

    void checkTextField()
    {
        if(urlTextField.getText().trim().equals(""))
            startButton.setEnabled(false);
        else
            startButton.setEnabled(true);
    }
}
