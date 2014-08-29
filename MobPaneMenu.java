/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dungeondisplay;

import java.io.*;
import javax.swing.JFileChooser;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;

/**
 *
 * @author plis
 */
public class MobPaneMenu extends JXFPanel implements ActionListener {
    String entityType;          // valid types are "player" and "mob"
    String entityDirectory;     // used for writing and reading Mob types
    int mobNum;                 // used to size MobPaneArray
    JButton newMobButton;       // Button that pops up a NewMobType dialog
    MobPane[] MobPaneArray;     // holds the MobPanes in this array
    JXFPanel listPanel = new JXFPanel();    // this panel contains the MobPane panes; it displays them
    DungeonDisplay parentDD;
    
    public MobPaneMenu(String entityTypeValue, String directoryName, DungeonDisplay parent) // valid directories are "Players" and "Bestiary"
    {
        parentDD = parent;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS)); // so addMobButton appears Below (and not aside) the listPanel/mob display
        listPanel.setLayout(new GridLayout(0,2,5,5));   // makes the listPanel show the mobs in a grid 2 wide (with 5 pixel spacing)
        entityType = entityTypeValue;  
        entityDirectory = DungeonDisplay.appdata + "\\DungeonDisplay\\" + directoryName + "\\"; // sets entityDirectory based on directoryName
        Populatelist();
        add(listPanel);     // places listPanel in the Menu
        newMobButton = new JButton("Add new " + entityType);    // makes newMobButton
        newMobButton.addActionListener(this);                   // sets an action listener on it
        add(newMobButton);                                      // puts it in the Menu
//        newMobButton.setPreferredSize(new Dimension(80,50));
    }

    public void createPlayer(File image,String name)
    {
        
        if(!new File(entityDirectory + "\\Images\\" + name + ".gif").exists()) // ensures player with name doesn't already exist
        {
            try
            {
                //copy over the chosen file to the entityDirectory\Images\  folder
                InputStream in = new FileInputStream(image);
                OutputStream out = new FileOutputStream(entityDirectory + "\\Images\\" + name + ".gif");
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0){
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                DungeonDisplay.debugtext.append("\nFile copied.");
                
                //increment mobNum, resize the array, then add new mob.
                mobNum++;
                MobPaneArray = new MobPane[mobNum]; 
                addMobPane(mobNum-1,name);
            }
            catch(FileNotFoundException ex)
            {
                System.out.println(ex.getMessage() + " in the specified directory.");
                System.exit(0);
            }
            catch(IOException e)
            {
                System.out.println(e.getMessage());  
            }
        }
        
    }
    
    public void Populatelist()
    {
        File[] fileList = new File(entityDirectory + "\\Images\\").listFiles();
        mobNum = fileList.length;
        MobPaneArray = new MobPane[mobNum]; 
        for(int i=0;i<mobNum;i++)
        {
            addMobPane(i,utility.removeExtension(fileList[i],".gif").getName());
        }
        
        
    }
    

    
    public void addMobPane (int position, String line)
    {
        MobPaneArray[position] = new MobPane(line, entityDirectory, entityType, parentDD); //problem here
                    listPanel.add(MobPaneArray[position]);
                    MobPaneArray[position].setToolTipText(line);
                    repaint();
    }
   
    public int getIntOf(String key)
    {
        
        for(int i=0;i<MobPaneArray.length;i++)
        {
            if(key.equals(MobPaneArray[i].name))
                return i;
        }
        return 0;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        NewMobType dialog = new NewMobType(this,entityType);
        dialog.setVisible(true);
    }    
    
    
    
    
    
    
    
}

