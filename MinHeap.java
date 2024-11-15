import java.util.ArrayList;
import java.util.NoSuchElementException;
/**
 * My implementation of a MinHeap.
 *
 * @author Akshat Karwa
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     */
    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array has an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, we create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     * We build the heap from the bottom up by repeated use of downHeap operations.
     * 
     * We first copy over the data from the ArrayList to the backingArray 
     * (leaving index 0 of the backingArray empty). The data in the backingArray is in 
     * the same order as it appears in the passed in ArrayList before we start the BuildHeap
     * algorithm.
     *
     * The backingArray has capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0
     * remains empty, indices 1 to n contain the data in proper order, and
     * the rest of the indices are empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The arrayList of data is null. There is no data to add!");
        }
        int sizeOfArrayList = data.size();
        this.backingArray = (T[]) new Comparable[(2 * sizeOfArrayList) + 1];
        for (int index = 0; index < sizeOfArrayList; index++) {
            T dataToAdd = data.get(index);
            if (dataToAdd == null) {
                throw new IllegalArgumentException("Element in ArrayList is null."
                        + "There is no data to add!");
            }
            this.backingArray[index + 1] = dataToAdd;
            this.size++;
        }
        for (int pIndex = (this.size / 2); pIndex >= 1; pIndex--) {
            downHeap(pIndex);
        }
    }

    /**
     * Private helper method that takes in the index of the parent,
     * and if any child is smaller than the parent, swaps the child
     * and the parent. Accounts for both cases: one child and two children.
     *
     * Compares left and right child to know the smaller child and then swaps
     * with the parent, if parent is larger.
     *
     * @param index is the index of the parent in the backingArray
     */
    private void downHeap(int index) {
        int lChild = 2 * index;
        int rChild = (2 * index) + 1;
        int child;
        if (rChild != (this.size + 1)) {
            if (this.backingArray[lChild].compareTo(this.backingArray[rChild]) < 0) {
                child = lChild;
            } else {
                child = rChild;
            }
        } else {
            child = lChild;
        }
        if (this.backingArray[child].compareTo(this.backingArray[index]) < 0) {
            T temp = this.backingArray[index];
            this.backingArray[index] = this.backingArray[child];
            this.backingArray[child] = temp;
            if (this.size >= (2 * child)) {
                downHeap(child);
            }
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and we're trying to add a new item, we double its capacity.
     * The order property of the heap is maintained after adding. We
     * assume that no duplicate data will be passed in.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to add cannot be null!!");
        }
        if (this.size == (this.backingArray.length - 1)) {
            T[] newArray = (T[]) new Comparable[(2 * this.backingArray.length)];
            for (int index = 1; index <= this.size; index++) {
                newArray[index] = this.backingArray[index];
            }
            this.backingArray = newArray;
        }
        this.backingArray[this.size + 1] = data;
        this.size++;
        if (this.size > 1) {
            upHeap(this.size);
        }
    }

    /**
     * Private recursive helper method that takes in the index of the child,
     * and if the parent is larger than the child, swaps the child
     * and the parent, till the order property of the binary heap is maintained.
     * UpHeaps if child is smaller.
     *
     * @param index is the index of the child in the backingArray
     */
    private void upHeap(int index) {
        int parent = index / 2;
        if (this.backingArray[index].compareTo(this.backingArray[parent]) < 0) {
            T temp = this.backingArray[parent];
            this.backingArray[parent] = this.backingArray[index];
            this.backingArray[index] = temp;
            if (parent > 1) {
                upHeap(parent);
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, we null out spots as we remove. We do not decrease the
     * capacity of the backing array.
     * The order property of the heap is maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (this.size == 0) {
            throw new NoSuchElementException("The heap is empty. There is no element to remove!!");
        }
        T removed = this.backingArray[1];
        if (this.size == 1) {
            this.backingArray[this.size] = null;
            this.size--;
        } else {
            this.backingArray[1] = this.backingArray[this.size];
            this.backingArray[this.size] = null;
            this.size--;
            if (this.size > 1) {
                downHeap(1);
            }
        }
        return removed;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (this.size == 0) {
            throw new NoSuchElementException("The heap is empty. There is no element to get!!");
        }
        return this.backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (this.size == 0);
    }

    /**
     * Clears the heap.
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Returns the backing array of the heap.
     * 
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     * 
     * @return the size of the list
     */
    public int size() {
        return size;
    }
}
