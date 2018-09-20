package ca.ghandalf.tutorial.wordscount.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ca.ghandalf.tutorial.wordscount.domain.WordContainer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ListWordsSearch.class)
public class ListWordsSearchTest {

  @Autowired
  private ListWordsSearch listWordsSearch;
  
  @Before
  public void setUp() {
    Assert.assertNotNull(listWordsSearch);
    loadData();
  }
  
  public void loadData() {
    String filename = "map.txt";
    
    listWordsSearch.loadData(filename);
    List<WordContainer> actual = listWordsSearch.getData();
    int expected = 0;
    Assert.assertNotEquals("e expecte data in the map, so size can't be 0.", expected, actual.size());
  }
  
  @Test
  public void sort() {
    WordContainer expected = listWordsSearch.getData().get(0);
    
    listWordsSearch.sort();
    WordContainer  actual = listWordsSearch.getData().get(0);
    Assert.assertNotEquals("In the file used to create the original map, J is the first element after the sort B must be the first element", expected.getWord(), actual.getWord());
    Assert.assertEquals("J".toLowerCase(), expected.getWord());
    Assert.assertEquals("B".toLowerCase(), actual.getWord());
  }
  
  @Test
  public void sortByKey() {
    List<WordContainer> list = new ArrayList<>();
    list.add(new WordContainer("Z", 12));
    list.add(new WordContainer("T", 11));
    list.add(new WordContainer("B", 2));
    list.add(new WordContainer("A", 24));
    
    Optional<WordContainer> expected = list.stream().findFirst();

    listWordsSearch.sortByKey(list);
    Optional<WordContainer> actual = list.stream().findFirst();

    Assert.assertNotEquals("We don't expect the same values", expected, actual);
  }
  
  @Test
  public void sortByValue() {
    List<WordContainer> list = new ArrayList<>();
    list.add(new WordContainer("Z", 12));
    list.add(new WordContainer("T", 11));
    list.add(new WordContainer("B", 2));
    list.add(new WordContainer("A", 24));
    
    Optional<WordContainer> expected = list.stream().findFirst();

    listWordsSearch.sortByValue(list);
    list.forEach(w -> System.out.println(w));
    Optional<WordContainer> actual = list.stream().findFirst();

    Assert.assertNotEquals("We don't expect the same values", expected, actual);
  }
  
  @Test
  public void extractTheMostUsedWords() {
    int expected = 1;
    List<WordContainer> mostUsedWords = listWordsSearch.extractTheMostUsedWords(expected);
    int actual = mostUsedWords.size();
    
    Assert.assertEquals("We expected that the list will not be more then one.", expected, actual);
    
    expected = 4;
    mostUsedWords = listWordsSearch.extractTheMostUsedWords(expected);
    actual = mostUsedWords.size();
   
    Assert.assertEquals("We expected that the list will not be more then " + expected + ".", expected, actual);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void extractTheMostUsedWordsFailure() {
    listWordsSearch.extractTheMostUsedWords(99999999);
  }
  
  @Test
  public void expectingThatNbWordsNotEqualsListSize() {
    Assert.assertNotSame("The amount of words can't be equals the amount of element in the list due to the word repetition", listWordsSearch.getNumberOfWords(), listWordsSearch.getData().size());
  }
}
