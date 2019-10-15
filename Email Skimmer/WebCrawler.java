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

class WebCrawler
{
    int maxTime;
    long startTime;
    
    Queue queue;
    
    AnchorTagHandler tagHandler;
    
    WebPage currentPage;
    
    WebCrawler(String seedURL, int maxTime, Queue queue)
    {
        this.queue = queue;
        this.maxTime = maxTime;
        
        this.startTime = System.currentTimeMillis();
        
        this.tagHandler = new AnchorTagHandler(this.queue);
        
        queue.addPage(seedURL);
        
        crawl();
    }
    
    boolean reachedMaxTime()
    {
        return (System.currentTimeMillis() - startTime >= maxTime);
    }
    
    void skimURL()
    {
        try
        {
            URL                 url = new URL(currentPage.getURL());
            URLConnection       urlConnection = url.openConnection();
            InputStreamReader   isr = new InputStreamReader(urlConnection.getInputStream());
            
            new ParserDelegator().parse(isr, tagHandler, true);
        }
        catch(MalformedURLException mue)
        {
            System.out.println("Error with URL: " + currentPage.getURL());
            mue.printStackTrace();
        }
        catch(IOException ioe)
        {
            System.out.println("TKL: Some IO Exception");
            ioe.printStackTrace();
        }
    }
    
    void crawl()
    {
        while(!reachedMaxTime() && !queue.endOfQueue())
        {
            currentPage = queue.nextPage();
            skimURL();
            currentPage.sortDomains();
        }
        queue.sortQueue();
        
    }
}
