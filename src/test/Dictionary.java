package test;

/*
@created 19/03/2024 - 17:09
@project PTM1 Project
@author  Zilberman Etai
*/
public class Dictionary {
  String[] stories;
  CacheManager lruCM;
  CacheManager lfuCM;
  BloomFilter bf;

  public Dictionary(String... fileNames) {
    stories = fileNames;
    lruCM = new CacheManager(400, new LRU()); // For existing words
    lfuCM = new CacheManager(100, new LFU()); // For non-existing words
    bf = new BloomFilter(256, "SHA1", "MD5");
    for (String fileName : fileNames) { // CHECK FOR WEEK 7 - WORKING WITH FILES
      bf.add(fileName);
    }
  }

  public boolean query(String word) {
    // CHECK LRU,then LFU THEN BF , UPDATE CACHE ACCORDINGLY after the search, IF EXCEPTION RETURN
    // FALSE, all in try catch
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

        } else {
          lruCM.add(word);
        }
        return true;
      }
    } catch (Exception e) {
      return false;
    }
  }

  public boolean challenge(String word) {
    // RETURN IO SEARCH ANSWER , UPDATE CACHE, , IN EXCEPTION RETURN FALSE
    if (IOSearcher.search(word, stories)) {
      lruCM.add(word);
      return true;
    } else {
      lfuCM.add(word);
      return false;
    }
  }
}
