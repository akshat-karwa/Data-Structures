/**
 * Node class used for implementing my linked data structures.
 *
 * @author AKSHAT KARWA
 */
public class LinkedListNode<T> {

    private T data;
    private LinkedListNode<T> previous;
    private LinkedListNode<T> next;

    /**
     * Constructs a new LinkedListNode with the given data and node references.
     *
     * @param data     the data stored in the new node
     * @param previous the previous node in the structure
     * @param next     the next node in the structure
     */
    public LinkedListNode(T data, LinkedListNode<T> previous, LinkedListNode<T> next) {
        this.data = data;
        this.previous = previous;
        this.next = next;
    }

    /**
     * Constructs a new LinkedListNode with only the given data.
     *
     * @param data the data stored in the new node
     */
    public LinkedListNode(T data) {
        this(data, null, null);
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Gets the previous node.
     *
     * @return the previous node
     */
    public LinkedListNode<T> getPrevious() {
        return previous;
    }

    /**
     * Gets the next node.
     *
     * @return the next node
     */
    public LinkedListNode<T> getNext() {
        return next;
    }

    /**
     * Sets the previous node.
     *
     * @param previous the new previous node
     */
    public void setPrevious(LinkedListNode<T> previous) {
        this.previous = previous;
    }

    /**
     * Sets the next node.
     *
     * @param next the new next node
     */
    public void setNext(LinkedListNode<T> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node containing: " + data;
    }
}