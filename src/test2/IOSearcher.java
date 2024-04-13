package test2;

import java.io.File;

/*
@created 19/03/2024 - 17:09
@project PTM1 Project
@author  Zilberman Etai
*/
public class IOSearcher {
  private String[] files;

  public IOSearcher(String... fileNames) {
    this.files = fileNames;
  }

  public static boolean search(String word, String... fileNames) {
    try {
      // search in all files for the word.
      // if found, return true.
      // if not found, return false.
      // if there is an exception, throw
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
