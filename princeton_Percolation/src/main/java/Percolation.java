import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private WeightedQuickUnionUF union;
    private int n;
    private Boolean[] sitesOpen;
    private int numberOfOpenSites;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        validateN(n);
        this.n = n;
        union = new WeightedQuickUnionUF(n * n + 2);
        sitesOpen = new Boolean[n * n + 2];

        for (int i = 1; i < sitesOpen.length; i++) {
            sitesOpen[i] = false;
        }
        sitesOpen[0] = true;
        sitesOpen[n * n + 1] = true;
    }


    //Converts the 2d(row, col) into 1d
    private int xyToOneDimensional(int row, int col) {
        return (row - 1) * this.n + col;
    }

    //Ensures that the number provided is no 0
    private void validateN(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateN(row);
        validateN(col);

        int oneD = xyToOneDimensional(row, col);

        if (!(sitesOpen[oneD])) {
            this.numberOfOpenSites++;
            sitesOpen[oneD] = true;
        }

        if (row == 1) {
            union.union(0, oneD);
        }
        if (row == this.n) {
            union.union((this.n * this.n + 1), oneD);
        }


        if (row < this.n && sitesOpen[oneD + this.n]) {
            union.union(oneD, oneD + this.n);
        }
        if (row > 1 && sitesOpen[oneD - this.n]) {
            union.union(oneD, oneD - this.n);
        }
        if (col < this.n && sitesOpen[oneD + 1]) {
            union.union(oneD, oneD + 1);
        }
        if (col > 1 && sitesOpen[oneD - 1]) {
            union.union(oneD, oneD - 1);
        }


    }

    //is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateN(row);
        validateN(col);

        return sitesOpen[xyToOneDimensional(row, col)];
    }

    //is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateN(row);
        validateN(col);

        int oneD = xyToOneDimensional(row, col);

        return sitesOpen[oneD] && union.find(0) == union.find(oneD);
    }

    //returns the number of open sites
    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    //does the system percolate?
    public boolean percolates() {
        int lastNumber = this.n * this.n + 1;
        return union.find(0) == union.find(lastNumber);
    }


}
