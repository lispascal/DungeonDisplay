/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 *
 * @author plis
 */
public class MobPane extends JXFPanel {
    String name;
    BufferedImage image;
    boolean isSelected = false;
    String directoryPath;
    int type; //3 for player, 4 for mob
    DungeonDisplay parentDD;
    public MobPane(String setName, String directory, String typeVal, DungeonDisplay parent)
    {
        parentDD = parent;
        if ("player".equals(typeVal))
            type=3;
        if ("mob".equals(typeVal))
            type=4;
        
        directoryPath = directory;
        name = setName;
        image = utility.getImage(new File(directoryPath + "Images\\" + setName + ".gif"));
        
        setPreferredSize(new Dimension(80,80));
        repaint();
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed (MouseEvent e)
            {   // based on old writeName and writeType values, un-ants it by 
                // marking isSelected = false and repainting, then mark this one true.
                int i;
                switch(ImagePane.writeType)
                {
                    case 0: parentDD.terrainArray.panels[Integer.parseInt(ImagePane.writeName)].isSelected = false;
                            parentDD.terrainArray.panels[Integer.parseInt(ImagePane.writeName)].repaint();
                        break;
                    case 1: parentDD.objectArray.panels[Integer.parseInt(ImagePane.writeName)].isSelected = false;
                            parentDD.objectArray.panels[Integer.parseInt(ImagePane.writeName)].repaint();
                        break;
                    case 2: parentDD.effectArray.panels[Integer.parseInt(ImagePane.writeName)].isSelected = false;
                            parentDD.effectArray.panels[Integer.parseInt(ImagePane.writeName)].repaint();
                        break;
                    case 3: i = parentDD.playerMenu.getIntOf(ImagePane.writeName);
                            parentDD.playerMenu.MobPaneArray[i].isSelected = false;
                            parentDD.playerMenu.MobPaneArray[i].repaint();
                        break;
                    case 4: i = parentDD.mobMenu.getIntOf(ImagePane.writeName);
                            parentDD.mobMenu.MobPaneArray[i].isSelected = false;
                            parentDD.mobMenu.MobPaneArray[i].repaint();
                        break;
                }
                
                isSelected = true;
                repaint();  // to make sure the ants are placed on this one
                
                ImagePane.writeName = name;
                ImagePane.writeType = type;
            }
        }
        );
    }
    
    
    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
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
