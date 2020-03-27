package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import particles.Particle;

public class SibylGUI extends JFrame {
    /** THE KEY TO LIFE */
    private static final long serialVersionUID = -3696717041450563682L;

    /** Name of the GUI */
    private static final String GUI_NAME = "Sibyl";
    
    /** Reference to the data frame */
    private DataFrame dataFrame;
    
    /** List of columns to create as inputs. */
    private ArrayList<String> columnsToUse;
    
    /** The JPanel which holds the options of successes and defaults option. */
    private JPanel sdOptionsMenu;
    
    /** The display for the data frame loaded in. */ 
    private JScrollPane dataFrameDisplay;
    
    /** Holds the inputs for the successes and defaults. Index 1 = successes, index 0 = defaults */
    private String[] sdInputs;
    
    /** Tool bar with options for each column. */
    private JToolBar genericOptionsMenu;
    
    /** Holds the input values for a given column */
    private HashMap<String, String> variableInputs;
    
    /**
     * 
     */
    public SibylGUI() {
        super(GUI_NAME);
        start();
    }
    
    /**
     * Constructs a new instance of the GUI.
     * @param theDataFrame reference to the main data frame.
     */
    public SibylGUI(DataFrame theDataFrame) {
        super(GUI_NAME);
        dataFrame = theDataFrame;
        columnsToUse = dataFrame.getColumnNames();
        sdOptionsMenu = new JPanel(new BorderLayout());
        sdInputs = new String[2];
        genericOptionsMenu = new JToolBar("Options");
        variableInputs = new HashMap<String, String>();
    }
    
    /**
     * Calls all methods required to build the GUI.
     */
    public void start() {
        this.setLayout(new BorderLayout());
        this.add(createToolBar(), BorderLayout.NORTH);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);
//        // Build the menu buttons.
//        buildOptionsMenu();
//        add(sdOptionsMenu, BorderLayout.NORTH);
//        buildGenericOptionsMenu();
//        add(genericOptionsMenu, BorderLayout.SOUTH);
//        dataFrameDisplay();
//        add(dataFrameDisplay, BorderLayout.CENTER);
//        pack();
    }
    
    /**
     * Creates the tool bar.
     * @return
     */
    private JMenuBar createToolBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        
        return menuBar;
    }
    
    private JMenu createFileMenu() {
        JMenu file = new JMenu("File");
        JMenuItem loadDf = new JMenuItem("Open file");
        loadDf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("CSV & TXT files", "csv", "txt"));
                int returnVal = fileChooser.showOpenDialog(loadDf);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    loadDataframe(fileChooser.getSelectedFile());
                }
            }
        });
        JMenuItem saveDf = new JMenuItem("Save DataFrame");
        file.add(loadDf);
        file.addSeparator();
        file.add(saveDf);
        return file;
    }
    
    private void loadDataframe(File file) {
        this.dataFrame = DataFrame_Read.loadcsv_new(file.getAbsolutePath());
        System.out.println("Loading data frame GUI");
    }
    
    private JMenu createViewsMenu() {
        JMenu view = new JMenu("Views");
        JMenuItem plot = new JMenuItem("Plots");
        
    }
    
    private JMenu createAlgorithmsMenu() {
        
    }
    
    private JButton loadFileButton() {
        JButton fileButton = new JButton("Load File");
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("CSV & TXT files", "csv", "txt"));
                int returnVal = fileChooser.showOpenDialog(fileButton);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    loadDataframe(fileChooser.getSelectedFile());
                }
            }
        });
        return fileButton;
    }
    
    
    
    /***********************************
     * OLD CODE !!!! 
     ***********************************/
    
    /**
     * Builds the options/inputs menu.
     */
    private void buildGenericOptionsMenu() {
        for (int i = 0; i < columnsToUse.size(); i++) {
            JPanel currentPanel = new JPanel(new BorderLayout());
            JLabel currentLabel = new JLabel(columnsToUse.get(i));
            Set<Object> currentOptions = dataFrame.getColumn(i).getUniqueValues(); //OPTIMIZE 
            if (currentOptions.size() >= 25) {  //TEXT BOX
                JTextField textInput = new JTextField(10);
                currentPanel.add(currentLabel, BorderLayout.NORTH);
                currentPanel.add(textInput, BorderLayout.SOUTH);
                textInput.addActionListener(new InputActionListener(columnsToUse.get(i), true));
                variableInputs.put(columnsToUse.get(i), null);
            } else {                            //DROP DOWN MENU
                JComboBox<String> dropDownInput = createDropDownMenus(currentOptions);
                currentPanel.add(currentLabel, BorderLayout.NORTH);
                currentPanel.add(dropDownInput, BorderLayout.SOUTH);
                variableInputs.put(columnsToUse.get(i), (String) dropDownInput.getSelectedItem());
                dropDownInput.addActionListener(new InputActionListener(columnsToUse.get(i), false));
            }
            genericOptionsMenu.add(currentPanel);
            genericOptionsMenu.addSeparator();
        }
        JButton runButton = new JButton("Run Algorithm");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runAlgorithm();
            }   
        });
        genericOptionsMenu.add(runButton);
    }
    
    /**
     * Converts a set to a dropdown menu consiting of the items within the set.
     * @param currentOptions the options to convert to a dropdown menu.
     * @return a dropdown menu consisting of the items in the passed set. 
     */
    private JComboBox<String> createDropDownMenus(Set<Object> currentOptions) {
        JComboBox<String> options = new JComboBox<String>();
        for (Object o : currentOptions) {
            if (o instanceof String)
                options.addItem((String) o);
            else
                options.addItem("" + o);
        }
        return options;
    }
    
    /**
     * Runs the algorithm.
     */
    private void runAlgorithm() {
        if (variableInputs.containsValue(null) || sdInputs[0] == null || sdInputs[1] == null) {
            JOptionPane.showMessageDialog(this, "One or more inputs have not been properly initialized.", 
                    "Invalid Input(s)",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println(variableInputs); //temporary to check for proper storage of inputs.
        }
    }
    
    /**
     * Builds the proportion of defaults and succsesses options menu.
     */
    private void buildOptionsMenu() {
        JLabel defaults = new JLabel("Proportion of Defaults", JLabel.TRAILING);
        JPanel defaultPanel = new JPanel(new BorderLayout());
        JTextField dTextBox = new JTextField(10);
        dTextBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sdInputs[0] = dTextBox.getText();
            }
            
        });
        defaults.setLabelFor(dTextBox);
        defaultPanel.add(dTextBox, BorderLayout.EAST);
        defaultPanel.add(defaults, BorderLayout.WEST);
        JLabel succsesses = new JLabel("Proportion of Succsesses", JLabel.TRAILING);
        JPanel succsessesPanel = new JPanel(new BorderLayout());
        JTextField sTextBox = new JTextField(10);
        sTextBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sdInputs[1] = sTextBox.getText();
            }
            
        });
        succsesses.setLabelFor(sTextBox);
        succsessesPanel.add(sTextBox, BorderLayout.EAST);
        succsessesPanel.add(succsesses, BorderLayout.WEST);
        sdOptionsMenu.add(defaultPanel, BorderLayout.NORTH);
        sdOptionsMenu.add(succsessesPanel, BorderLayout.SOUTH);
    }
    
