package examples;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class KNN_example {
    public static void main(String[] args) {
        DataFrame df = DataFrame_Read.loadcsv("testfiles/iris.txt");
        df.printDataFrame();
    }
}
