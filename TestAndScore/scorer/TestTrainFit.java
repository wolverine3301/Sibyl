package scorer;

import java.util.ArrayList;
import java.util.TreeSet;

import dataframe.DataFrame;
import dataframe.DataFrame_Copy;

public class TestTrainFit {
	
	DataFrame raw_train;
	DataFrame raw_test;
	DataFrame trial_test_variables; // just variables no target columns
	DataFrame trial_test_targets; //just target columns
	
	DataFrame trial_train_variables; //just variables
	DataFrame trial_train_targets;
	//just set training df
	public TestTrainFit(DataFrame train_df) {
		this.raw_train = train_df;
		trial_train_variables = new DataFrame();
		trial_train_targets = new DataFrame();
		setTrain();
	}
	public TestTrainFit(DataFrame train_df, DataFrame test_df) {
		this.raw_train = train_df;
		this.raw_test = test_df;
		trial_test_variables = new DataFrame();
		trial_test_targets = new DataFrame();
		trial_train_variables = new DataFrame();
		trial_train_targets = new DataFrame();
		setTrain();
		setTest();
	}
	public void setTest() {
		this.trial_test_targets = DataFrame_Copy.shallowCopy_columnTypes(this.raw_test, set_targets());
	    this.trial_test_variables = DataFrame_Copy.shallowCopy_columnTypes(this.raw_test, set_variables());
	}
	public void setTrain() {
		this.trial_train_targets = DataFrame_Copy.shallowCopy_columnTypes(this.raw_train, set_targets());
	    this.trial_train_variables = DataFrame_Copy.shallowCopy_columnTypes(this.raw_train, set_variables());
	}
	
	/**
	 * Sets the targets list.
	 * @return a tree set of targets to predict.
	 */
	private TreeSet<Character> set_targets() {
		TreeSet<Character> target = new TreeSet<Character>();
		target.add('T');
		return target;
	}
	/**
	 * Sets the variables list.
	 * @return a tree set of the variables used to train data.
	 */
	private TreeSet<Character> set_variables() {
		TreeSet<Character> vars = new TreeSet<Character>();
		vars.add('C');
		vars.add('G');
		vars.add('O');
		vars.add('N');
		return vars;
	}

}