//    /**
//     * Displays the entire dataframe
//     */
//    public void dataFrameDisplay() {
//        JPanel panel = new JPanel();
//        panel.setLayout(new GridLayout(dataFrame.getNumRows() + 1, dataFrame.getNumColumns()));
//        //Initialize the names of the columns
//        for (int i = 0; i < dataFrame.getNumRows(); i++) { 
//            panel.add(new JLabel(dataFrame.getColumn(i).getName()));
//        }
//        //Add all of the data from the data frame to the display.
//        for (int rowNum = 0; rowNum < dataFrame.getNumRows(); rowNum++) {
//            for (int colNum = 0; colNum < dataFrame.getNumColumns(); colNum++) {
//                JTextField textBox = new JTextField("" + dataFrame.getRow_byIndex(rowNum).getParticle(colNum).getValue());
//                textBox.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        e.getSource();
//                        dataFrame.replaceParticle(colNum, rowNum, Particle.resolveType(textBox.getText()));
//                        System.out.println("Editing DataFrame");
//                        dataFrame.printDataFrame();
//                    }
//                });
//                panel.add(textBox);
//            }
//        }
//        dataFrameDisplay = new JScrollPane(panel);
//    }
   
    /**
     * Creates an action listener for a combo box or text box.
     * @author Cade Reynoldson
     * @version 1.0
     */
    private class InputActionListener extends AbstractAction {

        /** STOP GIVING ME ERRORS FOR NOT HAVING THIS *ANGRY REACT* */
        private static final long serialVersionUID = 8379111664161837548L;
        
        /** Name of the input box */
        private String inputName;
        
        /** Indicates if the action listener is for a textbox. */
        private boolean isTextBox;
        
        /**
         * Creates an action listener for a combo box.
         * @param theName the name of the box.
         * @param textOrCombo true for text box, false for combo box.
         */
        public InputActionListener(String theName, boolean textOrCombo) {
            inputName = theName;
            isTextBox = textOrCombo;
            
        }
        
        /** 
         * Replaces the current value in the variable input map with the newly selected one. 
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isTextBox) {
                JTextField tempTB = (JTextField) e.getSource();
                variableInputs.replace(inputName, tempTB.getText());
            } else {
                @SuppressWarnings("unchecked")
                JComboBox<String> tempCB = (JComboBox<String>) e.getSource();
                variableInputs.replace(inputName, (String) tempCB.getSelectedItem());
            }
        }
        
    }

}
