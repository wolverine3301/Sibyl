package main_gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class CustomCheckBox extends JComboBox{

    public CustomCheckBox() { addStuff(); }
    public CustomCheckBox (JCheckBox[] items) { super(items); addStuff(); }
    public CustomCheckBox(Vector items) { super(items); addStuff(); }
    public CustomCheckBox(ComboBoxModel aModel) { super(aModel); addStuff(); }
    private void addStuff() {
      setRenderer((ListCellRenderer) new ComboBoxRenderer());
      addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent ae) { itemSelected(); }
      });
    }
    private void itemSelected() {
      if (getSelectedItem() instanceof JCheckBox) {
        JCheckBox jcb = (JCheckBox)getSelectedItem();
        jcb.setSelected(!jcb.isSelected());
      }
    }
    class ComboBoxRenderer implements ListCellRenderer {
      private JLabel defaultLabel;
      public ComboBoxRenderer() { setOpaque(true); }
      public Component getListCellRendererComponent(JList list, Object value, int index,
                  boolean isSelected, boolean cellHasFocus) {
        if (value instanceof Component) {
          Component c = (Component)value;
          if (isSelected) {
            c.setBackground(list.getSelectionBackground());
            c.setForeground(list.getSelectionForeground());
          } else {
            c.setBackground(list.getBackground());
            c.setForeground(list.getForeground());
          }
          return c;
        } else {
          if (defaultLabel==null) defaultLabel = new JLabel(value.toString());
          else defaultLabel.setText(value.toString());
          return defaultLabel;
        }
      }
    }
}
