/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

/**
 *
 * @author plis
 */

import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics;

public class Path {
    ArrayList<int[]> pathCoordList = new ArrayList<int[]>();
    final static int[] up = {0,-1,0};
    final static int[] right = {1,0,1};
    final static int[] down = {0,1,2};
    final static int[] left = {-1,0,3};
    
    
    public Path(int xStart,int yStart)
    {
        int[] firstCoords = new int[3];
        firstCoords[0]= xStart;
        firstCoords[1]= yStart;
        firstCoords[2]= -1;
        pathCoordList.add(firstCoords);
    }
    
    public int[] getFirstCoord()
    {
        return pathCoordList.get(0);
    }
    public int[] getLastCoord()
    {
        return pathCoordList.get(pathCoordList.size()-1);
    }
    public int[] getSecondtoLastCoord()
    {
        if (pathCoordList.size() != 1)
        {
            return pathCoordList.get(pathCoordList.size()-2);
        }
        else
            return new int[] {-1,-1};
    }
    public int[] getNextCoord(int[] Direction) // Direction is {0,-1} for up, {-1,0} for left, {1,0} for right, and {0,1} for down
    {
        int[] LastCoord = getLastCoord();
        int[] result = new int[3];
        result[0] = LastCoord[0] + Direction[0];
        result[1] = LastCoord[1] + Direction[1];
        result[2] = Direction[2];
        return result;
    }
    
    public void moveUp()
    {
        if(getNextCoord(up)[0] == getSecondtoLastCoord()[0] &&
           getNextCoord(up)[1] == getSecondtoLastCoord()[1])
            undoMove();
        else
            moveTo(getNextCoord(up));
    }
    public void moveLeft()
    {
        if(getNextCoord(left)[0] == getSecondtoLastCoord()[0] &&
           getNextCoord(left)[1] == getSecondtoLastCoord()[1])
            undoMove();
        else
            moveTo(getNextCoord(left));
    }
    public void moveRight()
    {
        if(getNextCoord(right)[0] == getSecondtoLastCoord()[0] && 
           getNextCoord(right)[1] == getSecondtoLastCoord()[1])
            undoMove();
        else
            moveTo(getNextCoord(right));
    }    
    public void moveDown()
    {
        if(getNextCoord(down)[0] == getSecondtoLastCoord()[0] && 
           getNextCoord(down)[1] == getSecondtoLastCoord()[1])
            undoMove();
        else
            moveTo(getNextCoord(down));
    }
    
    public void moveTo(int[] newCoords)
    {
         //if the move is within the bounds of the grid, add the point to the path.
        pathCoordList.add(newCoords);
//        DungeonDisplay.debugtext.append("Path at\nx=" + newCoords[0] + "\ny=" + newCoords[1] + "\n");
    }
 
    public void undoMove() //occurs if the person tries to move backward onto a place they had already been.
    {
        /*
        DungeonDisplay.grid[pathCoordList.get(pathCoordList.size()-1)[0]][pathCoordList.get(pathCoordList.size()-1)[1]].repaint();
        pathCoordList.remove(pathCoordList.size()-1);
        
        drawPath();*/
    }
    
    
    public void drawPath()
    {
        /*
        if(pathCoordList.size()>1)
        {
            int midpt = DungeonDisplay.displayPanel.fullsquaresize/2;
            for(int i=1;i<pathCoordList.size();i++)
            {
            //insert drawline stuff here
                
                Graphics g1 = DungeonDisplay.grid[pathCoordList.get(i-1)[0]][pathCoordList.get(i-1)[1]].getGraphics();
                Graphics g2 = DungeonDisplay.grid[pathCoordList.get(i)[0]][pathCoordList.get(i)[1]].getGraphics();

                g1.setColor(Color.RED);
                g2.setColor(Color.RED);
                int dir = pathCoordList.get(i)[2];
                if(dir == up[2])
                {
                    g1.drawLine(midpt,midpt,midpt,0);
                    g2.drawLine(midpt,midpt*2,midpt,midpt);
                }
                else if(dir == right[2])
                {
                    g1.drawLine(midpt,midpt,midpt*2,midpt);
                    g2.drawLine(0,midpt,midpt,midpt);
                }
                else if(dir == down[2])
                {
                    g1.drawLine(midpt,midpt,midpt,midpt*2);
                    g2.drawLine(midpt,0,midpt,midpt);
                }
                else if(dir == left[2])
                {
                    g1.drawLine(midpt,midpt,0,midpt);
                    g2.drawLine(midpt*2,midpt,midpt,midpt);
                } 
            }
        }
        */
    }
    

}
