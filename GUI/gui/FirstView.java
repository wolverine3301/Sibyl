package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class FirstView extends JFrame {

    /** Serial stuff and thing */
    private static final long serialVersionUID = 1338600238686282192L;
    
    public FirstView() {
        
    }
    
    public void start() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        add(modelButton(), BorderLayout.EAST);
        add(loadFileButton(), BorderLayout.WEST);
        pack();
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
    
    /**
     * Creates a button which will be used to load in a model.
     * @return a button which on being pressed will open a file chooser.
     */
    private JButton modelButton() {
        JButton modelButton = new JButton("Load Model");
        modelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                JOptionPane.showMessageDialog(modelButton, "Or never. "
                        + "How did you get this program? WHO IS THIS?? BE GONE!!\n"
                        + "Or just load in a dataframe for the timebeing", "This isn't implemented yet. Try again later", JOptionPane.ERROR_MESSAGE);
            }            
        });
        return modelButton;
    }
    
    private void loadDataframe(File file) {
        DataFrame df = DataFrame_Read.loadcsv(file.getAbsolutePath());
        System.out.println("Loading data frame GUI");
        new SibylGUI(df).start();
        this.
    }
        
    private void loadModel(File file) {
        
    }
    
    public String toString() {
        return "FirstView";
    }
}
