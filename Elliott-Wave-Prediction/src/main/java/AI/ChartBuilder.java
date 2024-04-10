package AI;

import java.util.Date;
import java.math.BigDecimal;
import java.util.Calendar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultHighLowDataset;

public class ChartBuilder {
    private static ChartBuilder instance;

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

        ChartPanel chartPanel = new ChartPanel(chart);
        return chartPanel;
    }

    public void updateDatase(Date[] dates, double[] high, double[] low, double[] open, double[] close, double[] volume){
        this.dataset = new DefaultHighLowDataset("BTC", dates, high, low, open, close, volume);
    }
}
