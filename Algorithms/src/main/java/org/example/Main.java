package org.example;
import java.util.Arrays;
import java.util.Random;


public class Main {
    public static void main(String[] args) {

        int arraySize = 1000000;
        int[] array = new int[arraySize];
        Sortable bubbleSort = new BubbleSort();
        Sortable insertSort = new InsertSort();
        Sortable selectSort = new SelectSort();

        generateIntArray(array, arraySize);

        // Base case with Arrays.sort method
        int[] arrayForSort = array.clone(); // clone massive for the same conditions
        long startTime = System.currentTimeMillis();
        Arrays.sort(arrayForSort);
        long endTime = System.currentTimeMillis();
        System.out.println("Arrays.sort time = " + (endTime-startTime));

        // Bubble Sort
        calculateAlgorithmTime(bubbleSort, array);
        // Insertion Sort
        calculateAlgorithmTime(insertSort, array);
        // Selection Sort
        calculateAlgorithmTime(selectSort, array);
    }

    public static void generateIntArray(int[] arr, int arrSize) {
        Random random = new Random();
        for (int i = 0; i < arrSize; i++) {
            arr[i] = random.nextInt(arrSize * 100);
        }
    }

    public static void calculateAlgorithmTime(Sortable sortable, int[] arr) {
        int[] arrayForSort = arr.clone();
        long startTime = System.currentTimeMillis();
        sortable.sort(arrayForSort);
        long endTime = System.currentTimeMillis();
        System.out.println(sortable.toString() + (endTime - startTime));
    }
}