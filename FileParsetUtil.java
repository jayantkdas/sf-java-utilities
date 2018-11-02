import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Utility class to count uncommented lines in a file. You can add/modify the pattern as you like in the section commented below
 * 
 * @author jayantkdas
 */
public class FileParsetUtil {

	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader(new File("path to my file"));
			BufferedReader br = new BufferedReader(fr);
			String line;
			int executableLines = 0;
			while((line = br.readLine()) != null) {
				line = line.trim();
				if (!(line.startsWith("/") || line.startsWith("*"))) { // add any pattern you want to exclude
					executableLines++;
				}
			}
			System.out.println("Total Uncommented lines : " + executableLines);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
