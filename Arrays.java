import java.io.*;
import java.util.*;

class Arrays {

    public static void main(String[] args) {

    }

    static int[] getRandomArray(int n) {
        // Create array of size n, Random object, and hashmap for used numbers
        Random rand = new Random();
        int[] arr = new int[n];
        HashMap<Integer, Integer> used = new HashMap<Integer, Integer>;

        // Iterate through the array getting random ints
        // If they're in the set, decrement and try again, else add to array and hashmap
        for (int i = 0; i < n; i++) {
            int temp = rand.nextInt(100);
            if (used.get(temp)) {
                i--;
                continue;
            }
            arr[i] = temp;
            used.put(temp, 1);
        }

        return arr;
    }

    static int[] getSortedArray(int n) {
        // Create array of size n
        int[] arr = new int[n];

        //Iterate through array setting values, n - i gives the proper value for the array
        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
        }

        return arr;
    }
}