import java.util.Collection;
import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;

/**
 * My implementation of a BST.
 *
 * @author AKSHAT KARWA
 */
public class BST<T extends Comparable<? super T>> {

    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * This constructor initializes an empty BST.
     */
    public BST() {}

    /**
     * Constructs a new BST.
     * This constructor initializes the BST with the data in the
     * Collection. The data is added in the same order it is in the
     * Collection.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data != null) {
            for (T value : data) {
                if (value != null) {
                    add(value);
                } else {
                    throw new java.lang.IllegalArgumentException("Value in collection(data) is"
                            + "null and cannot be added!!");
                }
            }
        } else {
            throw new java.lang.IllegalArgumentException("Data is null contains no value to add to the BST!");
        }
    }

    /**
     * Adds the data to the tree.
     * This is done recursively.
     * The data becomes a leaf in the tree.
     * 
     * Traverses the tree to find the appropriate location. If the data is
     * already in the tree, then nothing is done (the duplicate
     * isn't added, and size is not incremented).
     * O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to add to the BST cannot be null!!");
        }
        this.root = rAdd(this.root, data);
    }

    /**
     * Adds the data recursively to the tree.
     * The data becomes a leaf in the tree.
     * Recursively traverses the binary search tree to the location to add.
     * If a BSTNode already contains the data, then it returns the BSTNode itself.
     * Otherwise, a new BSTNode (a leaf) is created and the data is added.
     *
     * @param curr is the current BSTNode while traversing the Binary Search Tree.
     * @param data the data to add
     * @return returns the root BSTNode after recursively adding the data
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            this.size++;
            curr = new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * This is done recursively.
     * 
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * we simply remove it.
     * 2: The node containing the data has one child. In this case, we simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. We use the successor to
     * replace the data. We use recursion to find and remove the
     * successor.
     * We return the data that was stored in the tree.
     * O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to remove from the BST cannot be null!!");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        this.root = rRemove(this.root, data, dummy);
        if (dummy.getData() == null) {
            throw new java.util.NoSuchElementException("Data to remove does not exist in the BST!!");
        }
        return dummy.getData();
    }

    /**
     * Additional private helper method that recursively removes the
     * BSTNode containing the data using 3 cases.
     * Recursively removes the data from the tree that is equal to
     * the data passed in and returns the root BSTNode of the new tree with
     * the data removed.
     * Uses 3 cases during recursion.
     * 1: In case of 0 children, directly removes the BSTNode.
     * 2: In case of 1 child, replaces the BSTNode with its child.
     * 3: In case of 2 children, uses the successor of the Node and
     * replaces the BSTNode's data with its successor's data.
     * Calls an additional recursive method for case 3 which
     * recursively removes the successor BSTNode from the subtree,
     * stores its data in another dummy2 BSTNode.
     * The rRemove method then replaces the removed
     * BSTNode with its successor and connects the new subtree using
     * Pointer Reinforcement.
     *
     * @param curr  is the current BSTNode in the recursive remove method
     * @param data  is the data to be removed from the Binary Search Tree
     * @param dummy is a dummy BSTNode used to store the data of the node removed
     * @return returns the root BSTNode after recursively removing the data
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            return null;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            this.size--;
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            } else if (curr.getRight() == null && curr.getLeft() != null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null && curr.getRight() != null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * Additional private helper method that
     * handles the case when the removed Node has 2 children.
     * Recursively removes the successor BSTNode from the subtree,
     * stores its data in a dummy2 BSTNode and return the subtree
     * that is then connected to this Binary Search Tree using
     * Pointer Reinforcement.
     *
     * @param curr   is the current BSTNode in the recursive remove method
     * @param dummy2 is a dummy BSTNode used to replace the removed Node with successor BSTNode
     * @return returns a new subtree without the successor BSTNode
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy2) {
        if (curr.getLeft() == null) {
            dummy2.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy2));
            return curr;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter
     * This is done recursively.
     * We return the data that was stored in the tree.
     * 
     * O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to search for cannot be null!!");
        }
        T returnedData = rGet(this.root, data);
        if (returnedData == null) {
            throw new java.util.NoSuchElementException("Data searched does not exist in the BST!!");
        } else {
            return returnedData;
        }
    }

    /**
     * Private Helped Method that recursively searches for specified data,
     * and returns data from the tree that is equal to the data passed in.
     *
     * @param curr is the current BSTNode in the recursive get method
     * @param data is the data to get from the Binary Search Tree
     * @return returns the data in the recursive structure
     */
    private T rGet(BSTNode<T> curr, T data) {
        if (curr == null) {
            return null;
        } else if (data.compareTo(curr.getData()) < 0) {
            return rGet(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return rGet(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * This is done recursively.
     * 
     * O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to check for cannot be null!!");
        }
        return rContains(this.root, data);
    }

    /**
     * Private Helped Method that recursively searches for specified data,
     * and returns true or false based on whether the data is found or not.
     *
     * @param curr is the current BSTNode in the recursive contains method
     * @param data is the data to check for in the Binary Search Tree
     * @return returns true or false after checking for data
     */
    private boolean rContains(BSTNode<T> curr, T data) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) < 0) {
            return rContains(curr.getLeft(), data);
        } else if (data.compareTo(curr.getData()) > 0) {
            return rContains(curr.getRight(), data);
        } else {
            return true;
        }
    }

    /**
     * Generates a pre-order traversal of the tree.
     * This is done recursively in O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> preorder = new ArrayList<>(this.size);
        return rPreorder(this.root, preorder);
    }

    /**
     * Private Helped Method that recursively adds data using preorder traversal
     * from the BST to the List.
     *
     * @param curr is the current BSTNode in the recursive preorder traversal
     * @param list is the List with the data in the BST
     * @return returns the list after adding data to it from the BST
     */
    private List<T> rPreorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            rPreorder(curr.getLeft(), list);
            rPreorder(curr.getRight(), list);
        }
        return list;
    }

    /**
     * Generates an in-order traversal of the tree.
     * This is done recursively in O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> inorder = new ArrayList<>(this.size);
        return rInorder(this.root, inorder);
    }

    /**
     * Private Helped Method that recursively adds data using inorder traversal
     * from the BST to the List.
     *
     * @param curr is the current BSTNode in the recursive inorder traversal
     * @param list is the List with the data in the BST
     * @return returns the list after adding data to it from the BST
     */
    private List<T> rInorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            rInorder(curr.getLeft(), list);
            list.add(curr.getData());
            rInorder(curr.getRight(), list);
        }
        return list;
    }

    /**
     * Generates a post-order traversal of the tree.
     * This is done recursively in O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> postorder = new ArrayList<>(this.size);
        return rPostorder(this.root, postorder);
    }

    /**
     * Private Helped Method that recursively adds data using postorder traversal
     * from the BST to the List.
     *
     * @param curr is the current BSTNode in the recursive postorder traversal
     * @param list is the List with the data in the BST
     * @return returns the list after adding data to it from the BST
     */
    private List<T> rPostorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            rPostorder(curr.getLeft(), list);
            rPostorder(curr.getRight(), list);
            list.add(curr.getData());
        }
        return list;
    }

    /**
     * Generates a level-order traversal of the tree.
     * We use a queue of nodes and do this in O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> levelorder = new ArrayList<>(this.size);
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(this.root);
        while (queue.peek() != null) {
            BSTNode<T> curr = queue.remove();
            levelorder.add(curr.getData());
            if (curr.getLeft() != null) {
                queue.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                queue.add(curr.getRight());
            }
        }
        return levelorder;
    }

    /**
     * Returns the height of the root of the tree.
     * This is done recursively in O(n).
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(this.root);
    }

    /**
     * Private Helped Method that recursively calculates
     * the height of the root of the BST.
     *
     * @param curr is the current BSTNode in the recursive traversal
     * @return returns the height of the root of the BST
     */
    private int rHeight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        int lNodeHeight = rHeight(curr.getLeft());
        int rNodeHeight = rHeight(curr.getRight());

        if (lNodeHeight > rNodeHeight) {
            return (lNodeHeight + 1);
        } else {
            return (rNodeHeight + 1);
        }
    }

    /**
     * Clears the tree.
     * Clears all data and resets the size.
     * O(1).
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * This is done recursively.
     * The returned list contains the last node of each level.
     * If the tree is empty, an empty list is returned.
     * 
     * Ex:
     * Given the following BST composed of Integers
     * 2
     * /   \
     * 1     4
     * /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     * <p>
     * Ex:
     * Given the following BST composed of Integers
     * 50
     * /        \
     * 25         75
     * /    \
     * 12    37
     * /  \    \
     * 11   15   40
     * /
     * 10
     * getMaxDataPerLevel() returns the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     * O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> listOfMaxDataPerLevel = new ArrayList<>();
        rGetMaxDataPerLevel(this.root, listOfMaxDataPerLevel, 0);
        return listOfMaxDataPerLevel;
    }

    /**
     * Private Helped Method that recursively gets the max data value
     * at every level in the BST
     *
     * @param curr  is the current BSTNode in the recursive traversal
     * @param list  is the list with the max data value at every level
     * @param level is the current level in the recursive structure
     */
    private void rGetMaxDataPerLevel(BSTNode<T> curr, List<T> list, int level) {
        if (curr != null) {
            if (level == list.size()) {
                list.add(curr.getData());
            }
            rGetMaxDataPerLevel(curr.getRight(), list, level + 1);
            rGetMaxDataPerLevel(curr.getLeft(), list, level + 1);
        }
    }

    /**
     * Returns the root of the tree.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }

    /**
     * Returns the size of the tree.
     * 
     * @return the size of the tree
     */
    public int size() {
        return size;
    }
}