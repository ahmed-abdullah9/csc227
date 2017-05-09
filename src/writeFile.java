import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.Math;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;

public class writeFile {

PriorityQueue<PCB> job = new PriorityQueue<>();
	
	public void write(String fileName) {
		try {

			File file = new File(fileName);
			file.createNewFile();
			FileWriter writer = new FileWriter(file);
			int count = 1;
			for (int i = 0; i < 1000; i++) {

				writer.write(count++ + ";" + new Random().nextInt(2000) + ";CPU:" + new Random().nextInt(100) + ";IO:"
						+ new Random().nextInt(10) + ";CPU:" + new Random().nextInt(100) + ";IO:"
						+ new Random().nextInt(10) + ";CPU:" + new Random().nextInt(100) + ";IO:"
						+ new Random().nextInt(10) + ";CPU:1\n");
				writer.write(count++ + ";" + new Random().nextInt(2000) + ";CPU:" + new Random().nextInt(100) + ";IO:"
						+ new Random().nextInt(10) + ";CPU:1\n");
			}
			writer.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public void add(String fileName){
		try{
			
		File f = new File(fileName);
		Scanner s = new Scanner(f);
		s.useDelimiter("(;CPU:|;IO:|;| )");

		int id = s.nextInt();
		int arrival = s.nextInt();
		int cpu = s.nextInt();
		int io = s.nextInt();
		System.out.println(id + "  "+ arrival + " " + cpu+ " "+ io);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public static void main(String args[]) {
		Random r = new Random();
		writeFile w = new writeFile();
		//w.write("processes.txt");
		w.add("processes.txt");
	}
}