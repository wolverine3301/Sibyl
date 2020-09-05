package recollectionControl;

import java.util.concurrent.BlockingQueue;

import scorer.CrossValidation;
import scorer.Evaluate;

public class RecollectionConsumer2 implements Runnable {
	/** The parent to act as a producer for. */
    ReleaseRecollection2 parent;
	private final BlockingQueue<CrossValidation> EVALUATION_QUEUE;
	Evaluate evaluate;

	public RecollectionConsumer2(BlockingQueue<CrossValidation> blockingQueue,Evaluate evaluate,ReleaseRecollection2 reco_parent) {
	  this.EVALUATION_QUEUE = blockingQueue;
	  this.evaluate = evaluate;
	  this.parent = reco_parent;
	 }

	public void run() {
		boolean evalOn = parent.producing;
		while (true) {
			try {

				evaluate.evaluation(EVALUATION_QUEUE.take());
				System.out.println("Reco CONSUME");
			} catch (InterruptedException ex) {
				System.out.println("Evaluation Consumer thread interrupted.");
			}
		}
		//System.out.println("EVALUATION COMPLETE");
	}
	
}
