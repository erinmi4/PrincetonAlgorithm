import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    int top ;//定义top为连接顶部的表格元素
    int bottom ;//定义bottom为连接底部的表格元素
    int range;//定义range来表示长或宽的大小n
    int[] openState;//用来表示每个触点的开关状态，默认都为0，被关闭
    WeightedQuickUnionUF UF;
//按照约定，行索引和列索引是 1 和n之间的整数，其中 (1, 1) 是左上角站点
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        UF = new WeightedQuickUnionUF(n * n + 2);
        top = n * n;//top位于n*n+1的位置
        bottom = top + 1;//bottom位于n*n+2的位置
        range = n;
        openState = new int[n * n + 2];
        for (int i = 0; i < openState.length; i++) {
            openState[i] = 0;
        }
        //但是top和bottom是默认打开的并且分别与顶部和底部连接
        openState[top] = 1;
        openState[bottom] = 1;
        for (int i = 0; i < n; i++) {
            UF.union(top,i);
            UF.union(bottom,top - 1 - i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        openState[positionCalculate(row,col)] = 1;//将其打开
        //判断周围是否有已经打开的点，如果有，与其连接
        UnionAround(row, col);
    }
    public void UnionAround(int row, int col) {
        //按照上左下右排列
        int[] changeRow = new int[] {-1 , 0 , 0, 1};
        int[] changeCol = new int[] {0, -1, 1, 0};
        //如果是内部点
        if (row > 1 && row < range && col > 1 && col < range) {
            for (int i = 0; i < changeRow.length; i++) {
                int neighborRow = changeRow[i];
                int neighborCol = changeCol[i];
                if (isOpen(neighborRow, neighborCol)) {
                    UF.union(positionCalculate(row,col),positionCalculate(row + neighborRow, col + neighborCol));
                    return;
                }
            }
        }
        //如果是边上的
        else{
            //选择删去一个位置
            for (int i = 0; i < changeRow.length; i++) {
                //如果是顶部，就删去向上，
                if (row == 1 && i == 0) continue;
                //如果是底部就删去向下，
                if (row == range && i == 3) continue;
                //左边就删去向左，
                if (col == 1 && i == 1) continue;
                //右边就删去向右
                if (col == range && i == 2) continue;

                int neighborRow = changeRow[i];
                int neighborCol = changeCol[i];
                if (isOpen(neighborRow, neighborCol)) {
                    UF.union(positionCalculate(row,col),positionCalculate(row + neighborRow, col + neighborCol));
                    return;
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return openState[positionCalculate(row,col)] == 1;
    }

    // is the site (row, col) full?也就是判断该点是否和顶部union
    public boolean isFull(int row, int col) {
        return UF.connected(top,positionCalculate(row,col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return UF.count();
    }

    // does the system percolate?
    public boolean percolates() {
        return UF.connected(bottom,top);
    }
    //可以通过行列坐标，计算出该grid在整数组中的位置.
    public int positionCalculate(int row, int col) {
        validPositon(row,col);
        return range * (row - 1) + col;
    }

    //判断参数是否符合范围,任何参数超出其范围，则抛出IllegalArgumentException
    public void validPositon(int row, int col) {
        if (row < 1 || row > range || col < 1 || col > range) {
            throw new IllegalArgumentException("row or col is out of range");
        }
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}