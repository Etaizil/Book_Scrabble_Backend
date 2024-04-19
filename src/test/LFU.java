package test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/*
@created 19/03/2024 - 17:10
@project PTM1 Project
@author  Zilberman Etai
*/
public class LFU implements CacheReplacementPolicy { // remove the one we searched for the least

  private final int maxSize = 3;
  private HashMap<String, Node> cache;
  private PriorityQueue<Node> queue;

  public LFU() {
    this.cache = new HashMap<>();
    this.queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.frequency));
  }

  @Override
  public void add(String word) {
    if (!cache.containsKey(word)) {
      if (cache.size() == maxSize) {
        Node leastFreq = queue.poll();
        if (leastFreq != null) {
          cache.remove(leastFreq.word);
        }
      }
      Node node = new Node(word);
      cache.put(word, node);
      queue.add(node);
    } else {
      Node node = cache.get(word);
      queue.remove(node);
      node.frequency++;
      queue.add(node);
    }
  }

  @Override
  public String remove() {
    Node leastFreq = queue.poll();
    if (leastFreq != null) {
      cache.remove(leastFreq.word);
      return leastFreq.word;
    }
    return null;
  }

  private static class Node {
    String word;
    int frequency;

    public Node(String word) {
      this.word = word;
      this.frequency = 1;
    }
  }
}
