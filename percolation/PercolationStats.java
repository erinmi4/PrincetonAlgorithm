import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {

    int trials;
    int range;
    int[] openSites; // 保存每次试验中打开的站点数量
    double mean, stddev, lo, hi;
    int boad;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        range = n;
        this.trials = trials;
        openSites = new int[trials]; // 大小应为试验次数，而非网格维度
        boad =  range * range;
        for (int i = 0; i < trials; i++) {
            // 创建一个新的 Percolation 实例
            Percolation sites = new Percolation(n);
            int times = 0; // 追踪打开的 site 的个数，直到系统渗透

            // 随机打开站点，直到系统渗透
            while (!sites.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

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
        return StdStats.mean(openSites) / boad ; // 使用 StdStats 的方法来计算平均值
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSites) / boad; // 使用 StdStats 的方法来计算标准差
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
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }
}
