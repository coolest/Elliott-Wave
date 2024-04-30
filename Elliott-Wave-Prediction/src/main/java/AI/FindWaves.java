package AI;

import java.util.ArrayList;
import java.util.Comparator;

public class FindWaves {

    private Fibonacci fibonacci;
    private ArrayList<Double> localHighs;
    private ArrayList<Double> localLows;
    private ArrayList<Double> sma;
    private static final int IMPULSE_WINDOW_SIZE = 5;
        private static final int CORRECTIVE_WINDOW_SIZE = 3;

    public FindWaves(Feature feature) {
        this.fibonacci = feature.fibonacci;
        this.localHighs = feature.localHighs;
        this.localLows = feature.localLows;
        this.sma = feature.sma;
        
    }

    public ArrayList<Wave> identifyWaves() {
        ArrayList<Wave> impulseWaves = findImpulseWaves();
        ArrayList<Wave> correctiveWaves = findCorrectiveWaves();

        ArrayList<Wave> allWaves = new ArrayList<>();
        allWaves.addAll(impulseWaves);
        allWaves.addAll(correctiveWaves);

        // Sort the waves based on their start index
        allWaves.sort(Comparator.comparingInt(Wave::getStartIndex));

        return allWaves;
    }

    private ArrayList<Wave> findImpulseWaves() {
        ArrayList<Wave> impulseWaves = new ArrayList<>();

        for (int i = 0; i <= localHighs.size() - IMPULSE_WINDOW_SIZE; i++) {
            ArrayList<Double> windowHighs = new ArrayList<>(localHighs.subList(i, i + IMPULSE_WINDOW_SIZE));
            ArrayList<Double> windowLows = new ArrayList<>(localLows.subList(i, i + IMPULSE_WINDOW_SIZE));
            ArrayList<Double> windowSMA = new ArrayList<>(sma.subList(i, i + IMPULSE_WINDOW_SIZE));

            if (isImpulseWave(windowHighs, windowLows, windowSMA)) {
                impulseWaves.add(new Wave(WaveType.IMPULSE, i, i + IMPULSE_WINDOW_SIZE - 1));
            }
        }

        return impulseWaves;
    }

    private ArrayList<Wave> findCorrectiveWaves() {
        ArrayList<Wave> correctiveWaves = new ArrayList<>();

        for (int i = 0; i <= localHighs.size() - CORRECTIVE_WINDOW_SIZE; i++) {
            ArrayList<Double> windowHighs = new ArrayList<>(localHighs.subList(i, i + CORRECTIVE_WINDOW_SIZE));
            ArrayList<Double> windowLows = new ArrayList<>(localLows.subList(i, i + CORRECTIVE_WINDOW_SIZE));
            ArrayList<Double> windowSMA = new ArrayList<>(sma.subList(i, i + CORRECTIVE_WINDOW_SIZE));

            if (isCorrectiveWave(windowHighs, windowLows, windowSMA)) {
                correctiveWaves.add(new Wave(WaveType.CORRECTIVE, i, i + CORRECTIVE_WINDOW_SIZE - 1));
            }
        }

        return correctiveWaves;
    }
    private boolean isImpulseWave() {
        // Check if the ArrayLists have at least 5 elements (minimum for an impulse wave)
        // if (localHighs.size() < 5 || localLows.size() < 5 || sma.size() < 5) {
        //     return false;
        // }
    
        // // Check if the overall trend is up (last local high > first local high)
        // double firstHigh = localHighs.get(0);
        // double lastHigh = localHighs.get(localHighs.size() - 1);
        // if (lastHigh <= firstHigh) {
        //     return false;
        // }
    
        // // Check if the local highs and lows form a 5-wave pattern
        // for (int i = 0; i < 5; i++) {
        //     double high = localHighs.get(i);
        //     double low = localLows.get(i);
    
        //     // Wave 1: Should retrace to 0.618 or 0.5 of the previous wave
        //     if (i == 0) {
        //         // No previous wave to check
        //     } else if (i == 1) {
        //         double prevHigh = localHighs.get(i - 1);
        //         double retraceLevel = fibonacci.getRetraceLevel(prevHigh, low, 0.618);
        //         if (low > retraceLevel && low > fibonacci.getRetraceLevel(prevHigh, low, 0.5)) {
        //             return false;
        //         }
        //     }
    
        //     // Wave 3: Should be the longest wave and extend beyond 1.618 of Wave 1
        //     if (i == 2) {
        //         double wave1Range = localHighs.get(1) - localLows.get(0);
        //         double wave3ExtensionLevel = fibonacci.getExtensionLevel(localLows.get(0), wave1Range, 1.618);
        //         if (high < wave3ExtensionLevel) {
        //             return false;
        //         }
        //     }
    
        //     // Wave 4: Should retrace to 0.382 or 0.236 of Wave 3
        //     if (i == 3) {
        //         double prevHigh = localHighs.get(i - 1);
        //         double retraceLevel = fibonacci.getRetraceLevel(prevHigh, low, 0.382);
        //         if (low < retraceLevel && low < fibonacci.getRetraceLevel(prevHigh, low, 0.236)) {
        //             return false;
        //         }
        //     }
        // }
    }

    private boolean isCorrectiveWave(ArrayList<Double> localHighs, ArrayList<Double> localLows,
        ArrayList<Double> sma, Fibonacci fibonacci) {
    // Implement rules and conditions for identifying a corrective wave
    // ...
    }

    private boolean isZigZag(ArrayList<Double> localHighs, ArrayList<Double> localLows,
    ArrayList<Double> sma, Fibonacci fibonacci) {
    // Implement rules and conditions for identifying a zigzag pattern
    // ...
    }

    private boolean isFlat(ArrayList<Double> localHighs, ArrayList<Double> localLows,
    ArrayList<Double> sma, Fibonacci fibonacci) {
    // Implement rules and conditions for identifying a flat pattern
    // ...
    }

    private boolean isTriangle(ArrayList<Double> localHighs, ArrayList<Double> localLows,
    ArrayList<Double> sma, Fibonacci fibonacci) {
    // Implement rules and conditions for identifying a triangle pattern
    // ...
    }
}
