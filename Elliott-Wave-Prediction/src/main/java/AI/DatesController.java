package AI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DatesController {
    private PriceFetcher priceFetcher;
    private JFrame parentFrame;

    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ISO_DATE;
    private JButton[] dateInterval = new JButton[2];

    public DatesController(JFrame parentFrame, PriceFetcher priceFetcher){
        this.parentFrame = parentFrame;
        this.priceFetcher = priceFetcher;
    }

    public DateTimeFormatter getDateFormat(){
        return dateFormat;
    }

    private LocalDate parseDateString(String dateStr){
        try {
            LocalDate date = LocalDate.parse(dateStr, dateFormat);

            return date;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentFrame, "Invalid date format. Please enter the date in YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    public JButton buildDateButton(String initialDate, int index){
        LocalDate formattedDate = parseDateString(initialDate);
        JButton inputDate = new JButton(formattedDate.toString());

        inputDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dateStr = JOptionPane.showInputDialog(parentFrame, "Enter the date (YYYY-MM-DD):");

                LocalDate date = parseDateString(dateStr);
                inputDate.setText(date.toString());

                priceFetcher.fetchCryptoPrices(getDateInterval());
            }
        });

        dateInterval[index] = inputDate;
        return inputDate;
    }

    public ArrayList<LocalDate> getDateInterval(){
        ArrayList<LocalDate> interval = new ArrayList<>();

        for (int i = 0; i < 2; i++){
            JButton dateInput = dateInterval[i];

            LocalDate date = parseDateString(dateInput.getText());
            interval.add(date);
        }

        return interval;
    }
}
