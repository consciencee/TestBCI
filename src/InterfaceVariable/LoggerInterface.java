package InterfaceVariable;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import EmotivAPIFiles.Edk;
import EmotivAPIFiles.EmoLogger;
import EmotivAPIFiles.EmoLogger.Log;

public class LoggerInterface extends JFrame{
	
	private int cnt = 0, sNumber = 0;
	
	public LoggerInterface() {
		
		super("Sample");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		String[] emoLabels = {
				"emotion1", // aka happiness
				"emotion2", // aka calmness
				"emotion3", // aka sadness
				"emotion4", // aka disgust
				"emotion5" // aka anger
				// fear and interest?...
		};
		
		JPanel logPane = new JPanel(new GridLayout(0, 2, 10, 20));

		final JTextField usrInput = new JTextField();
		final JLabel usrLbl = new JLabel("Username:"),
		sessionLbl = new JLabel("Session0");
		sessionLbl.setHorizontalAlignment(SwingConstants.CENTER);
        
        final JComboBox labelList = new JComboBox(emoLabels);
        final JLabel listLbl = new JLabel("Class label");
        
        final DateFormat timeFormat = new SimpleDateFormat("mm:ss");
        final JLabel time = new JLabel(timeFormat.format(new Date(0)));
        time.setHorizontalAlignment(SwingConstants.CENTER);
        Timer timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(EmoLogger.enabled) {
	                time.setText(timeFormat.format(new Date(cnt += 1000)));
				}
			}
		});
        timer.setInitialDelay(0);
        timer.start();
        
        final JButton startBtt = new JButton("Start"); 
        startBtt.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);
        startBtt.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {	
				if(EmoLogger.enabled){
					EmoLogger.enabled = false;
					startBtt.setText("Start");
					EmoLogger.closeLogFile();
					sessionLbl.setText("Session" + Integer.toString(++sNumber));
				}
				else{
					EmoLogger.enabled = true;
					startBtt.setText("Stop");
					cnt = 0;
					try {
						EmoLogger.openLogFile(usrInput.getText());
						EmoLogger.print(Log.State, sessionLbl.getText());
						EmoLogger.print(Log.State, labelList.getSelectedItem().toString());
                        EmoLogger.print(Log.EEG, sessionLbl.getText());
                        EmoLogger.print(Log.EEG, labelList.getSelectedItem().toString());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
        
        final JButton showBtt = new JButton("Show data");
        showBtt.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);
        // TODO
        /*showBtt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				if(!EmoLogger.enabled){
					
					DynamicDataFile demo = new DynamicDataFile("Mental state data");
			        demo.pack();
			        RefineryUtilities.centerFrameOnScreen(demo);
			        demo.setVisible(true);
			        
			        try {
						demo.drawContents("log.csv");
					} catch (IOException exc) {
						// TODO Auto-generated catch block
						exc.printStackTrace();
					}
				}
			}
		});*/

        JPanel usrPane = new JPanel(new GridLayout(1, 3, 5, 5));
        usrPane.add(usrLbl);
        usrPane.add(usrInput);
        
        JPanel labelsPane = new JPanel(new GridLayout(0, 2, 5, 5));
        labelsPane.add(listLbl);
        labelsPane.add(labelList);
        labelsPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        logPane.add(usrPane);
        logPane.add(sessionLbl);
        logPane.add(labelsPane);
        logPane.add(time);
        
        logPane.add(startBtt);
        logPane.add(showBtt);
        
        logPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        logPane.setSize(500, 150);
        add(logPane);

        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                InterfaceVariables.flagEndCycle = 1;
                Edk.INSTANCE.EE_EngineDisconnect();
                e.getWindow().setVisible(false);
                System.exit(0);
            }
        };
        addWindowListener(wndCloser);

        setPreferredSize(new Dimension(512, 256));
        pack();
        setVisible(true);
        
	}

}
