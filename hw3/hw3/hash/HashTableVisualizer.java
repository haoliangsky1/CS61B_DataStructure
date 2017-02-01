package hw3.hash;

import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;

public class HashTableVisualizer {

    public static void main(String[] args) {
        /* scale: StdDraw scale
           N:     number of items
           M:     number of buckets */

        // double scale = 1.0;
        // int N = 50;
        // int M = 10;
        double scale = 0.5;
        int N = 2000;
        int M = 100;

        HashTableDrawingUtility.setScale(scale);
        Set<Oomage> oomies = new HashSet<Oomage>();
        for (int i = 0; i < N; i += 1) {
          oomies.add(SimpleOomage.randomSimpleOomage());
          // oomies.add(ComplexOomage.randomComplexOomage());
        }
        visualize(oomies, M, scale);
    }

    public static void visualize(Set<Oomage> set, int M, double scale) {
        HashTableDrawingUtility.drawLabels(M);
        /* TODO: Create a visualization of the given hash table. Use
           du.xCoord and du.yCoord to figure out where to draw
           Oomages.
         */
        int[] sizeSaver = new int[M];
        Arrays.fill(sizeSaver, 0);
        for (Oomage ooA : set) {
          // loop over the hash set to set their x,y coordinate
          // Use the mod function to find the proper bucket
          int bucketNum = (ooA.hashCode() & 0x7FFFFFF) % M ;
          // int bucketNum = ooA.hashCode() % M;
          // Find the proper number
          int bucketPos = sizeSaver[bucketNum];
          double x = HashTableDrawingUtility.xCoord(bucketPos);
          double y = HashTableDrawingUtility.yCoord(bucketNum, M);
          ooA.draw(x, y, scale);
          sizeSaver[bucketNum] += 1;
        }



        /* When done with visualizer, be sure to try 
           scale = 0.5, N = 2000, M = 100. */           
    }
} 
