package AI;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class EWFeatureExtraction {

    private JSONArray bar_data;
    ArrayList<Bar> bars = new ArrayList<>();
    ArrayList<Double> openPrices = new ArrayList<>();
    ArrayList<Double> highPrices = new ArrayList<>();
    ArrayList<Double> lowPrices = new ArrayList<>();
    ArrayList<Double> closePrices = new ArrayList<>();
    ArrayList<Double> volumes = new ArrayList<>();


    public ArrayList<Bar> localHighs = new ArrayList<>();
    public ArrayList<Bar> localLows = new ArrayList<>();

    Feature features;



    // public void printData(){
    //     printBars();
    // }

    // private void printBars() {
    //     for (Bar bar : bars) {
    //         System.out.println("Open: " + bar.o + ", High: " + bar.h + ", Low: " + bar.l +
    //                            ", Close: " + bar.c + ", Volume: " + bar.v + ", Time: " + bar.t);
    //     }
    // }

    // private void populate_bars(){
    //     for (int i=0; i < this.bar_data.length(); i++){
    //         JSONObject barJson = bar_data.getJSONObject(i);
    //         Bar bar = new Bar(barJson);
    //         this.bars.add(bar);
    //     }
    // }

    private void get_close_prices(){
        for (Bar bar: bars){
            closePrices.add(bar.c);
        }
    }

    private void get_open_prices(){
        for (Bar bar: bars){

            openPrices.add(bar.o);
        }
    }

    private void get_high_prices() {
        for (Bar bar : bars) {
            highPrices.add(bar.h);
        }
    }
    
    private void get_low_prices() {
        for (Bar bar : bars) {
            lowPrices.add(bar.l);
        }
    }
    
    private void get_volumes() {
        for (Bar bar : bars) {
            volumes.add(bar.v);
        }
    }

    public EWFeatureExtraction(ArrayList<Bar> bars) {

        this.bars = bars;
        get_close_prices();
        get_high_prices();
        get_low_prices();
        get_open_prices();
        get_volumes();
        
        
    }

    public ArrayList<Double> calculateSMA(ArrayList<Double> prices, int period){
        ArrayList<Double> smaValues = new ArrayList<>();
        if (prices.size() < period) {
            return smaValues; // Not enough data to calculate SMA
        }
    
        double sum = 0;
        // Calculate the sum of the initial period
        for (int i = 0; i < period; i++) {
            sum += prices.get(i);
        }
    
        // Calculate the first SMA and initialize the rolling sum
        smaValues.add(sum / period);
    
        // Calculate the rest of the SMA values
        for (int i = period; i < prices.size(); i++) {
            sum += prices.get(i) - prices.get(i - period);
            smaValues.add(sum / period);
        }
    
        return smaValues;
    }

    public void findHighsAndLows(int margin) {
        if (bars == null || bars.isEmpty() || margin <= 0 || margin >= bars.size() / 2) {
            return;
        }
    
        Bar highestBar = bars.get(0);
        Bar lowestBar = bars.get(0);
        ArrayList<Bar> localHighs = new ArrayList<>();
        ArrayList<Bar> localLows = new ArrayList<>();
    
        for (int i = margin; i < bars.size() - margin; i++) {
            Bar currentBar = bars.get(i);

            if (currentBar.h > highestBar.h) {
                highestBar = currentBar;
            }
    
            if (currentBar.l < lowestBar.l) {
                lowestBar = currentBar;
            }
    
            boolean isLocalHigh = true;
            for (int j = i - margin; j <= i + margin; j++) {
                if (j == i) {
                    continue;
                }
                if (currentBar.h <= bars.get(j).h) {
                    isLocalHigh = false;
                    break;
                }
            }
    
            boolean isLocalLow = true;
            for (int j = i - margin; j <= i + margin; j++) {
                if (j == i) {
                    continue;
                }
                if (currentBar.l >= bars.get(j).l) {
                    isLocalLow = false;
                    break;
                }
            }
    
            if (isLocalHigh)
                localHighs.add(currentBar);
    
            if (isLocalLow)
                localLows.add(currentBar);
        }
    
        this.localHighs = localHighs;
        this.localLows = localLows;
    }


    public int getDynamicSMAPeriod() {
        int period;
    
        if (localHighs.isEmpty() || localLows.isEmpty()) {
            // If there are no identified local highs or lows, use a default period
            period = 20; // Or any other suitable default value
        } else {
            // Calculate the length of the current wave
            int currentWaveLength = Math.abs(localHighs.size() - localLows.size());
    
            if (currentWaveLength > 0) {
                // Use the length of the current wave as the SMA period
                period = currentWaveLength;
            } else {
                // If the current wave length is 0 or cannot be determined,
                // use the length of the previous wave
                int prevWaveStart = Math.max(0, localHighs.size() - 2);
                int prevWaveEnd = Math.max(0, localLows.size() - 2);
                int prevWaveLength = Math.abs(prevWaveEnd - prevWaveStart);
    
                if (prevWaveLength > 0) {
                    period = prevWaveLength;
                } else {
                    // If no valid wave length can be determined, use a default period
                    period = 20; // Or any other suitable default value
                }
            }
        }
    
        return period;
    }
    
}

    




