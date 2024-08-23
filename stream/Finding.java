package stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Finding {

    // findFirst needs more work in parallel env. Use findAny if it does the job.
    // java.util.Optional ~
    //     (a) to avoid dealing with null -- in case of find,
    //     (b) to know if stream is empty -- in case of reduction operation
    private static void find(List<StreamSlicing.Book> books) {
        System.out.println("\nFinding ...");
        /*Optional<Book> result = */ books.stream()
                .filter(d -> d.getRating() >= 4.8 && d.getPrice() <= 50.0)
                .findAny().orElseGet(Finding::getDefault);

        Optional<StreamSlicing.Book> opt = Optional.ofNullable(books.get(0));
        System.out.println(opt.isPresent());

		/*if (result.isPresent()) {
			System.out.println(result.get());
		} else {
			System.out.println("not found!!!");
		}*/

    }

    private static void print(StreamSlicing.Book b) {
        System.out.println(b);
    }

    private static StreamSlicing.Book getDefault() {
        System.out.println("default ...");
        return new StreamSlicing.Book(0, "", 4.0, 25.0, "Amazon");
    }


    // Queries:
    //  (a) Is there at least one highly rated book (>= 4.8) that is inexpensive (<= $50)?
    //  (b) Do all the books have a rating >= 4.8
    //  (c) Check if none of books have bad rating (2.0)?
    private static void match(List<StreamSlicing.Book> books) {
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
    private static void slice(List<StreamSlicing.Book> books) {
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

        Stream<StreamSlicing.Book> booksStream = books.stream()
                .filter(d -> d.getRating() >= 4.5)
                .distinct()
                //.peek(d -> System.out.println(d.getTitle() + " " + d.getRating()))
                .limit(5);

        Stream<String> titleStream = booksStream.map(d -> d.getTitle());
        titleStream.forEach(System.out::println);
    }

    public static void main(String[] args) {

        List<StreamSlicing.Book> books = new ArrayList<>();

        books.addAll(DataExtractor.getFromAmazon("java"));
        books.addAll(DataExtractor.getFromBarnesAndNoble("java"));

        // intermediate operations (return stream objects)
        //slice(books);

        // terminal operations (return non-stream objects)
        //match(books);  // matching stream elements to some criteria

        // All matching & finding operations + limit
        //    are short-circuit operations (recall &&, ||)

        find(books);
    }

}
