import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int trials;           // 试验次数
    private int totalSites;       // 总站点数
    private int[] openSites;      // 保存每次试验中打开的站点数量
    private double mean, stddev, lo, hi;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.trials = trials;
        this.totalSites = n * n; // 计算总站点数
        openSites = new int[trials]; // 保存每次试验的打开站点数量

        for (int i = 0; i < trials; i++) {
            // 创建一个新的 Percolation 实例
            Percolation sites = new Percolation(n);
            int times = 0; // 追踪打开的站点数量

            // 随机打开站点，直到系统渗透
            while (!sites.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);

                if (!sites.isOpen(row, col)) { // 确保不会重复打开同一个站点
                    sites.open(row, col);
                    times++;
                }
            }
            openSites[i] = times; // 记录当前试验中打开的站点数量
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSites) / totalSites; // 计算占比
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSites) / totalSites; // 计算占比的标准差
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
