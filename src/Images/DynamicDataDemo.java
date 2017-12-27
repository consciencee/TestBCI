package Images;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
//import org.jfree.ui.ApplicationFrame;
//import org.jfree.ui.RefineryUtilities;


public class DynamicDataDemo extends JFrame /*extends ApplicationFrame*/ /* implements ActionListener*/ {

    //The number of subplots. 
    public static final int SUBPLOT_COUNT = 7;
    
    // The datasets. 
    private TimeSeriesCollection[] datasets;
    
    // The most recent value added to series 1. 
    private double[] lastValue = new double[SUBPLOT_COUNT];

    public DynamicDataDemo(final String title) {

        super(title);
        
        final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new DateAxis("Time"));
        this.datasets = new TimeSeriesCollection[SUBPLOT_COUNT];
        
        init(plot, 0, "excitementLong");
        init(plot, 1, "instantaneousExcitement");
        init(plot, 2, "relaxation");
        init(plot, 3, "stress");
        init(plot, 4, "engagementBoredom");
        init(plot, 5, "interest");
        init(plot, 6, "focus");
       /*
        for (int i = 0; i < SUBPLOT_COUNT; i++) {
            this.lastValue[i] = 100.0;
            final TimeSeries series = new TimeSeries("Data " + i, Millisecond.class);
            this.datasets[i] = new TimeSeriesCollection(series);
            final NumberAxis rangeAxis = new NumberAxis("Y" + i);
            rangeAxis.setAutoRangeIncludesZero(false);
            final XYPlot subplot = new XYPlot(
                    this.datasets[i], null, rangeAxis, new StandardXYItemRenderer()
            );
            subplot.setBackgroundPaint(Color.white);
            subplot.setDomainGridlinePaint(Color.white);
            subplot.setRangeGridlinePaint(Color.white);
            plot.add(subplot);
        }*/

        final JFreeChart chart = new JFreeChart("Get Emotion data", plot);
//        chart.getLegend().setAnchor(Legend.EAST);
        chart.setBorderPaint(Color.black);
        chart.setBorderVisible(true);
        chart.setBackgroundPaint(Color.white);
        
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
  //      plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 4, 4, 4, 4));
        final ValueAxis axis = plot.getDomainAxis();
        axis.setAutoRange(true);
        axis.setFixedAutoRange(60000.0);  // 60 seconds
        
        final JPanel content = new JPanel(new BorderLayout());

        final ChartPanel chartPanel = new ChartPanel(chart);
        content.add(chartPanel);

     //   final JPanel buttonPanel = new JPanel(new FlowLayout());
        
      /*  for (int i = 0; i < SUBPLOT_COUNT; i++) {
            final JButton button = new JButton("Series " + i);
            button.setActionCommand("ADD_DATA_" + i);
            button.addActionListener(this);
            buttonPanel.add(button);
        }
        final JButton buttonAll = new JButton("ALL");
        buttonAll.setActionCommand("ADD_ALL");
        buttonAll.addActionListener(this);
        buttonPanel.add(buttonAll);
        */
       // content.add(buttonPanel, BorderLayout.SOUTH);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 800));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setContentPane(content);

	 /*   WindowListener wndCloser = new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    			System.out.println("Exit");
	    			Variables.flagEndCycle = 1; 	    		
                    e.getWindow().setVisible(false);
                    System.exit(0);
	        }
	    };
	    addWindowListener(wndCloser);
	   */ 
        /*WindowListener wndCloser = new WindowAdapter() {
	    	public void windowClosing(WindowEvent e) {
	    			System.out.println("Exit");   		
                    e.getWindow().setVisible(false);
                    System.exit(0);
	        }
	    };
	    addWindowListener(wndCloser);*/
    }


 /*   public void actionPerformed(final ActionEvent e) {
 
        for (int i = 0; i < SUBPLOT_COUNT; i++) {
            if (e.getActionCommand().endsWith(String.valueOf(i))) {
                final Millisecond now = new Millisecond();
                System.out.println("Now = " + now.toString());
                this.lastValue[i] = this.lastValue[i] * (0.90 + 0.2 * Math.random());
                this.datasets[i].getSeries(0).add(new Millisecond(), this.lastValue[i]);       
            }
        }

        if (e.getActionCommand().equals("ADD_ALL")) {
            final Millisecond now = new Millisecond();
            System.out.println("Now = " + now.toString());
            for (int i = 0; i < SUBPLOT_COUNT; i++) {
                this.lastValue[i] = this.lastValue[i] * (0.90 + 0.2 * Math.random());
                this.datasets[i].getSeries(0).add(new Millisecond(), this.lastValue[i]);       
            }
        }

    }
*/
    public void drawData(double data, int index){
       // this.lastValue[index] = data;
        this.datasets[index].getSeries(0).add(new Millisecond(), data);   
    }
    
    public void drawDataPadding(double data, int index, Date timePadding){
        // this.lastValue[index] = data;
         this.datasets[index].getSeries(0).add(new Millisecond(timePadding), data);   
     }
    
    public void clearData() {
    	for(int i = 0; i < 7; i++)
    		this.datasets[i].getSeries(0).clear();
    }
   /* public static void main(final String[] args) {

        final DynamicDataDemo demo = new DynamicDataDemo("Dynamic Data Demo 3");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
*/
    public void init(CombinedDomainXYPlot plot, int i, String str)
    {
      this.lastValue[i] = 100.0D;
      TimeSeries series = new TimeSeries(str, Millisecond.class);
      this.datasets[i] = new TimeSeriesCollection(series);
      NumberAxis rangeAxis = new NumberAxis(str);
      
      rangeAxis.setRangeWithMargins(0.0D, 1.0D);
      XYPlot subplot = new XYPlot(
        this.datasets[i], null, rangeAxis, new StandardXYItemRenderer());
      
      subplot.setBackgroundPaint(Color.white);
      subplot.setDomainGridlinePaint(Color.lightGray);
      subplot.setRangeGridlinePaint(Color.lightGray);
      plot.add(subplot);
    }
}
