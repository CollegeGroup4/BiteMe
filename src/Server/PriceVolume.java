package Server;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.*;
import java.util.*;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.entity.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.labels.*;
import org.jfree.chart.panel.*;
import org.jfree.chart.plot.*;

public class PriceVolume extends JPanel implements ChartMouseListener    // A demo application for price-volume chart.   
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
ChartPanel panel;
  TimeSeries Price_series=new TimeSeries("Price");
  TimeSeries Volume_Series=new TimeSeries("Volume");
  Crosshair xCrosshair,yCrosshair;
  static Vector<String> Volume_Color_Vector=new Vector();

  public PriceVolume(String Symbol)
  {
    JFreeChart chart=createChart(Symbol);
    panel=new ChartPanel(chart,true,true,true,false,true);
    panel.setPreferredSize(new Dimension(1000,500));
    panel.addChartMouseListener(this);
    CrosshairOverlay crosshairOverlay=new CrosshairOverlay();
    float[] dash={2f,0f,2f};
    BasicStroke bs=new BasicStroke(1,BasicStroke.CAP_BUTT,BasicStroke.JOIN_ROUND,1.0f,dash,2f);

    xCrosshair=new Crosshair(Double.NaN,Color.GRAY,bs);
    xCrosshair.setLabelBackgroundPaint(new Color(0f,0f,0f,1f));
    xCrosshair.setLabelFont(xCrosshair.getLabelFont().deriveFont(14f));
    xCrosshair.setLabelPaint(new Color(1f,1f,1f,1f));
    
    xCrosshair.setLabelGenerator(new CrosshairLabelGenerator()
    {
      @Override
      public String generateLabel(Crosshair crosshair)
      {
        long ms=(long)crosshair.getValue();
        TimeSeriesDataItem item=null;
        for (int i=0;i<Volume_Series.getItemCount();i++)
        {
          item=Volume_Series.getDataItem(i);
          if (ms==item.getPeriod().getFirstMillisecond()) break;
        }
        long volume=item.getValue().longValue();
        return NumberFormat.getInstance().format(volume);
      }
    });

    xCrosshair.setLabelVisible(true);
    yCrosshair=new Crosshair(Double.NaN,Color.GRAY,bs);
    yCrosshair.setLabelBackgroundPaint(new Color(0f,0f,0f,1f));
    yCrosshair.setLabelFont(xCrosshair.getLabelFont().deriveFont(14f));
    yCrosshair.setLabelPaint(new Color(1f,1f,1f,1f));
    yCrosshair.setLabelVisible(true);
    crosshairOverlay.addDomainCrosshair(xCrosshair);
    crosshairOverlay.addRangeCrosshair(yCrosshair);
    panel.addOverlay(crosshairOverlay);
    add(panel);
    try {
		ChartUtils.saveChartAsPNG(new File("C://Users/MoshPe/Desktop/histogram.png"), chart, 1000, 600);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
/*
    xCrosshair.setValue(1.5959952E12);
    xCrosshair.setVisible(true);
    yCrosshair.setValue(45.230579);
    yCrosshair.setVisible(true);
*/
  }

  private JFreeChart createChart(String Symbol)
  {
    createPriceDataset(Symbol);
    XYDataset priceData=new TimeSeriesCollection(Price_series);
    JFreeChart chart=ChartFactory.createTimeSeriesChart(Symbol,"Date",getYLabel("Price($)"),priceData,true,true,true);
    XYPlot plot=chart.getXYPlot();
    plot.setBackgroundPaint(new Color(192,196,196));
    NumberAxis rangeAxis1=(NumberAxis)plot.getRangeAxis();
    rangeAxis1.setLowerMargin(0.40);                                           // Leave room for volume bars
    plot.getRenderer().setDefaultToolTipGenerator(new StandardXYToolTipGenerator(StandardXYToolTipGenerator.DEFAULT_TOOL_TIP_FORMAT,new SimpleDateFormat("yyyy-MM-d"),NumberFormat.getCurrencyInstance()));

    NumberAxis rangeAxis2=new NumberAxis("Volume");
    rangeAxis2.setUpperMargin(1.00);                                           // Leave room for price line   
    rangeAxis2.setNumberFormatOverride(NumberFormat.getNumberInstance());
    plot.setRangeAxis(1,rangeAxis2);
    plot.setDataset(1,new TimeSeriesCollection(Volume_Series));
    plot.setRangeAxis(1,rangeAxis2);
    plot.mapDatasetToRangeAxis(1,1);
    MyRender Renderer=new MyRender();
    Renderer.setShadowVisible(false);
    plot.setRenderer(1,Renderer);

    DateAxis domainAxis=(DateAxis) plot.getDomainAxis();                     // Consider adjusting the lower margin of the domain axis for symmetry.
    domainAxis.setLowerMargin(0.05);

    return chart;
  }

  private void createPriceDataset(String Symbol)
  {
    String Lines[]=new String[21],Items[],Date;
    int Year, Month, Day;
    long Volume,Last_Volume=0;
    double Price;

    Lines[0]="Date,Open,High,Low,Close,Adj Close,Volume";
    Lines[1]="2020-07-17,44.110001,44.369999,41.919998,42.509998,42.323395,849700";
    Lines[2]="2020-07-20,41.630001,41.680000,39.669998,40.119999,39.943886,1319300";
    Lines[3]="2020-07-21,40.880001,42.860001,40.860001,42.270000,42.084450,2070300";
    Lines[4]="2020-07-22,41.919998,42.700001,41.090000,42.570000,42.383133,1317600";
    Lines[5]="2020-07-23,43.919998,46.389999,43.279999,44.759998,44.563519,1917700";
    Lines[6]="2020-07-24,46.500000,46.500000,43.950001,44.410000,44.215057,1384600";
    Lines[7]="2020-07-27,44.000000,44.240002,42.610001,43.860001,43.667469,799800";
    Lines[8]="2020-07-28,43.389999,44.590000,42.930000,43.020000,42.831158,699700";
    Lines[9]="2020-07-29,42.759998,45.590000,42.740002,45.430000,45.230579,826200";
    Lines[10]="2020-07-30,44.160000,44.639999,42.959999,44.500000,44.304661,798100";
    Lines[11]="2020-07-31,44.330002,44.419998,42.580002,44.360001,44.165276,1037800";
    Lines[12]="2020-08-03,44.560001,45.599998,43.419998,44.939999,44.742729,797000";
    Lines[13]="2020-08-04,44.900002,45.500000,43.450001,43.540001,43.348877,971100";
    Lines[14]="2020-08-05,44.860001,45.389999,43.650002,45.330002,45.131020,902000";
    Lines[15]="2020-08-06,45.049999,46.279999,44.330002,45.299999,45.101147,645200";
    Lines[16]="2020-08-07,44.849998,46.189999,44.189999,46.150002,45.947418,604900";
    Lines[17]="2020-08-10,46.669998,48.410000,46.549999,47.290001,47.082417,960200";
    Lines[18]="2020-08-11,49.110001,50.849998,48.799999,48.910000,48.695301,1187700";
    Lines[19]="2020-08-12,49.759998,50.009998,47.060001,47.840000,47.630001,752800";
    Lines[20]="2020-08-13,46.950001,48.369999,46.459999,47.110001,47.110001,535700";

    for (int i=1;i<Lines.length;i++)
    {
      Items=Lines[i].split(",");
      Date=Items[0].replace("-0","-");
      Price=Double.parseDouble(Items[5]);
      Volume=Long.parseLong(Items[6]);
      Items=Date.split("-");
      Year=Integer.parseInt(Items[0]);
      Month=Integer.parseInt(Items[1]);
      Day=Integer.parseInt(Items[2]);
      Price_series.add(new Day(Day,Month,Year),Price);
      Volume_Series.add(new Day(Day,Month,Year),Volume);
      Volume_Color_Vector.add(Volume>=Last_Volume?"+":"-");
      Last_Volume=Volume;
    }
  }

  @Override
  public void chartMouseClicked(ChartMouseEvent event)
  {
    // ignore
  }

  public void chartMouseMoved(ChartMouseEvent cmevent)
  {
    ChartEntity chartentity=cmevent.getEntity();
    if (chartentity instanceof XYItemEntity)
    {
      XYItemEntity e=(XYItemEntity)chartentity;
      XYDataset d=e.getDataset();
      int s=e.getSeriesIndex();
      int i=e.getItem();
      double x=d.getXValue(s,i);
      double y=d.getYValue(s,i);
      Out("x = "+x+"  y = "+y);
      xCrosshair.setValue(x);
      yCrosshair.setValue(y);
    }
  }

  String getYLabel(String Text)
  {
    String Result="";

    for (int i=0;i<Text.length();i++) Result+=Text.charAt(i)+(i<Text.length()-1?"":"");
//    Out(Result);
    return Result;
  }

  private static void out(String message) { System.out.print(message); }

  private static void Out(String message) { System.out.println(message); }

  // Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
  static void Create_And_Show_GUI()
  {
    final PriceVolume demo=new PriceVolume("ADS");

    JFrame frame=new JFrame("PriceVolume_Chart Frame");
    frame.add(demo);
    frame.addWindowListener(new WindowAdapter()
    {
      public void windowActivated(WindowEvent e) { }
      public void windowClosed(WindowEvent e) { }
      public void windowClosing(WindowEvent e) { System.exit(0); }
      public void windowDeactivated(WindowEvent e) { }
      public void windowDeiconified(WindowEvent e) { demo.repaint(); }
      public void windowGainedFocus(WindowEvent e) { demo.repaint(); }
      public void windowIconified(WindowEvent e) { }
      public void windowLostFocus(WindowEvent e) { }
      public void windowOpening(WindowEvent e) { demo.repaint(); }
      public void windowOpened(WindowEvent e) { }
      public void windowResized(WindowEvent e) { demo.repaint(); }
      public void windowStateChanged(WindowEvent e) { demo.repaint(); }
    });
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public static void main(String[] args)
  {
    // Schedule a job for the event-dispatching thread : creating and showing this application's GUI.
    SwingUtilities.invokeLater(new Runnable() { public void run() { Create_And_Show_GUI(); } });
  }
}

class MyRender extends XYBarRenderer
{
  @Override
  public Paint getItemPaint(int row,int col)
  {
    this.setBarAlignmentFactor(0.5);
//    System.out.println(row+" "+col+" "+super.getItemPaint(row,col));
    return PriceVolume.Volume_Color_Vector.elementAt(col).equals("+")?super.getItemPaint(row,col):new Color(0.56f,0.2f,0.5f,1f);
  }
}