package test2;

/*
@created 19/03/2024 - 17:09
@project PTM1 Project
@author  Zilberman Etai
*/
public class Dictionary {

  public Dictionary(String... fileNames) {
    CacheManager lruCM = new CacheManager(400, new LRU()); // For existing words
    CacheManager lfuCM = new CacheManager(100, new LFU()); // For non-existing words
    BloomFilter bf = new BloomFilter(256, "SHA1", "MD5");
    for (String fileName : fileNames) { // CHECK FOR WEEK 7 - WORKING WITH FILES
      bf.add(fileName);
    }
  }

  public query(String word) {}

  public challenge(String word) {
    // RETURN IO SEARCH ANSWER , UPDATE CACHE, , IN EXCEPTION RETURN FALSE
  }
}
