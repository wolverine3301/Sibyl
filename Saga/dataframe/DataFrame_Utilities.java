package dataframe;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;

import log.Loggers;
/**
 * Contains various tools for manipulating dataframes.
 * @author Cade Reynoldson
 * @author Logan Collier
 */
public class DataFrame_Utilities {
    
    /**
     * Prepares a dataframe for a model. Returns a row which will have target indexes chopped out.
     * Removes row from dataframe, marks columns as targets. 
     * @param df the dataframe to prepare for a model. 
     * @param rowToPredict the index of the row to predict. 
     * @param targetIndexes the indexes of the columns to mark as targets.
     * @return the row to predict which will have the target indexes chopped out. 
     */
    public static Row prepareForModel(DataFrame df, int rowToPredict, int[] targetIndexes) {
        Row r = df.getRow_byIndex(rowToPredict);
        df.removeRow(rowToPredict);
        Arrays.sort(targetIndexes);
        for (int i = targetIndexes.length - 1; i >= 0; i--) {
            r.removeParticle(targetIndexes[i]);
            df.setColumnType(targetIndexes[i], 'T');
        }
        return r; 
    }
    
    /**
     * Prepares a dataframe for a model. Returns a row which will have target indexes chopped out.
     * Removes row from dataframe, marks columns as targets. 
     * @param df the dataframe to prepare for a model.
     * @param rowToPredict the index of the row to predict. 
     * @param targets currently only supports a collection of string names, collection of target indexes.
     * @return the row to predict which will have the target indexes chopped out. 
     */
    public static Row prepareForModel(DataFrame df, int rowToPredict, Collection<Object> targets) {
        int[] arr = new int[targets.size()];
        int counter = 0;
        for (Object o : targets) {
            if (o instanceof String) {
                arr[counter++] = df.getColumnIndex((String) o);
            } else if (o instanceof Integer) {
                arr[counter++] = (Integer) o;
            }
            
        }
        return prepareForModel(df, rowToPredict, arr);
    }
    
    /**
     * Prepares a dataframe for a model. Returns a row which will have the index of the targets name chopped out.
     * @param df the dataframe to prepare for a model.
     * @param rowToPredict the index of the row to predict. 
     * @param targetName the name of the column to set as a target. 
     * @return the row to predict which will have the target index chopped out. 
     */
    public static Row prepareForModel(DataFrame df, int rowToPredict, String targetName) {
        Row r = df.getRow_byIndex(rowToPredict);
        df.removeRow(rowToPredict);
        int index = df.getColumnIndex(targetName);
        r.removeParticle(index);
        df.setColumnType(index, 'T');
        return r;
    }
    
    /**
     * Prepares the dataframe for a model. Returns a row which will have the index of the target chopped out. 
     * @param df the dataframe to prepare for a model.
     * @param rowToPredict the index of the row to predict. 
     * @param targetIndex the index of the column to mark as a target. 
     * @return the row to predict which will have the target index chopped out. 
     */
    public static Row prepareForModel(DataFrame df, int rowToPredict, int targetIndex) {
        Row r = df.getRow_byIndex(rowToPredict);
        df.removeRow(rowToPredict);
        r.removeParticle(targetIndex);
        df.setColumnType(targetIndex, 'T');
        return r; 
    }
    
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
		    	Loggers.df_Logger.log(Level.SEVERE,"FAILED TO LOAD DATAFRAME FROM FILE: "+c);
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
            Loggers.df_Logger.log(Level.CONFIG,"Object  was succesfully written to a file: "+name+".ser");
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
