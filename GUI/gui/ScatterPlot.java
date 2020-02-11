package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dataframe.Column;
import dataframe.DataFrame;

public class ScatterPlot extends JFrame{
	private DataFrame df;
	private Column col_x;
	private Column col_y;
	private Plot scatter;
	/**
	 * constructor
	 */
	public ScatterPlot(DataFrame df) {
		this.df = df;
		
	}
	public void start() {
		JFrame frame = new JFrame("scatter plot");
		scatter = new Plot(df.getColumn(df.numericIndexes.get(0)), df.getColumn(df.numericIndexes.get(0)));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.add(columnSelectPanel(),BorderLayout.WEST);
		frame.add(scatter,BorderLayout.EAST);
	}
	private JPanel columnSelectPanel() {
		JPanel sidePanel = new JPanel();
		sidePanel.setSize(200, 600);
		sidePanel.setLayout(new BorderLayout());
		JLabel xaxis_label = new JLabel("X-Axis: ");
		JLabel yaxis_label = new JLabel("Y-Axis: ");
		
		JComboBox<String> columnNames_x = new JComboBox<String>();
		JComboBox<String> columnNames_y = new JComboBox<String>();
		//initiallize combo boxesa
		for(Integer i : df.numericIndexes) {
			columnNames_x.addItem(df.getColumnNames().get(i));
			columnNames_y.addItem(df.getColumnNames().get(i));
		}
		columnNames_x.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				col_x = df.getColumn_byName((String) columnNames_x.getSelectedItem());
				scatter = new Plot(col_x,col_y);
			}
			
		});
		columnNames_y.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				col_y = df.getColumn_byName((String) columnNames_y.getSelectedItem());
				scatter = new Plot(col_x,col_y);
			}
			
		});
		sidePanel.add(xaxis_label,BorderLayout.WEST);
		sidePanel.add(columnNames_x,BorderLayout.EAST);
		sidePanel.add(yaxis_label,BorderLayout.WEST);
		sidePanel.add(columnNames_y,BorderLayout.EAST);
		return sidePanel;
	}


}
