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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class MyPuzzleFrame extends JFrame
                            implements ActionListener, ChangeListener
{
    JPanel centerPanel;
    
    JButton magicButton;
    JButton fileButton;
    JButton resetButton;
    JButton exitButton;
    
    PuzzlePiecePanel myPuzzlePanel;
    
    JLabel elapsedTimeLabel;
    JLabel numOfMovesLabel;
    JLabel incorrectSpotsLabel;
    
    Timer gameTimer;

    ImageIcon playIcon;
    ImageIcon pauseIcon;
    
    JSlider sizeSlider;
    
    BufferedImage lastImage;
    
    int numOfPieces;
    int numOfMoves;
    int milliLapsed;
    int secondsLapsed;
    int minutesLapsed;
    
    boolean gameHasStarted;
    
    MyPuzzleFrame()
    {
        JPanel bottomPanel;
        JPanel buttonPanel;
        JPanel topPanel;
        
        gameHasStarted = false;
        
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel = new JPanel();
        topPanel = new JPanel(new GridLayout(1, 3));
        centerPanel = new JPanel();
        centerPanel.setPreferredSize(new Dimension(510, 510));
        
        playIcon = ImageManipulator.getIcon("playIcon.png", 50);
        pauseIcon = ImageManipulator.getIcon("pauseIcon.png", 50);
        
        centerPanel.add(new JPanel().add(new JLabel(ImageManipulator.getIcon("default.jpg", 510))));
        
        magicButton = buttonConstructor(playIcon, "PLAY", "Starts the game...", false);
        fileButton = buttonConstructor(ImageManipulator.getIcon("fileIcon.png", 50), "NEW IMAGE", "Choose new image...", true);
        resetButton = buttonConstructor(ImageManipulator.getIcon("resetIcon.png", 50), "RESET", "Resets the game...", false);
        exitButton = buttonConstructor(ImageManipulator.getIcon("exitIcon.png", 50), "EXIT", "Exits the game...", true);
        
        gameTimer = new Timer(100, this);
        gameTimer.setActionCommand("UPDATE TIMER");
        
        elapsedTimeLabel = new JLabel("Time Elapsed: 00.0");
        numOfMovesLabel = new JLabel("Moves: " + numOfMoves);
        incorrectSpotsLabel = new JLabel("Correct Pieces: -/-");
                               
        sizeSlider = sliderConstructor();
        
        numOfMoves = 0;
        milliLapsed = 0;
        secondsLapsed = 0;
        minutesLapsed = 0;
        
        topPanel.add(elapsedTimeLabel);
        topPanel.add(numOfMovesLabel);
        topPanel.add(incorrectSpotsLabel);
        
        buttonPanel.add(magicButton);
        buttonPanel.add(fileButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);
                               
        bottomPanel.add(buttonPanel);
        bottomPanel.add(sizeSlider);
        
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);
        
        setupMainFrame();
    }
    
    void setupMainFrame()
    {
        Toolkit     tk;
        Dimension    d;
        
        tk = Toolkit.getDefaultToolkit();
        d = tk.getScreenSize();
        pack();
        setLocation(d.width/4, d.height/4);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setTitle("Sliding Puzzle");     // For the title bar
        
        setVisible(true);
    }
    
    JSlider sliderConstructor()
    {
        JSlider slider = new JSlider(3, 5);
        slider.addChangeListener(this);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(1);
        slider.setMinorTickSpacing(1);
        Hashtable<Integer, JLabel> labels = new Hashtable<>();
        labels.put(3, new JLabel("Easy"));
        labels.put(4, new JLabel("Medium"));
        labels.put(5, new JLabel("Hard"));
        slider.setLabelTable(labels);
        slider.setPreferredSize(new Dimension(250, 50));
        slider.setToolTipText("Choose Puzzle Difficulty");
        
        return slider;
    }
    
    JButton buttonConstructor(ImageIcon icon, String actionCommand, String toolTipText, boolean enabled)
    {
        JButton button = new JButton();
        button.setIcon(icon);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setToolTipText(toolTipText);
        button.setEnabled(enabled);
        
        return button;
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("PLAY"))
            doPlay();
        else if(e.getActionCommand().equals("PAUSE"))
            doPause();
        else if(e.getActionCommand().equals("NEW IMAGE"))
            doNewImage();
        else if(e.getActionCommand().equals("EXIT"))
            doExit();
        else if(e.getActionCommand().equals("UPDATE TIMER"))
            doUpdateTimer();
        else if(e.getActionCommand().equals("PUZZLE CLICK"))
            doPuzzleClick(e);
        else if(e.getActionCommand().equals("RESET"))
            doReset();
    } // End of ActionPerformed
    
    public void stateChanged(ChangeEvent e)
    {
        if(lastImage != null)
            resetButton.setEnabled(true);
    }
    
    void doPlay()
    {
        centerPanel.removeAll();
        centerPanel.add(myPuzzlePanel);
        
        centerPanel.repaint();
        
        magicButton.setIcon(pauseIcon);
        magicButton.setActionCommand("PAUSE");
        gameTimer.start();
        
        gameHasStarted = true;
        
        resetButton.setEnabled(true);
    } // End of doPlay()
    
    void doPause()
    {
        magicButton.setIcon(playIcon);
        magicButton.setActionCommand("PLAY");
        gameTimer.stop();
        
        centerPanel.removeAll();
        centerPanel.add(new JLabel(new ImageIcon(lastImage)));
        
        centerPanel.revalidate();
    } // End of doPause()
    
    void doNewImage()
    {
        JFileChooser chooser;

        chooser = new JFileChooser(".");
        
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            lastImage = ImageManipulator.getPuzzleImage(chooser.getSelectedFile(), 500);
            
            myPuzzlePanel = new PuzzlePiecePanel(this, lastImage, sizeSlider.getValue());
            
            numOfPieces = (int)Math.pow(sizeSlider.getValue(), 2);
            
            centerPanel.removeAll();
            centerPanel.add(new JLabel(new ImageIcon(lastImage)));
            
            centerPanel.revalidate();
            centerPanel.repaint();
            
            magicButton.setIcon(playIcon);
            magicButton.setActionCommand("PLAY");
            magicButton.setEnabled(true);
        
            myPuzzlePanel.shufflePuzzle();
            
            incorrectSpotsLabel.setText("Correct Pieces: " + myPuzzlePanel.numOfCorrectPieces() + "/" + numOfPieces);
        }
    } // End of doNewImage()
    
    void doExit()
    {
        System.exit(0);
    } // End of doExit()
    
    void doUpdateTimer()
    {
            milliLapsed++;
            
            if(milliLapsed == 10)
            {
                milliLapsed = 0;
                secondsLapsed++;
                
                if(secondsLapsed == 60)
                {
                    secondsLapsed = 0;
                    minutesLapsed++;
                } // End of if(minutesLapsed == 60)
            } // End of if(secondsLapsed == 60)
        
        if(minutesLapsed == 0)
            elapsedTimeLabel.setText("Time Elapsed: " + String.format("%2d.%1d", secondsLapsed, milliLapsed).replace(' ', '0'));
        else
            elapsedTimeLabel.setText("Time Elapsed: " + String.format("%2d:%2d.%1d", minutesLapsed, secondsLapsed, milliLapsed).replace(' ', '0'));
            
    } // End of doUpdateTimer()
    
    void doPuzzleClick(ActionEvent e)
    {
        PuzzlePieceButton tempButton = ((PuzzlePieceButton)e.getSource());
        
        if(myPuzzlePanel.isNextToWrapper(tempButton))
        {
            myPuzzlePanel.doSwap(tempButton);
            incorrectSpotsLabel.setText("Correct Pieces: " + myPuzzlePanel.numOfCorrectPieces() + "/" + numOfPieces);
            
            if(myPuzzlePanel.puzzleIs("Finished"))
            {
                numOfMovesLabel.setText("Moves: " + ++numOfMoves);
                new WinDialog(String.format("%2d:%2d.%1d", minutesLapsed, secondsLapsed, milliLapsed).replace(' ', '0'), numOfMoves);
                gameTimer.stop();
                centerPanel.removeAll();
                centerPanel.add(new JLabel(new ImageIcon(lastImage)));
                centerPanel.revalidate();
                magicButton.setEnabled(false);
            }
            else
                numOfMovesLabel.setText("Moves: " + ++numOfMoves);
        }
    }
    
    void doReset()
    {
        myPuzzlePanel = new PuzzlePiecePanel(this, lastImage, sizeSlider.getValue());
        
        numOfPieces = (int)Math.pow(sizeSlider.getValue(), 2);

        centerPanel.removeAll();
        centerPanel.add(new JLabel(new ImageIcon(lastImage)));
        
        milliLapsed = 0;
        secondsLapsed = 0;
        minutesLapsed = 0;

        elapsedTimeLabel.setText("Time Elapsed: " + String.format("%2d.%1d", secondsLapsed, milliLapsed).replace(' ', '0'));
        
        numOfMoves = 0;
        
        numOfMovesLabel.setText("Moves: " + numOfMoves);

        centerPanel.revalidate();
        centerPanel.repaint();
        
        gameHasStarted = false;
        
        magicButton.setIcon(playIcon);
        magicButton.setActionCommand("PLAY");
        magicButton.setEnabled(true);
        
        gameTimer.stop();
        
        resetButton.setEnabled(false);
        
        myPuzzlePanel.shufflePuzzle();
    }
}
