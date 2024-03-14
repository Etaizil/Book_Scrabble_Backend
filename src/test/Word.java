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
    for (int i = 0; i < tiles.length; i++) if (!tiles[i].equals(word.tiles[i])) return false;
    return true;
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
    return tiles.clone();
  }
}
