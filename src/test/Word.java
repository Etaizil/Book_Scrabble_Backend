/*
@created 12/03/2024 - 21:30
@project PTM1 Project
@author Zilberman Etai
*/

package test;

/** Represents a Word. A Word is a sequence of Tiles. */
public class Word {
  private final Tile[] tiles;
  private final int row, col;
  private final boolean vertical;

  /**
   * Constructs a new Word with the specified tiles, row, column and direction.
   *
   * @param tiles The tiles of the word.
   * @param row The row of the first letter of the word.
   * @param col The column of the first letter of the word.
   * @param vertical true if the word is vertical, false otherwise.
   */
  public Word(Tile[] tiles, int row, int col, boolean vertical) {
    if (tiles == null) throw new IllegalArgumentException("tiles cannot be null");
    for (Tile tile : tiles) {
      if (tile == null) throw new IllegalArgumentException("tiles cannot contain null values");
    }
    this.tiles = tiles;
    this.row = row;
    this.col = col;
    this.vertical = vertical;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Word word = (Word) o;
    if (row != word.row || col != word.col || vertical != word.vertical) return false;
    for (int i = 0; i < tiles.length; i++) {
      if (this.tiles[i] != word.tiles[i]) return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder word = new StringBuilder();
    for (Tile tile : this.getTiles()) {
      word.append(tile.getLetter());
    }
    return word.toString();
  }

  public Word(Word original) {
    this.row = original.row;
    this.col = original.col;
    this.vertical = original.vertical;
    this.tiles = new Tile[original.tiles.length];
    for (int i = 0; i < original.tiles.length; i++) {
      this.tiles[i] = original.tiles[i].copy(); // assuming Tile class has a copy method
    }
  }

  public void setBlankValue(int index, char value) {
    if (index < 0 || index >= tiles.length)
      throw new IllegalArgumentException("index out of bounds");
    Tile tile = tiles[index];
    tile.blankValue = value;
  }

  /**
   * getter for row
   *
   * @return the row of the first letter of the word.
   */
  public int getRow() {
    return row;
  }

  /**
   * getter for col
   *
   * @return the column of the first letter of the word.
   */
  public int getCol() {
    return col;
  }

  /**
   * getter for vertical
   *
   * @return true if the word is vertical, false otherwise.
   */
  public boolean isVertical() {
    return vertical;
  }

  public Tile[] getTiles() {
    Tile[] copy = new Tile[tiles.length];
    for (int i = 0; i < tiles.length; i++) {
      copy[i] = tiles[i].copy();
    }
    return copy;
  }

  //  public void printWord() {
  //    for (Tile t : tiles) {
  //      if (t != null) {
  //        System.out.print(t.getLetter());
  //      } else {
  //        System.out.print("_"); // print a default value for null tiles
  //      }
  //    }
  //    System.out.println();
  //  }
}
