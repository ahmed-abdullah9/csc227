import java.util.LinkedList;

public class PCB {

	public int iD;
	public int arrivalTime;
	public int burstTime;
	public int turnaroundTime;
	public int watingTime;

	public String Nature;
	LinkedList<Integer> cpuBurst = new LinkedList<>();;
	LinkedList<Integer> ioBurst = new LinkedList<>();;

	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	public int getWatingTime() {
		return watingTime;
	}

}
