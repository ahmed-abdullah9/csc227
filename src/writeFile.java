import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.lang.Math;
import java.util.Random;

public class writeFile {

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

	public static void main(String args[]) {
		Random r = new Random();

		writeFile w = new writeFile();
		w.write("processes.txt");
	}
}
