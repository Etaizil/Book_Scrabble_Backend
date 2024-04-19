package test;

import java.util.HashMap;

/*
@created 19/03/2024 - 17:11
@project PTM1 Project
@author  Zilberman Etai
*/
public class LRU implements CacheReplacementPolicy { // Oldest element searched is removed
  private final int maxSize = 400;
  private HashMap<String, Node> cache;
  private DoublyLinkedList lruList;

  public LRU() {
    this.cache = new HashMap<String, Node>();
    this.lruList = new DoublyLinkedList();
  }

  @Override
  public void add(String word) {
    if (cache.containsKey(word)) { // move the node to be the new head
      Node node = cache.get(word);
      lruList.removeNode(node);
      lruList.addToHead(node);
    } else {
      if (cache.size() == maxSize) {
        remove();
      }
      Node newNode = new Node(word);
      lruList.addToHead(newNode); // ok because we check if the cache is full
      cache.put(word, newNode);
    }
  }

  @Override
  public String remove() {
    Node lruWord = lruList.removeTail(); // delete from list
    cache.remove(lruWord.word); // delete from hash map
    return lruWord.word; // return the lru word
  }

  public static class DoublyLinkedList {
    private Node head;
    private Node tail;

    public DoublyLinkedList() {
      this.head = null;
      this.tail = null;
    }

    public void addToHead(Node node) {
      if (head == null) {
        head = node;
        tail = node;
      } else {
        node.next = head;
        head.prev = node;
        head = node;
      }
    }

    public Node removeTail() {
      Node node = tail;
      if (node != null) {
        removeNode(node);
        if (node == head) { // If the list becomes empty
          head = null;
          tail = null;
        }
      }
      return node;
    }

    private void removeNode(Node node) {
      if (node.prev != null) {
        node.prev.next = node.next;
      } else {
        head = node.next;
      }
      if (node.next != null) {
        node.next.prev = node.prev;
      } else {
        tail = node.prev;
      }
    }
  }

  public static class Node {
    String word;
    Node prev;
    Node next;

    public Node(String word) {
      this.word = word;
    }
  }
}
