public class AcceleratingSawToothGenerator implements Generator {
	private int period;
	private int state;
	private double factor;

	public AcceleratingSawToothGenerator(int p, double f) {
		// input as period
		state = 0;
		this.period = p;
		this.factor = f;
	}
	public double next() {
		state = state + 1;
		if (state % period == 0) {
        	period = (int) ((double) period * factor);
        	state = 0;
        }
        double slope = 2.0 / (double) period;
        double out = (state % period) * slope - 1;
        return out;
	}

}