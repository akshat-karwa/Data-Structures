import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * My implementation of a LinearProbingHashMap.
 *
 * @author AKSHAT KARWA
 */
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap.
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    private LinearProbingHashMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     * The backing array has an initial capacity of INITIAL_CAPACITY.
     * We use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     * The backing array has an initial capacity of initialCapacity.
     * initialCapacity is a non-negative integer.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        this.table = new LinearProbingHashMapEntry[initialCapacity];
        this.size = 0;
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, we replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, we use linear probing as our resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, we check to
     * see if the array size violates the max load factor if the data were
     * added. If it does, we trigger a resize before attempting to add the data
     * or we figure out if it's a duplicate.
     *
     * When regrowing, we resize the length of the backing table to
     * 2 * old length + 1. We use the resizeBackingTable method to do so.
     *
     * Returns null if the key was not already in the map. If it was in the map,
     * returns the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value is null!!"
                    + " None of them can be null!!");
        }
        LinearProbingHashMapEntry<K, V> entry = new LinearProbingHashMapEntry<>(key, value);
        if ((((this.size + 1) * 100) / this.table.length) > (MAX_LOAD_FACTOR * 100)) {
            resizeBackingTable((2 * this.table.length) + 1);
        }
        int index = absoluteValue((key.hashCode() % this.table.length));
        int counter = 0;
        int indexOfFirstDeletedItem = 0;
        boolean foundDeletedItem = false;
        while (this.table[index] != null && counter != this.table.length) {
            if (this.table[index].isRemoved()) {
                if (!foundDeletedItem) {
                    indexOfFirstDeletedItem = index;
                    foundDeletedItem = true;
                }
            } else {
                if (this.table[index].getKey().equals(key)) {
                    V val = this.table[index].getValue();
                    this.table[index].setValue(value);
                    return val;
                }
            }
            index = ((index + 1) % this.table.length);
            counter++;
        }
        if (foundDeletedItem) {
            this.table[indexOfFirstDeletedItem] = entry;
        } else {
            this.table[index] = entry;
        }
        this.size++;
        return null;
    }

    /**
     * Private Helper Method to calculate the absolute value
     * of the compressed keyHashcode.
     *
     * @param keyHashcode is the compressed integer hashcode value of the key.
     * @return returns the absolute value of the compressed keyHashcode.
     */
    private int absoluteValue(int keyHashcode) {
        if (keyHashcode < 0) {
            return (-1 * keyHashcode);
        }
        return keyHashcode;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cannot be null!!");
        }
        int index = absoluteValue((key.hashCode() % this.table.length));
        int counter = 0;
        V removed = null;
        while (this.table[index] != null && counter != this.table.length) {
            if (!this.table[index].isRemoved()) {
                if (this.table[index].getKey().equals(key)) {
                    removed = this.table[index].getValue();
                    this.table[index].setRemoved(true);
                    break;
                }
            }
            index = ((index + 1) % this.table.length);
            counter++;
        }
        if (removed == null) {
            throw new NoSuchElementException("The key is not in the map!!");
        }
        this.size--;
        return removed;
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cannot be null!!");
        }
        int index = absoluteValue((key.hashCode() % this.table.length));
        int counter = 0;
        V val = null;
        while (this.table[index] != null && counter != this.table.length) {
            if (!this.table[index].isRemoved()) {
                if (this.table[index].getKey().equals(key)) {
                    val = this.table[index].getValue();
                    break;
                }
            }
            index = ((index + 1) % this.table.length);
            counter++;
        }
        if (val == null) {
            throw new NoSuchElementException("The key is not in the map!!");
        }
        return val;
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null!!");
        }
        int index = absoluteValue((key.hashCode() % this.table.length));
        int counter = 0;
        boolean check = false;
        while (this.table[index] != null && counter != this.table.length) {
            if (!this.table[index].isRemoved()) {
                if (this.table[index].getKey().equals(key)) {
                    check = true;
                    break;
                }
            }
            index = ((index + 1) % this.table.length);
            counter++;
        }
        return check;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Uses java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> hashSet = new HashSet<>(this.size);
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] != null && !this.table[i].isRemoved()) {
                hashSet.add(this.table[i].getKey());
            }
        }
        return hashSet;
    }

    /**
     * Returns a List view of the values contained in this map.
     * Uses java.util.ArrayList.
     * We iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> list = new ArrayList<>(this.size);
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] != null && !this.table[i].isRemoved()) {
                list.add(this.table[i].getValue());
            }
        }
        return list;
    }

    /**
     * Resizes the backing table to length.
     *
     * We iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * We do not copy over removed elements to the resized backing table.
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, we don't explicitly check for
     * duplicates.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < this.size) {
            throw new IllegalArgumentException("Length of HashMap cannot be less"
                    + "than the number of items!!");
        }
        LinearProbingHashMapEntry<K, V>[] newTable = new LinearProbingHashMapEntry[length];
        int numberOfInsertions = 0;
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] != null && !this.table[i].isRemoved()) {
                int index = absoluteValue((this.table[i].getKey().hashCode() % newTable.length));
                while (newTable[index] != null) {
                    index = ((index + 1) % newTable.length);
                }
                LinearProbingHashMapEntry<K, V> entry;
                entry = new LinearProbingHashMapEntry<>(this.table[i].getKey(), this.table[i].getValue());
                newTable[index] = entry;
                numberOfInsertions++;
            }
            if (numberOfInsertions == this.size) {
                break;
            }
        }
        this.table = newTable;
    }

    /**
     * Clears the map.
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size in O(1).
     */
    public void clear() {
        this.table = new LinearProbingHashMapEntry[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the table of the map.
     * 
     * @return the table of the map
     */
    public LinearProbingHashMapEntry<K, V>[] getTable() {
        return table;
    }

    /**
     * Returns the size of the map.
     * 
     * @return the size of the map
     */
    public int size() {
        return size;
    }
}
