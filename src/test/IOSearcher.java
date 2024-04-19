package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

  public static boolean search(String word, String... files) {
    try {
      // search in all files for the word.
      for (String fn : files) {
        if (fn == null) {
          throw new NullPointerException("File name is null");
        }
        BufferedReader bufferReader = new BufferedReader(new FileReader(fn));
        String line;
        while ((line = bufferReader.readLine()) != null) {
          if (line.contains(word)) {
            bufferReader.close();
            return true;
          }
        }
        bufferReader.close();
      }
      return false;
    } catch (IOException ioE) {
      ioE.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}
