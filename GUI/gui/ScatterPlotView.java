package gui;

import java.awt.BorderLayout;
import java.awt.SplashScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dataframe.Column;
import dataframe.DataFrame;
import regressionFunctions.LinearRegression;
import regressionFunctions.LogRegression;
import regressionFunctions.PolyRegression;
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
	
	/** The name of the current plot. */
	private String plotName;
	
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
	
	/**
	 * Starts a view with a polynomial regression.
	 * @param regression the regression to start the 
	 */
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
	    menuBar.add(createRegressionMenu());
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
	
	private JMenu createRegressionMenu() {
	    JMenu r = new JMenu("Regression");
	    JMenuItem generatePR = new JMenuItem("Generate Polynomial Regression");
	    generatePR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regressionInput(RegressionType.POLYNOMIAL);
            }
	    });
	    JMenuItem generateLin = new JMenuItem("Generate Linear Regression");
	    generateLin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regressionInput(RegressionType.LINEAR);
            }
	        
	    });
	    
	    JMenuItem generateLog = new JMenuItem("Generate Logarithmic Regression");
	    generateLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regressionInput(RegressionType.LOGARITHMIC);
            }
	    });
	    r.add(generateLin);
	    r.addSeparator();
	    r.add(generatePR);
	    r.addSeparator();
	    r.add(generateLog);
	    return r;
	}
	
	private void regressionInput(RegressionType type) {
	    switch (type) {
	        case POLYNOMIAL:
	            String degree = JOptionPane.showInputDialog(this, "Enter degree of polynomial: ", "Generate Polynomial Regression ", JOptionPane.PLAIN_MESSAGE);
	            try {
	                int deg = Integer.parseInt(degree);
	                if (deg <= 1) {
	                    JOptionPane.showMessageDialog(this, "Degree must be larger than 1.", "Input error", JOptionPane.ERROR_MESSAGE);
	                    break;
	                }
	                //showLoadingScreen("Preparing polynomial regression for " + col_x.getName() + " vs. " + col_y.getName()); //NEED THREADS FOR THIS
	                PolyRegression polyR = new PolyRegression(col_x, col_y, deg);
	                refreshPanel(polyR);
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(this, "Error when generating regression.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            break;
	        case LOGARITHMIC:
	                LogRegression logR = new LogRegression(col_x, col_y);
	                refreshPanel(logR);
	            break;
	        case LINEAR:
	            try {
	                LinearRegression linR = new LinearRegression(col_x, col_y);
	                refreshPanel(linR);
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(this, "Error when generating regression.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            break;
	    }
	}
	
	/**
	 * Shows a loading screen for a given message. 
	 * @param message the message to display with the loading screen. 
	 */
	private void showLoadingScreen(String message) {
	    JPanel panel = new JPanel();
	    ImageIcon loading = new ImageIcon("GUI_Icons/ajax-loader.gif");
	    JButton cancel = new JButton("Cancel");
	    cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPanel();
            }
	    });
	    panel.setLayout(new BorderLayout());
	    panel.add(new JLabel(message), BorderLayout.NORTH);
	    panel.add(new JLabel(loading), BorderLayout.CENTER);
	    panel.add(cancel, BorderLayout.SOUTH);
	    refreshPanel(panel);
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
	
	private void refreshPanel(JPanel panel) {
	    this.remove(scatter.panel);
	    this.add(panel, BorderLayout.CENTER);
	    this.revalidate();
	    this.repaint();
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
	
	/**
	 * Refreshes panel, used to update scatter plot with a regression. 
	 * @param r the regresssion to plot. 
	 */
	private void refreshPanel(Regression r) {
        Plot newScatter = new Plot(col_x, col_y, r);
        this.remove(scatter.panel);
        scatter = newScatter;
        this.add(scatter.panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
	}
	
	private enum RegressionType {
	    POLYNOMIAL, LINEAR, LOGARITHMIC;
	}
	
}
