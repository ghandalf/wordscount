package ca.ghandalf.tutorial.wordscount.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ca.ghandalf.tutorial.wordscount.domain.WordContainer;

public class Sort {

    // No instantiation
    private Sort() {
    }

    public static final Map<String, Integer> sort(Map<String, Integer> map) {
        Map<String, Integer> result = new LinkedHashMap<>();

        String[] keys = map.keySet().toArray(new String[0]);
        Integer[] values = map.values().toArray(new Integer[0]);

        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values.length - 1; j++) {

                if (values[j] > values[j + 1]) {
                    String currentWord = keys[j];
                    String nextWord = keys[j + 1];
                    Integer currentValue = values[j];
                    Integer nextValue = values[j + 1];

                    // Swap values and keys
                    keys[j] = nextWord;
                    keys[j + 1] = currentWord;
                    values[j] = nextValue;
                    values[j + 1] = currentValue;
                }
            }
        }
        for (int i = 0; i < values.length; i++) {
            result.put(keys[i], values[i]);
        }

        return result;
    }

    public static final List<WordContainer> sort(List<WordContainer> list)
            throws CloneNotSupportedException {

        WordContainer[] arrayList = list.toArray(new WordContainer[list.size()]);

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size() - 1; j++) {

                if (arrayList[j].getCount() > arrayList[j + 1].getCount()) {
                    WordContainer currentWordClone = arrayList[j].clone();
                    WordContainer nextWordClone = arrayList[j + 1].clone();

                    arrayList[j] = nextWordClone;
                    arrayList[j + 1] = currentWordClone;
                }
            }
        }

        return Arrays.asList(arrayList);
    }

    public static void insertOrUpdate(WordContainer[] words, WordContainer word, int index) {
        if (index < words.length) {
            words[index] = word;

        } else {
            for (int i = 0; i < words.length; i++) {
                if (word.getCount() > words[i].getCount()) {
                    words[i] = word;
                    break;
                }
            }
        }
    }

    public static final Map<String, Integer> sortMapByValue(Map<String, Integer> map) {
        Map<String, Integer> result
                = map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return result;
    }

    public static final Map<String, Integer> sortMapByKey(Map<String, Integer> map) {
        Map<String, Integer> result
                = map.entrySet().stream().sorted(Map.Entry.comparingByKey())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        return result;
    }

    public static final void sortListByKey(List<WordContainer> list) {
        list.sort((me, you) -> me.getWord().compareTo(you.getWord()));
    }

    public static final void sortListByValue(List<WordContainer> list) {
        list.sort(new Comparator<WordContainer>() {
            @Override
            public int compare(WordContainer me, WordContainer you) {
                return me.getCount() - you.getCount();
            }
        });
    }
}
