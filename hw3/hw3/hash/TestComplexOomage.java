package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;


public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    public boolean haveNiceHashCodeSpread(Set<ComplexOomage> oomages) {
        /* TODO: Write a utility function that ensures that the oomages have
         * hashCodes that would distribute them fairly evenly across
         * buckets To do this, mod each's hashCode by M = 10,
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        boolean flag = true;
        int M = 10;
        int N = oomages.size();
        int[] sizeSaver = new int[M];
        Arrays.fill(sizeSaver, 0);
        for (ComplexOomage ooA : oomages) {
            int bucketNum = Math.abs(ooA.hashCode() % M);
            sizeSaver[bucketNum] += 1;
        }
        for (int x : sizeSaver) {
            if (x < (N / 50) || x > (N / 2.5)) {
                flag = false;
            }
        }
        return flag;
    }


    @Test
    public void testRandomItemsHashCodeSpread() {
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    @Test
    public void testWithDeadlyParams() {
        /* TODO: Create a Set that shows the flaw in the hashCode function.
         */
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        for (int i = 0; i < 256; ++i) {
            List<Integer> list = new ArrayList<Integer>();
            list.add(i);
            list.add(0);
            list.add(0);
            list.add(0);
            list.add(0);
            list.add(0);
            ComplexOomage ooA = new ComplexOomage(list);
            oomages.add(ooA);
        }
        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
