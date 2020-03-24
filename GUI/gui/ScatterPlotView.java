package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import dataframe.Column;
import dataframe.DataFrame;
import regressionFunctions.Regression;

/**
 * Creates a JPanel which contains a scatterplot. 
 * @author Cade Reynoldson & Logan Collier
 * @version 1.0
 */
public class ScatterPlotView extends JPanel{
    
    /** The bullshit ID. */
    private static final long serialVersionUID = 9137857479069749287L;
    
    /** The data frame to base the scatter plot upon. */
    private DataFrame df;
    
    /** The current x column. */
	private Column col_x;
	
	/** The current y column. */
	private Column col_y;
	
	/** The scatter plot in the current view. */
	private Plot scatter;
	
	/**
	 * Constructs a new scatter plot view
	 * @param df the data frame to create a scatterplot view for. 
	 */
	public ScatterPlotView(DataFrame df) {
	    super();
	    this.df = df;
	    col_x = df.getColumn(df.numericIndexes.get(0));
	    col_y = df.getColumn(df.numericIndexes.get(0));
	    start();
	}
	
	public ScatterPlotView(Column x, Column y, Regression regression) {
	    super();
	    col_x = x;
	    col_y = y;
	    start(regression);
	}
	
	private void start(Regression regression) {
        scatter = new Plot(col_x, col_y, regression);
        this.setLayout(new BorderLayout());
        this.add(optionPanel(), BorderLayout.NORTH);
        this.add(scatter.panel, BorderLayout.CENTER);
	}
	
	/**
	 * Handles initializing everything in the panel. 
	 */
	public void start() {
		scatter = new Plot(df.getColumn(df.numericIndexes.get(0)), df.getColumn(df.numericIndexes.get(0)));
		this.setLayout(new BorderLayout());
		this.add(optionPanel(), BorderLayout.NORTH);
		this.add(scatter.panel, BorderLayout.CENTER);
		this.add(axisSelectPanel(), BorderLayout.SOUTH);
	}
	
	private JMenuBar optionPanel() {
	    JMenuBar menuBar = new JMenuBar();
	    JMenu file = createFileMenu();
	    menuBar.add(file);
	    return menuBar;
	}
	
	/**
	 * Creates a file menu for the option panel.  
	 * @return the
	 */
	private JMenu createFileMenu() {
	    JMenu file = new JMenu("File");
	    JMenuItem save = new JMenuItem("Save Plot");
	    save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
	    });
	    file.add(save);
	    file.addSeparator();
	    return file;
	}
	
	/**
	 * Creates a jpanel which contains combo boxes for selection of the x and y axis. 
	 * @return a jpanel which contains combo boxes for selection of the x and y axis.
	 */
	private JPanel axisSelectPanel() {
	    JPanel panel = new JPanel();
	    panel.add(new JLabel("X-Axis"));
        JComboBox<String> xNames = new JComboBox<String>();
        JComboBox<String> yNames = new JComboBox<String>();
        for (Integer i : df.numericIndexes) {
            xNames.addItem(df.getColumnNames().get(i));
            yNames.addItem(df.getColumnNames().get(i));
        }
        xNames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                col_x = df.getColumn_byName((String) xNames.getSelectedItem());
                refreshPanel();
            } 
        });
        yNames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                col_y = df.getColumn_byName((String) yNames.getSelectedItem());
                refreshPanel();
            }
        });
        panel.add(xNames);
        panel.add(new JLabel("Y-Axis"));
        panel.add(yNames);
        return panel;
	}
	
	/**
	 * Refreshes the panel. Used to update scatter plot.
	 */
	private void refreshPanel() {
	    Plot newScatter = new Plot(col_x, col_y);
	    this.remove(scatter.panel);
	    scatter = newScatter;
	    this.add(scatter.panel, BorderLayout.CENTER);
	    this.revalidate();
	    this.repaint();
	}
}
