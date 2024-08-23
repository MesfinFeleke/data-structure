package stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class ReductionCollection {

    static class Book2 implements Comparable {
        private long isbn;
        private String title;
        private double rating;
        private double price;
        private String source;

        public Book2(long isbn, String title, double rating, double price, String source) {
            this.isbn = isbn;
            this.title = title;
            this.rating = rating;
            this.price = price;
            this.source = source;
        }

        public long getIsbn() {
            return isbn;
        }

        public String getTitle() {
            return title;
        }

        public double getRating() {
            return rating;
        }

        public double getPrice() {
            return price;
        }

        public String getSource() {
            return source;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (int) (isbn ^ (isbn >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Book2 other = (Book2) obj;
            if (isbn != other.isbn)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Book [isbn=" + isbn + ", title=" + title + ", rating=" + rating + ", price=" + price + ", source="
                    + source + "]";
        }

        @Override
        public int compareTo(Object o) {

            return Long.valueOf(isbn).compareTo(((Book2)o).getIsbn());
        }

    }



    // if accumulator mutates, use collect(). Otherwise, use reduce()
    private static void mutableReduction() {
        System.out.println("\nmutableReduction ... ");
        String[] grades = {"A", "A", "B"};

        StringBuilder concat2 = Arrays.stream(grades).parallel()
                .collect(() -> new StringBuilder(),
                        (sb, s) -> sb.append(s),
                        (sb1, sb2) -> sb1.append(sb2));
        System.out.println("concat2: " + concat2);

        String concatWithJoining = Arrays.stream(grades).parallel()
                .collect(Collectors.joining());
        System.out.println("concatWithJoining: " + concatWithJoining);
    }

    static void overloadedReductions() {
        System.out.println("\noverloadedReductions ... ");

        String[] grades = {"A", "A", "B"};

        String concat1 = Arrays.stream(grades)
                .reduce("", (s1, s2) -> s1 + s2);
        System.out.println("concat1: " + concat1);

        StringBuilder concat2 = Arrays.stream(grades)
                .reduce(new StringBuilder(),
                        (sb, s) -> sb.append(s),
                        (sb1, sb2) -> sb1.append(sb2));
        //null);
        System.out.println("concat2: " + concat2);


        // Not efficient: Each accumulation step creates a new StringBuilder
        StringBuilder concat3 = Arrays.stream(grades).parallel()
                .reduce(new StringBuilder(),
                        (sb, s) -> new StringBuilder().append(sb).append(s),
                        (sb1, sb2) -> sb1.append(sb2));
        System.out.println("concat3: " + concat3);

    }

    // Limitations:
    //   1. Cumbersome
    //   2. Parallelizing is painful
    //   3. Synchronizing shared mutable variable is expensive
    private static void reduceImperatively(List<Book2> books) {
        System.out.println("\nReducing imperatively ...");
        Book2 result = null;

        for (Book2 book : books) {
            // Initialize result with first book having rating >= 4.5
            if (result == null) {
                if (book.getRating() >= 4.5) {
                    result = book;
                }
                continue;
            }

            if (book.getRating() >= 4.5 && result.getPrice() > book.getPrice()) {
                result = book;
            }
        }

        System.out.println("(Imperative) Lowest priced book with rating >= 4.5: " + result);

    }

    // Find the lowest priced book with a rating >= 4.5
    private static void reduce(List<Book2> books) {
        System.out.println("\nReduce ...");
        books.stream()
                .filter(b -> b.getRating() >= 4.5)
                .reduce((b1, b2) -> b1.getPrice() <= b2.getPrice() ? b1 : b2)
                .ifPresent(b -> System.out.println("Lowest priced book: " + b));
    }


    // findFirst needs more work in parallel env. Use findAny if it does the job.
    // java.util.Optional ~
    //     (a) to avoid dealing with null -- in case of find,
    //     (b) to know if stream is empty -- in case of reduction operation
    private static void find(List<Book2> books) {
        System.out.println("\nFinding ...");
        /*Optional<Book> result = */ books.stream()
                .filter(d -> d.getRating() >= 4.8 && d.getPrice() <= 50.0)
                .findAny().orElseGet(ReductionCollection::getDefault);
        Optional<Book2> opt = Optional.ofNullable(books.get(0));
        System.out.println(opt.isPresent());

		/*if (result.isPresent()) {
			System.out.println(result.get());
		} else {
			System.out.println("not found!!!");
		}*/

    }

    private static void print(Book2 b) {
        System.out.println(b);
    }

    private static Book2 getDefault() {
        System.out.println("default ...");
        return new Book2(0, "", 4.0, 25.0, "Amazon");
    }


    // Queries:
    //  (a) Is there at least one highly rated book (>= 4.8) that is inexpensive (<= $50)?
    //  (b) Do all the books have a rating >= 4.8
    //  (c) Check if none of books have bad rating (2.0)?
    private static void match(List<Book2> books) {
        System.out.println("\nMatching ... ");
        boolean anyMatch = books.stream()
                .anyMatch(d -> d.getRating() >= 4.8 && d.getPrice() <= 50.0);
        System.out.println("anyMatch? " + anyMatch);

        boolean allMatch = books.stream()
                .allMatch(d -> d.getRating() >= 4.8);
        //.noneMatch(d -> d.getRating() < 4.8);
        System.out.println("allMatch? " + allMatch);

        boolean noneMatch = books.stream()
                .noneMatch(d -> d.getRating() <= 2.0);
        // .allMatch(d -> d.getRating() > 2.0);
        //.anyMatch(d -> d.getRating() <= 2.0);
        System.out.println("noneMatch? " + noneMatch);

    }

    // Print at most 5 DISTINCT books with rating >= 4.5
    // DB world: select distinct (ISBN) from book where rating >= 4.5 limit 0, 5;
    private static void slice(List<Book2> books) {
        System.out.println("\nSlice ... ");
		/*List<String> result = books.stream()
			.filter(d -> d.getRating() >= 4.5)
			.distinct()
			//.peek(d -> System.out.println(d.getTitle() + " " + d.getRating()))
			.limit(5)
			//.skip(5)
			.map(d -> d.getTitle())
			//.forEach(System.out::println);
			.collect(Collectors.toList());

		for(String title : result)
			System.out.println("title: " + title);*/

        Stream<Book2> booksStream = books.stream()
                .filter(d -> d.getRating() >= 4.5)
                .distinct()
                //.peek(d -> System.out.println(d.getTitle() + " " + d.getRating()))
                .limit(5);

        Stream<String> titleStream = booksStream.map(d -> d.getTitle());
        titleStream.forEach(System.out::println);
    }

    public static void main(String[] args) {

        List<Book2> books2 = new ArrayList<>();

        books2.addAll(DataExtractor2.getFromAmazon("java"));
        books2.addAll(DataExtractor2.getFromBarnesAndNoble("java"));

        // intermediate operations (return stream objects)
        //slice(books);

        // terminal operations (return non-stream objects)
        //match(books);  // matching stream elements to some criteria

        // All matching & finding operations + limit
        //    are short-circuit operations (recall &&, ||)

        // find(books);

        //reduce(books);
        //reduceImperatively(books);
        overloadedReductions();

        mutableReduction();

    }

}
