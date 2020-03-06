import java.lang.Math;
import java.util.ArrayList;

class BBST {

    // Var to track level changes
    static int levelCountBBST = 0;

    public static void main(String[] args) {
        int[] randArr = Arrays.getRandomArray(10000);
        int[] sortArr = Arrays.getSortedArray(10000);

        // Initialize the root nodes
        Node rootBBST1 = new Node(randArr[0]);
        Node rootBBST2 = new Node(sortArr[0]);
        Node rootBST1 = new Node(randArr[0]);
        Node rootBST2 = new Node(sortArr[0]);
        Node rootBST3 = new Node(randArr[0]);
        Node rootBST4 = new Node(sortArr[0]);

        // Populate BST and BBST using random array
        for (int i = 0; i < randArr.length; i++) {
            // Insert into BBST
            rootBBST1 = insertIter(rootBBST1, randArr[i]);
            // Insert into BST
            BST.insertRec(rootBST1, randArr[i]);
            BST.insertIter(rootBST3, randArr[i]);
        }
        // Populate BST and BBST using sorted array
        for (int i = 1; i < sortArr.length; i++) {
            // Insert into BBST
            rootBBST2 = insertIter(rootBBST2, sortArr[i]);
            // Insert into BST
            BST.insertRec(rootBST2, sortArr[i]);
            BST.insertIter(rootBST4, sortArr[i]);
        }
    }

    static Node insertIter(Node root, int data) {
        Node curr = root;
        boolean insertFlag = true;
        // While node isn't inserted
        while (insertFlag) {
            // If data is greater than current node data
            if (curr.data < data) {
                // There is no right child
                if (curr.rightChild == null) {
                    // Create right child with data, node has been inserted
                    curr.rightChild = new Node(data);
                    curr.rightChild.parent = curr;
                    curr = curr.rightChild;
                    insertFlag = false;
                }
                else {
                    // Go to top of loop with curr at root of right subtree
                    curr = curr.rightChild;
                    levelCountBBST++;
                }
            }
            // If data less than current node data
            else {
                // If no left child
                if (curr.leftChild == null) {
                    // Create one with data, node has been inserted
                    curr.leftChild = new Node(data);
                    curr.leftChild.parent = curr;
                    curr = curr.leftChild;
                    insertFlag = false;
                }
                else {
                    // Go to top of loop with curr as root of left subtree
                    curr = curr.leftChild;
                    levelCountBBST++;
                }
            }
        }

        // New root to be returned later
        Node newRoot = root;

        // Iterate up through tree, checking bf of nodes
        while (curr != null) {
            levelCountBBST++;
            // Check for new root
            if (curr.parent == null) {
                newRoot = curr;
            }

            // Increase the height of current node
            if (curr.leftChild != null || curr.rightChild != null) {
                if (curr.leftChild == null && curr.rightChild != null) {
                    curr.height = curr.rightChild.height + 1;
                }
                else if (curr.rightChild == null && curr.leftChild != null){
                    curr.height = curr.leftChild.height + 1;
                }
                else {
                    if (curr.leftChild.height > curr.rightChild.height) {
                        curr.height = curr.leftChild.height + 1;
                    }
                    else {
                        curr.height = curr.rightChild.height + 1;
                    }
                }
            }
            else {
                curr.height = 1;
            }

            // If the tree is balanced, iterate up to curr's parent
            //System.out.println(curr.data + ": " + balanceFactor(curr));
            if (balanceFactor(curr) == 0 || Math.abs(balanceFactor(curr)) == 1) {
                curr = curr.parent;
            }
            // If the subtree is left balanced
            else if (balanceFactor(curr) > 1) {
                // If its a left-left case
                if (balanceFactor(curr.leftChild) > 0) {
                    // Right rotate
                    rightRotate(curr);
                }
                // If its a left-right case
                else if (balanceFactor(curr.leftChild) < 0) {
                    // Left rotate on curr left, right rotate on curr
                    leftRotate(curr.leftChild);
                    rightRotate(curr);
                }
            }
            // If the subtree is right balanced
            else if (balanceFactor(curr) < -1) {
                // Right-right case
                if (balanceFactor(curr.rightChild) < 0) {
                    // Left rotate curr
                    leftRotate(curr);
                }
                // Right-left case
                else if (balanceFactor(curr.rightChild) > 0) {
                    // Right rotate curr right, left rotate curr
                    rightRotate(curr.rightChild);
                    leftRotate(curr);
                }
            }
        }
        return newRoot;
    }

