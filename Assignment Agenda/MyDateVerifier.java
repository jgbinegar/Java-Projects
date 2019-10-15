import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.text.*;
import java.lang.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;

class MyDateVerifier extends InputVerifier
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
	public boolean verify(JComponent input)//the name of the textFields to be verified?
		{
		ParsePosition parsePos;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		sdf.setLenient(false);
		sdf.parse(((JTextField)input).getText().trim(), parsePos = new ParsePosition(0));

		if(parsePos.getIndex() != ((JTextField)input).getText().trim().length())
			{
			JOptionPane.showMessageDialog(input.getParent(),"Date must be in mm/dd/yyyy format","Error",JOptionPane.ERROR_MESSAGE);
			return false;
			}
		else
			return true;
		}
	}
