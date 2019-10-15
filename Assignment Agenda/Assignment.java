import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.lang.Math;
import java.text.*;
import java.io.*;
import java.util.*;

public class Assignment
{
    public String  nameOfClass;
    public String  nameOfAssignment;
    public long    dateAssigned;
    public long    dateDue;
    public double  pointValue;
    public int     percentageCompleted;
    public String  submissionMethod;
    
    static Random rand = new Random();
    static SimpleDateFormat dForm = new SimpleDateFormat("MM/dd/yyyy");
    
    Assignment(){}
    
    Assignment(String nameOfClass,
                      String nameOfAssignment,
                      long dateAssigned,
                      long dateDue,
                      double pointValue,
                      int percentageCompleted,
                      String submissionMethod)
    {
        this.nameOfClass = nameOfClass;
        this.nameOfAssignment = nameOfAssignment;
        this.dateAssigned = dateAssigned;
        this.dateDue = dateDue;
        this.pointValue = pointValue;
        this.percentageCompleted = percentageCompleted;
        this.submissionMethod = submissionMethod;
    }
    
    Assignment(DataInputStream dis) throws IOException
    {
        nameOfClass = dis.readUTF();
        nameOfAssignment = dis.readUTF();
        dateAssigned = dis.readLong();
        dateDue = dis.readLong();
        pointValue = dis.readDouble();
        percentageCompleted = dis.readInt();
        submissionMethod = dis.readUTF();
    }
// DEPRECATED METHOD - not used in final version
//    static Assignment getRandom()
//    {
//        String classList[] = {"COMP 1100", "COMP 1199", "COMP 2200", "COMP 2220", "COMP 2270", "MATH 1561", "MATH 2562"};
//        String assignmentList[] = {"Group Work", "Read Ch. 12", "Project 1", "Project 2", "Project 3", "STUDY!!!"};
//        String submissionList[] = {"Blackboard", "Email", "Paper", "Other"};
//
//        Assignment instance;
//
//        instance = new Assignment();
//
//        instance.nameOfClass = classList[rand.nextInt(7)];
//        instance.nameOfAssignment = assignmentList[rand.nextInt(6)];
//        instance.dateAssigned = System.currentTimeMillis();
//        instance.dateDue = System.currentTimeMillis() + 86400000 + rand.nextInt(864000001);
//        instance.pointValue = rand.nextInt(100) + rand.nextDouble();
//        instance.percentageCompleted = rand.nextInt(101);
//        instance.submissionMethod = submissionList[rand.nextInt(4)];
//
//        return instance;
//    }
    
    public void store(DataOutputStream dos) throws IOException
    {
        dos.writeUTF(nameOfClass);
        dos.writeUTF(nameOfAssignment);
        dos.writeLong(dateAssigned);
        dos.writeLong(dateDue);
        dos.writeDouble(pointValue);
        dos.writeInt(percentageCompleted);
        dos.writeUTF(submissionMethod);
    }
    
    @Override
    public String toString()
    {
        return String.format("%-15s   %-11s   %-10s   %-10s   %5.2f   %3s%%   %-10s", nameOfClass, nameOfAssignment, dForm.format(dateAssigned), dForm.format(dateDue), pointValue, percentageCompleted, submissionMethod);
    }
}
