package gui;

import dataframe.DataFrame;

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
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                dataFrame = new DataFrame();
//                String[] types = {"target","numeric","numeric","category","meta","meta"};
//                dataFrame.loadcsv("testfiles/testing.csv", types);
//                new SibylGUI2(dataFrame).start();
//            }
//        });
      dataFrame = new DataFrame();
      new FirstView().start();
    }

}
