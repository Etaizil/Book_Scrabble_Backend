package test2;

/*
@created 19/03/2024 - 17:10
@project PTM1 Project
@author  Zilberman Etai
*/
public class LFU implements CacheReplacementPolicy { // remove the one we searched for the least

  @Override
  public void add(String word) {}

  @Override
  public String remove() {
    return "";
  }
}
