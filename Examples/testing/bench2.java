package testing;

import java.util.ArrayList;

import dataframe.Column;
import dataframe.DataFrame;
import info_gain.Gain;
import info_gain.GiniIndex;

public class bench2 {
    public static void main(String[] args) {
        DataFrame df = DataFrame.read_csv("testfiles/testing.csv");
        df.setStatistics();
        for (int i = 0; i < df.getNumColumns(); i++) {
            System.out.println(df.getColumn(i).toStringStatistics());
        }
//        DataFrame df2 = df.shallowCopy();
//        System.out.println("\n\n\n::::COPY TEST::::\n\n\n");
//        for (int i = 0; i < df.getNumColumns(); i++) {
//            System.out.println(df2.getColumn(i).toStringStatistics());
//        }
        df.printDataFrame();
        System.out.println(df.getColumn(0));
        df.setColumnType(0, 'C');
        df.setColumnType(1, 'C');
        df.setColumnType(2, 'C');
        Gain info = new GiniIndex(df);
        ArrayList<Column> giniShit = info.gain(0);
        System.out.println(giniShit);
        //info.entropy(df.getColumn_byIndex(0).getFeatureStats());
        //System.out.println(info.gain(0));
        
//        df.getColumn_byIndex(3).setType('T');
//        Model knn = new KNN(df, new Manhattan(), 2);
//        Row testRow = new Row();
//        testRow.addToRow(Particle.resolveType("T"));
//        testRow.addToRow(Particle.resolveType(44));
//        testRow.addToRow(Particle.resolveType(6.666));
//        testRow.addToRow(Particle.resolveType("FAT BOI"));
//        testRow.addToRow(Particle.resolveType("5"));
//        ArrayList<ArrayList<Particle>> pred = knn.predict(testRow);
//        for (int i = 0; i < pred.size(); i++) {
//            System.out.println("Predictions: " + pred.get(i).toString());
//        }
//        HashMap<Object, Double> map = knn.probability(testRow);
//        System.out.println("PREDICTIONS FOR ROW: " + testRow.toString());
//        System.out.println(map.toString());
//        KMeans kmeans = new KMeans(2, new Euclidean(), df);
//        ArrayList<Set<Row>> cluster = kmeans.clusterOptimized();
//        int count = 1;
//        for (Set<Row> s : cluster) {
//            System.out.println("CLUSTER " + count + ":");
//            for (Row r : s) {
//                System.out.println(r.toString());
//            }
//            count++;
//            System.out.println();
//        }
//        Hierarchical h = new Hierarchical(df, new Euclidean());
//        ArrayList<ArrayList<Cluster>> c = h.cluster(2);
//        for (int i = 0; i < c.size(); i++) {
//            System.out.println("\n--------\nCLUSTER LEVEL " + i + ": \n" );
//            for (int j = 0; j < c.get(i).size(); j++) {
//                System.out.println(c.get(i).get(j));
//            }
//        }
    }
}
