package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private static double[] stat;
    // private static double stat;
    private int times;
    public PercolationStats(int N, int T) {
        // Perform T independent experiemnts on an N-by-N grid
        if (N <= 0) {
            throw new IllegalArgumentException(N + " is not positive.");
        }
        if (T <= 0) {
            throw new IllegalArgumentException(T + " is not positive.");
        }
        stat = new double[T];
        times = T;
        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(N);
            int count = 0;
            while (count < N * N * N) {
                int j = StdRandom.uniform(N);
                int k = StdRandom.uniform(N);
                perc.open(j, k);
                if (perc.percolates()) {
                    stat[i] = ((double) perc.numberOfOpenSites()) / (N * N);
                    break;
                }
                count += 1;
            }
        }
    }
    public double mean() {
        // Sample mean of percolation threshold
        return StdStats.mean(stat);
    }
    public double stddev() {
        // Sample standard deviation
        return StdStats.stddev(stat);
    }
    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }
    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStat = new PercolationStats(N, T);
        System.out.println(percStat.mean());
    }
}                       
