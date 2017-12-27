package InterfaceVariable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import org.jfree.ui.RefineryUtilities;

import ExampleAPIFiles.EmoLogger;
import Images.DynamicDataFile;

public class LoggerInterface extends JFrame{
	
	private int cnt = 0;
	
	public LoggerInterface() {
		
		super("Sample");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		String[] emoLabels = {
				"fear",
				"happiness",
				"calmness",
				"sadness",
				"interest",
				"disgust"
				// and so on...
		};
		
		JPanel logPane = new JPanel(new GridLayout(0, 2, 10, 20));  
        //panel.setBackground(Color.white);
        
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
				}
				else{
					EmoLogger.enabled = true;
					startBtt.setText("Stop");
					cnt = 0;
					try {
						EmoLogger.openLogFile();
						EmoLogger.print(labelList.getSelectedItem().toString());
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
        
        final JButton showBtt = new JButton("Show data");
        showBtt.setSize(InterfaceVariables.WIDTH_BUTTON, InterfaceVariables.HEIGHT_BUTTON);
        showBtt.addActionListener(new ActionListener() {		
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
		});
        
        
        JPanel labelsPane = new JPanel(new GridLayout(0, 2, 5, 5));
        labelsPane.add(listLbl);
        labelsPane.add(labelList);
        labelsPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        logPane.add(labelsPane);
        logPane.add(time);
        
        logPane.add(startBtt);
        logPane.add(showBtt);
        
        logPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        logPane.setSize(400, 100);
        add(logPane);
        
        // user settings
        //JPanel settingsPane = new JPanel();
        
        setSize(450, 400);
        //setPreferredSize(new Dimension(512, 256));
        //pack();
        setVisible(true);
        
	}

}
