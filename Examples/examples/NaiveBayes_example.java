package examples;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dataframe.Column;
import dataframe.DataFrame;
import machinations.NaiveBayes;
import particles.Particle;
import regressionFunctions.LinearRegression;
import regressionFunctions.LogRegression;
import scorer.CrossValidation;
import transform.Standardize;

public class NaiveBayes_example {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        df.convertNANS_mode();
        df.setColumnType(4, 'T');
        //df.getColumn_byName("species").setType('T'); //set target column
        System.out.println(df.numTargets);
        //System.out.println(df.getColumn_byName("species").getUniqueValues());
        //print column means
        for(Column i : df.columns) {
        	//System.out.println(i.mean);
        }
        
        //df.convertNANS_mean(); // conevert any NAN's to the mean of column 
        //df = Standardize.standardize_df(df); //Standardize the DF into z scores
        df = df.shuffle(df);
        NaiveBayes nb = new NaiveBayes();
        System.out.println(df.numTargets);
        //nb.initiallize();
        CrossValidation cv = new CrossValidation(df, 3, nb);
        //cv.printScores();
        
        cv.avgScores();
        cv.printOverAllScore();
        //cv.printMatrixs();
        //cv.printMatrixs();
        //cv.printScores();
        //nb.printProbTable();
        //nb.probabilityDF(df1.get(0));
        //nb.predictDF(df);
        //nb.probabilityDF(df1.get(1));
        
	}
}
