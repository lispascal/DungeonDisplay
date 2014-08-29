/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Pascal
 */
public class utility {
    public static Boolean FileExists(String filename) // returns a boolean true if file or directory exists
    {
        File file=new File(filename);
        return file.exists();
    }
    
    public static File removeExtension(File fileName, String dotExtension)
    {
        String newFileName = fileName.getName().substring(0,fileName.getName().lastIndexOf(dotExtension));
        return new File(fileName.getParent(),newFileName);
    }
    
    public static BufferedImage getImage(File file)
    { 
        BufferedImage result = null;
        try
        {
            result = ImageIO.read(file);
        }
        catch (IOException e) {
                System.out.println(file);
//                e.printStackTrace();
            System.exit(1);
        }
        return result;
    }
    
    public static BufferedImage getImage(String str)
    { 
        BufferedImage result = null;
        try
        {

            URL imageURL = DungeonDisplay.class.getResource(str);
            if (imageURL != null)
            {
                result = ImageIO.read(imageURL);
            }
            else
            {
                result = null;
            }
        }
        catch (MalformedURLException e) {
//             e.printStackTrace();
            System.exit(1);
        }
        catch (IOException e) {
//                e.printStackTrace();
            System.exit(1);
        }
        return result;
    }
    
}
