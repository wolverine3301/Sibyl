package classificationExamples;

import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import dataframe.DataFrame_Read;
import dataframe.DataFrame_Utilities;
import dataframe.Row;
import distances.Euclidean;
import machinations.KNN;
import machinations.Model;

public class KNN_example {
    public static void main(String[] args) {
        DataFrame df = DataFrame_Read.loadcsv("testfiles/iris.txt");
        int[] arr = {1, 2, 3, 4};
        Row predict = DataFrame_Utilities.prepareForModel(df, 0, arr);
        KNN knn = new KNN(df, new Euclidean(), 3);
        Model.printProbablility(knn.probability(predict), predict);
    }
}
