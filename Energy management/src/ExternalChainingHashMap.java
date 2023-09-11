import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMap<K, V>[] table;
    private int size;

    public ExternalChainingHashMap() {
        table = (ExternalChainingMap<K, V>[]) new ExternalChainingMap[INITIAL_CAPACITY];
    }


    public ExternalChainingHashMap(int capacity) {
        table = (ExternalChainingMap<K, V>[]) new ExternalChainingMap[capacity];
    }

    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null");
        }
        if ((1 + size) / (double) table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }
        int currK = Math.abs(key.hashCode() % table.length);
        ExternalChainingMap<K, V> curr = table[currK];
        V val;
        while (curr != null) {
            if (key.equals(curr.getKey())) {
                val = curr.getValue();
                curr.setValue(value);
                return val;
            }
            curr = curr.getNext();
        }
        ExternalChainingMap<K, V> addV = new ExternalChainingMap<>(key, value);
        if (table[currK] == null) {
            table[currK] = addV;
        } else {
            ExternalChainingMap<K, V> curr2 = table[currK];
            table[currK] = addV;
            table[currK].setNext(curr2);
        }
        size += 1;
        return null;
    }

    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }
        int currK = Math.abs(key.hashCode() % table.length);
        if (table[currK] == null) {
            throw new NoSuchElementException("The key is not in the map");
        }
        V val;
        if (table[currK].getKey().equals(key)) {
            val = table[currK].getValue();
            if (table[currK].getNext() == null) {
                table[currK] = null;
            } else {
                ExternalChainingMap<K, V> curr2 = table[currK].getNext();
                table[currK] = curr2;
            }
            size -= 1;
            return val;
        } else {
            ExternalChainingMap<K, V> curr = table[currK];
            while (curr.getNext() != null) {
                if (curr.getNext().getKey() == key) {
                    val = curr.getNext().getValue();
                    size -= 1;
                    if (curr.getNext().getNext() != null) { //see
                        curr.setNext(curr.getNext().getNext());
                    } else {
                        curr.setNext(null);
                    }
                    return val;
                }
                curr = curr.getNext();
            }
        }
        throw new NoSuchElementException("The key is not in the map");
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }
        int currK = Math.abs(key.hashCode() % table.length);
        ExternalChainingMap<K, V> curr = table[currK];
        while (curr != null) {
            if (curr.getKey().equals(key)) {
                return curr.getValue();
            }
            curr = curr.getNext();
        }
        throw new NoSuchElementException("The key is not in the map");
    }

    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null");
        }
        int currK = Math.abs(key.hashCode() % table.length);
        ExternalChainingMap<K, V> curr = table[currK];
        while (curr != null) {
            if (key.equals(curr.getKey())) {
                return true;
            }
            curr = curr.getNext();
        }
        return false;
    }

    public Set<K> keySet() {
        HashSet<K> sets = new HashSet<>();
        ExternalChainingMap<K, V> curr;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                sets.add(table[i].getKey());
                curr = table[i];
                while (curr.getNext() != null) {
                    sets.add(curr.getNext().getKey());
                    curr = curr.getNext();
                }
            }
        }
        return sets;
    }

    public List<V> values() {
        ArrayList<V> array = new ArrayList<>();
        ExternalChainingMap<K, V> curr;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                array.add(table[i].getValue());
                curr = table[i];
                while (curr.getNext() != null) {
                    array.add(curr.getNext().getValue());
                    curr = curr.getNext();
                }
            }
        }
        return array;
    }

    /*
    my own function-------------------------
    */

    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("The length is less than hashmap item");
        }
        ExternalChainingMap<K, V>[] nArr = (ExternalChainingMap<K, V>[]) new ExternalChainingMap[length];
        ExternalChainingMap<K, V> mCurr;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                if (nArr[Math.abs(table[i].getKey().hashCode() % length)] == null) {
                    nArr[Math.abs(table[i].getKey().hashCode() % length)] = table[i];
                } else {
                    ExternalChainingMap<K, V> curr = nArr[Math.abs(table[i].getKey().hashCode() % length)];
                    nArr[Math.abs(table[i].getKey().hashCode() % length)] = table[i];
                    table[i].setNext(curr);
                }
                mCurr = table[i];
                while (mCurr.getNext() != null) {
                    if (nArr[Math.abs(mCurr.getNext().getKey().hashCode() % length)] == null) {
                        nArr[Math.abs(mCurr.getNext().getKey().hashCode() % length)] = mCurr.getNext();
                    } else {
                        ExternalChainingMap<K, V> curr =
                                nArr[Math.abs(mCurr.getNext().getKey().hashCode() % length)];
                        nArr[Math.abs(mCurr.getNext().getKey().hashCode() % length)] = mCurr.getNext();
                        mCurr.setNext(curr);
                    }
                    mCurr = mCurr.getNext();
                }
            }
        }
        table = nArr;
    }

    public void clear() {
        ExternalChainingMap<K, V>[] nArr =
                (ExternalChainingMap<K, V>[]) new ExternalChainingMap[INITIAL_CAPACITY];
        table = nArr;
        size = 0;
    }

    public ExternalChainingMap<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
