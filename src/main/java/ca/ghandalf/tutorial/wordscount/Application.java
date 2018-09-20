package ca.ghandalf.tutorial.wordscount;

import java.util.Map;
import ca.ghandalf.tutorial.wordscount.domain.WordContainer;
import ca.ghandalf.tutorial.wordscount.handler.ListWordsSearch;
import ca.ghandalf.tutorial.wordscount.handler.MapWordsSearch;
import ca.ghandalf.tutorial.wordscount.thread.ParallelTask;
import ca.ghandalf.tutorial.wordscount.util.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Words count application
 *
 */
@SpringBootApplication
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        if (args.length > 0) {
            String which = args[0];
            System.out.println("Let's begin... ");

            switch (which) {
                case "list":
                    listWords();
                    break;
                case "map":
                    mapWords();
                    break;
                case "test":
                    test();
                    break;
                case "values":
                    mapByValues();
                    break;
                case "parallel":
                    parallelExecution();
                    break;
                default:
                    usage();
            }
        } else {
            usage();
        }
    }

    private static void usage() {
        System.out.println("\t Usage:");
        System.out.println("\t\t\n mvn spring-boot:run -Dspring-boot.run.arguments={list|map|test|values|parallel}\n");
    }

    private static void test() {
        WordContainer[] words = new WordContainer[10];
        words[0] = new WordContainer("J", 12);
        words[1] = new WordContainer("K", 3);
        words[2] = new WordContainer("L", 5);
        words[3] = new WordContainer("M", 7);
        words[4] = new WordContainer("P", 20);
        words[5] = new WordContainer("F", 2);
        words[6] = new WordContainer("A", 4);
        words[7] = new WordContainer("B", 1);
        words[8] = new WordContainer("C", 6);
        words[9] = new WordContainer("D", 8);

        Sort.insertOrUpdate(words, new WordContainer("O", 21), 10);

        for (WordContainer word : words) {
            System.out.println(word);
        }
    }

    private static void listWords() {
        ListWordsSearch listWordsSearch = new ListWordsSearch();
        listWordsSearch.loadData("simple.txt");

        System.out.println("Before sorting...");
        listWordsSearch.printData();
        listWordsSearch.sort();

        System.out.println("After sorting...");
        listWordsSearch.printData();
        int nbOfMostUsedWords = 4;
        System.out.format("The %s most used words are: \n", nbOfMostUsedWords);
        listWordsSearch.extractTheMostUsedWords(4);
        listWordsSearch.printTheMostUsedWords();
        System.out.println("Number of words in the file: " + listWordsSearch.getNumberOfWords());
    }

    private static void mapWords() {
        MapWordsSearch mapWordsSearch = new MapWordsSearch();
        mapWordsSearch.loadData("map.txt");

        System.out.println("Before sorting...");
        mapWordsSearch.printData();

        mapWordsSearch.sortUsingBubble(mapWordsSearch.getData());

        System.out.println("After sorting...");
        mapWordsSearch.printData();

        int nbOfMostUsedWords = 4;
        System.out.format("The %s most used words are: \n", nbOfMostUsedWords);
        mapWordsSearch.extractTheMostUsedWords(4);

        mapWordsSearch.printTheMostUsedWords();
        System.out.println("Number of words in the file: " + mapWordsSearch.getNumberOfWords());
    }

    private static void mapByValues() {
        MapWordsSearch mapWordsSearch = new MapWordsSearch();
        mapWordsSearch.loadData("map.txt");

        System.out.println("Before sorting...");
        mapWordsSearch.getData().forEach((k, v) -> System.out.println(k + ":" + v));

        Map<String, Integer> currentValues = mapWordsSearch.sortByValues(mapWordsSearch.getData());

        System.out.println("After sorting...");
        currentValues.forEach((k, v) -> System.out.println(k + ":" + v));

    }

    private static void parallelExecution() {
        ParallelTask instance_one = new ParallelTask("ParallelTask One");
        ParallelTask instance_two = new ParallelTask("ParallelTask Two");
        ParallelTask instance_three = new ParallelTask("ParallelTask Three");

        final Thread thread_one = new Thread(instance_one, "Thread One");
        final Thread thread_two = new Thread(instance_two, "Thread Two");
        final Thread thread_three = new Thread(instance_three, "Thread Three");

        instance_two.setPredecessor(thread_one);
        instance_three.setPredecessor(thread_two);

        thread_one.start();
        thread_two.start();
        thread_three.start();
    }
}
