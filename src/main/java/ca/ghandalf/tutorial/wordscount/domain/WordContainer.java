package ca.ghandalf.tutorial.wordscount.domain;

public class WordContainer implements Cloneable {

  private String word;
  private int count = 0;

  public WordContainer(String word) {
    this.word = word;
  }
  
  public WordContainer(String word, int count) {
    super();
    this.word = word;
    this.count = count;
  }

  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }
    if (!(object instanceof WordContainer)) {
      return false;
    }
    
    WordContainer that = (WordContainer) object;
    
    return this.getWord().equals(that.getWord());
  }
  
  
  @Override
  public WordContainer clone() throws CloneNotSupportedException {
    WordContainer wordContainerCloned = (WordContainer) super.clone();
    wordContainerCloned.setWord(new String(this.getWord()));
    wordContainerCloned.setCount(this.getCount());
    
    return wordContainerCloned;
  }

  public String getWord() {
    return word;
  }

  public void setWord(String word) {
    this.word = word;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }
  
  @Override
  public String toString() {
    return // "[" + this.getClass().getSimpleName() + "] " +
//        "word: " + this.getWord() + ", count: " + this.getCount();
    this.getWord() + ":" + this.getCount();
  }

}
