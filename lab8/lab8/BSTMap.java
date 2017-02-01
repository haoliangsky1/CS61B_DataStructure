package lab8;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    // This is the class that we create
    // This class should implement the methods given by the Map61B interface
    private BST root;
    private int size;

    public BST root() {
        return root;
    }

    private class BST {
        protected K key;
        protected V value;
        protected BST left;
        protected BST right;
        private int size;
        // Create a BST node 
        public BST(K k, V v) {
            this.key = k;
            this.value = v;
            this.size += 1;
        }
        public K key() {
            return key;
        }
        public V value() {
            return value;
        }
        public BST left() {
            return left;
        }
        public BST right() {
            return right;
        }
    }

    // The method that removes all of the mappings from this map.
    public void clear() {
        size = 0;
        root = null;
    }

    // The method that returns true if the map contains a mapping for the specified key
    public boolean containsKey(K key) {
        if (key == null) {
            return false;
        }
        return get(key) != null;
    }

    // The method that returns the value to which the specified key is mapped, or null if this 
    // map contains no mapping for the key.
    public V get(K key) {
        return get(root, key);
    }
    private V get(BST T, K key) {
        if (T == null) {
            return null;
        }
        if (key.compareTo(T.key) < 0) {
            return get(T.left, key);
        } else if (key.compareTo(T.key) > 0) {
            return get(T.right, key);
        } else {
            return T.value;
        }
    }

    // The method that returns the number of key-value mappings in the map
    public int size() {
        return size(root);
    }
    private int size(BST T) {
        if (T == null) {
            return 0;
        } else {
            return T.size;
        }
    }

    // The method that associates the specified value with the specified key in this map
    public void put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("Invalid operation.");
        }
        if (value == null) {
            return;
        }
        root = put(root, key, value);
    }
    private BST put(BST T, K key, V value) {
        if (T == null) {
            return new BST(key, value);
        }
        if (key.compareTo(T.key) < 0) {
            T.left = put(T.left, key, value);
        } else if (key.compareTo(T.key) > 0) {
            T.right = put(T.right, key, value);
        } else {
            T.value = value;
        }
        T.size = 1 + size(T.left) + size(T.right);
        return T;
    }

    // The additional method that prints out the BSTMap in order of increasing Key
    public void printInOrder(BST T) {
        if (T == null) {
            System.out.println("");
        }
        if (T.left == null) {
            if (T.right == null) {
                System.out.println(T.key + ", " + T.value);
            } else {
                printInOrder(T.right);
            }
        } else {
            printInOrder(T.left);
        }
    }

    // An iterator that iterates over the keys 
    private class BSTMapIter implements Iterator<K> {
        // Create a new BSTMapIter by setting current to the node
        // that stroes the key-value pairs
        private BST current;
        public BSTMapIter() {
            current = root;
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public K next() {
            K ret = current.key;
            current = current.right;
            return ret;
        }
    }

    // The following method should be excluded by throwing exception
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
}
