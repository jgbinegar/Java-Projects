import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.*;
import com.sun.mail.imap.*;

class EmailChecker
{
//    String      serverName;
//    String      username;
//    String      password;
//    String      protocolProvider;
//    int         portNumber;
    
    Session         session;
    Store           store;
    Folder          mailFolder;
    
    EmailChecker(String serverName, String username, String password, String protocolProvider, int portNumber)
    {
//        this.serverName          = serverName;
//        this.username            = username;
//        this.password            = password;
//        this.protocolProvider    = protocolProvider;
//        this.portNumber          = portNumber;
//        Local variables not needed due to logging in, inside of Constructor
        
        try
        {
            session = Session.getDefaultInstance(new Properties(), null);//authenticator);
            
            store = session.getStore(protocolProvider);
            store.connect(serverName, username, password);
            
            mailFolder = store.getFolder("INBOX");
        }
        catch(NoSuchProviderException nspe)
        {
            System.out.println("TKL - No such email protocol provider: " + protocolProvider);
        }
        catch(MessagingException me)
        {
            System.out.println("TKL - Messageing exception:");
            me.printStackTrace();
        }
        
    } // end of main(...)
    
    void checkMail(boolean soundToggle, String audioClip)
    {
        System.out.println("Inside EmailChecker: checking email");
        
        try
        {
        mailFolder.open(Folder.READ_WRITE);
        
        if(mailFolder.hasNewMessages())
        {
            new AlertPopup(mailFolder.getNewMessageCount());
            
            if(soundToggle)
                new AlertSound(audioClip);
        }
        
        mailFolder.close();
        }
        catch(MessagingException me)
        {
            System.out.println("Error retrieving message.");
        }
        

    }
}

