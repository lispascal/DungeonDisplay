/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;


/**
 *
 * @author plis
 */
public class JXFPanel extends JPanel {
    JXFPanel()
    {
        setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
    }

    JXFPanel(int sides,int topbot) //
    {
        setLayout(new FlowLayout(FlowLayout.CENTER,sides,topbot));
    }
    
    JXFPanel(int sides,int topbot,Color color) //
    {
        setLayout(new FlowLayout(FlowLayout.CENTER,sides,topbot));
        setBackground(color);
    }

}