    static void deleteIter(Node root, int data) {
        Node curr = root;
        boolean delFlag = true;

        while (delFlag) {
            if (curr.data < data) {
                curr = curr.rightChild;
                levelCountBBST++;
            }
            else if (curr.data > data) {
                curr = curr.leftChild;
                levelCountBBST++;
            }
            else {
                // If the node is a leaf
                if (curr.leftChild == null && curr.rightChild == null) {
                    if (curr.parent.rightChild == curr) {
                        curr = curr.parent;
                        curr.rightChild.parent = null;
                        curr.rightChild = null;
                        delFlag = false;
                    }
                    else if (curr.parent.leftChild == curr) {
                        curr = curr.parent;
                        curr.leftChild.parent = null;
                        curr.leftChild = null;
                        delFlag = false;
                    }
                }
                // If the node only has a right child
                else if (curr.leftChild == null && curr.rightChild != null) {
                    if (curr.parent.leftChild == curr) {
                        curr = curr.parent;
                        curr.leftChild.parent = null;
                        curr.leftChild = curr.leftChild.rightChild;
                        curr.leftChild.rightChild.parent = curr;
                        curr.leftChild.rightChild = null;
                        // Set curr to the node replacing the deleted node
                        curr = curr.leftChild;
                        levelCountBBST++;
                        // Break out of the loop
                        delFlag = false;
                    }
                    else if (curr.parent.rightChild == curr) {
                        curr = curr.parent;
                        curr.rightChild.parent = null;
                        curr.rightChild = curr.rightChild.rightChild;
                        curr.rightChild.rightChild.parent = curr;
                        curr.rightChild.rightChild = null;
                        // Set curr to the node replacing deleted node
                        curr = curr.rightChild;
                        levelCountBBST++;
                        // Break out of loop
                        delFlag = false;
                    }
                }
                // If the node only has a left child
                else if (curr.rightChild == null && curr.leftChild != null) {
                    if (curr.parent.leftChild == curr) {
                        curr = curr.parent;
                        curr.leftChild.parent = null;
                        curr.leftChild = curr.leftChild.leftChild;
                        curr.leftChild.leftChild.parent = curr;
                        curr.leftChild.leftChild = null;
                        // Set curr to node replacing deleted node
                        curr = curr.leftChild;
                        levelCountBBST++;
                        // Break out of loop
                        delFlag = false;
                    }
                    else if (curr.parent.rightChild == curr) {
                        curr = curr.parent;
                        curr.leftChild.parent = null;
                        curr.leftChild = curr.leftChild.rightChild;
                        curr.leftChild.rightChild.parent = curr;
                        curr.leftChild.rightChild = null;
                        // Set curr to node replacing deleted node
                        curr = curr.rightChild;
                        levelCountBBST++;
                        // Break out of loop
                        delFlag = false;
                    }
                }
                // If the node has both children
                else {
                    // Find the in order successor of the node
                    Node ios = findMinIter(curr.rightChild);
                    // Replace current node with in order successor
                    ios.parent.leftChild = null;
                    ios.parent = curr.parent;
                    if (curr.parent.leftChild == curr) {
                        curr.parent.leftChild = ios;
                    }
                    else {
                        curr.parent.rightChild = ios;
                    }
                    ios.leftChild = curr.leftChild;
                    ios.rightChild = curr.rightChild;
                    ios.height = curr.height;
                    curr.parent = null;
                    curr.rightChild = null;
                    curr.leftChild = null;
                    curr = ios;
                    delFlag = false;
                }
            }
        }

        // Iterate back up through the tree to rebalance
        while (curr != null) {
            levelCountBBST++;
            // Increase the height of current node
            curr.height--;
            // If the tree is balanced, iterate up to curr's parent
            if (Math.abs(balanceFactor(curr.parent)) == 1 || balanceFactor(curr.parent) == 0) {
                curr = curr.parent;
                continue;
            }
            // If the subtree is left balanced
            else if (balanceFactor(curr.parent) > 1) {
                // If its a left-left case
                if (balanceFactor(curr) > 0) {
                    // Right rotate curr parent
                    rightRotate(curr.parent);
                }
                // If its a left-right case
                else if (balanceFactor(curr) < 0) {
                    // Left rotate on curr, right rotate on curr parent
                    leftRotate(curr);
                    rightRotate(curr.parent);
                }
            }
            // If the subtree is right balanced
            else if (balanceFactor(curr.parent) < 0) {
                // Right-right case
                if (balanceFactor(curr) < 0) {
                    // Left rotate curr parent
                    rightRotate(curr.parent);
                }
                // Right-left case
                else if (balanceFactor(curr) > 1) {
                    rightRotate(curr);
                    leftRotate(curr.parent);
                }
            }
        }
    }

