package test;

import java.util.BitSet;
import java.security.MessageDigest;
import java.math.BigInteger;

/*
@created 19/03/2024 - 17:06
@project PTM1 Project
@author  Zilberman Etai
*/

public class BloomFilter {

  private final int arrSize;
  private BitSet bitSet;
  private String[] algsNames;

  /**
   * Bloom filter constructor.
   *
   * @param arrSize size of the bit set.
   * @param algsNames array of algorithm names for hashing.
   */
  public BloomFilter(int arrSize, String... algsNames) {
    this.arrSize = arrSize;
    bitSet = new BitSet(arrSize);
    this.algsNames = algsNames;
  }

  /** Add a word to the bit set. */
  public void add(String word) {
    for (String algName : algsNames) {
      try {
        MessageDigest md = MessageDigest.getInstance(algName);
        byte[] digest = md.digest(word.getBytes());
        BigInteger bigInt = new BigInteger(1, digest);
        int index = Math.abs(bigInt.intValue()) % arrSize;
        bitSet.set(index);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Check if the word is in the bit set.
   *
   * @param word word to check.
   * @return true if the word is in the bit set, false otherwise.
   */
  public boolean contains(String word) {
    for (String algName : algsNames) {
      try {
        MessageDigest md = MessageDigest.getInstance(algName);
        byte[] digest = md.digest(word.getBytes());
        BigInteger bigInt = new BigInteger(1, digest);
        int index = Math.abs(bigInt.intValue()) % arrSize;
        if (!bitSet.get(index)) {
          return false;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < arrSize; i++) {
      str.append(bitSet.get(i) ? "1" : "0");
    }
    return str.toString().replaceFirst("0+$", ""); // remove trailing zeros
  }
}
