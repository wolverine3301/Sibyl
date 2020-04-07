package plotting;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import particles.DoubleParticle;
import regressionFunctions.ConfidenceIntervals;
import regressionFunctions.LinearRegression;
import regressionFunctions.LogRegression;
import regressionFunctions.PolyRegression;
import regressionFunctions.Regression;

public class RegressionPanel extends JPanel{

    /** SERIALID ! */
    private static final long serialVersionUID = 4220301081698531384L;

    /** The current regressions plotted in the view. */
    private HashMap<String, Regression> allRegressions; 
    
    /** The regressions to plot. */
    private HashMap<String, Regression> plottedRegressions;
    
    
    private HashSet<JPanel> regressionSubPanels;
    
    /** Notifies the plot that a change has been made. */
    private PropertyChangeSupport notifyPlot = new PropertyChangeSupport(this);
    
    /**
     * 
     */
    public RegressionPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        allRegressions = new HashMap<String, Regression>();
        plottedRegressions = new HashMap<String, Regression>();
        regressionSubPanels = new HashSet<JPanel>();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setPreferredSize(new Dimension(screenSize.width / 3, screenSize.height / 2));
        start();
    }
    
    public void clear() {
        allRegressions.clear();
        plottedRegressions.clear();
        this.removeAll();
        start();
        notifyPlot.firePropertyChange("GEN", null, null);
    }
    
    /**
     * 
     */
    private void start() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Regressions");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(label);
        this.add(ScatterPlotView.horizontalSep());
    }
    
    
    public void addRegression(Regression r) {
        allRegressions.put(r.getEquation(), r);
        plottedRegressions.put(r.getEquation(), r);
        JPanel regPanel = generateRegressionPanel(r);
        regressionSubPanels.add(regPanel);
        this.add(regPanel);
        this.add(ScatterPlotView.horizontalSep());
        notifyPlot.firePropertyChange("PLOT", r.getEquation(), null);
    }
    
    /**
     * Generates a panel for a specific regression. 
     * @param r the regression.
     * @return
     */
    private JPanel generateRegressionPanel(Regression r) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel info = createRegressionInfo(r);
        JCheckBox box = new JCheckBox(r.getEquation());
        box.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.DESELECTED) {
                    notifyPlot.firePropertyChange("DELETE", box.getText(), null);
                } else {
                    notifyPlot.firePropertyChange("PLOT", box.getText(), null);
                }
            }
        });
        box.setSelected(true);
        panel.add(box);
        //Confidence Inteval stuff
        JPanel conf = new JPanel();
        conf.setLayout(new BoxLayout(conf, BoxLayout.X_AXIS));
        conf.add(new JLabel("Confidence Interval: Conf level = "));
        JTextField confLevel = new JTextField("90", 5);
        confLevel.setMaximumSize(new Dimension(50, confLevel.getMaximumSize().height));
        JButton confButton = new JButton("Plot Interval");
        ActionListener confListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x = getConfLevel(confLevel);
                if (x == Double.MAX_VALUE) {
                    JOptionPane.showInputDialog(confButton, "Invalid input for x.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    plotInterval(x, box.getText());
                }
            } 
        };
        confLevel.addActionListener(confListener);
        conf.add(confLevel);
        confButton.addActionListener(confListener);
        conf.add(confButton);
        
        //Point Plotting Stuff
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.add(new JLabel("Plot point: x = "));
        JTextField xVal = new JTextField(5);
        xVal.setMaximumSize(new Dimension(50, xVal.getMaximumSize().height));
        inputPanel.add(xVal);
        ActionListener pointListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x = getXInput(xVal);
                if (x == Double.MIN_VALUE) {
                    JOptionPane.showInputDialog(xVal, "Invalid input for x.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    plotPoint(x, box.getText(), inputPanel);
                }
            }
        };
        xVal.addActionListener(pointListener);
        JButton plotPoint = new JButton("Plot Point");
        plotPoint.addActionListener(pointListener);
        inputPanel.add(xVal);
        inputPanel.add(plotPoint);
        inputPanel.add(new JLabel(" Points: "));
        
        //Put it all together
        inputPanel.setAlignmentX(LEFT_ALIGNMENT);
        info.setAlignmentX(LEFT_ALIGNMENT);
        conf.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(inputPanel);
        panel.add(conf);
        panel.add(info);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, info.getMinimumSize().height * 6));
        return panel;
    }
    
    /**
     * Parses the xinput from a value. 
     * @param field
     * @return
     */
    private double getXInput(JTextField field) {
        String val = field.getText();
        try {
            double x = Double.parseDouble(val);
            return x;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(this, "Invalid input for x.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return Double.MIN_VALUE;
    }
    
    private double getConfLevel(JTextField field) {
        String val = field.getText();
        try {
            double x = Double.parseDouble(val);
            if (x < 0 || x > 100) {
                JOptionPane.showConfirmDialog(this, "Invalid input for confidence level.", "Error", JOptionPane.ERROR_MESSAGE);
                return Double.MIN_VALUE;
            }
            return x;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(this, "Invalid input for confidence level.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return Double.MIN_VALUE;
    }
    
    private void plotInterval(double confidenceLevel, String regFunction) {
        ConfidenceIntervals interval = new ConfidenceIntervals(allRegressions.get(regFunction));
        interval.setConfidenceLevel((int) confidenceLevel);
        notifyPlot.firePropertyChange("CONF", interval, null);
    }
    
    /**
     * Creates a regression info string for display.
     * @param r the regression whos info is to be displayed.
     * @return a jlabel which contins information about the given regression. 
     */
    private JLabel createRegressionInfo(Regression r) {
        String r2 = "R^2 = " + r.R2;
        String mse = "MSE = " + r.MSE;
        String mae = "MAE = " + r.RMSD;
        return new JLabel(r2 + ", " + mse + ", " + mae);
    }

    
    /**
     * Plots a point on the 
     * @param x
     * @param regFunction
     */
     private void plotPoint(double x, String regFunction, JPanel infoPanel) {
         Regression r = allRegressions.get(regFunction);
         double y = r.predictY(new DoubleParticle(x));
         infoPanel.add(new JLabel(" (" + x + ", " + y + ") "));
         notifyPlot.firePropertyChange("POINT", x, r);
     }
//    private void drawExistingRegression(String regressionFunction) {
//        Regression toAdd = null;
//        printRegressions();
//        for (Regression r : allRegressions.keySet()) {
//            if (r.getEquation().equals(regressionFunction)) {
//                toAdd = r;
//                break;
//            }
//        }
//        if (toAdd != null) {
//            //addRegression(toAdd);
//        }
//    }
//    
//    /**
//     * TODO: RE IMPLEMENT!
//     * @param regressionFunction
//     */
//    private void removePolyRegression(String regressionFunction) {
//        Regression toRemove = null;
//        for (Regression r : plottedRegressions) {
//            if (r instanceof PolyRegression && r.getEquation().equals(regressionFunction)) {
//                toRemove = r;
//                break;
//            }
//        }
//        if (toRemove != null) {
//            plottedRegressions.remove(toRemove);
//            //refreshPanel(true);
//        }
//    }
//    
//    /**
//     * Removes a linear or logarithmic regression from the plot.
//     * TODO: RE IMPLEMENT!
//     * @param type the regression type to remove. 
//     */
//    private void removeLinearOrLogRegression(RegressionType type) {
//        Regression toRemove = null;
//        for (Regression r : plottedRegressions) {
//            if (type == RegressionType.LINEAR && r instanceof LinearRegression) {
//                toRemove = r; 
//                break;
//            } else if (type == RegressionType.LOGARITHMIC && r instanceof LogRegression) {
//                toRemove = r;
//                break;
//            }
//        }
//        
//        if (toRemove != null) {
//            plottedRegressions.remove(toRemove);
//            printRegressions();
//
//        }
//    }
//    
//    private void printRegressions() {
//        System.out.println("Plotted Regressions");
//        for (Regression r : plottedRegressions)
//            System.out.println(r.getEquation());
//        System.out.println("Total Regressions");
//        for (Regression r : allRegressions)
//            System.out.println(r.getEquation());
//    }
    
    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        notifyPlot.addPropertyChangeListener(l);
    }
    
    public HashMap<String, Regression> getAllRegressions() {
        return allRegressions;
    }
    
}
