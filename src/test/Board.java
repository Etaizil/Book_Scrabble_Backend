/*
@created 12/03/2024 - 21:29
@project PTM1 Project
@author Zilberman Etai
*/

package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Board {
  private static Board CreatedBoard = null;
  private Tile[][] board;
  private final int boardRows = 15;
  private final int boardCols = 15;

  private boolean isFirstTurn = true;

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
    if (board == null) {
      return null; // Return null if there are no tiles on the board
    }
    Tile[][] copy = new Tile[board.length][]; // Create a new array for copying
    for (int i = 0; i < board.length; i++) {
      if (board[i] != null) {
        copy[i] = Arrays.copyOf(board[i], board[i].length); // Copy each row of the board
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
    int length = word.getTiles().length;
    for (int i = 0; i < word.getTiles().length; i++) {
      if (bonusBoard[row][col] == 0) {
        // System.out.println("Word is on star");
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
  boolean isTouchingAnotherWordLegally(Word word) {
    if (isBoardEmpty() && !isOutOfBounds(word)) return true;
    int row = word.getRow();
    int col = word.getCol();
    for (Tile tile : word.getTiles()) { // Check if the letter is on another letter.
      if (board[row][col] != null) {
        if (tile.getLetter() != '_') {
          if (board[row][col].getLetter() != tile.getLetter()) {
            // System.out.println("Word is not touching another word legally");
            return false;
          }
        }
      }
      if (word.isVertical()) row++;
      else col++;
    }
    row = word.getRow();
    col = word.getCol();
    for (Tile tile : word.getTiles()) { // Check if the word is touching another word.
      if ((row > 0 && board[row - 1][col] != null) // Above
          || (row < 14 && board[row + 1][col] != null) // Below
          || (col > 0 && board[row][col - 1] != null) // Left
          || (col < 14 && board[row][col + 1] != null)) // Right
      {
        return true;
      }
      if (word.isVertical()) row++;
      else col++;
    }
    return true;
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
  boolean isBoardEmpty() {
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
    if (!isTouchingAnotherWordLegally(word)) return false;
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
    // System.out.println("Checking legality of word: " + word);
    boolean legal = boardLegal(word) && dictionaryLegal(word);
    // System.out.println("Word is " + (legal ? "legal" : "illegal"));
    return legal;
  }

  private Tile[] getWordAboveAndBelowVerticalWord(Word word) {
    ArrayList<Tile> tiles = new ArrayList<>();
    int row = word.getRow();
    int col = word.getCol();
    int wordLength = word.getTiles().length;
    if (board[row - 1][col] == null && board[row + wordLength][col] == null) return null;
    if (board[row - 1][col] != null) {
      int i = row - 1;
      while (i >= 0 && board[i][col] != null) {
        i--;
      }
      i++;
      while (i < row) {
        tiles.add(board[i][col]); // Add the tiles above the original word
        i++;
      }
    }
    Collections.addAll(tiles, word.getTiles()); // Add the tiles of the original word
    if (board[row + wordLength][col] != null) {
      int i = row + wordLength;
      while (i < 15 && board[i][col] != null) { // Add the tiles below the original word
        tiles.add(board[i][col]);
        i++;
      }
    }
    Tile[] tilesArray = new Tile[tiles.size()]; // Convert ArrayList to List[]
    for (int i = 0; i < tiles.size(); i++) {
      tilesArray[i] = tiles.get(i);
    }
    return tilesArray;
  }

  private ArrayList<Word> getWordsLeftAndRightToVerticalWord(Word word) {
    ArrayList<Word> words = new ArrayList<>();
    int row = word.getRow();
    for (Tile t : word.getTiles()) {
      Tile[] tempTile =
          new Tile[] {t}; // Creating a word from a single tile to check left and right
      // now it's the same as check left and right for horizontal word, so I can call
      // getWordLeftAndRightHorizontalWord
      Word temp = new Word(tempTile, row, word.getCol(), false);
      Tile[] leftAndRightWord = getWordLeftAndRightHorizontalWord(temp);
      if (leftAndRightWord != null) {
        int startCol = word.getCol();
        while (startCol > 0 && board[row][startCol - 1] != null) {
          startCol--;
        }
        words.add(
            new Word(
                leftAndRightWord,
                row,
                startCol,
                false)); // check that the words contains an ArrayList of words
      }
      row++;
    }
    return words;
  }

  private ArrayList<Word> getWordsForVerticalWord(Word word) {
    ArrayList<Word> words = new ArrayList<>();
    Tile[] aboveAndBelowWord = getWordAboveAndBelowVerticalWord(word); // above+word+below
    if (aboveAndBelowWord != null) {
      words.add(
          new Word(
              aboveAndBelowWord,
              word.getRow() - (aboveAndBelowWord.length - word.getTiles().length),
              word.getCol(),
              true));
    }
    words.addAll(getWordsLeftAndRightToVerticalWord(word));
    // check that the words contains an ArrayList of words
    return words;
  }

  private Tile[] getWordLeftAndRightHorizontalWord(Word word) {
    ArrayList<Tile> tiles = new ArrayList<>();
    int row = word.getRow();
    int col = word.getCol();
    int wordLength = word.getTiles().length;
    if (board[row][col - 1] == null && board[row][col + wordLength] == null) return null;
    if (board[row][col - 1] != null) {
      int i = col - 1;
      while (i >= 0 && board[row][i] != null) {
        i--;
      }
      i++;
      while (i < col) {
        tiles.add(board[row][i]); // Add the tiles to the left of the original word
        i++;
      }
    }
    Collections.addAll(tiles, word.getTiles()); // Add the tiles of the original word
    if (board[row][col + wordLength] != null) {
      int i = col + wordLength;
      while (i < 15 && board[row][i] != null) { // Add the tiles to the right of the original word
        tiles.add(board[row][i]);
        i++;
      }
    }
    Tile[] tilesArray = new Tile[tiles.size()]; // Convert ArrayList to List[]
    for (int i = 0; i < tiles.size(); i++) {
      tilesArray[i] = tiles.get(i);
    }
    return tilesArray;
  }

  private ArrayList<Word> getWordsAboveAndBelowHorizontalWord(Word word) {
    ArrayList<Word> words = new ArrayList<>();
    int col = word.getCol();
    for (Tile t : word.getTiles()) {
      Tile[] tempTile =
          new Tile[] {t}; // Creating a word from a single tile to check above and below
      // now it's the same as check above and below for vertical word, so I can call
      // getWordAboveAndBelowVerticalWord
      Word temp = new Word(tempTile, word.getRow(), col, true);
      Tile[] aboveAndBelowWord = getWordAboveAndBelowVerticalWord(temp);
      if (aboveAndBelowWord != null) {
        int startRow = word.getRow();
        while (startRow > 0 && board[startRow - 1][col] != null) {
          startRow--;
        }
        words.add(new Word(aboveAndBelowWord, startRow, col, true));
      }
      col++;
    }
    return words;
  }

  private ArrayList<Word> getWordsForHorizontalWord(Word word) {
    ArrayList<Word> words = new ArrayList<>();
    Tile[] leftAndRightWord = getWordLeftAndRightHorizontalWord(word); // left+word+right
    if (leftAndRightWord != null) {
      int startCol = word.getCol();
      int row = word.getRow();
      while (startCol > 0 && board[row][startCol - 1] != null) {
        startCol--;
      }
      words.add(
          new Word(
              leftAndRightWord,
              word.getRow(),
              startCol,
              false)); // check that the words contains an ArrayList of words
    }
    words.addAll(getWordsAboveAndBelowHorizontalWord(word)); // for each tile check above and below
    return words;
  }

  public ArrayList<Word> getWords(Word word) {
    ArrayList<Word> words = new ArrayList<>();
    Tile[] wordTiles = word.getTiles();
    words.add(word);
    if (word.isVertical()) {
      ArrayList<Word> verticalWords = getWordsForVerticalWord(word);
      words.addAll(verticalWords);
    } else {
      ArrayList<Word> horizontalWords = getWordsForHorizontalWord(word);
      words.addAll(horizontalWords);
    }
    // remove words that already exist in the board
    words.removeIf(this::isWordOnBoard);
    return words;
  }

  public boolean isWordOnBoard(Word word) {
    int row = word.getRow();
    int col = word.getCol();
    for (Tile tile : word.getTiles()) {
      if (board[row][col] == null || board[row][col].getLetter() != tile.getLetter()) {
        return false;
      }
      if (word.isVertical()) {
        row++;
      } else {
        col++;
      }
    }
    // If we've gone through all the tiles, and they all match, then the word is on the board
    return true;
  }

  private Tile[][] copyBoard() {
    Tile[][] copiedBoard = new Tile[boardRows][boardCols];
    for (int i = 0; i < boardRows; i++) {
      for (int j = 0; j < boardCols; j++) {
        if (board[i][j] != null) {
          copiedBoard[i][j] = board[i][j].copy();
        }
      }
    }
    return copiedBoard;
  }

  /**
   * Places the specified word on the board. If the word is legal, return the score of the word. If
   * the word is not legal, return 0.
   *
   * @param word The word to place on the board.
   * @return the score of the word if the word is legal, 0 otherwise.
   */
  private int getScore(Word word) {
    if (word == null) return 0;
    int totalScore = 0;
    int wordBonus = 1;
    int row = word.getRow();
    int col = word.getCol();
    for (Tile tile : word.getTiles()) {
      if (bonusBoard[row][col] == 0) {
        if (isFirstTurn) { // Only the first player gets the bonus
          wordBonus *= 2;
          totalScore += tile.getScore();
          isFirstTurn = false;
        } else {
          totalScore += tile.getScore();
        }
      } else if (bonusBoard[row][col] == 1) {
        totalScore += tile.getScore();
      } else if (bonusBoard[row][col] == 2) {
        totalScore += (tile.getScore() * 2);
      } else if (bonusBoard[row][col] == 3) {
        totalScore += (tile.getScore() * 3);
      } else if (bonusBoard[row][col] == 4) {
        wordBonus *= 2;
        totalScore += tile.getScore();
      } else if (bonusBoard[row][col] == 5) {
        wordBonus *= 3;
        totalScore += tile.getScore();
      }
      if (word.isVertical()) row++;
      else col++;
    }
    totalScore *= wordBonus;
    return totalScore;
  }

  private void placeWord(Word word) {
    int row = word.getRow();
    int col = word.getCol();
    for (Tile tile : word.getTiles()) {
      if (!tile.originallyBlank) { // Only place the tile if it was not originally blank
        if (board[row][col] == null) {
          board[row][col] = tile;
        }
      }
      if (word.isVertical()) row++;
      else col++;
    }
  }

  /** Changes the underscore tiles in the word to the corresponding tiles on the board. */
  private Word changeUnderScoreTiles(Word word) {
    Tile[] tiles = word.getTiles();
    int row = word.getRow();
    int col = word.getCol();
    for (int i = 0; i < tiles.length; i++) {
      if (tiles[i].getLetter() == '_') {
        tiles[i] = board[row][col].copy();
      }

      if (word.isVertical()) row++;
      else col++;
    }
    return new Word(tiles, word.getRow(), word.getCol(), word.isVertical());
  }

  /**
   * Checks if the word contains an underscore.
   *
   * @param word The word to check.
   * @return true if the word contains an underscore, false otherwise.
   */
  private boolean containsUnderscore(Word word) {
    for (Tile t : word.getTiles()) {
      if (t.getLetter() == '_') {
        t.originallyBlank = true;
        return true;
      }
    }
    return false;
  }

  /**
   * Tries to place the specified word on the board. If the word is legal, place it and return the
   * score of the word. 0 otherwise.
   *
   * @param word The word to place on the board.
   * @return the score of the word if it was placed on the board, 0 otherwise.
   */
  public int tryPlaceWord(Word word) {
    if (word == null) return 0;
    if (containsUnderscore(word)) {
      word = changeUnderScoreTiles(word);
    }
    if (!isWordLegal(word)) return 0;
    ArrayList<Word> words = getWords(word);
    for (Word w : words) {
      if (!isWordLegal(w)) return 0;
    }
    placeWord(word); // Place the word on the board, other words will obviously be created.
    // printBoard();
    int score = 0;
    for (Word w : words) { // Sum the score from all the words created by the placed word.
      int wordScore = getScore(w);
      // System.out.println("Score for word " + w + ": " + wordScore);
      score += wordScore;
    }
    // System.out.println("Total score: " + score);

    return score;
  }

  //  public void printBoard() {
  //    for (int i = 0; i < boardRows; i++) {
  //      for (int j = 0; j < boardCols; j++) {
  //        if (board[i][j] != null) {
  //          System.out.print(board[i][j].getLetter() + " ");
  //        } else {
  //          System.out.print("  ");
  //        }
  //      }
  //      System.out.println();
  //    }
  //  }
}
