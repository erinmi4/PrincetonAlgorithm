import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * 配置需要的地图.
 */
public class PercolationStats {
  private final double mean;
  private final double stddev;
  private final double trials;

  /**
   * new.
   */
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    this.trials = trials;
    double[] ratio = new double[trials];
    for (int i = 0; i < trials; i += 1) {
      Percolation p = new Percolation(n);
      while (!p.percolates()) {
        int randRow = StdRandom.uniform(n);
        int randCol = StdRandom.uniform(n);
        p.open(randRow, randCol);
      }
      ratio[i] = ((double) p.numberOfOpenSites()) / (n * n);
    }

    this.mean = StdStats.mean(ratio);
    this.stddev = StdStats.stddev(ratio);
  }

  public double mean() {
    return mean;
  }

  public double stddev() {
    return stddev;
  }

  public double confidenceLo() {
    return mean - 1.96 * stddev / Math.sqrt(trials);
  }

  public double confidenceHi() {
    return mean + 1.96 * stddev / Math.sqrt(trials);
  }

  /**
  * main.
  * */
  public static void main(String[] args) {
    int trials = Integer.parseInt(args[0]);
    int gridSize = Integer.parseInt(args[1]);
    PercolationStats ps = new PercolationStats(gridSize, trials);
    //System.out.printf("Grid Size: %d x %d | Number of Trials: %d%n", gridSize, gridSize, trials);
    System.out.printf("mean                    = %f%n", ps.mean());
    System.out.printf("stddev                  = %f.%n",
        ps.stddev());
    System.out.printf("95%% confidence interval = [%f, %f].%n", ps.confidenceLo(),
        ps.confidenceHi());
  }
}
