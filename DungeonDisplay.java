/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dungeondisplay;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.swing.*;
import javax.swing.BoxLayout;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/**
 *
 * @author plis
 */


public class DungeonDisplay extends JFrame implements ActionListener {




    static final String currentVersion = "v0.6.4";
    static final int currentBuild = 8;
    static final int saveType = 3;


    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MakeDDFiles();
        

        FileDialog wind = new FileDialog("DungeonDisplay " + currentVersion);

        
        //InitiativeFrame test = new InitiativeFrame();
        //test.setVisible(true);
        //test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

    }

    static DungeonDisplay window;
    
                
    JXFPanel saveMenu = new JXFPanel();
    JLabel saveLabel = new JLabel("Enter save name below");
    JTextField saveNameField = new JTextField("untitled");
    JButton saveButton = new JButton("Save");
    JLabel saveStatusField = new JLabel();
    

    
    
    Container c = getContentPane();
    
    
    static JTextArea debugtext = new JTextArea(30,10);

    ImageMenu terrainArray = new ImageMenu(0, this);
    ImageMenu objectArray = new ImageMenu(1, this);
    ImageMenu effectArray = new ImageMenu(2, this);
    
    
    JPanel mobMenuPanel = new JPanel();
    JPanel playerMenuPanel = new JPanel();
    
    
    MobPaneMenu playerMenu = new MobPaneMenu("player","Players", this);
    MobPaneMenu mobMenu = new MobPaneMenu("mob","Bestiary", this);
    MiscMenu miscMenu = new MiscMenu(this);
    
    
    JScrollPane terrainScroller = new JScrollPane(terrainArray);
    JScrollPane objectScroller = new JScrollPane(objectArray);
    JScrollPane effectScroller = new JScrollPane(effectArray);
    JScrollPane playerScroller = new JScrollPane(playerMenu,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    JScrollPane mobScroller = new JScrollPane(mobMenu,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
    
    
    int displaywidth;
    int displayheight;
    Dimension displaysize;
    Dimension menusize;
    Dimension scrnsize;
    
    static DisplayPanel displayPanel;
    
    
    public DungeonDisplay(String windowname,int colnum, int rownum, int resolution)
    {
        super(windowname);
        displayPanel = new DisplayPanel(resolution, colnum, rownum, this);
        JTabbedPane menuPane = new JTabbedPane();
        
        
        // initialize buttons and panels
        JButton ExitButton = new JButton("exit");

 
        JPanel menuPanel = new JPanel();

        
        // figure out window dimensions from screen dimensions
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        scrnsize = toolkit.getScreenSize();
        
        displaywidth = (int)(scrnsize.getWidth()-220);
        displayheight = (int)(scrnsize.getHeight());
        displaysize = new Dimension(displaywidth,displayheight);
        
        menusize = new Dimension(200,(int)scrnsize.getHeight());
        
        setSize((int)scrnsize.getWidth(),(int)scrnsize.getHeight()); //sets size of window
      //  displayPanel.setPreferredSize(displaysize);   //should be moved below the fillgrid, to depend on size of grid
        
       
        menuPanel.setPreferredSize(menusize);

        c.setBackground(Color.white);
     //   c.setLayout(new FlowLayout());

        menuPanel.setLayout(new BorderLayout());
        menuPanel.add(ExitButton,BorderLayout.SOUTH);
        menuPanel.add(menuPane,BorderLayout.CENTER);
        menuPane.addTab("Terrain",terrainScroller);
            terrainArray.addImages();
        menuPane.addTab("Objects",objectScroller);
            objectArray.addImages();
        menuPane.addTab("Effects",effectScroller);
            effectArray.addImages();
        menuPane.addTab("Player",playerScroller);
        menuPane.addTab("Bestiary",mobScroller);
        menuPane.addTab("Misc.",miscMenu);
            
//        menuPane.addTab("Debug",debugtext);
//            debugtext.setLineWrap(true);
        menuPane.addTab("Save",saveMenu);
        saveMenu.setLayout(new BoxLayout(saveMenu,BoxLayout.Y_AXIS));
        saveMenu.add(saveLabel);
        saveMenu.add(saveNameField);
        saveMenu.add(saveButton);
        JPanel saveMenuFiller = new JPanel();
        saveMenu.add(saveStatusField);
        saveMenu.add(saveMenuFiller);
        saveMenuFiller.setPreferredSize(new Dimension(200,displayheight-200));
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveStatusField.setText("saving...");
                saveGrid(displayPanel.gridContainer.grid,saveNameField.getText());
                saveStatusField.setText("Saved!");

            }
        });
        
        
        c.add(displayPanel,BorderLayout.CENTER); 
        c.add(menuPanel,BorderLayout.EAST);
        ExitButton.addActionListener(this);
    }
    
    static String appdata = System.getenv("APPDATA"); //means this part will only work for windows (probably)

    private static void MakeDDFiles(){ //makes any files that are lacking; deletes ones from old versions
        
        if (!utility.FileExists(appdata + "\\DungeonDisplay"))
        {
            boolean success = new File(appdata + "\\DungeonDisplay").mkdir();
            if (!success)
            {
                System.out.println("could not make %appdata%\\DungeonDisplay directory");
                System.exit(1);
            }
        }
        
        
        
        
        
        
        //rewrite the config file. Basically an "update version" command set
        new File(appdata + "\\DungeonDisplay\\config.txt").delete();
        try{
            Writer out = new BufferedWriter(new FileWriter(appdata + "\\DungeonDisplay\\config.txt"));
            out.write(currentVersion);
            out.write("\n");
            out.write(Integer.toString(currentBuild));
            out.close();
        }
        catch(IOException e)
        {
            //e.printStackTrace();
        }
        
        
        
        
        
        
        //make player directory if it doesn't exist. this directory holds player images and data
        if (!utility.FileExists(appdata + "\\DungeonDisplay\\Players"))
            new File(appdata + "\\DungeonDisplay\\Players").mkdir();
        if (!utility.FileExists(appdata + "\\DungeonDisplay\\Players\\Data"))
            new File(appdata + "\\DungeonDisplay\\Players\\Data").mkdir(); 
        if (!utility.FileExists(appdata + "\\DungeonDisplay\\Players\\Images"))
            new File(appdata + "\\DungeonDisplay\\Players\\Images").mkdir();

        
        
        //make bestiary directory if it doesn't exist. this directory holds mob images and data
        if (!utility.FileExists(appdata + "\\DungeonDisplay\\Bestiary"))
            new File(appdata + "\\DungeonDisplay\\Bestiary").mkdir();
        if (!utility.FileExists(appdata + "\\DungeonDisplay\\Bestiary\\Data"))
            new File(appdata + "\\DungeonDisplay\\Bestiary\\Data").mkdir(); 
        if (!utility.FileExists(appdata + "\\DungeonDisplay\\Bestiary\\Images"))
            new File(appdata + "\\DungeonDisplay\\Bestiary\\Images").mkdir();
        
        
        
        //create maps directory to hold map files
        if (!utility.FileExists(appdata + "\\DungeonDisplay\\Maps"))
            new File(appdata + "\\DungeonDisplay\\Maps").mkdir();
        
        
        
        //delete legacy files; old and obsolete
        if (utility.FileExists(appdata + "\\DungeonDisplay\\Bestiary\\list.txt"))
        {
            new File(appdata + "\\DungeonDisplay\\Bestiary\\list.txt").delete();
        }
        if (utility.FileExists(appdata + "\\DungeonDisplay\\Bestiary\\config.txt"))
        {
            new File(appdata + "\\DungeonDisplay\\Bestiary\\config.txt").delete();
        }
        if (utility.FileExists(appdata + "\\DungeonDisplay\\Players\\list.txt"))
        {
            new File(appdata + "\\DungeonDisplay\\Players\\list.txt").delete();
        }
        if (utility.FileExists(appdata + "\\DungeonDisplay\\Players\\config.txt"))
        {
            new File(appdata + "\\DungeonDisplay\\Players\\config.txt").delete();
        }
        if (utility.FileExists(appdata + "\\DungeonDisplay\\Maps\\list.txt"))
        {
            new File(appdata + "\\DungeonDisplay\\Maps\\list.txt").delete();
        }
        if (utility.FileExists(appdata + "\\DungeonDisplay\\Maps\\config.txt"))
        {
            new File(appdata + "\\DungeonDisplay\\Maps\\config.txt").delete();
        }
        
        

    }
    
    private void saveGrid(DisplayPanelCell[][] board,String saveName)
    {
        String saveLocation = appdata + "\\DungeonDisplay\\Maps\\" + saveName + ".ddm";
        int mapNum=0;

        try
        {
            Writer out = new BufferedWriter(new FileWriter(saveLocation));
            String everything = saveName + "\n" + currentVersion + "\n" + currentBuild + "\n" + saveType + "\n" + Integer.toString(displayPanel.gridContainer.gridwidth) + "\n" + Integer.toString(displayPanel.gridContainer.gridheight) + "\n";
            for(int i=0;i<displayPanel.gridContainer.gridheight;i++)
            {
                for(int j=0;j<displayPanel.gridContainer.gridwidth;j++)
                {
                    if(!"".equals(board[j][i].player))
                    {
                        everything = everything + "-3\n" + board[j][i].player + "\n";
                    }
                    if(!"".equals(board[j][i].mob))
                    {
                        everything = everything + "-2\n" + board[j][i].mob + "\n" + board[j][i].mobnumber + "\n";
                    }
                    if(0 != board[j][i].effect)
                    {
                        everything = everything + "-1\n" + board[j][i].effect + "\n";
                    }
                    everything = everything + Integer.toString(board[j][i].roomNum) + "\n"
                                            + Integer.toString(board[j][i].object) + "\n"
                                            + Integer.toString(board[j][i].terrain) + "\n";
                }
            }
            out.write(everything);
            out.close();
            
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
    }

    static public void loadGrid(String loadName,int resolution)
    {
        String loadLocation = appdata + "\\DungeonDisplay\\Maps\\" + loadName;

        
        try
        {

            BufferedReader in = new BufferedReader(new FileReader(loadLocation));
            String line;
            int lineInt;
            String mapName = in.readLine();
            String versionNumber = in.readLine();
            int buildNumber = Integer.parseInt(in.readLine());
            int saveTypenum = Integer.parseInt(in.readLine());
                
            if (saveTypenum==1)           //for first savetype, which had problems loading. 
            {       

                int height = Integer.parseInt(in.readLine());
                int width = Integer.parseInt(in.readLine());
                window = new DungeonDisplay(loadName + " - DungeonDisplay " + currentVersion,width,height,resolution);
                window.setVisible(true);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                for(int i=0;i<height;i++) //loads saveType 2
                {
                    for(int j=0;j<width;j++)
                    {
                        lineInt = Integer.parseInt(in.readLine());
                        while(lineInt<0)
                        {
                            if(lineInt == -3)
                            {
                                //add player instance, the method of adding which contains changePlayer
                                displayPanel.gridContainer.grid[j][i].changePlayer(in.readLine());
                            }
                            else if(lineInt == -2)
                            {
                                String mob = in.readLine();
                                int mobNumber = Integer.parseInt(in.readLine());
                                //add mob instance, which contains
                                displayPanel.gridContainer.grid[j][i].changeMob(mob,j,i,mobNumber);
                            }
                            else if(lineInt == -1)
                            {
                                int effect = Integer.parseInt(in.readLine());
                                //add mob instance, which contains
                                displayPanel.gridContainer.grid[j][i].changeEffect(effect);
                            }
                            lineInt = Integer.parseInt(in.readLine());
                        }

                        displayPanel.gridContainer.grid[j][i].changeObject(lineInt);
                        displayPanel.gridContainer.grid[j][i].changeTerrain(Integer.parseInt(in.readLine()));
                    }
                }
                in.close();
            }    
            
            else if (saveTypenum==2)           //for old savetype(before roomNum added). 
            {       

                int width = Integer.parseInt(in.readLine());
                int height = Integer.parseInt(in.readLine());
                window = new DungeonDisplay(loadName + " - DungeonDisplay " + currentVersion,width,height,resolution);
                window.setVisible(true);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                for(int i=0;i<height;i++) //loads saveType 2
                {
                    for(int j=0;j<width;j++)
                    {
                        lineInt = Integer.parseInt(in.readLine());
                        while(lineInt<0)
                        {
                            if(lineInt == -3)
                            {
                                //add player instance, the method of adding which contains changePlayer
                                displayPanel.gridContainer.grid[j][i].changePlayer(in.readLine());
                            }
                            else if(lineInt == -2)
                            {
                                String mob = in.readLine();
                                int mobNumber = Integer.parseInt(in.readLine());
                                //add mob instance, which contains
                                displayPanel.gridContainer.grid[j][i].changeMob(mob,j,i,mobNumber);
                            }
                            else if(lineInt == -1)
                            {
                                int effect = Integer.parseInt(in.readLine());
                                //add mob instance, which contains
                                displayPanel.gridContainer.grid[j][i].changeEffect(effect);
                            }
                            lineInt = Integer.parseInt(in.readLine());
                        }

                        displayPanel.gridContainer.grid[j][i].changeObject(lineInt);
                        displayPanel.gridContainer.grid[j][i].changeTerrain(Integer.parseInt(in.readLine()));
                    }
                }
            
                in.close();
            }
            else if (saveTypenum==3)           //for current savetype. 
            {       

                int width = Integer.parseInt(in.readLine());
                int height = Integer.parseInt(in.readLine());
                window = new DungeonDisplay(loadName + " - DungeonDisplay " + currentVersion,width,height,resolution);
                window.setVisible(true);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                ImagePane.hide = true;
                
                for(int i=0;i<height;i++) //loads saveType 2
                {
                    for(int j=0;j<width;j++)
                    {
                        lineInt = Integer.parseInt(in.readLine());
                        while(lineInt<0)
                        {
                            if(lineInt == -3)
                            {
                                //add player instance, the method of adding which contains changePlayer
                                displayPanel.gridContainer.grid[j][i].changePlayer(in.readLine());
                            }
                            else if(lineInt == -2)
                            {
                                String mob = in.readLine();
                                int mobNumber = Integer.parseInt(in.readLine());
                                //add mob instance, which contains
                                displayPanel.gridContainer.grid[j][i].changeMob(mob,j,i,mobNumber);
                            }
                            else if(lineInt == -1)
                            {
                                int effect = Integer.parseInt(in.readLine());
                                //add mob instance, which contains
                                displayPanel.gridContainer.grid[j][i].changeEffect(effect);
                            }
                            lineInt = Integer.parseInt(in.readLine());
                        }

                        displayPanel.gridContainer.grid[j][i].changeRoom(lineInt);
                        displayPanel.gridContainer.grid[j][i].changeObject(Integer.parseInt(in.readLine()));
                        displayPanel.gridContainer.grid[j][i].changeTerrain(Integer.parseInt(in.readLine()));
                        
                    }
                }
                in.close();
            }                  
            
        }
        catch(IOException e)
        {
            System.out.println(e.getMessage());
        }
        
    }
    
    static public void deleteMap(File mapFile)
    {
        System.out.print(mapFile);
        mapFile.delete();
    }
    
    static public void renameMap(File Source, File Destination) // copies File Source, and renames/creates it into File Source 
    {
        System.out.print(Source);
        System.out.print(Destination);
        
        Source.renameTo(Destination);
        
    }
    
    

         
    
    @Override
    public void paint(Graphics g)
    {
        
        
        
        
        super.paint(g);

        
        
    }
    

    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if ("exit".equals(command)) {
            System.exit(0);
        }
        
    }


}