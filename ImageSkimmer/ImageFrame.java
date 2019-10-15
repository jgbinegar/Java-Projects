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



public class ImageFrame extends JFrame
                            implements ActionListener, DocumentListener, MouseListener, DropTargetListener
{
    JTextField          urlTextField;
    JButton             goButton;
    
    JList               imageList;
    
    DefaultListModel    dlm;
    
    ImageFrame()
    {
        urlTextField = new JTextField(25);
        urlTextField.getDocument().addDocumentListener(this);
        
        goButton = new JButton("Go");
        goButton.setActionCommand("GO");
        goButton.addActionListener(this);
        goButton.setEnabled(false);
        
        imageList = new JList();
        imageList.addMouseListener(this);
        
        JScrollPane myScrollPane;
        myScrollPane = new JScrollPane(imageList);
        myScrollPane.setPreferredSize(new Dimension(800, 400));
        
        JPanel urlPanel;
        urlPanel = new JPanel();
        urlPanel.add(new JLabel("URL: https://"));
        urlPanel.add(urlTextField);
        urlPanel.add(goButton);
        
        JPanel southPanel;
        southPanel = new JPanel(new GridLayout(1,2));
        southPanel.add(urlPanel);
        southPanel.add(goButton);
        
        JPanel centerPanel;
        centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(810, 410));
        centerPanel.add(myScrollPane);
        
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);
        
        DropTarget dropTarget;
        dropTarget = new DropTarget(myScrollPane, this);
        
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
        setTitle("Image Skimmer");
        setVisible(true);
        setResizable(false);
    }
    
    public void mouseClicked​(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
        {
            new ImagePopUp(dlm.elementAt(imageList.getSelectedIndex()).toString());
        }
    }
    
    public void mouseEntered​(MouseEvent e){}
    public void mouseExited​(MouseEvent e){}
    public void mousePressed​(MouseEvent e){}
    public void mouseReleased​(MouseEvent e){}
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("GO"))
            doGo();
    }
    
    public void changedUpdate(DocumentEvent e){}
    
    public void insertUpdate(DocumentEvent e)
    {
        checkURL();
    }
    
    public void removeUpdate(DocumentEvent e)
    {
        checkURL();
    }
    
    void doGo()
    {
        String tempStr = urlTextField.getText().trim();
        
        dlm = new DefaultListModel<String>();
        
        if(tempStr.startsWith("https://"))
        {
            urlConnection(tempStr, dlm);
        }
        else if(tempStr.startsWith("www."))
        {
            urlConnection("https://" + tempStr, dlm);
        }
        else
        {
            urlConnection("https://" + tempStr, dlm);
        }
        
        imageList.setModel(dlm);
        
        urlTextField.setText("");
        urlTextField.requestFocus();
    }
    
    void checkURL()
    {
        if(urlTextField.getText().trim().equals(""))
        {
            goButton.setEnabled(false);
        }
        else
            goButton.setEnabled(true);
    }
    
    public void drop​(DropTargetDropEvent dtde)
    {
        String               urlString;
        Transferable         transferableData;
        
        transferableData = dtde.getTransferable();
        
        try
        {
            if (transferableData.isDataFlavorSupported(DataFlavor.stringFlavor))
            {
                dtde.acceptDrop(DnDConstants.ACTION_COPY);
                urlString = (String)(transferableData.getTransferData(DataFlavor.stringFlavor));
                
                urlTextField.setText(urlString);
            }
        }
        catch (UnsupportedFlavorException ufe)
        {
            System.out.println("Unsupported file type!");
            ufe.printStackTrace();
        }
        catch (IOException ioe)
        {
            System.out.println("IOException found getting transerable data!");
            ioe.printStackTrace();
        }
    }
    
    public void dragEnter​(DropTargetDragEvent dtde){}
    public void dropActionChanged​(DropTargetDragEvent dtde){}
    public void dragExit​(DropTargetEvent dte){}
    public void dragOver​(DropTargetDragEvent dtde){}

    public static void urlConnection(String domain, DefaultListModel dlm)
    {
        try
        {
            new ParserDelegator().parse(
                    new InputStreamReader(
                                new URL(domain).openConnection().getInputStream()),
                                            new MyParserCallbackTagHandler(domain, dlm), true);
        }
        catch(MalformedURLException mue) // If given URL is in a bad form, displays message to Terminal and prints error stack trace.
        {
            System.out.println("Bad URL: -=> " + domain + " <=-"); // Displays
            mue.printStackTrace();
        }
        catch(IOException ioe) // If an error occurs receiving data from given website, displays message to Terminal and prints error stack trace.
        {
            System.out.println("Error reading website.");
            ioe.printStackTrace();
        }
    }
}
