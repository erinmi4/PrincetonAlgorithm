import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;
import java.util.List;

public class Percolation {
    private int nMatrix;
    private int size;
    private WeightedQuickUnionUF percolationmap;
    /*0是封闭的
    * 1是打开了的*/
    private int[] openSituation;
    private int top;
    private int bottom;

    public Percolation(int nMatrix) {
        this.size = 0;
        this.nMatrix = nMatrix;
        percolationmap = new WeightedQuickUnionUF(nMatrix * nMatrix + 2);
        openSituation = new int[nMatrix * nMatrix + 2];
        top = nMatrix * nMatrix;
        bottom = nMatrix * nMatrix + 1;
        openSituation[top] = 1;
        openSituation[bottom] = 1;

        for (int i = 0; i < nMatrix; i++) {
            percolationmap.union(top, i);
        }
        for (int i = nMatrix * (nMatrix - 1); i < nMatrix * nMatrix; i++) {
            percolationmap.union(bottom, i);
        }
    }

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

    public int xyTo1D(int r, int c) {
        return r * nMatrix + c;
    }

    public boolean isOpen(int row, int col) {
        checkSites(row, col);
        return openSituation[xyTo1D(row, col)] == 1;
    }

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

    public int aRoundOpenHad(int row, int col) {
        List<Integer> neighbors = getValidNeighbors(row, col);
        for (int site : neighbors) {
            if (openSituation[site] == 1) {
                return site;
            }
        }
        return -1;
    }

    public List<Integer> getValidNeighbors(int row, int col) {
        List<Integer> validSites = new ArrayList<>();
        if (row > 0) {
            validSites.add(xyTo1D(row - 1, col));
        }
        if (row < nMatrix - 1) {
            validSites.add(xyTo1D(row + 1, col));
        }
        if (col > 0) {
            validSites.add(xyTo1D(row, col - 1));
        }
        if (col < nMatrix - 1) {
            validSites.add(xyTo1D(row, col + 1));
        }
        return validSites;
    }

    public void checkSites(int rows, int cols) {
        if (rows < 0 || rows >= nMatrix || cols < 0 || cols >= nMatrix) {
            throw new IllegalArgumentException();
        }
    }
}
