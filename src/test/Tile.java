/*
@created 12/03/2024 - 21:29
@project PTM1 Project
@author Zilberman Etai
*/

package test;

final class Tile {
  private final char letter;
  private final int score;

  private Tile(char letter, int score) {
    this.letter = letter;
    this.score = score;
  }

  public static class Bag {
    private static Bag bag = new Bag();
    public static int[] quantities = {
      9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1
    };

    private Bag() {
      final Tile[] tiles = new Tile[quantities.length];
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

    public getrand() {
      // randomize an index and return a reference to the tile in that index
    }

    public static Bag getBag() {
      return bag;
    }
  }
}
