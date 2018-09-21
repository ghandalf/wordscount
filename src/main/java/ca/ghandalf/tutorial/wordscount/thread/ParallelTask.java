/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.tutorial.wordscount.thread;

import ca.ghandalf.tutorial.wordscount.handler.ListWordsSearch;
import ca.ghandalf.tutorial.wordscount.handler.MapWordsSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @see
 * http://www.java67.com/2015/07/how-to-join-two-threads-in-java-example.html
 *
 * @author ghandalf
 */
@Component
public class ParallelTask implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ParallelTask.class);

    @Autowired
    private ListWordsSearch listWordsSearch;

    @Autowired
    private MapWordsSearch mapWordsSearch;

    private Handler type;

    private Thread predecessor;
    private String name;

    public ParallelTask() {
    }

    public ParallelTask(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + ": Started");

        String information = "\t\tRunning: [%s] his predecessor is [%s] %n";

        if (predecessor != null) {
            try {

                predecessor.join();
                System.out.format(information, this.getName(), predecessor.getName());

            } catch (InterruptedException ex) {
                LOG.error("Interrupted Exception in class: {} error: {}", ParallelTask.class.getName(), ex.getMessage());
            }
        } else {
            System.out.format(information, this.getName(), (predecessor != null) ? predecessor.getName() : "null");
        }

        execute();

        System.out.println(Thread.currentThread().getName() + ": Finished");
    }

    public void setPredecessor(Thread predecessor) {
//        System.out.format("\t\tIn [%s] setting the predecessor Thread: [%s]%n", this.getName(), (predecessor != null) ? predecessor.getName() : "null");
        this.predecessor = predecessor;
    }

    public Thread getPredecessor() {
        return this.predecessor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ListWordsSearch getListWordsSearch() {
        return listWordsSearch;
    }

    public void setListWordsSearch(ListWordsSearch listWordsSearch) {
        this.listWordsSearch = listWordsSearch;
    }

    public MapWordsSearch getMapWordsSearch() {
        return mapWordsSearch;
    }

    public void setMapWordsSearch(MapWordsSearch mapWordsSearch) {
        this.mapWordsSearch = mapWordsSearch;
    }

    public void setHandler(Handler type) {
        this.type = type;
    }

    public Handler getHandler() {
        return type;
    }

    /**
     * Define which kind of handler we are going to call.
     * By default at least we have a word in the file
     */
    public enum Handler {
        ListWordsSearch("list", 1), MapWordsSearch("map", 1);

        private final String name;
        private int words;

        private Handler(String name, int words) {
            this.name = name;
            this.words = words;
        }

        public String getName() {
            return this.name;
        }
        
        public int getWords() {
            return this.words;
        }
        
        public void setWords(int words) {
            this.words = words;
        }
    }

    /**
     * Expected that type as been call first
     */
    private void execute() {
        System.out.format("\t\tRunning: [%s] execute() with task: [%s] %n", this.getName(), (this.getHandler() != null) ? this.getHandler().getName() : "Handler name is null");
        
        switch (this.getHandler()) {
            case ListWordsSearch:
                
                this.listWordsSearch.loadData("simple.txt");
                this.listWordsSearch.extractTheMostUsedWords(this.getHandler().getWords());
                this.listWordsSearch.printTheMostUsedWords();
                break;
            case MapWordsSearch:
                
                this.mapWordsSearch.loadData("simple.txt");
                this.mapWordsSearch.extractTheMostUsedWords(this.getHandler().getWords());
                this.mapWordsSearch.printTheMostUsedWords();
                break;
            
            default:
                throw new AssertionError();
        }

    }
}
