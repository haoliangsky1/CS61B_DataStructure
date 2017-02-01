package hw4.puzzle;

// import java.lang.IndexOutOfBoundsException;
// import java.util.Arrays;

public class Board {
    private final int[][] board;
    private int size;
    private int hamming;
    private int manhattan = -1;

    // Constructor
    public Board(int[][] tiles) {
        // The initial game board
        int[][] temp = tiles;
        size = tiles.length;
        board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = temp[i][j];
            }
        }
        // Set hamming
        int totalH = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) != i * size + j + 1) {
                    totalH += 1;
                }
            }
        }
        hamming = totalH;
        // Set manhattan
        int totalM = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int now = tileAt(i, j);
                if (now != 0) {
                    int row = (now - 1) / size;
                    int col = (now - 1) % size;
                    totalM += Math.abs(i - row) + Math.abs(j - col);
                }
            }
        }
        manhattan = totalM;
    }
    @Override
    public int hashCode() {
        int hash = java.util.Arrays.deepHashCode(this.board());
        return hash;
    }
    public int[][] board() {
        return this.board;
    }
    public int tileAt(int i, int j) {
        // Return the value of tile at row i, column j 
        if (i < 0 || i > size || j < 0 || j > size) {
            throw new IndexOutOfBoundsException("index out of bounds");
        }
        if (board == null) {
            return 0;
        } else {
            return this.board[i][j];
        }
    }

    public int size() {
        // Return the board size N
        return size;
    }
    public int hamming() {
        // Hamming priority function defined above
        return this.hamming;
    }
    public int manhattan() {
        return this.manhattan;
    }
    public boolean isGoal() {
        // Return true if is this board the goal board
        if (this == null) {
            return false;
        }
        return this.manhattan == 0;
    }
    @Override
    public boolean equals(Object object) {
        // Return true if this board's tile values are the same position as y's
        if (object == null) {
            return false;
        }
        if (this.getClass() == object.getClass()) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Board obj = (Board) object;
                    if (this.tileAt(i, j) != obj.tileAt(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
