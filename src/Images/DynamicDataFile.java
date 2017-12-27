package Images;

import Images.DynamicDataDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class DynamicDataFile extends DynamicDataDemo{
	
	private int setRange = 60000; // 60 s - size of visible set	
	private int upperBound = setRange, lowerBound = 0, step = 5000;
	Boolean isLastSet, isFirstSet = true;;
	
	String file;

	public DynamicDataFile(String title) {
		
		super(title);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel bttns = new JPanel(new BorderLayout());
		
		final JButton prevBtt = new JButton(">");
		final JButton nextBtt = new JButton("<");
		
		prevBtt.setEnabled(false);
		prevBtt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upperBound -= step;
				lowerBound -= step;
				if(lowerBound == 0)
					prevBtt.setEnabled(false);
				
				try {
					drawContents(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				if(isLastSet)
					nextBtt.setEnabled(false);
				else
					nextBtt.setEnabled(true);
			}
		});
		
		
		nextBtt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isLastSet)
					nextBtt.setEnabled(false);
				else {
				upperBound += step;
				lowerBound += step;
				prevBtt.setEnabled(true);
				try {
					drawContents(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(isLastSet)
					nextBtt.setEnabled(false);
				}
			}
		});
		
		bttns.add(prevBtt, BorderLayout.EAST);
		bttns.add(nextBtt, BorderLayout.WEST);
		bttns.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		super.add(bttns, BorderLayout.SOUTH);
        
	}
	
	public void drawContents(String fileName) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;
        
        file = fileName;
        
        super.clearData();
        
        br.readLine(); // ignore header

        while ((line = br.readLine()) != null) {
        	
        	if(line.isEmpty())
        		continue;
        	
            String[] fields = line.split(",");
            
            // skip class label
            // TO DO: handle it by the another way
            if(fields.length == 1)
            	continue;
            
            int timeStamp = Integer.parseInt(fields[0]);
            if(timeStamp <= upperBound && timeStamp >= lowerBound) {
            Date timePadding = new Date(timeStamp);
            super.drawDataPadding(Double.parseDouble(fields[1]), 0, timePadding);
            super.drawDataPadding(Double.parseDouble(fields[2]), 1, timePadding);
            super.drawDataPadding(Double.parseDouble(fields[3]), 2, timePadding);
            super.drawDataPadding(Double.parseDouble(fields[4]), 3, timePadding);
            super.drawDataPadding(Double.parseDouble(fields[5]), 4, timePadding);
            super.drawDataPadding(Double.parseDouble(fields[6]), 5, timePadding);
            super.drawDataPadding(Double.parseDouble(fields[7]), 6, timePadding);
            }
            
            if(timeStamp > upperBound)
            	break;
        }
        
        isLastSet = (line == null || line.isEmpty()); // TO DO: handle empty lines in a more proper way

        br.close();		
	}
	
}
