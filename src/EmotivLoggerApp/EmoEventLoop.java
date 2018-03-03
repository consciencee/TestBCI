package EmotivLoggerApp;

import EmotivAPIFiles.*;
import InterfaceVariable.InterfaceVariables;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

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

        while (InterfaceVariables.flagEndCycle == 0){	// endless loop

            state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);

            if (state == EdkErrorCode.EDK_OK.ToInt()) {

                printContactQuality(eState);

                int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);

                if(eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt())
                    logEmoState(startTime);

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

        float excitementLong = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetExcitementLongTermScore(eState);
        float instantaneousExcitement = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetInstantaneousExcitementScore(eState);
        float relaxation = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetRelaxationScore(eState);
        float stress = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetStressScore(eState);
        float engagementBoredom = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetEngagementBoredomScore(eState);
        float interest = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetInterestScore(eState);
        float focus = PerformanceMetrics.INSTANCE.IS_PerformanceMetricGetFocusScore(eState);

        int diffTime = (int)(System.currentTimeMillis() - startTime);

        // TODO: change EmoLogger API and usage
        if(EmoLogger.enabled)
            EmoLogger.print("" + diffTime + ',' +
                    excitementLong + ',' +
                    instantaneousExcitement + ',' +
                    relaxation + ',' +
                    stress + ',' +
                    engagementBoredom + ',' +
                    interest + ',' +
                    focus);
    }

    public boolean isConnected(){
        return connected;
    }

}
