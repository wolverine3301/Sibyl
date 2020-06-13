package logan.sybilGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Console {
    public static void main( String [] args ) throws InterruptedException  {
        JFrame frame = new JFrame();
        frame.add( new JLabel(" SYBIL " ), BorderLayout.NORTH );

        JTextArea ta = new JTextArea();
        ta.setBackground(Color.black);
        ta.setForeground(Color.gray);
        TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );


        frame.add( new JScrollPane( ta )  );

        frame.pack();
        frame.setVisible( true );
        frame.setSize(800,600);

        for( int i = 0 ; i < 100 ; i++ ) {
            System.out.println( i );
            Thread.sleep( 50 );
        }
    }
}
