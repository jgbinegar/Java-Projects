import javax.swing.*;
import java.util.regex.*;
import java.io.*;
import java.awt.*;
import java.text.*;
import java.lang.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

class EmailVerifier
{
    static public boolean verify(JTextField input)
    {
        boolean isValid = false;
        final Pattern pattern = Pattern.compile("[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})");
        
        Matcher matcher = pattern.matcher(input.getText().trim());
        
        boolean done = false;
        
        while(!done)
        {
            if(matcher.find())
            {
                return true;
            }
            else
                done = true;
        }
        
        JOptionPane.showMessageDialog(input.getParent(),"Please enter a valid email address.","Error",JOptionPane.ERROR_MESSAGE);
        
        return false;
    }
}

