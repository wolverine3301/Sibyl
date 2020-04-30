package examples;

import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import dataframe.DataFrame_Read;
import dataframe.Row;
import distances.Euclidean;
import machinations.KNN;

public class KNN_example {
    public static void main(String[] args) {
        DataFrame df = DataFrame_Read.loadcsv("testfiles/iris.txt");
        //df.printDataFrame();
        df.convertNANS_mode();
        df.getColumn_byName("species").setType('T'); //set target column
        df.getRow_byIndex(0);
        Row r = new Row(df.getRow_byIndex(0)); //Row which is setosa.
        r.removeParticle(r.getLength());
        KNN knn = new KNN(df, new Euclidean(), 5);
    }
}
