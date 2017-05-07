import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Test {

    static int counter = 0;
    static Timer timer;

    public static void main(String[] args) throws IOException {

        //create timer task to increment counter
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                // System.out.println("TimerTask executing counter is: " + counter);
                counter++;
            }
        };

        //create thread to print counter value
        Thread t = new Thread(new Runnable() {
        	
        	@Override
        	public void run() {
        		while (true) {
        			try {
        				System.out.println("Thread reading counter is: " + counter);
        				if (counter >1000) {
        					System.out.println("Counter has reached 100000 now will terminate");
        					timer.cancel();//end the timer
        					break;//end this loop
        				}
        				Thread.sleep(1000);
        			} catch (InterruptedException ex) {
        				ex.printStackTrace();
        			}
        		}
        	}
        });
        
        timer = new Timer("MyTimer");//create a new timer
        timer.scheduleAtFixedRate(timerTask, 1, 1);
        
        t.start();//start thread to display counter
        process p = new process();
		p.read("processes.txt");
		System.out.println("the averg = " + p.getAvgT());
		System.out.println("the averg wating = " + p.getAvWating());
		p.FCFS();
		System.exit(0);
    }
    
}