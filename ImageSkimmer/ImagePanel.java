import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.*;

class ImagePanel extends JPanel
{

    BufferedImage bufferedImage;
    
    ImagePanel(BufferedImage urlImage)
    {
        super();
        bufferedImage = urlImage;
        setPreferredSize(new Dimension(bufferedImage.getWidth(), bufferedImage.getHeight()));
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D selectedImage;
        selectedImage = (Graphics2D) g;
        
        super.paintComponent(g);
        
        selectedImage.drawImage(bufferedImage, 0, 0, null);
        
    }

}
