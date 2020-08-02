package logging;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class TextAreaHandler extends java.util.logging.Handler {

    private JTextArea textArea;
    private ArrayList<Color> colors = new ArrayList<Color>();
    private ArrayList<String> lines = new ArrayList<String>();
    @Override
    public void publish(final LogRecord record) {
        //SwingUtilities.invokeLater(new Runnable() {

            //@Override
           // public void run() {
                StringWriter text = new StringWriter();
                PrintWriter out = new PrintWriter(text);
                String line = String.format("[%s] [Thread-%d]: %s.%s -> %s", record.getLevel(),
                        record.getThreadID(), record.getSourceClassName(),
                        record.getSourceMethodName(), record.getMessage());
                lines.add(line);
                textArea.setText(text.toString());
                out.println(textArea.getText());
                if(record.getLevel() == Level.CONFIG) {
                	textArea.setForeground(Color.GREEN);
                }else if(record.getLevel() == Level.INFO) {
                	textArea.setForeground(Color.LIGHT_GRAY);
                }else if(record.getLevel() == Level.FINE) {
                	textArea.setForeground(Color.BLUE);
                }else if(record.getLevel() == Level.FINER) {
                	textArea.setForeground(Color.cyan);
                }
                colors.add(textArea.getForeground());
                for(int i = 0; i < lines.size();i++) {
                	textArea.add
                }
                out.printf("[%s] [Thread-%d]: %s.%s -> %s", record.getLevel(),
                        record.getThreadID(), record.getSourceClassName(),
                        record.getSourceMethodName(), record.getMessage());
                textArea.setText(text.toString());
           //}

       // });
    }
    public void setTextArea(JTextArea area) {
    	this.textArea = area;
    }
    public JTextArea getTextArea() {
        return this.textArea;
    }

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}

    //...
}
