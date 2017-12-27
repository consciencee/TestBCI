package ExampleAPIFiles;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jfree.ui.RefineryUtilities;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import InterfaceVariable.InterfaceVariables;
import InterfaceVariable.LoggerInterface;

public class MainLogGui {
	
	public static void main(String[] args) {
		
		LoggerInterface loggerFrame = new LoggerInterface();
		RefineryUtilities.centerFrameOnScreen(loggerFrame);
		
		long startTime = System.currentTimeMillis();
		
		Pointer eEvent = Edk.INSTANCE.IEE_EmoEngineEventCreate();
		Pointer eState = Edk.INSTANCE.IEE_EmoStateCreate();
		int state = 0;
		Variable.userID = new IntByReference(0);

		if (Edk.INSTANCE.IEE_EngineConnect("Emotiv Systems-5") != 
				EdkErrorCode.EDK_OK.ToInt()) {
			System.out.println("Emotiv Engine start up failed.");
			return;
		}
		
		while (InterfaceVariables.flagEndCycle == 0){		
			
			state = Edk.INSTANCE.IEE_EngineGetNextEvent(eEvent);
			
			if (state == EdkErrorCode.EDK_OK.ToInt()) {
				
				int eventType = Edk.INSTANCE.IEE_EmoEngineEventGetType(eEvent);
				Edk.INSTANCE.IEE_EmoEngineEventGetUserId(eEvent, Variable.userID);	

				if(eventType == Edk.IEE_Event_t.IEE_EmoStateUpdated.ToInt()){
					
					Edk.INSTANCE.IEE_EmoEngineEventGetEmoState(eEvent, eState);
					
					printContactQuality(eState);
					
					float excitementLong = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetExcitementLongTermScore(eState);
					float instantaneousExcitement = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetInstantaneousExcitementScore(eState);
					float relaxation = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetRelaxationScore(eState);
					float stress = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetStressScore(eState);
					float engagementBoredom = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetEngagementBoredomScore(eState);											
					float interest = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetInterestScore(eState);					
					float focus = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetFocusScore(eState);						
					
					int diffTime = (int)(System.currentTimeMillis() - startTime);
					
					if(EmoLogger.enabled)
						EmoLogger.print("" + diffTime + ',' + excitementLong + ',' + instantaneousExcitement + ',' + relaxation + ',' + stress + ',' + engagementBoredom + ',' + interest + ',' + focus);
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

}
