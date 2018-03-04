package  EmotivAPIFiles;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.*;


/** Simple example of JNA interface mapping and usage. */
public class EmoStateLog 
{      
    public static void main(String[] args) 
    {
    	Pointer eEvent			= Edk.INSTANCE.EE_EmoEngineEventCreate();
    	Pointer eState			= Edk.INSTANCE.EE_EmoStateCreate();
    	IntByReference userID 	= null;
		IntByReference nSamplesTaken= null;
    	short composerPort		= 1726;
    	int option 				= 1;
    	int state  				= 0;
    	float secs 				= 1;
    	
    	userID = new IntByReference(0);
		nSamplesTaken	= new IntByReference(0);
    	
    	switch (option) {
		case 1:
		{
			if (Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
				System.out.println("Emotiv Engine start up failed.");
				return;
			}
			break;
		}
		case 2:
		{
			System.out.println("Target IP of EmoComposer: [127.0.0.1] ");

			if (Edk.INSTANCE.EE_EngineRemoteConnect("127.0.0.1", composerPort, "Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
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
    	
		Pointer hData = Edk.INSTANCE.EE_DataCreate();
		Edk.INSTANCE.EE_DataSetBufferSizeInSec(secs);
		System.out.print("Buffer size in secs: ");
		System.out.println(secs);
    		
    	System.out.println("Start receiving EEG Data!");
    	
		while (true) 
		{
			state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);

			// New event needs to be handled
			if (state == EdkErrorCode.EDK_OK.ToInt()) {

				int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
				Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, userID);

				// Log the EmoState if it has been updated
				if (eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()) {

					Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);
					float timestamp = EmoState.INSTANCE.ES_GetTimeFromStart(eState);
					System.out.println(timestamp + " : New EmoState from user " + userID.getValue());
					
					System.out.print("WirelessSignalStatus: ");
					System.out.println(EmoState.INSTANCE.ES_GetWirelessSignalStatus(eState));
					
					if (EmoState.INSTANCE.ES_ExpressivIsBlink(eState) == 1)
						System.out.println("Blink");
					if (EmoState.INSTANCE.ES_ExpressivIsLeftWink(eState) == 1)
						System.out.println("LeftWink");
					if (EmoState.INSTANCE.ES_ExpressivIsRightWink(eState) == 1)
						System.out.println("RightWink");
					if (EmoState.INSTANCE.ES_ExpressivIsLookingLeft(eState) == 1)
						System.out.println("LookingLeft");
					if (EmoState.INSTANCE.ES_ExpressivIsLookingRight(eState) == 1)
						System.out.println("LookingRight");
					
					System.out.print("ExcitementShortTerm: ");
					System.out.println(EmoState.INSTANCE.ES_AffectivGetExcitementShortTermScore(eState));
					System.out.print("ExcitementLongTerm: ");
					System.out.println(EmoState.INSTANCE.ES_AffectivGetExcitementLongTermScore(eState));
					System.out.print("EngagementBoredom: ");
					System.out.println(EmoState.INSTANCE.ES_AffectivGetEngagementBoredomScore(eState));
					
					System.out.print("CognitivGetCurrentAction: ");
					System.out.println(EmoState.INSTANCE.ES_CognitivGetCurrentAction(eState));
					System.out.print("CurrentActionPower: ");
					System.out.println(EmoState.INSTANCE.ES_CognitivGetCurrentActionPower(eState));
					
					//if(EmoLogger.enabled)
					//	EmoLogger.print("" + diffTime + ',' + excitementLong + ',' + instantaneousExcitement + ',' + relaxation + ',' + stress + ',' + engagementBoredom + ',' + interest + ',' + focus);
					// eeg data
					Edk.INSTANCE.EE_DataUpdateHandle(0, hData);

					Edk.INSTANCE.EE_DataGetNumberOfSample(hData, nSamplesTaken);

					if (nSamplesTaken != null)
					{
						if (nSamplesTaken.getValue() != 0) {
							
							//System.out.print("Updated: ");
							//System.out.println(nSamplesTaken.getValue());
							
							double[] data = new double[nSamplesTaken.getValue()];
							String ans = "";
						//	System.out.println("NEW FOR1 " + nSamplesTaken.getValue());
							for (int sampleIdx=0 ; sampleIdx<nSamplesTaken.getValue(); ++ sampleIdx) {
						//		System.out.println("NEW FOR2");
								for (int i =  0; i < 14; i++) {

									Edk.INSTANCE.EE_DataGet(hData, i, data, nSamplesTaken.getValue());
									//System.out.print(data[sampleIdx]);
									//System.out.print(",");
									if(sampleIdx == 0){
										ans += data[sampleIdx];										
									}
									
								}	
								if(EmoLogger.enabled)
								EmoLogger.print(EmoLogger.Log.State, "" + data[sampleIdx]);
							//	System.out.println();
								//demo.drawData(data[sampleIdx], sampleIdx);
							}
						}
					}
				}
			}
			else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
				System.out.println("Internal error in Emotiv Engine!");
				break;
			}
		}
    	
    	Edk.INSTANCE.EE_EngineDisconnect();
    	System.out.println("Disconnected!");
    }
}
