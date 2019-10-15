import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

class ConnectDialog extends JDialog
                implements ActionListener
{
    JPanel              centerPanel;
    
    JTextField          usernameTF;
    JPasswordField      passwordPF;
    
    JTextField          portTF;
    JTextField          domainTF;
    
    JButton             loginButton;
    JButton             registerButton;
    JButton             exitButton;
    
    Client              client;

    ConnectDialog(Client client)
    {
        this.client = client;
        //this.setModal(true);
        
        usernameTF = new JTextField();
        TextPrompt tf1 = new TextPrompt("Username", usernameTF);
        tf1.changeAlpha(128);
        
        passwordPF = new JPasswordField();
        TextPrompt pf1 = new TextPrompt("Password", passwordPF);
        pf1.changeAlpha(128);
        
        portTF = new JTextField();
        TextPrompt tf2 = new TextPrompt("Port Number", portTF);
        tf2.changeAlpha(128);
        
        domainTF = new JTextField();
        TextPrompt tf3 = new TextPrompt("Domain/IP", domainTF);
        tf3.changeAlpha(128);
        
        centerPanel = new JPanel(new GridLayout(5, 1));
        
        centerPanel.add(usernameTF);
        centerPanel.add(passwordPF);
        centerPanel.add(portTF);
        centerPanel.add(domainTF);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        
        buttonPanel.add(createButton("Login", this, "LOGIN"));
        buttonPanel.add(createButton("Register", this, "REGISTER"));
        buttonPanel.add(createButton("Exit", this, "EXIT"));
        
        centerPanel.add(buttonPanel);
        
        this.add(centerPanel, BorderLayout.CENTER);
        
        setupMainFrame();
    }

    void setupMainFrame()
    {
        Toolkit    tk;
        Dimension   d;
        
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        pack();
        setLocation(d.width/3, d.height/3);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Login/Register");
        setVisible(true);
        setResizable(false);
    }
    
    // create message panel
    
    JButton createButton(String bt, ActionListener al, String ac)
    {
        JButton btn = new JButton(bt);
        btn.addActionListener(al);
        btn.setActionCommand(ac);
        return btn;
    }

    public void actionPerformed(ActionEvent ae)
    {
        if(ae.getActionCommand().equals("LOGIN"))
            doLogin();
        else if(ae.getActionCommand().equals("REGISTER"))
            doRegister();
        else if(ae.getActionCommand().equals("EXIT"))
            doExit();
    }
    
    void doLogin()
    {
        String username = usernameTF.getText().trim();
        String password = new String(passwordPF.getPassword());
        
        if(!username.equals("") && !password.equals(""))
            client.connectToServer("LOGIN", username, password.trim());
    }
    
    void doRegister()
    {
        String username = usernameTF.getText().trim();
        String password = new String(passwordPF.getPassword());
        
        if(isValidUsername(username) && isValidPassword(password))
            client.connectToServer("REGISTER", username, password.trim());
    }
    
    void doExit()
    {
        dispose();
    }
    
    static boolean isValidUsername(String username)
    {
        if(username.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Username may only contain:\n" +
                                                " a-z, A-Z, 0-9, _"
                                              , "Username Guidelines", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        for (int i = 0; i < username.length(); i++)
        {
            char ch = username.charAt(i);
            
            if(!isNumeric(ch) && !isLetter(ch) && !isUnderscore(ch))
            {
                JOptionPane.showMessageDialog(null, "Username may only contain:\n" +
                                                    " a-z, A-Z, 0-9, _"
                                                    , "Username Guidelines", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("" + ch);
                
                return false;
            }
        }
        
        return true;
    }
    
    static boolean isValidPassword(String password)
    {
        if (password.length() < 8 || password.equals(""))
        {
            JOptionPane.showMessageDialog(null, "Username may only contain:\n" +
                                          " a-z, A-Z, 0-9, length of 8 or more"
                                          , "Username Guidelines", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        
        int charCount = 0;
        int numCount = 0;
        for (int i = 0; i < password.length(); i++)
        {
            char ch = password.charAt(i);
            
            if (isNumeric(ch))
                numCount++;
            else if (isLetter(ch))
                charCount++;
            else
            {
                JOptionPane.showMessageDialog(null, "Username may only contain:\n" +
                                              " a-z, A-Z, 0-9, length of 8 or more"
                                              , "Username Guidelines", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
    }
        
        return (charCount >= 1 && numCount >= 1);
    }
    
    static boolean isLetter(char ch)
    {
        ch = Character.toUpperCase(ch);
        return (ch >= 'A' && ch <= 'Z');
    }
    
    static boolean isNumeric(char ch)
    {
        return (ch >= '0' && ch <= '9');
    }
    
    static boolean isUnderscore(char ch)
    {
        return (ch == '_');
    }
}
