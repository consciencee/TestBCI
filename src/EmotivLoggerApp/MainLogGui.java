package EmotivLoggerApp;

import InterfaceVariable.LoggerInterface;
import org.jfree.ui.RefineryUtilities;

public class MainLogGui {
	
	public static void main(String[] args) {
		

		EmoEventLoop insight = new EmoEventLoop();

		if(!insight.isConnected()){
			System.out.println("Startup failed");
			return;
		}

		LoggerInterface loggerFrame = new LoggerInterface();
		RefineryUtilities.centerFrameOnScreen(loggerFrame);
        insight.loop();

	}

}
