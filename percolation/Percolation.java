import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类用于模拟渗透（Percolation）问题.
 *
 * @param nmatrix        地图的大小，决定了模拟的范围。
 * @param size           已经被打开的个数，用于跟踪渗透状态。
 * @param percolationmap 地图本身，使用一个加权树来构成，用于表示渗透系统。
 * @param openSituation  用一个数组来表述所有的 site 的打开情况，方便查询和更新状态。
 * @param top            创建的顶层虚拟节点，可以与顶部的 nmatrix 个 site 直接连接，用于简化渗透判断。
 * @param bottom         创建的底部虚拟节点，可以与底部的 nmatrix 个 site 直接连接，同样用于简化渗透判断。
 * @author [Mika]
 * @version 1.0
 */
public class Percolation {

  private int nmatrix;
  private int size;
  private WeightedQuickUnionUF percolationmap;
  /*0是封闭的
   * 1是打开了的*/
  private int[] openSituation;
  private int top;
  private int bottom;

  /**
   * 构造函数，用于初始化渗透模型.
   *
   * @param nmatrix 网格的大小，构造成一个nmatrix x nmatrix的网格
   */
  public Percolation(int n) {
    this.size = 0;
    this.nmatrix = n;
    percolationmap = new WeightedQuickUnionUF(nmatrix * nmatrix + 2);
    openSituation = new int[nmatrix * nmatrix + 2];
    top = nmatrix * nmatrix;
    bottom = nmatrix * nmatrix + 1;
    openSituation[top] = 1;
    openSituation[bottom] = 1;

    for (int i = 0; i < nmatrix; i++) {
      percolationmap.union(top, i);
    }
    for (int i = nmatrix * (nmatrix - 1); i < nmatrix * nmatrix; i++) {
      percolationmap.union(bottom, i);
    }
  }

  /**
   * 用来将一个坐标的site打开.
   */
  public void open(int row, int col) {
    checkSites(row, col);
    if (isOpen(row, col)) {
      return;
    }
    size++;
    int location = xyTo1D(row, col);
    openSituation[location] = 1;

    List<Integer> neighbors = getValidNeighbors(row, col);
    for (int neighbor : neighbors) {
      if (openSituation[neighbor] == 1) {
        percolationmap.union(location, neighbor);
      }
    }
  }

  private int xyTo1D(int r, int c) {
    return r * nmatrix + c;
  }

  public boolean isOpen(int row, int col) {
    checkSites(row, col);
    return openSituation[xyTo1D(row, col)] == 1;
  }

  /**
   * 判断该点是否能够从顶部联通过来.
   */
  public boolean isFull(int row, int col) {
    checkSites(row, col);
    // 如果位置已打开并且与顶部连通，则该位置是 full
    int location = xyTo1D(row, col);
    if (isOpen(row, col) && percolationmap.connected(top, location)) {

      return true;
    }
    return false;
  }


  public int numberOfOpenSites() {
    return size;
  }

  public boolean percolates() {
    return percolationmap.connected(top, bottom);
  }

  /**
  *判断周围的点是否打开.
  */
  private int aroundppenhad(int row, int col) {
    List<Integer> neighbors = getValidNeighbors(row, col);
    for (int site : neighbors) {
      if (openSituation[site] == 1) {
        return site;
      }
    }
    return -1;
  }

  /**
   * 获取周围合法的点的坐标.
   */
  public List<Integer> getValidNeighbors(int row, int col) {
    List<Integer> validSites = new ArrayList<>();
    if (row > 0) {
      validSites.add(xyTo1D(row - 1, col));
    }
    if (row < nmatrix - 1) {
      validSites.add(xyTo1D(row + 1, col));
    }
    if (col > 0) {
      validSites.add(xyTo1D(row, col - 1));
    }
    if (col < nmatrix - 1) {
      validSites.add(xyTo1D(row, col + 1));
    }
    return validSites;
  }

  /**
   * 判断坐标是否是合法坐标.
   */
  private void checkSites(int rows, int cols) {
    if (rows < 0 || rows >= nmatrix || cols < 0 || cols >= nmatrix) {
      throw new IllegalArgumentException();
    }
  }
}
