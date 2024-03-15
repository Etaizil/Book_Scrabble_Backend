/*
@created 12/03/2024 - 21:29
@project PTM1 Project
@author Zilberman Etai
*/

package test;

import java.util.ArrayList;

public class Board {
  private static Board CreatedBoard = null;
  private Tile[][] board;
  int boardRows = 15;
  int boardCols = 15;

  /**
   * The bonus for each tile on the board. The bonusBoard is a 15x15 matrix, where each cell
   * contains the bonus for the corresponding tile. 0 - a star, double the score for the word, 1 -
   * is a normal score, 2 - double score for a letter, 3 - triple score for a letter, 4 - double
   * score for a Word, 5 - triple score for a WORD.
   */
  private final int[][] bonusBoard = {
    {5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5},
    {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
    {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
    {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
    {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
    {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
    {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
    {5, 1, 1, 2, 1, 1, 1, 0, 1, 1, 1, 2, 1, 1, 5},
    {1, 1, 2, 1, 1, 1, 2, 1, 2, 1, 1, 1, 2, 1, 1},
    {1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 3, 1},
    {1, 1, 1, 1, 4, 1, 1, 1, 1, 1, 4, 1, 1, 1, 1},
    {2, 1, 1, 4, 1, 1, 1, 2, 1, 1, 1, 4, 1, 1, 2},
    {1, 1, 4, 1, 1, 1, 2, 1, 2, 1, 1, 1, 4, 1, 1},
    {1, 4, 1, 1, 1, 3, 1, 1, 1, 3, 1, 1, 1, 4, 1},
    {5, 1, 1, 2, 1, 1, 1, 5, 1, 1, 1, 2, 1, 1, 5}
  };

  private Board() {
    board = new Tile[15][15];
  }

  /**
   * The first player to play will create the board. If already created, return the singleton
   *
   * @return the singleton instance of the board.
   */
  public static Board getBoard() {
    if (CreatedBoard == null) {
      CreatedBoard = new Board();
    }
    return CreatedBoard;
  }

  /**
   * returns the tiles of the board.
   *
   * @return 2D array of the tiles on the board.
   */
  public Tile[][] getTiles() {
    Tile[][] copy = new Tile[15][15];
    for (int i = 0; i < 15; i++) {
      for (int j = 0; j < 15; j++) {
        if (board[i][j] != null) {
          copy[i][j] = board[i][j].copy();
        }
      }
    }
    return copy;
  }

  /**
   * Check if the given word is on the star tile.
   *
   * @param word The word to check.
   * @return True if the word is on the star tile, false otherwise.
   */
  private boolean isOnStar(Word word) {
    if (word == null) return false;
    int row = word.getRow();
    int col = word.getCol();
    for (int i = 0; i < word.getTiles().length; i++) {
      if (bonusBoard[row][col] == 0) {
        return true;
      }
      if (word.isVertical()) {
        if (row < boardRows) { // In the limits
          row++;
        } else {
          break;
        }
      } else { // Horizontal
        if (col < boardCols) { // In the limits
          col++;
        } else {
          break;
        }
      }
    }
    return false;
  }

  // One of the tiles must touch a letter on the board.
  // If the letter is on another existing letter, the letters must be the same.
  private boolean isTouchingAnotherWord(Word word) {
    return false;
  }

  private boolean isOutOfBounds(Word word) {
    if (word == null) return false;
    int row = word.getRow();
    int col = word.getCol();
    for (Tile tile : word.getTiles()) {
      if (row < 0 || row > 14 || col < 0 || col > 14) return true;
      if (word.isVertical()) row++;
      else col++;
    }
    return false;
  }

  /**
   * Iterates over the board to check if there are any tiles on it.
   *
   * @return true if the board is empty, false otherwise.
   */
  private boolean isBoardEmpty() {
    for (int i = 0; i < boardRows; i++) {
      for (int j = 0; j < boardCols; j++) {
        if (board[i][j] != null) return false;
      }
    }
    return true;
  }

  /**
   * Check if not the word is on the board limits. Check if the word is crossing or touching another
   * word.
   *
   * @param word The word to place on the board.
   * @return true if the word was placed on the board, false otherwise.
   */
  public boolean boardLegal(Word word) {
    if (isOutOfBounds(word)) return false;
    if (isBoardEmpty() && !isOnStar(word)) return false;
    if (!isTouchingAnotherWord(word)) return false;
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

  public ArrayList<Word> getWords(Word word) {}

  /**
   * Places the specified word on the board. If the word is legal, return the score of the word. If
   * the word is not legal, return 0.
   *
   * @param word The word to place on the board.
   * @return the score of the word if the word is legal, 0 otherwise.
   */
  private int getScore(Word word) {}

  private void placeWord(Word word) {}

  /**
   * Tries to place the specified word on the board. If the word is legal, place it and return the
   * score of the word. 0 otherwise.
   *
   * @param word The word to place on the board.
   * @return the score of the word if it was placed on the board, 0 otherwise.
   */
  public int tryPlaceWord(Word word) {
    if (word == null) return 0;
    if (!isWordLegal(word)) return 0;
    ArrayList<Word> words = getWords(word);
    for (Word w : words) {
      if (!isWordLegal(w)) return 0;
    }
    placeWord(word); // Place the word on the board, other words will obviously be created.
    int score = 0;
    for (Word w : words) { // Sum the score from all the words created by the placed word.
      score += getScore(w);
    }
    return score;
  }
}
