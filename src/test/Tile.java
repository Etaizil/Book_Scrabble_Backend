/*
 * @created 12/03/2024 - 21:29
 * @project PTM1 Project
 * @author Zilberman Etai
 */
package test;

import java.security.SecureRandom;
import java.util.Objects;

/** Represents a Tile. Each Tile has a letter and a score associated with it. */
public class Tile {

  /** Represents the letter of the tile. */
  public final char letter;

  /** Represents the score of the tile. */
  public final int score;

  /** A flag to indicate if the tile was originally blank. */
  public boolean originallyBlank = false;

  /**
   * Constructs a new Tile with the specified letter and score.
   *
   * @param letter The letter for this Tile.
   * @param score The score for this Tile.
   */
  private Tile(char letter, int score) { // MUST BE PRIVATE
    if (letter == '_') {
      this.originallyBlank = true;
      this.letter = '_';
      this.score = 0;
      return;
    }
    if (letter < 'A' || letter > 'Z')
      throw new IllegalArgumentException("letter must be a capital letter");
    this.letter = letter;
    this.score = score;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tile tile = (Tile) o;
    return letter == tile.letter && score == tile.score;
  }

  @Override
  public int hashCode() {
    return Objects.hash(letter, score);
  }

  @Override
  public String toString() {
    // Assuming the Tile class has a field named 'letter' of type char
    return String.valueOf(this.letter);
  }

  /**
   * @return the score of the tile.
   */
  public int getScore() {
    return score;
  }

  /**
   * @return the letter of the tile.
   */
  public char getLetter() {
    return letter;
  }

  /**
   * This method returns a DEEP copy of the tile.
   *
   * @return a DEEP copy of the tile.
   */
  public Tile copy() {
    Tile copy = new Tile(this.letter, this.score);
    copy.originallyBlank = this.originallyBlank;
    return copy;
  }

  /** Represents a Bag of Tiles. The Bag contains a specific quantity of each Tile. */
  public static class Bag {

    /** The Bag instance for the game. */
    private static Bag bag = null;

    /** The maximum quantities of each letter */
    private static final int[] MAX_QUANTITIES = {
      9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
    };

    /**
     * The scores of each letter. The score of the letter A is at index 0, the score of the letter B
     * is at index 1, and so on.
     */
    private final int[] scores = {
      1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10
    };

    /** The current quantities of each letter */
    private int[] quantities = MAX_QUANTITIES.clone(); // Current quantities of each letter

    /** An array of tiles */
    private final Tile[] tiles;

    /** A secure random number generator */
    private static SecureRandom random = new SecureRandom();

    /** Constructs a new Bag. The Bag contains a specific quantity of each Tile. */
    public Bag() {
      tiles = new Tile[quantities.length]; // Array of tiles
      for (int i = 0; i < quantities.length; i++) {
        char letter = (char) ('A' + i);
        tiles[i] = new Tile(letter, scores[i]);
      }
    }

    /**
     * Returns the Bag instance. The first player to call the method creates the bag.
     *
     * @return the Bag instance.
     */
    public static Bag getBag() {
      if (bag == null) {
        bag = new Bag();
      }
      return bag;
    }

    /**
     * A helper function for getrand. It returns a random index of a non-empty tile.
     *
     * @return a random index of a non-empty tile.
     */
    private int getRandomInt() {
      int index;
      do {
        index = random.nextInt(quantities.length);
      } while (quantities[index] == 0);
      return index;
    }

    /**
     * @return a random tile from the bag or null if the bag is empty.
     */
    public Tile getRand() {
      if (size() == 0) return null;
      int index = getRandomInt();
      if (quantities[index] == 0) return null;
      quantities[index]--;
      return tiles[index];
    }

    /**
     * This method returns a specific tile from the bag. If the index is empty, it returns null.
     * Might need to handle concurrent access from multiple threads (Later on this course).
     *
     * @param c the letter of the tile to get.
     * @return a specific tile from the bag or null if the index is empty.
     */
    public Tile getTile(char c) {
      if (c == '_') {
        // Special case for blank tiles
        return new Tile('_', 0);
      }
      if (c < 'A' || c > 'Z') return null;
      int index = c - 'A';
      if (quantities[index] == 0) return null;
      quantities[index]--;
      return tiles[index];
    }

    /**
     * This method put a specific tile in the bag.
     *
     * @param t the tile to put in the bag.
     */
    public void put(Tile t) {
      if (t == null) return;
      if (t.letter < 'A' || t.letter > 'Z') return;
      int index = t.letter - 'A';
      if (index >= quantities.length || quantities[index] >= MAX_QUANTITIES[index]) return;
      quantities[index]++;
    }

    /**
     * This method sums the quantities of all the tiles in the bag.
     *
     * @return the size of the bag.
     */
    public int size() {
      int size = 0;
      for (int quantity : quantities) {
        size += quantity;
      }
      return size;
    }

    /**
     * This method returns a copy of the quantities array.
     *
     * @return quantities array
     */
    public int[] getQuantities() {
      return quantities.clone();
    }
  }
}
