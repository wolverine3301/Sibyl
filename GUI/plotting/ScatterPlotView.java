package plotting;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartPanel;

import dataframe.Column;
import dataframe.DataFrame;
import regressionFunctions.ConfidenceIntervals;
import regressionFunctions.LinearRegression;
import regressionFunctions.LogRegression;
import regressionFunctions.PolyRegression;
import regressionFunctions.Regression;

/**
 * Creates a JPanel which contains a scatterplot. 
 * @author Cade Reynoldson & Logan Collier
 * @version 1.0
 */
public class ScatterPlotView extends JPanel implements PropertyChangeListener {
    
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
	
	/** Contains the infromation from plotted regressions, clusters, etc. */
	private JTabbedPane plotInfo;
	
	/** The number of regression samples to take for the regression lines. (more samples = smoother line) */
	private JTextField numRegressionSamples;
	
	private JPanel plotPanel; 
	
	/** The JPanel which will contain regression info. */
	private RegressionPanel regressionPanel;
	
    /** Notifies the plot that a change has been made. */
    private PropertyChangeSupport notifyPlot = new PropertyChangeSupport(this);
	
	/**
	 * Constructs a new scatter plot view
	 * @param df the data frame to create a scatterplot view for. 
	 */
	public ScatterPlotView(DataFrame df) {
	    super();
	    this.df = df;
	    regressionPanel = new RegressionPanel();
	    plotInfo = new JTabbedPane();
	    col_x = df.getColumn(df.numericIndexes.get(0));
	    col_y = df.getColumn(df.numericIndexes.get(0));
	    start();
	}
	
	/**
	 * Handles initializing everything in the panel. 
	 */
	public void start() {
	    initPlotPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(plotPanel);
		plotInfo.add(regressionPanel, "Regressions");
		this.add(plotInfo);
	}
	
