import java.io.File;

import java.io.IOException;
import java.util.*;



public class process implements Comparator<PCB> {

	PriorityQueue<PCB>job = new PriorityQueue<>();
	PriorityQueue<PCB> ready = new PriorityQueue<>();
	Queue<Integer> IOQ = new LinkedList<>();

	public int count = 0;
	double sumtruned = 0;
	double sum_waiting = 0;
	int currCPU =0;
	int currIO =0;

	int timer = 0;
	Timer myTimer = new Timer();
	TimerTask task = new TimerTask() {

		public void run() {

			timer += 1;
		}

	};

	void Strart() {

		myTimer.scheduleAtFixedRate(task, 1, 1);
	}

	
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
				currCPU =ss.nextInt();
				cpu += currCPU;
				p.cpuBurst.add(currCPU);
				if (ss.hasNextInt()){
					currIO = ss.nextInt();
					io +=currIO;
					p.ioBurst.add(currIO);
				}
			}
			ss.nextLine();
			if (cpu > io) {
				p.setNature("CPUBound");
			} else
				p.setNature("IOBound");

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
	public void FCFS(){
		
		while(!job.isEmpty()|| !ready.isEmpty()){
			
			jobToReady();
			if(!ready.isEmpty()){
				PCB p = ready.remove();
				int x = p.cpuBurst.removeFirst();
				while(x-- > 0)
					jobToReady();
				
				IOQ.add(p.ioBurst.removeFirst());
				if(p.cpuBurst.element() != 1)
					ready.add(p);				
			}
			
			
		}
	}

	public void jobToReady(){
		PCB p = job.peek();
		
		if(p.arrivalTime<=timer){
			
		ready.add(job.remove());
			
		}
		
		
		
		
	}
	public double getAvgT() {

		return sumtruned / count;
	}
            
	double getAvWating(){
		return sum_waiting/count;
	}
	
	
	
	
	public static void main(String[] args) throws IOException {
		process p = new process();
		p.read("processes.txt");
		System.out.println("the averg = "+p.getAvgT());
		System.out.println("the averg wating = "+p.getAvWating());

		PCB s = p.job.peek();
		LinkedList<Integer> l = s.cpuBurst;
		LinkedList<Integer> l2 = s.ioBurst;
		while(!l.isEmpty()){
			int x = l.removeFirst();
			int x2 = l2.removeFirst();
			System.out.print(x+"\n");
			System.out.println(x2);
		}
//		while (!p.job.isEmpty()) {
//			System.out.println(p.job.peek().iD);
//			System.out.println(p.job.peek().nature);
//			System.out.println(p.job.peek().arrivalTime);
//			System.out.println(p.job.peek().burstTime);
//			System.out.println(p.job.peek().turnaroundTime);
//			p.job.remove();
//			System.out.println("======");
//		}
	}


	@Override
	public int compare(PCB o1, PCB o2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
