package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.TreeSet;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
    
    /** Holds the inputs for the successes and defaults. Index 1 = successes, index 0 = defaults */
    private String[] sdInputs;
    
    /** Tool bar with options for each column. */
    private JToolBar genericOptionsMenu;
    
    /** Holds the input values for a given column */
    private HashMap<String, String> variableInputs;
    
    
    /**
     * Constructs a new instance of the GUI.
     * @param theDataFrame reference to the main data frame.
     */
    public SibylGUI(DataFrame<String> theDataFrame) {
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
            TreeSet<String> currentOptions = (TreeSet<String>) dataFrame.getColumn_byIndex(i).uniqueValuesTree(); //OPTIMIZE 
            if (currentOptions.size() >= 25) {  //TEXT BOX
                JTextField textInput = new JTextField(10);
                currentPanel.add(currentLabel, BorderLayout.NORTH);
                currentPanel.add(textInput, BorderLayout.SOUTH);
                textInput.addActionListener(new InputActionListener(columnsToUse[i], true));
                variableInputs.put(columnsToUse[i], null);
            } else {                            //DROP DOWN MENU
                JComboBox<String> dropDownInput = new JComboBox<String>(currentOptions.toArray(new String[0]));
                currentPanel.add(currentLabel, BorderLayout.NORTH);
                currentPanel.add(dropDownInput, BorderLayout.SOUTH);
                variableInputs.put(columnsToUse[i], (String) dropDownInput.getSelectedItem());
                dropDownInput.addActionListener(new InputActionListener(columnsToUse[i], false));
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
