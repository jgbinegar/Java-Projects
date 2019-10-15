



class Buddy
{
    String      buddyID;
    boolean     isOnline;
    ChatWindow  chat;
    
    
    
    Buddy(String buddyID, String isOnline)
    {
        this.buddyID = buddyID;
        this.isOnline = Boolean.parseBoolean(isOnline);
    }
    
    String getBuddyID()
    {
        return buddyID;
    }
    
    void changeStatus()
    {
        if(isOnline)
            isOnline = false;
        else
            isOnline = true;
    }
    
    void openChatWindow(String userID, ConnectionToServer cts)
    {
        if(chat == null)
            chat = new ChatWindow(userID, buddyID, cts);
    }
    
    void displayMessage(String userID, String message, ConnectionToServer cts)
    {
        if(chat == null)
            chat = new ChatWindow(userID, buddyID, cts);
        
        chat.receiveMessage(message);
    }
    
    @Override
    public String toString()
    {
        if(isOnline)
            return "(O) " + buddyID;
            
        return "(X) " + buddyID;
    }
}
