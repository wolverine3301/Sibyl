package logan.sybilGUI;

import java.awt.Color;
import java.io.PrintStream;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import scout.Invoke;

public class Hypothesis_Panel2 extends Tertiary_View{

	public Hypothesis_Panel2(int width, int height, Color main_bg_color, Color main_side_color, int side_panel_W) {
		super(width, height, main_bg_color, main_side_color, side_panel_W);
	}

	@Override
	protected void initComponents() {
        ttest_panel = new javax.swing.JPanel();
        t_test_checkbox = new javax.swing.JCheckBox();
        ttest_significe_spinner = new javax.swing.JSpinner();
        chisquared_panel = new javax.swing.JPanel();
        chisquared_checkbox = new javax.swing.JCheckBox();
        chi_significe_spinner = new javax.swing.JSpinner();
        obstable_checkbox = new javax.swing.JCheckBox();
        expected_checkbox = new javax.swing.JCheckBox();
        infogain_panel = new javax.swing.JPanel();
        infogain_checkbox = new javax.swing.JCheckBox();
        gini_panel = new javax.swing.JPanel();
        gainratio_checkbox = new javax.swing.JCheckBox();
        execute_panel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        makeReport_checkbox = new javax.swing.JCheckBox();
        
        side_panel.setLayout(new java.awt.GridLayout(5, 1));

        ttest_panel.setBackground(main_side_color);

        t_test_checkbox.setBackground(main_side_color);
        t_test_checkbox.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        t_test_checkbox.setForeground(new java.awt.Color(153, 153, 153));
        t_test_checkbox.setText("T-Test");

        javax.swing.GroupLayout ttest_panelLayout = new javax.swing.GroupLayout(ttest_panel);
        ttest_panel.setLayout(ttest_panelLayout);
        ttest_panelLayout.setHorizontalGroup(
            ttest_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ttest_panelLayout.createSequentialGroup()
                .addComponent(t_test_checkbox)
                .addGap(18, 18, 18)
                .addComponent(ttest_significe_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 150, Short.MAX_VALUE))
        );
        ttest_panelLayout.setVerticalGroup(
            ttest_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ttest_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ttest_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(t_test_checkbox)
                    .addComponent(ttest_significe_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(106, Short.MAX_VALUE))
        );

        side_panel.add(ttest_panel);

        chisquared_panel.setBackground(main_side_color);

        chisquared_checkbox.setBackground(main_side_color);
        chisquared_checkbox.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        chisquared_checkbox.setForeground(new java.awt.Color(153, 153, 153));
        chisquared_checkbox.setSelected(true);
        chisquared_checkbox.setText("Chi Square Independence");

        obstable_checkbox.setBackground(main_side_color);
        obstable_checkbox.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        obstable_checkbox.setForeground(new java.awt.Color(153, 153, 153));
        obstable_checkbox.setSelected(true);
        obstable_checkbox.setText("Observed Tables");

        expected_checkbox.setBackground(main_side_color);
        expected_checkbox.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        expected_checkbox.setForeground(new java.awt.Color(153, 153, 153));
        expected_checkbox.setSelected(true);
        expected_checkbox.setText("expected Tables");

