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
    
    public static Object[] predictNextWavePriceAction(ArrayList<Bar> localHighs, ArrayList<Bar> localLows) {
        if (localHighs.isEmpty() || localLows.isEmpty()) {
            return new Object[]{0.0, 0L};
        }
    
        int highsCount = localHighs.size();
        int lowsCount = localLows.size();
    
        if (highsCount < 2 || lowsCount < 2) {
            return new Object[]{0.0, 0L};
        }
    
        Bar lastHigh = localHighs.get(highsCount - 1);
        Bar secondLastHigh = localHighs.get(highsCount - 2);
    
        Bar lastLow = localLows.get(lowsCount - 1);
        Bar secondLastLow = localLows.get(lowsCount - 2);
    
        double predictedPrice = 0.0;
        long predictedTime = 0L;
    
        if (lastHigh.t.compareTo(lastLow.t) > 0) {
            // Last high is more recent than last low
            if (lastHigh.h > secondLastHigh.h) {
                // Upward impulse wave
                double highDiff = lastHigh.h - secondLastHigh.h;
                predictedPrice = lastHigh.h + highDiff;
    
                long lastHighTime = Long.parseLong(lastHigh.t);
                long secondLastHighTime = Long.parseLong(secondLastHigh.t);
                long timeDiff = lastHighTime - secondLastHighTime;
                predictedTime = lastHighTime + timeDiff;
            } else if (lastHigh.h < secondLastHigh.h) {
                // Downward corrective wave
                double lowDiff = secondLastLow.l - lastLow.l;
                predictedPrice = lastLow.l - lowDiff;
    
                long lastLowTime = Long.parseLong(lastLow.t);
                long secondLastLowTime = Long.parseLong(secondLastLow.t);
                long timeDiff = lastLowTime - secondLastLowTime;
                predictedTime = lastLowTime + timeDiff;
            }
        } else {
            // Last low is more recent than last high
            if (lastLow.l < secondLastLow.l) {
                // Downward impulse wave
                double lowDiff = secondLastLow.l - lastLow.l;
                predictedPrice = lastLow.l - lowDiff;
    
                long lastLowTime = Long.parseLong(lastLow.t);
                long secondLastLowTime = Long.parseLong(secondLastLow.t);
                long timeDiff = lastLowTime - secondLastLowTime;
                predictedTime = lastLowTime + timeDiff;
            } else if (lastLow.l > secondLastLow.l) {
                // Upward corrective wave
                double highDiff = lastHigh.h - secondLastHigh.h;
                predictedPrice = lastHigh.h + highDiff;
    
                long lastHighTime = Long.parseLong(lastHigh.t);
                long secondLastHighTime = Long.parseLong(secondLastHigh.t);
                long timeDiff = lastHighTime - secondLastHighTime;
                predictedTime = lastHighTime + timeDiff;
            }
        }
    
        return new Object[]{predictedPrice, predictedTime};
    }

    private void performAnalysis(){
        DefaultHighLowDataset dataset = prices.getDataset();
        ArrayList<Bar> bars = convertToBarList(dataset);
        EWFeatureExtraction wave = new EWFeatureExtraction(bars);
        wave.findHighsAndLows(3);

        ArrayList<Bar> highs = wave.localHighs;
        ArrayList<Bar> lows = wave.localLows;

        Object[] result = predictNextWavePriceAction(highs, lows);
        double priceAction = (double) result[0];
        long priceActionTime = (long) result[1];
        
        App.buildReportGUI(priceAction, priceActionTime);
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
