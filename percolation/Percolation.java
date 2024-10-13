import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    int top;  // 定义top为连接顶部的表格元素
    int bottom;  // 定义bottom为连接底部的表格元素
    int range;  // 定义range来表示长或宽的大小n
    int[] openState;  // 用来表示每个触点的开关状态，默认都为0，被关闭
    WeightedQuickUnionUF UF;

    // 按照约定，行索引和列索引是 1 和n之间的整数，其中 (1, 1) 是左上角站点
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        UF = new WeightedQuickUnionUF(n * n + 2);
        top = n * n;  // top位于n*n+1的位置
        bottom = top + 1;  // bottom位于n*n+2的位置
        range = n;
        openState = new int[n * n + 2];  // 包含顶部和底部额外的两个点
        for (int i = 0; i < openState.length; i++) {
            openState[i] = 0;  // 初始所有点都是关闭状态
        }
        openState[top] = 1;  // top默认打开
        openState[bottom] = 1;  // bottom默认打开

        // 将 top 与第一行所有站点连接，bottom与最后一行所有站点连接
        for (int i = 0; i < n; i++) {
            UF.union(top, i);
            UF.union(bottom, top - 1 - i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int index = positionCalculate(row, col);
        if (openState[index] == 0) {  // 如果该点未打开，则打开
            openState[index] = 1;
            UnionAround(row, col);  // 与相邻的打开站点连接
        }
    }

    // 与周围打开的站点相连
    public void UnionAround(int row, int col) {
        int[] changeRow = new int[]{-1, 0, 0, 1};  // 上左下右
        int[] changeCol = new int[]{0, -1, 1, 0};

        for (int i = 0; i < changeRow.length; i++) {
            int neighborRow = row + changeRow[i];
            int neighborCol = col + changeCol[i];

            // 检查邻接点是否在范围内并且是打开的
            if (neighborRow >= 1 && neighborRow <= range && neighborCol >= 1 && neighborCol <= range && isOpen(neighborRow, neighborCol)) {
                UF.union(positionCalculate(row, col), positionCalculate(neighborRow, neighborCol));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openState[positionCalculate(row, col)] == 1;
    }

    // is the site (row, col) full?也就是判断该点是否和顶部相连
    public boolean isFull(int row, int col) {
        return UF.connected(top, positionCalculate(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 1; i <= range * range; i++) {
            if (openState[i] == 1) {
                count++;
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.connected(top, bottom);
    }

    // 根据行列坐标计算在数组中的位置
    public int positionCalculate(int row, int col) {
        validPositon(row, col);
        return range * (row - 1) + col - 1;  // 行列转化为一维数组索引
    }

    // 判断行列坐标是否有效
    public void validPositon(int row, int col) {
        if (row < 1 || row > range || col < 1 || col > range) {
            throw new IllegalArgumentException("row or col is out of range");
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        // 测试用例可以在这里添加
    }
}