        javax.swing.GroupLayout chisquared_panelLayout = new javax.swing.GroupLayout(chisquared_panel);
        chisquared_panel.setLayout(chisquared_panelLayout);
        chisquared_panelLayout.setHorizontalGroup(
            chisquared_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chisquared_panelLayout.createSequentialGroup()
                .addComponent(chisquared_checkbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(chi_significe_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addGroup(chisquared_panelLayout.createSequentialGroup()
                .addGroup(chisquared_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(obstable_checkbox)
                    .addComponent(expected_checkbox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        chisquared_panelLayout.setVerticalGroup(
            chisquared_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chisquared_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chisquared_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chi_significe_spinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chisquared_checkbox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(obstable_checkbox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expected_checkbox)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        side_panel.add(chisquared_panel);

        infogain_panel.setBackground(main_side_color);

        infogain_checkbox.setBackground(main_side_color);
        infogain_checkbox.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        infogain_checkbox.setForeground(new java.awt.Color(153, 153, 153));
        infogain_checkbox.setText("Information Gain");

        javax.swing.GroupLayout infogain_panelLayout = new javax.swing.GroupLayout(infogain_panel);
        infogain_panel.setLayout(infogain_panelLayout);
        infogain_panelLayout.setHorizontalGroup(
            infogain_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infogain_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infogain_checkbox)
                .addContainerGap(124, Short.MAX_VALUE))
        );
        infogain_panelLayout.setVerticalGroup(
            infogain_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infogain_panelLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(infogain_checkbox)
                .addContainerGap(60, Short.MAX_VALUE))
        );

        side_panel.add(infogain_panel);

        gini_panel.setBackground(main_side_color);

        gainratio_checkbox.setBackground(main_side_color);
        gainratio_checkbox.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        gainratio_checkbox.setForeground(new java.awt.Color(153, 153, 153));
        gainratio_checkbox.setText("Gini decrese");

        javax.swing.GroupLayout gini_panelLayout = new javax.swing.GroupLayout(gini_panel);
        gini_panel.setLayout(gini_panelLayout);
        gini_panelLayout.setHorizontalGroup(
            gini_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gini_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gainratio_checkbox)
                .addContainerGap(152, Short.MAX_VALUE))
        );
        gini_panelLayout.setVerticalGroup(
            gini_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gini_panelLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(gainratio_checkbox)
                .addContainerGap(60, Short.MAX_VALUE))
        );
        side_panel.add(gini_panel);

        execute_panel.setBackground(main_side_color);

        jButton1.setText("Reveal");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invokeActionPerformed(evt);
            }
        });
        makeReport_checkbox.setBackground(main_side_color);
        makeReport_checkbox.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
        makeReport_checkbox.setForeground(new java.awt.Color(153, 153, 153));
        makeReport_checkbox.setText("Make Report");

        javax.swing.GroupLayout execute_panelLayout = new javax.swing.GroupLayout(execute_panel);
        execute_panel.setLayout(execute_panelLayout);
        execute_panelLayout.setHorizontalGroup(
            execute_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(execute_panelLayout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(jButton1)
                .addContainerGap(100, Short.MAX_VALUE))
            .addGroup(execute_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(execute_panelLayout.createSequentialGroup()
                    .addGap(79, 79, 79)
                    .addComponent(makeReport_checkbox)
                    .addContainerGap(85, Short.MAX_VALUE)))
        );
        execute_panelLayout.setVerticalGroup(
            execute_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, execute_panelLayout.createSequentialGroup()
                .addContainerGap(106, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
            .addGroup(execute_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(execute_panelLayout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(makeReport_checkbox)
                    .addContainerGap(59, Short.MAX_VALUE)))
        );

        side_panel.add(execute_panel);
        System.out.println(H);
        System.out.println(center_panel.getWidth());
        JTextArea ta = new JTextArea();
        ta.setBackground(Color.BLACK);
        ta.setForeground(Color.gray);
        //ta.setSize(W-100, H);
        ta.setPreferredSize(new java.awt.Dimension(center_panel.getPreferredSize().width, H));
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );
        JScrollPane consol = new JScrollPane( ta );

        consol.setPreferredSize(new java.awt.Dimension(center_panel.getPreferredSize().width, H));
        center_panel.add( consol ,java.awt.BorderLayout.CENTER);

	    add(side_panel, java.awt.BorderLayout.WEST);
	    add(center_panel, java.awt.BorderLayout.CENTER);
	}
    private void invokeActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Invoke inv = new Invoke();
        inv.start();
    }        
    // Variables declaration - do not modify                     
    private javax.swing.JSpinner chi_significe_spinner;
    private javax.swing.JCheckBox chisquared_checkbox;
    private javax.swing.JPanel chisquared_panel;
    private javax.swing.JPanel execute_panel;
    private javax.swing.JCheckBox expected_checkbox;
    private javax.swing.JCheckBox gainratio_checkbox;
    private javax.swing.JPanel gini_panel;
    private javax.swing.JCheckBox infogain_checkbox;
    private javax.swing.JPanel infogain_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox makeReport_checkbox;
    private javax.swing.JCheckBox obstable_checkbox;
    private javax.swing.JCheckBox t_test_checkbox;
    private javax.swing.JPanel ttest_panel;
    private javax.swing.JSpinner ttest_significe_spinner;
}
