package test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
@created 19/03/2024 - 17:10
@project PTM1 Project
@author  Zilberman Etai
*/

/**
 * Least Frequently Used cache replacement policy implementation. Evicts the least frequently used
 * item. Used for non-existing words in the dictionary.
 */
public class LFU implements CacheReplacementPolicy {

  private final int capacity = 400;
  private Map<Integer, DoubleLinkedList> freqMap;
  private Map<String, Node> cache;
  private int minFreq = 0;

  public LFU() {
    this.cache = new HashMap<>();
    this.freqMap = new HashMap<>();
  }

  @Override
  public void add(String word) {
    if (capacity <= 0) return;
    Node node = cache.get(word);
    if (node != null) {
      updateFrequency(node);
    } else {
      if (cache.size() >= capacity) {
        evictLFU();
      }
      node = new Node(word);
      cache.put(word, node);
      addToFreqMap(node);
      minFreq = 1;
    }
  }

  @Override
  public String remove() {
    if (cache.isEmpty()) return null;
    DoubleLinkedList list = freqMap.get(minFreq);
    Node lfuNode = list.removeLast();
    cache.remove(lfuNode.word);
    if (list.isEmpty()) {
      freqMap.remove(minFreq);
      if (minFreq == lfuNode.freq) {
        minFreq++; // If the removed node was the only one with minFreq, increment minFreq
      }
    }
    return lfuNode.word;
  }

  private void updateFrequency(Node node) {
    int freq = node.freq;
    DoubleLinkedList oldList = freqMap.get(freq);
    oldList.remove(node);
    if (freq == minFreq && oldList.isEmpty()) {
      minFreq++; // If the node was the only one with minFreq, increment minFreq
    }
    node.freq++;
    DoubleLinkedList newList = freqMap.computeIfAbsent(node.freq, k -> new DoubleLinkedList());
    newList.addFirst(node);
  }

  private void evictLFU() {
    DoubleLinkedList list = freqMap.get(minFreq);
    if (list == null) return;
    Node lfuNode = list.removeLast();
    if (lfuNode == null) return;
    cache.remove(lfuNode.word);
    if (list.isEmpty()) {
      freqMap.remove(minFreq);
    }
  }

  private void addToFreqMap(Node node) {
    DoubleLinkedList list = freqMap.computeIfAbsent(1, k -> new DoubleLinkedList());
    list.addFirst(node);
  }

  private static class Node {
    String word;
    int freq;
    Node prev;
    Node next;

    Node(String word) {
      this.word = word;
      this.freq = 1;
      this.prev = null;
      this.next = null;
    }
  }

  private static class DoubleLinkedList {
    Node head;
    Node tail;

    DoubleLinkedList() {
      head = new Node(null);
      tail = new Node(null);
      head.next = tail;
      tail.prev = head;
    }

    void addFirst(Node node) {
      node.next = head.next;
      node.prev = head;
      head.next.prev = node;
      head.next = node;
    }

    Node removeLast() {
      if (isEmpty()) return null;
      Node last = tail.prev;
      remove(last);
      return last;
    }

    void remove(Node node) {
      node.prev.next = node.next;
      node.next.prev = node.prev;
    }

    boolean isEmpty() {
      return head.next == tail;
    }
  }
}
