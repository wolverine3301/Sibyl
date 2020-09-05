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
	
	ReleaseRecollection2 reco_parent;
	public RecollectionProducer2(BlockingQueue<CrossValidation> blockingQueue,ArrayList<DataFrame> memories,Model model,ReleaseRecollection2 reco) {
		this.EVALUATION_QUEUE = blockingQueue;
		this.memories = memories;
		this.model = model;
		this.reco_parent = reco;
	}

	public void run() {
		for (DataFrame df : memories) {
			try {
                // to insert the jobs in the list 
                EVALUATION_QUEUE.put(new CrossValidation(df, 5,model.copy())); 
                System.out.println("RECO PRODUCE");
			} catch (InterruptedException ex) {
				System.out.println("Recollection Producer thread interrupted.");
			}
		}
		reco_parent.producing = false;
		//reco_parent.consumer.notify();
		
		System.out.println("DONE PRODUCING");
		//
	}

}
