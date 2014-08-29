/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author plis
 */
public class DisplayPanelCell extends JXFPanel
{
    static int[] oldCoords = new int[2]; // holds two ints; the x and y coordinates of last click

    
    int x,y; // the x and y coordinates of the cell
    int terrain = 8;
    int object = 0;
    int effect = 0;
    int roomNum = 0;
    GridContainer parentGrid;
    
    String player = "";
    String mob = "";
    int mobnumber = 0;
    
    
    BufferedImage mobImage;
    BufferedImage playerImage;

    public DisplayPanelCell(int xVal, int yVal, GridContainer parent)
    {
        parentGrid = parent;
        x=xVal;
        y=yVal;
        
        setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        setPreferredSize(new Dimension(parentGrid.parentDisplayPanel.fullsquaresize-2,parentGrid.parentDisplayPanel.fullsquaresize-2));
        setBackground(Color.LIGHT_GRAY);
        repaint();

    }

       public void resetToolTipText()
    {
        String text = parentGrid.parentDisplayPanel.parentDD.terrainArray.imageList[terrain];
        if(object != 0)
            text = parentGrid.parentDisplayPanel.parentDD.objectArray.imageList[object] + ", " + text ;
        if(effect != 0)
            text = parentGrid.parentDisplayPanel.parentDD.effectArray.imageList[effect] + ", " + text ;
        if(!player.equals(""))
            text = player + ", " + text;
        if(!mob.equals(""))
            text = mob + ", " + text;
 
        
        if ( roomNum > 0 )
        {
            text = "This square is hidden. Unhide with Effects->Delete"; // if square is hidden, hide the tooltip text too, but include the coordinates
        }
        
        text = "(" + x + "," + y + ") " + text;

        
        setToolTipText(text);
    }


    void changeTerrain(int newTerrain)
    {
        terrain = newTerrain;
            resetToolTipText();
    }
    
    void changeObject(int newObject)
    {
        object = newObject;
            resetToolTipText();
    }

    
    void changeEffect(int newEffect)
    {
        effect = newEffect;
            resetToolTipText();
    }

    void changePlayer(String PlayerName)
    {
        if (player.equals(PlayerName))
        {
//            movePlayer();
            player = "";
        }
        else
        {
        //    addnewPlayer();
            player = PlayerName;
            playerImage = utility.getImage(new File(DungeonDisplay.appdata + "\\DungeonDisplay\\Players\\Images\\" + PlayerName + ".gif"));

        }
        resetToolTipText();
    }        
    
    void changeMob(String mobName, int x, int y, int number)
    {
        if (mob.equals(mobName))
        {
//            moveMob();
            mob = "";
            
            
        }
        else
        {
        //    addnewPlayer();
            mob = mobName;
            mobImage = utility.getImage(new File(DungeonDisplay.appdata + "\\DungeonDisplay\\Bestiary\\Images\\" + mobName + ".gif"));

        }
        resetToolTipText();
    }
    public void changeRoom()
    {
        roomNum = MiscMenu.getRoomNum();
            resetToolTipText();
    }
    public void changeRoom(int newRoomNum)
    {
        roomNum = newRoomNum;
            resetToolTipText();
    }
    
