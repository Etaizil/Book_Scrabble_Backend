/*
@created 12/03/2024 - 21:29
@project PTM1 Project
@author Zilberman Etai
*/

package test;

public class Board {
  private static Board board = null;

  public static Board getBoard() {
    if (board == null) {
      board = new Board();
    }
    return board;
  }
}
