package test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/*
@created 19/03/2024 - 17:11
@project PTM1 Project
@author  Zilberman Etai
*/

/**
 * Least Recently Used cache replacement policy implementation. Evicts the least recently used item.
 * Used for existing words in the dictionary.
 */
public class LRU implements CacheReplacementPolicy {
  private final int maxSize = 400;
  private Map<String, Node> map;
  private LinkedList<Node> lruList;

  public LRU() {
    this.map = new HashMap<>(maxSize);
    this.lruList = new LinkedList<>();
  }

  @Override
  public void add(String word) {
    if (map.containsKey(word)) { // move the node to be the new head
      Node currentNode = map.get(word);
      lruList.remove(currentNode); // remove from the list
      lruList.addFirst(currentNode); // add to the head
    } else {
      if (map.size() == maxSize) {
        remove();
      }
      Node newNode = new Node(word);
      lruList.addFirst(newNode);
      map.put(word, newNode);
    }
  }

  @Override
  public String remove() {
    Node lruWord = lruList.removeLast(); // remove the last element (lru element)
    map.remove(lruWord.word); // delete from hash map
    return lruWord.word; // return the lru word
  }

  public static class Node {
    String word;

    public Node(String word) {
      this.word = word;
    }
  }
}
