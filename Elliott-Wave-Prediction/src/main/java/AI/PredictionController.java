package AI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.jfree.data.xy.DefaultHighLowDataset;

public class PredictionController {
    private ChartBuilder chartBuilder;

    private void performAnalysis(){
        DefaultHighLowDataset dataset = chartBuilder.getDataset();
        
    }

    public PredictionController(JButton analyzeButton, ChartBuilder chart){
        this.chartBuilder = chart;

        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performAnalysis();
            }
        });
    }
}
