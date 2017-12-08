package InterfaceVariable;

import java.util.ArrayList;

import Commands.Commands;
import ExampleAPIFiles.EmoState;
import ExampleAPIFiles.EmoState.IEE_MentalCommandAction_t;

public class InterfaceVariables{
	// button size and location
	static int WIDTH_BUTTON = 120;
	static int HEIGHT_BUTTON = 25;
	
	static int START_X_BUTTON_LOCATION = 20;
	static final int START_Y_BUTTON_LOCATION = 50;
	static int X_BUTTON_LOCATION = START_X_BUTTON_LOCATION;
	static int Y_BUTTON_LOCATION = START_Y_BUTTON_LOCATION;
	
	static int DELTA_X_BUTTON_LOCATION = WIDTH_BUTTON + 20;
	static int DELTA_Y_BUTTON_LOCATION = 100;

	// mind train
	public static String [] optionsMind = {"Neutral","Forward","Backward","Turn left","Turn right"};
	public static String [] optionsExp = {"Neutral", "EYEBROW", "FURROW", "SMILE", "CLENCH", "LAUGH","SMIRK_LEFT", "SMIRK_RIGHT"};
	
	public static final int Index_NEUTRAL = 0;
	public static final int Index_MIND_CMD_1 = 1;
	public static final int Index_MIND_CMD_2 = 2;
	public static final int Index_MIND_CMD_3 = 3;
	public static final int Index_MIND_CMD_4 = 4;
	public static int [] mindCommandsActions = {-1, IEE_MentalCommandAction_t.MC_PUSH.ToInt(), IEE_MentalCommandAction_t.MC_PULL.ToInt(),
		IEE_MentalCommandAction_t.MC_ROTATE_LEFT.ToInt(), IEE_MentalCommandAction_t.MC_ROTATE_RIGHT.ToInt()};
	
	// settings
	public static String [] optionsMindTrained = {"(None)","MIND_CMD_1","MIND_CMD_2","MIND_CMD_3","MIND_CMD_4"};
	public static String [] optionsExpressions = {"(None)", "BLINK", "WINK_LEFT", "WINK_RIGHT", "LOOK_LEFT", "LOOK_RIGHT", "EYEBROW", "FURROW", "SMILE", "CLENCH", "LAUGH","SMIRK_LEFT", "SMIRK_RIGHT"};

	
	public static String [] optionsCommand = {"Stop","Push","Pull","Rotate_Left","Rotate_Right", "Fire", "Reset gyro"};	
	
	public static ArrayList<Commands> commandsList = new ArrayList<Commands>();	

	public static float[] currentMindCommands = new float[optionsMindTrained.length];
	public static float[] currentExpressionsCommands = new float[optionsExpressions.length];
	
	public static int flagStartSend = 0;
	//public static int StartSend = 0;	
	public static int flagEndCycle = 0;

	public static final int MIND = 0;
	public static final int EXPRESSIV = 1;
	

}