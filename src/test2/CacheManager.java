package test2;

import java.util.HashSet;

/*
@created 19/03/2024 - 17:08
@project PTM1 Project
@author  Zilberman Etai
*/
public class CacheManager {
  public final int size;
  CacheReplacementPolicy crp;
  HashSet<String> cache;

  public CacheManager(int size, CacheReplacementPolicy crp) {
    this.size = size;
    this.crp = crp;
  }

  public boolean query(String word) { // is the word in the cache
    return cache.contains(word);
  }

  public void add(String word) { // adds the word to the cache
    if (cache.size() == size) {
      String removed = crp.remove();
      cache.remove(removed);
    }
    crp.add(word);
    cache.add(word);
  }
}
