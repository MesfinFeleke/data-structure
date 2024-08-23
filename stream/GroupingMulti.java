package stream;
import stream.ReductionCollection.Book2;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class GroupingMulti {


    private static void collectToMap(List<Book2> books) {
        System.out.println("\nIn collectToMap ...");
        Map<Long, Book2> map = books.stream()
                //.collect(Collectors.toMap(b -> b.getIsbn(), b -> b));
                .collect(Collectors.toMap(b -> b.getIsbn(), b -> b, (b1, b2) -> b1.getPrice() <= b2.getPrice() ? b1 : b2));
        for (Map.Entry<Long, Book2> entry : map.entrySet()) {
            System.out.println("isbn: " + entry.getKey() + ", Book2: " + entry.getValue());
        }

        System.out.println(map instanceof HashMap);

        Map<Long, Book2> treeMap = books.stream()
                //.collect(Collectors.toMap(b -> b.getIsbn(), b -> b));
                //.collect(Collectors.toMap(Book2::getIsbn, Function.identity(), (b1, b2) -> b1.getPrice() <= b2.getPrice() ? b1 : b2, () -> new TreeMap()));
                .collect(Collectors.toMap(Book2::getIsbn, Function.identity(), BinaryOperator.minBy(Comparator.comparingDouble(Book2::getPrice))));
        for (Map.Entry<Long, Book2> entry : treeMap.entrySet()) {
            System.out.println("isbn: " + entry.getKey() + ", Book2: " + entry.getValue());
        }
		
			/*Map<Double, List<Book2>> ratingsMap = treeMap.values().stream()
				.collect(Collectors.toMap(Book2::getRating, b -> Collections.singletonList(b), (l1, l2) -> { ArrayList<Book2> l = new ArrayList<>(l1);
																				l.addAll(l2);
																				return l;}));
			for (Entry<Double, List<Book2>> entry : ratingsMap.entrySet()) {
				System.out.println("\nRating: " + entry.getKey());
				for (Book2 b : entry.getValue()) {
					System.out.println(b);
				}
			}*/

        System.out.println("\ngroupingBy with values as List ... ");
        Map<Double, List<Book2>> ratingsMap1 = treeMap.values().stream()
                .collect(Collectors.groupingBy(Book2::getRating));
        for (Map.Entry<Double, List<Book2>> entry : ratingsMap1.entrySet()) {
            System.out.println("\nRating: " + entry.getKey());
            for (Book2 b : entry.getValue()) {
                System.out.println(b);
            }
        }

        System.out.println("\ngroupingBy with values as Set ... ");
        Map<Double, Set<Book2>> ratingsMap2 = treeMap.values().stream()
                .collect(Collectors.groupingBy(Book2::getRating, TreeMap::new, toSet()));
        for (Map.Entry<Double, Set<Book2>> entry : ratingsMap2.entrySet()) {
            System.out.println("\nRating: " + entry.getKey());
            for (Book2 b : entry.getValue()) {
                System.out.println(b);
            }
        }

        Map<Double, Map<String, List<Book2>>> multiLevelMap = treeMap.values().stream()
                .collect(Collectors.groupingBy(Book2::getRating, Collectors.groupingBy(Book2::getSource, toList())));
        System.out.println("\nmultiLevelMap: " + multiLevelMap);

        Map<Double, Long> ratingsCountMap = treeMap.values().stream()
                .collect(Collectors.groupingBy(Book2::getRating, Collectors.counting()));
        System.out.println("\nratingsCountMap: " + ratingsCountMap);
        System.out.println("\ncount: " + treeMap.values().stream().collect(Collectors.counting()));

        Map<Double, Double> ratingsAvgPriceMap = treeMap.values().stream()
                .collect(Collectors.groupingBy(Book2::getRating, Collectors.averagingDouble(Book2::getPrice)));
        System.out.println("\nratingsAvgPriceMap: " + ratingsAvgPriceMap);

        Map<Double, Optional<Book2>> ratingsMinPriceMap = treeMap.values().stream()
                .collect(Collectors.groupingBy(Book2::getRating, Collectors.minBy(Comparator.comparingDouble(Book2::getPrice))));
        System.out.println("\nratingsMinPriceMap: " + ratingsMinPriceMap);

        Map<Double, DoubleSummaryStatistics> ratingsSummaryMap = treeMap.values().stream()
                .collect(Collectors.groupingBy(Book2::getRating, Collectors.summarizingDouble(Book2::getPrice)));
        System.out.println("\nDoubleSummaryStatistics: " + ratingsSummaryMap);

        Map<Boolean, List<Book2>> partitionedMap = treeMap.values().stream()
                .collect(Collectors.partitioningBy(b -> b.getRating() >= 4.5));
        System.out.println("\npartitionedMap: " + partitionedMap);

        //System.out.println("\ngroupingBy with values as List ... ");
        Map<Double, List<String>> ratingsTitleMap = treeMap.values().stream()
                .collect(Collectors.groupingBy(Book2::getRating, Collectors.mapping(Book2::getTitle, toList())));
        System.out.println("\nratingsTitleMap: " + ratingsTitleMap);
    }

/*    private static void collectToCollection(List<Book2> books) {
        System.out.println("\nIn collectToCollection ...");
        List<Book2> list1 = books.stream()
                .filter(b -> b.getRating() >= 4.5)
                .distinct()
                .collect(ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll);
        //.collect(Collectors.toList());
        System.out.println("list1.size: " + list1.size());

        Set<Book2> set1 = books.stream()
                .filter(b -> b.getRating() >= 4.5)
                .collect(toSet());
        System.out.println("set1.size: " + set1.size());

        TreeSet<Book2> set2 = books.stream()
                .filter(b -> b.getRating() >= 4.5)
                .collect(toCollection(() -> new TreeSet()));
        System.out.println("set2.size: " + set2.size());
    }*/

    // if accumulator mutates, use collect(). Otherwise, use reduce()
    private static void mutableReduction() {
        System.out.println("\nmutableReduction ... ");
        String[] grades = {"A", "A", "B"};

        StringBuilder concat2 = Arrays.stream(grades).parallel()
                /*.collect(() -> new StringBuilder(), 
                        (sb, s) -> sb.append(s),
                        (sb1, sb2) -> sb1.append(sb2));*/
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append);
        System.out.println("concat2: " + concat2);

        String concatWithJoining = Arrays.stream(grades).parallel()
                .collect(joining());
        System.out.println("concatWithJoining: " + concatWithJoining);
    }

    static void overloadedReductions() {
        System.out.println("\noverloadedReductions ... ");

        String[] grades = {"A", "A", "B"};

        // Stream API: Design Principle!!!
        // API does not differentiate between sequential & parallel streams
        String concat1 = Arrays.stream(grades)
                .reduce("", (s1, s2) -> s1 + s2);
        System.out.println("concat1: " + concat1);

        // Single instance of container is used + 
        //                SB is not thread-safe + 
        //                combiner redundantly combines
        StringBuilder concat2 = Arrays.stream(grades).parallel()
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

        for (Book2 Book2 : books) {
            // Initialize result with first Book2 having rating >= 4.5
            if (result == null) {
                if (Book2.getRating() >= 4.5) {
                    result = Book2;
                }
                continue;
            }

            if (Book2.getRating() >= 4.5 && result.getPrice() > Book2.getPrice()) {
                result = Book2;
            }
        }

        System.out.println("(Imperative) Lowest priced Book2 with rating >= 4.5: " + result);

    }

    // Find the lowest priced Book2 with a rating >= 4.5
    private static void reduce(List<Book2> books) {
        System.out.println("\nReduce ...");
        books.stream()
                .filter(b -> b.getRating() >= 4.5)
                .reduce((b1, b2) -> b1.getPrice() <= b2.getPrice() ? b1 : b2)
                .ifPresent(b -> System.out.println("Lowest priced Book2: " + b));
    }


    // findFirst needs more work in parallel env. Use findAny if it does the job.
    // java.util.Optional ~ 
    //     (a) to avoid dealing with null -- in case of find, 
    //     (b) to know if stream is empty -- in case of reduction operation
    private static void find(List<Book2> books) {
        System.out.println("\nFinding ...");
        /*Optional<Book2> result = */ books.stream()
                .filter(d -> d.getRating() >= 4.8 && d.getPrice() <= 50.0)
                .findAny().orElseGet(GroupingMulti::getDefault);
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
    //  (a) Is there at least one highly rated Book2 (>= 4.8) that is inexpensive (<= $50)?
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
    // DB world: select distinct (ISBN) from Book2 where rating >= 4.5 limit 0, 5;
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

        List<Book2> books = new ArrayList<>();

        books.addAll(DataExtractor2.getFromAmazon("java"));
        books.addAll(DataExtractor2.getFromBarnesAndNoble("java"));

        // intermediate operations (return stream objects)
        //slice(books);		

        // terminal operations (return non-stream objects)
        //match(books);  // matching stream elements to some criteria

        // All matching & finding operations + limit 
        //    are short-circuit operations (recall &&, ||)	

        // find(books);		

        //reduce(books);
        //reduceImperatively(books);
        //overloadedReductions();	

        //mutableReduction();

        //collectToCollection(books);

        collectToMap(books);

    }

}
