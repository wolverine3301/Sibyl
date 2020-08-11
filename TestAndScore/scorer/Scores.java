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
		if(tp == 0  && fp == 0 && fn == 0) {
			return 0;
		}else if(fp == 0 && fn ==0) {
			return 1;
		}else if((fn != 0 || fp != 0)&& (tp == 0 || tn == 0)) {
			return -1;
		}
		double dtp = tp;
		double dtn = tn;
		double dfp = fp;
		double dfn = fn;
		return ( ( (dtp * dtn) - (dfp * dfn) ) / (Math.sqrt( ( (dtp + dfp)*(dtp+dfn)*(dtn+dfp)*(dtn+dfn) ) )) );	
	}
	/**
	 * Returns F1 score 
	 * if all are 0 returns 0, if no true+ and only false+ OR false- returns -1(meaning it got all of them wrong)
	 * @param tp - count of true positives
	 * @param fp - count of false positives
	 * @param fn - count of false negatives
	 * @return F1
	 */
	public static double F1(int tp, int fp, int fn) {
		if(tp == 0  && fp == 0 && fn == 0) {
			return 0;
		}else if(tp==0 && (fp != 0 || fn !=0)) {
			return -1;
		}
		return(2* (precision(tp,fp) * recall(tp,fn)) / (precision(tp,fp) + recall(tp,fn)));	
	}
	/**
	 * Returns precision of a given class, if there are no true positives or negatives returns 0
	 * if only true+ and no false+ returns 1, if only false+ return -1
	 * @param tp - true positives
	 * @param fp - false positives
	 * @return precision
	 */
	public static double precision(int tp, int fp) {
		double dtp = tp;
		double dfp = fp;
		if(dfp == 0 && dtp == 0) {	
			return 0;
		}
		else if(dfp == 0 && dtp != 0) {
			return 1;
		}else if( dtp == 0 && dfp != 0) {
			return -1;
		}
		return (dtp/(dtp+dfp));
	}
	/**
	 * Returns recall
	 * @param tp - true positives
	 * @param fn - false negatives
	 * @return recall
	 */
	public static double recall(int tp, int fn) {
		double dtp = tp;
		double dfn = fn;
		if(dtp == 0 && dfn ==0) {
			return 0;
		}
		return (dtp/(dtp+dfn));	
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
		return (double)(tp+tn)/(tp+fp+fn+tn);
	}
	/**
	 * False omission rate
	 * @param fn
	 * @param tn
	 * @return
	 */
	public double FOR(int fn, int tn) {
		return (double)fn/(fn+tn);
	}
	/**
	 * false discovery rate
	 * @param fp
	 * @param tp
	 * @return
	 */
	public double FDR(int fp,int tp) {
		return (double)fp / (fp+tp);
	}
	/**
	 * False negative rate
	 * @param fp
	 * @param tp
	 * @return
	 */
	public double FNR(int fn,int tp) {
		return (double)fn / (fn+tp);
	}
	/**
	 * False Positive rate
	 * @return
	 */
	public double FPR(int fp, int tn,int fn) {
		return (double)fp / ( tn + fn);
		
	}
	/**
	 * negative prediction value
	 * @param tn
	 * @param fn
	 * @return
	 */
	public double NPV(int tn, int fn) {
		return (double)tn / (tn+fn);
	}
	/**
	 * positive_likelihood_ratio
	 * @return
	 */
	public double PLR(int tp, int fn) {
		return recall(tp,fn);
		
	}
}
