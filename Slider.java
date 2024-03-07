/*
 * Slider.java
 * By Heyang Yu (jhyyu@bu.edu)
 * This class is for the board game sliding puzzle
 * The rule of sliding puzzle and operations which can be taken during the gameplay are defined in this class.
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Slider extends BoardGame{
    private int level; // 1-5
    public static final int MIN_R = 2; // R for row and C for column
    public static final int MIN_C = 2;
    public static final int MAX_R = 5;
    public static final int MAX_C = 5;
    public static final int MIN_LEVEL = 1;
    public static final int MAX_LEVEL = 5;
    // constructor
    public Slider(Board board, int level) {
        super(board);
        // check the validity of difficulty
        if(level < 1 || level > 5) {
            System.out.println("Invalid difficulty level! Please input a level within (1, 5).");
            throw new IllegalArgumentException();
        }
        // check the validity of board size for sliding puzzle
        if(board.getColNum() < MIN_C || board.getColNum() > MAX_C || board.getRowNum() < MIN_R || board.getRowNum() > MAX_R) {
            System.out.println("Invalid board size, please use a valid board!");
            throw new IllegalArgumentException();
        }
        this.level = level;
    }
    // getter
    public int getLevel() {
        return this.level;
    }
    // methods
    // customize team and player
    public void customize() {
        // Slider is a single team single player game
        Scanner scanner = new Scanner(System.in);
        System.out.print("Team 1, please input your team name: ");
        String teamName = scanner.nextLine();
        // team name cannot be empty
        while(teamName.length() == 0) {
            System.out.print("Team name cannot be empty, input again: ");
            teamName = scanner.nextLine();
        }
        // create a new team
        Team team = new Team(teamName, 0);
        team.setTeamNo(1);
        // ask for the player name
        System.out.print("Team " + team.getTeamName() + ", please input your player name: ");
        String playerName = scanner.nextLine();
        //player name cannot be empty
        while(playerName.length() == 0) {
            System.out.print("Player name cannot be empty, input again: ");
            playerName = scanner.nextLine();
        }
        // create a new player
        Player player = new Player(playerName);
        // add player to the team
        player.setTeamNo(team.getTeamNo());
        team.addPlayer(player);
        // add team to the game
        this.addTeam(team);
        // scanner.close();
    }
    // board initialization for slider
    public void boardInitialization() {
        int index = 1;
        for(int i = 0; i < this.board.getRowNum(); i++) {
            for(int j = 0; j < this.board.getColNum(); j++){
                Tile tile = new Tile(i, j);
                // last tile has no piece with value
                if(index == this.board.getRowNum() * this.board.getColNum()) {
                    Piece piece = new Piece(" ", Color.WHITE);
                    tile.setPiece(piece, 0);
                }else{
                    Piece piece = new Piece(Integer.toString(index), Color.WHITE);
                    tile.setPiece(piece, 0);
                }
                for(int k = 1; k < 5; k ++) {
                    Piece edgePiece = new Piece(Color.WHITE);
                    tile.setPiece(edgePiece, k);
                }
                this.board.setTile(tile, i, j);
                index++;
            }   
        }
    }
    // find the empty tile
    public Tile findEmptyTile() {
        for(int i = 0; i < this.board.getRowNum(); i++) {
            for(int j = 0; j < this.board.getColNum(); j++) {
                if(this.board.getTile(i, j).getPiece(0).getValue().equals(" ")) {
                    return this.board.getTile(i, j);
                }
            }
        }
        System.out.println("Error! No empty tile!");
        return null;
    }
    // find tiles where movable pieces are placed
    public List<Tile> findTilesofMovablePieces() {
        List<Tile> tilesofMovalbePieces = new ArrayList<>();
        Tile emptyTile = findEmptyTile();
        int x = emptyTile.getX();
        int y = emptyTile.getY();
        // top
        if(x > 0) {
            tilesofMovalbePieces.add(this.board.getTile(x - 1, y));
        }
        // bottom 
        if(x < this.board.getRowNum() - 1) {
            tilesofMovalbePieces.add(this.board.getTile(x + 1, y));
        }
        // left
        if(y > 0) {
            tilesofMovalbePieces.add(this.board.getTile(x, y - 1));
        }
        // right
        if(y < this.board.getColNum() - 1) {
            tilesofMovalbePieces.add(this.board.getTile(x, y + 1));
        }
        return tilesofMovalbePieces;
    }
    // move a piece, the input of this function will always be the tile which have a movable
    // piece on it, we will move the piece on it to the empty tile.
    public void move(Tile tile) {
        // first use a tmpPiece to reference the empty piece
        Tile emptyTile = this.findEmptyTile();
        Piece tmpPiece = emptyTile.getPiece(0);
        // move the piece to the empty tile
        emptyTile.setPiece(tile.getPiece(0), 0); 
        // set the piece on the tile to empty
        tile.setPiece(tmpPiece, 0);
    }
    // get a random tile from a list of tile
    public Tile getRandomTile(List<Tile> tiles) {
        if (tiles == null || tiles.isEmpty()) {
            return null;
        }

        Random random = new Random();
        int randomIndex = random.nextInt(tiles.size());

        return tiles.get(randomIndex);
    }
    public void shuffleBoard() {
        // randomly move the piece for level * 10 times, then the puzzle is solvable.
        for (int i = 0; i < this.level * 10; i++) {
            List<Tile> movableTiles = findTilesofMovablePieces();
            Tile randomTile = getRandomTile(movableTiles);
            move(randomTile);
        }
    }
    // check user input return 1 for valid, 2 for invalid 3 for quit
    public int checkUserInputandMove(String userInput) {
        // user can input q to quit
        if(userInput.equalsIgnoreCase("q")) {
            return 3;
        }
        // check if user input is one of the valid choice
        List<Tile> tiles = findTilesofMovablePieces();
        for(Tile tile: tiles) {
            if(userInput.equals(tile.getPiece(0).getValue())) {
                move(tile);
                return 1;
            }
        }
        return 2;
    }
    // play a slider game
    public void play() {
        boardInitialization();
        shuffleBoard();
        // the initial state may be the winning state
        while(checkWinningCondition()) {
            shuffleBoard();
        }
        Scanner scanner = new Scanner(System.in);
        while(true) {
            this.board.displayBoard();
            System.out.print("Player "+ this.teams.get(0).getPlayers().get(0).getName() +", which piece do you want to slide to the empty space? q to quit: ");
            String userInput = scanner.nextLine();
            int inputType = checkUserInputandMove(userInput);
            if(inputType == 1) {
                // no action, the piece is moved in the above method
            }else if(inputType == 2) {
                System.out.println("Invalid input!");
            }else if(inputType == 3) {
                // quit the while loop
                System.out.println("Bye!");
                break;
            }
            // check winning condition
            if(checkWinningCondition()) {
                board.displayBoard();
                System.out.println("Player " + this.teams.get(0).getPlayers().get(0).getName() + " won!");
                win();
                break;
            }
        }
        // print the scores
        printScores();
        // ask if the user wants to play one more game
        System.out.println("Do you want to play one more game? (y/n)");
        String playAgain = scanner.nextLine();
        while(!playAgain.equalsIgnoreCase("y") && !playAgain.equalsIgnoreCase("n")) {
            System.out.println("Invalid input!");
            System.out.println("Do you want to play one more game? (y/n)");
            playAgain = scanner.nextLine();
        }
        if(playAgain.equalsIgnoreCase("y")) {
            // start a new game
            play();
        }
    }
    // first game, customize needed
    public void firstGame() {
        customize();
        play();
    }
    // check winning condition
    public boolean checkWinningCondition() {
        int index = 1;
        // check if every piece is on the correct position
        for(int i = 0; i < this.board.getRowNum(); i++) {
            for(int j = 0; j < this.board.getColNum(); j++) {
                if(!this.board.getTile(i, j).getPiece(0).getValue().equals(Integer.toString(index))) {
                    return false;
                }
                index++;
                // if reach the last tile, the winning condition is satisfied.
                if(index == this.board.getRowNum() * this.board.getColNum()) {
                    return true;
                }
            }
        }
        return true;
    }

    // winning award
    public void win() {
        // get current score
        int currentScore = this.teams.get(0).getPlayers().get(0).getScore();
        // award point is 100 * level to the player
        this.teams.get(0).getPlayers().get(0).setScore(currentScore + level * 100);
    }
}
