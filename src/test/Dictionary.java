package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
@created 19/03/2024 - 17:09
@project PTM1 Project
@author  Zilberman Etai
*/

/** Dictionary class. Responsible for managing the dictionary and the cache managers. */
public class Dictionary {
  String[] stories;
  CacheManager lruCM; // Existing words
  CacheManager lfuCM; // Non-existing words
  BloomFilter bf;

  public Dictionary(String... fileNames) {
    stories = fileNames;
    lruCM = new CacheManager(400, new LRU()); // For existing words
    lfuCM = new CacheManager(100, new LFU()); // For non-existing words
    bf = new BloomFilter(256, "SHA1", "MD5");
    for (String fileName : fileNames) {
      try {
        Scanner scanner = new Scanner(new File(fileName));
        while (scanner.hasNext()) {
          bf.add(scanner.next());
        }
        scanner.close();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Query the dictionary.
   *
   * @param word The word to query.
   * @return True if the word exists in the dictionary, false otherwise.
   */
  public boolean query(String word) {
    try {
      if (lruCM.query(word)) {
        lruCM.add(word);
        return true;
      } else if (lfuCM.query(word)) {
        lfuCM.add(word);
        return false;
      } else {
        if (!bf.contains(word)) {
          lfuCM.add(word);
          return false;
        } else {
          // Check the stories
          if (IOSearcher.search(word, stories)) {
            lruCM.add(word);
            return true;
          } else {
            lfuCM.add(word);
            return false;
          }
        }
      }
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * A player may call for a challenge on a word that he think exists in the dictionary. The
   * dictionary returns whether the word exists in the books or not.
   *
   * @param word The word to challenge.
   * @return True if the word exists in the dictionary, false otherwise.
   */
  public boolean challenge(String word) {
    if (IOSearcher.search(word, stories)) {
      lruCM.add(word);
      return true;
    } else {
      lfuCM.add(word);
      return false;
    }
  }
}
