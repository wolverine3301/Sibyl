package bayes;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
	public void saveModel(String fileName) {
		
		try { 
            FileOutputStream fileOut = new FileOutputStream(fileName+".ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
	}
	
}
