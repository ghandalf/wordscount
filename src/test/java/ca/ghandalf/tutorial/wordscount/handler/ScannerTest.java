package ca.ghandalf.tutorial.wordscount.handler;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Scanner;
import static org.junit.Assert.*;

/**
 * Use to understand {@code Regex}
 *
 * @author ghandalf
 */
@RunWith(SpringRunner.class)
public class ScannerTest {

    /**
     * This pattern will remove all string finishing with any characters equals
     * to in square brackets.
     */
    @Test
    public void scanRegex() {
        String values = "the the, The. the; The! the? th,e th.e th!e th?e th1e th2e";
        String expected = "the";
        String pattern = "[,.;!?]\\z";
        List<String> actuals = new ArrayList<>();

        Scanner scanner = new Scanner(values);
        while (scanner.hasNext()) {
            String result = scanner.next();
            actuals.add(result.replaceAll(pattern, ""));
        }

        for (int i = 0; i < actuals.size(); i++) {
            if (i < (actuals.size() - 6)) {
                assertTrue(expected.equalsIgnoreCase(actuals.get(i)));
            } else {
                assertFalse(expected.equalsIgnoreCase(actuals.get(i)));
            }
        }
    }

    @Test
    public void understandRegexSimplefWord() {
        String values = "the the, the. tHe; the! thE?";

        String expected = "the";
        String[] actuals = values.split(" ");

        assertTrue(expected.equals(actuals[0]));

        for (int i = 1; i < actuals.length; i++) {
            assertFalse(expected.equals(actuals[i].toLowerCase()));
        }
    }

    @Test
    public void removePonctuationAtTheEndOfWordUsingRegex() {
        String values = "the, the. the; the! the?";

        String expected = "the";
        String[] actuals = values.split(" ");

        String pattern = "[,.;!?]"; // All , . ; ! ? at the end of a word

        for (String actual : actuals) {
            assertEquals(expected, actual.replaceAll(pattern, "").toLowerCase());
        }
    }

    @Test
    public void removePonctuationAtTheEndOfWordUsingRegexWithDollard() {
        String values = "the, the. the; the! the?";

        String expected = "the";
        String[] actuals = values.split(" ");

        String pattern = "[,.;!?]$"; // All , . ; ! ? at the end of a word

        for (String actual : actuals) {
            assertEquals(expected, actual.replaceAll(pattern, "").toLowerCase());
        }
    }

    @Test
    public void removePonctuationAtTheBeginningOfWordUsingRegexWithDollard() {
        String values = ",the .the ;the !the ?the 1The 2THE 3the";

        String expected = "the";
        String[] actuals = values.split(" ");

        String pattern = "^[,.;!?0-9]"; // All , . ; ! ? at the end of a word

        for (String actual : actuals) {
            assertEquals(expected, actual.replaceAll(pattern, "").toLowerCase());
        }
    }

    @Test
    public void removePonctuationInTheMiddleOfTheWord() {
        String values = "tH,e th.E th!e th?e Th1e th2e";
        String expected = "the";
        String pattern = "[,.;!?0-9]";

        Scanner scanner = new Scanner(values);
        while (scanner.hasNext()) {
            String result = scanner.next();
            assertTrue(expected.equalsIgnoreCase(result.replaceAll(pattern, "").toLowerCase()));
        }
    }
}
