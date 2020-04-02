package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import dataframe.DataFrame;
import machinations.Model;



/****
 * TO DO: 
 * - Add closeable tabs
 * - Implement a more efficient way of displaying a dataframe
 * - User inputted regression
 * - Display confidence intervals
 * - Model menu
 * - Pipeline display
 * - Menu on homescreen
 * - AND MUCH MORE !
 * @author Cade
 *
 */

public class SibylGUI extends JFrame {
    /** THE KEY TO LIFE */
    private static final long serialVersionUID = -3696717041450563682L;

    /** Name of the GUI */
    private static final String GUI_NAME = "Sibyl";
    
    /** A hashmap of the dataframes loaded in. */ 
    private HashMap<String, DataFrame> loadedDataFrames;
    
    private MainToolBar toolBar;
    
    /** A HashSet of plots. */
    private HashSet<JPanel> plots;
    
    /** The current model in view. */
    private Model currentModel;
    
    /** The center tabs of the GUI. */
    private JTabbedPane centerTabs;
    
    
    public SibylGUI() {
        super(GUI_NAME);
        currentModel = null;
        loadedDataFrames = new HashMap<String, DataFrame>();
        toolBar = new MainToolBar(this);
        centerTabs = new JTabbedPane();
        plots = new HashSet<JPanel>();
        start();
    }
    
    
    /**
     * Calls all methods required to build the GUI.
     */
    public void start() {
        this.setLayout(new BorderLayout());
        this.add(toolBar, BorderLayout.NORTH);
        this.add(centerTabs, BorderLayout.CENTER);
        buildHomeMenu();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
    
    
    private void buildHomeMenu() {
        JPanel homePanel = new JPanel();
        JLabel startImage = new JLabel(new ImageIcon("BackgroundPics/ai_world.jpg"));
        JLabel greeting = new JLabel("SIBYL GUI VERSION 0.1");
        homePanel.setLayout(new BorderLayout());
        homePanel.add(startImage, BorderLayout.CENTER);
        homePanel.add(greeting, BorderLayout.NORTH);
        addTab("Home", homePanel);
    }
    
    /**
     * Loads a dataframe given a filepath and option.
     * Options: 
     * 0 = CSV format
     * 1 = TSV format
     * 2 = XLSX format
     * @param file
     * @param option
     */
    public void loadDataframe(File file, int option) {
        System.out.println("Loading data frame GUI");
        try {
            if (option == 0) {
                DataFrame dataFrame = DataFrame.read_csv(file.getAbsolutePath());
                this.loadedDataFrames.put(dataFrame.getName(), dataFrame);
                toolBar.addDataFrame(dataFrame.getName());
            } else if (option == 1) {
                
            } else if (option == 2) {
                
            }
            JOptionPane.showMessageDialog(this, "Dataframe successfully loaded.", "Notice", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading in dataframe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void addPlotDisplay(String dfName) {
        ScatterPlotView plot = new ScatterPlotView(loadedDataFrames.get(dfName));
        addTab(dfName + " Plot", plot);
    }
    
      /**
      * Displays the entire dataframe
      */
     public void addDataFrameDisplay(String dfName) {
         DataFrame df = loadedDataFrames.get(dfName);
         if (loadedDataFrames.size() == 0) {
             JOptionPane.showMessageDialog(this, "No dataframes have been initialized.", "Error", JOptionPane.ERROR_MESSAGE);
         } else {
             JPanel panel = new JPanel();
             panel.setLayout(new GridLayout(df.getNumRows() + 1, df.getNumColumns()));
             //Initialize the names of the columns
             for (int i = 0; i < df.getNumColumns(); i++) { 
                 panel.add(new JLabel(df.getColumn(i).getName()));
             }
             //Add all of the data from the data frame to the display.
             for (int rowNum = 0; rowNum < df.getNumRows(); rowNum++) {
                 for (int colNum = 0; colNum < df.getNumColumns(); colNum++) {
                     JLabel textBox = new JLabel("" + df.getRow_byIndex(rowNum).getParticle(colNum).getValue());
                     panel.add(textBox);
                 }
             }
             addTab(dfName + " data", new JScrollPane(panel));
         }
     }
    
     private void refresh() {
         this.revalidate();
         this.repaint();
         this.pack();
     }
     
     /**
      * Refreshes the center view of the GUI.
      * @param center the new center. 
      */
     private void addTab(String tabName, Component tab) {
         centerTabs.addTab(tabName, tab);
         this.revalidate();
         this.repaint();
     }
//    
//    private JMenu createAlgorithmsMenu() {
//        
//    }
    
    /***********************************
     * OLD CODE !!!! 
     ***********************************/
    
//    /**
//     * Builds the options/inputs menu.
//     */
//    private void buildGenericOptionsMenu() {
//        for (int i = 0; i < columnsToUse.size(); i++) {
//            JPanel currentPanel = new JPanel(new BorderLayout());
//            JLabel currentLabel = new JLabel(columnsToUse.get(i));
//            Set<Object> currentOptions = dataFrame.getColumn(i).getUniqueValues(); //OPTIMIZE 
//            if (currentOptions.size() >= 25) {  //TEXT BOX
//                JTextField textInput = new JTextField(10);
//                currentPanel.add(currentLabel, BorderLayout.NORTH);
//                currentPanel.add(textInput, BorderLayout.SOUTH);
//                textInput.addActionListener(new InputActionListener(columnsToUse.get(i), true));
//                variableInputs.put(columnsToUse.get(i), null);
//            } else {                            //DROP DOWN MENU
//                JComboBox<String> dropDownInput = createDropDownMenus(currentOptions);
//                currentPanel.add(currentLabel, BorderLayout.NORTH);
//                currentPanel.add(dropDownInput, BorderLayout.SOUTH);
//                variableInputs.put(columnsToUse.get(i), (String) dropDownInput.getSelectedItem());
//                dropDownInput.addActionListener(new InputActionListener(columnsToUse.get(i), false));
//            }
//            genericOptionsMenu.add(currentPanel);
//            genericOptionsMenu.addSeparator();
//        }
//        JButton runButton = new JButton("Run Algorithm");
//        runButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                runAlgorithm();
//            }   
//        });
//        genericOptionsMenu.add(runButton);
//    }
//    
//    /**
//     * Converts a set to a dropdown menu consiting of the items within the set.
//     * @param currentOptions the options to convert to a dropdown menu.
//     * @return a dropdown menu consisting of the items in the passed set. 
//     */
//    private JComboBox<String> createDropDownMenus(Set<Object> currentOptions) {
//        JComboBox<String> options = new JComboBox<String>();
//        for (Object o : currentOptions) {
//            if (o instanceof String)
//                options.addItem((String) o);
//            else
//                options.addItem("" + o);
//        }
//        return options;
//    }
//    
//    /**
//     * Runs the algorithm.
//     */
//    private void runAlgorithm() {
//        if (variableInputs.containsValue(null) || sdInputs[0] == null || sdInputs[1] == null) {
//            JOptionPane.showMessageDialog(this, "One or more inputs have not been properly initialized.", 
//                    "Invalid Input(s)",
//                    JOptionPane.ERROR_MESSAGE);
//        } else {
//            System.out.println(variableInputs); //temporary to check for proper storage of inputs.
//        }
//    }
//    
//    /**
//     * Builds the proportion of defaults and succsesses options menu.
//     */
//    private void buildOptionsMenu() {
//        JLabel defaults = new JLabel("Proportion of Defaults", JLabel.TRAILING);
//        JPanel defaultPanel = new JPanel(new BorderLayout());
//        JTextField dTextBox = new JTextField(10);
//        dTextBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sdInputs[0] = dTextBox.getText();
//            }
//            
//        });
//        defaults.setLabelFor(dTextBox);
//        defaultPanel.add(dTextBox, BorderLayout.EAST);
//        defaultPanel.add(defaults, BorderLayout.WEST);
//        JLabel succsesses = new JLabel("Proportion of Succsesses", JLabel.TRAILING);
//        JPanel succsessesPanel = new JPanel(new BorderLayout());
//        JTextField sTextBox = new JTextField(10);
//        sTextBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                sdInputs[1] = sTextBox.getText();
//            }
//            
//        });
//        succsesses.setLabelFor(sTextBox);
//        succsessesPanel.add(sTextBox, BorderLayout.EAST);
//        succsessesPanel.add(succsesses, BorderLayout.WEST);
//        sdOptionsMenu.add(defaultPanel, BorderLayout.NORTH);
//        sdOptionsMenu.add(succsessesPanel, BorderLayout.SOUTH);
//    }
//   
//    /**
//     * Creates an action listener for a combo box or text box.
//     * @author Cade Reynoldson
//     * @version 1.0
//     */
//    private class InputActionListener extends AbstractAction {
//
//        /** STOP GIVING ME ERRORS FOR NOT HAVING THIS *ANGRY REACT* */
//        private static final long serialVersionUID = 8379111664161837548L;
//        
//        /** Name of the input box */
//        private String inputName;
//        
//        /** Indicates if the action listener is for a textbox. */
//        private boolean isTextBox;
//        
//        /**
//         * Creates an action listener for a combo box.
//         * @param theName the name of the box.
//         * @param textOrCombo true for text box, false for combo box.
//         */
//        public InputActionListener(String theName, boolean textOrCombo) {
//            inputName = theName;
//            isTextBox = textOrCombo;
//            
//        }
//        
//        /** 
//         * Replaces the current value in the variable input map with the newly selected one. 
//         */
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (isTextBox) {
//                JTextField tempTB = (JTextField) e.getSource();
//                variableInputs.replace(inputName, tempTB.getText());
//            } else {
//                @SuppressWarnings("unchecked")
//                JComboBox<String> tempCB = (JComboBox<String>) e.getSource();
//                variableInputs.replace(inputName, (String) tempCB.getSelectedItem());
//            }
//        }
//        
//    }

}
