 package EmotivLoggerApp;

import java.io.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import EmotivAPIFiles.*;
import org.jfree.ui.RefineryUtilities;

import Images.DynamicDataDemo;
import Images.DynamicDataFile;
import InterfaceVariable.Interface;
import InterfaceVariable.InterfaceVariables;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;


public class MainGui{
  
	public static void main(String args[]) throws FileNotFoundException {
		
		
		float old = 0;
		long startTime = System.currentTimeMillis();
		Interface menu = new Interface();
		Pointer eEvent = Edk.INSTANCE.IEE_EmoEngineEventCreate();
		Pointer eState = Edk.INSTANCE.IEE_EmoStateCreate();
		int state = 0;
		Variable.userID = new IntByReference(0);

		if (Edk.INSTANCE.IEE_EngineConnect("Emotiv Systems-5") != 
				EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Emotiv Engine start up failed.");
			return;
		}
		
        final DynamicDataDemo demo = new DynamicDataDemo("EEG Data");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        
        for(int i = 0; i < 100; i++) {
        	demo.drawData(1, 0);
			demo.drawData(1, 1);
			demo.drawData(1, 2);
			demo.drawData(1, 3);
			demo.drawData(1, 4);
			demo.drawData(1, 5);
			demo.drawData(1, 6);
        }
        
	   // Logger cmRootLogger = Logger.getLogger("default.config");
	   // cmRootLogger.setLevel(java.util.logging.Level.OFF);
		/*
		Pointer eEvent			= Edk.INSTANCE.IEE_EmoEngineEventCreate();
    	Pointer eState			= Edk.INSTANCE.IEE_EmoStateCreate();
    	IntByReference userID 	= null;
    	short composerPort		= 1726;
    	int option 				= 1;
    	int state  				= 0;
    	userID = new IntByReference(0);   	
    	
    	switch (option) {
		case 1:
		{
			if (Edk.INSTANCE.IEE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
				System.out.println("Emotiv Engine start up failed.");
				return;
			}
			break;
		}
		case 2:
		{
			System.out.println("Target IP of EmoComposer: [127.0.0.1] ");

			if (Edk.INSTANCE.IEE_EngineRemoteConnect("127.0.0.1", composerPort, "Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
				System.out.println("Cannot connect to EmoComposer on [127.0.0.1]");
				return;
			}
			System.out.println("Connected to EmoComposer on [127.0.0.1]");
			break;
		}
		default:
			System.out.println("Invalid option...");
			return;
    	}
*/ 	
		
		//String logPath = "log.csv";
		
		//PrintWriter logEmoState = new PrintWriter(new FileOutputStream(logPath));
		
		while (InterfaceVariables.flagEndCycle == 0){		
				state = Edk.INSTANCE.IEE_EngineGetNextEvent(eEvent);
				// New event needs to be handled
				if (state == EdkErrorCode.EDK_OK.ToInt()) {
					
					/*Edk.INSTANCE.IEE_EmoEngineEventGetEmoState(eEvent, eState);
					
					printContactQuality(eState);
					*/
	
					int eventType = Edk.INSTANCE.IEE_EmoEngineEventGetType(eEvent);
					Edk.INSTANCE.IEE_EmoEngineEventGetUserId(eEvent, Variable.userID);					
					//System.out.println("eventType = " + eventType);
					if(eventType == Edk.IEE_Event_t.IEE_UserAdded.ToInt())
					{
						//System.out.println("Add user num = " + Edk.IEE_Event_t.EE_UserAdded.ToInt());
						
						//
						//EmoProfileManagement.AddNewProfile("3");
						//
						///////////////////////////////////////////////////////////////////////////////////////////////////////
						
					/*	EmoProfileManagement.LoadProfilesFromFile();
						EmoProfileManagement.SetUserProfile("1");
						String actionList = EmoProfileManagement.CheckCurrentProfile();
						long cognitivActions = Long.valueOf(actionList);
						Edk.INSTANCE.IEE_MentalCommandSetActiveActions(0, cognitivActions);
					*/	JOptionPane.showMessageDialog(new JFrame(), "User add", "Dialog",
					        JOptionPane.INFORMATION_MESSAGE);
					}
					
					else if(eventType == Edk.IEE_Event_t.IEE_MentalCommandEvent.ToInt())
					{
						System.out.println("Cognitiv train num = " + Edk.IEE_Event_t.IEE_MentalCommandEvent.ToInt());
						int cogType = Edk.INSTANCE.IEE_MentalCommandEventGetType(eEvent);
						
						if(cogType ==Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingStarted.getType())
						{
							JOptionPane.showMessageDialog(new JFrame(), "Cognitiv Training Start", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
						}
						if(cogType == Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingCompleted.getType())
						{
							JOptionPane.showMessageDialog(new JFrame(), "Cognitiv Training Complete", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
						}
						if(cogType == Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingSucceeded.getType())
						{
							Edk.INSTANCE.IEE_MentalCommandSetTrainingControl(Variable.userID.getValue(),Edk.IEE_MentalCommandTrainingControl_t.MC_ACCEPT.getType());
							JOptionPane.showMessageDialog(new JFrame(), "Cognitiv Training Succeeded", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
						}
						if(cogType == Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingFailed.getType())
						{
							JOptionPane.showMessageDialog(new JFrame(), "Cognitiv Training Failed", "Dialog",
							        JOptionPane.ERROR_MESSAGE);
						}
						if(cogType == Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingRejected.getType())
						{
							JOptionPane.showMessageDialog(new JFrame(), "Cognitiv Training Rejected", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
						}
					}
					
					else if(eventType == Edk.IEE_Event_t.IEE_FacialExpressionEvent.ToInt())
					{
						System.out.println("Expressiv train num = " + Edk.IEE_Event_t.IEE_FacialExpressionEvent.ToInt());
						int expType = Edk.INSTANCE.IEE_FacialExpressionEventGetType(eEvent);
						
						if(expType ==Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingStarted.getType())
						{
							JOptionPane.showMessageDialog(new JFrame(), "Expressiv Training Start", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
						}
						if(expType == Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingCompleted.getType())
						{
							JOptionPane.showMessageDialog(new JFrame(), "Expressiv Training Complete", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
						}
						if(expType == Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingSucceeded.getType())
						{
							//Edk.INSTANCE.IEE_MentalCommandSetTrainingControl(0,Edk.IEE_MentalCommandTrainingControl_t.COG_ACCEPT.getType());
							Edk.INSTANCE.IEE_FacialExpressionSetTrainingControl(Variable.userID.getValue(), Edk.IEE_FacialExpressionTrainingControl_t.FE_ACCEPT.toInt());
							JOptionPane.showMessageDialog(new JFrame(), "Expressiv Training Succeeded", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
						}
						if(expType == Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingFailed.getType())
						{
							JOptionPane.showMessageDialog(new JFrame(), "Expressiv Training Failed", "Dialog",
							        JOptionPane.ERROR_MESSAGE);
						}
						if(expType == Edk.IEE_MentalCommandEvent_t.IEE_MentalCommandTrainingRejected.getType())
						{
							JOptionPane.showMessageDialog(new JFrame(), "Expressiv Training Rejected", "Dialog",
							        JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else if(eventType == Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt())
					{

						//System.out.println("State updated num = " + Edk.IEE_Event_t.EE_EmoStateUpdated.ToInt());
						Edk.INSTANCE.IEE_EmoEngineEventGetEmoState(eEvent, eState);
						
						printContactQuality(eState);
						
						float excitementLong = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetExcitementLongTermScore(eState);
						float instantaneousExcitement = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetInstantaneousExcitementScore(eState);
						float relaxation = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetRelaxationScore(eState);
						float stress = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetStressScore(eState);
						float engagementBoredom = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetEngagementBoredomScore(eState);											
						float interest = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetInterestScore(eState);					
						float focus = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetFocusScore(eState);						
						
						int diffTime = (int)(System.currentTimeMillis() - startTime);//1000;
						
						if (excitementLong != old){
							old = excitementLong;
							System.out.println("Magic: " + excitementLong + " ; " + instantaneousExcitement + " ; " + relaxation + " ; " + stress +  " ; "+ engagementBoredom + " ; " + interest + " ; " + focus + " ; ");
						}
						//System.out.println("Magic: " + excitementLong + " ; " + instantaneousExcitement + " ; " + relaxation + " ; " + stress +  " ; "+ engagementBoredom + " ; " + interest + " ; " + focus + " ; ");
						
						demo.drawData(excitementLong, 0);
						demo.drawData(instantaneousExcitement, 1);
						demo.drawData(relaxation, 2);
						demo.drawData(stress, 3);
						demo.drawData(engagementBoredom, 4);
						demo.drawData(interest, 5);
						demo.drawData(focus, 6);
						
						if(EmoLogger.enabled)
							EmoLogger.print("" + diffTime + ',' + excitementLong + ',' + instantaneousExcitement + ',' + relaxation + ',' + stress + ',' + engagementBoredom + ',' + interest + ',' + focus);
						
						// mind data
						int action = EmoState.INSTANCE.IS_MentalCommandGetCurrentAction(eState);							
						float power = EmoState.INSTANCE.IS_MentalCommandGetCurrentActionPower(eState);
						getCurrentMindCommands(action, power);
						/**
						 * faster version
						 * */
						/*if(InterfaceVariables.flagStartSend == 0){						
							//Edk.INSTANCE.EE_HeadsetGetGyroDelta(userID.getValue(), pXOut, pYOut);
							//InterfaceVariables.gyroAxisData += pXOut.getValue();			
							for(int i=0; i < InterfaceVariables.currentExpressionsCommands.length; ++i){
								InterfaceVariables.currentExpressionsCommands[i] = getCurrentPower(InterfaceVariables.EXPRESSIV, i, eState);
							}
							
							checkCurrentStatus();						
						}else{
							checkCurrentConditions(eState);
							///////////////////////////////////////////////////////////////////////////////////
							//checkCurrentStatus();	
						}*/
						/**
						 * showing (slowing) version
						 */							
						for(int i=0; i < InterfaceVariables.currentExpressionsCommands.length; ++i){
							InterfaceVariables.currentExpressionsCommands[i] = getCurrentPower(InterfaceVariables.EXPRESSIV, i, eState);
						}
						checkCurrentConditions(eState);
						checkCurrentStatus();	
	
						
					}
					
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
					System.out.println("Internal error in Emotiv Engine!");
					break;
				}
			}	
		
		//logEmoState.close();
		
	    }
	
	
	static void getCurrentMindCommands(int action, float power){
		/* index 0 is not possible (power value = -1)*/
		for(int i=1; i<InterfaceVariables.currentMindCommands.length;++i){
			if((InterfaceVariables.mindCommandsActions[i] & action) == InterfaceVariables.mindCommandsActions[i]){
				InterfaceVariables.currentMindCommands[i] = power;
			}else{
				InterfaceVariables.currentMindCommands[i] = 0;
			}
		}		
	}
	
	static void checkCurrentStatus(){
		Interface.repaintListCurrentActions();
	}
	
	static void checkCurrentConditions(Pointer eState){
		int index_mind, index_expression;
		float min_power_mind, min_power_expression;		
		int indexInCommandNames; 
		for(int i=0; i< InterfaceVariables.commandsList.size(); ++i){
			index_mind = InterfaceVariables.commandsList.get(i).getIndexMind();
			index_expression = InterfaceVariables.commandsList.get(i).getIndexExpression();		
			min_power_mind = InterfaceVariables.commandsList.get(i).getPowerMind();
			min_power_expression = InterfaceVariables.commandsList.get(i).getPowerExpression();
			
			indexInCommandNames = Interface.listModelCommandNames.indexOf(InterfaceVariables.commandsList.get(i).getCommandName());			
			
			/* mind execute */
			if(getCurrentPower(InterfaceVariables.MIND, index_mind, eState) >= min_power_mind)
			{
				Interface.listModelExecuteCommands.set(indexInCommandNames , "exec");
				System.out.println("MIND Execute command " + InterfaceVariables.commandsList.get(i).getCommandName() + " mind current = " + getCurrentPower(InterfaceVariables.MIND, index_mind, eState)
						+ " ; min_saved = " + min_power_mind);
			}
			if(getCurrentPower(InterfaceVariables.EXPRESSIV, index_expression, eState) >= min_power_expression)
			{
				Interface.listModelExecuteCommands.set(indexInCommandNames , "exec");
				System.out.println("Expressiv Execute command " + InterfaceVariables.commandsList.get(i).getCommandName() + " exp current = " + getCurrentPower(InterfaceVariables.EXPRESSIV, index_expression, eState)
						+ " ; min_saved = " + min_power_expression);
			}else{
				Interface.listModelExecuteCommands.set(indexInCommandNames , "0");
			}
		}
	}
	
/*{"(None)", "BLINK", "WINK_LEFT", "WINK_RIGHT", "LOOK_LEFT", "LOOK_RIGHT", 
 * "EYEBROW", "FURROW", "SMILE", "CLENCH", "LAUGH","SMIRK_LEFT", "SMIRK_RIGHT"};
*/
	static float getCurrentPower(int type, int index, Pointer eState){
		if(type == InterfaceVariables.MIND){
			return InterfaceVariables.currentMindCommands[index];				
		}else if(type == InterfaceVariables.EXPRESSIV){
			switch(index){
				case 0:
					return -1;
				case 1:
					return EmoState.INSTANCE.IS_FacialExpressionIsBlink(eState);
				case 2:
					return EmoState.INSTANCE.IS_FacialExpressionIsLeftWink(eState);
				case 3:
					return EmoState.INSTANCE.IS_FacialExpressionIsRightWink(eState);
				case 4:
					return EmoState.INSTANCE.IS_FacialExpressionIsLookingLeft(eState);
				case 5:
					return EmoState.INSTANCE.IS_FacialExpressionIsLookingRight(eState);				
				case 6:					
					return EmoState.INSTANCE.IS_FacialExpressionIsLookingRight(eState);//EmoState.INSTANCE.IS_FacialExpressionGetSurpriseExtent(eState);					
				case 8:
					return EmoState.INSTANCE.IS_FacialExpressionGetSmileExtent(eState);			
				case 9:
					return EmoState.INSTANCE.IS_FacialExpressionGetClenchExtent(eState);
			///////////////////////////////////////////////////////////////
				case 7:	// 7 - FURROW
					return getLowerUpperFacePower(EmoState.INSTANCE.IS_FacialExpressionGetUpperFaceAction(eState),
							EmoState.INSTANCE.IS_FacialExpressionGetUpperFaceActionPower(eState),
							EmoState.IEE_FacialExpressionAlgo_t.FE_FROWN.ToInt());
				case 10:	// 10 - LAUGH
					return getLowerUpperFacePower(EmoState.INSTANCE.IS_FacialExpressionGetLowerFaceAction(eState),
							EmoState.INSTANCE.IS_FacialExpressionGetLowerFaceActionPower(eState),
							EmoState.IEE_FacialExpressionAlgo_t.FE_LAUGH.ToInt());
				case 11: // 11 - SMIRK_LEFT
					return getLowerUpperFacePower(EmoState.INSTANCE.IS_FacialExpressionGetLowerFaceAction(eState),
							EmoState.INSTANCE.IS_FacialExpressionGetLowerFaceActionPower(eState),
							EmoState.IEE_FacialExpressionAlgo_t.FE_SMIRK_LEFT.ToInt());
				case 12: // 12 - SMIRK_RIGHT	
					return getLowerUpperFacePower(EmoState.INSTANCE.IS_FacialExpressionGetLowerFaceAction(eState),
							EmoState.INSTANCE.IS_FacialExpressionGetLowerFaceActionPower(eState),
							EmoState.IEE_FacialExpressionAlgo_t.FE_SMIRK_RIGHT.ToInt());		
			}
		}
		return -1;
	}
	
	static float getLowerUpperFacePower(int faceType, float facePower, int type){
		if (facePower > 0.0) {						
			if(faceType == type){
				return facePower;
			}			
		}
		return 0;
	}
	
	static void printContactQuality(Pointer eState){
		//5 channels: AF3, AF4, T7, T8, Pz
		//2 references: In the CMS/DRL noise cancellation configuration
		int af3Level = EmoState.INSTANCE.IS_GetContactQuality(eState, EmoState.IEE_InputChannels_t.IEE_CHAN_AF3.getType());
		int af4Level = EmoState.INSTANCE.IS_GetContactQuality(eState, EmoState.IEE_InputChannels_t.IEE_CHAN_AF4.getType());
		int t7Level = EmoState.INSTANCE.IS_GetContactQuality(eState, EmoState.IEE_InputChannels_t.IEE_CHAN_T7.getType());
		int t8Level = EmoState.INSTANCE.IS_GetContactQuality(eState, EmoState.IEE_InputChannels_t.IEE_CHAN_T8.getType());
		int pzLevel = EmoState.INSTANCE.IS_GetContactQuality(eState, EmoState.IEE_InputChannels_t.IEE_CHAN_O1.getType());
		
		System.out.println("AF3: " + af3Level + ", AF4: " + af4Level + ", T7: " + t7Level + ", T8: " + t8Level + ", PZ: " + pzLevel);
	}
	
	static void dynamicDataUsage() {
				final DynamicDataFile demo = new DynamicDataFile("EEG Data");
		        demo.pack();
		        RefineryUtilities.centerFrameOnScreen(demo);
		        demo.setVisible(true);
		        
		        try {
					demo.drawContents("log.csv");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
}