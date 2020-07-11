package classificationExamples;

import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import dataframe.DataFrame_Read;
import dataframe.DataFrame_Utilities;
import dataframe.Row;
import distances.Euclidean;
import knn.KNN;
import machinations.Model;

public class KNN_example {
    public static void main(String[] args) {
        DataFrame df = DataFrame_Read.loadcsv("testfiles/iris.txt");
        df.printDataFrame();
        int[] arr = {1, 2, 3, 4};
        Row predict = DataFrame_Utilities.prepareForModel(df, 0, arr);
        KNN knn = new KNN();
        knn.train(df);
        knn.initiallize();
        Model.printProbablility(knn.probability(predict), predict);
        String i = "Hello";
        System.out.println(i.toString());
    }
}
