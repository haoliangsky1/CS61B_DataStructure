import java.util.ArrayList;

public class SawToothGenerator implements Generator {
    private int period;
    private int state;

    public SawToothGenerator(int x) {
        // input the period
        state = 0;
        this.period = x;
    }
    public double next() {
        state = state + 1;
        double slope = 2.0 / period;
        return (state % period) * slope - 1;

    }
}