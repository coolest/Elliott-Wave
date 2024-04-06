package AI;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class App 
{
    private static DatesController datesController;
    private static PredictionController predictionController;
    private static PriceFetcher priceFetcher;

    private static void buildGUI(){
        JFrame frame = new JFrame("Elliott Wave Predictor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel borderPanel = new JPanel(new BorderLayout());
        JPanel flowLayoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        borderPanel.add(flowLayoutPanel, BorderLayout.SOUTH);

        datesController = new DatesController(frame);

        LocalDateTime todayAtMidnight = LocalDate.now().atStartOfDay();
        JButton startDateButton = datesController.buildDateButton(todayAtMidnight.toString(), 0);
        JButton endDateButton = datesController.buildDateButton(todayAtMidnight.minusWeeks(1).toString(), 1);
        flowLayoutPanel.add(endDateButton);
        flowLayoutPanel.add(startDateButton);

        JButton analyzeButton = new JButton("Analyze");
        borderPanel.add(analyzeButton, BorderLayout.NORTH);

        ChartBuilder chartBuilder = ChartBuilder.getChartBuilder();
        borderPanel.add(chartBuilder.createTickerChartPanel("Bitcoin Prices"), BorderLayout.CENTER);

        priceFetcher = new PriceFetcher(datesController, chartBuilder);
        predictionController = new PredictionController(analyzeButton, priceFetcher);
        
        // Display the window.
        frame.add(borderPanel);
        frame.setSize(800, 300);
        frame.setVisible(true);
    }

    public static void main( String[] args )
    {
        buildGUI();
    }
}
