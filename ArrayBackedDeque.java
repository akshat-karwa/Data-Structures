/**
 * My implementation of an ArrayBackedDeque.
 *
 * @author AKSHAT KARWA
 */
public class ArrayBackedDeque<T> {

    /**
     * The initial capacity of the ArrayDeque.
     */
    public static final int INITIAL_CAPACITY = 11;
    private T[] backingArray;
    private int front;
    private int size;

    /**
     * Constructs a new ArrayBackedDeque.
     */
    public ArrayBackedDeque() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.front = 0;
        this.size = 0;
    }

    /**
     * Adds the element to the front of the deque.
     *
     * If sufficient space is not available in the backing array, we resize it to
     * double the current capacity. When resizing, we copy elements to the
     * beginning of the new array and reset front to 0. After the resize and add
     * operation, the new data is at index 0 of the array.
     * 
     * Amortized O(1).
     * 
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to add cannot be null!!");
        }
        if (this.size == this.backingArray.length) {
            resize(data);
        } else {
            int indexToAdd = mod(this.front - 1, this.backingArray.length);
            this.backingArray[indexToAdd] = data;
            this.front = indexToAdd;
        }
        this.size++;
    }

    /**
     * Resizes the deque to double its capacity and
     * adds efficiently to the front of the deque.
     *
     * @param data the data to add to the front of the deque
     */
    private void resize(T data) {
        T[] newArray = (T[]) new Object[this.backingArray.length * 2];
        newArray[0] = data;
        for (int i = 0; i < this.size; i++) {
            newArray[i + 1] = this.backingArray[mod(this.front + i, this.backingArray.length)];
        }
        this.front = 0;
        this.backingArray = newArray;
    }

    /**
     * Adds the element to the back of the deque.
     *
     * If sufficient space is not available in the backing array, we resize it to
     * double the current capacity. When resizing, we copy elements to the
     * beginning of the new array and reset front to 0.
     *
     * Amortized O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to add cannot be null!!");
        }
        if (this.size == this.backingArray.length) {
            resize();
            this.backingArray[this.front + this.size] = data;
        } else {
            int indexToAdd = mod(this.front + this.size, this.backingArray.length);
            this.backingArray[indexToAdd] = data;
        }
        this.size++;
    }

    /**
     * Resizes the deque to double its capacity.
     */
    private void resize() {
        T[] newArray = (T[]) new Object[this.backingArray.length * 2];
        for (int i = 0; i < this.size; i++) {
            newArray[i] = this.backingArray[mod(this.front + i, this.backingArray.length)];
        }
        this.front = 0;
        this.backingArray = newArray;
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * If the deque becomes empty as a result of this call, we do not reset
     * front to 0. Rather, we modify the front index as if the deque did not become
     * empty as a result of this call.
     *
     * We replace any spots that we remove from with null.
     * O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("The Deque is Empty! No element to remove!");
        }
        T removed = this.backingArray[this.front];
        this.backingArray[this.front] = null;
        this.front = mod(this.front + 1, this.backingArray.length);
        this.size--;
        return removed;
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * If the deque becomes empty as a result of this call, we do not reset
     * front to 0. 
     *
     * We replace any spots that we remove from with null.
     * O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("The Deque is Empty! No element to remove!");
        }
        int indexToRemove = mod(this.front + this.size - 1, this.backingArray.length);
        T removed = this.backingArray[indexToRemove];
        this.backingArray[indexToRemove] = null;
        this.size--;
        return removed;
    }

    /**
     * Returns the first data of the deque without removing it.
     * O(1).
     *
     * @return the first data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("The Deque is Empty! No element to get!");
        }
        return this.backingArray[this.front];
    }

    /**
     * Returns the last data of the deque without removing it.
     * O(1).
     *
     * @return the last data
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("The Deque is Empty! No element to get!");
        }
        return this.backingArray[mod(this.front + this.size - 1, this.backingArray.length)];
    }

    /**
     * Returns the backing array of the deque.
     *
     * @return the backing array of the deque
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the deque.
     *
     * @return the size of the deque
     */
    public int size() {
        return size;
    }

    /**
     * Returns the smallest non-negative remainder when dividing index by
     * modulo. So, for example, if modulo is 5, then this method will return
     * either 0, 1, 2, 3, or 4, depending on what the remainder is.
     *
     * This differs from using the % operator in that the % operator returns
     * the smallest answer with the same sign as the dividend. So, for example,
     * (-5) % 6 => -5, but with this method, mod(-5, 6) = 1.
     *
     * Examples:
     * mod(-3, 5) => 2
     * mod(11, 6) => 5
     * mod(-7, 7) => 0
     *
     * This helper method is here to make the math part of the circular
     * behavior easier to work with.
     *
     * @param index  the number to take the remainder of
     * @param modulo the divisor to divide by
     * @return the remainder in its smallest non-negative form
     * @throws java.lang.IllegalArgumentException if the modulo is non-positive
     */
    private static int mod(int index, int modulo) {
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive");
        }
        int newIndex = index % modulo;
        return newIndex >= 0 ? newIndex : newIndex + modulo;
    }
}