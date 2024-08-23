package stream;

import java.util.Arrays;
import java.util.List;
import stream.ReductionCollection.Book2;
import java.util.Arrays;
import java.util.List;


public class DataExtractor2 {

    static List<Book2> getFromAmazon(String keyword) {

        Book2  b1 = new Book2(9780596009201L, "Java 1", 4.0, 25.0, "Amazon");
       Book2 b2 = new Book2(9780596009202L, "Java 2", 4.1, 25.0, "Amazon");
       Book2 b3 = new Book2(9780596009203L, "Java 3", 4.5, 25.0, "Amazon");
       Book2 b4 = new Book2(9780596009204L, "Java 4", 4.5, 25.0, "Amazon");
       Book2 b5 = new Book2(9780596009205L, "Java 5", 3.9, 30.0, "Amazon");
       Book2 b6 = new Book2(9780596009206L, "Java 6", 4.8, 40.0, "Amazon");
       Book2 b7 = new Book2(9780596009207L, "Java 7", 4.9, 40.0, "Amazon");
       Book2 b8 = new Book2(9780596009208L, "Java 8", 4.7, 50.0, "Amazon");
       Book2 b9 = new Book2(9780596009209L, "Java 9", 4.3, 50.0, "Amazon");
       Book2 b10 = new Book2(9780596009210L, "Java 10", 4.5, 60.0, "Amazon");

        return Arrays.asList(b1, b2, b3, b4, b5, b6, b7, b7, b8, b9, b10);
    }

    static List<Book2> getFromBarnesAndNoble(String keyword) {
       Book2 b1 = new Book2(9780596009201L, "Java 1", 3.9, 20.0, "B&N");
       Book2 b2 = new Book2(9780596009202L, "Java 2", 4.1, 20.0, "B&N");
       Book2 b3 = new Book2(9780596009203L, "Java 3", 4.6, 20.0, "B&N");
       Book2 b4 = new Book2(9780596009204L, "Java 4", 4.5, 20.0, "B&N");
       Book2 b5 = new Book2(9780596009211L, "Java 11", 4.9, 40.0, "B&N");
       Book2 b6 = new Book2(9780596009212L, "Java 12", 4.9, 55.0, "B&N");
       Book2 b7 = new Book2(9780596009213L, "Java 13", 4.9, 60.0, "B&N");

        return Arrays.asList(b1, b2, b3, b4, b5, b6, b7);
    }

}
