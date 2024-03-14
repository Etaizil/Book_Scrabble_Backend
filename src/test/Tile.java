/*
@created 12/03/2024 - 21:29
@project PTM1 Project
@author Zilberman Etai
*/
package test;

import java.util.Objects;
import java.util.Random;

/** Represents a Tile. Each Tile has a letter and a score associated with it. */
final class Tile {
  private final char letter;
  private final int score;

  /**
   * Constructs a new Tile with the specified letter and score.
   *
   * @param letter The letter for this Tile.
   * @param score The score for this Tile.
   */
  private Tile(char letter, int score) {
    if (letter < 'A' || letter > 'Z')
      throw new IllegalArgumentException("letter must be a capital letter");
    this.letter = letter;
    this.score = score;
  }

  public Tile(Tile other) {
    this.letter = other.letter;
    this.score = other.score;
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

  /** Represents a Bag of Tiles. The Bag contains a specific quantity of each Tile. */
  public static class Bag {
    private static Bag bag = null;
    private static final int[] MAX_QUANTITIES = {
      9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
    }; // Maximum quantities of each letter
    private int[] quantities = MAX_QUANTITIES.clone(); // Current quantities of each letter
    private final Tile[] tiles;
    private static Random random = new Random();

    /** Constructs a new Bag. The Bag contains a specific quantity of each Tile. */
    private Bag() {
      tiles = new Tile[quantities.length]; // Array of tiles
      tiles[0] = new Tile('A', 1);
      tiles[1] = new Tile('B', 3);
      tiles[2] = new Tile('C', 3);
      tiles[3] = new Tile('D', 2);
      tiles[4] = new Tile('E', 1);
      tiles[5] = new Tile('F', 4);
      tiles[6] = new Tile('G', 2);
      tiles[7] = new Tile('H', 4);
      tiles[8] = new Tile('I', 1);
      tiles[9] = new Tile('J', 8);
      tiles[10] = new Tile('K', 5);
      tiles[11] = new Tile('L', 1);
      tiles[12] = new Tile('M', 3);
      tiles[13] = new Tile('N', 1);
      tiles[14] = new Tile('O', 1);
      tiles[15] = new Tile('P', 3);
      tiles[16] = new Tile('Q', 10);
      tiles[17] = new Tile('R', 1);
      tiles[18] = new Tile('S', 1);
      tiles[19] = new Tile('T', 1);
      tiles[20] = new Tile('U', 1);
      tiles[21] = new Tile('V', 4);
      tiles[22] = new Tile('W', 4);
      tiles[23] = new Tile('X', 8);
      tiles[24] = new Tile('Y', 4);
      tiles[25] = new Tile('Z', 10);
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
    public int getRandomInt() {
      int index;
      do {
        index = random.nextInt(quantities.length);
      } while (quantities[index] == 0);
      return index;
    }

    /**
     * This method returns a random tile from the bag. If the bag is empty, it returns null.
     *
     * @return a random tile from the bag or null if the bag is empty.
     */
    public Tile getrand() {
      if (tiles.length == 0) return null;
      int index = getRandomInt();
      quantities[index]--;
      return tiles[index];
    }

    /**
     * This method returns a specific tile from the bag. If the index is empty, it returns null.
     *
     * @return a specific tile from the bag or null if the index is empty.
     */
    public Tile getTile(char c) {
      if (c < 'A' || c > 'Z') return null;
      int index = c - 'A';
      if (quantities[index] == 0) return null;
      quantities[index]--;
      return tiles[index];
    }

    /**
     * This method returns a specific tile from the bag. If the index is empty, it returns.
     *
     * @return a specific tile from the bag or null if the index is empty.
     */
    public void put(Tile t) {
      if (t == null) return;
      int index = t.letter - 'A';
      if (index < 0 || index >= quantities.length || quantities[index] >= MAX_QUANTITIES[index])
        return;
      quantities[index]++;
    }

    /**
     * This method sums the quantities of all the tiles in the bag.
     *
     * @return the size of the bag.
     */
    public int size() {
      int size = 0;
      for (int i = 0; i < quantities.length; i++) {
        size += quantities[i];
      }
      return size;
    }

    /** This method returns a copy of the quantities array. */
    public int[] getQuantities() {
      return quantities.clone();
    }
  }
}
