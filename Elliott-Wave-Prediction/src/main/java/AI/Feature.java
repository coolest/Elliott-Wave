package AI;

import java.util.ArrayList;

public class Feature {
    
    public Fibonacci fibonacci;
    public ArrayList<Double> localHighs;
    public ArrayList<Double> localLows;
    public ArrayList<Double> sma;



    public Feature(Fibonacci fibonacci, ArrayList<Double> localHighs, ArrayList<Double> localLows, ArrayList<Double> sma){
        this.fibonacci = fibonacci;
        this.localHighs = localHighs;
        this.localLows = localLows;
        this.sma = sma;
    }


}
