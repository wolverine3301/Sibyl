package bayes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class NaiveBayes_A implements java.io.Serializable{
	
	private HashMap<String , HashMap<Object, HashMap<String, Double[]>>> cont_Naive_Bayes;
	private HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>> cat_Naive_Bayes;

	public void setCont_Naive_Bayes(HashMap<String, HashMap<Object, HashMap<String, Double[]>>> cont_Naive_Bayes) {
		this.cont_Naive_Bayes = cont_Naive_Bayes;
	}
	public void setCat_Naive_Bayes(
			HashMap<String, HashMap<Object, HashMap<String, HashMap<Object, Double>>>> cat_Naive_Bayes) {
		this.cat_Naive_Bayes = cat_Naive_Bayes;
	}
	/**
	 * save model object
	 * @param fileName
	 */
	public void saveModel(String fileName) {
		try { 
            FileOutputStream fileOut = new FileOutputStream(fileName+".ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
            System.out.println(fileOut.getFD());
            fileOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
	public void save_cat() {
		try { 
            FileOutputStream fileOut = new FileOutputStream("CAT.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cat_Naive_Bayes);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
            System.out.println(fileOut.getFD());
            fileOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	
	}
	public void save_cont() {
		try { 
            FileOutputStream fileOut = new FileOutputStream("CONT.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(cont_Naive_Bayes);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
            System.out.println(fileOut.getFD());
            fileOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	
	}
	
	
	/**
	 * load a model file
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static NaiveBayes_A loadModel(String filePath) throws IOException {
	    NaiveBayes_A nb = new NaiveBayes_A();
        FileInputStream fileIn = new FileInputStream(filePath);
        ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(fileIn);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			nb = (NaiveBayes_A) in.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         System.out.println(nb.cat_Naive_Bayes);
         System.out.println(nb.cont_Naive_Bayes);
         in.close();
         fileIn.close();
         return nb;
		}
	
	
}
