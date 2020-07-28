package logan.sybilGUI;

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JButton;

public class Evaluation_Control_Panel extends Tertiary_View{

	private HashMap<String,javax.swing.JComboBox> target_classes;
	private javax.swing.JComboBox priority_metric_button;
	private Color txtColor;
	
    private javax.swing.JComboBox<String> Priority_class_menu;
    private javax.swing.JComboBox<String> Priority_targets_menu;
    private javax.swing.JCheckBox chi2Ranker_checkbox;
    private javax.swing.JCheckBox gainRatioRanker_checkbox;
    private javax.swing.JCheckBox giniRanker_checkbox;
    private javax.swing.JCheckBox infoGainRanker_checkbox;
    private javax.swing.JLabel priority_class_label;
    private javax.swing.JLabel priority_metric_label;
    private javax.swing.JComboBox<String> priority_metric_menu;
    private javax.swing.JLabel priority_targets_label;
    private javax.swing.JButton releaseRecollection_button;
    private javax.swing.JLabel stepSizeLabel;
    private javax.swing.JSpinner stepSize_spinner;
    
	public Evaluation_Control_Panel(int width, int height, Color main_bg_color, Color main_side_color, int side_panel_W) {
		super(width, height, main_bg_color, main_side_color, side_panel_W);
		
	}

	@Override
	protected void initComponents() {
		this.txtColor = new java.awt.Color(153, 0, 153);
        //side_panel = new javax.swing.JPanel();
        chi2Ranker_checkbox = new javax.swing.JCheckBox();
        infoGainRanker_checkbox = new javax.swing.JCheckBox();
        gainRatioRanker_checkbox = new javax.swing.JCheckBox();
        giniRanker_checkbox = new javax.swing.JCheckBox();
        stepSize_spinner = new javax.swing.JSpinner();
        stepSizeLabel = new javax.swing.JLabel();
        priority_metric_label = new javax.swing.JLabel();
        Priority_targets_menu = new javax.swing.JComboBox<>();
        priority_targets_label = new javax.swing.JLabel();
        Priority_class_menu = new javax.swing.JComboBox<>();
        priority_class_label = new javax.swing.JLabel();
        priority_metric_menu = new javax.swing.JComboBox<>();
        releaseRecollection_button = new javax.swing.JButton();

        setBackground(main_side_color);

        chi2Ranker_checkbox.setBackground(main_side_color);
        chi2Ranker_checkbox.setForeground(txtColor);
        chi2Ranker_checkbox.setText("Chi2 Ranker");

        infoGainRanker_checkbox.setBackground(main_side_color);
        infoGainRanker_checkbox.setForeground(txtColor);
        infoGainRanker_checkbox.setText("Info Gain");
        
        infoGainRanker_checkbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoGainRanker_checkboxActionPerformed(evt);
            }
        });

        gainRatioRanker_checkbox.setBackground(main_side_color);
        gainRatioRanker_checkbox.setForeground(txtColor);
        gainRatioRanker_checkbox.setText("Gain Raito");

        giniRanker_checkbox.setBackground(main_side_color);
        giniRanker_checkbox.setForeground(txtColor);
        giniRanker_checkbox.setText("Gini index");
        
        
        stepSizeLabel.setBackground(main_side_color);
        stepSizeLabel.setForeground(new java.awt.Color(153, 0, 153));
        stepSizeLabel.setText("Step Size");

        priority_metric_label.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        priority_metric_label.setForeground(txtColor);
        priority_metric_label.setText("Priority Metric");

        Priority_targets_menu.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        Priority_targets_menu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        priority_targets_label.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        priority_targets_label.setForeground(txtColor);
        priority_targets_label.setText("Priority Targets");

        Priority_class_menu.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        Priority_class_menu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        priority_class_label.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        priority_class_label.setForeground(txtColor);
        priority_class_label.setText("Priority Item");

        priority_metric_menu.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        priority_metric_menu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        releaseRecollection_button.setBackground(main_side_color);
        releaseRecollection_button.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        releaseRecollection_button.setForeground(txtColor);
        releaseRecollection_button.setText("Release Recolection");
        releaseRecollection_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                releaseRecollection_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(side_panel);
        side_panel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Priority_class_menu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(priority_targets_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(priority_metric_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(priority_class_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(priority_metric_menu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(chi2Ranker_checkbox)
                                .addComponent(infoGainRanker_checkbox)
                                .addComponent(gainRatioRanker_checkbox)
                                .addComponent(giniRanker_checkbox)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(stepSize_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(stepSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(Priority_targets_menu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(releaseRecollection_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(chi2Ranker_checkbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(infoGainRanker_checkbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(gainRatioRanker_checkbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(giniRanker_checkbox)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stepSize_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stepSizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(priority_targets_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Priority_targets_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(priority_class_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Priority_class_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(priority_metric_label, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(priority_metric_menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
                .addComponent(releaseRecollection_button, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(side_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 601, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(side_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        
	    add(side_panel, java.awt.BorderLayout.WEST);
	    add(center_panel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>                        

    private void infoGainRanker_checkboxActionPerformed(java.awt.event.ActionEvent evt) {                                                        
        // TODO add your handling code here:
    }                                                       

    private void releaseRecollection_buttonActionPerformed(java.awt.event.ActionEvent evt) {                                                           
        // TODO add your handling code here:
    }                

}