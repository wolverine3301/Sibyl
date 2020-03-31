package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	
	/** The current regressions plotted in the view. */
	private HashSet<Regression> plottedRegressions; 
	
	/** The number of regression samples to take for the regression lines. (more samples = smoother line) */
	private JTextField numRegressionSamples;
	
	private JPanel regressionInfo;
	
	/**
	 * Constructs a new scatter plot view
	 * @param df the data frame to create a scatterplot view for. 
	 */
	public ScatterPlotView(DataFrame df) {
	    super();
	    this.df = df;
	    plottedRegressions = new HashSet<Regression>();
	    col_x = df.getColumn(df.numericIndexes.get(0));
	    col_y = df.getColumn(df.numericIndexes.get(0));
	    start(false);
	}
	
	/**
	 * Creates a scatter plot view given a dataframe and already created regression. 
	 * @param df the dataframe to plot.
	 * @param regression the regression to plot. 
	 */
	public ScatterPlotView(DataFrame df, Regression regression) {
        super();
        this.df = df;
        col_x = df.getColumn(df.numericIndexes.get(0));
        col_y = df.getColumn(df.numericIndexes.get(0));
	    plottedRegressions = new HashSet<Regression>();
	    plottedRegressions.add(regression);
	    start(true);
	}
	
	/**
	 * Handles initializing everything in the panel. 
	 */
	public void start(boolean plotRegression) {
	    if (plotRegression) 
	        scatter = new Plot(col_x, col_y, plottedRegressions, 20);
	    else 
	        scatter = new Plot(col_x, col_y);
	    regressionPanel();
		this.setLayout(new BorderLayout());
		this.add(optionPanel(), BorderLayout.NORTH);
		this.add(scatter.getPlot(), BorderLayout.CENTER);
		this.add(axisSelectPanel(), BorderLayout.SOUTH);
		this.add(regressionInfo, BorderLayout.EAST);
	}
	
	/**
	 * The main option menu/toolbar.
	 * @return 
	 */
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
	
	private void regressionPanel() {
	    JPanel panel = new JPanel();
	    JLabel baseRegressions = new JLabel("Base Regressions");
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    JCheckBox linear = new JCheckBox("Linear Regression");
	    linear.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    removeLinearOrLogRegression(RegressionType.LINEAR);
                } else {
                    regressionInput(RegressionType.LINEAR);
                }
            }
	    });
	    JCheckBox log = new JCheckBox("Logarithmic Regression");
	    log.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    removeLinearOrLogRegression(RegressionType.LOGARITHMIC);
                } else {
                    regressionInput(RegressionType.LOGARITHMIC);
                }
            }
        });
	    JLabel polyLabel = new JLabel("Polynomial Regressions");
	    JCheckBox poly = new JCheckBox("Polynomial Regression");
	    poly.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {

                } else {
                    regressionInput(RegressionType.POLYNOMIAL);
                }
            }
        });
	    panel.add(baseRegressions);
	    panel.add(linear);
	    panel.add(log);
	    panel.add(polyLabel);
	    panel.add(poly);
	    regressionInfo = panel;
	}
	
	
	private void removeLinearOrLogRegression(RegressionType type) {
	    Regression toRemove = null;
	    for (Regression r : plottedRegressions) {
	        if (type == RegressionType.LINEAR && r instanceof LinearRegression) {
	            toRemove = r; 
	            break;
	        } else if (type == RegressionType.LOGARITHMIC && r instanceof LogRegression) {
	            toRemove = r;
	            break;
	        }
	    }
	    if (toRemove != null) {
	        plottedRegressions.remove(toRemove);
	        refreshPanel(true);
	    }
	}
	
