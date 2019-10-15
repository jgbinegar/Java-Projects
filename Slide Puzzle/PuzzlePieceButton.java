import javax.swing.*;
import javax.swing.Timer;
import java.io.*;
import java.awt.*;
import java.text.*;
import java.lang.*;
import java.awt.event.*;
import java.util.*;
import java.awt.dnd.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.JFileChooser.*;
import java.awt.Color.*;
import java.awt.event.MouseEvent.*;
import java.awt.datatransfer.*;

class PuzzlePieceButton extends JButton
{

    final int buttonRow;
    final int buttonCol;
    
    int originalIconRow;
    int originalIconCol;
    
    PuzzlePieceButton(ImageIcon icon, int row, int col, MyPuzzleFrame myFrame)
    {
        super(icon);
        buttonRow = row;
        buttonCol = col;
        originalIconRow = row;
        originalIconCol = col;
        
        addActionListener(myFrame);
        setActionCommand("PUZZLE CLICK");
        setBorder(BorderFactory.createEtchedBorder());
    }
    
    boolean isNextTo(PuzzlePieceButton p)
    {
        int absRow = Math.abs(p.buttonRow - this.buttonRow);
        int absCol = Math.abs(p.buttonCol - this.buttonCol);
        
        if(absRow == 1 && absCol == 0)
            return true;
        else if(absRow == 0 && absCol == 1)
            return true;
        else
            return false;
    }
    
    void swapPieces(PuzzlePieceButton p)// blankButton first, swaps with seconds button
    {
        Icon tempIcon = this.getIcon();
        int tempRow = this.originalIconRow;
        int tempCol = this.originalIconCol;
        
        this.setIcon(p.getIcon());
        this.originalIconRow = p.originalIconRow;
        this.originalIconCol = p.originalIconCol;
        
        p.setIcon(tempIcon);
        p.originalIconRow = tempRow;
        p.originalIconCol = tempCol;
    }
    
    int getButtonRow()
    {
        return buttonRow;
    }
    
    int getButtonCol()
    {
        return buttonCol;
    }
    
    boolean isInOriginalLocation()
    {
        if(buttonRow == originalIconRow && buttonCol == originalIconCol)
            return true;
        else
            return false;
    }
}
