package ca.ghandalf.tutorial.wordscount;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ca.ghandalf.tutorial.wordscount.handler.ListWordsSearch;
import ca.ghandalf.tutorial.wordscount.handler.MapWordsSearch;
import ca.ghandalf.tutorial.wordscount.thread.ParallelTask;
import ca.ghandalf.tutorial.wordscount.util.Sort;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Sort.class, ListWordsSearch.class, MapWordsSearch.class, ParallelTask.class})
public class ApplicationTest {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationTest.class);

    @Test
    public void contextLoads() {
        LOG.info("Spring beans initialization...");
    }
}
