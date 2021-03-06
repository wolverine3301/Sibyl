package stats_and_graph_components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import Controllers.Data_Controller;
import dataframe.Column;
import dataframe.DataFrame;
import logan.sybilGUI.Tertiary_View;
import plotting.Plot;
import plotting.RegressionPanel;
import regressionFunctions.ConfidenceIntervals;
import regressionFunctions.LinearRegression;
import regressionFunctions.LogRegression;
import regressionFunctions.PolyRegression;
import regressionFunctions.Regression;

public class TwoD_Scatter_Panel extends Tertiary_View implements PropertyChangeListener{


	private Data_Controller controller;
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
    private PropertyChangeSupport notifyPlot;
    
	public TwoD_Scatter_Panel(int width, int height, Color main_bg_color, Color main_side_color, int side_panel_W,Data_Controller controller) {
		super(width, height, main_bg_color, main_side_color, side_panel_W);
		this.controller = controller;
		this.df = controller.getDF();
		notifyPlot = new PropertyChangeSupport(this);
	    regressionPanel = new RegressionPanel();
	    regressionPanel.addPropertyChangeListener(this);
	    plotInfo = new JTabbedPane();
	    col_x = df.numeric_columns.get(0);
	    col_y = df.numeric_columns.get(0);
	    
	    //start();
	    initComponents();
	}
	
	/**
	 * Handles initializing everything in the panel. 
	 */
	public void start() {
	    initPlotPanel();
		//this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		//this.add(plotPanel);
		plotInfo.add(regressionPanel, "Regressions");
		this.add(plotInfo);
	}
	
	/**
	 * Initializes the plot panel.
	 */
	private void initPlotPanel() {
	    scatter = new Plot(col_x, col_y);
	    scatter.addPropertyChangeListener(this);
	    plotPanel = new JPanel();
	    plotPanel.setLayout(new BorderLayout());
	    plotPanel.add(scatter, BorderLayout.CENTER);
	    JPanel optionPanel = new JPanel();
	    optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
	    optionPanel.add(horizontalSep());
	    optionPanel.add(axisSelectPanel());
	    optionPanel.add(horizontalSep());
	    optionPanel.add(regressionButtons());
	    plotPanel.add(optionPanel, BorderLayout.SOUTH);
	    plotInfo.add(regressionPanel, "Regressions");
	    center_panel.add(plotPanel, BorderLayout.CENTER);
	    center_panel.add(plotInfo,BorderLayout.SOUTH);

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
        numRegressionSamples.setMaximumSize(new Dimension(50, numRegressionSamples.getMaximumSize().height));
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
        for (Column i : df.numeric_columns) {
            xNames.addItem(i.getName());
            yNames.addItem(i.getName());
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
	    plotPanel.remove(scatter);
	    scatter = new Plot(col_x, col_y);
	    scatter.addPropertyChangeListener(this);
	    plotPanel.add(scatter, BorderLayout.CENTER);
	    regressionPanel.clear();
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
	 * Refreshes the panel given a property change in the plot view. ASSUMES THE MIDDLE PLOT HAS BEEN REMOVED PRIOR!
	 */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String option = evt.getPropertyName();
        if (option.equals("GEN")) { //Used for general refreshing of the panel.
            this.revalidate();
            this.repaint();
        } else if (option.equals("POINT")) { //Plot a point.
            scatter.plotPoint((Double) evt.getOldValue(), (Double) evt.getNewValue());
        } else if (option.equals("REMOVE")) { //Remove a regression. 
            Object toRemove = evt.getOldValue();
            if (toRemove instanceof Regression) {
                System.out.println("DELETING ");
                scatter.removeRegression((Regression) toRemove, getNumSamples());
            } else if (toRemove instanceof Point) {
                
            }
        } else if (option.equals("PLOT")) { //Refreshes the plot. 
            System.out.println("REFRESHING PLOT");
            this.revalidate();
            this.repaint();
        } else if (option.equals("CONF")) { //Plot a confidence interval
            scatter.plotConfidenceInterval((ConfidenceIntervals) evt.getOldValue(), getNumSamples());
            
        } else if (option.equals("REPLOT")) { //Re-plot a regression or confidence interval.
            Object toPlot = evt.getOldValue();
            if (toPlot instanceof Regression) {
                scatter.plotRegression((Regression) toPlot, getNumSamples());
            } else if (toPlot instanceof ConfidenceIntervals) {
                scatter.plotConfidenceInterval((ConfidenceIntervals) evt.getOldValue(), getNumSamples());
            }
        }
    
    }
	
    public static JSeparator horizontalSep() {
        JSeparator sep = new JSeparator(JSeparator.HORIZONTAL);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }
	@Override
	protected void initComponents() {
		initPlotPanel();
		add(center_panel, BorderLayout.CENTER);
		
	}
}
