package armorment_components;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import Controllers.Evaluate_Controller;
import armorment.Armorment;
import logan.sybilGUI.Secondary_View;

public class ArmormentPanel extends Secondary_View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Armorment armorment;
	
	public ArmormentPanel(int width, int height, Color main_bg_color, Color main_side_color,Armorment armorment) {
		super(width, height, main_bg_color, main_side_color);
		this.armorment = armorment;
	}

    protected void initComponents() {
    	
        add_KNN = new javax.swing.JCheckBox();
        add_naiveBayes = new javax.swing.JCheckBox();
        init_k_spinner = new javax.swing.JSpinner();
        initK_label = new javax.swing.JLabel();
        terminatK_label = new javax.swing.JLabel();
        terminal_K_spinner = new javax.swing.JSpinner();
        add_decisionTree = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        enhanceArmorment = new JButton();
        
        setBackground(main_side_color);

        add_KNN.setBackground(main_side_color);
        add_KNN.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        add_KNN.setForeground(new java.awt.Color(153, 153, 153));
        add_KNN.setText("KNN");

        add_naiveBayes.setBackground(main_side_color);
        add_naiveBayes.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        add_naiveBayes.setForeground(new java.awt.Color(153, 153, 153));
        add_naiveBayes.setText("Naive Bayes");
        
        add_naiveBayes.setSelected(true);
        
        initK_label.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        initK_label.setForeground(new java.awt.Color(153, 153, 153));
        initK_label.setText("Init K");

        terminatK_label.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        terminatK_label.setForeground(new java.awt.Color(153, 153, 153));
        terminatK_label.setText("Terminal K");

        add_decisionTree.setBackground(main_side_color);
        add_decisionTree.setFont(new java.awt.Font("Courier New", 0, 12)); // NOI18N
        add_decisionTree.setForeground(new java.awt.Color(153, 153, 153));
        add_decisionTree.setText("Decision Tree");
        
        jLabel3.setFont(new java.awt.Font("Courier New", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("ARMORY");

        enhanceArmorment.setBackground(main_side_color);
        enhanceArmorment.setFont(new java.awt.Font("Courier New", 0, 18)); // NOI18N
        enhanceArmorment.setForeground(new java.awt.Color(102, 102, 102));
        enhanceArmorment.setText("ENHANCE ARMORMENT");

        enhanceArmorment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enhance_ActionPerformed(evt);
            }
        });
        		
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(initK_label, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                                    .addComponent(init_k_spinner, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(terminal_K_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(terminatK_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(add_naiveBayes)
                                    .addComponent(add_KNN, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(42, 42, 42))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(add_decisionTree)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(enhanceArmorment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(add_KNN)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(initK_label)
                    .addComponent(terminatK_label))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(init_k_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(terminal_K_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(add_naiveBayes)
                .addGap(18, 18, 18)
                .addComponent(add_decisionTree)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 351, Short.MAX_VALUE)
                .addComponent(enhanceArmorment, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
    }// </editor-fold>                          

    private void enhance_ActionPerformed(java.awt.event.ActionEvent evt) {                                                 
       if(add_KNN.isSelected()) {
    	   armorment.addKNN((int)init_k_spinner.getValue(),(int) terminal_K_spinner.getValue());
       }
       if(add_naiveBayes.isSelected()) {
    	   armorment.addNaiveBayes();
       }
       if(add_decisionTree.isSelected()) {
    	   armorment.addDecisionTree();;
       }
       Evaluate_Controller.setArmorment(this.armorment);
       //System.out.println(Evaluate_Controller.models.getModels().size());
    }                                                


    // Variables declaration - do not modify                     
    private javax.swing.JCheckBox add_KNN;
    private javax.swing.JCheckBox add_decisionTree;
    private javax.swing.JCheckBox add_naiveBayes;
    private javax.swing.JLabel initK_label;
    private javax.swing.JSpinner init_k_spinner;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSpinner terminal_K_spinner;
    private javax.swing.JLabel terminatK_label;
    private JButton enhanceArmorment;

}
