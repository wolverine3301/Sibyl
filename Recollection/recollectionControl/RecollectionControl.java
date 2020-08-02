package recollectionControl;

import java.util.ArrayList;
import java.util.List;

import dataframe.DataFrame;
import machinations.Model;
import scorer.Evaluate;

public class RecollectionControl {

	public RecollectionControl(List<ArrayList<DataFrame>> memories, Model model,Evaluate ev) throws InterruptedException {
		System.out.println("RELEASE RECOLLECTION");
		final ReleaseRecollection reco = new ReleaseRecollection(memories,model,ev);
		
		Thread t1 = new Thread(new Runnable() { 
            @Override
            public void run(){ 
                try { 
                    reco.produce(); 
                } 
                catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
        }); 
  
        // Create consumer thread 
        Thread t2 = new Thread(new Runnable() { 
            @Override
            public void run() { 
                try { 
                    reco.consume(); 
                } 
                catch (InterruptedException e) { 
                    e.printStackTrace(); 
                } 
            } 
        }); 
  
        // Start both threads 
        t1.start(); 
        t2.start(); 
        while(t1.isAlive()) {
        	System.out.println(ev.getCurrent_f1());
        }
        // t1 finishes before t2 
        t1.join(); 
        t2.join(); 
	
	}
}
