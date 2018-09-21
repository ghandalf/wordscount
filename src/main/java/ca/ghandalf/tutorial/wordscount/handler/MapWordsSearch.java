package ca.ghandalf.tutorial.wordscount.handler;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import ca.ghandalf.tutorial.wordscount.domain.WordContainer;
import ca.ghandalf.tutorial.wordscount.util.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The class {@code MapWordSearch} scan a file to find out the most used words.
 * Those words will be keep in memory in lower case. All the punctuation at the
 * end or in the middle of the word will be removed.
 *
 * @author ghandalf
 */
@Component
public class MapWordsSearch extends SearchHandler {

    private static final Logger LOG = LoggerFactory.getLogger(MapWordsSearch.class);

    private Map<String, Integer> values;

    public MapWordsSearch() {
        super();
        this.values = new LinkedHashMap<>();
    }

    /**
     * Load the data in a structure and count the amount of words.
     *
     * @param fileName
     */
    public void loadData(String fileName) {

        Path path;
        try {
            path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
            try ( Scanner scanner = new Scanner(path.toFile())) {

                while (scanner.hasNext()) {
                    String currentWord = scanner.next();
                    currentWord = currentWord.replaceAll(END_WORD_PATTERN, "").replaceAll(BEGIN_WORD_PATTERN, "").toLowerCase();
                    load(currentWord);
                    this.incrementNumberOfWords();
                }

            } catch (FileNotFoundException e) {
                System.out.println("Please provides the file...");
            }

        } catch (URISyntaxException e1) {
            System.out.println("Please fix the URI syntax...");
        }

    }

    private void load(String word) {
        if (this.values.containsKey(word)) {
            this.values.put(word, this.values.get(word) + 1);
        } else {
            this.values.put(word, 1);
        }
    }

    public Map<String, Integer> getData() {
        return this.values;
    }

    public Map<String, Integer> sortUsingBubble(Map<String, Integer> values) {
        this.values = Sort.sort(values);

        return this.values;
    }

    public Map<String, Integer> sortByKey(Map<String, Integer> map) {
        this.values = Sort.sortMapByKey(map);
        return this.values;
    }

    public Map<String, Integer> sortByValue(Map<String, Integer> map) {
        this.values = Sort.sortMapByValue(map);
        return this.values;
    }

    public List<WordContainer> extractTheMostUsedWords(int nbOfMostUsedWords) {

        List<WordContainer> list = new ArrayList<>();

        if (nbOfMostUsedWords < this.values.size()) {
            this.sortByValue(this.values);

            String keys[] = this.values.keySet().toArray(new String[0]);
            Integer currentValues[] = this.values.values().toArray(new Integer[this.values.size()]);

            for (int i = 0; i < nbOfMostUsedWords; i++) {
                list.add(new WordContainer(keys[i], currentValues[i]));
            }

            this.setTheMostUsedWords(list);

            return this.getTheMostUsedWords();

        } else {
            throw new IllegalArgumentException(
                    "The size of the most used words given is bigger then the size of the data!!!");
        }
    }

    public void printData() {
        this.values.forEach((k, v) -> System.out.println(k + ":" + v));
    }

}
