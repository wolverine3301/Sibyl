package data_panel_components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileSystemView;

import Controllers.Data_Label_Controller;
import logan.sybilGUI.GUI_Util;
import logan.sybilGUI.Tertiary_View;
/**
 * 
 * @author logan collier
 *
 */
public class Data_Label_Panel2 extends Tertiary_View{

	public Data_Label_Panel2(int width, int height, Color main_bg_color, Color main_side_color, int side_panel_W) {
		super(width, height, main_bg_color, main_side_color, side_panel_W);
	}

	@Override
	protected void initComponents() {
		   buttons = new HashMap<String,JButton>();
		   this.controller = new Data_Label_Controller();

	       bottom_panel = new javax.swing.JPanel();
	       numeric_panel = new javax.swing.JPanel();
	       target_panel = new javax.swing.JPanel();
	       categorical_panel = new javax.swing.JPanel();
	       meta_panel = new javax.swing.JPanel();
	       misc_panel = new javax.swing.JPanel();
	       top_panel = new javax.swing.JPanel();
	       
	       jLabel16 = new javax.swing.JLabel();
	       jLabel17 = new javax.swing.JLabel();
	       numNumeric_label = new javax.swing.JLabel();
	       jLabel34 = new javax.swing.JLabel();
	       jLabel18 = new javax.swing.JLabel();
	       num_cat_label = new javax.swing.JLabel();
	       jLabel22 = new javax.swing.JLabel();
	       jLabel19 = new javax.swing.JLabel();
	       num_target_label = new javax.swing.JLabel();
	       jLabel28 = new javax.swing.JLabel();
	       jLabel20 = new javax.swing.JLabel();
	       num_meta_label = new javax.swing.JLabel();
	       jLabel25 = new javax.swing.JLabel();
	       jLabel21 = new javax.swing.JLabel();
	       num_misc_label = new javax.swing.JLabel();
	       file_name_label = new javax.swing.JLabel();
	       fileSize_label = new javax.swing.JLabel();
	       label_size = new javax.swing.JLabel();
	       label_rows = new javax.swing.JLabel();
	       numOfRows_label = new javax.swing.JLabel();
	       label_column = new javax.swing.JLabel();
	       numOfColumns_label = new javax.swing.JLabel();
	       file_sel_btn = new javax.swing.JButton();
	       
	       //ADD side panel stuff
	       ArrayList<String> names = controller.getColumnNames();
	       ArrayList<Character> types = controller.getColumnTypes();
	       
	       
	       side_pane = new JPanel();
	       side_pane.setPreferredSize(new Dimension(side_panel.getPreferredSize().width-10, side_panel.getPreferredSize().height-20));
	       side_pane.setBackground(main_side_color);
	       for(int i = 0; i< names.size(); i++) {
	    	   make_button(names.get(i),types.get(i));
	       }
	       //add(side_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 150, H));
	       scrollSide();
	       add(side_panel, java.awt.BorderLayout.WEST);
	       
	       top_panel.setBackground(main_bg_color);
	       top_panel.setPreferredSize(new java.awt.Dimension(W-side_panel.getPreferredSize().width, 200));

	       file_name_label.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
	       file_name_label.setForeground(new java.awt.Color(153, 153, 153));
	       file_name_label.setText(controller.getFileName());
	       
	       file_sel_btn.setBackground(main_bg_color);
	       file_sel_btn.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
	       file_sel_btn.setForeground(new java.awt.Color(153, 153, 153));
	       file_sel_btn.setText("FILE: ");
	       file_sel_btn.addActionListener(new ActionListener() {
	    	   public void actionPerformed(ActionEvent e) {
	    		   file_select_action();
	    	   }
	       });
	       
	       
	       fileSize_label.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
	       fileSize_label.setForeground(new java.awt.Color(153, 153, 153));
	       fileSize_label.setText("SIZE: ");

	       label_size.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
	       label_size.setForeground(new java.awt.Color(153, 153, 153));
	       label_size.setText("SIZE: ");

	       label_rows.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
	       label_rows.setForeground(new java.awt.Color(153, 153, 153));
	       label_rows.setText("ROWS:");

	       numOfRows_label.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
	       numOfRows_label.setForeground(new java.awt.Color(153, 153, 153));
	       numOfRows_label.setText(String.valueOf(controller.getTotalRows()));

	       label_column.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
	       label_column.setForeground(new java.awt.Color(153, 153, 153));
	       label_column.setText("COLUMNS:");

	       numOfColumns_label.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
	       numOfColumns_label.setForeground(new java.awt.Color(153, 153, 153));
	       numOfColumns_label.setText(String.valueOf(controller.getNumColumns()));

	       javax.swing.GroupLayout top_panelLayout = new javax.swing.GroupLayout(top_panel);
	       top_panel.setLayout(top_panelLayout);
	       top_panelLayout.setHorizontalGroup(
	           top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	           .addGroup(top_panelLayout.createSequentialGroup()
	               .addGroup(top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                   .addGroup(top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
	                       .addGroup(top_panelLayout.createSequentialGroup()
	                           .addComponent(file_sel_btn)
	                           .addGap(18, 18, 18)
	                           .addComponent(file_name_label))
	                       .addGroup(top_panelLayout.createSequentialGroup()
	                           .addGroup(top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, top_panelLayout.createSequentialGroup()
	                                   .addComponent(label_size)
	                                   .addGap(18, 18, 18))
	                               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, top_panelLayout.createSequentialGroup()
	                                   .addComponent(label_rows)
	                                   .addGap(29, 29, 29)))
	                           .addGroup(top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	                               .addComponent(numOfRows_label)
	                               .addComponent(fileSize_label))))
	                   .addGroup(top_panelLayout.createSequentialGroup()
	                       .addComponent(label_column)
	                       .addGap(18, 18, 18)
	                       .addComponent(numOfColumns_label)))
	               .addGap(0, 812, Short.MAX_VALUE))
	       );
	       top_panelLayout.setVerticalGroup(
	           top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	           .addGroup(top_panelLayout.createSequentialGroup()
	               .addContainerGap()
	               .addGroup(top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                   .addComponent(file_sel_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
	                   .addComponent(file_name_label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
	               .addGap(10, 10, 10)
	               .addGroup(top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                   .addComponent(label_size, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
	                   .addComponent(fileSize_label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
	               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	               .addGroup(top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                   .addComponent(label_rows, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
	                   .addComponent(numOfRows_label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
	               .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	               .addGroup(top_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
	                   .addComponent(label_column, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
	                   .addComponent(numOfColumns_label, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
	               .addContainerGap(77, Short.MAX_VALUE))
	       );

	       center_panel.add(top_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, W-side_panel.getPreferredSize().width, 200));
	      
	       add(center_panel,java.awt.BorderLayout.CENTER);

	       bottom_panel.setBackground(main_bg_color);
	       bottom_panel.setPreferredSize(new java.awt.Dimension(center_panel.getPreferredSize().width-200, 500));
	       bottom_panel.setLayout(new java.awt.GridLayout(1, 5));

	       numeric_panel.setBackground(main_bg_color);
	       numeric_panel.setLayout(new java.awt.GridLayout(20, 1));

	       jLabel16.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
	       jLabel16.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel16.setText("Numeric");
	       numeric_panel.add(jLabel16);

	       jLabel17.setBackground(new java.awt.Color(255, 255, 255));
	       jLabel17.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       jLabel17.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel17.setText("Total: ");
	       numeric_panel.add(jLabel17);

	       numNumeric_label.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       numNumeric_label.setForeground(new java.awt.Color(153, 153, 153));
	       numNumeric_label.setText(String.valueOf(controller.getTotalNumericColumns()));
	       numeric_panel.add(numNumeric_label);

	       bottom_panel.add(numeric_panel);

	       categorical_panel.setBackground(main_bg_color);
	       categorical_panel.setLayout(new java.awt.GridLayout(20, 1));

	       jLabel34.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
	       jLabel34.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel34.setText("Categorical");
	       categorical_panel.add(jLabel34);

	       jLabel18.setBackground(new java.awt.Color(255, 255, 255));
	       jLabel18.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       jLabel18.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel18.setText("Total: ");
	       categorical_panel.add(jLabel18);

	       num_cat_label.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       num_cat_label.setForeground(new java.awt.Color(153, 153, 153));
	       num_cat_label.setText(String.valueOf(controller.getTotalCategoricalColumns()));
	       categorical_panel.add(num_cat_label);

	       bottom_panel.add(categorical_panel);

	       target_panel.setBackground(main_bg_color);
	       target_panel.setLayout(new java.awt.GridLayout(20, 1));

	       jLabel22.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
	       jLabel22.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel22.setText("Target");
	       target_panel.add(jLabel22);

	       jLabel19.setBackground(new java.awt.Color(255, 255, 255));
	       jLabel19.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       jLabel19.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel19.setText("Total: ");
	       target_panel.add(jLabel19);

	       num_target_label.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       num_target_label.setForeground(new java.awt.Color(153, 153, 153));
	       num_target_label.setText(String.valueOf(controller.getTotalTargetColumns()));
	       target_panel.add(num_target_label);

	       bottom_panel.add(target_panel);

	       meta_panel.setBackground(main_bg_color);
	       meta_panel.setLayout(new java.awt.GridLayout(20, 1));

	       jLabel28.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
	       jLabel28.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel28.setText("Meta");
	       meta_panel.add(jLabel28);

	       jLabel20.setBackground(new java.awt.Color(255, 255, 255));
	       jLabel20.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       jLabel20.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel20.setText("Total: ");
	       meta_panel.add(jLabel20);

	       num_meta_label.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       num_meta_label.setForeground(new java.awt.Color(153, 153, 153));
	       num_meta_label.setText(String.valueOf(controller.getTotalMetaColumns()));
	       meta_panel.add(num_meta_label);

	       bottom_panel.add(meta_panel);

	       misc_panel.setBackground(main_bg_color);
	       misc_panel.setLayout(new java.awt.GridLayout(20, 1));

	       jLabel25.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
	       jLabel25.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel25.setText("MISC");
	       misc_panel.add(jLabel25);

	       jLabel21.setBackground(new java.awt.Color(255, 255, 255));
	       jLabel21.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       jLabel21.setForeground(new java.awt.Color(153, 153, 153));
	       jLabel21.setText("Total: ");
	       misc_panel.add(jLabel21);

	       num_misc_label.setFont(new java.awt.Font("Courier New", 0, 14)); // NOI18N
	       num_misc_label.setForeground(new java.awt.Color(153, 153, 153));
	       num_misc_label.setText("jLabel7");
	       misc_panel.add(num_misc_label);

	       bottom_panel.add(misc_panel);

	       center_panel.add(bottom_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 200, W-side_panel.getPreferredSize().width, H-200));

	   }// </editor-fold>                        
	   private void make_button(String name, char t) {
		   JButton btn = new javax.swing.JButton();
	       btn.setBackground(new java.awt.Color(24, 30, 40));
	       btn.setForeground(new java.awt.Color(153, 153, 153));
	       btn.setPreferredSize(new java.awt.Dimension(150, 30));
	       
	       setBtnIcon(btn,t);
	       
	       btn.setText(name);
	       
	       btn.addActionListener(new ActionListener() {
	       	public void actionPerformed(ActionEvent e) {
	       		JDialog edit_column = new JDialog();
	            edit_column.setSize(400, 300);
	            Edit_Column_Panel edit_panel = new Edit_Column_Panel(name,t,controller);
	            edit_column.addWindowListener(new WindowAdapter() 
	            {
	              public void windowClosed(WindowEvent e)
	              {
	            	  
	              }
	              public void windowClosing(WindowEvent e)
	              {
	                //System.out.println("jdialog window closing event received");
	                num_cat_label.setText(String.valueOf(controller.getTotalCategoricalColumns()));
	                num_target_label.setText(String.valueOf(controller.getTotalTargetColumns()));
	                num_meta_label.setText(String.valueOf(controller.getTotalMetaColumns()));
	                numNumeric_label.setText(String.valueOf(controller.getTotalNumericColumns()));
	                setBtnIcon(btn,controller.getColummnType(name));
	              }
	            });
	            edit_column.add(edit_panel);
	            edit_column.setVisible(true);
	            
	       	}
	       });
	       buttons.put(name,btn);
	       side_pane.add(btn);
	   }
	   //open file selector
	   private void file_select_action() {
	        // create an object of JFileChooser class 
	  		javax.swing.JFileChooser filechoose = new javax.swing.JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory()); 
	        // invoke the showsOpenDialog function to show the save dialog 
	        int r = filechoose.showOpenDialog(null); 
	        // if the user selects a file 
	        if (r == filechoose.APPROVE_OPTION){ 
	            // set the label to the path of the selected file 
	            System.out.println(filechoose.getSelectedFile().getAbsolutePath()); 
	            this.controller.openFile(filechoose.getSelectedFile().getAbsolutePath());
	            side_panel.removeAll();
       			side_pane.removeAll();

       			ArrayList<String> names = controller.getColumnNames();
       			ArrayList<Character> types = controller.getColumnTypes();
       			for(int i = 0; i< names.size(); i++) {
       				make_button(names.get(i),types.get(i));
       			}

       			side_pane.repaint();
       			side_pane.revalidate();
       			scrollPane = new JScrollPane(side_pane);
       	        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
       	        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       	        scrollPane.setBounds(0, 0, side_panel.getPreferredSize().width, side_panel.getPreferredSize().height);
       	        side_panel.add(scrollPane);
       	        //scrollPane.repaint();
       	        //scrollPane.revalidate();
       			side_panel.repaint();
       			side_panel.revalidate();
       			numOfRows_label.setText(String.valueOf(controller.getTotalRows()));
       			numOfColumns_label.setText(String.valueOf(controller.getNumColumns()));
                num_cat_label.setText(String.valueOf(controller.getTotalCategoricalColumns()));
                num_target_label.setText(String.valueOf(controller.getTotalTargetColumns()));
                num_meta_label.setText(String.valueOf(controller.getTotalMetaColumns()));
                numNumeric_label.setText(String.valueOf(controller.getTotalNumericColumns()));
	        } 
	        // if the user cancelled the operation 
	        else
	            System.out.println("the user cancelled the operation");
	        
		    filechoose.setSize(500, 500);
	        filechoose.setVisible(true);
	        
	   }
	   private void setBtnIcon(JButton btn, char t) {
	       if(t == 'T')
	    	   btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/icons8_t_20px_2.png",20,20)); // NOI18N
	       if(t == 'C')
	    	   btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/icons8_c_20px_1.png",20,20)); // NOI18N
	       if(t == 'N')
	    	   btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/icons8_n_20px_2.png",20,20)); // NOI18N
	       if(t == 'M')
	    	   btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/icons8_m_20px.png",20,20)); // NOI18N
	   }
	public Data_Label_Controller getDataCtrl() {
		return controller;
	}
	protected void scrollSide() {
		
        scrollPane = new JScrollPane(side_pane);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, side_panel.getPreferredSize().width-100, side_panel.getPreferredSize().height-100);
        
        //contentPane.setPreferredSize(new Dimension(side_panel.getWidth(), side_panel.getHeight()));
        side_panel.add(scrollPane);
	}
	/************	SWING COMPONENTS ******************/
	   // Variables declaration - do not modify                     
	   private javax.swing.JPanel bottom_panel;
	   private javax.swing.JPanel categorical_panel;
	   private javax.swing.JPanel meta_panel;
	   private javax.swing.JPanel misc_panel;
	   private javax.swing.JPanel numeric_panel;
	   private javax.swing.JPanel target_panel;
	   private javax.swing.JPanel top_panel;
	   
	   private JPanel side_pane;
	   private JScrollPane scrollPane;
	   
	   private javax.swing.JLabel fileSize_label;
	   private javax.swing.JLabel file_name_label;
	   private javax.swing.JLabel jLabel16;
	   private javax.swing.JLabel jLabel17;
	   private javax.swing.JLabel jLabel18;
	   private javax.swing.JLabel jLabel19;
	   private javax.swing.JLabel jLabel20;
	   private javax.swing.JLabel jLabel21;
	   private javax.swing.JLabel jLabel22;
	   private javax.swing.JLabel jLabel25;
	   private javax.swing.JLabel jLabel28;
	   private javax.swing.JLabel jLabel34;
	   private javax.swing.JLabel label_column;
	   private javax.swing.JLabel label_rows;
	   private javax.swing.JLabel label_size;
	   private javax.swing.JLabel numNumeric_label;
	   private javax.swing.JLabel numOfColumns_label;
	   private javax.swing.JLabel numOfRows_label;
	   private javax.swing.JLabel num_cat_label;
	   private javax.swing.JLabel num_meta_label;
	   private javax.swing.JLabel num_misc_label;
	   private javax.swing.JLabel num_target_label;

	   
	   
	   private javax.swing.JButton file_sel_btn;
	   // End of variables declaration    
	   private HashMap<String,JButton> buttons;
	   private Data_Label_Controller controller;

}
