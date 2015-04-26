/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JScrollPane;

/**
 *
 * @author plis
 */
public class DisplayPanel extends JXFPanel
{
    DungeonDisplay parentDD;
    int fullsquaresize = 80;
    GridContainer gridContainer;
    JScrollPane gridScrollPane;
    public DisplayPanel(int resolution, int colnum, int rownum, DungeonDisplay parent)
    {
        gridContainer = new GridContainer(colnum, rownum, this);
        parentDD = parent;
        setLayout(new GridLayout(1,1));
        gridScrollPane = new JScrollPane(gridContainer);
        add(gridScrollPane);
        fullsquaresize = resolution;

        gridContainer.gridwidth = colnum;
        gridContainer.gridheight = rownum;

        setBackground(Color.GRAY);
    }

}