    static Node findNextIter(Node root, int data) {
        // Iterate down the tree until you find the node you're looking for
        Node curr = root;
        while (curr.data != data) {
            if (curr.data < data) {
                curr = curr.rightChild;
                continue;
            }
            else if (curr.data > data) {
                curr = curr.leftChild;
                continue;
            }
        }
        // If the node has a right child the next node is min of right subtree
        if (curr.rightChild != null) {
            return findMinIter(curr.rightChild);
        }
        // If the node doesn't have a right child, next is it's parent
        else {
            // Iterate through the parents of node
            // If node data less than parent data return parent
            // If no parents greater return null
            while (curr.parent != null) {
                if (curr.parent.data > data) {
                    return curr.parent;
                }
                else {
                    return null;
                }
            }
        }
        return curr;
    }

    static Node findPrevIter(Node root, int data)  {
        // Iterate down the tree until you find the node you're looking for
        Node curr = root;
        while (curr.data != data) {
            if (curr.data < data) {
                curr = curr.rightChild;
                continue;
            }
            else if (curr.data > data) {
                curr = curr.leftChild;
                continue;
            }
        }
        // If the node has a left child, previous is max of left subtree
        if (curr.leftChild != null) {
            return findMaxIter(curr.leftChild);
        }
        // Otherwise check all the parents
        else {
            while (curr.parent != null) {
                if (curr.parent.data < data) {
                    return curr.parent;
                }
                else {
                    return null;
                }
            }
        }
        return curr;
    }

    static Node findMinIter(Node node) {
        Node curr = node;
        // While there are left children, use temp node to discover lowest height left child
        while (curr.leftChild != null) {
            curr = curr.leftChild;
        }
        // Once there are no more left children return the value of current left child
        return curr;
    }

    static Node findMaxIter(Node node) {
        Node curr = node;
        // While there are right children use temp node to find lowest height right child
        while (curr.rightChild != null) {
            curr = curr.rightChild;
        }
        // Return right child's data if no more right children
        return curr;
    }

    // TODO: for both rotations adjust height values
    // Rotate the subtree to the right
    // The inserted node is the top node of the rotation
    static void rightRotate(Node node) {
        Node parent = node;
        Node mid = node.leftChild;
        mid.parent = parent.parent;
        if (mid.parent != null) {
            if (mid.parent.leftChild == parent) {
                mid.parent.leftChild = mid;
            }
            else if(mid.parent.rightChild == parent){
                mid.parent.rightChild = mid;
            }
        }
        parent.leftChild = mid.rightChild;
        mid.rightChild = parent;
        parent.parent = mid;
        node.height--;
    }

    // Rotate the subtree to the left
    static void leftRotate(Node node) {
        Node parent = node;
        Node mid = node.rightChild;
        mid.parent = parent.parent;
        if (mid.parent != null) {
            if (mid.parent.leftChild == parent) {
                mid.parent.leftChild = mid;
            }
            else if (mid.parent.rightChild == parent){
                mid.parent.rightChild = mid;
            }
        }
        parent.rightChild = mid.leftChild;
        mid.leftChild = parent;
        parent.parent = mid;
        node.height--;
    }

    // Find the balance factor of a node
    static private int balanceFactor(Node node) {
        if (node.leftChild == null && node.rightChild == null) {
            return 0;
        }
        else if (node.rightChild ==  null && node.leftChild != null) {
            return node.leftChild.height;
        }
        else if (node.leftChild == null && node.rightChild != null) {
            return -node.rightChild.height;
        }
        else {
            return node.leftChild.height - node.rightChild.height;
        }
    }
}

class Node {
    int data;
    int height;
    Node leftChild;
    Node rightChild;
    Node parent;

    public Node(int data) {
        this.data = data;
        height = 1;
        leftChild = null;
        rightChild = null;
        parent = null;
    }
}