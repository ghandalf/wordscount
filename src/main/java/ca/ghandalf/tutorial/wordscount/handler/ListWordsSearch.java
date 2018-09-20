package ca.ghandalf.tutorial.wordscount.handler;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import ca.ghandalf.tutorial.wordscount.domain.WordContainer;
import ca.ghandalf.tutorial.wordscount.util.Sort;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;

/**
 * The class {@code ListWordsSearch} scan a file to find out the most used words.
 * Those words will be keep in memory in lower case. All the punctuation at the
 * end or in the middle of the word will be removed.
 *
 * @author ghandalf
 */
@Component
public class ListWordsSearch extends SearchHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ListWordsSearch.class);

    private List<WordContainer> values;

    public ListWordsSearch() {
        super();
        this.values = Collections.synchronizedList(new ArrayList<>());
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
                LOG.error("Please provides the proper path or filename...");
            }

        } catch (URISyntaxException e1) {
            LOG.error("Please fix the URI syntax...");
        }

    }

    private void load(String word) {
        WordContainer wordContainer = new WordContainer(word);

        synchronized (this.values) {
            if (this.values.contains(wordContainer)) {
                WordContainer currentWordContainer = retrieve(word);
                currentWordContainer.setCount(currentWordContainer.getCount() + 1);
            } else {
                wordContainer.setCount(wordContainer.getCount() + 1);
                this.values.add(wordContainer);
            }
        }

    }

    public List<WordContainer> getData() {
        return this.values;
    }

    public void sort() {
        try {
            synchronized (this.values) {
                this.values = Sort.sort(this.values);
            }

        } catch (CloneNotSupportedException e) {
            LOG.error("{} can't be cloned...", WordContainer.class.getSimpleName());
        }
    }

    public void sortByKey(List<WordContainer> list) {
        synchronized (list) {
            Sort.sortListByKey(list);
        }
    }

    public void sortByValue(List<WordContainer> list) {
        synchronized (list) {
            Sort.sortListByValue(list);
        }
    }

    public List<WordContainer> extractTheMostUsedWords(int nbOfMostUsedWords) {

        // There is a problem here, when we use the clear() method on the list. 
        // It is the same method use in MapWordSearch 
        // the same List definition and if failed with UnsupportedOperation
//            this.getTheMostUsedWords().clear();
        this.setTheMostUsedWords(new ArrayList<>());

        synchronized (this.values) {
            if (nbOfMostUsedWords < this.values.size()) {
                this.setTheMostUsedWords(
                        this.values.subList(this.values.size() - nbOfMostUsedWords, this.values.size()));

                return this.getTheMostUsedWords();

            } else {
                throw new IllegalArgumentException(
                        "The size of the most used words given is bigger then the size of the data!!!");
            }
        }
    }

    private WordContainer retrieve(String word) {
        synchronized (this.values) {
            for (WordContainer container : this.values) {
                if (container.getWord().equals(word)) {
                    return container;
                }
            }
        }
        return null;
    }

    public void printData() {
        this.values.forEach(k -> System.out.println(k));
    }
}
