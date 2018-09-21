/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.ghandalf.tutorial.wordscount.thread;

import ca.ghandalf.tutorial.wordscount.handler.ListWordsSearch;
import ca.ghandalf.tutorial.wordscount.handler.MapWordsSearch;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author ghandalf
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParallelTask.class, ListWordsSearch.class, MapWordsSearch.class})
public class ParallelTaskTest {

    public ParallelTaskTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * We have three threads and we need to run in the order T1, T2 and T3 i.e.
     * T1 should start first and T3 should start last. You can enforce this
     * ordering using join() method but join method must be called from run()
     * method because the thread which will execute run() method will wait for
     * thread on which join is called.
     *
     * Test of run method, of class ParallelTask.
     */
    @Test(timeout = 1200)
    public void parallelExecutionByListWords() {
        ParallelTask instance_one = new ParallelTask("ParallelTask One");
        ParallelTask instance_two = new ParallelTask("ParallelTask Two");
        ParallelTask instance_three = new ParallelTask("ParallelTask Three");

        // Spring Context is not used here. 
        instance_one.setListWordsSearch(new ListWordsSearch());
        instance_two.setListWordsSearch(new ListWordsSearch());
        instance_three.setListWordsSearch(new ListWordsSearch());

        ParallelTask.Handler executor = ParallelTask.Handler.ListWordsSearch;
        executor.setWords(4);
        instance_one.setHandler(executor);
        instance_two.setHandler(executor);
        instance_three.setHandler(executor);

        final Thread thread_one = new Thread(instance_one, "Thread One");
        final Thread thread_two = new Thread(instance_two, "Thread Two");
        final Thread thread_three = new Thread(instance_three, "Thread Three");

        instance_two.setPredecessor(thread_one);
        instance_three.setPredecessor(thread_two);

        thread_one.start();
        thread_two.start();
        thread_three.start();
    }

    @Test(timeout = 1200)
    public void parallelExecutionByMapWords() {
        ParallelTask instance_one = new ParallelTask("ParallelTask One");
        ParallelTask instance_two = new ParallelTask("ParallelTask Two");
        ParallelTask instance_three = new ParallelTask("ParallelTask Three");

        // Spring Context is not used here.
        instance_one.setMapWordsSearch(new MapWordsSearch());
        instance_two.setMapWordsSearch(new MapWordsSearch());
        instance_three.setMapWordsSearch(new MapWordsSearch());

        ParallelTask.Handler executor = ParallelTask.Handler.MapWordsSearch;
        executor.setWords(4);
        instance_one.setHandler(executor);
        instance_two.setHandler(executor);
        instance_three.setHandler(executor);

        final Thread thread_one = new Thread(instance_one, "Thread One");
        final Thread thread_two = new Thread(instance_two, "Thread Two");
        final Thread thread_three = new Thread(instance_three, "Thread Three");

        instance_two.setPredecessor(thread_one);
        instance_three.setPredecessor(thread_two);

        thread_one.start();
        thread_two.start();
        thread_three.start();
    }

    /**
     * Test of setPredecessor method, of class ParallelTask.
     */
    @Test
    public void setNullredecessor() {
        Thread predecessor = null;

        ParallelTask instance = new ParallelTask("ParallelTask One");

        instance.setPredecessor(predecessor);

        assertNull(instance.getPredecessor());
    }

}
