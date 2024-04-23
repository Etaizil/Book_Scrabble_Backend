package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/*
@created 20/04/2024 - 20:11
@project PTM1 Project
@author  Zilberman Etai
*/

/** Responsible for handling the client's request for the BookScrabble game. */
public class BookScrabbleHandler implements ClientHandler {

  /**
   * Handles the client's request for the BookScrabble game. It reads the client's request for a
   * query ("Q") or a challenge ("C"), the book names and the requested word, all separated by
   * commas. It then sends true or false to the client.
   *
   * @param inFromclient The input stream from the client.
   * @param outToClient Answer to the client, true or false.
   */
  @Override
  public void handleClient(InputStream inFromclient, OutputStream outToClient) {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(inFromclient));
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outToClient));
      DictionaryManager dictionaryManager = DictionaryManager.get();
      String line = reader.readLine();
      String[] words = line.split(",");
      if (words.length < 3) {
        return;
      }
      String queryOrChallenge = words[0];
      String[] bookNames = new String[words.length - 2];
      System.arraycopy(words, 1, bookNames, 0, words.length - 2);
      String queryWord = words[words.length - 1];
      boolean result;
      String[] queryArgs = new String[bookNames.length + 1];
      System.arraycopy(bookNames, 0, queryArgs, 0, bookNames.length);
      queryArgs[bookNames.length] = queryWord;
      if (queryOrChallenge.equals("Q")) {
        result = dictionaryManager.query(queryArgs);
      } else if (queryOrChallenge.equals("C")) {
        result = dictionaryManager.challenge(queryArgs);
      } else {
        writer.write("false\n");
        writer.flush();
        return;
      }
      writer.write(result ? "true\n" : "false\n");
      writer.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void close() {}
}
