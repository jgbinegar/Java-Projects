import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import javax.swing.*;

class MyParserCallbackTagHandler extends HTMLEditorKit.ParserCallback
{
    Vector urlQueue;
    WebPage webPage;
    int insertRadius;
    boolean expTimeExpired;
    int totalRadius;
    
    public MyParserCallbackTagHandler(Vector urlQueue, WebPage webPage, boolean expTimeExpired, int totalRadius)
    {
        this.urlQueue = urlQueue;
        this.webPage = webPage;
        this.insertRadius = webPage.getRadius() + 1;
        this.expTimeExpired = expTimeExpired;
        this.totalRadius = totalRadius
    }
    
    @Override
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attSet, int pos)
    {
        Object          attribute;
        String attStr;
        
            // Write code here that will handle finding links and emails
            if(tag == HTML.Tag.A)
            {
                attribute = attSet.getAttribute(HTML.Attribute.HREF);
                
                if(attribute != null)
                {
                    attStr = attribute.toString();
                    
                    if(attStr.startsWith("mailto:"))
                    {
                        webPage.addEmail(attStr.subString(6));
                    }
                    else if(!expTimeExpired && insertRadius != totalRadius) // Testing to see if anymore WebPage objects should be added
                    {
                        if(attStr.startsWith("http"))
                        {
                            urlQueue.add(new WebPage(insertRadius, attStr))
                        }
                        else if(attStr.startsWith("//"))
                            urlQueue.add(new WebPage(insertRadius, "https:" + attStr))
                    }
                    else
                    {
                     // DO SOMETHING
                    }
                }
            }
        
        } // end of if(tag == HTML.Tag.IMG)
    }// end of handleSimpleTag
}// end of class TagHandler

