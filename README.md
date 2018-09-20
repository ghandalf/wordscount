# README #

The aim of the project is to response an interview question that I had.

I want to thank the interviewer for his patient. In this interview, I mistaken 
too often, this is why I decided to create this little project. 

Those questions are not usual, how often are we going to work with threads 
this days...


### What was the question? ###
1. I want you to give me a simple implementation of:
	Find in a file the 10 most used words?

    Solution 1 use list:
        Create a WordContainer class with workd/count
        Create a ListWordSearch class used to load the data from the file
            List<WordContainer> container = Collections.synchronizedList(new ArrayList<>())
                That imply each time we iterate to the list we need to synchronize the block 
            The data will be insert in the container and when we add an element it costs 0(n)
            Each time we insert let's check if we have it if so increment count
            Otherwise insert with count at one.

            Sort the list in ascending order and extract the last 10 elements from the list 

    Solution 2 use map:
        Use Map and LinkedHashMap

2. Now that you got it: 
    I want you to read the file only once?

        Already done, with the list solution

        Already done, with the Map solution

3. What we have done is not efficient: 
        Can we improve the performance by using CPU power using multi thread? 

        What ever we do, the multi threading will read the file on the disk. 
        The I/O in a disk are always sequential !!!

### Who do I talk to? ###
* Community: Those who wants to learn.

### Todo ###
* Improve explanations
