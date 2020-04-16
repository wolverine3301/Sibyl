package plotting;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Shuts down the JVM when the demo window is closed.
 */
public class ExitOnClose extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
    
}
