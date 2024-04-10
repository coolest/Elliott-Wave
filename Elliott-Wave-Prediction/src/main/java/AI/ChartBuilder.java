package AI;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Calendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultHighLowDataset;

public class ChartBuilder {
    private static ChartBuilder instance;

    private JFreeChart chart;
    private DefaultHighLowDataset dataset;
    private ChartBuilder(){
        Date[] date = new Date[0];
        double[] high = new double[0];
        double[] low = new double[0];
        double[] open = new double[0];
        double[] close = new double[0];
        double[] volume = new double[0];
        
        this.dataset = new DefaultHighLowDataset("BTC", date, high, low, open, close, volume);
    }

    public static ChartBuilder getChartBuilder(){
        if (instance == null)
            instance = new ChartBuilder();

        return instance;
    }

    public ChartPanel createTickerChartPanel(String title) {
        JFreeChart chart = ChartFactory.createCandlestickChart(
                "Bitcoin Price Chart",
                "Time",
                "Price",
                this.dataset,
                false
        );

        this.chart = chart;

        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }

    public void updateDataset(DefaultHighLowDataset dataset, double min, double max){
        this.dataset = dataset;

        chart.getXYPlot().setDataset(dataset);
        
        XYPlot plot = (XYPlot) chart.getPlot();
        ValueAxis yAxis = plot.getRangeAxis();

        yAxis.setRange(min - min * 1/25, max + max * 1/25);
    }
}
