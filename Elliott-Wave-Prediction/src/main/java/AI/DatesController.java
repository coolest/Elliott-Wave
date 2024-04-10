package AI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DatesController {
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public JFrame parentFrame;
    private JButton[] dateInterval = new JButton[2];

    public DatesController(JFrame parentFrame){
        this.parentFrame = parentFrame;
    }

    private Date parseDateString(String dateStr){
        try {
            System.out.println(dateStr);
            Date date = dateFormat.parse(dateStr);

            return date;
        } catch (ParseException parseException) {
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
                // System.out.println(date.toString());
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
