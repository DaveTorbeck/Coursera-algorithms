import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private boolean[][] sites;
    private int sitesOpened;
    private int top;
    private int bottom;
    private int length;


    public Percolation(int n)  {
        if (n <= 0) throw new java.lang.IllegalArgumentException("N is less than or equal to 0");

        sites = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        uf2 = new WeightedQuickUnionUF(n*n + 1);
        sitesOpened = 0;
        top = n * n;
        bottom = n * n + 1;
        length = n;

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validateInput(row, col); // Validate inputs

        if (!isOpen(row, col)) {
            sites[row - 1][col - 1] = true; // Set site to true
            sitesOpened++; // Increment number of opened sites
        }

        // If we are on the first row, union with top site
        // No need to convert to 1D since we are on first row
        if (isInTopRow(row)) {
            uf.union(col - 1, top);
            uf2.union(col - 1, top);

        }

        // If we are on the last row
        // Union site with bottom site
        if (isInBottomRow(row)) {
            uf.union((row - 1) * length + col - 1, bottom);
        }

        // If we are not on the first and and the site above is open
        // Union with the site with the one aboe it
        if (!isInTopRow(row)&& isOpen(row - 1, col)) {
            uf.union((row - 1) * length + col - 1, (row - 2) * length + col - 1);
            uf2.union((row - 1) * length + col - 1, (row - 2) * length + col - 1);
        }

        // If the site we are on is on a row before the last, and the site below it is open
        // Union the site with the site bleow it
        if (!isInBottomRow(row) && isOpen(row + 1, col)) {
            uf.union((row - 1) * length + col - 1, row * length + col - 1);
            uf2.union((row - 1) * length + col - 1, row * length + col - 1);
        }

        // If the col of the site we are on is not the first column and the column to the left is open
        // Union the column site with the site to the left
        if (isFirstCol(col) && isOpen(row, col - 1)) {
            uf.union((row - 1) * length + col - 1, (row - 1) * length + col - 2);
            uf2.union((row - 1) * length + col - 1, (row - 1) * length + col - 2);
        }

        // If the col of the site we are on is not the last one in the row, and the site to the right is open
        // Union the column site with the site to the right
        if (isLastCol(row) && isOpen(row, col + 1)) {
            uf.union((row - 1) * length + col - 1, (row - 1) * length + col);
            uf2.union((row - 1) * length + col - 1, (row - 1) * length + col);
        }
    }

    public boolean isOpen(int row, int col){
        validateInput(row, col);

        // is site (row, col) open?
        return sites[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateInput(row, col);

        // Check if site is connected with top site
        // If true, we have filled sites from top to target site
        return uf2.connected((row - 1) * length + col - 1, top);

    }

    public int numberOfOpenSites() {
        return sitesOpened;
    }

    public boolean percolates() {
        return uf.connected(top, bottom);
    }

    private boolean validateInput(int row, int col) {
        if (row > length || col > length) {
            throw new java.lang.IllegalArgumentException(Integer.toString(row));
        }

        if (row < 0 || col < 0) {
            throw new java.lang.IllegalArgumentException(Integer.toString(row));
        }

        return true;
    }

    private boolean isInTopRow(int row) {
        return row == 1;
    }

    private boolean isInBottomRow(int row) { return row == length; }

    private boolean isFirstCol(int col) {
        return col == 1;
    }

    private boolean isLastCol(int col) { return col == length; }

    public static void main(String[] args) {
        // test client (optional)
    }
}
