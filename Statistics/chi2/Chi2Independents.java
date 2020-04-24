package chi2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import dataframe.Column;
import dataframe.DataFrame;
import forensics.Gamma;

import java.util.Set;

/**
 * Chi^2 Independents
 *  The chi-squared test is used to determine whether there is a significant difference between 
 *  the expected frequencies and the observed frequencies in one or more categories.
 * @author logan.collier
 *
 */
public class Chi2Independents {
	
	private DataFrame df;
	private List<Column> targets;
	private HashMap<String ,HashMap<Object, HashMap<Object, Integer>>> obs_table; //observed value table ; key1(String) = "target x column" -> key2 = targetClass_n -
	private HashMap<String, HashMap<Object, HashMap<Object, Double>>> exp_table; //expected table
	private HashMap<Object, HashMap<Object, Integer>> deg_free; //degrees freedom
	private HashMap<String, HashMap<String, Double>> p_values; //p-values
	
	public final String description = "Chi Squared Test of Independents\n"
			+ "- This test determines if two categorical attributes are independent of each other\n"
			+ "- If the p-value is low the higher the chance that that these variables are related\n"
			+ "  to one another, or they are dependent\n";
			
	
	public Chi2Independents(DataFrame df){
		this.df = df;
		this.obs_table = new HashMap<String ,HashMap<Object, HashMap<Object, Integer>>>();
		this.exp_table = new HashMap<String, HashMap<Object, HashMap<Object, Double>>>();
		targets = new ArrayList<Column>();
		setTargets();
		chi2IndependentsAll();
	}
	public ArrayList<Column> ranked(int targetIndex){
		HashMap<String, HashMap<String, Double>> chi = chi2IndependentsAll();
		ArrayList<Column> rank = new ArrayList<Column>();
		for(String i : chi.keySet()) {
			for(String j : chi.get(i).keySet()) {
				rank.add(df.getColumn_byName(j));
			}
		}
		return rank;
	}
	/**
	 * calculate degrees of freedom
	 * @param col1
	 * @param col2
	 * @return degrees of freedom
	 */
	public int degreesFreedom(Column col1, Column col2) {
		return (col1.getTotalUniqueValues() - 1)* (col2.getTotalUniqueValues() - 1);
	}
	/**
	 * Perform a chi2 test on all columns on all targets
	 * @return HashMap<String, HashMap<String, Double>> key1 = target name -> HashMap<string,Double> => key2 = variable column -> p-value
	 * ranked by smallest p value (best)
	 */
	public HashMap<String, HashMap<String, Double>> chi2IndependentsAll(){
		HashMap<String, HashMap<String, Double>> ranks = new HashMap<String, HashMap<String, Double>>();
		HashMap<String, Double> tmp;
		for(Column target : targets) {
			tmp = new HashMap<String,Double>();
			for (int i = 0; i < df.getNumColumns(); i++) {
			    if (df.getColumn(i).getType() == 'T')
			        continue;
			    tmp.put(df.getColumn(i).getName(), chi2Independents(target, df.getColumn(i)));
			}
	        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double> >(tmp.entrySet()); 
	        
	        // Sort the list 
	        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() { 

				@Override
				public int compare(Entry<String, Double> o1, Entry<String, Double> o2) {
					// TODO Auto-generated method stub
					return (o1.getValue()).compareTo(o2.getValue()); 
				} 
	        });
	        // put data from sorted list to hashmap  
	        HashMap<String, Double> temp = new LinkedHashMap<String, Double>(); 
	        for (Map.Entry<String, Double> aa : list) { 
	            temp.put(aa.getKey(), aa.getValue()); 
	        }
			ranks.put(target.getName(), temp);		
		}
		this.p_values = ranks;
		return ranks;
	}
	/**
	 * Given 2 categorical variables ,this is used to test wheather there is a significant
	 * association between them, or rather test if they are independent of one another(the value
	 * of one variable does not have significant influence on the value of the other).
	 * formula: x^2 = rE_i=1 * cE_j=1 (O_i,j - e_i,j)^2 / e_i,j
	 * if you plot the same column against itself there would be no difference and x^2 would = 0
	 * @param col1
	 * @param col2
	 * @return p values
	 */
	public double chi2Independents(Column target,Column col) {
		double sum = 0;
		HashMap<Object, HashMap<Object, Integer>> observed = obs_contegencyTable(target,col);
		HashMap<Object, HashMap<Object, Double>> expected = exp_contegencyTable(target,col);
		for(Object key1 : observed.keySet()) {
			for(Object key2 : observed.get(key1).keySet()) {
				sum = sum + Math.pow((observed.get(key1).get(key2) - expected.get(key1).get(key2)), 2) / expected.get(key1).get(key2) ;
			}
			
		}
		return p_value(sum, degreesFreedom(target,col));
	}
	/**
	 * makes a contegency table of 2 columns expected values
	 * @param target
	 * @param col
	 * @return - HashMap<Object, HashMap<Object, Double>>
	 */
	public HashMap<Object, HashMap<Object, Double>> exp_contegencyTable(Column target, Column col){
		HashMap<Object, HashMap<Object, Double>> table = new HashMap<Object, HashMap<Object, Double>>();
		HashMap<Object, Integer> target_info = target.getUniqueValueCounts();
		HashMap<Object, Integer> col_info = col.getUniqueValueCounts();
		//Initialize and fill
		HashMap<Object, Double> vals;
		for(Object key1 : target_info.keySet()) {
			//values in column
			vals = new HashMap<Object,Double>();
			for(Object key2 : col_info.keySet()) {					
				vals.put(key2, ((target_info.get(key1).doubleValue() * col_info.get(key2).doubleValue())/target.getLength()));
			}
			table.put(key1,vals);
		}
		this.exp_table.put(target.getName().concat(" x ").concat(col.getName()),table);
		return table;
	}
	/**
	 * makes a contegency table of 2 columns expected values
	 * @param target -
	 * @param col - 
	 * @return - HashMap<Object, HashMap<Object, Integer>>
	 */
	public HashMap<Object, HashMap<Object, Integer>> obs_contegencyTable(Column target, Column col){
		HashMap<Object, HashMap<Object, Integer>> table = new HashMap<Object, HashMap<Object, Integer>>();
		Set<Object> target_info = target.getUniqueValues();
		Set<Object> col_info = col.getUniqueValues();
		
		//Initialize
		HashMap<Object, Integer> vals;
		for(Object key1 : target_info) {
			//values in column
			vals = new HashMap<Object,Integer>();
			for(Object key2 : col_info) {					
				vals.put(key2, 0);
			}
			table.put(key1,vals);
		}
		
		//fill
		for(int j = 0; j < target.getLength();j++) {
			try {
			table.get(target.getParticle(j).getValue()).replace(col.getParticle(j).getValue(), table.get(target.getParticle(j).getValue()).get(col.getParticle(j).getValue())+1);
		
			}catch (Exception e){
				System.out.println(e);
			}
		}
		if(col.getType() != 'N')
			this.obs_table.put(target.getName().concat(" x ").concat(col.getName()),table);
		return table;
	}
	/**
	 * get p-value of chi2 distribution
	 * @param chi2
	 * @param df
	 * @return double
	 */
	public double p_value(double chi2, int df) {
		//return (Math.pow(chi2, (df/2)-1) * Math.pow(Math.E,(-1)*(chi2/2))) / (Math.pow(2, (df/2)) * gamma((double)df/2));
		return Gamma.regularizedUpperIncompleteGamma(0.5 * df, 0.5 * chi2);
	}
	/**
	 * compute gamma function
	 * @param v
	 * @return
	 */
	public double gamma(double v) {
		double fac = 1;
		for(int i = 1; i < v;i++) {
			fac = fac * i;
		}
		return fac;
	}
	/**
	 * set target variables
	 */
	private void setTargets() {
		for(int i = 0; i < df.getNumColumns(); i++) {
			if(df.getColumn(i).getType() == 'T') {
				targets.add(df.getColumn(i));
			}
		}
	}
	public void printObsContengencyTables() {
		ArrayList<String> tableName = new ArrayList<String>();
		ArrayList<ArrayList<String>> headers = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<ArrayList<String>>> lines = new ArrayList<ArrayList<ArrayList<String>>>();
		for(String i : this.obs_table.keySet()) {
			tableName.add(i);
			ArrayList<String> header = new ArrayList<String>();
			ArrayList<ArrayList<String>> line = new ArrayList<ArrayList<String>>(); 
			for(Object j : this.obs_table.get(i).keySet()) {
				ArrayList<String> lin = new ArrayList<String>();
				lin.add(j.toString());
				for(Object k : this.obs_table.get(i).get(j).keySet()) {
					if(!header.contains(k.toString()))
						header.add(k.toString());
					lin.add(this.obs_table.get(i).get(j).get(k).toString());
				}
				line.add(lin);
			}
			headers.add(header);
			lines.add(line);
		}
		for(int i = 0; i < tableName.size(); i++) {
			System.out.println("OBSERVED: " + tableName.get(i));
			for(int j = 0; j < headers.get(i).size(); j++) {
				System.out.print("    "+headers.get(i).get(j));
			}
			for(int j = 0; j < lines.get(i).size(); j++) {
				System.out.println();
				for(int k = 0; k < lines.get(i).get(j).size(); k++) {
					System.out.print(lines.get(i).get(j).get(k)+ "  ");
				}		
			}
			System.out.println();
		}
	}
	public void printResults() {
		HashMap<String, HashMap<String, Double>> map = chi2IndependentsAll();
		for(String i : map.keySet()) {
			System.out.println(i);
			for(String j : map.get(i).keySet()) {
				System.out.println(j + " "+ map.get(i).get(j));
			}
		}
	}
	public void printEXPContengencyTables() {
		ArrayList<String> tableName = new ArrayList<String>();
		ArrayList<ArrayList<String>> headers = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<ArrayList<String>>> lines = new ArrayList<ArrayList<ArrayList<String>>>();
		for(String i : this.exp_table.keySet()) {
			tableName.add(i);
			ArrayList<String> header = new ArrayList<String>();
			ArrayList<ArrayList<String>> line = new ArrayList<ArrayList<String>>(); 
			for(Object j : this.exp_table.get(i).keySet()) {
				ArrayList<String> lin = new ArrayList<String>();
				lin.add(j.toString());
				for(Object k : this.exp_table.get(i).get(j).keySet()) {
					if(!header.contains(k.toString()))
						header.add(k.toString());
					lin.add(this.exp_table.get(i).get(j).get(k).toString());
				}
				line.add(lin);
			}
			headers.add(header);
			lines.add(line);
		}
		for(int i = 0; i < tableName.size(); i++) {
			System.out.println("EXPECTED: " + tableName.get(i));
			for(int j = 0; j < headers.get(i).size(); j++) {
				System.out.print("    "+headers.get(i).get(j));
			}
			for(int j = 0; j < lines.get(i).size(); j++) {
				System.out.println();
				for(int k = 0; k < lines.get(i).get(j).size(); k++) {
					System.out.print(lines.get(i).get(j).get(k)+ "  ");
				}		
			}
			System.out.println();
		}
	}
	
}
