package examples;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import machinations.KNN;

public class KNN_example {
    public static void main(String[] args) {
        DataFrame df = DataFrame_Read.loadcsv("testfiles/iris.txt");
        //df.printDataFrame();
        df.convertNANS_mode();
        df.getColumn_byName("species").setType('T'); //set target column
        
    }
}
