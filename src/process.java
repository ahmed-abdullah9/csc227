import java.io.File;

import java.io.IOException;
import java.util.*;

public class process {

	int countTimer = 0;
	PriorityQueue<PCB> job = new PriorityQueue<>(new Comparator() {

		@Override
		public int compare(Object x, Object y) {
			// TODO Auto-generated method stub
			if (((PCB) x).arrivalTime < ((PCB) y).arrivalTime)
				return -1;
			if (((PCB) x).arrivalTime > ((PCB) y).arrivalTime)

				return 1;

			return 0;
		}
	}

	);
	Queue<PCB> ready = new LinkedList<>();
	Queue<Integer> IOQ = new LinkedList<>();

	public int count = 0;
	double sumtruned = 0;
	double sum_waiting = 0;
	int currCPU = 0;
	int currIO = 0;

	Timer T = new Timer();

	TimerTask Task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			countTimer++;
			System.out.println(countTimer);
		}
	};

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
				System.out.print("cpu");
				while (x-- > 0){
					System.out.print(".");
					jobToReady();
				}
				System.out.println();
				IOQ.add(p.ioBurst.removeFirst());

			}
		}

	}

	public void jobToReady() {
		PCB p = job.peek();
		if (p.arrivalTime <= countTimer)
			ready.add(job.remove());

	}

	public double getAvgT() {

		return sumtruned / count;
	}

	double getAvWating() {
		return sum_waiting / count;
	}

	public static void main(String[] args) throws IOException {
		process p = new process();
		p.read("processes.txt");
		System.out.println("the averg = " + p.getAvgT());
		System.out.println("the averg wating = " + p.getAvWating());
		p.FCFS();
		p.T.scheduleAtFixedRate(p.Task, 1, 1);
		p.job.remove();
		p.job.remove();
		System.out.println(p.job.element().watingTime);
		System.exit(0);
	}

}