//	private JScrollPane regressionInfo() {
//	    JPanel panel;
//	    for (Regression r : plottedRegressions) { //For each regression, plot it's info. 
//	        
//	    }
//	}
	
	/**
	 * Creates the regression options menu. 
	 * @return the regression options menu.
	 */
	private JMenu createRegressionMenu() {
	    JMenu r = new JMenu("Regression");
	    //Setup the distance input. 
	    JMenu distance = new JMenu("Regresion Sample Count");
	    distance.setToolTipText("Changes the distance bewteen each point\n plotted in a regression line.");
	    numRegressionSamples = new JTextField("20", 7);
	    numRegressionSamples.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshPanel(true);
            }
	    });
	    distance.add(numRegressionSamples);
	    //Polynomial regression input. 
	    JMenuItem generatePR = new JMenuItem("Generate Polynomial Regression");
	    generatePR.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regressionInput(RegressionType.POLYNOMIAL);
            }
	    });
	    //Linear regression input
	    JMenuItem generateLin = new JMenuItem("Generate Linear Regression");
	    generateLin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regressionInput(RegressionType.LINEAR);
            }
	        
	    });
	    //Log regression button
	    JMenuItem generateLog = new JMenuItem("Generate Logarithmic Regression");
	    generateLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regressionInput(RegressionType.LOGARITHMIC);
            }
	    });
	    
	    //Distance between points
	    r.add(generateLin);
	    r.addSeparator();
	    r.add(generatePR);
	    r.addSeparator();
	    r.add(generateLog);
	    r.addSeparator();
	    r.add(distance);
	    return r;
	}
	
	/**
	 * Generates a new regression. 
	 * @param type
	 */
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
	                plottedRegressions.add(new PolyRegression(col_x, col_y, deg));
	                refreshPanel(true);
	            } catch (Exception e) {
	                JOptionPane.showMessageDialog(this, "Error when generating regression.", "Error", JOptionPane.ERROR_MESSAGE);
	            }
	            break;
	        case LOGARITHMIC:
	                plottedRegressions.add(new LogRegression(col_x, col_y));
	                refreshPanel(true);
	            break;
	        case LINEAR:
	            try {
	                plottedRegressions.add(new LinearRegression(col_x, col_y));
	                refreshPanel(true);
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
                refreshPanel(false);
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
                refreshPanel(false);
            } 
        });
        yNames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                col_y = df.getColumn_byName((String) yNames.getSelectedItem());
                refreshPanel(false);
            }
        });
        panel.add(xNames);
        panel.add(new JLabel("Y-Axis"));
        panel.add(yNames);
        return panel;
	}
	
	private void refreshPanel(JPanel panel) {
	    this.remove(scatter.getPlot());
	    this.add(panel, BorderLayout.CENTER);
	    this.revalidate();
	    this.repaint();
	}
	
	/**
	 * Refreshes the panel. Used to update scatter plot.
	 */
	private void refreshPanel(boolean plotRegressions) {
	    Plot newScatter;
	    if (plotRegressions && plottedRegressions.size() != 0) {
	        try {
	            double distance = Double.parseDouble(numRegressionSamples.getText());
	            if (distance <= 0.0)
	                 JOptionPane.showMessageDialog(this, "Error: Regression sample interval cannot be less than or"
	                         + " equal to zero.", "Error", JOptionPane.ERROR_MESSAGE);
	            newScatter = new Plot(col_x, col_y, plottedRegressions, (int) distance);
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Error with regression point frequency input.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        } catch (Exception e) {
	            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    } else {
	        newScatter = new Plot(col_x, col_y);
	        plottedRegressions.clear(); // Clear all currently plotted regressions. 
	        deselectCheckboxes();
	    }
	    this.remove(scatter.getPlot());
	    scatter = newScatter;
	    this.add(scatter.getPlot(), BorderLayout.CENTER);
	    this.revalidate();
	    this.repaint();
	}
	
	private void deselectCheckboxes() {
	    Component[] components = regressionInfo.getComponents();
	    for (int i = 0; i < components.length; i++) {
	        if (components[i] instanceof JCheckBox) {
	            ((JCheckBox) components[i]).setSelected(false);
	        }
	    }
	}
	
	/**
	 * Refreshes panel, used to update scatter plot with a SINGLE regression. 
	 * @param r the regresssion to plot. 
	 */
//	private void refreshPanel(Regression r) {
//        Plot newScatter = new Plot(col_x, col_y, r);
//        this.remove(scatter.panel);
//        scatter = newScatter;
//        this.add(scatter.panel, BorderLayout.CENTER);
//        this.revalidate();
//        this.repaint();
//	}
	
	/**
	 * Enum indicating which regression type is being created. 
	 * @author Cade
	 */
	private enum RegressionType {
	    POLYNOMIAL, LINEAR, LOGARITHMIC;
	}
	
}
