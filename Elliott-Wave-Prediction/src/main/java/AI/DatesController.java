package AI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DatesController {
    private PriceFetcher priceFetcher;
    private JFrame parentFrame;

    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY-MM-DD");
    private JButton[] dateInterval = new JButton[2];

    public DatesController(JFrame parentFrame, PriceFetcher priceFetcher){
        this.parentFrame = parentFrame;
        this.priceFetcher = priceFetcher;
    }

    public DateTimeFormatter getDateFormat(){
        return dateFormat;
    }

    private Date parseDateString(String dateStr){
        try {
            System.out.println(dateStr);
            Date date = (Date) dateFormat.parse(dateStr);

            return date;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid date format. Please enter the date in YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public JButton buildDateButton(String initialDate, int index){
        Date formattedDate = parseDateString(initialDate);
        JButton inputDate = new JButton(formattedDate.toString());

        inputDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dateStr = JOptionPane.showInputDialog(parentFrame, "Enter the date (YYYY-MM-DD):");

                Date date = parseDateString(dateStr);
                inputDate.setText(date.toString());

                priceFetcher.fetchCryptoPrices(getDateInterval());
            }
        });

        dateInterval[index] = inputDate;
        return inputDate;
    }

    public ArrayList<Date> getDateInterval(){
        ArrayList<Date> interval = new ArrayList<>();

        for (int i = 0; i < 2; i++){
            JButton dateInput = dateInterval[i];

            Date date = parseDateString(dateInput.getText());
            interval.add(date);
        }

        return interval;
    }
}
