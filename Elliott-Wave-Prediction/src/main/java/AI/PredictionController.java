package AI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.jfree.data.xy.DefaultHighLowDataset;
import org.json.JSONObject;

public class PredictionController {
    private PriceFetcher prices;

    public static ArrayList<Bar> convertToBarList(DefaultHighLowDataset dataset) {
        ArrayList<Bar> barList = new ArrayList<>();

        for (int i = 0; i < dataset.getItemCount(0); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("o", dataset.getOpenValue(0, i));
            jsonObject.put("h", dataset.getHighValue(0, i));
            jsonObject.put("l", dataset.getLowValue(0, i));
            jsonObject.put("c", dataset.getCloseValue(0, i));
            jsonObject.put("v", dataset.getVolumeValue(0, i));
            jsonObject.put("t", dataset.getX(0, i).toString());

            Bar bar = new Bar(jsonObject);
            barList.add(bar);
        }

        return barList;
    }
    
    private void performAnalysis(){
        DefaultHighLowDataset dataset = prices.getDataset();
        ArrayList<Bar> bars = convertToBarList(dataset);
        EWFeatureExtraction wave = new EWFeatureExtraction(bars);

        ArrayList<Double> highs = wave.localHighs;
        ArrayList<Double> lows = wave.localLows;

        for (Double d : highs){
            System.out.println(d);
        }
        for (Double d : lows){
            System.out.println(d);
        }
        // make a new chart builder

        App.buildReportGUI();
    }

    public PredictionController(JButton analyzeButton, PriceFetcher prices){
        this.prices = prices;

        analyzeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("aaaaa");
                performAnalysis();
            }
        });
    }
}
