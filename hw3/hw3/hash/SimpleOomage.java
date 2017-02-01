package hw3.hash;
import java.awt.Color;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;


public class SimpleOomage implements Oomage {
    protected int red;
    protected int green;
    protected int blue;

    private static final double WIDTH = 0.01;
    private static final boolean USE_PERFECT_HASH = true;

    
    @Override
    public boolean equals(Object o) {
        // TODO: Uncomment this method and make it correct.
        if (o == null) {
            return (this == null);
        }
        if (this.getClass() == o.getClass()) {
            return (this.red() == ((SimpleOomage) o).red() && this.green() == ((SimpleOomage) o).green() 
                && this.blue() == ((SimpleOomage) o).blue());
        }
        return false;
    }
    public int red() {
        return this.red;
    }
    public int green() {
        return this.green;
    }
    public int blue() {
        return this.blue;
    }

    @Override
    public int hashCode() {
        if (!USE_PERFECT_HASH) {
            return red + green + blue;
        } else {
            // TODO: Replace with a "perfect" hashing function.
            final int prime = 257;
            int result = 1;
            result = prime * result + red;
            result = prime * result + green;
            result = prime * result + blue;
            return result;
        }
    }

    public SimpleOomage(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException();
        }
        red = r;
        green = g;
        blue = b;
    }

    @Override
    public void draw(double x, double y, double scalingFactor) {
        StdDraw.setPenColor(new Color(red, green, blue));
        StdDraw.filledSquare(x, y, WIDTH * scalingFactor);
    }

    public static SimpleOomage randomSimpleOomage() {
        int red = StdRandom.uniform(0, 256);
        int green = StdRandom.uniform(0, 256);
        int blue = StdRandom.uniform(0, 256);

        return new SimpleOomage(red, green, blue);
    }

    public static void main(String[] args) {
        System.out.println("Drawing 4 random simple Oomages.");
        randomSimpleOomage().draw(0.25, 0.25);
        randomSimpleOomage().draw(0.75, 0.75);
        randomSimpleOomage().draw(0.25, 0.75);
        randomSimpleOomage().draw(0.75, 0.25);
    }

    public String toString() {
        return "R: " + red + ", G: " + green + ", B: " + blue;
    }
} 
