package gui;

import java.awt.EventQueue;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class SibylGUIRunner {
    static DataFrame dataFrame;
    /**
     * Prevents the class from being instantiated.
     */
    private SibylGUIRunner() {
        throw new IllegalStateException();
    }
    
    /**
     * Runs the power paint gui.
     * @param theArgs allows aruments to be passed.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SibylGUI();
                //DataFrame df = DataFrame_Read.loadcsv("testfiles/testing.csv");
                //new SibylGUI(df).start();
            }
        });
    }

}
