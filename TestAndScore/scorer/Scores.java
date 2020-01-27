package scorer;

public class Scores {
	/**
	 * Returns Matthews Correlation coefficient 
	 * @param tp - count of true positives
	 * @param tn - count of true negatives
	 * @param fp - count of false positives
	 * @param fn - count of false negatives
	 * @return MCC
	 */
	public static double mcc(int tp, int tn, int fp, int fn) {
		return ( ( (tp * tn) - (fp * fn) ) / (Math.sqrt( (tp + fp)*(tp+fn)*(tn+fp)*(tn+fn) ) ) );	
	}
	/**
	 * Returns F1 score 
	 * @param tp - count of true positives
	 * @param fp - count of false positives
	 * @param fn - count of false negatives
	 * @return F1
	 */
	public static double F1(int tp, int fp, int fn) {
		return(2* (precision(tp,fp) * recall(tp,fn)) / (precision(tp,fp) + recall(tp,fn)));	
	}
	/**
	 * Returns precision of a given class
	 * @param tp - true positives
	 * @param fp - false positives
	 * @return precision
	 */
	public static double precision(int tp, int fp) {
		if(fp == 0 && tp == 0) {	
			return 0;
		}
		else if(fp == 0 && tp != 0) {
			return 1;
		}
		return ((double)tp/(tp+fp));
	}
	/**
	 * Returns recall
	 * @param tp - true positives
	 * @param fn - false negatives
	 * @return recall
	 */
	public static double recall(int tp, int fn) {
		if(tp == 0 && fn ==0) {
			return -1;
		}

		return (tp/(tp+fn));	
	}
	/**
	 * accuracy
	 * @param tp
	 * @param fp
	 * @param tn
	 * @param fn
	 * @return
	 */
	public double accuracy(int tp,int fp,int tn, int fn) {
		return (tp+tn)/(tp+fp+fn+tn);
	}
	/**
	 * False omission rate
	 * @param fn
	 * @param tn
	 * @return
	 */
	public double FOR(int fn, int tn) {
		return fn/(fn+tn);
	}
	/**
	 * false discovery rate
	 * @param fp
	 * @param tp
	 * @return
	 */
	public double FDR(int fp,int tp) {
		return fp / (fp+tp);
	}
	/**
	 * False negative rate
	 * @param fp
	 * @param tp
	 * @return
	 */
	public double FNR(int fn,int tp) {
		return fn / (fn+tp);
	}
	/**
	 * False Positive rate
	 * @return
	 */
	public double FPR(int fp, int tn,int fn) {
		return fp / ( tn + fn);
		
	}
	/**
	 * negative prediction value
	 * @param tn
	 * @param fn
	 * @return
	 */
	public double NPV(int tn, int fn) {
		return tn / (tn+fn);
	}
	/**
	 * positive_likelihood_ratio
	 * @return
	 */
	public double PLR(int tp, int fn) {
		return recall(tp,fn);
		
	}
}
