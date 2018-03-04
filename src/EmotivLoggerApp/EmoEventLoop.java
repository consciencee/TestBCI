package EmotivLoggerApp;

import EmotivAPIFiles.*;
import InterfaceVariable.InterfaceVariables;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import EmotivAPIFiles.EmoLogger.Log;

public class EmoEventLoop {

    private Pointer eEvent, eState;
    private int state;
    private boolean connected = false;

    public EmoEventLoop (){
        eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();
        eState = Edk.INSTANCE.EE_EmoStateCreate();
        state = 0;
        Variable.userID = new IntByReference(0);

        connect();
    }

    public boolean connect(){
        if (Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5") !=
                EdkErrorCode.EDK_OK.ToInt()) {
            System.out.println("Emotiv Engine start up failed.");
            connected = false;
        }
        else
            connected = true;
        return connected;
    }

    public void loop(){

        if(!connected)
            return;

        long startTime = System.currentTimeMillis();

        while (InterfaceVariables.flagEndCycle == 0){

            state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);

            if (state == EdkErrorCode.EDK_OK.ToInt()) {

                printContactQuality(eState);

                int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
                if(eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()) {
                    logEmoState(startTime);
                    logEEG(startTime);
                }

                // logEEG(startTime);

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

    private static void printContactQuality(Pointer eState){
        //5 channels: AF3, AF4, T7, T8, Pz
        //2 references: In the CMS/DRL noise cancellation configuration
        // TO DO: EPOC channels
        int af3Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_AF3.ordinal());
        int af4Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_AF4.ordinal());
        int t7Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_T7.ordinal());
        int t8Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_T8.ordinal());
        int pzLevel = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_O1.ordinal());

        System.out.println("AF3: " + af3Level + ", AF4: " + af4Level + ", T7: " + t7Level +
                ", T8: " + t8Level + ", PZ: " + pzLevel);
    }

    private void logEmoState(long startTime){

        Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);


        float excitementLong = EmoState.INSTANCE.ES_AffectivGetExcitementLongTermScore(eState);
        float excitementShort = EmoState.INSTANCE.ES_AffectivGetExcitementShortTermScore(eState);
        float meditation = EmoState.INSTANCE.ES_AffectivGetMeditationScore(eState);
        float boredom = EmoState.INSTANCE.ES_AffectivGetEngagementBoredomScore(eState);
        float frustration = EmoState.INSTANCE.ES_AffectivGetFrustrationScore(eState);

        int diffTime = (int)(System.currentTimeMillis() - startTime);

        if(EmoLogger.enabled)
            EmoLogger.print(Log.State, "" + diffTime + ',' +
                    excitementLong + ',' +
                    excitementShort + ',' +
                    meditation + ',' +
                    boredom + ',' +
                    frustration);
    }

    private void logEEG(long startTime){

        Pointer hData = Edk.INSTANCE.EE_DataCreate();
        IntByReference nSamplesTaken = null;

        Edk.INSTANCE.EE_DataUpdateHandle(0, hData);
        Edk.INSTANCE.EE_DataGetNumberOfSample(hData, nSamplesTaken);

        if (nSamplesTaken != null)
        {
            if (nSamplesTaken.getValue() != 0) {

                //System.out.print("Updated: ");
                //System.out.println(nSamplesTaken.getValue());

                double[] data = new double[nSamplesTaken.getValue()];
                String ans = "";
                for (int sampleIdx=0 ; sampleIdx<nSamplesTaken.getValue(); ++ sampleIdx) {
                    for (int i =  0; i < 14; i++) {

                        Edk.INSTANCE.EE_DataGet(hData, i, data, nSamplesTaken.getValue());
                        //System.out.print(data[sampleIdx]);
                        //System.out.print(",");
                        if(sampleIdx == 0){
                            ans += data[sampleIdx];
                        }

                    }

                    int diffTime = (int)(System.currentTimeMillis() - startTime);

                    if(EmoLogger.enabled)
                        EmoLogger.print(Log.EEG, "" + diffTime + data[sampleIdx]);
                }
            }
        }

    }

    public boolean isConnected(){
        return connected;
    }

}
