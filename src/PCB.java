import java.util.LinkedList;
public class PCB {

	
	public int iD;
	public int arrivalTime;
	public int burstTime;
	public int turnaroundTime;
	public int watingTime;
	public String nature;
	LinkedList<Integer> cpuBurst;
	LinkedList<Integer> ioBurst;
	
	
	
	PCB(){
		cpuBurst = new LinkedList<>();
		ioBurst = new LinkedList<>();

	}
		public void setNature(String s){
		nature =s;
			
	}
		public int getTurnaroundTime() {
			return turnaroundTime;
		}
		public int getWatingTime() {
			return watingTime;
		}

}
