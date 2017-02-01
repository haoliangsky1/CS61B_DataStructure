package lab9;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.ArrayList;

public class MyHashMap<K, V> implements Map61B<K, V> {
    // instance variables
    private static final int INIT_CAPACITY = 4;
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    // Number of key-value pairs
    private int N;
    // hash table size
    private int M;
    private double loadFactor;
    // array of arraylist symbol
    private SearchST<K, V>[] st;
    // the key set that contains all the keys in the map
    private HashSet<K> keySet;

    // Constructor
    // An empty table with size of initial capacity
    public MyHashMap() {
        this(INIT_CAPACITY);
    }
    // And empty symbol table with initialSize chains
    public MyHashMap(int initialSize) {
    	this(initialSize, DEFAULT_LOAD_FACTOR);
    }

    public MyHashMap(int initialSize, double loadFactor) {
    	if (loadFactor <= 0.0) {
            throw new IllegalArgumentException("loadFactor is not greater than 0");
        }

 		keySet = new HashSet<K> ();
        this.M = initialSize;
        st = (SearchST<K, V>[]) new SearchST[initialSize];
        for (int i = 0; i < initialSize; i++) {
            st[i] = new SearchST<K, V> ();
        }

        this.loadFactor = loadFactor;
    }

    private void resize(int chains) {
        MyHashMap<K, V> temp = new MyHashMap<K, V> (chains, this.loadFactor);
        for (K key: keys()) {
        	temp.put(key, this.get(key));
        }
        this.M = temp.M;
        this.N = temp.N;
        this.st = temp.st;
    }

    @Override
    public HashSet<K> keySet() {
        // return a set view of the keys contained in this map
        return keySet;
    }
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }
    public int size() {
        return N;
    }
    public boolean isEmpty() {
        return size() == 0;
    }
    public boolean containsKey(K key) {
        if (key == null) {
            throw new NullPointerException("argument to containsKey() is null");
        }
        return get(key) != null;
    }
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException("argument to get() is null");
        }
        int i = hash(key);
        System.out.println(i);
        System.out.println(M);
        return st[i].get(key);
    }
    // @Override
    public void put(K key, V val) {
        if (key == null) {
            throw new NullPointerException("first argument to put() is null");
        }
        if (val == null) {
            remove(key);
            return;
        }
        if ((double) N / M > this.loadFactor) {
            resize(2 * M);
        }
        int i = hash(key);

        if (!st[i].contains(key)) {
            N++;
        	keySet.add(key);
        }
        st[i].put(key, val);
    }
    // @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("this operation is not supported");
        // if (key == null) {
        //     throw new NullPointerException("argument to remove() is null");
        // }
        // int i = hash(key);
        // if (st[i].containsKey(key)) {
        //     N--;
        // }
        // st[i].delete(key);
        // if (M > INIT_CAPACITY && N <= 2 * M) {
        //     resize (M / 2);
        // }
    }
    // @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("this operation is not supported");
        // Remove the entry for th espcified key only if it is currently mapped to 
        // the specified value.
        // if (key == null) {
        //     throw new NullPointerException("argument to remove() is null")
        // }
        // int i = hash(key);
        // if (st[i].containsKey(key)) {
        //     if (get(key) == value) {
        //         // If so, remove the item
        //         N--;
        //     }
        // }
    }
    // @Override
    public void clear() {
        this.N = 0;
        this.st = (SearchST<K, V>[]) new SearchST[M];
        for (int i = 0; i < M; i++) {
            this.st[i] = new SearchST<K, V> ();
        }
        this.keySet = new HashSet<K>();
    }




    public Iterable<K> keys() {
    	return keySet;
    }


    public Iterator<K> iterator() {
    	return keySet.iterator();
    	// return new MyHashMapIter();
    }


    // private class MyHashMapIter implements Iterator<K> {
    // 	private Set<K> current;
    // 	private int i = 0;
    // 	public MyHashMapIter() {
    // 		current = HashSet;
    // 	}
    // 	@Override
    // 	public boolean hasNext() {
    // 		return i < N;
    // 	}
    // 	@Override
    // 	public K next() {
    // 		if (!hasNext()) {
    // 			throw new NoSuchElementException();
    // 		}
    // 		return current.iterator();
    // 	}
    // }

    private class SearchST<K, V> implements Iterable<K> {
        private static final int INIT_SIZE = 8;
        private K[] keys;
        private V[] vals;
        private int N = 0;

        public SearchST() {
            keys = (K[] ) new Object[INIT_SIZE];
            vals = (V[] ) new Object[INIT_SIZE];
        }
        public K[] keys() {
        	return keys;
        }
        public boolean isEmpty() {
            return N == 0;
        }
        private void resize(int capacity) {
            K[] tempK = (K[]) new Object[capacity];
            V[] tempV = (V[]) new Object[capacity];
            for (int i = 0; i < N; i++) {
                tempK[i] = keys[i];
            }
            for (int i = 0; i < N; i++) {
                tempV[i] = vals[i];
            }
            keys = tempK;
            vals = tempV;
        }
        public void put(K key, V val) {
            remove(key);
            if (N >= vals.length) {
                resize(2 * N);
            }
            vals[N] = val;
            keys[N] = key;
            N++;
        }
        // @Override
        public V get(K key) {
            for (int i = 0; i < N; i++) {
                if (keys[i].equals(key)) {
                    return vals[i];
                }
            }
            return null;
        }
        // @Override
        public boolean contains(K key) {
            return get(key) != null;
        }
        // @Override
        public int size() {
            return this.N;
        }
        public void remove(K key) {
            for (int i = 0; i < N; i++) {
                if (key.equals(keys[i])) {
                    keys[i] = keys[N - 1];
                    vals[i] = vals[N - 1];
                    keys[N - 1] = null;
                    vals[N - 1] = null;
                    N--;
                    return;
                }
            }
        }
        public Iterator<K> iterator() {
            return new ArrayIterator();
        }

        private class ArrayIterator implements Iterator<K> {
            private int i = 0;
            public boolean hasNext() {
                return i < N;
            }
            public void remove() {
                throw new UnsupportedOperationException();
            }
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return keys[i++];
            }
        }
    }

}


