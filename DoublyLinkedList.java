import java.util.NoSuchElementException;
/**
 * My implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author AKSHAT KARWA
 */
public class DoublyLinkedList<T> {

    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the specified index.
     * O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Invalid index! Index is less than 0 or greater "
                    + "than the size of the LinkedList.");
        } else if (data == null) {
            throw new IllegalArgumentException("Data to be added is null!");
        } else {
            if (index == 0) {
                addToFront(data);
                return;
            } else if (index == this.size) {
                addToBack(data);
                return;
            } else if (index <= this.size / 2) {
                DoublyLinkedListNode<T> curr = this.head;
                addRecursively(curr, index, data, 0);
            } else {
                DoublyLinkedListNode<T> curr = this.tail;
                addRecursively(curr, index, data, this.size - 1);
            }
            this.size++;
        }
    }

    /**
     * Helper Method
     * Adds the element to the specified index, after traversing the list recursively from
     * the head or the tail based on the specified index (whichever is more efficient).
     *
     * @param curr is the current node in the recursive structure
     *             (initially the head/tail node)
     * @param index is the index at which the data is to be added
     * @param data is the data to be added
     * @param counter is a variable used to traverse forward or backward in the list.
     *                (index of the current node)
     */
    private void addRecursively(DoublyLinkedListNode<T> curr, int index, T data, int counter) {
        if (index <= this.size / 2) {
            if (counter == index - 1) {
                DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, curr, curr.getNext());
                curr.setNext(newNode);
                newNode.getNext().setPrevious(newNode);
                return;
            }
            addRecursively(curr.getNext(), index, data, counter + 1);
        } else {
            if (counter == index) {
                DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, curr.getPrevious(), curr);
                curr.setPrevious(newNode);
                newNode.getPrevious().setNext(newNode);
                return;
            }
            addRecursively(curr.getPrevious(), index, data, counter - 1);
        }
    }

    /**
     * Adds the element to the front of the list.
     * O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be added is null!");
        } else {
            if (this.size == 0) {
                this.head = new DoublyLinkedListNode<>(data);
                this.tail = this.head;
            } else {
                DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, this.head);
                this.head.setPrevious(newNode);
                this.head = newNode;
            }
            this.size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     * O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be added is null!");
        }
        if (this.size == 0) {
            this.tail = new DoublyLinkedListNode<>(data);
            this.head = this.tail;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, this.tail, null);
            this.tail.setNext(newNode);
            this.tail = newNode;
        }
        this.size++;
    }

    /**
     * Removes and returns the element at the specified index. 
     * O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Invalid index! Index is less than 0 or greater "
                    + "than equal to the size of the LinkedList.");
        }
        if (index == 0) {
            return removeFromFront();
        } else if (index == this.size - 1) {
            return removeFromBack();
        } else if (index < this.size / 2) {
            DoublyLinkedListNode<T> curr = this.head;
            return removeRecursively(curr, index, 0);
        } else {
            DoublyLinkedListNode<T> curr = this.tail;
            return removeRecursively(curr, index, this.size - 1);
        }
    }

    /**
     * Helper Method
     * Removes the element at the specified index, after traversing the list recursively from
     * the head or the tail based on the specified index (whichever is more efficient).
     *
     * @param curr is the current node in the recursive structure
     *             (initially the head/tail node)
     * @param index is the index from which the data is to be removed
     * @param counter is a variable used to traverse forward or backward in the list.
     *                (index of the current node)
     * @return the data formerly located at the specified index
     */
    private T removeRecursively(DoublyLinkedListNode<T> curr, int index, int counter) {
        if (counter == index) {
            T removed = curr.getData();
            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());
            this.size--;
            return removed;
        }
        if (index < this.size / 2) {
            return removeRecursively(curr.getNext(), index, counter + 1);
        } else {
            return removeRecursively(curr.getPrevious(), index, counter - 1);
        }
    }

    /**
     * Removes and returns the first element of the list.
     * O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.size == 0) {
            throw new NoSuchElementException("The List is Empty!");
        }
        T removed = this.head.getData();
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.head.getNext().setPrevious(null);
            this.head = this.head.getNext();
        }
        this.size--;
        return removed;
    }

    /**
     * Removes and returns the last element of the list.
     * O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.size == 0) {
            throw new NoSuchElementException("The List is Empty!");
        }
        T removed = this.tail.getData();
        if (this.size == 1) {
            this.head = null;
            this.tail = null;
        } else {
            this.tail.getPrevious().setNext(null);
            this.tail = this.tail.getPrevious();
        }
        this.size--;
        return removed;
    }

    /**
     * Returns the element at the specified index. 
     * O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Invalid index! Index is less than 0 or greater "
                    + "than equal to the size of the LinkedList.");
        }
        if (index == 0) {
            return this.head.getData();
        } else if (index == this.size - 1) {
            return this.tail.getData();
        } else if (index < this.size / 2) {
            DoublyLinkedListNode<T> curr = this.head;
            return getRecursively(curr, index, 0);
        } else {
            DoublyLinkedListNode<T> curr = this.tail;
            return getRecursively(curr, index, this.size - 1);
        }
    }

    /**
     * Helper Method
     * Returns the element at the specified index, after traversing
     * the list recursively from the head or the tail based on whichever is
     * more efficient.
     *
     * @param curr is the current node in the recursive structure
     *             (initially the head/tail node)
     * @param index is the index from which the data is to be retrieved
     * @param counter is a variable used to traverse forward or backward in the list.
     *                (index of the current node)
     * @return the data located at the specified index
     */
    private T getRecursively(DoublyLinkedListNode<T> curr, int index, int counter) {
        if (index < this.size / 2) {
            if (counter == index - 1) {
                return curr.getNext().getData();
            }
            return getRecursively(curr.getNext(), index, counter + 1);
        } else {
            if (counter == index + 1) {
                return curr.getPrevious().getData();
            }
            return getRecursively(curr.getPrevious(), index, counter - 1);
        }
    }

    /**
     * Returns whether or not the list is empty.
     * O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Clears the list.
     * Clears all data and resets the size.
     * O(1).
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data to be removed is null!");
        } else if (this.size == 0) {
            throw new NoSuchElementException("Data not found in the LinkedList!");
        } else {
            if (this.tail.getData().equals(data)) {
                return removeFromBack();
            } else {
                DoublyLinkedListNode<T> curr = this.tail;
                T removed = removeOccurrence(data, curr);
                if (removed == null) {
                    throw new NoSuchElementException("Data not found in the LinkedList!");
                }
                return removed;
            }
        }
    }

    /**
     * Helper Method
     * Removes and returns the last copy of the given data from the list,
     * after checking the LinkedList while recursively moving backwards.
     *
     * @param data is the data to be removed from the list
     * @param curr is the current node in the recursive structure
     * @return the data that was removed
     */
    private T removeOccurrence(T data, DoublyLinkedListNode<T> curr) {
        if (curr == this.head) {
            if (this.head.getData().equals(data)) {
                return removeFromFront();
            } else {
                return null;
            }
        }
        if (curr.getData().equals(data)) {
            T removed = curr.getData();
            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());
            this.size--;
            return removed;
        }
        return removeOccurrence(data, curr.getPrevious());
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, returns an empty array.
     * O(n) for all cases.
     *
     * @return an array of length size holding all the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        DoublyLinkedListNode<T> curr = this.head;
        if (this.size != 0) {
            for (int i = 0; i < this.size; i++) {
                arr[i] = curr.getData();
                curr = curr.getNext();
            }
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     * 
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * @return the size of the list
     */
    public int size() {
        return size;
    }
}
