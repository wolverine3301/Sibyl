package examples;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class LoadDataframe_Example {
    public static void main(String[] args) {
        DataFrame df = DataFrame_Read.loadcsv("testfiles/testing.csv");
        df.printAllData(true);
    }
}
