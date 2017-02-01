import java.util.ArrayList;

public class StrangeBitwiseGenerator implements Generator {
    private int period;
    private int state;

    public StrangeBitwiseGenerator(int x) {
        // input the period
        state = 0;
        this.period = x;
    }
    public double next() {
        state = state + 1;
        double slope = 2.0 / period;
        return ((state & (state >> 3)) % period) * slope - 1;

    }
}