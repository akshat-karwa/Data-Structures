import java.util.Collection;
/**
 * My implementation of an AVL.
 *
 * @author AKSHAT KARWA
 */
public class AVL<T extends Comparable<? super T>> {

    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     * This constructor initializes an empty AVL.
     */
    public AVL() {}

    /**
     * Constructs a new AVL.
     * This constructor initializes the AVL with the data in the Collection. 
     * The data is added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or element in data is null
     */
    public AVL(Collection<T> data) {
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
            throw new java.lang.IllegalArgumentException("Data is null and contains no value to add to the BST!");
        }
    }

    /**
     * Private Helper Method to balance the AVL through single or double rotations.
     *
     * @param curr is the root of the subtree that will be rotated
     * @return returns the root of the rotated subtree
     */
    private AVLNode<T> rotate(AVLNode<T> curr) {
        if (curr.getBalanceFactor() == -2) {
            if (curr.getRight().getBalanceFactor() <= 0) {
                curr = leftRotate(curr);
            } else if (curr.getRight().getBalanceFactor() == 1) {
                curr.setRight(rightRotate(curr.getRight()));
                curr = leftRotate(curr);
            }
        } else if (curr.getBalanceFactor() == 2) {
            if (curr.getLeft().getBalanceFactor() >= 0) {
                curr = rightRotate(curr);
            } else if (curr.getLeft().getBalanceFactor() == -1) {
                curr.setLeft(leftRotate(curr.getLeft()));
                curr = rightRotate(curr);
            }
        }
        return curr;
    }

    /**
     * Private Helper Method to balance the AVL through a single left rotation
     *
     * @param leftNode is the current root node of the subtree that will be rotated
     *                 This node will become the left node after the rotation
     * @return returns the root node of the new left rotated subtree
     */
    private AVLNode<T> leftRotate(AVLNode<T> leftNode) {
        AVLNode<T> newCenterNode = leftNode.getRight();
        leftNode.setRight(newCenterNode.getLeft());
        newCenterNode.setLeft(leftNode);
        leftNode.setHeight(heightOfNode(leftNode));
        newCenterNode.setHeight(heightOfNode(newCenterNode));
        leftNode.setBalanceFactor(balanceFactor(leftNode));
        newCenterNode.setBalanceFactor(balanceFactor(newCenterNode));
        return newCenterNode;
    }

    /**
     * Private Helper Method to balance the AVL through a single right rotation
     *
     * @param rightNode is the current root node of the subtree that will be rotated
     *                  This node will become the right node after the rotation
     * @return returns the root node of the new right rotated subtree
     */
    private AVLNode<T> rightRotate(AVLNode<T> rightNode) {
        AVLNode<T> newCenterNode = rightNode.getLeft();
        rightNode.setLeft(newCenterNode.getRight());
        newCenterNode.setRight(rightNode);
        rightNode.setHeight(heightOfNode(rightNode));
        newCenterNode.setHeight(heightOfNode(newCenterNode));
        rightNode.setBalanceFactor(balanceFactor(rightNode));
        newCenterNode.setBalanceFactor(balanceFactor(newCenterNode));
        return newCenterNode;
    }

    /**
     * Adds the element to the tree.
     * 
     * Starts by adding it as a leaf like in a regular BST and then rotates the
     * tree as necessary.
     * If the data is already in the tree, then nothing is done (the
     * duplicate isn't added, and size is not incremented).
     * 
     * We recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * needed.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to add to the AVL cannot be null!!");
        }
        this.root = rAdd(this.root, data);
    }

    /**
     * Adds the data recursively to the AVL Tree.
     * The data becomes a leaf in the AVL Tree.
     * 
     * Recursively traverses the AVL to the location to add.
     * If an AVLNode already contains the data, then it returns the AVLNode itself.
     * Otherwise, a new AVLNode (a leaf) is created and the data is added.
     * Maintains the height and balanceFactors of the AVLNodes
     * by updating them when returning from the recursive add process.
     *
     * @param curr is the current AVLNode while traversing the AVL Tree.
     * @param data the data to add
     * @return returns the root BSTNode after recursively adding the data
     */
    private AVLNode<T> rAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            this.size++;
            curr = new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        curr.setHeight(heightOfNode(curr));
        curr.setBalanceFactor(balanceFactor(curr));
        if (curr.getBalanceFactor() == -2 || curr.getBalanceFactor() == 2) {
            curr = rotate(curr);
        }
        return curr;
    }


    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     * 
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * we simply remove it.
     * 2: The node containing the data has one child. In this case, we simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. We use the predecessor to
     * replace the data, NOT the successor. 
     * 
     * We recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * needed.
     * We return the data that was stored in the tree.
     * 
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data to remove from the AVL cannot be null!!");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        this.root = rRemove(this.root, data, dummy);
        if (dummy.getData() == null) {
            throw new java.util.NoSuchElementException("Data to remove does not exist in the AVL!!");
        }
        return dummy.getData();
    }

    /**
     * Additional private helper method that recursively removes the
     * AVLNode containing the data using 3 cases.
     * 
     * Recursively removes the data from the tree that is equal to
     * the data passed in and returns the root AVLNode of the new tree with
     * the data removed.
     * 
     * Uses 3 cases during recursion.
     * 1: In case of 0 children, directly removes the AVLNode.
     * 2: In case of 1 child, replaces the AVLNode with its child.
     * 3: In case of 2 children, uses the predecessor of the Node and
     * replaces the AVLNode's data with its predecessor's data.
     * Calls an additional recursive method for case 3 which
     * recursively removes the predecessor AVLNode from the subtree,
     * stores its data in another dummy2 AVLNode.
     * The rRemove method then replaces the removed
     * AVLNode with its predecessor and connects the new subtree using
     * Pointer Reinforcement.
     *
     * @param curr  is the current AVLNode in the recursive remove method
     * @param data  is the data to be removed from the AVL Tree
     * @param dummy is a dummy AVLNode used to store the data of the node removed
     * @return returns the root AVLNode after recursively removing the data
     */
    private AVLNode<T> rRemove(AVLNode<T> curr, T data, AVLNode<T> dummy) {
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
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        curr.setHeight(heightOfNode(curr));
        curr.setBalanceFactor(balanceFactor(curr));
        if (curr.getBalanceFactor() == -2 || curr.getBalanceFactor() == 2) {
            curr = rotate(curr);
        }
        return curr;
    }

    /**
     * Additional private helper method that
     * handles the case when the removed Node has 2 children.
     * 
     * Recursively removes the predecessor AVLNode from the subtree,
     * stores its data in a dummy2 AVLNode and return the subtree
     * that is then connected to this AVL Tree using
     * Pointer Reinforcement.
     *
     * @param curr   is the current AVLNode in the recursive remove method
     * @param dummy2 is a dummy AVLNode used to replace the removed Node with predecessor AVLNode
     * @return returns a new subtree without the successor AVLNode
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy2) {
        if (curr.getRight() == null) {
            dummy2.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy2));
            curr.setHeight(heightOfNode(curr));
            curr.setBalanceFactor(balanceFactor(curr));
            if (curr.getBalanceFactor() == -2 || curr.getBalanceFactor() == 2) {
                curr = rotate(curr);
            }
            return curr;
        }
    }


    /**
     * Returns the element from the tree matching the given parameter.
     * 
     * We return the data that was stored in the tree.
     *
     * @param data the data to search for in the tree
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
     * @param curr is the current AVLNode in the recursive get method
     * @param data is the data to get from the AVL Tree
     * @return returns the data in the recursive structure
     */
    private T rGet(AVLNode<T> curr, T data) {
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
     *
     * @param data the data to search for in the tree.
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
     * @param curr is the current AVLNode in the recursive contains method
     * @param data is the data to check for in the AVL Tree
     * @return returns true or false after checking for data
     */
    private boolean rContains(AVLNode<T> curr, T data) {
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
     * Returns the height of the root of the tree.
     * O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightOfNode(this.root);
    }

    /**
     * Clears the tree.
     * Clears all data and resets the size.
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with
     * the deepest depth.
     * Recursive and runs in O(log n) for all cases.
     * 
     * Example
     * Tree:
     * 2
     * /    \
     * 0      3
     * \
     * 1
     * Max Deepest Node:
     * 1 because it is the deepest node
     * <p>
     * Example
     * Tree:
     * 2
     * /    \
     * 0      4
     * \    /
     * 1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (this.root == null) {
            return null;
        }
        return rMaxDeepestNode(this.root);
    }

    /**
     * Private Recursive Method that recursively returns the data
     * in the deepest node of the AVL Tree.
     *
     * @param curr is the current AVLNode in the recursive method
     * @return returns the data in the deepest node in the AVL
     */
    private T rMaxDeepestNode(AVLNode<T> curr) {
        if (curr.getHeight() == 0) {
            return curr.getData();
        }
        if (curr.getLeft() == null && curr.getRight() != null) {
            return rMaxDeepestNode(curr.getRight());
        } else if (curr.getRight() == null && curr.getLeft() != null) {
            return rMaxDeepestNode(curr.getLeft());
        } else {
            if (curr.getLeft().getHeight() > curr.getRight().getHeight()) {
                return rMaxDeepestNode(curr.getLeft());
            } else {
                return rMaxDeepestNode(curr.getRight());
            }
        }
    }

    /**
     * In BSTs, we have the concept of the successor: the
     * smallest data that is larger than the current data. However, we only
     * have it in the context of the 2-child remove case.
     * 
     * This method retrieves (but not removes) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data.
     * 
     * The second case means the successor node will be one of the node(s) we
     * traversed left from to find data. Since the successor is the SMALLEST element
     * greater than data, the successor node is the lowest/last node
     * we traversed left from on the path to the data node.
     * Method is recursive.
     * 
     * Ex:
     * Given the following AVL composed of Integers
     * 76
     * /    \
     * 34      90
     * \    /
     * 40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data cannot be null!! No successor!!");
        }
        AVLNode<T> successor = new AVLNode<>(null);
        boolean dataFound = rSuccessor(this.root, data, successor);
        if (!dataFound) {
            throw new java.util.NoSuchElementException("Data does not exist in the AVL Tree!!");
        }
        return successor.getData();
    }

    /**
     * Private helper method that retrieves the successor of the data passed in.
     * Handles two cases:
     * The non-empty right subtree: Recursively retrieves the successor data.
     * The empty right subtree: Gets the lowest/last node we traversed
     * left from on the path to the data node and sets the dummy node's data
     * equal to this node's data.
     *
     * @param curr      is the current AVLNode in the recursive remove method
     * @param data      is the data whose successor we find
     * @param successor is a dummy node containing the successor data
     * @return returns whether the data exists in the AVL or not
     */
    private boolean rSuccessor(AVLNode<T> curr, T data, AVLNode<T> successor) {
        if (curr == null) {
            return false;
        } else if (data.compareTo(curr.getData()) < 0) {
            boolean check = rSuccessor(curr.getLeft(), data, successor);
            if (check && successor.getData() == null) {
                successor.setData(curr.getData());
            }
            return check;
        } else if (data.compareTo(curr.getData()) > 0) {
            return rSuccessor(curr.getRight(), data, successor);
        } else {
            if (curr.getRight() != null) {
                successor.setData(getSuccessorData(curr.getRight()));
            }
            return true;
        }
    }

    /**
     * Private Recursive Helper Method to find the successor data
     * if the right node is not null
     *
     * @param curr is the current node in the recursive structure
     * @return returns the successor data
     */
    private T getSuccessorData(AVLNode<T> curr) {
        if (curr.getLeft() == null) {
            return curr.getData();
        } else {
            return getSuccessorData(curr.getLeft());
        }
    }

    /**
     * Returns the root of the tree.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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

    /**
     * Private Helper Method that calculates the balance factor of a given node.
     * O(1) time complexity.
     *
     * @param curr is the current node whose balance factor is to be calculated
     * @return returns the balance factor of the current node
     */
    private int balanceFactor(AVLNode<T> curr) {
        if (curr.getLeft() == null && curr.getRight() == null) {
            return 0;
        } else if (curr.getLeft() == null && curr.getRight() != null) {
            return ((-1) - curr.getRight().getHeight());
        } else if (curr.getRight() == null && curr.getLeft() != null) {
            return (curr.getLeft().getHeight() + 1);
        } else {
            return (curr.getLeft().getHeight() - curr.getRight().getHeight());
        }
    }

    /**
     * Private Helper Method that calculates the height of a given node.
     * O(1) time complexity.
     *
     * @param curr is the current node whose height is to be calculated
     * @return returns the height of the current node
     */
    private int heightOfNode(AVLNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        if (curr.getLeft() == null && curr.getRight() == null) {
            return 0;
        } else if (curr.getLeft() == null && curr.getRight() != null) {
            return (curr.getRight().getHeight() + 1);
        } else if (curr.getRight() == null && curr.getLeft() != null) {
            return (curr.getLeft().getHeight() + 1);
        } else {
            if (curr.getLeft().getHeight() > curr.getRight().getHeight()) {
                return (curr.getLeft().getHeight() + 1);
            } else {
                return (curr.getRight().getHeight() + 1);
            }
        }
    }
}