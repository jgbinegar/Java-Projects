import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;
import javax.swing.*;
import java.util.regex.*;
import java.util.Vector;

class AnchorTagHandler extends HTMLEditorKit.ParserCallback
{
    final Pattern pattern = Pattern.compile("[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");
    
    Queue queue;
    
    public AnchorTagHandler(Queue queue)
    {
        this.queue = queue;
    }
    
    @Override
    public void handleStartTag(HTML.Tag tag, MutableAttributeSet attSet, int pos)
    {
        Object  attribute;
        String  attStr;
        
        if(tag == HTML.Tag.A)
        {
            attribute = attSet.getAttribute(HTML.Attribute.HREF);
            
            if(attribute != null)
            {
                attStr = attribute.toString();
                
                if(attStr.startsWith("mailto:"))
                    queue.addEmail(attStr.substring(7));
                else if(attStr.startsWith("http"))
                    queue.addPage(attStr);
                else if (attStr.startsWith("//"))
                    queue.addPage("https:" + attStr);
            }
        }
    } // end of handleStartTag
    
    @Override
    public void handleText(char[] data, int pos)
    {
        boolean done = false;
        String text = new String(data);
        Matcher matcher = pattern.matcher(text);
        
        while(!done)
        {
            if(matcher.find())
            {
                queue.addEmail(text.substring(matcher.start(), matcher.end()));
                matcher.region(matcher.end(), text.length());
            }
            else
                done = true;
        }
    } // end of handleText
}// end of class TagHandler
