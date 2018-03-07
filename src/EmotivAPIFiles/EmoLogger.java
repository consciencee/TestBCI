package EmotivAPIFiles;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class EmoLogger {
	
	static String logPath = "log.csv";
	static boolean append = false;
	static PrintWriter logOutput, eegLogOutput;
	public static boolean enabled = false;

	public enum Log{
	    State,
        EEG
    }
	
    public static void openLogFile(String userID) throws FileNotFoundException{
    	logOutput = new PrintWriter(new FileOutputStream(userID + logPath, append));
    	eegLogOutput = new PrintWriter(new FileOutputStream(userID + "_eeg_" + logPath, append));
    	append = true;
    	logOutput.println();
    	eegLogOutput.println();
	}

	public static void openLogFile() throws FileNotFoundException{
    	openLogFile("");
	}
	
    public static void closeLogFile(){
    	
    	logOutput.close();
        eegLogOutput.close();
	} 
    
    public static void print(Log type, String line){

	    if(type == Log.State)
	        logOutput.println(line);
	    else
	        eegLogOutput.println(line);
    }
    
}
