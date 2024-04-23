package test;

import java.util.HashMap;
import java.util.Map;

/*
@created 19/04/2024 - 19:18
@project PTM1 Project
@author  Zilberman Etai
*/

/**
 * A class that manages a collection of dictionaries. It can query a word in a dictionary, or
 * challenge a word in a dictionary.
 */
public class DictionaryManager {

  public Map<String, Dictionary> booksMap;
  public static DictionaryManager DM = null;

  public DictionaryManager() {
    booksMap = new HashMap<>();
  }

  /**
   * @return the number of books in the collection
   */
  public int getSize() {
    return booksMap.size();
  }

  /**
   * @return a singleton instance of the DictionaryManager
   */
  public static DictionaryManager get() {
    if (DM == null) {
      DM = new DictionaryManager();
    }
    return DM;
  }

  /**
   * @param args - The first n-1 elements are the names of the books, the last element is the word
   *     to query.
   * @return true if the word is found in any of the books
   */
  public boolean query(String... args) {
    String word = args[args.length - 1];
    boolean found = false;
    for (int i = 0; i < args.length - 1; i++) {
      String book = args[i];
      Dictionary dictionary = booksMap.get(book);
      if (dictionary == null) {
        booksMap.put(book, new Dictionary(book));
      }
      found = found || booksMap.get(book).query(word); // If found in one book, will return true
    }
    return found;
  }

  /**
   * @param args - The first n-1 elements are the names of the books, the last element is the word
   *     to challenge.
   * @return true if the word is found in at least one of the books
   */
  public boolean challenge(String... args) {
    String word = args[args.length - 1];
    boolean found = false;
    for (int i = 0; i < args.length - 1; i++) {
      String book = args[i];
      Dictionary dictionary = booksMap.get(book);
      if (dictionary == null) {
        booksMap.put(book, new Dictionary(book));
      }
      found = found || booksMap.get(book).challenge(word); // If found in one book, will return true
    }
    return found;
  }
}
