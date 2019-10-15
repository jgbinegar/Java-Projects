import java.io.*;
import java.net.*;

class ConnectionToClient
                implements Runnable
{
    MessageController   messageController;
    
    MessengerServer     server;
    
    User                user;
    
    UserList            usersList;
    
    Thread              runnable;
    
    ConnectionToClient(MessengerServer server, Socket socket, UserList usersList)
    {
        this.server = server;
        
        messageController = new MessageController(socket);
        
        this.usersList = usersList;
        
        System.out.println("ConnectionToClient: Connection Made");
        
        runnable = new Thread(this);
        runnable.start();
    }
    
    public void sendMessage(String message)
    {
        try
        {
            messageController.send(message);
        }
        catch(IOException ioe)
        {
            System.out.println("Unable to connect to client. Are you there?");
        }
    }
    
    @Override
    public void run()
    {
        String message;
        
        try
        {
            while(true)
            {
                message = messageController.receive();
                
                if(message == null)
                    throw new Exception();
                
                server.incomingTransmission(message, this);
            }
        }
        catch(IOException ioe)
        {
            System.out.println("Client disconnected.");
        }
        catch(Exception e)
        {
            System.out.println("User disconnected...");
            user.disconnected();
        }
    }
    
    void setUser(User user)
    {
        this.user = user;
    }
}
