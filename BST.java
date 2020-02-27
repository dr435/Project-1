import java.io.*;
import java.util.*;


class BST {

    public static void main(String[] args) {
        // Sorry I don't have everything finished, will be working on it :)

    }

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
        insertFlag = true;
        // While node isn't inserted
        while (insertFlag) {
            // If data is greater than current node data
            if (curr.data < data) {
                // There is no right child
                if (curr.rightChild == null) {
                    // Create right child with data, node has been inserted
                    curr.rightChild = new Node(data);
                    insertFlag = false;
                }
                else {
                    // Go to top of loop with curr at root of right subtree
                    curr = curr.rightChild;
                    continue;
                }
            }
            // If data less than current node data
            else {
                // If no left child
                if (curr.leftChild == null) {
                    // Create one with data, node has been inserted
                    curr.leftChild = new Node(data);
                    insertFlag = false;
                }
                else {
                    // Go to top of loop with curr as root of left subtree
                    curr = curr.leftChild;
                    continue;
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
            node.data = findMinIter(node.rightChild).data;

            // Recursively delete in order successor in old position
            node.rightChild = deleteRec(node.data, node.rightChild);
        }
    }

    static void deleteIter(int num, Node node) {
        // Make dummy variables for the root node and it's parent & grandparent
        Node curr = node;
        Node parent = null;
        Node temp = null;
        while (curr != null) {
            // If num less than node data traverse down left subtree
            if (num < curr.data) {
                parent = curr;
                curr = parent.leftChild;
                continue;
            }
            // If num greater than node data traverse down right subtree
            else if (num > curr.data) {
                parent = curr;
                curr = parent.rightChild;
                continue;
            }
            // Num equals node data
            else {
                if (parent.leftChild == curr) {
                    // If curr is a leaf delete it
                    if (curr.leftChild == null && curr.rightChild == null) {
                        parent.leftChild == null;
                    }
                    // If curr has a left child, make it the left child of parent, then delete
                    else if (curr.rightChild == null) {
                        parent.leftChild = curr.leftChild;
                        curr.leftChild = null
                    }
                    // If curr has a right child, make it the left child of parent, then delete
                    else if (curr.leftChild == null) {
                        parent.leftChild = curr.rightChild;
                        curr.rightChild = null;
                    }
                    // Curr has a left and right child
                    else {
                        if (curr.rightChild) {
                            temp = findMinRec(curr.rightChild);
                            swap(curr, temp);
                        }
                    }
                }
                else if (parent.rightChild == curr) {
                    if (curr.leftChild == null && curr.rightChild == null) {
                        parent.rightChild == null;
                    }
                    // If curr has a left child, make it the left child of parent, then delete
                    else if (curr.rightChild == null) {
                        parent.rightChild = curr.leftChild;
                        curr.leftChild = null
                    }
                    // If curr has a right child, make it the left child of parent, then delete
                    else if (curr.leftChild == null) {
                        parent.rightChild = curr.rightChild;
                        curr.rightChild = null;
                    }
                    // Curr has a left and right child
                    else {
                        temp = findMinRec(curr.rightChild);
                        swap(temp, curr);

                    }
                }
            }
        }
    }

    static int findNextRec(Node node, int data) {
        if (node == null) {
            return node;
        }

        // ArrayList for node vals
        ArrayList<Integer> nodeVals = new ArrayList<Integer>();
        next = findNextRec(node.leftChild, data);
        nodeVals.add(next);
        next = findNextRec(node.rightChild, data);
        nodeVals.add(next);

        // Iterate through arraylist, find data and return next element
        for (int i = 0; i < nodeVals.size(); i++) {
            if (nodeVals.get(i) == data) {
                return nodeVals.get(i + 1);
            }
        }
    }

    static int findNextIter() {

    }

    static int findPrevRec(Node node, int data) {
        if (node == null) {
            return node;
        }

        // ArrayList for node vals
        ArrayList<Integer> nodeVals = new ArrayList<Integer>();
        next = findNextRec(node.leftChild, data);
        nodeVals.add(next);
        next = findNextRec(node.rightChild, data);
        nodeVals.add(next);

        // Iterate through arraylist, find data and return next element
        for (int i = 0; i < nodeVals.size(); i++) {
            if (nodeVals.get(i) == data) {
                return nodeVals.get(i - 1);
            }
        }
    }

    static int findPrevIter(int num, Node node) {
        // Create a priority queue to store node data and temp node
        PriorityQueue<Integer> pQ = new PriorityQueue<Integer>();
        Node curr = node;


    }

    static Node findMinRec(Node node) {
        // If there is a left child, recursively find the lowest height left child
        if (node.leftChild != null) {
            findMinRec(node.leftChild);
        }
        // If no more left children return the value of the current left child
        else {
            return node;
        }
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
        else {
            return node;
        }
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

    static ArrayList<Integer> sort(Node node) {
        if (node == null) {
            return node;
        }

        // ArrayList for node vals
        ArrayList<Integer> nodeVals = new ArrayList<Integer>();
        next = findNextRec(node.leftChild, data);
        nodeVals.add(next);
        next = findNextRec(node.rightChild, data);
        nodeVals.add(next);

        return nodeVals;
    }

    static private void swap(Node node1, Node node2) {
        // Store the vals of node 2 in a temp node
        Node temp = new Node(node2.data);
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

class Node {
    int data;
    Node leftChild;
    Node rightChild;
    Node parent;

    public Node(int data) {
        this.data = data;
        leftChild = null;
        rightChild = null;
    }
}