import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.*;
import java.lang.Math.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class PuzzlePiecePanel extends JPanel
{
    int puzzleSize;
    
    PuzzlePieceButton [] [] buttonMatrix;
    
    int blankRow;
    int blankCol;
    
    PuzzlePiecePanel(MyPuzzleFrame myFrame, BufferedImage buffImage, int puzzleSize)
    {
        super();
        
        this.setPreferredSize(new Dimension(500, 500));
        
        this.puzzleSize = puzzleSize;
        
        setLayout(new GridLayout(puzzleSize, puzzleSize));
        
        buttonMatrix = new PuzzlePieceButton [puzzleSize] [puzzleSize];

        puzzlePieceConstructor(myFrame, buffImage);
    }
    
    void doSwap(PuzzlePieceButton b)
    {
        buttonMatrix[blankRow][blankCol].swapPieces(b);
        blankRow = b.getButtonRow();
        blankCol = b.getButtonCol();
    }
    
    void puzzlePieceConstructor(MyPuzzleFrame myFrame, BufferedImage buffImage)
    {
        int x = 0;
        int y = 0;
        int imageScale;
        
        imageScale = buffImage.getHeight() / puzzleSize;
        
        for(int row = 0; row < puzzleSize; row++)
        {
            for(int col = 0; col < puzzleSize; col++)
            {
                buttonMatrix[row][col] = new PuzzlePieceButton(new ImageIcon(buffImage.getSubimage(x, y, imageScale, imageScale)), row, col, myFrame);
                add(buttonMatrix[row][col]);
                x += imageScale;
            }
            x = 0;
            y += imageScale;
        }
        
        buttonMatrix[puzzleSize-1][puzzleSize-1].setIcon(ImageManipulator.getIcon("blankIcon.png", imageScale));
        
        blankRow = puzzleSize - 1;
        blankCol = puzzleSize - 1;
    }
    
    void shufflePuzzle()
    {
        Random r = new Random();
        
        while(!puzzleIs("Shuffled"))
        {
            switch(r.nextInt(4) + 1)
            {
                case 1: if(!outOfBounds(blankRow - 1, blankCol))
                            doSwap(buttonMatrix[blankRow - 1][blankCol]);
                        break;
                case 2: if(!outOfBounds(blankRow, blankCol - 1))
                            doSwap(buttonMatrix[blankRow][blankCol - 1]);
                        break;
                case 3: if(!outOfBounds(blankRow + 1, blankCol))
                            doSwap(buttonMatrix[blankRow + 1][blankCol]);
                        break;
                case 4: if(!outOfBounds(blankRow, blankCol + 1))
                            doSwap(buttonMatrix[blankRow][blankCol + 1]);
                        break;
            }
        }
    }
    
    boolean outOfBounds(int row, int col)
    {
        if(row < 0 || col < 0 || row >= puzzleSize || col >= puzzleSize)
            return true;
        else
            return false;
    }
    
    int numOfCorrectPieces()
    {
        int tempNums = 0;
        
        for(int r = 0; r < puzzleSize; r++)
            for(int c = 0; c < puzzleSize; c++)
            {
                if(buttonMatrix[r][c].isInOriginalLocation())
                    tempNums++;
            }
        
        return tempNums;
    }
    
    boolean puzzleIs(String type)
    {
        if(type.equals("Shuffled"))
        {
            for(int r = 0; r < puzzleSize; r++)
                for(int c = 0; c < puzzleSize; c++)
                    if(buttonMatrix[r][c].isInOriginalLocation())
                        return false;
        }
        else if(type.equals("Finished"))
        {
            for(int r = 0; r < puzzleSize; r++)
                for(int c = 0; c < puzzleSize; c++)
                    if(!buttonMatrix[r][c].isInOriginalLocation())
                        return false;
        }
        
        return true;
    }
    
    boolean isNextToWrapper(PuzzlePieceButton temp)
    {
        return buttonMatrix[blankRow][blankCol].isNextTo(temp);
    }
}
