import java.io.*;
import java.net.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;

class AlertSound extends Thread
                        implements LineListener
{
    Clip        audioClip;
    String      alertClip;
    
    AlertSound(String alertClip)
    {
        this.alertClip = "." + File.separator + "Audio" + File.separator + alertClip;
        start();
    }
    
    @Override
    public void run()
    {
        try
        {
            audioClip = AudioSystem.getClip();
            audioClip.open(
                    AudioSystem.getAudioInputStream(
                            getClass().getClassLoader().getResource(alertClip)));
            audioClip.addLineListener(this);
            audioClip.start();
            
            while(true)
                Thread.sleep(50);
        }
        catch(UnsupportedAudioFileException uafe)
        {
            System.out.println("Unsupported File");
        }
        catch(IOException ioe)
        {
            System.out.println("IO Exception");
        }
        catch(LineUnavailableException lue)
        {
            System.out.println("Line unavailable exception");
        }
        catch(InterruptedException tie)
        {
            System.out.println("Thread was interrupted");
        }
    }
    
    public void update(LineEvent event)
    {
        if(event.getType() == LineEvent.Type.STOP)
        {
            event.getLine().close();
            this.interrupt();
        }
    }
}
