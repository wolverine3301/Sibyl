package dataframe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class DataFrame_Utilities {
	/**
	 * load dataframe from serial object
	 * @param fileName
	 * @return
	 */
	public static DataFrame loadDataFrame(String fileName) {
		DataFrame df;
		try {
		       FileInputStream fileIn = new FileInputStream(fileName);
		       ObjectInputStream in = new ObjectInputStream(fileIn);
		       df = (DataFrame) in.readObject();
		       in.close();
		       fileIn.close();
		    } catch (IOException i) {
		       i.printStackTrace();
		       return null;
		    } catch (ClassNotFoundException c) {
		       System.out.println("DataFrame not found");
		       c.printStackTrace();
		       return null;
		    }
		    return df;
		}
	
	/**
	 * save the dataframe to a serial object
	 * @param df
	 */
	public static void saveDataFrame(DataFrame df,String name) {
		try { 
            FileOutputStream fileOut = new FileOutputStream(name+".ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(df);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	public static void to_csv(DataFrame df, String filename) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(filename+".csv", "UTF-8");
        for(int i = 0; i < df.columnNames.size(); i++) {
        	writer.print(df.columnNames.get(i) + ",");
        }
        writer.println();
        for(int z = 0 ; z < df.numRows; z++) {
        	writer.println(df.rows.get(z).toString());
        }
        writer.close();
	}
}
