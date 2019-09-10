package transmute;

import saga.Column;
import saga.DataFrame;
import saga.DoubleParticle;
import saga.Particle;

/**
 * Contains methods which transforms all numeric values in a dataframe to their log_10 representation or their log_e representation.
 * @author Cade Reynoldson
 * @version 1.0
 */
public class LogTransform {
    
    /**
     * Transforms all numeric values in a given data frame to their log_10 representation.
     * @param dataFrame the data frame to be transformed.
     * @return a data frame in which all numeric values are changed to their log_10 representation.
     */
    public static DataFrame transformBase10(DataFrame dataFrame) {
        for (int i = 0; i < dataFrame.numColumns; i++) {
            Column c = dataFrame.getColumn_byIndex(i);
            if (c.type == 'd') {
                for (int j = 0; j < dataFrame.numRows; j++) {
                    Particle p = new DoubleParticle(Math.log10((Double) c.getParticle_atIndex(j).value));
                    dataFrame.replace(i, j, p);
                }
            } else if (c.type == 'i') {
                for (int j = 0; j < dataFrame.numRows; j++) {
                    int integer = (Integer) c.getParticle_atIndex(j).value;
                    Particle p = new DoubleParticle(Math.log10((double) integer));
                    dataFrame.replace(i, j, p);
                }
                c.type = 'd';
            }
        }
        return dataFrame;
    }
    
    /**
     * Transforms all numeric values in a given data frame to their log_e representation.
     * @param dataFrame the data frame to be transformed.
     * @return a data frame in which all numeric values are changed to their log_e representation.
     */
    public static DataFrame transformNaturalLog(DataFrame dataFrame) {
        for (int i = 0; i < dataFrame.numColumns; i++) {
            Column c = dataFrame.getColumn_byIndex(i);
            if (c.type == 'd') {
                for (int j = 0; j < dataFrame.numRows; j++) {
                    Particle p = new DoubleParticle(Math.log((Double) c.getParticle_atIndex(j).value));
                    dataFrame.replace(i, j, p);
                }
            } else if (c.type == 'i') {
                for (int j = 0; j < dataFrame.numRows; j++) {
                    int integer = (Integer) c.getParticle_atIndex(j).value;
                    Particle p = new DoubleParticle(Math.log((double) integer));
                    dataFrame.replace(i, j, p);
                }
                c.type = 'd';
            }
        }
        return dataFrame;
    }
}
