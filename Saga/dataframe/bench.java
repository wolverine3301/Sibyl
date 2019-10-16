package dataframe;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import machinations.NaiveBayes;
import particles.Particle;
import regressionFunctions.LinearRegression;
import regressionFunctions.LogRegression;
import scorer.CrossValidation;
import transform.Standardize;

public class bench {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String file = "testfiles/iris.txt";
        DataFrame df = new DataFrame();
        df.loadcsv(file);
        //df.getColumn_byName("Name").setType('M');
        Standardize s = new Standardize(df);
        s.standardize_df();
        //s.std_df.printDataFrame();
        LogRegression l = new LogRegression(df.getColumn(0), df.getColumn(1));

        df.getColumn(4).setType('T');

        ArrayList<DataFrame>df1 = DataFrameTools.split(df, 2);
        for(int i = 0; i < df.getNumColumns();i++) {
        	//System.out.println(df.getColumn_byIndex(i).name + "  " +df.getColumn_byIndex(i).mean);
        	//df1.get(i).printDataFrame();
        	//df1.get(i).printDataFrame();
        }
        df = df.shuffle(df);
       // df.printDataFrame();
        List<Integer> ll = new ArrayList<Integer>();
        
        ll.add(4);
        //df1.set(1, df1.get(1).exclude(df1.get(1), ll));
        
        NaiveBayes nb = new NaiveBayes();
        CrossValidation cv = new CrossValidation(df, 10, nb);
        cv.avgScores();
        //cv.printMatrixs();
        //cv.printScores();
        //nb.printProbTable();
        //nb.probabilityDF(df1.get(0));
        //nb.predictDF(df);
        //nb.probabilityDF(df1.get(1));
        
	}
}
