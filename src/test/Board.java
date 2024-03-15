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
          copy[i][j] = new Tile(board[i][j]);
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
    for (Tile tile : word.getTiles()) {
      if (bonusBoard[row][col] == 0) {
        return true;
      }
      if (word.isVertical()) row++;
      else col++;
    }
    return false;
  }

  private boolean hasAdjacentTile(Word word) {
    if (word == null) return false;
    if (isBoardEmpty()) {
      return true;
    }
    int row = word.getRow();
    int col = word.getCol();
    for (Tile tile : word.getTiles()) {
      int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
      for (int[] direction : directions) {
        int newRow = row + direction[0];
        int newCol = col + direction[1];
        if (newRow >= 0
            && newRow < 15
            && newCol >= 0
            && newCol < 15
            && board[newRow][newCol] != null) {
          return true; // An adjacent tile is found
        }
      }
      if (word.isVertical()) row++;
      else col++;
    }
    return false;
  }

  private boolean isOutOfBounds(Word word) {
    if (word == null) return false;
    int row = word.getRow();
    int col = word.getCol();
    for (Tile tile : word.getTiles()) {
      if (row < 0 || row >= 15 || col < 0 || col >= 15) {
        return true;
      }
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
    for (Tile[] rowTiles : board) {
      for (Tile tile : rowTiles) {
        if (tile != null) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Places the specified word on the board. Check if not all the word is on the board. Check if the
   * word is crossing or touching another word.
   *
   * @param word The word to place on the board.
   * @return true if the word was placed on the board, false otherwise.
   */
  public boolean boardLegal(Word word) {
    if (word == null) return true;
    int row = word.getRow();
    int col = word.getCol();
    if (isOutOfBounds(word)) return false;
    if (isBoardEmpty()) { // First word on the board
      if (!isOnStar(word)) { // First word not on star
        System.out.println("* First word is not on the star tile ");
        return false; // First word is not on the star tile
      }
    } else { // If the board is not empty
      if (!hasAdjacentTile(word)) {
        return false;
      }
    }
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

  public ArrayList<Word> getWords(Word word) {
    ArrayList<Word> words = new ArrayList<>();
    int row = word.getRow();
    int col = word.getCol();
    for (Tile tile : word.getTiles()) {
      int i = 0;
      int j = 0;
      if (word.isVertical()) i = 1;
      else j = 1;
      while (row + i >= 0
          && row + i < 15
          && col + j >= 0
          && col + j < 15
          && board[row + i][col + j] != null) {
        i += word.isVertical() ? 1 : 0;
        j += word.isVertical() ? 0 : 1;
      }
      if (i != 0 || j != 0) {
        words.add(new Word(word.getTiles(), row + i, col + j, !word.isVertical()));
      }
      row += word.isVertical() ? 1 : 0;
      col += word.isVertical() ? 0 : 1;
    }
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
    ArrayList<Word> newWords = getWords(word);
    int totalScore = 0;
    int wordMultiplier = 1; // Initialize word multiplier
    for (Word newWord : newWords) {
      int wordScore = 0;
      int letterMultiplier = 1; // Initialize letter multiplier
      for (Tile tile : newWord.getTiles()) {
        if (tile != null) {
          int letterScore = tile.getScore();
          int bonus = bonusBoard[newWord.getRow()][newWord.getCol()];
          if (bonus == 2) {
            letterMultiplier *= 2; // Double letter score
          } else if (bonus == 3) {
            letterMultiplier *= 3; // Triple letter score
          } else if (bonus == 4) {
            wordMultiplier *= 2; // Double word score
          } else if (bonus == 5) {
            wordMultiplier *= 3; // Triple word score
          }
          wordScore += letterScore;
        }
      }
      wordScore *= letterMultiplier; // Apply letter multiplier
      totalScore += wordScore;
    }
    totalScore *= wordMultiplier; // Apply word multiplier
    return totalScore;
  }

  /**
   * Tries to place the specified word on the board. If the word is legal, place it and return the
   * score of the word. 0 otherwise.
   *
   * @param word The word to place on the board.
   * @return the score of the word if it was placed on the board, 0 otherwise.
   */
  public int tryPlaceWord(Word word) {
    if (isWordLegal(word)) {
      int row = word.getRow();
      int col = word.getCol();
      for (Tile tile : word.getTiles()) {
        if (tile != null) {
          if (board[row][col] == null) { // If the tile is not already on the board
            board[row][col] = new Tile(tile);
          }
        }
        if (word.isVertical()) row++;
        else col++;
      }
      return getScore(word);
    }
    System.out.println("tryPlaceWord Word illegal");
    return 0;
  }
}
