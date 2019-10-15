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

//    /$$$$$$
//   /$$__  $$
//  | $$  \ $$ /$$   /$$  /$$$$$$  /$$   /$$  /$$$$$$
//  | $$  | $$| $$  | $$ /$$__  $$| $$  | $$ /$$__  $$
//  | $$  | $$| $$  | $$| $$$$$$$$| $$  | $$| $$$$$$$$
//  | $$/$$ $$| $$  | $$| $$_____/| $$  | $$| $$_____/
//  |  $$$$$$/|  $$$$$$/|  $$$$$$$|  $$$$$$/|  $$$$$$$
//   \____ $$$ \______/  \_______/ \______/  \_______/
//        \__/

class Queue
{
    Vector<WebPage>             pageList;
    WebPage                     currentPage;
    int                         listPosition;
    int                         maxRadius;
    int                         expTime;
    long                        startTime;
    
    Queue(int maxRadius, int expTime)
    {
        pageList        = new Vector<WebPage>();
        listPosition    = 0;
        this.maxRadius  = maxRadius;
        this.expTime    = expTime;
        startTime       = System.currentTimeMillis();
    }
    
    public boolean checkQueue(String str)
    {
        for(int i = 0; i<pageList.size(); i++)
        {
            if(inQueue(i, str))
                return true;
        }
        return false;
    }
    
    private boolean inQueue(int pos, String str)
    {
        return(pageList.elementAt(pos)).getURL().equals(str);
    }
    
    public boolean isInsideRadius()
    {
        return (currentPage.getRadius() + 1 <= maxRadius);
    }
    
    public boolean checkExpansion()
    {
        return (System.currentTimeMillis() - startTime < expTime);
    }
    
    public void sortQueue()
    {
        WebPage pageOne;
        WebPage pageTwo;
        
        for (int i = 0; i < pageList.size(); i++)
        {
            for (int j= 0; j < pageList.size() - i - 1; j++)
            {
                pageOne = pageList.elementAt(j);
                pageTwo = pageList.elementAt(j + 1);
                
                if(pageOne.getURL().compareTo(pageTwo.getURL()) > 0)
                {
                    pageList.setElementAt(pageTwo, j);
                    pageList.setElementAt(pageOne, j + 1);
                }
            }
        }
    }
    
    public void addEmail(String email)
    {
        currentPage.addEmail(email);
    }
    
    public void addPage(String url)
    {
        if(pageList.isEmpty())
            pageList.addElement(new WebPage(0, url));
        else if(!checkQueue(url) && isInsideRadius() && checkExpansion())
            pageList.addElement(new WebPage(currentPage.getRadius() + 1, url));
    }
    
    public WebPage nextPage()
    {
        currentPage = pageList.elementAt(listPosition++);
        return currentPage;
    }
    
    public boolean endOfQueue()
    {
        return (listPosition == pageList.size());
    }
    
//    ***DEPRECATED***
//    public void reset()
//    {
//        listPosition = 0;
//    }
    
    public Vector<String> getList()
    {
        Vector<String> temp = new Vector<String>();
        WebPage tempPage;
        Vector<String> tempVect;
        
        for(int i = 0; i < pageList.size(); i++)
        {
            tempPage = pageList.elementAt(i);
            tempVect = tempPage.printWebPage();
            
            for(int j = 0; j < tempVect.size(); j++)
            {
                temp.addElement(tempVect.elementAt(j));
            }
        }
        
        return temp;
    }
}

//   /$$$$$$$                                    /$$
//  | $$__  $$                                  |__/
//  | $$  \ $$  /$$$$$$  /$$$$$$/$$$$   /$$$$$$  /$$ /$$$$$$$   /$$$$$$$
//  | $$  | $$ /$$__  $$| $$_  $$_  $$ |____  $$| $$| $$__  $$ /$$_____/
//  | $$  | $$| $$  \ $$| $$ \ $$ \ $$  /$$$$$$$| $$| $$  \ $$|  $$$$$$
//  | $$  | $$| $$  | $$| $$ | $$ | $$ /$$__  $$| $$| $$  | $$ \____  $$
//  | $$$$$$$/|  $$$$$$/| $$ | $$ | $$|  $$$$$$$| $$| $$  | $$ /$$$$$$$/
//  |_______/  \______/ |__/ |__/ |__/ \_______/|__/|__/  |__/|_______/

class Domains
{
    private String domain;
    private Vector<String> usernames;
    
    Domains(String domain)
    {
        this.domain = domain;
        usernames = new Vector<String>();
    }
    
    public String getDomain()
    {
        return domain;
    }
    
    void addUsername(String user)
    {
        usernames.addElement(user);
    }
    
    public String getUsername(int pos)
    {
        return usernames.elementAt(pos);
    }
    
    public int getSize()
    {
        return usernames.size();
    }
    
    void sortUsers()
    {
        String strOne;
        String strTwo;
        
        for (int i = 0; i < usernames.size() - 1; i++)
        {
            for (int j= 0; j < usernames.size() - i - 1; j++)
            {
                strOne = usernames.elementAt(j);
                strTwo = usernames.elementAt(j + 1);
                
                if(strOne.compareTo(strTwo) > 0)
                {
                    usernames.setElementAt(strTwo, j);
                    usernames.setElementAt(strOne, j + 1);
                }
            }
        }
    }
    
    public int compareTo(Domains d)
    {
        return (this.getDomain().compareTo(d.getDomain()));
    }
}
