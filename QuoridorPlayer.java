public class QuoridorPlayer extends Player {
    // variables
    private int row;
    private int col;
    private int wallCnt;
    // constructor, team 1 top, team 2 bottom, team 3 left, team 4 right
    public QuoridorPlayer(String playerName) {
        super(playerName);
    }
    // getters
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }
    public int getWallCnt() {
        return this.wallCnt;
    }
    // setters
    public void setCoords(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }
    public void setWallCnt(int wallCnt) {
        this.wallCnt = wallCnt;
    }
}
