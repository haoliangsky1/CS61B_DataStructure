package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;

public class Percolation {
    // We will use the WeightedQuickUnionUF as data structure
    private WeightedQuickUnionUF parent;
    private WeightedQuickUnionUF parentHelper;
    private boolean[][] grid;
    private int gridSize;
    private int gridDim;
    private int numberOfOpenSites;
    private boolean percolate;
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException(N + " is non-positive.");
        }
        // Create N-by-N grid with all sites initially blocked
        gridSize = N * N;
        gridDim = N;
        numberOfOpenSites = 0;
        grid = new boolean[gridDim][gridDim]; 
        // initialize everything with false
        for (boolean[] row: grid) {
            Arrays.fill(row, false);
        }
        percolate = false;
        // Create two more spots, to store as virtual top and bottom.
        parent = new WeightedQuickUnionUF(gridSize + 2);
        parentHelper = new WeightedQuickUnionUF(gridSize + 1);

    }
    // We write a helper function here to convert X,Y-coordinate into 
    // index of the WeightedQuickUnionUF
    private int coordinateToIndex(int row, int col) {
        // because this is a N-by-N grid
        int index = col + 1;
        if (row > 0) {
            index = row * gridDim + col + 1;
        }
        return index;
    }
    private void validate(int row, int col) {
        if (row < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (row >= gridDim) {
            throw new IndexOutOfBoundsException();
        }
        if (col < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (col >= gridDim) {
            throw new IndexOutOfBoundsException();
        }
        int x = coordinateToIndex(row, col);
        if (x < 1 || x >  gridSize) {
            throw new IndexOutOfBoundsException();
        }
    }
    public void open(int row, int col) {
        // open the site(row, col) if it is not open already
        try {
            validate(row, col);
            int index = coordinateToIndex(row, col);
            // check if it is open already
            if (!isOpen(row, col)) {
            // if not, open, and take care of connection
                grid[row][col] = true;
                numberOfOpenSites += 1;
                // Then connect any neighbor
                if (row - 1 >= 0) {
                    if (isOpen(row - 1, col)) {
                        parent.union(index, coordinateToIndex(row - 1, col));
                        parentHelper.union(index, coordinateToIndex(row - 1, col));
                    }
                }
                if (row + 1 < gridDim) {
                    if (isOpen(row + 1, col)) {
                        parent.union(index, coordinateToIndex(row + 1, col));
                        parentHelper.union(index, coordinateToIndex(row + 1, col));
                    }
                }
                if (col - 1 >= 0) {
                    if (isOpen(row, col - 1)) {
                        parent.union(index, coordinateToIndex(row, col - 1));
                        parentHelper.union(index, coordinateToIndex(row, col - 1));
                    }
                }
                if (col + 1 < gridDim) {
                    if (isOpen(row, col + 1)) {
                        parent.union(index, coordinateToIndex(row, col + 1));
                        parentHelper.union(index, coordinateToIndex(row, col + 1));
                    }
                }
                if (row == 0) {
                // connect to the top
                    parent.union(index, 0);
                    parentHelper.union(index, 0);
                }
                if (row == gridDim - 1) {
                    parent.union(index, gridSize + 1);
                }
                if (row == gridDim - 1) {
                    if (isFull(row, col)) {
                        percolate = true;
                    }
                }
                if (parent.connected(0, gridSize + 1)) {
                    percolate = true;
                }
                // The condition that any of the last row percolates
            }
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // System.out.println("Index out of bounds.");
        }
    }
    public boolean isOpen(int row, int col) {
        // is the site (row, col) open
        try {
            validate(row, col);
            int index = coordinateToIndex(row, col);
            // can we the connected function
            return grid[row][col];
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // System.out.println("Index out of bounds.");
            return false;
        }
    }
    public boolean isFull(int row, int col) {
        // is the site (row, col) full
        try {
            validate(row, col);
            int index = coordinateToIndex(row, col);
            // see if the current index is connected to the top
            return parentHelper.connected(index, 0);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // System.out.println("Index out of bounds.");
            return false;
        }
    }
    public int numberOfOpenSites() {
        // return the number of open sites
        // System.out.println("number of open sites");
        // System.out.println(numberOfOpenSites);
        return numberOfOpenSites;
    }
    public boolean percolates() {
        // return true if the system percolates
        // ie, any of the last row is full.
        return percolate;
    }
    public static void main(String[] args) {
        // unit testing
        // System.out.println("This is the main.");
    }
}                       
