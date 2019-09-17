package testing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.Row;
import distances.Distance;
import distances.Euclidean;
import distances.Manhattan;
import machinations.KNN;
import machinations.Model;
import metamorphose.Cluster;
import metamorphose.Hierarchical;
import metamorphose.KMeans;
import particles.DoubleParticle;
import particles.Particle;
import transform.LogTransform;

public class bench2 {
    public static void main(String[] args) {
        DataFrame df = new DataFrame();
        df.loadcsv("testfiles/distancetesting.csv");
//        df.getColumn_byIndex(3).setType("target");
//        Model knn = new KNN(df, new Manhattan(), 6);
//        Row testRow = new Row();
//        testRow.addToRow(Particle.resolveType("T"));
//        testRow.addToRow(Particle.resolveType(44));
//        testRow.addToRow(Particle.resolveType(6.666));
//        testRow.addToRow(Particle.resolveType("FAT BOI"));
//        testRow.addToRow(Particle.resolveType("5"));
//        HashMap<Object, Double> map = knn.probability(testRow);
//        System.out.println("PREDICTIONS FOR ROW: " + testRow.toString());
//        System.out.println(map.toString());
        KMeans kmeans = new KMeans(2, new Euclidean(), df);
        ArrayList<Set<Row>> cluster = kmeans.clusterOptimized();
        int count = 1;
        for (Set<Row> s : cluster) {
            System.out.println("CLUSTER " + count + ":");
            for (Row r : s) {
                System.out.println(r.toString());
            }
            count++;
            System.out.println();
        }
        Hierarchical h = new Hierarchical(df, new Euclidean());
        ArrayList<ArrayList<Cluster>> c = h.cluster(2);
        for (int i = 0; i < c.size(); i++) {
            System.out.println("\n--------\nCLUSTER LEVEL " + i + ": \n" );
            for (int j = 0; j < c.get(i).size(); j++) {
                System.out.println(c.get(i).get(j));
            }
        }
            
    }
}
