package Graphics;

import java.awt.*;
//import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;
/**
 * This class is used to GENERATE IMAGES for my GUI.
 * This can also be used to create 'clean canvases' so that I can
 * write and edit anything I want onto it. 
 * @author Victor
 * @date 01/5/2014
 */
@SuppressWarnings("serial")
public class ImageGenerator extends JComponent
{

	// The current width and height of this panel
    private int width, height;

    // An internal image buffer that is used for painting. For
    // actual display, this image buffer is then copied to screen.
    private BufferedImage panelImage;

    /**
     * Create a new, empty ImagePanel with a set width and height.
     */
    public ImageGenerator()
    {
        this.width = 360;    // Pixel Dimensions.
        this.height = 240;
        this.panelImage = null;
    }

    /**
     * Set the image that this panel should show.
     * 
     * @param image  The image to be displayed.
     */
    public void setImage(BufferedImage image)
    {
        if(image != null) {
            width = image.getWidth();
            height = image.getHeight();
            panelImage = image;
            repaint();
        }
    }
    /**
     * Used when you want to clear canvas and display something else.
     */
    public void clearImage()
    {
        Graphics imageGraphics = panelImage.getGraphics();
        imageGraphics.setColor(Color.LIGHT_GRAY);
        imageGraphics.fillRect(0, 0, width, height);
        repaint();
    }
    
    /**
     * Tell the layout manager how big we would like to be.
     * (This method gets called by layout managers for placing
     * the components.)
     * 
     * @return The preferred dimension for this component.
     */
    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }
    
    /**
     * This component needs to be redisplayed. Copy the internal image 
     * to screen. (This method gets called by the Swing screen painter 
     * every time it want this component displayed.)
     * 
     * @param g The graphics context that can be used to draw on this component.
     */
    public void paintComponent(Graphics g)
    {
        Dimension size = getSize();
        g.clearRect(0, 0, size.width, size.height);
        if(panelImage != null) {
            g.drawImage(panelImage, 0, 0, null);
        }
    }
}