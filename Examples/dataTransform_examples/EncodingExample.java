package dataTransform_examples;

import Encoders.OneHotEncoder;
import dataframe.DataFrame;

public class EncodingExample {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        df = OneHotEncoder.encode(df);
        //this is suppose to remove species and replace it with 3 columns for the values in species
        //it seems to do so but some refrences are not being updated
        df.printColumnNameTypes();
        System.out.println(df.columnNamesToString());
        System.out.println(df.numCategorical);
	}

}
