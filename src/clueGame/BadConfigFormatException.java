package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException(String boardConfigFile)
	{
	       super("Bad config format in " + boardConfigFile +  ".");
		   	PrintWriter out;
			try {
				out = new PrintWriter("badconfiglogfile.txt");
			   	out.println("Bad config format in " + boardConfigFile +  ".");
			   	out.close();
			} catch (FileNotFoundException e) {
				System.out.println("logfile.txt could not be made.");
			}
	}
}
