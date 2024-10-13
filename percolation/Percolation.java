import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
//按照约定，行索引和列索引是 1 和n之间的整数，其中 (1, 1) 是左上角站点
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

    }

    // returns the number of open sites
    public int numberOfOpenSites() {

    }

    // does the system percolate?
    public boolean percolates() {

    }
    //可以通过行列坐标，计算出该grid在整个图中的位置.
    public int positionCalculate(int row, int col) {

    }

    //判断参数是否符合范围,任何参数超出其范围，则抛出IllegalArgumentException
    public void validPositon(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row or col is out of range");
        }
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}