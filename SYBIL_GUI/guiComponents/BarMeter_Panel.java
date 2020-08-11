package guiComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BarMeter_Panel extends JPanel{
	private HashMap<String, Integer> vals;
	private HashMap<String, Integer> previous_vals;
	private HashMap<String, Integer> vals_max;
	private Color BackGround;
	private Color barColor;
	private int bar_w;
	private int bar_h;
	
	private ArrayList<String> meter_names;
	private ArrayList<JLabel> meter_labels;
	private ArrayList<Bar_Meter> meters;
	
	
	/**
	 * 
	 * @param vals
	 * @param w
	 * @param h
	 * @param barColor
	 * @param bg_color
	 */
	public BarMeter_Panel(HashMap<String,Integer> vals,HashMap<String,Integer> vals_max,int w, int h, Color barColor,Color bg_color) {
		this.vals = vals;
		this.previous_vals = new HashMap<String,Integer>();
		for(String i : vals.keySet()) {
			this.previous_vals.put(i, vals.get(i));
		}
		setPreferredSize(new Dimension(w,h));
		setBackground(bg_color);
		setBorder(new EmptyBorder(15, 15, 15, 15));
		this.barColor = barColor;
		this.bar_w = w-30;
		this.bar_h = (h/(vals.size()*2));
		this.vals_max = vals_max;
		initMeters();
	}
	private void initMeters() {
		meter_labels = new ArrayList<JLabel>();
		meter_names = new ArrayList<String>();
		meters = new ArrayList<Bar_Meter>();
		setLayout(new GridLayout(vals.size()*2+2, 1));
		
		for(String i :vals.keySet()) {
			JLabel label = new JLabel();
			if(bar_h < 40)
				label.setFont(new java.awt.Font("Courier New", 0, bar_h)); // NOI18N
			else
				label.setFont(new java.awt.Font("Courier New", 0, 40));
			
			label.setForeground(new java.awt.Color(153, 153, 153));
			label.setText(i);
			meter_labels.add(label);
			meter_names.add(i);
			Bar_Meter bar = new Bar_Meter(barColor,Color.white,bar_w ,bar_h);
			//bar.setLabel2(0, lvl.getBase_levels().get(i).getExp_next());
			meters.add(bar);
			add(label);
			add(bar);
		}
	}
    public void setBars(HashMap<String,Integer> newvals) {
    	this.vals = newvals;
    	for(int i = 0; i < meters.size();i++) {
    		updater(i);
    	}
    }
    public void setBars2(HashMap<String,Integer> newvals) {
    	this.vals = newvals;
    	for(int i = 0; i < meters.size();i++) {
    		updater2(i);
    	}
    }
    private void updater(int k) {
    	int cnt = 0;
    	for(String i : vals.keySet()) {
    		meter_labels.get(cnt).setText(i);
    		cnt++;
    	}
    	//repaint();
    	final int i = k;
    	new Thread(new Runnable(){
			public void run() {
		    	for(int j=0; j <= ((double)vals.get(meter_names.get(i)) / vals_max.get(meter_names.get(i)) ) *100;j++) {
		        	meters.get(i).updateProgress(j);
		        	//meters.get(i).updateProgress2(lvl.getBase_levels().get(meter_names.get(i)).getExp(),lvl.getBase_levels().get(meter_names.get(i)).getExp_next());
		        	meters.get(i).repaint();
		            try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
			}
    	}).start();
    }
    // allows bar to not start from 0
    private void updater2(int k) {
    	int cnt = 0;
    	for(String i : vals.keySet()) {
    		meter_labels.get(cnt).setText(i);
    		cnt++;
    	}
    	//repaint();
    	final int i = k;
    	
    	if(previous_vals.get(meter_names.get(i)) >= vals.get(meter_names.get(i)) ){

	    	new Thread(new Runnable(){
				public void run() {
			    	for(int j=(int) (((double)previous_vals.get(meter_names.get(i)) / vals_max.get(meter_names.get(i)) ) *100); j <= ((double)vals.get(meter_names.get(i)) / vals_max.get(meter_names.get(i)) ) *100;j++) {
			        	meters.get(i).updateProgress(j);
			        	//meters.get(i).updateProgress2(lvl.getBase_levels().get(meter_names.get(i)).getExp(),lvl.getBase_levels().get(meter_names.get(i)).getExp_next());
			        	meters.get(i).repaint();
			    		previous_vals.put(meter_names.get(i), vals.get(meter_names.get(i)));
			            try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
				}
	    	}).start();
    	}else {
	    	new Thread(new Runnable(){
				public void run() {
			    	for(int j=(int) (((double)previous_vals.get(meter_names.get(i)) / vals_max.get(meter_names.get(i)) ) *100); j > ((double)vals.get(meter_names.get(i)) / vals_max.get(meter_names.get(i)) ) *100;j--) {
			        	meters.get(i).updateProgress(j);
			        	//meters.get(i).updateProgress2(lvl.getBase_levels().get(meter_names.get(i)).getExp(),lvl.getBase_levels().get(meter_names.get(i)).getExp_next());
			        	meters.get(i).repaint();
			    		previous_vals.put(meter_names.get(i), vals.get(meter_names.get(i)));
			            try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
				}
	    	}).start();
    	}
    }
}
