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
import transform.Standardize;

public class bench {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		String file = "testfiles/iris.txt";
        DataFrame df = new DataFrame();
        df.loadcsv(file);
        df.getColumn_byName("Name").setType('M');
        Standardize s = new Standardize(df);
        s.standardize_df();
        //s.std_df.printDataFrame();
        LogRegression l = new LogRegression(df.getColumn_byIndex(0), df.getColumn_byIndex(1));

        df.getColumn_byIndex(4).setType('T');
        
        df.setStuff();

        ArrayList<DataFrame>df1 = DataFrameTools.split(df, 2);
        for(int i = 0; i < df1.size();i++) {
        	df1.get(i).setStuff();
        	//df1.get(i).printDataFrame();
        	//df1.get(i).printDataFrame();
        }
        List<Integer> ll = new ArrayList<Integer>();
        
        ll.add(4);
        df1.set(1, df1.get(1).exclude(df1.get(1), ll));
        for(int i = 0; i < df1.get(1).getNumColumns(); i++) {
        	System.out.println(df1.get(1).getColumn_byIndex(i).mean);
        }
        
        NaiveBayes nb = new NaiveBayes(df1.get(0));
        nb.printProbTable();
        //nb.probabilityDF(df1.get(1));
        
	}
}
