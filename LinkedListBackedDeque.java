/**
 * My implementation of a LinkedListBackedDeque.
 *
 * @author AKSHAT KARWA
 */
public class LinkedListBackedDeque<T> {

    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the front of the deque in O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to add cannot be null!!");
        }
        if (this.size == 0) {
            LinkedListNode<T> newNode = new LinkedListNode<>(data);
            this.head = newNode;
            this.tail = this.head;
        } else {
            LinkedListNode<T> newNode = new LinkedListNode<>(data, null, this.head);
            this.head.setPrevious(newNode);
            this.head = newNode;
        }
        this.size++;
    }

    /**
     * Adds the element to the back of the deque in O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to add cannot be null!!");
        }
        if (this.size == 0) {
            LinkedListNode<T> newNode = new LinkedListNode<>(data);
            this.head = newNode;
            this.tail = this.head;
        } else {
            LinkedListNode<T> newNode = new LinkedListNode<>(data, this.tail, null);
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.size++;
    }

    /**
     * Removes and returns the first element of the deque in O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("The Deque is Empty! No element to remove!");
        }
        T removed = this.head.getData();
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
            this.size--;
            return removed;
        }
        this.head = this.head.getNext();
        this.head.setPrevious(null);
        this.size--;
        return removed;
    }

    /**
     * Removes and returns the last element of the deque in O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("The Deque is Empty! No element to remove!");
        }
        T removed = this.tail.getData();
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
            this.size--;
            return removed;
        }
        this.tail = this.tail.getPrevious();
        this.tail.setNext(null);
        this.size--;
        return removed;
    }

    /**
     * Returns the first data of the deque without removing it in O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("The Deque is Empty! No element to get!");
        }
        return this.head.getData();
    }

    /**
     * Returns the last data of the deque without removing it in O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (this.size == 0) {
            throw new java.util.NoSuchElementException("The Deque is Empty! No element to get!");
        }
        return this.tail.getData();
    }

    /**
     * Returns the head node of the deque.
     * 
     * @return node at the head of the deque
     */
    public LinkedListNode<T> getHead() {
        return head;
    }

    /**
     * Returns the tail node of the deque.
     * 
     * @return node at the head of the deque
     */
    public LinkedListNode<T> getTail() {
        return tail;
    }

    /**
     * Returns the size of the deque.
     * 
     * @return the size of the deque
     */
    public int size() {
        return size;
    }
}
