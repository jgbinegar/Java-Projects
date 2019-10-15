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



class ListDialog extends JDialog
                    implements ActionListener
{
    Queue               queue;
    MyPrintableData     myPrintableDataObject;
    
    ListDialog(Queue queue)
    {
        this.queue = queue;
        
        
//      /$$$$$ /$$$$$$$              /$$     /$$
//     |__  $$| $$__  $$            | $$    | $$
//        | $$| $$  \ $$ /$$   /$$ /$$$$$$ /$$$$$$    /$$$$$$  /$$$$$$$
//        | $$| $$$$$$$ | $$  | $$|_  $$_/|_  $$_/   /$$__  $$| $$__  $$
//   /$$  | $$| $$__  $$| $$  | $$  | $$    | $$    | $$  \ $$| $$  \ $$
//  | $$  | $$| $$  \ $$| $$  | $$  | $$ /$$| $$ /$$| $$  | $$| $$  | $$
//  |  $$$$$$/| $$$$$$$/|  $$$$$$/  |  $$$$/|  $$$$/|  $$$$$$/| $$  | $$
//   \______/ |_______/  \______/    \___/   \___/   \______/ |__/  |__/
        
        JButton printButton;
        printButton = new JButton("Print");
        printButton.addActionListener(this);
        printButton.setActionCommand("PRINT");
        
        JButton exitButton;
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setActionCommand("EXIT");
        
//      /$$$$$ /$$       /$$             /$$
//     |__  $$| $$      |__/            | $$
//        | $$| $$       /$$  /$$$$$$$ /$$$$$$
//        | $$| $$      | $$ /$$_____/|_  $$_/
//   /$$  | $$| $$      | $$|  $$$$$$   | $$
//  | $$  | $$| $$      | $$ \____  $$  | $$ /$$
//  |  $$$$$$/| $$$$$$$$| $$ /$$$$$$$/  |  $$$$/
//   \______/ |________/|__/|_______/    \___/
        
        JList display;
        display = new JList(queue.getList());
        display.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
//      /$$$$$  /$$$$$$                                /$$ /$$ /$$$$$$$
//     |__  $$ /$$__  $$                              | $$| $$| $$__  $$
//        | $$| $$  \__/  /$$$$$$$  /$$$$$$   /$$$$$$ | $$| $$| $$  \ $$ /$$$$$$  /$$$$$$$   /$$$$$$
//        | $$|  $$$$$$  /$$_____/ /$$__  $$ /$$__  $$| $$| $$| $$$$$$$/|____  $$| $$__  $$ /$$__  $$
//   /$$  | $$ \____  $$| $$      | $$  \__/| $$  \ $$| $$| $$| $$____/  /$$$$$$$| $$  \ $$| $$$$$$$$
//  | $$  | $$ /$$  \ $$| $$      | $$      | $$  | $$| $$| $$| $$      /$$__  $$| $$  | $$| $$_____/
//  |  $$$$$$/|  $$$$$$/|  $$$$$$$| $$      |  $$$$$$/| $$| $$| $$     |  $$$$$$$| $$  | $$|  $$$$$$$
//   \______/  \______/  \_______/|__/       \______/ |__/|__/|__/      \_______/|__/  |__/ \_______/
        
        JScrollPane scrollPane;
        scrollPane = new JScrollPane(display);
        scrollPane.setPreferredSize(new Dimension(450, 600));
        
//      /$$$$$ /$$$$$$$                               /$$
//     |__  $$| $$__  $$                             | $$
//        | $$| $$  \ $$ /$$$$$$  /$$$$$$$   /$$$$$$ | $$
//        | $$| $$$$$$$/|____  $$| $$__  $$ /$$__  $$| $$
//   /$$  | $$| $$____/  /$$$$$$$| $$  \ $$| $$$$$$$$| $$
//  | $$  | $$| $$      /$$__  $$| $$  | $$| $$_____/| $$
//  |  $$$$$$/| $$     |  $$$$$$$| $$  | $$|  $$$$$$$| $$
//   \______/ |__/      \_______/|__/  |__/ \_______/|__/
        
        JPanel centerPanel;
        centerPanel = new JPanel();
        centerPanel.add(scrollPane);
        centerPanel.setPreferredSize(new Dimension(460, 610));
        
        JPanel buttonPanel;
        buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(printButton);
        buttonPanel.add(exitButton);
        
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
        
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
        setTitle("Selected Image");
        setVisible(true);
        setResizable(false);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("PRINT"))
        {
            try
            {
                doPrint();
            }
            catch(PrinterException exc)
            {
                System.out.println("Error Printing...");
            }
        }
        else if(e.getActionCommand().equals("EXIT"))
            doExit();
    }
    
    void doPrint() throws PrinterException
    {
        
        MyPrintableData myPrintableDataObject = new MyPrintableData(queue.getList());
        
        PrinterJob pj;
        PageFormat pageFormat;
        PageFormat defaultPageFormat;
        
        pj = PrinterJob.getPrinterJob();
        defaultPageFormat = pj.defaultPage();
        pageFormat = pj.pageDialog(defaultPageFormat);
        
        if(defaultPageFormat != pageFormat)
        {
            pj.setPrintable(myPrintableDataObject, pageFormat);
            if(pj.printDialog())
            {
                System.out.println("Ready to print...");
                pj.print();
            }
        }
    }
    
    public void doExit()
    {
        dispose();
    }
}

