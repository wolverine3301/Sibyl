package infoGainTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataframe.ColumnTools;
import dataframe.DataFrame;
import info_gain.InformationGain;

class infoGainTest {

    
    
    @Test
    void entropyTest() {
        DataFrame df = new DataFrame();
        df.loadcsv("testfiles/entropyTesting.csv");
        InformationGain info = new InformationGain(df);
        double entropy = info.entropy(ColumnTools.uniqueValCnt(df.getColumn_byIndex(0)));
        assertEquals(entropy, 0.9967916319816366);
    }
    
    @Test
    void calculateInfoGain() {
        DataFrame df = new DataFrame();
        df.loadcsv("testfiles/testing.csv");
        df.setColumnType(5, 'T');
        df.setColumnType(1, 'C');
        df.setColumnType(2, 'C');
        InformationGain info = new InformationGain(df);
        info.infoGain(0);
    }

}
