/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
    private MiscMenu mm;
    JMenuBar menuBar;
    
    public DisplayPanel(int resolution, int colnum, int rownum, DungeonDisplay parent, MiscMenu miscMen)
    {
        
        menuBar = makeMenuBar();
        parent.setJMenuBar(menuBar);
        gridContainer = new GridContainer(colnum, rownum, this);
        parentDD = parent;
        setLayout(new GridLayout(1,1));
        gridScrollPane = new JScrollPane(gridContainer);
        add(gridScrollPane);
        fullsquaresize = resolution;
        mm = miscMen;
        
        gridContainer.gridwidth = colnum;
        gridContainer.gridheight = rownum;

        setBackground(Color.GRAY);
        gridContainer.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.isControlDown()) {
                    mm.resize(e.getWheelRotation());
                    System.out.println("absf");
                }
                    
            }
            
        });
    }

    private JMenuBar makeMenuBar() {
        JMenuBar result = new JMenuBar();
        JMenu fileMenu = makeMenu("File", KeyEvent.VK_F, "File menu");
        {
            // new
            JMenuItem newFileItem = makeMenuItem("New Window", KeyEvent.VK_N,
                    "Open \"new file\" dialog",
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("new window pressed");
                        }
                    });
            fileMenu.add(newFileItem);
            //open
            JMenuItem closeWindowItem = makeMenuItem("Close Window", 
                    KeyEvent.VK_W, "Closes current window", 
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("close window pressed");
                        }
                    });
            fileMenu.add(closeWindowItem);
        }
        result.add(fileMenu);
        
        JMenu creationMenu = makeMenu("Create", KeyEvent.VK_C, "Creation menu");
        {
            // new
            JMenuItem roomItem = makeMenuItem("Room", KeyEvent.VK_R, 
                    "generate a room", new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println("room generation pressed");
                        }
                    });
            creationMenu.add(roomItem);
        }
        result.add(creationMenu);
        
        return result;
    }
    
    private JMenu makeMenu(String name, int mnemonic, String description) {
        JMenu result = new JMenu(name);
        result.setMnemonic(mnemonic);
        result.getAccessibleContext().setAccessibleDescription(description);
        return result;
    }
    
    private JMenuItem makeMenuItem(String name, int mnemonic, String description, ActionListener listener) {
        JMenuItem result = new JMenuItem(name);
        result.setMnemonic(mnemonic);
        result.getAccessibleContext().setAccessibleDescription(description);
        result.addActionListener(listener);
        return result;
    }
    

}
