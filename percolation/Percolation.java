import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int top;  // 连接顶部的表格元素
    private int bottom;  // 连接底部的表格元素
    private int range;  // 表示网格的大小 n
    private int[] openState;  // 表示每个站点的开关状态，默认都为0（关闭）
    private WeightedQuickUnionUF uf;

    // 创建 n-by-n 网格，所有站点初始为阻塞状态
    public Percolation(int n) {
        uf = new WeightedQuickUnionUF(n * n + 2);
        top = n * n;  // top 位于 n*n 的位置
        bottom = top + 1;  // bottom 位于 n*n + 1 的位置
        range = n;
        openState = new int[n * n + 2];  // 包含顶部和底部额外的两个点
        for (int i = 0; i < openState.length; i++) {
            openState[i] = 0;  // 初始所有点都是关闭状态
        }
        openState[top] = 1;  // top 默认打开
        openState[bottom] = 1;  // bottom 默认打开

        // 将 top 与第一行所有站点连接，bottom 与最后一行所有站点连接
        for (int i = 0; i < n; i++) {
            uf.union(top, i);
            uf.union(bottom, top - 1 - i);
        }
    }

    // 打开指定的站点（row, col），如果尚未打开
    public void open(int row, int col) {
        int index = positionCalculate(row, col);
        if (openState[index] == 0) {  // 如果该点未打开，则打开
            openState[index] = 1;
            unionAround(row, col);  // 与相邻的打开站点连接
        }
    }

    // 与周围打开的站点相连
    private void unionAround(int row, int col) {
        int[] changeRow = new int[]{-1, 0, 0, 1};  // 上左下右
        int[] changeCol = new int[]{0, -1, 1, 0};

        for (int i = 0; i < changeRow.length; i++) {
            int neighborRow = row + changeRow[i];
            int neighborCol = col + changeCol[i];

            // 检查邻接点是否在范围内并且是打开的
            if (neighborRow >= 1 && neighborRow <= range && neighborCol >= 1 && neighborCol <= range && isOpen(neighborRow, neighborCol)) {
                uf.union(positionCalculate(row, col), positionCalculate(neighborRow, neighborCol));
            }
        }
    }

    // 判断站点 (row, col) 是否打开
    public boolean isOpen(int row, int col) {
        return openState[positionCalculate(row, col)] == 1;
    }

    // 判断站点 (row, col) 是否充满，也就是判断该点是否和顶部相连
    public boolean isFull(int row, int col) {
        return uf.find(top) == uf.find(positionCalculate(row, col));
    }

    // 返回打开的站点数量
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < range * range; i++) {
            if (openState[i] == 1) {
                count++;
            }
        }
        return count;
    }

    // 系统是否渗透
    public boolean percolates() {
        return uf.find(top) == uf.find(bottom);
    }

    // 根据行列坐标计算在数组中的位置
    private int positionCalculate(int row, int col) {
        validPosition(row, col);
        return range * (row - 1) + col - 1;  // 行列转化为一维数组索引
    }

    // 判断行列坐标是否有效
    private void validPosition(int row, int col) {
        if (row < 1 || row > range || col < 1 || col > range) {
            throw new IllegalArgumentException("row or col is out of range");
        }
    }

    // 测试客户端（可选）
    public static void main(String[] args) {
        // 可以在这里添加测试用例
    }
}
