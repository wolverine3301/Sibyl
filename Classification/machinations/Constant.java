package machinations;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.Row;

/**
 * simple classifier that picks the most common occurance of each target class
 * @author logan
 *
 */
public class Constant extends Model{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<String,Object> constants;
	
	public Constant() {
		constants = new HashMap<String,Object>();
	}
	public Constant(DataFrame df) {
		constants = new HashMap<String,Object>();
	}
	
	@Override
	public HashMap<String, HashMap<Object, Double>> probability(Row row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object predict(String target,Row row) {
		return constants.get(target);
	}

	@Override
	public HashMap<String, ArrayList<Object>> predictDF(DataFrame testDF) {
		HashMap<String, ArrayList<Object>> preds = new HashMap<String, ArrayList<Object>>();
		ArrayList<Object> p;
		for(Column i : super.rawTrain.target_columns) {
			p = new ArrayList<Object>();
			for(int j = 0; j < testDF.getNumRows(); j++) {
				p.add(predict(i.getName(),testDF.getRow_byIndex(j)));
			}
			preds.put(i.getName(), p);
		}
		return preds;
	}

	@Override
	public void initiallize() {
		for(Column i : super.rawTrain.target_columns) {
			constants.put(i.getName(), i.mode);
		}
		
	}

	@Override
	public void saveModel(String fileName) {
		// TODO Auto-generated method stub
		
	}
    @Override
    public Model copy() {
        // TODO Auto-generated method stub
        return null;
    }

}
