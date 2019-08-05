package gui;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import saga.DataFrame;

public class SibylGUI extends JFrame{
    /** THE KEY TO LIFE */
    private static final long serialVersionUID = -3696717041450563682L;

    /** Name of the GUI */
    private static final String GUI_NAME = "Sibyl";
    
    /** Reference to the data frame */
    private DataFrame<String> dataFrame;
    
    /** List of columns to create as inputs. */
    private String[] columnsToUse;
    
    /** The JPanel which holds the options of successes and defaults option. */
    private JPanel sdOptionsMenu;
    
    /** Tool bar with options for each column. */
    private JToolBar genericOptionsMenu;
    
    /** Holds the input values for a given column */
    private HashMap<String, String> variableInputs;
    
    /**
     * Constructs a new instance of the GUI.
     * @param theCSV the CSV file to pass the GUI.
     */
    public SibylGUI(DataFrame<String> theDataFrame) {
        super(GUI_NAME);
        dataFrame = theDataFrame;
        columnsToUse = dataFrame.getColumnNames();
        sdOptionsMenu = new JPanel(new BorderLayout());
        genericOptionsMenu = new JToolBar("Options");
        variableInputs = new HashMap<String, String>();
    }
    
    /**
     * Calls all methods required to build the GUI.
     */
    public void start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        // Build the menu buttons.
        buildOptionsMenu();
        add(sdOptionsMenu, BorderLayout.NORTH);
        buildGenericOptionsMenu();
        add(genericOptionsMenu, BorderLayout.SOUTH);
        pack();
    }
    
    /**
     * Builds the options/inputs menu.
     */
    private void buildGenericOptionsMenu() {
        for (int i = 0; i < columnsToUse.length; i++) {
            JPanel currentPanel = new JPanel(new BorderLayout());
            JLabel currentLabel = new JLabel(columnsToUse[i]);
            TreeSet<String> currentOptions = (TreeSet<String>) dataFrame.getColumn(i).uniqueValuesTree(); //OPTIMIZE 
            if (currentOptions.size() >= 25) {  //TEXT BOX
                JTextField textInput = new JTextField(10);
                currentPanel.add(currentLabel, BorderLayout.NORTH);
                currentPanel.add(textInput, BorderLayout.SOUTH);
            } else {                            //DROP DOWN MENU
                JComboBox<String> dropDownInput = new JComboBox<String>(currentOptions.toArray(new String[0]));
                currentPanel.add(currentLabel, BorderLayout.NORTH);
                currentPanel.add(dropDownInput, BorderLayout.SOUTH);
            }
            genericOptionsMenu.add(currentPanel);
            genericOptionsMenu.addSeparator();
            // TO DO : STORE INPUTS
        }
    }
    
    /**
     * Builds the proportion of defaults and succsesses options menu.
     */
    private void buildOptionsMenu() {
        JLabel defaults = new JLabel("Proportion of Defaults", JLabel.TRAILING);
        JPanel defaultPanel = new JPanel(new BorderLayout());
        JTextField dTextBox = new JTextField(10);
        defaults.setLabelFor(dTextBox);
        defaultPanel.add(dTextBox, BorderLayout.EAST);
        defaultPanel.add(defaults, BorderLayout.WEST);
        JLabel succsesses = new JLabel("Proportion of Succsesses", JLabel.TRAILING);
        JPanel succsessesPanel = new JPanel(new BorderLayout());
        JTextField sTextBox = new JTextField(10);
        succsesses.setLabelFor(sTextBox);
        succsessesPanel.add(sTextBox, BorderLayout.EAST);
        succsessesPanel.add(succsesses, BorderLayout.WEST);
        sdOptionsMenu.add(defaultPanel, BorderLayout.NORTH);
        sdOptionsMenu.add(succsessesPanel, BorderLayout.SOUTH);
    }

}
