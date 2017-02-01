// Import the required package
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class TestArrayDeque1B {
    // Begin the test
    int count = 500;
    FailureSequence fs = new FailureSequence();

    @Test
    public void addRemoveTest() {
    	StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
    	int[] exp = new int[1];
    	for (int i = 1; i < 5; i++) {
    		count = StdRandom.uniform(i * 10, i * 100);
    		for (int j = 0; j < count; j++) {
    			ad.addFirst(j);
    			DequeOperation dequeOp1 = new DequeOperation("addFirst", j);
    			fs.addOperation(dequeOp1);
    			int actual = ad.get(0);
    			int expected = j;
    			assertEquals(fs.toString(), expected, actual);
    			ad.removeFirst();
    			DequeOperation dequeOp2 = new DequeOperation("removeFirst");
    			fs.addOperation(dequeOp2);
    		}
    		for (int k = 0; k < count; k++) {
    			ad.addLast(k);
    			DequeOperation dequeOp3 = new DequeOperation("addLast", k);
    			fs.addOperation(dequeOp3);
    			int actual = ad.get(0);
    			int expected = k;
    			assertEquals(fs.toString(), expected, actual);
    			ad.removeLast();
    			DequeOperation dequeOp4 = new DequeOperation("removeLast");
    			fs.addOperation(dequeOp4);
    		}
    	}

    }
    @Test
    public void addRemoveSizeTest() {
    	StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
    	ArrayList<Integer> exp = new ArrayList<Integer>();
    	count = 500;
    	for (int i = 0; i < 100; i++) {
    		for (int j = 0; j < count; j++) {
    			ad.addFirst(i + j);
    			DequeOperation dequeOp1 = new DequeOperation("addFirst", i + j);
    			fs.addOperation(dequeOp1);
    			exp.add(0, i + j);
    		}
    		for (int k = 0; k < count; k++) {
    			int actual = ad.removeFirst();
    			DequeOperation dequeOp2 = new DequeOperation("removeFirst");
    			fs.addOperation(dequeOp2);
    			int expected = exp.get(k);
    			assertEquals(fs.toString(), expected, actual);
    		}

    	}
    	StudentArrayDeque<Integer> ad2 = new StudentArrayDeque<Integer>();
    	FailureSequence fs1 = new FailureSequence();
    	ArrayList<Integer> exp2 = new ArrayList<Integer>();
    	for (int l = 0; l < 100; l++) {
    		for (int m = 0; m < count; m++) {
    			ad2.addLast(l + m);
    			DequeOperation dequeOp3 = new DequeOperation("addLast", l + m);
    			fs1.addOperation(dequeOp3);
    			exp2.add(exp2.size(), l + m);
    		}
    		for (int n = 0; n < count; n++) {
    			int actual = ad2.removeLast();
    			DequeOperation dequeOp4 = new DequeOperation("removeLast");
    			fs1.addOperation(dequeOp4);
    			int expected = exp2.get(exp2.size()-1);
    			assertEquals(fs1.toString(), expected, actual);
    		}

    	}


    }
    // Randomly test addLast and get
    @Test
    public void addLastTest() {
        StudentArrayDeque<Integer> ad  = new StudentArrayDeque<Integer>();
        int k;
        int[] exp = new int[1000];
        for (k = 1; k < 5; k++) {
        	count = StdRandom.uniform(k * 10, k * 100);
            for (int i = 0; i < count; i++) {
            	ad.addLast(i);
                // System.out.println("addLast(" + i +")");
                DequeOperation dequeOp1 = new DequeOperation("addLast", i);
                fs.addOperation(dequeOp1);
                exp[i] = i;
            }
	        for (int j = 0; j < count; j++) {
	            int actual = ad.get(j);
	            int expected = exp[j];
	            assertEquals(fs.toString(), expected, actual);
	        }
	        for (int l = 0; l < count; l++) {
	        	ad.removeLast();
	        	DequeOperation dequeOp2 = new DequeOperation("removeLast");
	        	fs.addOperation(dequeOp2);
	        }
    }
}
    // Randomly test addFirst and get
    @Test
    public void addFirstTest() {
    	count = 1000;
        StudentArrayDeque<Integer> ad  = new StudentArrayDeque<Integer>();
        int[] exp = new int[count];
        for (int i = 0; i < count; i++) {
            ad.addFirst(i);
            DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
            fs.addOperation(dequeOp1);
            exp[i] = count - 1 - i;
        }
        for (int j = 0; j < count; j++) {
            int actual = ad.get(j);
            int expected = exp[j];
            assertEquals(fs.toString(), expected, actual);
        }
    }

    // Ensure resizing does not cause nulls;
    // Add N items, then remove first then last and ensure they're not null.
    @Test
    public void resizingNullTest() {
        StudentArrayDeque<Integer> ad  = new StudentArrayDeque<Integer>();
        count = 1000;
        for (int i = 0; i < count; i++) {
            ad.addFirst(i);
            DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
            fs.addOperation(dequeOp1);
        }
        ad.removeFirst();
        DequeOperation dequeOp2 = new DequeOperation("removeFirst");
        fs.addOperation(dequeOp2);
        Object front = ad.get(0);
        assertNotEquals(fs.toString(), null, front);

        ad.removeLast();
        DequeOperation dequeOp3 = new DequeOperation("RemoveLast");
        fs.addOperation(dequeOp3);
        Object back = ad.get(ad.size() - 1);
        assertNotEquals(fs.toString(), null, back);
    }

    // Random add/size tests
    // Making random calls to addFirst(), addLast(), and size()
    @Test
    public void addSizeTest() {
        StudentArrayDeque<Integer> ad  = new StudentArrayDeque<Integer>();
        count = 1000;
        int i = 0;
        for (i = 0; i < count; i++) {
            ad.addFirst(i);
            DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
            fs.addOperation(dequeOp1);
        }
        for (i = 0; i < count; i++) {
            ad.addLast(i);
            DequeOperation dequeOp2 = new DequeOperation("addLast", i);
            fs.addOperation(dequeOp2);

        }
        int actual = ad.size();
        int expected = count + count;
        assertEquals(fs.toString(), expected, actual);

    }

    // Random addFist/removeFirst/isEmpty test
    // Making random calls to addFirst(), removeFirst(), and isEmpty()
    @Test
    public void addFirstRemoveFirstIsEmptyTest() {
        StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
        int i = 0;
        boolean actual = false;
        count = 1000;
        for (i = 0; i < count; i++) {
            ad.addFirst(i);
            DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
            fs.addOperation(dequeOp1);
        }
        for (i = 0; i < count; i++) {
            ad.removeFirst();
            DequeOperation dequeOp2 = new DequeOperation("removeFirst");
            fs.addOperation(dequeOp2);
        }
        actual = ad.isEmpty();
        DequeOperation dequeOp3 = new DequeOperation("isEmpty");
        fs.addOperation(dequeOp3);
        assertEquals(fs.toString(), true, actual);
    }

    // Rnadom addFirst/removeLast/isEmpty test
    // Making random calls to addFirst(), removeLast(), and isEmpty()
    @Test
    public void addFirstRemoveLastIsEmptyTest() {
        StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
        int i = 0;
        boolean actual = false;
        count = 1000;
        for (i = 0; i < count; i++) {
            ad.addFirst(i);
            DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
            fs.addOperation(dequeOp1);
        }
        for (i = 0; i < count; i++) {
            ad.removeLast();
            DequeOperation dequeOp2 = new DequeOperation("removeLast");
            fs.addOperation(dequeOp2);
        }
        actual = ad.isEmpty();
        DequeOperation dequeOp3 = new DequeOperation("isEmpty");
        fs.addOperation(dequeOp3);
        assertEquals(fs.toString(), true, actual);
    }    

    // Random addLast/removeLast/isEmpty test
    // Making random calls to addLast(), removeLast(), and isEmpty()

    @Test
    public void addLastRemoveLastIsEmptyTest() {
        StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
        int i = 0;
        boolean actual = false;
        count = 1000;
        for (i = 0; i < count; i++) {
            ad.addLast(i);
            DequeOperation dequeOp1 = new DequeOperation("addLast", i);
            fs.addOperation(dequeOp1);
        }
        for (i = 0; i < count; i++) {
            ad.removeLast();
            DequeOperation dequeOp2 = new DequeOperation("removeLast");
            fs.addOperation(dequeOp2);
        }
        actual = ad.isEmpty();
        DequeOperation dequeOp3 = new DequeOperation("isEmpty");
        fs.addOperation(dequeOp3);
        assertEquals(fs.toString(), true, actual);
    }
    // Random addLast/removeFirst/isEmpty test
    // Making random calls to addLast(), removeFirst(), and isEmpty()
    @Test
    public void addLastRemoveFirstIsEmptyTest() {
        StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
        int i = 0;
        boolean actual = false;
        count = 1000;
        for (i = 0; i < count; i++) {
            ad.addLast(i);
            DequeOperation dequeOp1 = new DequeOperation("addLast", i);
            fs.addOperation(dequeOp1);
        }
        for (i = 0; i < count; i++) {
            ad.removeFirst();
            DequeOperation dequeOp2 = new DequeOperation("removeFirst");
            fs.addOperation(dequeOp2);
        }
        actual = ad.isEmpty();
        DequeOperation dequeOp3 = new DequeOperation("isEmpty");
        fs.addOperation(dequeOp3);
        assertEquals(fs.toString(), true, actual);
    }
    // Random add/remove/isEmpty/size test
    // Making random calls to addFirst(), addLast(), removeFirst(), 
    // removeLast(), isEmpty(), and size().
    @Test
    public void addFirstAddLastRemoveFirstRemoveLastIsEmptySizeTest() {
    	StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
    	count = 1000;
    	int i;
    	for (i = 0; i < count; i++) {
    		ad.addFirst(i);
    		DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
            fs.addOperation(dequeOp1);
    	}
    	for (i = 0; i < count / 2; i++) {
    		ad.removeLast();
    		DequeOperation dequeOp2 = new DequeOperation("removeLast");
            fs.addOperation(dequeOp2);
    	}
    	for (i = 0; i < count; i++) {
    		ad.addLast(i);
    		DequeOperation dequeOp3 = new DequeOperation("addLast", i);
            fs.addOperation(dequeOp3);
    	}
    	for (i = 0; i < count / 2; i++) {
    		ad.removeFirst();
    		DequeOperation dequeOp4 = new DequeOperation("removeFirst");
            fs.addOperation(dequeOp4);
    	}
    	int actual = ad.size();
    	int expected = count - count / 2 + count - count / 2;
    	assertEquals(fs.toString(), expected, actual);
    }
    // Removing from empty list
    @Test
    public void removeFromEmptyTest() {
        StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
        DequeOperation dequeOp1 = new DequeOperation("removeFirst");
        fs.addOperation(dequeOp1);
        assertEquals(fs.toString(), null, ad.removeFirst());
        DequeOperation dequeOp2 = new DequeOperation("removeLast");
        fs.addOperation(dequeOp2);
        assertEquals(fs.toString(), null, ad.removeLast());
    }
    // Fill, empty and fill test
    // Inserting a bunch of stuff to the front, removing it from the front, 
    // and then insertFronting it back again.
    @Test
    public void fillEmptyFillTest() {
        StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
        count = 1000;
        int[] exp = new int[count];
        int i = 0;
        int expected = 0;
        int actual = 0;
        // Fill the array
        for (i = 0; i < count; i++) {
            ad.addFirst(i);
            DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
            fs.addOperation(dequeOp1);
            exp[i] = i;
        }
        // Remove all from the first
        for (i = 0; i < count; i++) {
            ad.removeFirst();
            DequeOperation dequeOp2 = new DequeOperation("removeFirst");
            fs.addOperation(dequeOp2);
        }
        // Fill the array again with addLast
        for (i = 0; i < count; i++) {
            ad.addLast(i);
            DequeOperation dequeOp3 = new DequeOperation("addLast", i);
            fs.addOperation(dequeOp3);
        }
        // Compare with the true value
        for (i = 0; i < count; i++) {
            expected = exp[i];
            actual = ad.get(i);
            assertEquals(fs.toString(), expected, actual);
        }
        for (i = 0; i < count; i++) {
        	ad.removeLast();
        	DequeOperation dequeOp4 = new DequeOperation("removeLast");
        	fs.addOperation(dequeOp4);

        }
        // Fill the array again with addFirst
        for (i = 0; i < count; i++) {
        	ad.addFirst(count - 1 - i);
        	DequeOperation dequeOp5 = new DequeOperation("addFirst", count -  1 - i);
        	fs.addOperation(dequeOp5);
        }
        for (i = 0; i < count; i++) {
        	expected = exp[i];
            actual = ad.get(i);
            assertEquals(fs.toString(), expected, actual);
        }

    }
    // Get test
    @Test
    public void getEmptyTest() {
    	StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
    	DequeOperation dequeOp = new DequeOperation("ad.get(10)");
    	fs.addOperation(dequeOp);
    	assertEquals(fs.toString(), null, ad.get(10));
    }
    // Making a random calls to add/remove/get.
    @Test
    public void getTest() {
        StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
        int i = 0;
        count = 500;
        int[] exp = new int[count];
        int expected = 0;
        int actual = 0;
        for (i = 0; i < count; i++) {
        	ad.addLast(i);
        	DequeOperation dequeOp1 = new DequeOperation("addLast", i);
        	fs.addOperation(dequeOp1);
        	exp[i] = i;
        }
        for (i = 0; i < count; i++) {
        	actual = ad.get(i);
        	expected = exp[i];
        	assertEquals(fs.toString(), expected, actual);
        }
        for (i = 0; i < count; i++) {
        	ad.removeLast();
        	DequeOperation dequeOp2 = new DequeOperation("removeLast");
        	fs.addOperation(dequeOp2);
        }
        for (i = 0; i < count; i++) {
        	ad.addFirst(count - 1 - i);
        	DequeOperation dequeOp3 = new DequeOperation("addFirst", count -  1 - i);
        	fs.addOperation(dequeOp3);
        }
        for (i = 0; i < count; i++) {
        	actual = ad.get(i);
        	expected = exp[i];
        	assertEquals(fs.toString(), expected, actual);
        }
    }

    @Test
    public void addFirstSuperSizeTest() {
        StudentArrayDeque<Integer> ad = new StudentArrayDeque<Integer>();
        int i = 0;
        boolean actual = false;
        int tmp;
        count = 1000;
        for (i = 0; i < count; i++) {
            ad.addFirst(i);
            DequeOperation dequeOp1 = new DequeOperation("addFirst", i);
            fs.addOperation(dequeOp1);
        }
        for (i = 0; i < 10; i++) {
            tmp = ad.removeLast();
            DequeOperation dequeOp2 = new DequeOperation("removeLast");
            fs.addOperation(dequeOp2);
            assertEquals(fs.toString(), i, tmp);
        }
    }  

    // The main method for calling the test
    public static void main(String[] args) {
        jh61b.junit.TestRunner.runTests("failed", TestArrayDeque1B.class);
    }

}
