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

class WinDialog extends JDialog
{
    WinDialog(String finalTime, int finalMoves)
    {
        JPanel mainPanel = new JPanel();
        JPanel labelPanel = new JPanel(new GridLayout(2, 1));
        JLabel time = new JLabel("Final Time: " + finalTime);
        JLabel moves = new JLabel("Moves: " + finalMoves);
        
        //mainPanel.setPreferredSize(new Dimension(217, 378));
        
        try
        {
            mainPanel.add(new JLabel(new ImageIcon(ImageIO.read(new File("." + File.separator + "images" + File.separator + "winnerIcon.png")))));
        }
        catch(IOException e)
        {
            System.out.println("Unable to load Dialog Icons.");
        }
        
        time.setHorizontalAlignment(JLabel.CENTER);
        moves.setHorizontalAlignment(JLabel.CENTER);
        
        labelPanel.add(time);
        labelPanel.add(moves);
        
        add(mainPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.SOUTH);
        
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
        setTitle("You win!");
        setVisible(true);
    }
}
