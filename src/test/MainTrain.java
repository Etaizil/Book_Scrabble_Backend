package test;

import test.MainTrain.ClientHandler1;
import test.Tile.Bag;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class MainTrain {

  /*
  @created 12/03/2024 - 21:31
  @project PTM1 Project
  @author etai4
  */

  public static void testBag() {
    Bag b = Tile.Bag.getBag();
    Bag b1 = Tile.Bag.getBag();
    if (b1 != b) System.out.println("your Bag in not a Singleton (-5)");

    int[] q0 = b.getQuantities();
    q0[0] += 1;
    int[] q1 = b.getQuantities();
    if (q0[0] != q1[0] + 1) System.out.println("getQuantities did not return a clone (-5)");

    for (int k = 0; k < 9; k++) {
      int[] qs = b.getQuantities();
      Tile t = b.getRand();
      int i = t.letter - 'A';
      int[] qs1 = b.getQuantities();
      if (qs1[i] != qs[i] - 1) System.out.println("problem with getRand (-1)");

      b.put(t);
      b.put(t);
      b.put(t);

      if (b.getQuantities()[i] != qs[i]) System.out.println("problem with put (-1)");
    }

    if (b.getTile('a') != null || b.getTile('$') != null || b.getTile('A') == null)
      System.out.println("your getTile is wrong (-2)");
  }

  private static Tile[] get(String s) {
    Tile[] ts = new Tile[s.length()];
    int i = 0;
    for (char c : s.toCharArray()) {
      ts[i] = Bag.getBag().getTile(c);
      i++;
    }
    return ts;
  }

  public static void testBoard() {
    Board b = Board.getBoard();
    if (b != Board.getBoard()) System.out.println("board should be a Singleton (-5)");

    Bag bag = Bag.getBag();
    Tile[] ts = new Tile[10];
    for (int i = 0; i < ts.length; i++) ts[i] = bag.getRand();

    Word w0 = new Word(ts, 0, 6, true);
    Word w1 = new Word(ts, 7, 6, false);
    Word w2 = new Word(ts, 6, 7, true);
    Word w3 = new Word(ts, -1, 7, true);
    Word w4 = new Word(ts, 7, -1, false);
    Word w5 = new Word(ts, 0, 7, true);
    Word w6 = new Word(ts, 7, 0, false);

    if (b.boardLegal(w0)
        || b.boardLegal(w1)
        || b.boardLegal(w2)
        || b.boardLegal(w3)
        || b.boardLegal(w4)
        || !b.boardLegal(w5)
        || !b.boardLegal(w6)) System.out.println("your boardLegal function is wrong (-10)");

    for (Tile t : ts) bag.put(t);

    Word horn = new Word(get("HORN"), 7, 5, false);
    if (b.tryPlaceWord(horn) != 14) System.out.println("problem in placeWord for 1st word (-10)");

    Word farm = new Word(get("FA_M"), 5, 7, true);
    int retscore = b.tryPlaceWord(farm);
    if (retscore != 9)
      System.out.println(
          "problem in placeWord for 2ed word (-10), returned " + retscore + " instead of 9");

    Word paste = new Word(get("PASTE"), 9, 5, false);
    if (b.tryPlaceWord(paste) != 25) System.out.println("problem in placeWord for 3ed word (-10)");

    Word mob = new Word(get("_OB"), 8, 7, false);
    if (b.tryPlaceWord(mob) != 18) System.out.println("problem in placeWord for 4th word (-10)");

    Word bit = new Word(get("BIT"), 10, 4, false);
    if (b.tryPlaceWord(bit) != 22) System.out.println("problem in placeWord for 5th word (-15)");
  }

  /*
  @created 19/03/2024 - 17:11
  @project PTM1 Project
  @author  Zilberman Etai
  */

  public static void testLRU() {
    CacheReplacementPolicy lru = new LRU();
    lru.add("a");
    lru.add("b");
    lru.add("c");
    lru.add("a");

    if (!lru.remove().equals("b")) System.out.println("wrong implementation for LRU (-10)");
  }

  public static void testLFU() {
    CacheReplacementPolicy lfu = new LFU();
    lfu.add("a");
    lfu.add("b");
    lfu.add("b");
    lfu.add("c");
    lfu.add("a");

    if (!lfu.remove().equals("c")) System.out.println("wrong implementation for LFU (-10)");
  }

  public static void testCacheManager() {
    CacheManager exists = new CacheManager(3, new LRU());
    boolean b = exists.query("a");
    b |= exists.query("b");
    b |= exists.query("c");

    if (b) System.out.println("wrong result for CacheManager first queries (-5)");

    exists.add("a");
    exists.add("b");
    exists.add("c");

    b = exists.query("a");
    b &= exists.query("b");
    b &= exists.query("c");

    if (!b) System.out.println("wrong result for CacheManager second queries (-5)");

    boolean bf = exists.query("d"); // false, LRU is "a"
    exists.add("d");
    boolean bt = exists.query("d"); // true
    bf |= exists.query("a"); // false
    exists.add("a");
    bt &= exists.query("a"); // true, LRU is "b"

    if (bf || !bt) System.out.println("wrong result for CacheManager last queries (-10)");
  }

  public static void testBloomFilter() {
    BloomFilter bf = new BloomFilter(256, "MD5", "SHA1");
    String[] words = "the quick brown fox jumps over the lazy dog".split(" ");
    for (String w : words) bf.add(w);

    if (!bf.toString()
        .equals(
            "0010010000000000000000000000000000000000000100000000001000000000000000000000010000000001000000000000000100000010100000000010000000000000000000000000000000110000100000000000000000000000000010000000001000000000000000000000000000000000000000000000000000001"))
      System.out.println("problem in the bit vector of the bloom filter (-10)");

    boolean found = true;
    for (String w : words) found &= bf.contains(w);

    if (!found)
      System.out.println("problem finding words that should exist in the bloom filter (-15)");

    found = false;
    for (String w : words) found |= bf.contains(w + "!");

    if (found)
      System.out.println("problem finding words that should not exist in the bloom filter (-15)");
  }

  public static void testIOSearch() throws Exception {
    String words1 = "the quick brown fox \n jumps over the lazy dog";
    String words2 =
        "A Bloom filter is a space efficient probabilistic data structure, \n conceived by Burton Howard Bloom in 1970";
    PrintWriter out = new PrintWriter(new FileWriter("text1.txt"));
    out.println(words1);
    out.close();
    out = new PrintWriter(new FileWriter("text2.txt"));
    out.println(words2);
    out.close();

    if (!IOSearcher.search("is", "text1.txt", "text2.txt"))
      System.out.println("your IOsearch did not find a word (-5)");
    if (IOSearcher.search("cat", "text1.txt", "text2.txt"))
      System.out.println("your IOsearch found a word that does not exist (-5)");
  }

  public static void testDictionary() {
    Dictionary d = new Dictionary("text1.txt", "text2.txt");
    if (!d.query("is")) System.out.println("problem with dictionary in query (-5)");
    if (!d.challenge("lazy")) System.out.println("problem with dictionary in query (-5)");
  }

  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////////////////////////////////

  /*
  @created 19/04/2024 - 19:20
  @project PTM1 Project
  @author  Zilberman Etai
  */

  public static class ClientHandler1 implements ClientHandler {
    PrintWriter out;
    Scanner in;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
      out = new PrintWriter(outToClient);
      in = new Scanner(inFromclient);
      String text = in.next();
      out.println(new StringBuilder(text).reverse().toString());
      out.flush();
    }

    @Override
    public void close() {
      in.close();
      out.close();
    }
  }

  public static void client1(int port) throws Exception {
    Socket server = new Socket("localhost", port);
    Random r = new Random();
    String text = "" + (1000 + r.nextInt(100000));
    String rev = new StringBuilder(text).reverse().toString();
    PrintWriter outToServer = new PrintWriter(server.getOutputStream());
    Scanner in = new Scanner(server.getInputStream());
    outToServer.println(text);
    outToServer.flush();
    String response = in.next();
    if (response == null || !response.equals(rev))
      System.out.println(
          "problem getting the right response from your server, cannot continue the test (-100)");
    in.close();
    outToServer.println(text);
    outToServer.close();
    server.close();
  }

  public static boolean testServer() {
    boolean ok = true;
    Random r = new Random();
    int port = 6000 + r.nextInt(1000);
    MyServer s = new MyServer(port, new ClientHandler1());
    int c = Thread.activeCount();
    s.start(); // runs in the background
    try {
      client1(port);
    } catch (Exception e) {
      System.out.println(
          "some exception was thrown while testing your server, cannot continue the test (-100)");
      ok = false;
    }
    s.close();

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
    }

    if (Thread.activeCount() != c) {
      System.out.println("you have a thread open after calling close method (-100)");
      ok = false;
    }
    return ok;
  }

  public static String[] writeFile(String name) {
    Random r = new Random();
    String txt[] = new String[10];
    for (int i = 0; i < txt.length; i++) txt[i] = "" + (10000 + r.nextInt(10000));

    try {
      PrintWriter out = new PrintWriter(new FileWriter(name));
      for (String s : txt) {
        out.print(s + " ");
      }
      out.println();
      out.close();
    } catch (Exception e) {
    }

    return txt;
  }

  public static void testDM() {
    String t1[] = writeFile("t1.txt");
    String t2[] = writeFile("t2.txt");
    String t3[] = writeFile("t3.txt");

    DictionaryManager dm = DictionaryManager.get();

    if (!dm.query("t1.txt", "t2.txt", t2[4]))
      System.out.println("problem for Dictionary Manager query (-5)");
    if (!dm.query("t1.txt", "t2.txt", t1[9]))
      System.out.println("problem for Dictionary Manager query (-5)");
    if (dm.query("t1.txt", "t3.txt", "2" + t3[2]))
      System.out.println("problem for Dictionary Manager query (-5)");
    if (dm.query("t2.txt", "t3.txt", "3" + t2[5]))
      System.out.println("problem for Dictionary Manager query (-5)");
    if (!dm.challenge("t1.txt", "t2.txt", "t3.txt", t3[2]))
      System.out.println("problem for Dictionary Manager challenge (-5)");
    if (dm.challenge("t2.txt", "t3.txt", "t1.txt", "3" + t2[5]))
      System.out.println("problem for Dictionary Manager challenge (-5)");

    if (dm.getSize() != 3) System.out.println("wrong size for the Dictionary Manager (-10)");
  }

  public static void runClient(int port, String query, boolean result) {
    try {
      Socket server = new Socket("localhost", port);
      PrintWriter out = new PrintWriter(server.getOutputStream());
      Scanner in = new Scanner(server.getInputStream());
      out.println(query);
      out.flush();
      String res = in.next();
      if ((result && !res.equals("true")) || (!result && !res.equals("false")))
        System.out.println("problem getting the right answer from the server (-10)");
      in.close();
      out.close();
      server.close();
    } catch (IOException e) {
      System.out.println("your code ran into an IOException (-10)");
    }
  }

  public static void testBSCH() {
    String s1[] = writeFile("s1.txt");
    String s2[] = writeFile("s2.txt");
    Random r = new Random();
    int port = 6000 + r.nextInt(1000);
    MyServer s = new MyServer(port, new BookScrabbleHandler());
    s.start();
    runClient(port, "Q,s1.txt,s2.txt," + s1[1], true);
    runClient(port, "Q,s1.txt,s2.txt," + s2[4], true);
    runClient(port, "Q,s1.txt,s2.txt,2" + s1[1], false);
    runClient(port, "Q,s1.txt,s2.txt,3" + s2[4], false);
    runClient(port, "C,s1.txt,s2.txt," + s1[9], true);
    runClient(port, "C,s1.txt,s2.txt,#" + s2[1], false);
    s.close();
  }

  public static void main(String[] args) {
    testBag(); // 30 points
    testBoard(); // 70 points
    System.out.println("done with stone 1");

    testLRU();
    testLFU();
    testCacheManager();
    testBloomFilter();
    try {
      testIOSearch();
    } catch (Exception e) {
      System.out.println("you got some exception (-10)");
    }
    testDictionary();
    System.out.println("done with stone 2");

    if (testServer()) {
      testDM();
      testBSCH();
      System.out.println("done with stone 3");
    }
    System.out.println("done");
  }
}
