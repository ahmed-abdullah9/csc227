import java.io.File;
import java.io.IOException;
import java.util.*;

public class process {

	static int countTimer = 0;
	static Timer timer;
	
	PriorityQueue<PCB> job = new PriorityQueue<>(new Comparator<PCB>() {
		@Override
		public int compare(PCB x, PCB y) {
			// TODO Auto-generated method stub
			if (x.arrivalTime < y.arrivalTime)
				return -1;
			else

				return 1;

		}
	}

	);
	Queue<PCB> ready = new LinkedList<>();
	Queue<Integer> IOQ = new LinkedList<>();
	static int x =0;
	public int count = 0;
	double sumtruned = 0;
	double sum_waiting = 0;
	int currCPU = 0;
	int currIO = 0;


	public void read(String x) throws IOException {

		File f = new File(x);
		Scanner ss = new Scanner(f);
		ss.useDelimiter("(;CPU:|;IO:|;| )");
		int tt = 0;// turnarrount before process

		while (ss.hasNext()) {
			int cpu = 0;
			int io = 0;
			PCB p = new PCB();
			p.iD = ss.nextInt();
			p.arrivalTime = ss.nextInt();
			while (ss.hasNextInt()) {
				currCPU = ss.nextInt();
				cpu += currCPU;
				p.cpuBurst.add(currCPU);
				if (ss.hasNextInt()) {
					currIO = ss.nextInt();
					io += currIO;
					p.ioBurst.add(currIO);
				}
			}
			ss.nextLine();
			p.burstTime = io + cpu;
			p.watingTime = tt - p.arrivalTime;

			if (p.watingTime < 0)
				p.watingTime = 0;

			p.turnaroundTime = p.burstTime + p.arrivalTime + p.watingTime;
			tt = p.turnaroundTime;
			sumtruned += p.turnaroundTime;
			sum_waiting += p.watingTime;

			job.add(p);
			count++;
		}
		ss.close();

	}

	public void FCFS() {
		while (!job.isEmpty() || !ready.isEmpty()) {
			jobToReady();
			if (!ready.isEmpty()) {
				PCB p = ready.remove();
				int x = p.cpuBurst.removeFirst();
				while (x-- > 0);
				IOQ.add(p.ioBurst.removeFirst());
				if (!p.cpuBurst.isEmpty()) {
					if (p.cpuBurst.element() != 1)
						ready.add(p);
				}
			}
		}

	}

	public void jobToReady() {
		if (!job.isEmpty()) {
			PCB p = job.peek();
			if (p.arrivalTime <= countTimer) {
				ready.add(job.remove());
			}
		}
		
	}

	public double getAvgT() {
		return sumtruned / count;
	}

	double getAvWating() {
		return sum_waiting / count;
	}

	public static void main(String[] args) throws IOException {
		TimerTask task = new TimerTask() {

			@Override
			public void run() {

				countTimer++;
			}
		};
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						if (countTimer > 2000) {
							System.out.println("Counter has reached 20000 now will terminate");
							timer.cancel();// end the timer
							break;// end this loop
						}
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		timer = new Timer("MyTimer");
		timer.scheduleAtFixedRate(task, 1, 1);
		t.start();

		process p = new process();
		p.read("processes.txt");
		p.FCFS();
		System.out.println("the averg = " + p.getAvgT()+" m/s");
		System.out.println("the averg wating = " + p.getAvWating()+" m/s");
	}

}
