package AI;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;

import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * Hello world!
 *
 */
public class App 
{
    private static void buildGUI(){
        JFrame frame = new JFrame("Elliott Wave Predictor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel borderPanel = new JPanel(new BorderLayout());
        JPanel flowLayoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        borderPanel.add(flowLayoutPanel, BorderLayout.SOUTH);

        LocalDateTime[] startEndPrefix = getStartandEndPrefix();
        JButton startDateButton = buildDateButton(frame, startEndPrefix[0].toString());
        JButton endDateButton = buildDateButton(frame, startEndPrefix[1].toString());
        flowLayoutPanel.add(startDateButton);
        flowLayoutPanel.add(endDateButton);
        
        ChartBuilder chartBuilder = ChartBuilder.getChartBuilder();
        borderPanel.add(chartBuilder.createTickerChartPanel("Bitcoin Prices"), BorderLayout.CENTER);

        // Display the window.
        frame.add(borderPanel);
        frame.setSize(800, 300);
        frame.setVisible(true);
    }

    private static Date startDate;
    private static Date endDate;

    private static LocalDateTime[] getStartandEndPrefix(){
        LocalDateTime todayAtMidnight = LocalDate.now().atStartOfDay(); // Today at 00:00:00
        LocalDateTime weekAgoAtMidnight = todayAtMidnight.minusWeeks(1); // A week ago at 00:00:00

        LocalDateTime[] dateTimes = {todayAtMidnight, weekAgoAtMidnight};
        return dateTimes;
    }
    private static JButton buildDateButton(JFrame frame, String prompt){
        JButton inputDate = new JButton(prompt);

        inputDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Use JOptionPane to get the date as a string
                String dateStr = JOptionPane.showInputDialog(frame, "Enter the date (YYYY-MM-DD):");

                // Optional: Parse the date string to a Date object to validate or use
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = dateFormat.parse(dateStr);
                    // Use the date for your application needs
                    System.out.println("Date entered: " + date);
                } catch (ParseException parseException) {
                    JOptionPane.showMessageDialog(frame, "Invalid date format. Please enter the date in YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException nullPointerException) {
                    // Handle case where user cancels the input dialog
                    System.out.println("No date entered.");
                }
            }
        });
        return inputDate;
    }

    public static void main( String[] args )
    {
        buildGUI();
    }
}
