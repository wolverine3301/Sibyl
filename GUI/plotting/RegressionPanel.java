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
import javax.swing.JTextField;

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
    
    /**
     * 
     */
    private void start() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Regressions");
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(label);
    }
    
    
    public void addRegression(Regression r) {
        allRegressions.put(r.getEquation(), r);
        plottedRegressions.put(r.getEquation(), r);
        JPanel regPanel = generateRegressionPanel(r);
        regressionSubPanels.add(regPanel);
        this.add(regPanel);
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
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.add(new JLabel("x = "));
        JTextField xVal = new JTextField(5);
        inputPanel.add(xVal);
        ActionListener pointListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double x = getXInput(xVal);
                if (x == Double.MIN_VALUE) {
                    JOptionPane.showInputDialog(xVal, "Invalid input for x.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    notifyPlot.firePropertyChange("POINT", box.getText(), x);
                }
            }
        };
        xVal.addActionListener(pointListener);
        JButton plotPoint = new JButton("Plot Point");
        plotPoint.addActionListener(pointListener);
        inputPanel.add(xVal);
        inputPanel.add(plotPoint);
        panel.add(inputPanel);
        panel.add(info);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, info.getMinimumSize().height * 4));
        return panel;
    }
    
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
    
    private JLabel createRegressionInfo(Regression r) {
        String r2 = "R^2 = " + r.R2;
        String mse = "MSE = " + r.MSE;
        String mae = "MAE = " + r.RMSD;
        return new JLabel(r2 + ", " + mse + ", " + mae);
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
