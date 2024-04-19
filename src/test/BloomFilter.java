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
  String[] algsNames;

  public BloomFilter(int arrSize, String... algsNames) {
    this.arrSize = arrSize;
    bitSet = new BitSet(arrSize);
    this.algsNames = algsNames;
  }

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
    return str.toString().replaceFirst("0+$", "");
  }
}