	/**
	 * Initializes the plot panel.
	 */
	private void initPlotPanel() {
	    scatter = new Plot(col_x, col_y);
	    plotPanel = new JPanel();
	    plotPanel.setLayout(new BorderLayout());
	    plotPanel.add(scatter.getPlot(), BorderLayout.CENTER);
	    JPanel optionPanel = new JPanel();
	    optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
	    optionPanel.add(axisSelectPanel());
	    optionPanel.add(regressionButtons());
	    plotPanel.add(optionPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Initializes the regression buttons. 
	 * @return
	 */
	private JPanel regressionButtons() {
	    JPanel panel = new JPanel();
        JLabel baseRegressions = new JLabel(" Generate Regressions: ");
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JButton linear = new JButton("Linear Regression");
        linear.setSelected(false);
        linear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regressionInput(RegressionType.LINEAR);
            }
        });
        JButton log = new JButton("Logarithmic Regression");
        log.setSelected(false);
        log.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regressionInput(RegressionType.LOGARITHMIC);
            }
        });
        JButton poly = new JButton("Polynomial Regression");
        poly.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regressionInput(RegressionType.POLYNOMIAL);
            }
        });
        numRegressionSamples = new JTextField("20");
        poly.setSelected(false);
        panel.add(baseRegressions);
        panel.add(linear);
        panel.add(log);
        panel.add(poly);
        JLabel regSamples = new JLabel(" Regression Samples ");
        regSamples.setToolTipText("Controls how many samples from the regression are taken.\nHigher sample number will produce a smoother line.");
        panel.add(regSamples);
        panel.add(numRegressionSamples);
        return panel;
	}
	
	/**
	 * Generates a new regression based off of the regression tuple passed to it. 
	 * @param type
	 */
	private void regressionInput(RegressionType type) {
	    Regression r = null;
	    switch (type) {
	        case POLYNOMIAL:
	            try {
	                String degree = JOptionPane.showInputDialog(this, "Enter degree of polynomial: ", "Generate Polynomial Regression ", JOptionPane.PLAIN_MESSAGE);
	                int deg = Integer.parseInt(degree);
	                if (deg <= 1) {
	                    JOptionPane.showMessageDialog(this, "Degree must an be an integer larger than 1.", "Input error", JOptionPane.ERROR_MESSAGE);
	                    break;
	                }
	                //showLoadingScreen("Preparing polynomial regression for " + col_x.getName() + " vs. " + col_y.getName()); //NEED THREADS FOR THIS
	                r = new PolyRegression(col_x, col_y, deg);
	                regressionPanel.addRegression(r);
	                break;
	            } catch (Exception e) {
	                
	            }
	            String degree = JOptionPane.showInputDialog(this, "Enter degree of polynomial: ", "Generate Polynomial Regression ", JOptionPane.PLAIN_MESSAGE);
                int deg = Integer.parseInt(degree);
                if (deg <= 1) {
                    JOptionPane.showMessageDialog(this, "Degree must be larger than 1.", "Input error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                //showLoadingScreen("Preparing polynomial regression for " + col_x.getName() + " vs. " + col_y.getName()); //NEED THREADS FOR THIS
                r = new PolyRegression(col_x, col_y, deg);
                regressionPanel.addRegression(r);
	            break;
	        case LOGARITHMIC:
                r = new LogRegression(col_x, col_y);
                regressionPanel.addRegression(r);
	            break;
	        case LINEAR:
	            r = new LinearRegression(col_x, col_y);
                regressionPanel.addRegression(r);
                break;
	    }
	    if (r != null) {
	        scatter.plotRegression(r, getNumSamples());
	    }
	}
	
	private int getNumSamples() {
	    try {
            double distance = Double.parseDouble(numRegressionSamples.getText());
            if (distance < 5)
                JOptionPane.showMessageDialog(this, "Error: Regression sample interval cannot be less than or"
                         + " equal to zero.", "Error", JOptionPane.ERROR_MESSAGE);
            else
                return (int) distance;
	    } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Error with regression point frequency input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 20;
    }
	
//	/**
//	 * Plots a point on the 
//	 * @param x
//	 * @param regFunction
//	 */
//	private void plotPoint(Double x, String regFunction) {
//	    Regression regression = null;
//	    for (Regression r : RegressionPanel.getAllRegressions()) {
//	        if (r.getEquation().equals(regFunction)) {
//	            regression = r;
//	            break;
//	        }
//	    }
//	    if (regression != null) {
//	        this.remove(scatter.getPlot());
//	        scatter.plotPoint(x, regression);
//	        this.add(scatter.getPlot(), BorderLayout.CENTER);
//	        this.revalidate();
//	        this.repaint();
//	    }
//	}
	
    /**
     * Adds a property change listener to the panel.
     * @param theListener the listener to be added.
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        notifyPlot.addPropertyChangeListener(theListener);
    }
	
	/**
	 * Creates a jpanel which contains combo boxes for selection of the x and y axis. 
	 * @return a jpanel which contains combo boxes for selection of the x and y axis.
	 */
	private JPanel axisSelectPanel() {
	    JPanel panel = new JPanel();
	    panel.add(new JLabel("Column Select: "));
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
                createNewPlot();
            } 
        });
        yNames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                col_y = df.getColumn_byName((String) yNames.getSelectedItem());
                createNewPlot();
            }
        });
        panel.add(xNames);
        panel.add(new JLabel("Y-Axis"));
        panel.add(yNames);
        return panel;
	}
	
	/**
	 * Creates a new plot. 
	 */
	private void createNewPlot() {
	    Plot newScatter = new Plot(col_x, col_y);
	    plotPanel.remove(scatter.getPlot());
	    scatter = newScatter;
	    plotPanel.add(scatter.getPlot());
	    scatter.addPropertyChangeListener(this);
	    this.revalidate();
	    this.repaint();
	}
	
	/**
	 * Enum indicating which regression type is being created. 
	 * @author Cade
	 */
	private enum RegressionType {
	    POLYNOMIAL, LINEAR, LOGARITHMIC;
	}

	/**
	 * Refreshes the panel given a property change in the plot view. ASSUMES THE MIDDLE PLOT HAS BEEN REMOVED PRIOR! NOTE: This might not be needed?
	 */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String option = (String) evt.getSource();
        if (option.equals("PLOT")) { //Re plot an existing regression
            
            this.revalidate();
            this.repaint();
        } else if (option.equals("POINT")) { //Plot a point.
            
        } else if (option.equals("DELETE")) { //Delete a something fix this later cade
            
        } 
    }
	
    
//  /**
//  * Shows a loading screen for a given message. 
//  * @param message the message to display with the loading screen. 
//  */
// private void showLoadingScreen(String message) {
//     JPanel panel = new JPanel();
//     ImageIcon loading = new ImageIcon("GUI_Icons/ajax-loader.gif");
//     JButton cancel = new JButton("Cancel");
//     cancel.addActionListener(new ActionListener() {
//           @Override
//           public void actionPerformed(ActionEvent e) {
//               refreshPanel(false);
//           }
//     });
//     panel.setLayout(new BorderLayout());
//     panel.add(new JLabel(message), BorderLayout.NORTH);
//     panel.add(new JLabel(loading), BorderLayout.CENTER);
//     panel.add(cancel, BorderLayout.SOUTH);
//     refreshPanel(panel);
// }
}
