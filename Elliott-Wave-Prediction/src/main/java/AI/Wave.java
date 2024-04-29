package AI;

public class Wave {
    private WaveType waveType;
    private int startIndex;
    private int endIndex;
    // Add other properties as needed

    // Constructor, getters, and setters
    // ...

    public Wave(WaveType waveType, Integer start, Integer end){
        this.waveType = waveType;
        this.startIndex = start;
        this.endIndex = end;
    }

    public Integer getStartIndex(){
        return startIndex;
    }

    public Integer getEndIndex(){
        return endIndex;
    }

    public WaveType getWaveType(){
        return waveType;
    }
}
