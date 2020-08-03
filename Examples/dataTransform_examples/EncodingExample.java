package dataTransform_examples;

import dataframe.DataFrame;
import transform.OneHotEncoder;

public class EncodingExample {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        df = OneHotEncoder.encode(df);

	}

}
