package AI;

import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class EWFeatureExtraction {

    static class Bar {

        public double o; // open
        public double h; // high
        public double l; // low
        public double c; // close
        public double v; // volume
        public String t; // time

        public Bar(JSONObject jsonObject) {
            this.o = jsonObject.getDouble("o");
            this.h = jsonObject.getDouble("h");
            this.l = jsonObject.getDouble("l");
            this.c = jsonObject.getDouble("c");
            this.v = jsonObject.getDouble("v");
            this.t = jsonObject.getString("t");
        }
    }
    private JSONArray bar_data;
    ArrayList<Bar> bars = new ArrayList<>();
    ArrayList<Double> openPrices = new ArrayList<>();
    ArrayList<Double> highPrices = new ArrayList<>();
    ArrayList<Double> lowPrices = new ArrayList<>();
    ArrayList<Double> closePrices = new ArrayList<>();
    ArrayList<Double> volumes = new ArrayList<>();


    public ArrayList<Double> localHighs = new ArrayList<>();
    public ArrayList<Double> localLows = new ArrayList<>();


    private double findMax(ArrayList<Double> prices) {
        return Collections.max(prices);
    }

    // Method to find the minimum price in a list
    private double findMin(ArrayList<Double> prices) {
        return Collections.min(prices);
    }

    public void printData(){
        printBars();
    }

    private void printBars() {
        for (Bar bar : bars) {
            System.out.println("Open: " + bar.o + ", High: " + bar.h + ", Low: " + bar.l +
                               ", Close: " + bar.c + ", Volume: " + bar.v + ", Time: " + bar.t);
        }
    }

    private void populate_bars(){
        for (int i=0; i < this.bar_data.length(); i++){
            JSONObject barJson = bar_data.getJSONObject(i);
            Bar bar = new Bar(barJson);
            this.bars.add(bar);
        }
    }

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

    public EWFeatureExtraction(String jsonFilePath) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
            JSONObject json = new JSONObject(content);
            this.bar_data = json.getJSONObject("bars").getJSONArray("BTC/USD");
        } catch (Exception e) {
            e.printStackTrace();
        }
        populate_bars();
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

    public void findLocalHighsAndLows(int radius) {
        for (int i = radius; i < highPrices.size() - radius; i++) {
            boolean isLocalHigh = true;
            boolean isLocalLow = true;
            for (int j = i - radius; j <= i + radius; j++) {
                if (j != i) {
                    if (highPrices.get(i) <= highPrices.get(j)) {
                        isLocalHigh = false;
                    }
                    if (lowPrices.get(i) >= lowPrices.get(j)) {
                        isLocalLow = false;
                    }
                }
            }
            if (isLocalHigh) {
                localHighs.add(highPrices.get(i));
            }
            if (isLocalLow) {
                localLows.add(lowPrices.get(i));
            }
        }
    }

   

    // Calculate Fibonacci retracement levels from given peak and trough
    public void calculateFibonacciLevels() {
        if (localHighs.isEmpty() || localLows.isEmpty()) {
            System.out.println("Insufficient data to calculate Fibonacci levels.");
            return;
        }
    
        // Assuming localHighs and localLows are sorted or the latest is the most relevant
        double lastHigh = localHighs.get(localHighs.size() - 1);
        double lastLow = localLows.get(localLows.size() - 1);
    
        double range = lastHigh - lastLow;
    
        double retracement38 = lastHigh - range * 0.382;
        double retracement50 = lastHigh - range * 0.500;
        double retracement61 = lastHigh - range * 0.618;
    
        System.out.println("Fibonacci Retracement Levels:");
        System.out.println("38.2%: " + retracement38);
        System.out.println("50%: " + retracement50);
        System.out.println("61.8%: " + retracement61);
    }
    
}

    




