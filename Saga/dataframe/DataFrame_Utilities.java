package dataframe;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
}
