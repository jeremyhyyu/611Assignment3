/*
 * Board.java
 * By Heyang Yu (jhyyu@bu.edu)
 * The class is for the game board.
 * Designed to render both Slider and dots and boxes game boards.
 */

public class Board {
    private int rowNum;
    private int colNum;
    private Tile[][] tiles;
    // constructor
    public Board(int rowNum, int colNum) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        tiles = new Tile[rowNum][colNum];
    }
    // getters
    public int getRowNum() {
        return this.rowNum;
    }
    public int getColNum() {
        return this.colNum;
    }
    public Tile getTile(int row, int col) {
        return this.tiles[row][col];
    }
    // setters
    public void setTile(Tile tile, int row, int col) {
        this.tiles[row][col] = tile;
    }
    // methods
    // display the game board
    public void displayBoard() {
        for (int i = 0; i < getRowNum(); i++) {
            for(int j = 0; j < getColNum(); j++) {
                System.out.print("+");
                // top
                if(getTile(i, j).getPiece(1) != null) {
                    Color.print(getTile(i, j).getPiece(1).getColor(), "--");
                }else {
                    System.out.print("  ");
                }
            }
            System.out.print("+");
            System.out.println();
            for (int j = 0; j < getColNum(); j++) {
                // left for each grid
                if(getTile(i, j).getPiece(3) != null) {
                    Color.print(getTile(i, j).getPiece(3).getColor(), "|");
                }else {
                    System.out.print(" ");
                }
                Tile tile = tiles[i][j];
                // center
                if (tile.getPiece(0).getValue() != null) {
                    Color.print(getTile(i, j).getPiece(0).getColor(), "%-2s", tile.getPiece(0).getValue());
                } else {
                    System.out.print("  ");
                }
            }
            // right edge for last grid of each row
            if(getTile(i, getColNum() - 1).getPiece(4) != null) {
                Color.print(getTile(i, getColNum() - 1).getPiece(4).getColor(), "|");
            }else {
                System.out.print(" ");
            }
            System.out.println();
        }
        for(int j = 0; j < getColNum(); j++) {
            System.out.print("+");
            // bottom
            if(getTile(getRowNum() - 1, j).getPiece(2) != null) {
                Color.print(getTile(getRowNum() - 1, j).getPiece(2).getColor(), "--");
            }else {
                System.out.print("  ");
            }
        }
        System.out.print("+");
        System.out.println();
    }
}
