import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Quoridor extends BoardGame {
    // class variables
    public static int MIN_TEAM = 2;
    public static int MAX_TEAM = 4;
    public static int MIN_PLAYER_PER_TEAM = 1;
    public static int MAX_PLAYER_PER_TEAM = 4;
    public static int MIN_SIDE_LENGTH = 5;
    public static int MAX_SIDE_LENGTH = 9;
    // variables
    private int teamCnt; // 2-4
    private List<String> teamSigns;
    private int currentTeam;
    private List<String> colorList;
    private List<String> colorNames;
    // constructor
    public Quoridor(Board board, int teamCnt){
        super(board);
        this.teamCnt = teamCnt;
        this.teamSigns = new ArrayList<>();
        this.currentTeam = 1;
        this.colorList = Color.initializeColorList("Quoridor");
        this.colorNames = Color.initializeColorNames("Quoridor");
    }
    // getters
    public int getTeamCnt() {
        return this.teamCnt;
    }
    public List<String> getTeamSigns() {
        return this.teamSigns;
    }
    public int getCurrentTeam() {
        return this.currentTeam;
    }
    public List<String> getColorList() {
        return this.colorList;
    }
    public List<String> getColorNames() {
        return this.colorNames;
    }
    // setters
    // methods
    // customize the team names and player names of each team
    public void customize() {
        // quoridor is a multi team multi player game
        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < this.teamCnt; i++) {
            // customize the team name and players' names
            String teamName = InputHandler.getANonEmptyString("Team " + Integer.toString(i + 1) + ", please input your team name: ");
            Team team = new Team(teamName, 0);
            team.setTeamNo(i + 1);
            int playerCnt = InputHandler.getAnIntegerInARange("number of player", MIN_PLAYER_PER_TEAM, MAX_PLAYER_PER_TEAM);
            for(int j = 0; j < playerCnt; j++) {
                String playerName = InputHandler.getANonEmptyString("Team " + teamName + " player " + Integer.toString(j + 1) + ", please input your player name: ");
                QuoridorPlayer player = new QuoridorPlayer(playerName);
                player.setTeamNo(team.getTeamNo());
                team.addPlayer(player);
            }
            // set the first player to be the current player
            team.setCurrentPlayerIndex(0);
            // add team to the game
            this.addTeam(team);
            // set the sign for this team
            this.teamSigns.add(teamName.substring(0, 1).toUpperCase());
        }
    }

    // DONE: check if a player can reach the destination after adding a new wall
    public boolean checkSolvable(int x, int y, String choice, QuoridorPlayer player) {
        int r = player.getRow();
        int c = player.getCol();
        int R = this.board.getRowNum();
        int C = this.board.getColNum();
        boolean[][] visited = new boolean[R][C];
        for(int i = 0; i < R; i++){
            for(int j = 0; j < C; j++){
                visited[i][j] = false;
            }
        }
        // add the wall to board, later erase it
        if(choice.equals("1")) {
            Piece newEdgPiece = new Piece(this.colorList.get(player.getTeamNo() - 1));
            this.board.getTile(x - 1, y - 1).setPiece(newEdgPiece, 2);
            this.board.getTile(x - 1, y).setPiece(newEdgPiece, 2);
            this.board.getTile(x, y - 1).setPiece(newEdgPiece, 1);
            this.board.getTile(x, y).setPiece(newEdgPiece, 1);
        }
        if(choice.equals("2")) {
            Piece newEdgPiece = new Piece(this.colorList.get(player.getTeamNo() - 1));
            this.board.getTile(x - 1, y - 1).setPiece(newEdgPiece, 4);
            this.board.getTile(x - 1, y).setPiece(newEdgPiece, 3);
            this.board.getTile(x, y - 1).setPiece(newEdgPiece, 4);
            this.board.getTile(x, y).setPiece(newEdgPiece, 3);
        }
        // DFS
        boolean res = dfs(r, c, R, C, visited, player);
        // erase it
        if(choice.equals("1")) {
            Piece newEdgPiece = null;
            this.board.getTile(x - 1, y - 1).setPiece(newEdgPiece, 2);
            this.board.getTile(x - 1, y).setPiece(newEdgPiece, 2);
            this.board.getTile(x, y - 1).setPiece(newEdgPiece, 1);
            this.board.getTile(x, y).setPiece(newEdgPiece, 1);
        }
        if(choice.equals("2")) {
            Piece newEdgPiece = null;
            this.board.getTile(x - 1, y - 1).setPiece(newEdgPiece, 4);
            this.board.getTile(x - 1, y).setPiece(newEdgPiece, 3);
            this.board.getTile(x, y - 1).setPiece(newEdgPiece, 4);
            this.board.getTile(x, y).setPiece(newEdgPiece, 3);
        }

//        System.out.printf("player.r = %d, player.c = %d, player.teamno = %d, checkSolvable() returns %b\n", r, c, player.getTeamNo(), res);
        return res;
    }

    private boolean dfs(int r, int c, int R, int C, boolean[][] visited, QuoridorPlayer player) {
        // param r, c should never be out of range
        assert 0 <= r && r <= R-1 && 0 <= c && c <= C-1;
        visited[r][c] = true;
        // if player can reach the destination
        if(
            player.getTeamNo() == 1 && r == R-1 ||
            player.getTeamNo() == 2 && r == 0 ||
            player.getTeamNo() == 3 && c == C-1 ||
            player.getTeamNo() == 4 && c == 0
        ){
            return true;
        }
        Tile currentTile = this.board.getTile(r, c);

        int[][] directions = new int[][]{{-1, 0}, {+1, 0}, {0, -1}, {0, +1}};
        for(int i = 1; i <= 4; i++) { // up -> down -> left -> right
            int rr = r + directions[i-1][0];
            int cc = c + directions[i-1][1];
            // if rr/cc out of range then skip this direction
            if (rr < 0 || rr > R-1 || cc < 0 || cc > C-1) {
                continue;
            }
            // if blocked by a wall then skip this direction
            // IMPORTANT: Here the new wall has NOT been updated in the Board!!
            if (currentTile.getPiece(i) != null) {
                continue;
            }
            // if already DFS the nextTile skip this direction
            if (visited[rr][cc]) {
                continue;
            }
            boolean res = dfs(rr, cc, R, C, visited, player);
            if(res) {
//                System.out.println(currentTile.getPiece(1) + " " + currentTile.getPiece(2) + " " + currentTile.getPiece(3) + " " + currentTile.getPiece(4));
//                System.out.printf("%b %b %b %b\n", currentTile.getPiece(1) != null, currentTile.getPiece(2) != null ,currentTile.getPiece(3) != null, currentTile.getPiece(4) != null);
//                System.out.printf("i=%d, %b\n", i, currentTile.getPiece(i) != null);
//                System.out.printf("dfs(%d, %d) returns true cuz dfs(%d, %d) returns true\n", r, c, rr, cc);
                return true;
            }
        }
        return false;
    }



    // DONE: check if the wall collide with other walls
    public boolean checkCollision(int x, int y, String choice){
        // Piece index: 0 center 1 top 2 bottom 3 left 4 right
        Piece top = this.board.getTile(x - 1, y - 1).getPiece(4);
        Piece bottom = this.board.getTile(x, y - 1).getPiece(4);
        Piece left = this.board.getTile(x - 1, y - 1).getPiece(2);
        Piece right = this.board.getTile(x - 1, y).getPiece(2);

        if(choice.equals("1")){ // try to add a horizontal line
            boolean verticalOk = (top == null || bottom == null || !top.getColor().equals(bottom.getColor()));
            boolean horizontalOk = (left == null && right == null);
            return verticalOk && horizontalOk;
        } else{ // try to add a vertical line
            boolean verticalOk = (top == null && bottom == null);
            boolean horizontalOk = (left == null || right == null || !left.getColor().equals(right.getColor()));
            return verticalOk && horizontalOk;
        }

    }
    // add a wall to the board, return true if a wall is added successfully
    public boolean addAWall(int teamNo) {
        QuoridorPlayer player = (QuoridorPlayer)this.teams.get(teamNo - 1).getCurrentPlayer();
        // if player does not have enough wall, return false
        if(player.getWallCnt() <= 0) {
            System.out.println("You don't have any walls left!");
            return false;
        }

        boolean ok = false;
        int x = -1, y = -1;
        String choice = "-1";
        do {
            // ask for coordinates
            System.out.println("Where would you like to add the wall? Please input xy coordinate, x for row and y for column.");
            x = InputHandler.getAnIntegerInARange("X coordinate", 1, this.board.getRowNum() - 1);
            y = InputHandler.getAnIntegerInARange("Y coordinate", 1, this.board.getColNum() - 1);
            // ask the user to input the direction of the wall
            String prompt = "Please input the direction of the wall. 1: horizontal; 2: vertical; q: return. Your choice is: ";
            String[] validStrings = new String[]{"1", "2", "q", "Q"};
            choice = InputHandler.getAValidChoiceString(prompt, validStrings);
            if(choice.equalsIgnoreCase("q")) {
                return false;
            }
            // DONE: check collision and solvable
            boolean collisionOk = checkCollision(x, y, choice);
            if (!collisionOk) {
                System.out.println(" -- Invalid choice - collision detected. Please select the wall again.");
            }
            boolean solvableOk = checkSolvable(x, y, choice, player);
            if (collisionOk && !solvableOk) {
                System.out.println(" -- Invalid choice - unsolvable detected. Please select the wall again.");
            }
            ok = collisionOk && solvableOk;
        } while(!ok);

        // add the wall to the board
        // Piece index: 0 center 1 top 2 bottom 3 left 4 right
        if(choice.equals("1")) {
            Piece newEdgPiece = new Piece(this.colorList.get(teamNo - 1));
            this.board.getTile(x - 1, y - 1).setPiece(newEdgPiece, 2);
            this.board.getTile(x - 1, y).setPiece(newEdgPiece, 2);
            this.board.getTile(x, y - 1).setPiece(newEdgPiece, 1);
            this.board.getTile(x, y).setPiece(newEdgPiece, 1);
            player.setWallCnt(player.getWallCnt() - 1);
            return true;
        }
        if(choice.equals("2")) {
            Piece newEdgPiece = new Piece(this.colorList.get(teamNo - 1));
            this.board.getTile(x - 1, y - 1).setPiece(newEdgPiece, 4);
            this.board.getTile(x - 1, y).setPiece(newEdgPiece, 3);
            this.board.getTile(x, y - 1).setPiece(newEdgPiece, 4);
            this.board.getTile(x, y).setPiece(newEdgPiece, 3);
            player.setWallCnt(player.getWallCnt() - 1);
            return true;
        }
        return false;
    }
    // game initialization
    public void gameInitiallization() {
        // initialize the board
        for(int i = 0; i < this.board.getRowNum(); i++) {
            for(int j = 0; j < this.board.getColNum(); j++){
                Tile tile = new Tile(i, j);
                Piece blankPiece = new Piece(Color.BLACK);
                tile.setPiece(blankPiece, 0);
                this.board.setTile(tile, i, j);
            }   
        }
        // set the coords for all player
        for(int i = 1; i <= this.teamCnt; i++) {
            QuoridorPlayer player = (QuoridorPlayer)this.teams.get(i - 1).getCurrentPlayer();
            // set the coords of player according to the side
            if(i == 1) {
                player.setCoords(0, (this.board.getColNum() - 1) / 2);
            }
            if(i == 2) {
                player.setCoords(this.board.getRowNum() - 1, (this.board.getColNum() - 1) / 2);
            }
            if(i == 3) {
                player.setCoords((this.board.getRowNum() - 1) / 2, 0);
            }
            if(i == 4) {
                player.setCoords((this.board.getRowNum() - 1) / 2, this.board.getColNum() - 1);
            }
            // set the wall quantity for player
            if(this.teamCnt <= 3) {
                player.setWallCnt(10);
            } else {
                player.setWallCnt(5);
            }
            // set the pawn to the board
            String teamSign = this.teamSigns.get(i - 1);
            Piece piece = new Piece(teamSign, this.colorList.get(i - 1));
            Tile tile = this.board.getTile(player.getRow(), player.getCol());
            tile.setPiece(piece, 0);
        }
    }
    // ask for player action, return false if a player choose to quit the game
    public boolean askPlayerAction(int teamNo) {
        QuoridorPlayer player = (QuoridorPlayer)this.teams.get(teamNo - 1).getCurrentPlayer();
        // print the message to ask for a input
        Color.println(this.colorList.get(teamNo - 1), "You are " + this.colorNames.get(teamNo - 1) + "!");
        String prompt = "Player " + player.getName() + ", please choose your action. 1: move your pawn; 2: add a wall; q: quit. Your choice is: ";
        String[] validStrings = new String[]{"1", "2", "q", "Q"};
        String choice = InputHandler.getAValidChoiceString(prompt, validStrings);
        if(choice.equals("1")) {
            // make a move
            move(teamNo);
            // change turn to next team
            this.currentTeam = this.currentTeam % this.teamCnt + 1;
            return true;
        }
        if(choice.equals("2")) {
            // add a wall
            if(addAWall(teamNo)) {
                // if a wall is added successfully, change current team to next team
                this.currentTeam = this.currentTeam % this.teamCnt + 1;
            }
            return true;
        }
        if(choice.equalsIgnoreCase("q")) {
            return false;
        }
        return false;
    }
    // find which tiles can a pawn move to, and mark the tiles with numbers
    public List<Tile> findNextTiles(QuoridorPlayer player) {
        List<Tile> nextTiles = new ArrayList<>();
        Tile currentTile = this.board.getTile(player.getRow(), player.getCol());
        for(int i = 1; i <= 4; i++) {
            // if out of range then skip this direction
            if((i == 1 && player.getRow() == 0) ||
                (i == 2 && player.getRow() == this.board.getRowNum() - 1) ||
                (i == 3 && player.getCol() == 0) ||
                (i == 4 && player.getCol() == this.board.getColNum() - 1)) {
                    continue;
                }
            // if blocked by a wall then skip this direction
            if(currentTile.getPiece(i) != null) {
                continue;
            }
            // if the tile in this direction has a pawn on it
            Tile neighborTile = getNeighborTile(currentTile, i);
            if(neighborTile.getPiece(0).getValue() != null) {
                // if neighbor is on the edge check the side
                if(neighborTile.getX() == 0 || neighborTile.getX() == this.board.getRowNum() - 1 || neighborTile.getY() == 0 || neighborTile.getY() == this.board.getColNum() - 1) {
                    // add the side tiles?
                    continue;
                }
                // if the pawn has no wall or other pawn behind it
                Tile neighborOfNeoghborTile = getNeighborTile(neighborTile, i);
                if(neighborTile.getPiece(i) == null && neighborOfNeoghborTile.getPiece(0).getValue() == null) {
                    // add neighbor of neighbor tile to valid move list and skip the rest of the loop
                    nextTiles.add(neighborOfNeoghborTile);
                    continue;
                } else {
                    // check side directions
                    int[] sideDirs;
                    // direction mapping: 1/2 -> 3, 4; 3/4 -> 1, 2
                    switch(i) {
                        case 1:
                        case 2:
                            sideDirs = new int[]{3, 4};
                            break;
                        case 3:
                        case 4:
                            sideDirs = new int[]{1, 2};
                            break;
                        default:
                            sideDirs = new int[]{};
                            break;
                    }
                    for(int dir: sideDirs) {
                        // if there is no wall or a pawn on this side
                        Tile sideNeighborTile = getNeighborTile(neighborTile, dir);
                        if(neighborTile.getPiece(dir) == null && sideNeighborTile.getPiece(0).getValue() == null) {
                            nextTiles.add(sideNeighborTile);
                        }
                    }
                    // skip the rest of the loop
                    continue;
                }
            }
            // no any blocks on this side, add the neighbor
            nextTiles.add(neighborTile);
        }
        // mark valid tiles
        int index = 1;
        for(Tile tile: nextTiles) {
            Piece piece = new Piece(Integer.toString(index), Color.WHITE);
            tile.setPiece(piece, 0);
            index++;
        }
        return nextTiles;
    }
    // get the neighbor tile in a direction
    public Tile getNeighborTile(Tile tile, int dir) {
        if(dir == 1 && tile.getX() > 0) {
            return this.board.getTile(tile.getX() - 1, tile.getY());
        }
        if(dir == 2 && tile.getX() < this.board.getRowNum() - 1) {
            return this.board.getTile(tile.getX() + 1, tile.getY());
        }
        if(dir == 3 && tile.getY()  > 0) {
            return this.board.getTile(tile.getX(), tile.getY() - 1);
        }
        if(dir == 4 && tile.getY() < this.board.getColNum() - 1) {
            return this.board.getTile(tile.getX(), tile.getY() + 1);
        }
        return null;
    }
    // clear the marks we made when asking for user input
    public void clearMarks(List<Tile> indexedTiles) {
        for(Tile tile: indexedTiles) {
            Piece piece = new Piece(Color.BLACK);
            // set the value of center piece to be null
            tile.setPiece(piece, 0);
        }
    }
    // move the pawn, this action is guaranteed to be finished as there's always at least 1 valid move
    public void move(int teamNo) {
        QuoridorPlayer player = (QuoridorPlayer)this.teams.get(teamNo - 1).getCurrentPlayer();
        // find tiles to move to and display the board
        List<Tile> nexTiles = findNextTiles(player);
        this.board.displayIndexedBoard();
        // ask the player to make the choice
        int move = InputHandler.getAnIntegerInARange("move", 1, nexTiles.size());
        // clear the marks
        clearMarks(nexTiles);
        // make the move
        Tile currentTile = this.board.getTile(player.getRow(), player.getCol());
        Tile nexTile = nexTiles.get(move - 1);
        Piece pawn = currentTile.getPiece(0);
        Piece blankPiece = new Piece(Color.BLACK);
        nexTile.setPiece(pawn, 0);
        currentTile.setPiece(blankPiece, 0);
        player.setCoords(nexTile.getX(), nexTile.getY());
    }
    // check winning condition, 0 if no winner, else return the teamNo of the winner
    public boolean checkWinningCondition() {
        for(int i = 0; i < this.teamCnt; i++) {
            QuoridorPlayer player = (QuoridorPlayer)this.teams.get(i).getCurrentPlayer();
            if((i == 0 && player.getRow() == this.board.getRowNum() - 1) ||
                (i == 1 && player.getRow() == 0) ||
                (i == 2 && player.getCol() == this.board.getColNum() - 1) ||
                (i == 3 && player.getCol() == 0)) {
                    System.out.println("Team " + Integer.toString(i + 1) + " player " + this.teams.get(i).getCurrentPlayer().getName() +" won!");
                    this.teams.get(i).getCurrentPlayer().setScore(this.teams.get(i).getCurrentPlayer().getScore() + 100);
                    return true;
            }
        }
        return false;
    }
    // play a quoridor game
    public void play() {
        // initialize the game
        gameInitiallization();
        // enter the game
        while(true) {
            this.board.displayIndexedBoard();
            boolean flag = checkWinningCondition();
            if(flag) {
                break;
            } else {
                // keep playing
                if(!askPlayerAction(this.currentTeam)) {
                    // print the message of player who quit the game
                    System.out.println("Player " + this.teams.get(this.currentTeam - 1).getCurrentPlayer().getName() + " quited the game.");
                    break;
                }
            }
        }
        printScores();
        // ask if the players want to play one more game
        String message = "Do you want to play one more game? (y/n): ";
        String[] validChoices = new String[]{"y", "n", "Y", "N"};
        String playOneMore = InputHandler.getAValidChoiceString(message, validChoices);
        if(playOneMore.equalsIgnoreCase("y")) {
            // change players
            changePlayers();
            // start a new game
            play();
        } else {
            System.out.println("Bye!");
        }
    }
    // first game, customize needed
    public void firstGame() {
        customize();
        play();
    }
    // change players for each team
    public void changePlayers() {
        for(int i = 0; i < this.teamCnt; i++) {
            int currentPlayerIndex = this.teams.get(i).getCurrentPlayerIndex();
            int nextPlayerIndex = (currentPlayerIndex + 1) % this.teams.get(i).getPlayerCount();
            this.teams.get(i).setCurrentPlayerIndex(nextPlayerIndex);
        }
    }

    public static void main(String[] args) {
        Board b = new Board(9, 9);
        Quoridor q = new Quoridor(b, 2);
        q.customize();
        q.play();
    }
}
