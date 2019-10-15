import java.net.*;
import java.io.*;

public class MessageController
{
    private BufferedReader br;
    private DataOutputStream dos;
    
    MessageController(String identifier, int portNumber)
    {
        try
        {
            Socket socket = new Socket(identifier, portNumber);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dos = new DataOutputStream(socket.getOutputStream());
        }
        catch(IOException ioe)
        {
            System.out.println("Error creating BuffereredReader and DOS");
        }
    }
    
    MessageController(Socket clientConnection)
    {
        try
        {
            Socket socket = clientConnection;
            
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dos = new DataOutputStream(socket.getOutputStream());
        }
        catch(IOException ioe)
        {
            System.out.println("Error creating BuffereredReader and DOS");
        }
    }
    
    public void send(String message) throws IOException
    {
        dos.writeBytes(message + "\n");
    
        System.out.println("Sent: " + message);

    }
    
    public String receive() throws IOException
    {
        String message = br.readLine();
        
        System.out.println("Received: " + message);
        return message;
    }
}
