package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
@created 19/03/2024 - 17:09
@project PTM1 Project
@author  Zilberman Etai
*/

/** Search for a word in a list of files. */
public class IOSearcher {
  private String[] files;

  public IOSearcher(String... fileNames) {
    this.files = fileNames;
  }

  /**
   * Search for a word in the files.
   *
   * @param word
   * @param files
   * @return true if the word is found in the files, false otherwise.
   */
  public static boolean search(String word, String... files) {
    try {
      if (word == null) {
        throw new NullPointerException("Word is null");
      }
      for (String fn : files) {
        if (fn == null) {
          throw new NullPointerException("File name is null");
        }
        BufferedReader bufferReader = new BufferedReader(new FileReader(fn));
        String line;
        while ((line = bufferReader.readLine()) != null) {
          String[] words = line.split("\\s+"); // Split by space
          for (String w : words) {
            if (w.equals(word)) {
              return true;
            }
          }
        }
        bufferReader.close();
      }
    } catch (IOException ioE) {
      ioE.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return false;
  }
}
