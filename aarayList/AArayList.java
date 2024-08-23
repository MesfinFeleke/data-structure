package aarayList;

import java.util.ArrayList;
import java.util.Iterator;

public class AArayList {

   /*
An ArrayList in Java is a part of the Java Collections Framework and is one of the most commonly used classes for handling dynamic arrays.
Unlike arrays, which have a fixed size, ArrayList can grow and shrink in size dynamically, making it more flexible for use cases where the size of the collection can change over time.

Key Characteristics of ArrayList
Dynamic Size: ArrayList can dynamically adjust its capacity as elements are added or removed.
Index-Based: Just like arrays, elements in an ArrayList are accessed using their index, starting from 0.
Ordered Collection: Elements are maintained in the order they are inserted.
Allow Duplicates: ArrayList can contain duplicate elements.
Non-Synchronized: By default, ArrayList is not thread-safe, meaning it is not synchronized. If synchronization is needed, you should manually synchronize it or use CopyOnWriteArrayList.
   */

    public static void main(String[] args) {

        ArrayList<String> names; // Declares an ArrayList of Strings
        ArrayList<Integer> numbers; // Declares an ArrayList of Integers

        names = new ArrayList<>(); // Initializes an ArrayList of Strings
        numbers = new ArrayList<>(); // Initializes an ArrayList of Integers

        ArrayList<String> names1 = new ArrayList<>();
        ArrayList<Integer> numbers1 = new ArrayList<>();

        names.add("Alice");
        names.add("Bob");
        numbers.add(10);
        numbers.add(20);

        names.add(1, "Charlie"); // Adds "Charlie" at index 1

        String name = names.get(0); // Accesses the first element ("Alice")
        int number = numbers.get(1); // Accesses the second element (20)

        //Modifying ArrayList Elements

        names.set(1, "David"); // Changes the second element to "David"
        numbers.set(0, 30); // Changes the first element to 30

        //Removing Elements from an ArrayList

        names.remove("Alice"); // Removes "Alice" from the list
        numbers.remove(1); // Removes the element at index 1 (second element)

        names.clear(); // Removes all elements from the list

       //Iterating Through an ArrayList
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
        }

        for (String name1 : names) {
            System.out.println(name);
        }

        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        //ArrayList Methods

        int size = names.size();
        boolean containsAlice = names.contains("Alice");
        boolean isEmpty = names.isEmpty();
        int index = names.indexOf("Bob");
        String[] namesArray = names.toArray(new String[0]);


        // Example Code

        // Initialize an ArrayList of Strings
        ArrayList<String> names2 = new ArrayList<>();

        // Add elements to the ArrayList
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");

        // Access and print an element
        System.out.println("First name: " + names.get(0));

        // Modify an element
        names.set(1, "David");

        // Remove an element
        names.remove("Alice");

        // Iterate over the ArrayList using a for-each loop
        for (String name3 : names) {
            System.out.println(name);
        }

        // Check the size of the ArrayList
        System.out.println("Number of names: " + names.size());

        // Check if the ArrayList contains an element
        if (names.contains("Charlie")) {
            System.out.println("Charlie is in the list.");
        }

    }
}
