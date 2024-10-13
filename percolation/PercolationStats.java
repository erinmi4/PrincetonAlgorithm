import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class PercolationStats {

    int trial;
    int range;
    int[] opensites;//判断打开了times次数后，系统开始渗透
    Percolation sites;
    double mean, stddev, lo, hi;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        range = n;
        trial = trials;
        //打开了n次，就有n个数值
        opensites = new int[n];
        for (int i = 0; i < n; i++) {
            //创建sites
        sites = new Percolation(n);
        int times = 0; //追踪打开的site的个数，直到渗透
        while (!sites.percolates()) {
            times++;
            sites.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);//随机打开一个site
        }
        opensites[i] = times;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double sum = 0;
        for (int i = 0; i < range; i++) {
            sum += opensites[i];
        }
        mean = (sum / range);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double xDarsh = mean();
        double sum = 0;
        for (int i = 0; i < range; i++) {
            sum += (opensites[i] - xDarsh) * (opensites[i] - xDarsh);
        }
        double ssquare = sum / trial - 1;
        double s = Math.sqrt(ssquare);
        stddev = s;
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        lo = mean() - 1.96 * stddev() / Math.sqrt(trial);
        return lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        hi = mean() + 1.96 * stddev() / Math.sqrt(trial);
        return hi;
    }

   // test client (see below)
   public static void main(String[] args) {
        //PercolationStats test = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
       PercolationStats test = new PercolationStats(200,100);
        System.out.println("mean                    ="+test.mean());
        System.out.println("stddev                  ="+test.stddev());
        System.out.println("95% confidence interval ="+"["+test.confidenceLo()+","+test.confidenceHi()+"]");
   }

}