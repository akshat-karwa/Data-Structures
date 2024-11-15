/**
 * Map entry class used for implementing the LinearProbingHashMap.
 *
 * @author AKSHAT KARWA
 */
public class LinearProbingHashMapEntry<K, V> {

    private K key;
    private V value;
    private boolean removed;

    /**
     * Constructs a new LinearProbingHashMapEntry with the given key and value.
     * The removed flag is default set to false.
     *
     * @param key   the key for this entry
     * @param value the value for this entry
     */
    public LinearProbingHashMapEntry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public K getKey() {
        return key;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public V getValue() {
        return value;
    }

    /**
     * Gets the removed status.
     *
     * @return true if the entry is marked as removed, false otherwise
     */
    public boolean isRemoved() {
        return removed;
    }

    /**
     * Sets the key.
     *
     * @param key the new key
     */
    public void setKey(K key) {
        this.key = key;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(V value) {
        this.value = value;
    }

    /**
     * Sets the removed status.
     *
     * @param removed the new removed status
     */
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", key.toString(), value.toString());
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        // DO NOT USE THIS METHOD!  This is for testing ONLY!
        if (!(o instanceof LinearProbingHashMapEntry)) {
            return false;
        } else {
            LinearProbingHashMapEntry<K, V> that = (LinearProbingHashMapEntry<K, V>) o;
            return that.getKey().equals(key)
                    && that.getValue().equals(value)
                    && that.isRemoved() == removed;
        }
    }
}