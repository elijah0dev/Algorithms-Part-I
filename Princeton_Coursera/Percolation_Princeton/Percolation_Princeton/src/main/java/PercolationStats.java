import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] threshold;
    private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validateN(n);
        validateN(trials);

        this.trials = trials;

        threshold = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!(percolation.percolates())) {
                int randomRow = StdRandom.uniformInt(1, n + 1);
                int randomCol = StdRandom.uniformInt(1, n + 1);

                percolation.open(randomRow, randomCol);
            }
            threshold[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    private void validateN(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException();
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(threshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(this.trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(this.trials);

    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length == 2) {
            int n = Integer.valueOf(args[0]);
            int t = Integer.valueOf(args[1]);


            PercolationStats stats = new PercolationStats(n, t);

            System.out.println("mean = " + stats.mean());
            System.out.println("stddev = " + stats.stddev());
            System.out.println("95% confidence interval = [" + stats.confidenceHi() + ", " + stats.confidenceLo() + "]");

        }
    }

}