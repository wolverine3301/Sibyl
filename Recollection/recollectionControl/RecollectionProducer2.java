package recollectionControl;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

import dataframe.DataFrame;
import machinations.Model;
import scorer.CrossValidation;

public class RecollectionProducer2 implements Runnable {

	private final BlockingQueue<CrossValidation> EVALUATION_QUEUE;
	private final ArrayList<DataFrame> memories;
	private final Model model;
	private boolean EvalOn;
	public RecollectionProducer2(BlockingQueue<CrossValidation> blockingQueue,boolean eval,ArrayList<DataFrame> memories,Model model) {
		this.EVALUATION_QUEUE = blockingQueue;
		this.EvalOn = eval;
		this.memories = memories;
		this.model = model;
	}

	public void run() {
		for (DataFrame df : memories) {
			try {
                // to insert the jobs in the list 
                EVALUATION_QUEUE.put(new CrossValidation(df, 5,model.copy())); 
			} catch (InterruptedException ex) {
				System.out.println("Recollection Producer thread interrupted.");
			}
		}
		EvalOn = false;
		System.out.println("DONE PRODUCING");
		//notify();
	}

}
