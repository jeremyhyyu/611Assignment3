/*
 * Tile.java
 * By Heyang Yu (jhyyu@bu.edu)
 * Tile is a grid on the board.
 * There can be multiple pieces in a tile.
 */
public class Tile {
    private Piece[] pieces; // index: 0 center 1 top 2 bottom 3 left 4 right
    private int x;
    private int y;
    // constructor
    public Tile(int x, int y) {
        this.pieces = new Piece[5];
        this.x = x;
        this.y = y;
    }
    // getters
    public Piece getPiece(int index) {
        return this.pieces[index];
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
    // setters
    public void setPiece(Piece piece, int index) {
        this.pieces[index] = piece;
    }
}
