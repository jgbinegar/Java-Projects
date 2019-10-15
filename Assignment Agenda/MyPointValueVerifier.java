import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.text.*;
import java.lang.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

class MyPointValueVerifier extends InputVerifier
{
        
        
        
        //   /$$    /$$                    /$$  /$$$$$$
        //  | $$   | $$                   |__/ /$$__  $$
        //  | $$   | $$ /$$$$$$   /$$$$$$  /$$| $$  \__//$$   /$$
        //  |  $$ / $$//$$__  $$ /$$__  $$| $$| $$$$   | $$  | $$
        //   \  $$ $$/| $$$$$$$$| $$  \__/| $$| $$_/   | $$  | $$
        //    \  $$$/ | $$_____/| $$      | $$| $$     | $$  | $$
        //     \  $/  |  $$$$$$$| $$      | $$| $$     |  $$$$$$$
        //      \_/    \_______/|__/      |__/|__/      \____  $$
        //                                              /$$  | $$
        //                                             |  $$$$$$/
        //                                              \______/
        
        
        
	@Override
	public boolean verify(JComponent input)
    {
		boolean isValid = false;

		try
			{
			if(Double.parseDouble(((JTextField)input).getText()) < 1)
				{
				JOptionPane.showMessageDialog(input.getParent(),"Please enter a digit greater than 0.","Error",JOptionPane.ERROR_MESSAGE);
				isValid = false;
				}
			else
				isValid = true;
			}
		catch(NumberFormatException nfe)
			{
			JOptionPane.showMessageDialog(input.getParent(),"Please enter a valid integer value.","Error",JOptionPane.ERROR_MESSAGE);
			isValid = false;
			}
		return isValid;
    }
}
