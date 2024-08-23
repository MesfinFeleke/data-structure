package aarays;

import java.util.Arrays;

public class AArays {

  /*  In Java, an array is a data structure that allows you to store a fixed-size sequence of elements of the same type.
    Arrays are particularly useful when you need to store multiple values in a single variable, rather
    than declaring separate variables for each value.*/


    public static void main(String[] args) {

        int[] numbers;  // Declares an array of integers
        String[] names; // Declares an array of strings

        numbers = new int[5];  // Creates an array of 5 integers
        names = new String[3]; // Creates an array of 3 strings

        int[] numbers2 = new int[5];        // Array of 5 integers, all initialized to 0
        String[] names2 = new String[3];    // Array of 3 strings, all initialized to null

        int[] numbers3 = {1, 2, 3, 4, 5};          // Array of 5 integers
        String[] names3 = {"Alice", "Bob", "Eve"}; // Array of 3 strings

        int firstNumber = numbers3[0]; // Accesses the first element (1)
        String firstName = names3[0];  // Accesses the first element ("Alice")

        numbers3[2] = 10;   // Changes the third element to 10
        names3[1] = "John"; // Changes the second element to "John"


        for (int i = 0; i < numbers3.length; i++) {
            System.out.println(numbers3[i]);
        }

        for (int number : numbers3) {
            System.out.println(number);
        }

       //Multidimensional Arrays

        int[][] matrix = new int[3][3]; // 3x3 matrix (2D array)
        int[][] matrixE = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int value = matrixE[1][2]; // Accesses the element at row 1, column 2 (value is 6)

       //Common Array Operations


        int length = numbers.length;

        int[] copy = Arrays.copyOf(numbers, numbers.length);

        Arrays.sort(numbers);

        //Searching an array: Use Arrays.binarySearch() for sorted arrays.
        int index = Arrays.binarySearch(numbers, 3);


         //Example Code

        // Declare and initialize an array
        int[] numbersE = {10, 20, 30, 40, 50};

        // Access and print an array element
        System.out.println("First element: " + numbersE[0]);

        // Modify an array element
        numbersE[2] = 100;

        // Print the entire array using a for-each loop
        for (int number : numbersE) {
            System.out.println(number);
        }

        // Find the length of the array
        System.out.println("Array length: " + numbersE.length);
    }
}
