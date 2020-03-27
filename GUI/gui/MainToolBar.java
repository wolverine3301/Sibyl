package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainToolBar extends JMenuBar{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5274933693632287679L;

    /** Reference to the parent GUI. Allows for direct calling of the methods within the dataframe. */
    private SibylGUI mainGUI;

    private JMenuBar theMenu;
    
    public JMenu fileMenu; 
    
    /** Heirarchy of view menus */
    
    private JMenu viewMenu;
    
    private JMenu dataMenu;
    
    private JMenu plotMenu;
    
    public MainToolBar(SibylGUI parent) {
        super();
        this.mainGUI = parent;
        theMenu = new JMenuBar();
        buildMenu();
    }
    
    private void buildMenu() {
        createFileMenu();
        createViewsMenu();
        add(fileMenu);
        add(viewMenu);
    }
    
    private void createViewsMenu() {
        viewMenu = new JMenu("Views");
        dataMenu = new JMenu("New Data View");
        plotMenu = new JMenu("New Plot View");
        viewMenu.add(dataMenu);
        viewMenu.addSeparator();
        viewMenu.add(plotMenu);
    }
    
    /**
     * Adds a dataframe to the menu options (data and plot at this point)
     * @param dfName the name of the dataframe to add. 
     */
    public void addDataFrame(String dfName) {
        JMenuItem dataOption = new JMenuItem(dfName);
        dataOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.addDataFrameDisplay(dataOption.getText());
            }
        });
        dataMenu.add(dataOption);
        dataMenu.addSeparator();
        JMenuItem plotOption = new JMenuItem(dfName);
        plotOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.addPlotDisplay(dfName);
            }
        });
        plotMenu.add(plotOption);
        plotMenu.addSeparator();
        refreshMenu();
    }
    
    /**
     * Refreshes the main menu.
     */
    private void refreshMenu() {
        theMenu.revalidate();
        theMenu.repaint();
    }
    
    /**
     * Generates the file menu.
     */
    private void createFileMenu() {
        fileMenu = new JMenu("File");
        //Load in menu options. 
        JMenu loadDfMenu = new JMenu("Load Data"); //Load in options.
        JMenuItem csvFormat = new JMenuItem("CSV Formatted File");
        csvFormat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("CSV & TXT files", "csv", "txt"));
                int returnVal = fileChooser.showOpenDialog(csvFormat);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    mainGUI.loadDataframe(fileChooser.getSelectedFile(), 0);
                }
            }
        });
        JMenuItem tsvFormat = new JMenuItem("TSV Formatted File");
        JMenuItem xlsxFormat = new JMenuItem("XLSX Formatted File");
        loadDfMenu.add(csvFormat);
        loadDfMenu.addSeparator();
        loadDfMenu.add(tsvFormat);
        loadDfMenu.addSeparator();
        loadDfMenu.add(xlsxFormat);
        
        //Save options.
        JMenuItem saveDf = new JMenuItem("Save DataFrame");
        JMenuItem loadModel = new JMenuItem("Load Model");
        JMenuItem saveModel = new JMenuItem("Save Model");
        fileMenu.add(loadDfMenu);
        fileMenu.addSeparator();
        fileMenu.add(loadModel);
        fileMenu.addSeparator();
        fileMenu.add(saveDf);
        fileMenu.addSeparator();
        fileMenu.add(saveModel);
    }
}
