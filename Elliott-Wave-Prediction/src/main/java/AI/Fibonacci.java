package AI;

public class Fibonacci {
    public double retracement38;
    public double retracement50;
    public double retracement61;
    public double extension161;
    public double extension261;
    public double extension423;

    public Fibonacci(double r38, double r50, double r61, double e161, double e261, double e423) {
        this.retracement38 = r38;
        this.retracement50 = r50;
        this.retracement61 = r61;
        this.extension161 = e161;
        this.extension261 = e261;
        this.extension423 = e423;
    }

    public double getRetraceLevel(double start, double end, double ratio) {
        double range = Math.abs(start - end);
        double retracement = start - (range * ratio);
        return retracement;
    }

    public double getExtensionLevel(double start, double range, double ratio) {
        double extension = start + (range * ratio);
        return extension;
    }
}
