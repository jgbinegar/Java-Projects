import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.net.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


class ImagePopUp extends JDialog
{
    ImagePopUp(String selectedURL)
    {
        ImagePanel panel = new ImagePanel(getRescaledImage(selectedURL));
        add(panel, BorderLayout.CENTER);
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
    
    public static BufferedImage getRescaledImage(String selectedURL)
    {
        try
        {
            BufferedImage buffIm = ImageIO.read(new URL(selectedURL));
            
            int h = buffIm.getHeight();
            int w = buffIm.getWidth();
            
            if(w > 400 || 400 < h)
            {
                BufferedImage scaled = new BufferedImage(w/4, h/4, buffIm.getType());
                
                Graphics2D g = scaled.createGraphics();
                g.drawImage(buffIm, 0, 0, w/4, h/4, null);
                g.dispose();
                
                return scaled;
            }
            else if(w < 200 || 200 > h)
            {
                BufferedImage scaled = new BufferedImage(w*2, h*2, buffIm.getType());
                
                Graphics2D g = scaled.createGraphics();
                g.drawImage(buffIm, 0, 0, w*2, h*2, null);
                g.dispose();
                
                return scaled;
            }
            else
            {
                return buffIm;
            }
        }
        catch(MalformedURLException moe)
        {
            System.out.println("Error: Unable to retrieve image from selected URL.");
            moe.printStackTrace();
        }
        catch(IOException ioe)
        {
            System.out.println("Error with I/O");
            ioe.printStackTrace();
        }
        
        return null;
    }
}

