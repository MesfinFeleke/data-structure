package hashmap;

import java.util.HashMap;

public class HHashMAP {
    /*

 Key Characteristics of HashMap
Key-Value Pair Storage: HashMap stores elements in the form of key-value pairs, where each key is unique.
 If you try to insert a duplicate key, the new value will replace the old value.

Null Keys and Values: HashMap allows one null key and multiple null values.

Non-Ordered Collection: The order of keys in a HashMap is not guaranteed.
The insertion order is not maintained.

Not Thread-Safe: By default, HashMap is not synchronized.
If multiple threads access a HashMap concurrently, and at least one of the threads modifies the map structurally,
it must be synchronized externally.

Constant-Time Performance: HashMap provides constant-time performance (O(1)) for basic operations like get()
and put() assuming the hash function disperses the elements properly among the buckets.
     */

    public static void main(String[] args) {

        HashMap<String, Integer> ageMap = new HashMap<>();
        HashMap<Integer, String> idMap = new HashMap<>();

        ageMap.put("Alice", 30);
        ageMap.put("Bob", 25);
        ageMap.put("Charlie", 35);

        ageMap.put("Alice", 32); // Updates Alice's age to 32
        int age = ageMap.get("Bob"); // Retrieves the age of Bob, which is 25
        Integer age2 = ageMap.get("David"); // Returns null because "David" is not in the map
        ageMap.remove("Charlie"); // Removes the entry for "Charlie"

        boolean hasAlice = ageMap.containsKey("Alice"); // Checks if "Alice" exists in the map
        boolean hasAge30 = ageMap.containsValue(30); // Checks if any entry has the value 30


        //Iterating Over HashMap
        for (String key : ageMap.keySet()) {
            System.out.println("Key: " + key);
        }

        for (Integer value : ageMap.values()) {
            System.out.println("Value: " + value);
        }

        for (HashMap.Entry<String, Integer> entry : ageMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }


        //Example Code

        // Create a HashMap to store names and ages
        HashMap<String, Integer> ageMap2 = new HashMap<>();

        // Add elements to the HashMap
        ageMap2.put("Alice", 30);
        ageMap2.put("Bob", 25);
        ageMap2.put("Charlie", 35);

        // Access and print an element
        System.out.println("Alice's age: " + ageMap2.get("Alice"));

        // Modify an element
        ageMap2.put("Bob", 26); // Updates Bob's age to 26

        // Remove an element
        ageMap2.remove("Charlie");

        // Check if a key or value exists
        boolean hasAlice2 = ageMap.containsKey("Alice");
        boolean hasAge302 = ageMap.containsValue(30);

        System.out.println("Has Alice? " + hasAlice);
        System.out.println("Has age 30? " + hasAge30);

        // Iterate over the HashMap
        for (HashMap.Entry<String, Integer> entry : ageMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }



    }
}
