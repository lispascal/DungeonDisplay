/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author plis
 */
public class ImagePane extends JXFPanel //stores an image & name
{
    static String writeName="1";
    static int writeType=5;
    public String name; 
    boolean isSelected = false;
    public BufferedImage image;
    public BufferedImage backImage;
    public int type; // 0=terrain, 1=object 2=effects 3=player 4=mob
    static boolean hide = false;
    DungeonDisplay parentDD;
    
    public ImagePane(DungeonDisplay parent)
    {
        parentDD = parent;
        setPreferredSize(new Dimension(80,80));
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed (MouseEvent e)
            {
                int i;
                //based on old writeName and writeType values, un-ants it by marking isSelected = false, then mark this one true.
                switch(writeType)
                {
                    case 0: parentDD.terrainArray.panels[Integer.parseInt(writeName)].isSelected = false;
                            parentDD.terrainArray.panels[Integer.parseInt(writeName)].repaint();
                        break;
                    case 1: parentDD.objectArray.panels[Integer.parseInt(writeName)].isSelected = false;
                            parentDD.objectArray.panels[Integer.parseInt(writeName)].repaint();
                        break;
                    case 2: parentDD.effectArray.panels[Integer.parseInt(writeName)].isSelected = false;
                            parentDD.effectArray.panels[Integer.parseInt(writeName)].repaint();
                        break;
                    case 3: i = parentDD.mobMenu.getIntOf(writeName);
                            parentDD.playerMenu.MobPaneArray[i].isSelected = false;
                            parentDD.playerMenu.MobPaneArray[i].repaint();
                        break;
                    case 4: i = parentDD.mobMenu.getIntOf(writeName);
                            parentDD.mobMenu.MobPaneArray[i].isSelected = false;
                            parentDD.mobMenu.MobPaneArray[i].repaint();
                        break;
                }
                
                isSelected = true;
                repaint();
                writeName = name;
                writeType = type;
            }
        }
        );

    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if (backImage != null)
        {
            g.drawImage(backImage, 0, 0, null);
        }
        if (image != null)
        {
            g.drawImage(image, 0, 0, null);
        }
        if (isSelected)
        {
            g.drawImage(utility.getImage("Dungeon Display Images/Other/ants.gif"), 0, 0, null);
        }
    }
}
