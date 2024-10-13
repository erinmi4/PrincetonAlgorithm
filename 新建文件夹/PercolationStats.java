import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;           // 试验次数
    private int TotalSites;       // 总站点数
    private int[] OpenSites;      // 保存每次试验中打开的站点数量
    private double mean, stddev, lo, hi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.TotalSites = n * n; // 计算总站点数
        OpenSites = new int[trials]; // 保存每次试验的打开站点数量

        for (int i = 0; i < trials; i++) {
            // 创建一个新的 Percolation 实例
            Percolation Sites = new Percolation(n);
            int Times = 0; // 追踪打开的站点数量

            // 随机打开站点，直到系统渗透
            while (!Sites.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                if (!Sites.isOpen(row, col)) { // 确保不会重复打开同一个站点
                    Sites.open(row, col);
                    Times++;
                }
            }
            OpenSites[i] = Times; // 记录当前试验中打开的站点数量
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(OpenSites) / TotalSites; // 计算占比
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(OpenSites) / TotalSites; // 计算占比的标准差
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (optional)
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java PercolationStats <grid size> <number of trials>");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(n, trials);
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}
