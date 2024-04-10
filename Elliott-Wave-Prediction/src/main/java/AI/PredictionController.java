package AI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.jfree.data.xy.DefaultHighLowDataset;

public class PredictionController {
    private PriceFetcher prices;

    private void performAnalysis(){
        DefaultHighLowDataset dataset = prices.getDataset();

        // make a new chart builder

        App.buildReportGUI();
    }

    public PredictionController(JButton analyzeButton, PriceFetcher prices){
        this.prices = prices;

        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                performAnalysis();
            }
        });
    }
}
