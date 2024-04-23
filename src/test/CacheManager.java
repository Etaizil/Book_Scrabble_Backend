package test;

import java.util.HashSet;

/*
@created 19/03/2024 - 17:08
@project PTM1 Project
@author  Zilberman Etai
*/

/** This class is responsible for managing the cache. */
public class CacheManager {
  public final int size;
  CacheReplacementPolicy crp;
  HashSet<String> cache;

  /**
   * @param size the size of the cache
   * @param crp the cache replacement policy
   */
  public CacheManager(int size, CacheReplacementPolicy crp) {
    this.size = size;
    this.crp = crp;
    this.cache = new HashSet<>();
  }

  /** This method is responsible for querying the cache. */
  public boolean query(String word) {
    return cache.contains(word);
  }

  /** This method is responsible for adding a word to the cache. */
  public void add(String word) {
    if (cache.size() == size) {
      String removed = crp.remove();
      cache.remove(removed);
    }
    crp.add(word);
    cache.add(word);
  }
}
