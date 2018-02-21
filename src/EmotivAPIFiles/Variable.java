package EmotivAPIFiles;
import java.util.ArrayList;
import java.util.Arrays;

import com.sun.jna.ptr.IntByReference;

public class Variable{
	public static IntByReference userID = null;
	
	public static float minValue;
	
	static public boolean flagGetValue = false;
	static public boolean startValue = true;
	
	public static ArrayList<Float> partEmotionsData = new ArrayList<Float>();
	
	public static void process(float partEmotions){
		//min value
		/*if(startValue == true){
			minValue = partEmotions;
			startValue = false;
		}else if(partEmotions < minValue){
			minValue = partEmotions;
		}
		
		*/
		// mediana
		partEmotionsData.add(partEmotions);
		
	}

	static public boolean initVariables = false;
	static public boolean sendWantSend = false;
	static public int countWantSend = 0;

	
	public static void getVariable(){
		//return Variable.minValue;		
		Float[] emotionData; 
		emotionData =  partEmotionsData.toArray(new Float[partEmotionsData.size()]);
		
		Arrays.sort(emotionData);
		minValue = emotionData[(int)(partEmotionsData.size()/2)];		
			
	}
}