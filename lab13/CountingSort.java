/**
 * Class with 2 ways of doing Counting sort, one naive way and one "better" way
 * 
 * @author 	Akhil Batra
 * @version	1.1 - April 16, 2016
 * 
**/
public class CountingSort {
    
    /**
     * Counting sort on the given int array. Returns a sorted version of the array.
     *  does not touch original array (non-destructive method)
     * DISCLAIMER: this method does not always work, find a case where it fails
     *
     * @param arr int array that will be sorted
     * @return the sorted array
    **/
    public static int[] naiveCountingSort(int[] arr) {
        // find max
        if (arr.length == 0 || arr.length == 1) {
            return arr;
        }
        int max = Integer.MIN_VALUE;
        for (int i : arr) {
            if (i > max) {
                max = i;
            }
        }

        // gather all the counts for each value
        int[] counts = new int[max + 1];
        for (int i : arr) {
            counts[i] += 1;
        }

        // put the value count times into a new array
        int[] sorted = new int[arr.length];
        int k = 0;
        for (int i = 0; i < counts.length; i += 1) {
            for (int j = 0; j < counts[i]; j += 1, k += 1) {
                sorted[k] = i;
            }
        }

        // return the sorted array
        return sorted;
    }

    /**
     * Counting sort on the given int array, must work even with negative numbers.
     * Note, this code does not need to work for ranges of numbers greater
     * than 2 billion.
     *  does not touch original array (non-destructive method)
     * 
     * @param toSort int array that will be sorted
    **/
    public static int[] betterCountingSort(int[] toSort) {
        //TODO make it work with arrays containing negative numbers.
        // Kepp track of both the max and the min
        if (toSort.length == 0 || toSort.length == 1) {
            return toSort;
        }
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int[] arr = new int[toSort.length];
        for (int i = 0; i < toSort.length; i++) {
            if (toSort[i] > max) {
                max = toSort[i];
            }
            if (toSort[i] < min) {
                min = toSort[i];
            }
            arr[i] = toSort[i];
        }
        // If all positive, no need to preceed
        if (min > 0 && max > 0) {
            return naiveCountingSort(arr);
        }
        // gather all the counts for each value.
        // we need index going from 0 to both left and right
        for (int i  = 0; i < arr.length; i++) {
            arr[i] += Math.abs(min);
        }
        int[] sorted_helper = naiveCountingSort(arr);
        int[] sorted = new int[sorted_helper.length];
        for (int i = 0; i < sorted.length; i++) {
            sorted[i] = sorted_helper[i] - Math.abs(min);
        }
        return sorted;
    }
}
