package Graphics;

import java.awt.image.*;

import javax.imageio.*;

import java.io.*;
import java.net.URL;

/**
 * ReadImageFiles is a small utility class with static methods to load
 * images into our project. This will be used to create and and show
 * the pokedex indexes for each pokemon as well as the small poke 
 * thumb nails.
 * 
 * 
 * @author Victor Kesten
 * @version 0.1
 * @date 01/5/2014
 */
public class ReadImageFiles
{
    /**
     * This makes sure that the buffered images can be read and that
     * no errors occur.
     * 
     * @param imageFile  The image file to be loaded.
     * @return           The image object or null is it could not be read.
     */
    public static BufferedImage loadImage(URL imageFilename)
    {
    	//File file = new File(imageFilename);
    	
        try {
            BufferedImage image = ImageIO.read(imageFilename);
            if(image == null || (image.getWidth(null) < 0)) {
                // we could not load the image - probably invalid file format
                return null;
            }
            return new BufferedImage(image.getColorModel(), image.copyData(null), 
                    image.isAlphaPremultiplied(), null);
        }
        catch(IOException exc) {
            return null;
        }
    }
}
