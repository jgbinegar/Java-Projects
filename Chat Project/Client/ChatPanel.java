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
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;

final class ChatPanel extends JPanel
{
    JScrollPane scroll;
    
    public ChatPanel(int width,int height)
    {
        setPreferredSize(new Dimension(width, height));
        
        scroll = new JScrollPane();
        
        scroll.setPreferredSize(new Dimension(width, height));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getViewport().add(this);
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new LineBorder(Color.BLACK));
        setBackground(Color.lightGray);
    }
    public void addMessage(String message, String alignment)
    {
        JLabel messageLabel = new JLabel("<html>" + message + "</html>");
        //messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        messageLabel.setOpaque(true);
        messageLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        if(alignment.equals("RIGHT"))
        {
            messageLabel.setBackground(Color.pink);
            messageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            messageLabel.setHorizontalTextPosition(JLabel.RIGHT);
        }
        else
        {
            messageLabel.setBackground(Color.CYAN);
            messageLabel.setHorizontalAlignment(SwingConstants.LEFT);
            messageLabel.setHorizontalTextPosition(JLabel.LEFT);
        }
        
        add(messageLabel);
        
        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setMaximumSize(new Dimension(0, 5));
        add(sep);
        
        revalidate();
    }
}
