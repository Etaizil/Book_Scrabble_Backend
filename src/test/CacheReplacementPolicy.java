package test;

/*
@created 19/03/2024 - 17:08
@project PTM1 Project
@author  Zilberman Etai
*/
public interface CacheReplacementPolicy {
  void add(String word);

  String remove();
}
