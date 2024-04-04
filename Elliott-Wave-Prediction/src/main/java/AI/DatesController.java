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

    public JButton buildDateButton(String initialDate, int index){
        JButton inputDate = new JButton(initialDate);

        inputDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String dateStr = JOptionPane.showInputDialog(parentFrame, "Enter the date (YYYY-MM-DD):");

                try {
                    Date date = dateFormat.parse(dateStr);
                    inputDate.setText(date.toString());
                } catch (ParseException parseException) {
                    JOptionPane.showMessageDialog(parentFrame, "Invalid date format. Please enter the date in YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        dateInterval[index] = inputDate;
        return inputDate;
    }

    public ArrayList<Date> getDateInterval(){
        ArrayList<Date> interval = new ArrayList<>();

        for (int i = 0; i < 2; i++){
            JButton dateInput = dateInterval[i];

            try {
                Date date = dateFormat.parse(dateInput.getText());
                interval.add(date);
            } catch (ParseException parseException) {
                JOptionPane.showMessageDialog(parentFrame, "Invalid date format. Please enter the date in YYYY-MM-DD format.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return interval;
    }
}
