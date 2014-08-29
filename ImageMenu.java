/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
/**
 *
 * @author plis
 */
public class ImageMenu extends JXFPanel{
    
    // terain imagelist
    static String[] terrainList = {"BlackSquare","Dirt","Dirt2","Dirt3","Grass",
        "Lava1","Lava2","Lava3","TiledFloor","Water1"
        ,"Water2","Water3"};
    
    static String[] objectList = {"","Bush","DoorLockHorizontal","DoorLockVertical",
                            "DoorOpenHorizontal","DoorOpenVertical",
                            "DoorUnlockHorizontal","LargeStone1","LargeStone2",
                            "StairsDown","StairsLeft","StairsRight","StairsUp",
                            "WallBL","WallHorizontal","WallLT","WallMid",
                            "WallRB","WallTR","WallVertical"};
    
    static String[] effectList = {"","PoisonCloud","SacredGround"};
    
    String[] imageList;
    String imageFolder;
    
    DungeonDisplay parentDD;
    
    ImagePane[] panels;
    int type;
    
    public ImageMenu(int typeVal, DungeonDisplay parent)
    {
        parentDD = parent;
        setLayout(new GridLayout(0,2,5,5));
        type = typeVal;
        if (typeVal==0)
        {
            imageList=terrainList;
            imageFolder = "Dungeon Display Images/Terrain/"; 
        }
        if (typeVal==1)
        {
            imageList=objectList;
            imageFolder = "Dungeon Display Images/Object/";
        }
        if (typeVal==2)
        {
            imageList=effectList;
            imageFolder = "Dungeon Display Images/Effect/";
        }
        
    }
    
    public void addImages()
    {
        int start=0;
        panels = new ImagePane[imageList.length];
        if (type>0) // if it isn't "terrain" type, then set first panel to be a delete <type> option
        {
            panels[0] = new ImagePane(parentDD);
            panels[0].image = utility.getImage("Dungeon Display Images/Other/0.gif");
            panels[0].backImage = utility.getImage("Dungeon Display Images/Other/redx.gif");
            panels[0].name = "0";
            panels[0].type = type;
            panels[0].setToolTipText("Delete");
            add(panels[0]);
            start++;
        }
        
        for(int i=start;i<imageList.length;i++)
        {
            panels[i] = new ImagePane(parentDD);
            panels[i].image = utility.getImage(imageFolder + imageList[i] + ".gif");
            panels[i].name = Integer.toString(i);
            panels[i].type = type;
            panels[i].setToolTipText(imageList[i]);
            add(panels[i]);
        }
        
        
    }
    
    public int getIntOf(String key)
    {
        for(int i=0;i<imageList.length;i++)
        {
            if(key.equals(imageList[i]))
                return i;
        }
        return 0;
    }
    
    
}
