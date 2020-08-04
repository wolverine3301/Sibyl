package logging;

import java.util.logging.LogRecord;

import javax.swing.JTextPane;

public class TextPaneHandler extends java.util.logging.Handler{
	private JTextPane pane;
	StringBuilder builder = new StringBuilder();
	public void setTextPane(JTextPane pan) {
		this.pane=pan;
		pane.setContentType("text/html");
	}
	@Override
	public void publish(LogRecord record) {
		
		//super.getFormatter().format(record);
		String formated_record = super.getFormatter().format(record);
		builder.append(formated_record);
		//builder.append("<html>" 
		//		+ formated_record + "</html>");
		
		//pane.setText(builder.toString());
		pane.setText(formated_record);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() throws SecurityException {
		// TODO Auto-generated method stub
		
	}

}
