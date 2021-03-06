import java.io.*;
import java.util.*;


public class BST {

    static int levelCountBST = 0;

    static void insertRec(Node root, int data) {
        // If the data is greater than root data
        if (root.data < data) {
            if (root.rightChild == null) {
                // If no right child create new right child
                root.rightChild = new Node(data);
            }
            else {
                // Otherwise look at the next right subtree
                insertRec(root.rightChild, data);
            }
        }
        // If data less than root data
        else {
            if (root.leftChild == null) {
                // If no left child create one
                root.leftChild = new Node(data);
            }
            else {
                // Otherwise look at next left subtree
                insertRec(root.leftChild, data);
            }
        }
    }

    static void insertIter(Node root, int data) {
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
                    levelCountBST++;
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
                    levelCountBST++;
                }
            }
        }
    }

    static Node deleteRec(int num, Node node) {
        // If the tree is empty return the empty node
        if (node == null) {
            return node;
        }
        // If val is less than node data traverse down left subtree
        if (num < node.data) {
            node.leftChild = deleteRec(num, node.leftChild);
        }
        // If val is greater than node data traverse down right subtree
        else if (num > node.data) {
            node.rightChild = deleteRec(num, node.rightChild);
        }
        else {
            // If node doesn't have a left child
            if (node.leftChild == null) {
                return node.rightChild;
            }
            // If node doesn't have a right child
            else if (node.rightChild == null) {
                return node.leftChild;
            }
            // Find in order successor and put it's data in curr node data
            node.data = (node.rightChild).data;

            // Recursively delete in order successor in old position
            node.rightChild = deleteRec(node.data, node.rightChild);
        }
        return node;
    }

    static void deleteIter(Node root, int data) {
        Node curr = root;
        boolean delFlag = true;

        while (delFlag) {
            if (curr.data < data) {
                curr = curr.rightChild;
                levelCountBST++;
            }
            else if (curr.data > data) {
                curr = curr.leftChild;
                levelCountBST++;
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
                        levelCountBST++;
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
                        levelCountBST++;
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
                        levelCountBST++;
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
                        levelCountBST++;
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
    }

    static Node findNextRec(Node node, int data) {
        if (node == null) {
            return node;
        }

        // ArrayList for node vals
        ArrayList<Node> nodeVals = new ArrayList<Node>();
        Node next = findNextRec(node.leftChild, data);
        nodeVals.add(next);
        next = findNextRec(node.rightChild, data);
        nodeVals.add(next);

        // Iterate through arraylist, find data and return next element
        for (int i = 0; i < nodeVals.size(); i++) {
            if (nodeVals.get(i).data == data) {
                return nodeVals.get(i + 1);
            }
        }
        return node;
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

    static Node findPrevRec(Node node, int data) {
        if (node == null) {
            return node;
        }

        // ArrayList for node vals
        ArrayList<Node> nodeVals = new ArrayList<Node>();
        Node next = findPrevRec(node.leftChild, data);
        nodeVals.add(next);
        next = findPrevRec(node.rightChild, data);
        nodeVals.add(next);

        // Iterate through arraylist, find data and return next element
        for (int i = 0; i < nodeVals.size(); i++) {
            if (nodeVals.get(i).data == data) {
                return nodeVals.get(i - 1);
            }
        }
        return node;
    }

    static Node findPrevIter(int num, Node node) {
        // Iterate down the tree until you find the node you're looking for
        Node curr = node;
        while (curr.data != num) {
            if (curr.data < num) {
                curr = curr.rightChild;
                continue;
            }
            else if (curr.data > num) {
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
                if (curr.parent.data < num) {
                    return curr.parent;
                }
                else {
                    return null;
                }
            }
        }
        return curr;
    }

    static Node findMinRec(Node node) {
        // If there is a left child, recursively find the lowest height left child
        if (node.leftChild != null) {
            findMinRec(node.leftChild);
        }
        // If no more left children return the value of the current left child
        return node;
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

    static Node findMaxRec(Node node) {
        // If there is a right child recursively find the lowest height right child
        if (node.rightChild != null) {
            findMaxRec(node.rightChild);
        }
        // If no more right children return the val of current right child
        return node;
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

    static private void swap(Node node1, Node node2) {
        // Store the vals of node 2 in a temp node
        Node temp = node2;
        temp.leftChild = node2.leftChild;
        temp.rightChild = node2.rightChild;

        // Swap the vals of node 1 into node 2
        node2.data = node1.data;
        node2.leftChild = node1.leftChild;
        node2.rightChild = node1.rightChild;

        // Swap the vals of temp into node 1
        node1.data = temp.data;
        node1.leftChild = temp.leftChild;
        node1.rightChild = temp.rightChild;
    }
}