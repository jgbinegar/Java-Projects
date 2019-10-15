import javax.swing.*;
import java.awt.TextField.*;
import java.lang.Math;
import java.awt.event.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.lang.*;
import java.io.*;
import java.awt.*;
import java.text.*;

class AddEditDialog extends JDialog
					implements ActionListener, ChangeListener
{
    JTextField  classInputField;
	JTextField  assignmentInputField;
	JTextField	assignedDateInputField;
	JTextField	dueDateInputField;
	JTextField  pointValueInputField;
    JComboBox methodOfSubmissionDropDown;
    JSlider percentageCompleteSlider;//all above stays
    
    JPanel myButtonPanel;
    JPanel myOutputPanel;

//    JLabel  classLabel;
//    JLabel    assignmentLabel;
//    JLabel  assignedDateLabel;
//    JLabel  dueDateLabel;
//    JLabel  pointValueLabel;
//    JLabel  submissionLabel;
    JLabel  percentageLabel;//stays

	JButton saveButton;
	JButton saveAndCloseButton;
	JButton closeButton;// stays
    JButton replaceButton;

    Assignment assignment;
	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    
	ParsePosition parsePosOne;
    ParsePosition parsePosTwo;
    
    int index;
    
    DataManager dm;
    
    MyDateVerifier dateVerifier;
    
    String submissionList[] = {"Blackboard", "Email", "Paper", "Other"};

    //   /$$$$$$$  /$$$$$$$$ /$$$$$$$$ /$$$$$$  /$$   /$$ /$$    /$$$$$$$$
    //  | $$__  $$| $$_____/| $$_____//$$__  $$| $$  | $$| $$   |__  $$__/
    //  | $$  \ $$| $$      | $$     | $$  \ $$| $$  | $$| $$      | $$
    //  | $$  | $$| $$$$$   | $$$$$  | $$$$$$$$| $$  | $$| $$      | $$
    //  | $$  | $$| $$__/   | $$__/  | $$__  $$| $$  | $$| $$      | $$
    //  | $$  | $$| $$      | $$     | $$  | $$| $$  | $$| $$      | $$
    //  | $$$$$$$/| $$$$$$$$| $$     | $$  | $$|  $$$$$$/| $$$$$$$$| $$
    //  |_______/ |________/|__/     |__/  |__/ \______/ |________/|__/
    
	AddEditDialog(){}

    //    /$$$$$$  /$$$$$$$  /$$$$$$$
    //   /$$__  $$| $$__  $$| $$__  $$
    //  | $$  \ $$| $$  \ $$| $$  \ $$
    //  | $$$$$$$$| $$  | $$| $$  | $$
    //  | $$__  $$| $$  | $$| $$  | $$
    //  | $$  | $$| $$  | $$| $$  | $$
    //  | $$  | $$| $$$$$$$/| $$$$$$$/
    //  |__/  |__/|_______/ |_______/
    
	AddEditDialog(DataManager dm)
    {
        setupConstructor(dm);

		myButtonPanel = new JPanel(new GridLayout(1,3));
        this.add(myButtonPanel,BorderLayout.SOUTH);

        percentageLabel = new JLabel("Percentage: 50");
        myOutputPanel.add(percentageLabel);
        percentageCompleteSlider.addChangeListener(this);

		saveAndCloseButton = new JButton("Save & Close");
        saveAndCloseButton.setActionCommand("SAVE&CLOSE");
        saveAndCloseButton.addActionListener(this);

        saveButton = new JButton("Save");
		saveButton.setActionCommand("SAVE");
		saveButton.addActionListener(this);


		myButtonPanel.add(saveButton);
		myButtonPanel.add(saveAndCloseButton);
		myButtonPanel.add(closeButton);

		setupMainFrame("Add an Assignment");
    }

    //   /$$$$$$$  /$$$$$$$$ /$$      /$$  /$$$$$$  /$$    /$$ /$$$$$$$$
    //  | $$__  $$| $$_____/| $$$    /$$$ /$$__  $$| $$   | $$| $$_____/
    //  | $$  \ $$| $$      | $$$$  /$$$$| $$  \ $$| $$   | $$| $$
    //  | $$$$$$$/| $$$$$   | $$ $$/$$ $$| $$  | $$|  $$ / $$/| $$$$$
    //  | $$__  $$| $$__/   | $$  $$$| $$| $$  | $$ \  $$ $$/ | $$__/
    //  | $$  \ $$| $$      | $$\  $ | $$| $$  | $$  \  $$$/  | $$
    //  | $$  | $$| $$$$$$$$| $$ \/  | $$|  $$$$$$/   \  $/   | $$$$$$$$
    //  |__/  |__/|________/|__/     |__/ \______/     \_/    |________/
    
	AddEditDialog(DataManager dm, int index, Assignment assignment)
    {
        setupConstructor(dm);
            
        this.assignment = assignment;
        
        this.index = index;

		myButtonPanel = new JPanel(new GridLayout(1,2));
        this.add(myButtonPanel,BorderLayout.SOUTH);
        
        classInputField.setText(assignment.nameOfClass);
        assignmentInputField.setText(assignment.nameOfAssignment);
        assignedDateInputField.setText(sdf.format(assignment.dateAssigned));
        dueDateInputField.setText(sdf.format(assignment.dateDue));
        pointValueInputField.setText(String.format("%4.2f", assignment.pointValue));
        methodOfSubmissionDropDown.setSelectedItem(assignment.submissionMethod);
        percentageCompleteSlider.setValue(assignment.percentageCompleted);

        percentageLabel = new JLabel("Percentage: " + assignment.percentageCompleted);
        myOutputPanel.add(percentageLabel);
        percentageCompleteSlider.addChangeListener(this);

		replaceButton = new JButton("Replace");
		replaceButton.setActionCommand("REPLACE");
		replaceButton.addActionListener(this);

		myButtonPanel.add(replaceButton);
		myButtonPanel.add(closeButton);

		setupMainFrame("Edit an Assignment");
    }
    
    void setupConstructor(DataManager dm)
    {
        JPanel myTextPanel;
        
        this.dm = dm;
        
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        myTextPanel = new JPanel(new GridLayout(7,1));
        myOutputPanel = new JPanel(new GridLayout(7,1));
        
        this.add(myTextPanel,BorderLayout.EAST);
        this.add(myOutputPanel,BorderLayout.CENTER);
        this.add(new JPanel(), BorderLayout.WEST);
        
        classInputField = new JTextField(10);
        assignmentInputField = new JTextField(25);
        assignedDateInputField = new JTextField(10);
        dueDateInputField = new JTextField(10);
        pointValueInputField = new JTextField(4);
        methodOfSubmissionDropDown = new JComboBox(submissionList);
        percentageCompleteSlider = new JSlider(0, 100);
        
        pointValueInputField.setInputVerifier(new MyPointValueVerifier());
//        pointValueInputField.setVerifyInputWhenFocusTarget(false);
        
        dateVerifier = new MyDateVerifier();
        
        assignedDateInputField.setInputVerifier(dateVerifier);
//        assignedDateInputField.setVerifyInputWhenFocusTarget(false);
        
        dueDateInputField.setInputVerifier(dateVerifier);
//        dueDateInputField.setVerifyInputWhenFocusTarget(false);
        
        JLabel classLabel = new JLabel("      Class:");
        JLabel assignmentLabel = new JLabel(" Assignment:");
        JLabel assignedDateLabel = new JLabel("   Assigned:");
        JLabel dueDateLabel = new JLabel("        Due:");
        JLabel pointValueLabel = new JLabel("Point Value:");
        JLabel submissionLabel = new JLabel(" Submission:");
        
        closeButton = new JButton("Close");
        closeButton.setActionCommand("CLOSE");
        closeButton.addActionListener(this);
        closeButton.setVerifyInputWhenFocusTarget(false);
        
        myTextPanel.add(classInputField);
        myTextPanel.add(assignmentInputField);
        myTextPanel.add(assignedDateInputField);
        myTextPanel.add(dueDateInputField);
        myTextPanel.add(pointValueInputField);
        myTextPanel.add(methodOfSubmissionDropDown);
        myTextPanel.add(percentageCompleteSlider);
        
        myOutputPanel.add(classLabel);
        myOutputPanel.add(assignmentLabel);
        myOutputPanel.add(assignedDateLabel);
        myOutputPanel.add(dueDateLabel);
        myOutputPanel.add(pointValueLabel);
        myOutputPanel.add(submissionLabel);
    }
    
    void setupMainFrame(String title)
    {
        Toolkit tk;
        Dimension d;
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        setSize(d.width/3, d.height/4);
        setLocation(d.width/3, d.height/3);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle(title);
        setVisible(true);
    }

    //    /$$$$$$   /$$$$$$  /$$$$$$$$ /$$$$$$  /$$$$$$  /$$   /$$
    //   /$$__  $$ /$$__  $$|__  $$__/|_  $$_/ /$$__  $$| $$$ | $$
    //  | $$  \ $$| $$  \__/   | $$     | $$  | $$  \ $$| $$$$| $$
    //  | $$$$$$$$| $$         | $$     | $$  | $$  | $$| $$ $$ $$
    //  | $$__  $$| $$         | $$     | $$  | $$  | $$| $$  $$$$
    //  | $$  | $$| $$    $$   | $$     | $$  | $$  | $$| $$\  $$$
    //  | $$  | $$|  $$$$$$/   | $$    /$$$$$$|  $$$$$$/| $$ \  $$
    //  |__/  |__/ \______/    |__/   |______/ \______/ |__/  \__/
    
	public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("SAVE"))
            doSave();
		else if(e.getActionCommand().equals("SAVE&CLOSE"))
            doSaveAndClose();
		else if(e.getActionCommand().equals("CLOSE"))
            doClose();
		else if(e.getActionCommand().equals("REPLACE"))
            doReplace(assignment);
    }

    void doSave()
    {
        if (classInputField.getText().trim().equals("")
            || assignmentInputField.getText().trim().equals("")
                || assignedDateInputField.getText().trim().equals("")
                    || dueDateInputField.getText().trim().equals("")
                        || pointValueInputField.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please populate all of the fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
//            if(dateVerifier.verify(dueDateInputField) && dateVerifier.verify(assignedDateInputField))
//            {

            long d1 = sdf.parse(assignedDateInputField.getText().trim(), parsePosOne = new ParsePosition(0)).getTime();
            
            long d2 = sdf.parse(dueDateInputField.getText().trim(), parsePosTwo = new ParsePosition(0)).getTime();
            
            if(parsePosOne.getIndex() != assignedDateInputField.getText().trim().length()
               && parsePosTwo.getIndex() != dueDateInputField.getText().trim().length())
            {
                System.out.println("Error parsing Assigned Date");
            }
            else
                if(d1 <= d2)
                {
                    dm.addAssignment(new Assignment(classInputField.getText().trim(), assignmentInputField.getText().trim(), d1, d2, Double.parseDouble(pointValueInputField.getText().trim()), percentageCompleteSlider.getValue(), "" + methodOfSubmissionDropDown.getItemAt(methodOfSubmissionDropDown.getSelectedIndex())));
                    
                    classInputField.setText("");
                    assignmentInputField.setText("");
                    assignedDateInputField.setText("");
                    dueDateInputField.setText("");
                    pointValueInputField.setText("");
                    methodOfSubmissionDropDown.setSelectedIndex(0);
                    percentageCompleteSlider.setValue(50);
                }
                else
                    JOptionPane.showMessageDialog(this, "Due Date is before Assigned Date", "Error", JOptionPane.ERROR_MESSAGE);
        }
        //}
    }
    
    void doSaveAndClose()
    {
        if (classInputField.getText().trim().equals("")
            || assignmentInputField.getText().trim().equals("")
                || assignedDateInputField.getText().trim().equals("")
                    || dueDateInputField.getText().trim().equals("")
                        || pointValueInputField.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please populate all of the fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            long d1 = sdf.parse(assignedDateInputField.getText().trim(), parsePosOne = new ParsePosition(0)).getTime();
            
            long d2 = sdf.parse(dueDateInputField.getText().trim(), parsePosTwo = new ParsePosition(0)).getTime();
            
            if(parsePosOne.getIndex() != assignedDateInputField.getText().trim().length()
               && parsePosTwo.getIndex() != dueDateInputField.getText().trim().length())
            {
                System.out.println("Error parsing Assigned Date");
            }
            else
                if(d1 <= d2)
                {
                    dm.addAssignment(new Assignment(classInputField.getText().trim(), assignmentInputField.getText().trim(), d1, d2, Double.parseDouble(pointValueInputField.getText().trim()), percentageCompleteSlider.getValue(), "" + methodOfSubmissionDropDown.getItemAt(methodOfSubmissionDropDown.getSelectedIndex())));
                    
                        this.dispose();
                }
                else
                    JOptionPane.showMessageDialog(this, "Due Date is before Assigned Date", "Error", JOptionPane.ERROR_MESSAGE);
            

        }
    }
    
    void doClose()
    {
        this.dispose();
    }
    
    void doReplace(Assignment assignmentToBeEdited)
    {
        if (classInputField.getText().trim().equals("")
            || assignmentInputField.getText().trim().equals("")
                || assignedDateInputField.getText().trim().equals("")
                    || dueDateInputField.getText().trim().equals("")
                        || pointValueInputField.getText().trim().equals(""))
        {
            JOptionPane.showMessageDialog(this, "Please populate all of the fields", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            long d1 = sdf.parse(assignedDateInputField.getText().trim(), parsePosOne = new ParsePosition(0)).getTime();
            
            long d2 = sdf.parse(dueDateInputField.getText().trim(), parsePosTwo = new ParsePosition(0)).getTime();
            
            if(parsePosOne.getIndex() != assignedDateInputField.getText().trim().length()
               && parsePosTwo.getIndex() != dueDateInputField.getText().trim().length())
            {
                System.out.println("Error parsing Assigned Date");
            }
            else
                if(d1 <= d2)
                {
                    dm.replaceAssignment(new Assignment(classInputField.getText().trim(), assignmentInputField.getText().trim(),
                                                        d1,
                                                        d2, Double.parseDouble(pointValueInputField.getText().trim()), percentageCompleteSlider.getValue(), "" + methodOfSubmissionDropDown.getItemAt(methodOfSubmissionDropDown.getSelectedIndex())),
                                         index);
                    
                    this.dispose();
                }
                else
                    JOptionPane.showMessageDialog(this, "Due Date is before Assigned Date", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void stateChanged(ChangeEvent e)
    {
        percentageLabel.setText("Percentage: " +
                                percentageCompleteSlider.getValue());
    }

}
