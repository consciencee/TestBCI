package ExampleAPIFiles;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class EmoLogger {
	
	static String logPath = "log.csv";
	static boolean append = false;
	static PrintWriter logOutput;
	public static boolean enabled = false;
	
    public static void openLogFile() throws FileNotFoundException{
    	logOutput = new PrintWriter(new FileOutputStream(logPath, append));
    	append = true;
    	logOutput.println();
	}
	
    public static void closeLogFile(){
    	
    	logOutput.close();
	} 
    
    public static void print(String line){
    	logOutput.println(line);
    }
    
}
