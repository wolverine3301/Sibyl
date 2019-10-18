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
		//String file = "testfiles/iris.txt";
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        for(Column i : df.columns) {
        	System.out.println(i.mean);
        }
       // df.printDataFrame();
        df.convertNANS_mean();
        df = Standardize.standardize_df(df);
        
       // df.printDataFrame();
       // df = df.shuffle(df);
        //NaiveBayes nb = new NaiveBayes();
        //CrossValidation cv = new CrossValidation(df, 10, nb);
        //cv.avgScores();
        //cv.printMatrixs();
        //cv.printScores();
        //nb.printProbTable();
        //nb.probabilityDF(df1.get(0));
        //nb.predictDF(df);
        //nb.probabilityDF(df1.get(1));
        
	}
}
