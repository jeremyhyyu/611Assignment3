/*
 * DotsAndBoxes.java
 * By Heyang Yu (jhyyu@bu.edu)
 * This is the class for the board game dots and boxes.
 * The rule of the game and possible actions are implemented in this class.
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class DotsAndBoxes extends BoardGame{
    // constants
    public static final int MIN_R = 2; // R for row and C for column
    public static final int MIN_C = 2;
    public static final int MAX_R = 5;
    public static final int MAX_C = 5;
    public static final int MIN_TEAM = 2;
    public static final int MAX_TEAM = 8;
    public static final int MIN_PLAYER_PER_TEAM = 1;
    public static final int MAX_PLAYER_PER_TEAM = 4;
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    // colors to be referenced in this class, 1-8 for team 1-8
    public static final int RED = 1;
    public static final int BLUE = 2;
    public static final int YELLOW = 3;
    public static final int GREEN = 4;
    public static final int PURPLE = 5;
    public static final int CYAN = 6;
    public static final int WHITE = 7;
    public static final int BLACK = 8;
    // variables
    private List<Integer> teamBoxCnts;
    private List<String> teamSigns;
    private int currentTeam = 1; // 1-8
    private List<String> colorList;
    private List<String> colorNames;
    private int teamCnt; // 2-8
    // constructor
    public DotsAndBoxes(Board board, int teamCnt) {
        super(board);
        this.teamCnt = teamCnt;
        this.colorList = Color.initializeColorList("DotsAndBoxes");
        this.colorNames = Color.initializeColorNames("DotsAndBoxes");
        this.teamBoxCnts = new ArrayList<>();
        this.teamSigns = new ArrayList<>();
    }
    // getters
    public int getCurrentPlayer() {
        return this.currentTeam;
    }
    public List<String> getColorList() {
        return this.colorList;
    }
    public List<String> getColorNames() {
        return this.colorNames;
    }
    public int getTeamCount() {
        return this.teamCnt;
    }
    public List<Integer> getTeamBoxCounts() {
        return this.teamBoxCnts;
    }
    public List<String> getTeamSigns() {
        return this.teamSigns;
    }
    // setters
    public void setCurrentTeam(int teamNo) {
        this.currentTeam = teamNo;
    }
    // methods
    // customize team and player info
    public void customize() {
        // d&b is a multiple team game, each team has one player, and each team takes a color
        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < this.teamCnt; i++) {
            // customize the team name and players' names
            String teamName = InputHandler.getANonEmptyString("Team " + Integer.toString(i + 1) + ", please input your team name: ");
            Team team = new Team(teamName, 0);
            team.setTeamNo(i + 1);
            int playerCnt = InputHandler.getAnIntegerInARange("number of player", MIN_PLAYER_PER_TEAM, MAX_PLAYER_PER_TEAM);
            for(int j = 0; j < playerCnt; j++) {
                String playerName = InputHandler.getANonEmptyString("Team " + teamName + " player " + Integer.toString(j + 1) + ", please input your player name: ");
                Player player = new Player(playerName);
                player.setTeamNo(team.getTeamNo());
                team.addPlayer(player);
            }
            // set the first player to be the current player
            team.setCurrentPlayerIndex(0);
            // add team to the game
            this.addTeam(team);
            // set the sign for this team
            this.teamSigns.add(teamName.substring(0, 1).toUpperCase());
            // initialize the box counter of the team
            this.teamBoxCnts.add(0);
        }
    }
    // board initialization
    public void boardInitialization() {
        // different from slider, d&b have no edge pieces initially
        int index = 1;
        for(int i = 0; i < this.board.getRowNum(); i++) {
            for(int j = 0; j < this.board.getColNum(); j++){
                Tile tile = new Tile(i, j);
                Piece piece = new Piece(Integer.toString(index), Color.WHITE);
                tile.setPiece(piece, 0);
                this.board.setTile(tile, i, j);
                index++;
            }   
        }
    }
    // check if a box is completed
    public boolean checkBox(int index) {
        if(index > this.board.getColNum() * this.board.getRowNum()) {
            System.out.println("Box does not exist");
            return false;
        }
        int i = (index - 1) / this.board.getColNum();
        int j = (index - 1) % this.board.getColNum();
        Tile tile = this.board.getTile(i, j);
        if(tile.getPiece(1) != null && tile.getPiece(2) != null && tile.getPiece(3) != null && tile.getPiece(4) != null) {
            return true;
        }
        return false;
    }
    // add an edge, return 0 if no tile is finished, 1 if a tile is finished, return -1 if err
    public int addAnEdge(int index, int side, int teamNo) {
        int flag = 0;
        int i = (index - 1) / this.board.getColNum();
        int j = (index - 1) % this.board.getColNum();
        Tile tile = this.board.getTile(i, j);
        if(tile.getPiece(side) != null) {
            System.out.println("Edge already exist!");
            return -1;
        }
        // check and set neighbors
        // top
        if(side == TOP) {
            // for the neighbor on the top side of this tile
            if(i > 0) {
                Tile neighborTile = this.board.getTile(i - 1, j);
                Piece neighborPiece = new Piece(this.colorList.get(teamNo - 1));
                neighborTile.setPiece(neighborPiece, BOTTOM);
                // check if the tile is finished
                if(checkBox(index - this.board.getColNum())) {
                    // update the value of the box
                    finishATile(index - this.board.getColNum(), teamNo);
                    flag = 1;
                }
            }
        }
        // bottom
        if(side == BOTTOM) {
            // for the neighbor on the bottom side of this tile
            if(i < this.board.getRowNum() - 1) {
                Tile neighborTile = this.board.getTile(i + 1, j);
                Piece neighborPiece = new Piece(this.colorList.get(teamNo - 1));
                neighborTile.setPiece(neighborPiece, TOP);
                // check if the tile is finished
                if(checkBox(index + this.board.getColNum())) {
                    // update the value of the box
                    finishATile(index + this.board.getColNum(), teamNo);
                    flag = 1;
                }
            } 
        }
        // left
        if(side == LEFT) {
            // for the neighbor on the left side of this tile
            if(j > 0) {
                Tile neighborTile = this.board.getTile(i, j - 1);
                Piece neighborPiece = new Piece(this.colorList.get(teamNo - 1));
                neighborTile.setPiece(neighborPiece, RIGHT);
                // check if the tile is finished
                if(checkBox(index - 1)) {
                    // update the value of the box
                    finishATile(index - 1, teamNo);
                    flag = 1;
                }
            }
        }
        // right
        if(side == RIGHT) {
            // for the neighbor on the right side of this tile
            if(j < this.board.getColNum() - 1) {
                Tile neighborTile = this.board.getTile(i, j + 1);
                Piece neighborPiece = new Piece(this.colorList.get(teamNo - 1));
                neighborTile.setPiece(neighborPiece, LEFT);
                // check if the tile is finished
                if(checkBox(index + 1)) {
                    // update the value of the box
                    finishATile(index + 1, teamNo);
                    flag = 1;
                }
            }
        }
        // update and check the selected tile
        Piece piece = new Piece(this.colorList.get(teamNo - 1));
        tile.setPiece(piece, side);
        // check if the tile is finished
        if(checkBox(index)) {
            // update the value of the box
            finishATile(index, teamNo);
            flag = 1;
        }

        // return the flag to let the caller know whether a tile is finished after this
        return flag;
    }
    // modify the value of piece in the tile to the first letter of the team who closed it
    public void finishATile(int index, int teamNo) {
        int i = (index - 1) / this.board.getColNum();
        int j = (index - 1) % this.board.getColNum();
        // chage the inner piece of the tile to the letter and add box counts
        this.board.getTile(i, j).getPiece(0).setValue(this.teamSigns.get(teamNo - 1));
        this.board.getTile(i, j).getPiece(0).setColor(this.colorList.get(teamNo - 1));
        this.teamBoxCnts.set(teamNo - 1, this.teamBoxCnts.get(teamNo - 1) + 1);
    }
    // check if the game is finished
    // if finished, print the winner and add points to the winner(s)
    public boolean checkIfGameIsFinished() {
        int sum = 0;
        for(int i = 0; i < this.teamCnt; i++) {
            sum += this.teamBoxCnts.get(i);
        }
        // compare the sum to the total number of grids
        if(sum == this.board.getColNum() * this.board.getRowNum()) {
            // game is finished, check the result
            int maxBoxCnt = 0;
            int maxIndex = -1;
            boolean isTie = false;
            for(int j = 0; j < this.teamCnt; j++) {
                if(this.teamBoxCnts.get(j) > maxBoxCnt) {
                    maxBoxCnt = this.teamBoxCnts.get(j);
                    maxIndex = j;
                    isTie = false;
                } else if(this.teamBoxCnts.get(j) == maxBoxCnt) {
                    isTie = true;
                }
            }
            // print result and award points to the winners
            if(!isTie) {
                // there is a only winner
                System.out.println("Team " + Integer.toString(maxIndex + 1) + " player " + this.teams.get(maxIndex).getCurrentPlayer().getName() +" won!");
                // only winner get 100 pts
                this.teams.get(maxIndex).getCurrentPlayer().setScore(this.teams.get(maxIndex).getCurrentPlayer().getScore() + 100);
            } else {
                System.out.println("Tie!");
                for(int j = 0; j < this.teamCnt; j++) {
                    if(this.teamBoxCnts.get(j) == maxBoxCnt) {
                        // all winnners get 50 pts when tie
                        this.teams.get(j).getCurrentPlayer().setScore(this.teams.get(j).getCurrentPlayer().getScore() + 50);
                    }
                }
            }
            return true;
        }
        return false;
    }
    // ask player to make a move, false for quit
    public boolean askPlayerAction(int teamNo) {
        Scanner scanner = new Scanner(System.in);
        // print the message to ask for a input
        Color.println(this.colorList.get(teamNo - 1), "You are " + this.colorNames.get(teamNo - 1) + "!");
        System.out.print("Player " + this.teams.get(teamNo - 1).getCurrentPlayer().getName() + ", which gird do you want to add an edge to? q to quit: ");

        // get and check user input
        if (scanner.hasNextInt()) {
            int userInput = scanner.nextInt();
            // check if the input is in the range
            if (userInput >= 1 && userInput <= this.board.getColNum() * this.board.getRowNum()) {
                // in the range, compute the row and col of tile
                int i = (userInput - 1) / this.board.getColNum();
                int j = (userInput - 1) % this.board.getColNum();
                // check if the selected tile is occupied
                if(checkBox(userInput)) {
                    System.out.println("Invalid input, this tile is closed.");
                    return true;
                } else {
                    // ask user to input a valid side
                    scanner.nextLine();
                    while(true) {
                        System.out.print("Please choose a side, 1: Top; 2:Bottom; 3: Left; 4: Right; your choice is: ");
                        String side = scanner.nextLine();
                        if(side.equals("1") || side.equals("2") || side.equals("3") || side.equals("4")) {
                            // check if the side is occupied
                            if(this.board.getTile(i, j).getPiece(Integer.parseInt(side)) == null) {
                                // add the edge, change the round and return true
                                int flag = addAnEdge(userInput, Integer.parseInt(side), teamNo);
                                if(flag == 1) {
                                    // current player just finished an edge no action
                                } else if(flag == 0) {
                                    // change to next team
                                    this.currentTeam = this.currentTeam % this.teamCnt + 1;
                                }
                                return true;
                            } else {
                                // edge occupied
                                System.out.println("This edge is occupied!");
                            }
                        } else {
                            // invalid input
                            System.out.println("Invalid input");
                        }
                    }
                }
            } else {
                System.out.println("Input out of range");
                return true;
            }
        } else {
            if(scanner.nextLine().equalsIgnoreCase("q")) {
                // user wants to quit, return 3
                return false;
            } else {
                // other non-integer inputs are invalid
                System.out.println("Your input should be an integer!");
                return true;
            }
        }
    }
    // play a d&b game
    public void play() {
        // initialize the board
        boardInitialization();
        while(true) {
            this.board.displayBoard();
            // check if the game is finished
            boolean flag = checkIfGameIsFinished();
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
        // clear the game data (box counts)
        clearBoxCnts();
        // print the scores
        printScores();
        // ask if the user wants to play one more game
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play one more game? (y/n)");
        String playAgain = scanner.nextLine();
        while(!playAgain.equalsIgnoreCase("y") && !playAgain.equalsIgnoreCase("n")) {
            System.out.println("Invalid input!");
            System.out.println("Do you want to play one more game? (y/n)");
            playAgain = scanner.nextLine();
        }
        if(playAgain.equalsIgnoreCase("y")) {
            // change players
            changePlayers();
            // start a new game
            play();
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
    // clear box counts
    public void clearBoxCnts() {
        for(int i = 0; i < this.teamCnt; i++) {
            this.teamBoxCnts.set(i, 0);
        }
    }
}
