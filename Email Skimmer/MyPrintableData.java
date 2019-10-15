import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.util.Vector;

class MyPrintableData extends JPanel
                        implements Printable
{
    Vector<String> printList;
    final int fontSize = 12;
    
    public MyPrintableData(Vector<String> printList)
    {
        this.printList = printList;
    }
    
//    public void paintComponant(Graphics g)
//    {
//
//    }
    
    public int print(Graphics g, PageFormat pf, int pageIndex)
    {
        Graphics2D g2;
        
        int linesPerPage = ((int)(pf.getImageableHeight() + fontSize + 2) / (fontSize + 2)) - 5;
        
        if(pageIndex > (printList.size() / linesPerPage))
            return Printable.NO_SUCH_PAGE;
        
        g2 = (Graphics2D)g;
        g2.translate(pf.getImageableX(), pf.getImageableY());
        g2.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        double yPos = pf.getImageableY();
        double xPos = pf.getImageableX();
            
        for(int i = linesPerPage * pageIndex; i < printList.size() && i < linesPerPage * (pageIndex + 1); i++)
        {
            g2.drawString(printList.elementAt(i), (int)xPos, (int)yPos);
            yPos += fontSize + 2;
        }
        
        g2.drawString("" + (pageIndex + 1), (int)(pf.getImageableX() + pf.getImageableWidth()) / 2, (int)pf.getImageableHeight());
        
        return java.awt.print.Printable.PAGE_EXISTS;
    }
}

