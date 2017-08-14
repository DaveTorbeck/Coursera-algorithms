import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] x;
    private int trials;

    public PercolationStats(int n, int trials) {
        this.trials = trials; // perform trials independent experiments on an n-by-n grid

        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Given n <= 0 || trials <= 0");
        }

        x = new double[trials];

        for (int i = 0; i < trials; i++) {
            int opened = 0;
            Percolation pc = new Percolation(n);

            while (!pc.percolates()) {
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);

                if (!pc.isOpen(row, col) && !pc.isFull(row, col)) {
                    pc.open(row, col);
                }
            }

            opened = pc.numberOfOpenSites();
            x[i] = (double) opened / (n*n);
        }

    }

    public double mean() {
        return StdStats.mean(x); // sample mean of percolation threshold
    }

    public double stddev() {
        return StdStats.stddev(x); // sample standard deviation of percolation threshold
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));  // low  endpoint of 95% confidence interval
    }
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials)); // high endpoint of 95% confidence interval
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}