    @Override
    protected void paintComponent(Graphics g)
    {
        DungeonDisplay parent = parentGrid.parentDisplayPanel.parentDD;
        DisplayPanel parentDP = parentGrid.parentDisplayPanel;
        setPreferredSize(new Dimension(parentDP.fullsquaresize-2,parentDP.fullsquaresize-2));
        revalidate();
        super.paintComponent(g);
        if (true)
        {
            g.drawImage(parent.terrainArray.panels[terrain].image,0,0,
                    parentDP.fullsquaresize-2, parentDP.fullsquaresize-2, null);
        }
        
        if (effect != 0)
        {
            g.drawImage(parent.effectArray.panels[effect].image,0,0,
                    parentDP.fullsquaresize-2,parentDP.fullsquaresize-2,null);
        }            
        if (object != 0)
        {
            g.drawImage(parent.objectArray.panels[object].image,0,0,
                    parentDP.fullsquaresize-2,parentDP.fullsquaresize-2,null);
        }
        if(!player.equals(""))
        {
            g.drawImage(playerImage,0,0,parentDP.fullsquaresize-2,parentDP.fullsquaresize-2,null);
        }
        if(!mob.equals(""))
        {
            g.drawImage(mobImage,0,0,parentDP.fullsquaresize-2,parentDP.fullsquaresize-2,null);
        }
        if (roomNum > 0 && !ImagePane.hide) // if "Hide Square" is on a square, and things 
        {                                   //          aren't hidden, show room numbers
            g.setColor(new Color(209,209,209));
            g.fillRect(0,0,7*((int)Math.log10(roomNum))+7,12);
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(roomNum),0,10);
        }
        else if (roomNum > 0 && ImagePane.hide) // if "Hide Square" is on a square, and things 
        {                                   //          aren't hidden, show room numbers
            g.setColor(new Color(167,162,175));
            g.fillRect(0,0,parentDP.fullsquaresize,parentDP.fullsquaresize);
        }
        
    }
    
    protected void addMouseListener()
    {
        addMouseListener(new MouseAdapter(){
            
            @Override
            public void mousePressed (MouseEvent e)
            {
                if(roomNum>0 && ImagePane.hide) // unhides whole group of squares with this roomNum if they are hidden.
                {
                    int num=roomNum;
                    for(int i=0;i<parentGrid.gridheight;i++)
                    {
                        for(int j=0;j<parentGrid.gridwidth;j++)
                        {
                            if(parentGrid.grid[i][j].roomNum == num)
                            {
                                parentGrid.grid[i][j].roomNum=0;
                                parentGrid.grid[i][j].repaint();
                            }
                        }
                    }
                }
                else if(!GridContainer.pathActive && e.isAltDown() == true && !player.equals("")) // if there is a player on the square, and alt held down, start path
                {
                    GridContainer.pathActive = true;
                    GridContainer.path = new Path(x,y);
                }
                else if(e.isShiftDown() == true && e.isControlDown()==false && oldCoords != null)
                {   //if shift is down, fill in the whole area from oldCoords to the clicked location
                    int i = oldCoords[0];
                    int j= oldCoords[1];

                    int iincrease,jincrease; // dummy variables to make it less loops

                    if(oldCoords[0]<=x)
                        iincrease = 1;
                    else 
                        iincrease = -1;

                    if(oldCoords[1]<=y)
                        jincrease = 1;
                    else
                        jincrease = -1;

                    while(i!=x+iincrease)
                    {
                        while(j!=y+jincrease)
                        {

                            if(ImagePane.writeType==0)
                                parentGrid.grid[i][j].changeTerrain(Integer.parseInt(ImagePane.writeName));
                            if(ImagePane.writeType==1)
                                parentGrid.grid[i][j].changeObject(Integer.parseInt(ImagePane.writeName));
                            if(ImagePane.writeType==2)
                                parentGrid.grid[i][j].changeEffect(Integer.parseInt(ImagePane.writeName));
                            if((oldCoords[1]!=j || oldCoords[0]!=i) && ImagePane.writeType==4)
                                parentGrid.grid[i][j].changeMob(ImagePane.writeName,i,j,1);    
                            if(ImagePane.writeType==5)
                                parentGrid.grid[i][j].changeRoom();

                            parentGrid.grid[i][j].repaint();

                            j=j+jincrease;


                        }

                        i=i+iincrease;
                        j= oldCoords[1];
                    }

                }
                else // default action; occurs if no shift is held and no group is inside the visible place
                {
                    if(ImagePane.writeType==0)
                        changeTerrain(Integer.parseInt(ImagePane.writeName));
                    if(ImagePane.writeType==1)
                        changeObject(Integer.parseInt(ImagePane.writeName));
                    if(ImagePane.writeType==2)
                        changeEffect(Integer.parseInt(ImagePane.writeName));
                    if(ImagePane.writeType==3)
                        changePlayer(ImagePane.writeName);
                    if(ImagePane.writeType==4)
                        changeMob(ImagePane.writeName,x,y,1);          
                    if(ImagePane.writeType==5)
                        changeRoom();             

                    repaint();

                    oldCoords[0]=x;
                    oldCoords[1]=y;
                }
                DungeonDisplay.debugtext.append(e.paramString() + "\nx=" + x + "\ny=" + y + "\n");
            
                
            }
            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(e.isControlDown()==true)
                {
                    mousePressed(e);
                }
                resetToolTipText();
            }
        }
        );
    }

}