import java.util.Observable;
import java.util.ArrayDeque;
/**
 *  @author Josh Hug
 */

public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private ArrayDeque<Integer> fringe;
    private int s;
    private int t;
    private boolean targetFound;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        targetFound = false;
        fringe = new ArrayDeque<Integer>();
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;

    }

    /** Conducts a breadth first search of the maze starting at vertex x. */
    private void bfs(int v) {
        /* Your code here. */
        // announce();
        // if (v == t) {
        //     targetFound = true;
        // }
        // if (targetFound) {
        //     return;
        // }
        fringe.add(s);
        marked[v] = true;
        while(!fringe.isEmpty()) {
            int k = fringe.remove();
            for (int w : maze.adj(k)) {
                if (!marked[w]) {
                    fringe.add(w);
                    marked[w] = true;
                    edgeTo[w] = k;
                    distTo[w] = distTo[k] + 1;
                    if (s == t) {
                        return;
                    }
                }
            }
        }


    }


    @Override
    public void solve() {
        bfs(s);
    }
}

