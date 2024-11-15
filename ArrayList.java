import java.util.NoSuchElementException;

/**
 * My implementation of an ArrayList.
 *
 * @author AKSHAT KARWA
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     */
    public static final int INITIAL_CAPACITY = 9;
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     */
    public ArrayList() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Helper Method that creates a new array with double the
     * capacity of the backingArray, copies all elements and then
     * sets the backingArray equal to the new array.
     * For efficiency, this method prevents unnecessary data shifting by copying elements
     * , inserting new data and then copying rest of the elements.
     * @param indexOfElementInsertion the index at which to add the new element
     * @param data the data to add at the specified index
     */
    private void doubleBackingArrayCapacity(int indexOfElementInsertion, T data) {
        T[] arr = (T[]) new Object[(this.backingArray).length * 2];
        for (int index = 0; index < indexOfElementInsertion; index++) {
            arr[index] = this.backingArray[index];
        }
        arr[indexOfElementInsertion] = data;
        for (int index = indexOfElementInsertion + 1; index <= this.size; index++) {
            arr[index] = this.backingArray[index - 1];
        }
        this.backingArray = arr;
    }

    /**
     * Adds the element to the specified index.
     *
     * Amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index is negative or greater than the size of the ArrayList!");
        } else if (data == null) {
            throw new IllegalArgumentException("Data to be added is null!");
        } else {
            if ((this.backingArray).length == this.size) {
                doubleBackingArrayCapacity(index, data);
                this.size++;
                return;
            }
            if (index == this.size) {
                this.backingArray[index] = data;
                this.size++;
                return;
            }
            T temp = this.backingArray[index];
            this.backingArray[index] = data;
            addAtIndex(index + 1, temp);
        }
    }

    /**
     * Adds the element to the front of the list (O(n)).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be added is null!");
        } else {
            if ((this.backingArray).length == this.size) {
                doubleBackingArrayCapacity(0, data);
                this.size++;
                return;
            }
            for (int index = 0; index <= this.size; index++) {
                if (index == this.size) {
                    this.backingArray[index] = data;
                    this.size++;
                    return;
                }
                T temp = this.backingArray[index];
                this.backingArray[index] = data;
                data = temp;
            }
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be added is null!");
        } else {
            if ((this.backingArray).length == this.size) {
                doubleBackingArrayCapacity(this.size, data);
            }
            this.backingArray[this.size] = data;
            this.size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index is negative, or equal to or"
                    + "greater than the size of the ArrayList!");
        } else {
            T removed = this.backingArray[index];
            for (int num = index; num < (this.size - 1); num++) {
                this.backingArray[num] = this.backingArray[num + 1];
            }
            this.backingArray[this.size - 1] = null;
            this.size--;
            return removed;
        }
    }

    /**
     * Removes and returns the first element of the list (O(n)).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.size == 0) {
            throw new NoSuchElementException("List is empty!");
        } else {
            T removed = this.backingArray[0];
            for (int num = 0; num < (this.size - 1); num++) {
                this.backingArray[num] = this.backingArray[num + 1];
            }
            this.backingArray[this.size - 1] = null;
            this.size--;
            return removed;
        }
    }

    /**
     * Removes and returns the last element of the list (O(1)).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.size == 0) {
            throw new NoSuchElementException("List is empty!");
        } else {
            T removed = this.backingArray[this.size - 1];
            this.backingArray[this.size - 1] = null;
            this.size--;
            return removed;
        }
    }

    /**
     * Returns the element at the specified index (O(1)).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index is negative, or equal"
                    + "to or greater than the size of the ArrayList!");
        } else {
            return this.backingArray[index];
        }
    }

    /**
     * Returns whether or not the list is empty (O(1)).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (this.size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size (O(1)).
     */
    public void clear() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        return this.backingArray;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return this.size;
    }
}
