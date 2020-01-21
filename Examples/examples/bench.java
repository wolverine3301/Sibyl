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

public class bench {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		String file = "testfiles/allfraud.csv";
        DataFrame df = DataFrame.read_csv(file);
        df.convertNANS_mode();
        df.getColumn_byName("defaulted").setType('T'); //set target column
        df.getColumn_byName("trackingNumber").setType('M');
        df.getColumn_byName("DebtorClientId").setType('M');
        df.getColumn_byName("Id").setType('M');
        df.getColumn_byName("inquiryInquiryReceivedAt").setType('M');
        df.getColumn_byName("fullName").setType('M');
        df.getColumn_byName("overallMatchResult").setType('M');
        df.getColumn_byName("ssnNameMatch").setType('M');
        df.getColumn_byName("nameAddressMatch").setType('M');
        df.getColumn_byName("ssnDobMatch").setType('M');
        df.getColumn_byName("nameAddressReasonCode").setType('M');
        //df.getColumn_byName("ssnDobMatch").setType('M');
        //df.getColumn_byName("ssnDobMatch").setType('M');
        System.out.println(df.getColumn_byName("defaulted").getUniqueValues());
        //print column means
        for(Column i : df.columns) {
        	System.out.println(i.mean);
        }
        
        df.convertNANS_mean(); // conevert any NAN's to the mean of column 
        df = Standardize.standardize_df(df); //Standardize the DF into z scores
        df = df.shuffle(df);
        NaiveBayes nb = new NaiveBayes();
        nb.initiallize();
        CrossValidation cv = new CrossValidation(df, 2, nb);
        cv.avgScores();
        //cv.printMatrixs();
        //cv.printScores();
        //nb.printProbTable();
        //nb.probabilityDF(df1.get(0));
        //nb.predictDF(df);
        //nb.probabilityDF(df1.get(1));
        
	}
}
