// Import the required package
import static org.junit.Assert.*;
import org.junit.Test;
// Begin the test
public class TestLinkedListDeque1B {
    // Begin the test
    int count;
    // Random add/size test
    // Making random calls to addFirst(), addLast(), and size()
    @Test
    public void addFirstAddLastSizeTest() {
        StudentLinkedListDeque<Integer> lld = new StudentLinkedListDeque<Integer>();
        int i;
        count = 500;
        for (i = 0; i < count; i++) {
        	lld.addFirst(i);
        }
        for (i = 0; i < count / 2; i++) {
        	lld.removeLast();
        }
        for (i = 0; i < count; i++) {
        	lld.addLast(i);
        }
        for (i = 0; i < count / 2; i++) {
        	lld.removeFirst();
        }
        int actual = lld.size();
        int expected = count - count / 2 + count - count / 2;
        assertEquals("addFirstAddLastSizeTest. \n Random addFirst()/size() when wrong. \n Expect " + expected + " but get " + actual + ".", expected, actual);

    }
    // Random addFirst/removeFist/isEmpty test
    // Making random calls to addFirst(), removeFirst(), and isEmpty()
    @Test
    public void addFirstRemoveFirstIsEmptyTest() {
        StudentLinkedListDeque<Integer> lld = new StudentLinkedListDeque<Integer>();
        int i;
        for (int j = 1; j < 11; j++) {
            count = StdRandom.uniform(1, j * 100);
            for (i = 0; i < count; i++) {
                lld.addFirst(i);
            }
            for (i = 0; i < count; i++) {
                lld.removeFirst();
            }
            boolean actual = lld.isEmpty();
            assertEquals("addFirstRemoveFirstIsEmptyTest. \n Making random calls to addFirst(), removeFirst(), and isEmpty(). \n Expected " + true + " but return " + actual +" . " , true, actual);
        }
    }
    // RandomaddFirst/removeLast/isEmpty test
    // Making random calls to addFirst(), removeLast(), and isEmpty()
    @Test
    public void addFirstRemoveLastIsEmptyTest() {
        StudentLinkedListDeque<Integer> lld = new StudentLinkedListDeque<Integer>();
        int i;
        for (int j = 1; j < 11; j++) {
            count = StdRandom.uniform(1, j * 100);
            for (i = 0; i < count; i++) {
                lld.addFirst(i);
            }
            for (i = 0; i < count; i++) {
                lld.removeLast();
            }
            boolean actual = lld.isEmpty();
            assertEquals("addFirstRemoveLastIsEmptyTest. \n Making random calls to addFirst(), removeFirst(), and isEmpty(). \n Expected " + true + " but returns " + actual +" . ", true, actual);
        }
    }
    // Random addLast/removeFirst/isEmpty test
    // Making random calls to addLast(), removeFirst(), and isEmpty()
    @Test
    public void addLastRemoveFirstIsEmptyTest() {
        StudentLinkedListDeque<Integer> lld = new StudentLinkedListDeque<Integer>();
        int i;
        for (int j = 1; j < 11; j++) {
            count = StdRandom.uniform(1, j * 100);
            for (i = 0; i < count; i++) {
                lld.addLast(i);
            }
            for (i = 0; i < count; i++) {
                lld.removeFirst();
            }
            boolean actual = lld.isEmpty();
            assertEquals("addLastRemoveFirstIsEmptyTest. \n Making random calls to addLast(), removeFirst(), and isEmpty(). \n Expected " + true + " but returns " + actual +" . ", true, actual);
        }
    }
    // Random addLast/removeLast/isEmpty test
    // Making random calls to addLast(), removeLast(), and isEmpty()
    @Test
    public void addLastRemoveLastIsEmptyTest() {
        StudentLinkedListDeque<Integer> lld = new StudentLinkedListDeque<Integer>();
        int i;
        for (int j = 1; j < 11; j++) {
            count = StdRandom.uniform(1, j * 100);
            for (i = 0; i < count; i++) {
                lld.addLast(i);
            }
            for (i = 0; i < count; i++) {
                lld.removeLast();
            }
            boolean actual = lld.isEmpty();
            assertEquals("addLastRemoveLastIsEmptyTest. \n Making random calls to addLast(), removeLast(), and isEmpty(). \n Expected " + true + " but returns " + actual +" . ", true, actual);
        }
    }
    // Removing from empty linked list
    // RemoveFirst(), RemoveLast()
    @Test
    public void removeFromEmptyTest() {
        StudentLinkedListDeque<Integer> lld = new StudentLinkedListDeque<Integer>();
        assertEquals("removeFromEmptyTest. \n Calling removeFirst() from an empty list. \n Expected " + null + " but returns " + lld.removeFirst() + " . ", null, lld.removeFirst());
        assertEquals("removeFromEmptyTest. \n Calling removeLast() form an empty list. \n Expected " + null + " but returns " + lld.removeLast() + " . ", null, lld.removeLast());
    }
    // Ieterative get
    // Making random calls to add/remove/get.
    @Test
    public void iterativeGetTest() {
        StudentLinkedListDeque<Integer> lld = new StudentLinkedListDeque<Integer>();
        count = 500;
        int i;
        int[] exp = new int[count];
        for (i = 0; i < count; i++) {
        	exp[i] = i;
            lld.addFirst(i);
        }
        for (i = 0; i < count; i++) {
        	lld.removeLast();
        }
        for (i = 0; i < count; i++) {
        	lld.addLast(i);
        }
        for (i = 0; i < count; i++) {
        	int expected = exp[i];
        	int actual = lld.get(i);
        	assertEquals("iterativeGetTest. \n Calling get() from a list. \n Expected " + expected + " but returns " + actual + " . ", expected, actual);
        }
    }
    // Recusive get
    // Making random calls to add/remove/get.
    @Test
    public void recusiveGetTest() {
        StudentLinkedListDeque<Integer> lld = new StudentLinkedListDeque<Integer>();
        count = 500;
        int i;
        int[] exp = new int[count];
        for (i = 0; i < count; i++) {
        	exp[i] = i;
            lld.addFirst(i);
        }
        for (i = 0; i < count; i++) {
        	lld.removeLast();
        }
        for (i = 0; i < count; i++) {
        	lld.addLast(i);
        }
        for (i = 0; i < count; i++) {
        	int expected = exp[i];
        	int actual = lld.getRecursive(i);
        	assertEquals("recursiveGetTest. \n Calling getRecursive() from a list. \nExpected " + expected + " but returns " + actual +" . ", expected, actual);
        }

    }


    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests("failed", TestLinkedListDeque1B.class);
    }


}
