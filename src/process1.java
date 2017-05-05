import java.io.File;
import java.io.IOException;
import java.util.*;



public class process1 implements Comparator<PCB> {

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
		process1 p = new process1();
		p.read("processes.txt");

		System.out.println("the averg = "+p.getAvgT());
		System.out.println("the averg wating = "+p.getAvWating());

		System.out.println(p.job.element().iD);
		
	}


	@Override
	public int compare(PCB o1, PCB o2) {
		// TODO Auto-generated method stub
		return 0;
	}


	

}
