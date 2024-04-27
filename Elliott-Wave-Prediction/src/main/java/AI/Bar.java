package AI;

import org.json.JSONObject;

public class Bar {
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