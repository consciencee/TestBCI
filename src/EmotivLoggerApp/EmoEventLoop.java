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

        int af3Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_AF3.ordinal());
        int af4Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_AF4.ordinal());
        int t7Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_T7.ordinal());
        int t8Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_T8.ordinal());
        int o1Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_O1.ordinal());
        int f7Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_F7.ordinal());
        int fp1Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_FP1.ordinal());
        int fp2Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_FP2.ordinal());
        int f3Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_F3.ordinal());
        int fc5Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_FC5.ordinal());
        int p7Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_P7.ordinal());
        int o2Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_O2.ordinal());
        int p8Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_P8.ordinal());
        int fc6Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_FC6.ordinal());
        int f4Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_F4.ordinal());
        int f8Level = EmoState.INSTANCE.ES_GetContactQuality(eState, EmoState.EE_InputChannels_t.EE_CHAN_F8.ordinal());

        System.out.println("FP1: " + fp1Level + ", FP2: " + fp2Level +
                ", AF3: " + af3Level + ", AF4: " + af4Level +
                ", F7: " + f7Level + ", F8: " + f8Level +
                ", F3: " + f3Level + ", F4: " + f4Level +
                ", FC5: " + fc5Level + ", FC6: " + fc6Level +
                ", T7: " + t7Level + ", T8: " + t8Level +
                ", P7: " + p7Level + ", P8: " + p8Level +
                ", PZ01: " + o1Level + ", PZ02: " + o2Level);



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
        IntByReference nSamplesTaken = new IntByReference(0);

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
