package hw4.puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;

public class Solver {
    private int moves;
    private MinPQ<SearchNode> gameTree;
    private ArrayList<Board> chosenPath;
    // private HashSet<Board> boardSaver;
    // private HashMap<Board, Integer> boardSaver;

    private class SearchNode implements Comparable<SearchNode> {
        // The input of priority queue
        private Board board;
        private int moves;
        private int priority;
        private SearchNode prevNode;
        
        public SearchNode(Board input, int i, SearchNode prev) {
            board = input;
            moves = i;
            prevNode = prev;
            priority = moves + board.manhattan();
        }
        public int priority() {
            return this.priority;
        }

        // Implement the compareTo method
        public int compareTo(SearchNode other) {
            return Integer.compare(this.priority(), other.priority());
        }
        public Board board() {
            return this.board;
        }
        public Board prevBoard() {
            if (prevNode == null) {
                return null;
            } else {
                return this.prevNode.board();
            }
        }
        public SearchNode prevNode() {
            return prevNode;
        }
        public int moves() {
            return this.moves;
        }
        public ArrayList<Board> chosenPath() {
            return chosenPath;
        }
    }
    // Constructor
    public Solver(Board initial) {
        // First, insert the initial search node (the initial board, 
        // 0 moves, and a null previous search node) into a priority queue.
        gameTree = new MinPQ<SearchNode>();
        chosenPath = new ArrayList<Board>();
        // Save the path
        chosenPath.add(0, initial);
        int moveCount = 0;
        SearchNode initialNode = new SearchNode(initial, moveCount, null);
        gameTree.insert(initialNode);
        int counter = 0;
        // Repeat until the search node dequeued corresponds to a goal board.
        while (!gameTree.min().board().isGoal()) {
            // Then, delete from the priority queue the search node with the minimum priority,
            int pri1 = gameTree.min().priority();

            SearchNode current = gameTree.delMin();
            Board currentBoard = current.board();
            Board prevBoard = current.prevBoard();
            for (Board x : BoardUtils.neighbors(currentBoard)) {
                if (!x.equals(prevBoard)) {
                    SearchNode newNode = new SearchNode(x, current.moves() + 1, current);
                    gameTree.insert(newNode);
                } else {
                    counter++;
                }
            }
        }
        // System.out.println(counter);
        // Traverse back to get the path
        SearchNode end = gameTree.min();
        while (end.prevBoard() != null) {
            chosenPath.add(1, end.board());
            end = end.prevNode();
        }
    }
    public int moves() {
        // Return the minimum number of moves to solve the initial board
        return chosenPath.size() - 1;
    }

    public Iterable<Board> solution() {
        // Return the sequence of Boards from the initial board to the solution
        // Use the node to traverse back to its parent
        return this.chosenPath;
    }

    // DO NOT MODIFY MAIN METHOD
     // Uncomment this method once your Solver and Board classes are ready.
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            // System.out.println("printing the solution");
            StdOut.println(board);
        }
    }
}
