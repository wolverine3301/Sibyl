package plotting;

import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.orsoncharts.Chart3DPanel;


/**
 * The base class for panels created by demo applications.  Some demos will 
 * subclass to add extra controls in addition to the main {@link Chart3DPanel}.
 */
@SuppressWarnings("serial")
public class Plot_3D_Panel extends JPanel {
    
    /** 
     * A list of the chart panels on the demo panel (usually just one, but 
     * there can be multiple panels).
     */
    private List<Chart3DPanel> chartPanels;
    
    /**
     * Creates a new instance.
     * 
     * @param layout  the layout manager. 
     */
    public Plot_3D_Panel(LayoutManager layout) {
        super(layout);
        this.chartPanels = new ArrayList<Chart3DPanel>();
    }
    
    /**
     * Returns the chart panel for this demo panel.  In the case where there
     * are multiple chart panels, this method will return the first one.
     * 
     * @return The chart panel (possibly {@code null}). 
     */
    public Chart3DPanel getChartPanel() {
        if (this.chartPanels.isEmpty()) {
            return null;
        }
        return this.chartPanels.get(0);    
    }
    
    /**
     * Returns the {@link Chart3DPanel} from the demo panel.
     * 
     * @return The {@link Chart3DPanel}. 
     */
    public List<Chart3DPanel> getChartPanels() {
        return this.chartPanels;
    }
    
    /**
     * Sets the chart panel that is displayed within this demo panel (for the
     * case where there is only one chart panel).
     * 
     * @param panel  the panel.
     */
    public void setChartPanel(Chart3DPanel panel) {
        this.chartPanels.clear();
        this.chartPanels.add(panel);
    }
    
    /**
     * Adds the {@link Chart3DPanel} for this demo panel.  This can be
     * accessed by code that wants to do something to the chart.
     * 
     * @param panel  the panel. 
     */
    public void addChartPanel(Chart3DPanel panel) {
        this.chartPanels.add(panel);
    }
}
