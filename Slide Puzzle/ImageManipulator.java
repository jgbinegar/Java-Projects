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

class ImageManipulator
{
    public static ImageIcon getIcon(String fileName, int scale)
    {
        try
        {
            File tempFile = new File("." + File.separator + "images" + File.separator + fileName);
            BufferedImage tempBuff = ImageIO.read(tempFile);
            
            BufferedImage resizedImage = new BufferedImage(scale, scale, tempBuff.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(tempBuff.getSubimage(0, 0, 512, 512), 0, 0, scale, scale, null);
            g.dispose();
            
            return new ImageIcon(resizedImage);
        }
        catch(IOException e)
        {
            System.out.println("Fatal Error: Unable to create icon using " + fileName + ".");
            System.out.println("Exiting now...");
            System.exit(1);
        }
        
        return null;
    }
    
    public static BufferedImage getPuzzleImage(File file, int scale)
    {
        BufferedImage tempBuff;
        
        int x = 0;
        int y = 0;
        int h;
        int w;
        
        BufferedImage newImage;
        Graphics2D g;
        
        try
        {
            tempBuff = ImageIO.read(file);
            newImage = new BufferedImage(scale, scale, tempBuff.getType());
            g = newImage.createGraphics();
            h = tempBuff.getHeight();
            w = tempBuff.getWidth();
        }
        catch(IOException e)
        {
            System.out.println("Error loading file " + file.getName());
            return null;
        }
        
        if(h < w)
        {
            y = 0;
            x = (w - h) / 2;
            g.drawImage(tempBuff.getSubimage(x, y, h, h), 0, 0, scale, scale, null);
        }
        else if(w < h)
        {
            x = 0;
            y = (h - w) / 2;
            g.drawImage(tempBuff.getSubimage(x, y, w, w), 0, 0, scale, scale, null);
        }
        else
            g.drawImage(tempBuff, 0, 0, scale, scale, null);
        
        g.dispose();
        
        return newImage;
    }
}
