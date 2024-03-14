/*
@created 12/03/2024 - 21:29
@project PTM1 Project
@author Zilberman Etai
*/

package test;

import java.util.ArrayList;

public class Board {
  private static Board board = null;
  private Tile[][] boardTiles = new Tile[15][15];

  private Board() {}

  /**
   * The first player to play will create the board. If already created, return the singleton
   *
   * @return the singleton instance of the board.
   */
  public static Board getBoard() {
    if (board == null) {
      board = new Board();
    }
    return board;
  }

  /**
   * returns the tiles of the board.
   *
   * @return 2D array of the tiles on the board.
   */
  public Tile[][] getTiles() {
    Tile[][] tiles = new Tile[15][15];
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (boardTiles[i][j] != null) {
          tiles[i][j] = new Tile(boardTiles[i][j]);
        }
      }
    }
    return tiles;
  }

  /**
   * Check if the given word is on the star tile.
   *
   * @param word The word to check.
   * @return True if the word is on the star tile, false otherwise.
   */
  private boolean isStar(Word word) {
    int row = word.getRow();
    int col = word.getCol();
    for (int i = 0; i < getTiles().length; i++) {
      if (row == 7 && col == 7) return true;
      if (word.isVertical()) row++;
      else col++;
    }
    return false;
  }

  /**
   * Places the specified word on the board. Check if not all the word is on the board. Check if the
   * word is crossing or touching another word.
   *
   * @param word The word to place on the board.
   * @return true if the word was placed on the board, false otherwise.
   */
  public boolean boardLegal(Word word) {
    if (word.getRow() < 0 || word.getCol() < 0) return false;
    if (word.getRow() >= 15 || word.getCol() >= 15) return false;
    if (word.isVertical()) {
      if (word.getRow() + word.getTiles().length - 1 >= 15) return false;
      else {
        if (word.getCol() + word.getTiles().length - 1 >= 15) return false;
      }
    }
    if (boardTiles[7][7] == null) { // Means this is the first word
      return isStar(word);
    }
    // Need to check if the word is not crossing nor touching another word (if so, return false)
    // Need to check if needed to replace another letter (if so, return false)

    return true;
  }

  /**
   * Checks if the specified word is legal according to the dictionary. For now, return true.
   *
   * @param word The word to check.
   * @return true if the word is legal according to the dictionary, false otherwise.
   */
  private boolean dictionaryLegal(Word word) {
    return true; // FOR NOW
  }

  /** Checks is the word is legal by checking board and dictionary legality. * */
  private boolean isWordLegal(Word word) {
    return boardLegal(word) && dictionaryLegal(word);
  }

  /**
   * Checks for the words created by the given word, if we would put it on the board.
   *
   * @param word is the word to check.
   * @return the words created by the word.
   */
  private ArrayList<Word> getWords(Word word) {
    ArrayList<Word> words = new ArrayList<>();
    // What I need to check is - for each letter in the word, is there a word that is created by it.
    return words;
  }

  /**
   * Places the specified word on the board. If the word is legal, return the score of the word. If
   * the word is not legal, return 0.
   *
   * @param word The word to place on the board.
   * @return the score of the word if the word is legal, 0 otherwise.
   */
  private int getScore(Word word) {
    if (!isWordLegal(word)) return 0;
    int score = 0;
    for (Tile tile : word.getTiles()) {
      score += tile.getScore(); // Add getScore to Tile!
    }
    return score;
  }

  /**
   * Tries to place the specified word on the board. If the word is legal, place it and return the
   * score of the word. 0 otherwise.
   *
   * @param word The word to place on the board.
   * @return the score of the word if it was placed on the board, 0 otherwise.
   */
  private int tryPlaceWord(Word word) {
    if (!isWordLegal(word)) return 0;
    // Need to place the word on the board and return the score of the word.
    return getScore(word);
  }
}
