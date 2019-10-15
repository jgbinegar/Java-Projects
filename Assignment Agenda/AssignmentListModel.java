import javax.swing.*;
import java.io.*;


class AssignmentListModel
                implements DataManager
{
    private DefaultListModel<Assignment> dlm;
    
    boolean needsSaved;
    
    AssignmentListModel()
    {
        dlm = new DefaultListModel<Assignment>();
        
        needsSaved = false;
    }
    
    public void store(DataOutputStream dos) throws IOException
    {
        needsSaved = false;
        try
        {
        dos.writeInt(dlm.size());
        
        for(int i = 0; i < dlm.size(); i++)
            dlm.elementAt(i).store(dos);
        }
        catch(IOException poo)
        {
            System.out.println("Error storing List Model.");
        }
    }
    
    //   /$$$$$$$  /$$       /$$      /$$       /$$$$$$$$                              /$$     /$$
    //  | $$__  $$| $$      | $$$    /$$$      | $$_____/                             | $$    |__/
    //  | $$  \ $$| $$      | $$$$  /$$$$      | $$    /$$   /$$ /$$$$$$$   /$$$$$$$ /$$$$$$   /$$  /$$$$$$  /$$$$$$$   /$$$$$$$
    //  | $$  | $$| $$      | $$ $$/$$ $$      | $$$$$| $$  | $$| $$__  $$ /$$_____/|_  $$_/  | $$ /$$__  $$| $$__  $$ /$$_____/
    //  | $$  | $$| $$      | $$  $$$| $$      | $$__/| $$  | $$| $$  \ $$| $$        | $$    | $$| $$  \ $$| $$  \ $$|  $$$$$$
    //  | $$  | $$| $$      | $$\  $ | $$      | $$   | $$  | $$| $$  | $$| $$        | $$ /$$| $$| $$  | $$| $$  | $$ \____  $$
    //  | $$$$$$$/| $$$$$$$$| $$ \/  | $$      | $$   |  $$$$$$/| $$  | $$|  $$$$$$$  |  $$$$/| $$|  $$$$$$/| $$  | $$ /$$$$$$$/
    //  |_______/ |________/|__/     |__/      |__/    \______/ |__/  |__/ \_______/   \___/  |__/ \______/ |__/  |__/|_______/
    
    public void setDLM(DataInputStream dis) throws IOException
    {
        dlm = new DefaultListModel<Assignment>();
        
        for(int i = dis.readInt() - 1; i >= 0; i--)
            dlm.addElement(new Assignment(dis));
    }
    
    public DefaultListModel getDLM()
    {
        return dlm;
    }
    
// DEPRECATED METHOD - not used in final version
//    public void addAssignment()
//    {
//        needsSaved = true;
//        dlm.addElement(Assignment.getRandom());
//    }
    
    public void removeAssignment(int[] deleteArr)
    {
        needsSaved = true;
        for(int i = deleteArr.length - 1; i >= 0; i--)
            dlm.remove(deleteArr[i]);
    }
    
    public void completeAssignment(int selectedIndex)
    {
        needsSaved = true;
        dlm.elementAt(selectedIndex).percentageCompleted = 100;
    }
    
    public Assignment returnAssignment(int selectedIndex)
    {
        return dlm.elementAt(selectedIndex);
    }
    
    public DefaultListModel newDLM()
    {
        return dlm = new DefaultListModel();
    }
    
    //   /$$$$$$$              /$$                     /$$      /$$
    //  | $$__  $$            | $$                    | $$$    /$$$
    //  | $$  \ $$  /$$$$$$  /$$$$$$    /$$$$$$       | $$$$  /$$$$  /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$
    //  | $$  | $$ |____  $$|_  $$_/   |____  $$      | $$ $$/$$ $$ |____  $$| $$__  $$ |____  $$ /$$__  $$ /$$__  $$ /$$__  $$
    //  | $$  | $$  /$$$$$$$  | $$      /$$$$$$$      | $$  $$$| $$  /$$$$$$$| $$  \ $$  /$$$$$$$| $$  \ $$| $$$$$$$$| $$  \__/
    //  | $$  | $$ /$$__  $$  | $$ /$$ /$$__  $$      | $$\  $ | $$ /$$__  $$| $$  | $$ /$$__  $$| $$  | $$| $$_____/| $$
    //  | $$$$$$$/|  $$$$$$$  |  $$$$/|  $$$$$$$      | $$ \/  | $$|  $$$$$$$| $$  | $$|  $$$$$$$|  $$$$$$$|  $$$$$$$| $$
    //  |_______/  \_______/   \___/   \_______/      |__/     |__/ \_______/|__/  |__/ \_______/ \____  $$ \_______/|__/
    //                                                                                            /$$  \ $$
    //                                                                                           |  $$$$$$/
    //                                                                                            \______/
    
    public void addAssignment(Assignment assignment)
    {
        needsSaved = true;
        dlm.addElement(assignment);
    }
    
    public void replaceAssignment(Assignment assignment, int index)
    {
        needsSaved = true;
        dlm.setElementAt(assignment, index);
    }
}
