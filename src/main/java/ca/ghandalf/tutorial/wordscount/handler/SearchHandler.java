/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.tutorial.wordscount.handler;

import ca.ghandalf.tutorial.wordscount.domain.WordContainer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ghandalf
 */
public class SearchHandler {

    protected static final String END_WORD_PATTERN = "[,.;!?0-9]$";
    protected static final String BEGIN_WORD_PATTERN = "^[,.;!?0-9]";

    private List<WordContainer> theMostUsedWords;
    private int numberOfWords;

    public SearchHandler() {
        this.theMostUsedWords = new ArrayList<>();
        this.numberOfWords = 0;
    }
    
    protected void incrementNumberOfWords() {
        this.numberOfWords++;
    }

    public List<WordContainer> getTheMostUsedWords() {
        return theMostUsedWords;
    }

    public void setTheMostUsedWords(List<WordContainer> theMostUsedWords) {
        this.theMostUsedWords = theMostUsedWords;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }
    
    public void printTheMostUsedWords() {
        this.theMostUsedWords.forEach(k -> System.out.println(k));
    }
}
