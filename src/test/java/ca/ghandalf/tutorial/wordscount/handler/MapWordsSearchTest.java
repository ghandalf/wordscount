package ca.ghandalf.tutorial.wordscount.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
@SpringBootTest(classes = MapWordsSearch.class)
public class MapWordsSearchTest {

  @Autowired
  private MapWordsSearch mapWordsSearch;
  
  @Before
  public void setUp() {
    Assert.assertNotNull(mapWordsSearch);
    loadData();
  }
  
  private void loadData() {
    String filename = "map.txt";
    
    mapWordsSearch.loadData(filename);
    Map<String, Integer> actual = mapWordsSearch.getData();
    int expected = 0;
    
    Assert.assertNotEquals("We expecte data in the map, so size can't be 0.", expected, actual.size());
  }
  
  @Test
  public void sortUsingBubble() {
    Optional<Map.Entry<String, Integer>> expected = mapWordsSearch.getData().entrySet().stream().findFirst();
    
    mapWordsSearch.sortUsingBubble(mapWordsSearch.getData());
    
    Optional<Map.Entry<String, Integer>> actual = mapWordsSearch.getData().entrySet().stream().findFirst();
    
    Assert.assertNotEquals("After the sort, we don't expected to have the same key/value in the first element.",
        expected, actual);
    Assert.assertFalse(expected.equals(actual));
  }
  
  @Test
  public void sortByKey() {
    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("Z", 12);
    map.put("T", 11);
    map.put("B", 2);
    map.put("A", 24);
        
    Optional<Map.Entry<String, Integer>> expected = map.entrySet().stream().findFirst();
    
    Map<String, Integer> result = mapWordsSearch.sortByKey(map);
    
    Optional<Map.Entry<String, Integer>> actual = result.entrySet().stream().findFirst();

    Assert.assertNotEquals("After the sort, we don't expected the same key/value in the firs element.", expected, actual);
    Assert.assertFalse(expected.equals(actual));
  }
  
  @Test
  public void sortByValue() {
    Map<String, Integer> map = new LinkedHashMap<>();
    map.put("Z", 12);
    map.put("T", 11);
    map.put("B", 2);
    map.put("A", 24);
    
    Optional<Map.Entry<String, Integer>> expected = map.entrySet().stream().findFirst();
    
    Map<String, Integer> result = mapWordsSearch.sortByValue(map);
    
    Optional<Map.Entry<String, Integer>> actual = result.entrySet().stream().findFirst();
    
    Assert.assertNotEquals("After the sort, we don't expected the same key/value in the first element", expected, actual);
    Assert.assertFalse(expected.equals(actual));
  }

  @Test
  public void extractTheMostUsedWords() {
    int expected = 1;
    
    List<WordContainer> mostUsedWords = mapWordsSearch.extractTheMostUsedWords(1);
    int actual = mostUsedWords.size();
    
    Assert.assertEquals("We expected the size of the list will not to be more then one", expected,  actual);
    
    expected = 4;
    mostUsedWords = mapWordsSearch.extractTheMostUsedWords(expected);
    actual = mostUsedWords.size();
   
    Assert.assertEquals("We expected that the list will not be more then " + expected + ".", expected, actual);    
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void extractTheMostUsedWordsFailure() {
    mapWordsSearch.extractTheMostUsedWords(99999999);
  }
  
  @Test
  public void expectingThatNbWordsNotEqualsListSize() {
    Assert.assertNotSame("The amount of words can't be equals the amount of element in the list due to the word repetition", mapWordsSearch.getNumberOfWords(), mapWordsSearch.getData().size());
  }
}
