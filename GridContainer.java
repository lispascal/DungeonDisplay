/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author plis
 */
public class GridContainer extends JXFPanel
{
    static boolean pathActive = false;
    static Path path;
    List<List<Cell> > cellGrid;
    DisplayPanelCell[][] grid;
    int gridwidth;
    int gridheight;
    DisplayPanel parentDisplayPanel;
    
    public GridContainer(int colnum, int rownum, DisplayPanel parent)
    {
        gridwidth = colnum;
        gridheight = rownum;
        parentDisplayPanel = parent;
        grid = fillgrid(gridwidth, gridheight);
        
        setFocusable(true);
        setBackground(Color.BLACK);
        setLayout(new GridLayout(0,1,0,2)); //grid that holds 1 column of panels
        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
//                DungeonDisplay.debugtext.append(evt.getKeyChar() + "\n");
                if(pathActive)
                {
                    if(evt.getKeyCode() == KeyEvent.VK_UP ||
                    evt.getKeyCode() == KeyEvent.VK_KP_UP ||     //if d or a right arrow is pressed,
                    evt.getKeyCode() == KeyEvent.VK_W )             //move to the right (or delete right path side
                    {
                        path.moveUp();
                    }
                    else if(evt.getKeyCode() == KeyEvent.VK_LEFT ||
                    evt.getKeyCode() == KeyEvent.VK_KP_LEFT ||     //if d or a right arrow is pressed,
                    evt.getKeyCode() == KeyEvent.VK_A )             //move to the right (or delete right path side
                    {
                        path.moveLeft();
                    }
                    else if(evt.getKeyCode() == KeyEvent.VK_RIGHT ||
                    evt.getKeyCode() == KeyEvent.VK_KP_RIGHT ||     //if d or a right arrow is pressed,
                    evt.getKeyCode() == KeyEvent.VK_D )             //move to the right (or delete right path side
                    {
                        path.moveRight();
                    }
                    else if(evt.getKeyCode() == KeyEvent.VK_DOWN ||
                    evt.getKeyCode() == KeyEvent.VK_KP_DOWN ||     //if d or a right arrow is pressed,
                    evt.getKeyCode() == KeyEvent.VK_S )             //move to the right (or delete right path side
                    {
                        path.moveDown();
                    }
                    if(path!=null)
                        path.drawPath();
                }
                
            }
        }
        );
    }

    public void fillArea(int startX, int startY, int endX, int endY) {

        int iinc, jinc; // indicate direction of incrementation

        if (startX > endX)
        {
            int temp = startX;
            startX = endX;
            endX = temp;
        }
        if (startY > endY) {
            int temp = startY;
            startY = endY;
            endY = temp;
        }

        for(int i = startX; i <= endX; i++)
        {
            for(int j = startY; j <= endY; j ++)
            {
                if (ImagePane.writeType == 0) {
                    cellGrid.get(i).get(j).changeTerrain(Integer.parseInt(ImagePane.writeName));
                }
                else if (ImagePane.writeType == 1) {
                    cellGrid.get(i).get(j).changeObject(Integer.parseInt(ImagePane.writeName));
                }
                else if (ImagePane.writeType == 2) {
                    cellGrid.get(i).get(j).changeEffect(Integer.parseInt(ImagePane.writeName));
                }
                else if ((startY != j || startX != i) && ImagePane.writeType == 4) {
                    grid[i][j].changeMob(ImagePane.writeName, i, j, 1);
                }
                else if (ImagePane.writeType == 5) {
                    grid[i][j].changeRoom();
                }

                grid[i][j].repaint();
            }
        }
        
    }
    
    private DisplayPanelCell[][] fillgrid(int x,int y)
    {
        DisplayPanelCell[][] result;  // array for resulting grid
        cellGrid = new ArrayList<>(x);
        result = new DisplayPanelCell[x][y];

         // array for row panels
        

        for(int i=0; i<x; i++)
        {
            List<Cell> col = new ArrayList<>(y);
            for(int j=0; j<y; j++)
                col.add(new Cell());
            cellGrid.add(col);
        }
        
        for(int i=0;i<y;i++) // iterate through rows, placing a jpanel for
        {                    // a row, then filling that row with cells
            JXFPanel[] row;
            row = new JXFPanel[x];
            row[i] = new JXFPanel(2,0,Color.BLACK);

            add(row[i]);
            for(int j=0;j<x;j++)
            {
                result[j][i] = new DisplayPanelCell(j,i, this);
                row[i].add(result[j][i]);
                result[j][i].addMouseListener();
            }

        }
        return result;
    }
    @Override
    protected void paintComponent(Graphics g)
    {
//        for(int i=0;i<gridwidth;i++)
//        {
//            row[i].setPreferredSize(new Dimension((parentDisplayPanel.fullsquaresize)*gridwidth,parentDisplayPanel.fullsquaresize-2));
//            row[i].revalidate();
//            row[i].repaint();
//        }
        for (DisplayPanelCell[] gridRow : grid) {
            for (DisplayPanelCell gridCell : gridRow) {
                gridCell.revalidate();
                gridCell.repaint();
            }
        }
        revalidate();
        super.paintComponent(g);
        if(path!=null)
            path.drawPath();
    }


}