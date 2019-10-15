import java.util.Vector;
import java.util.Arrays;
import javax.swing.DefaultListModel;
import java.lang.*;

class WebPage
{
    Vector<String>      emailList;
    Vector<Domains>     domainList;
    int                 pageRadius;
    String              pageURL;
    
    WebPage(int pageRadius, String pageURL)
    {
        this.pageRadius = pageRadius;
        this.pageURL = pageURL;
        domainList = new Vector<Domains>();
        
        emailList = new Vector<String>();
    }
    
    public void addEmail(String email)
    {
        // Check if emails domain is inside
        // domainList, if not, add domain and
        // add email, else add email to domain
        
        String[] splitEmail = email.split("@");
        Domains tempDomain;
        
        for(int i = 0; i < domainList.size(); i++)
        {
            tempDomain = domainList.elementAt(i);
            
            if(splitEmail[1].equals(tempDomain.getDomain()))
            {
                tempDomain.addUsername(splitEmail[0]);
                return;
            }
        }
        
        tempDomain = new Domains(splitEmail[1]);
        
        domainList.addElement(tempDomain);
        tempDomain.addUsername(splitEmail[0]);
    }

    public int getRadius()
    {
        return pageRadius;
    }
    
    public String getURL()
    {
        return pageURL;
    }
    
    public int getSize()
    {
        return domainList.size();
    }
    
    public Vector<String> printWebPage()
    {
        Vector<String> webpage;
        Domains tempDomain;
        
        webpage = new Vector<String>();
        
        sortDomains();
        
        webpage.addElement("****************************************");
        webpage.addElement(pageURL);
        webpage.addElement("========================================");
        webpage.addElement(" ");
        
        for(int i = 0; i < domainList.size(); i++)
        {
            tempDomain = domainList.elementAt(i);
            tempDomain.sortUsers();
            
            for(int j = 0; j < tempDomain.getSize(); j++)
            {
                String tempStr = tempDomain.getUsername(j) + "@" + tempDomain.getDomain();
                
                webpage.addElement(tempStr);
            }
        }
        
        webpage.addElement(" ");
        
        return webpage;
    }
    
    public boolean isEmpty()
    {
        return domainList.isEmpty();
    }
    
    void sortDomains()
    {
        Domains domainOne;
        Domains domainTwo;
        
        for (int i = 0; i < domainList.size() - 1; i++)
        {
            for (int j= 0; j < domainList.size() - 1; j++)
            {
                domainOne = domainList.elementAt(j);
                domainTwo = domainList.elementAt(j + 1);
                
                if(domainOne.compareTo(domainTwo) > 0)
                {
                    domainList.setElementAt(domainTwo, j);
                    domainList.setElementAt(domainOne, j + 1);
                }
            }
        }
    }
}
