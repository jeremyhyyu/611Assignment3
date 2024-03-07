/*
 * BoardGame.java
 * By Heyang Yu (jhyyu@bu.edu)
 * This class is the super class for all board games.
 * A game board and a list of team exists in all board games.
 * This class is also the starting point of choosing game to play, which is implemented in the static method chooseaGame().
 * Common actions of all board games like add teams and print scores are also implemented in this class.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BoardGame {
    protected Board board;
    protected List<Team> teams;
    // private int type; // 0 for slider and 1 for dots and boxes ...
    // Constructor
    public BoardGame(Board board) {
        this.board = board;
        this.teams = new ArrayList<>();
    }
    // methods
    // add a team to the game
    public void addTeam(Team newTeam) {
        teams.add(newTeam);
    }
    // print the scores
    public void printScores() {
        System.out.printf("%-15s %-15s %-15s\n", "Name", "Team", "Score");
        for(Team team: this.teams) {
            for(Player player: team.getPlayers()) {
                System.out.printf("%-15s %-15s %-15s\n", player.getName(), team.getTeamName(), player.getScore());
            }
        }
    }
    // choose a game
    public static void chooseaGame() {
        System.out.println("Welcome to CS611 Board Game!");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // ask for user's choice
            System.out.println("Which game do you want to play? 1: Slider 2: Dots and Boxes Q: Quit");
            String userChoice = scanner.nextLine();
            if(userChoice.equals("1")) {
                System.out.println("You chose Slider.");
                // ask user to customize a slider game
                // height
                int height = InputHandler.getAnIntegerInARange("height of the board", Slider.MIN_R, Slider.MAX_R);
                // width
                int width = InputHandler.getAnIntegerInARange("width of the board", Slider.MIN_C, Slider.MAX_C);
                // level (difficulty)
                int level = InputHandler.getAnIntegerInARange("level of the game", Slider.MIN_LEVEL, Slider.MAX_LEVEL);
                // create the slider game and play
                Board board = new Board(height, width);
                Slider slider = new Slider(board, level);
                slider.firstGame();
            }else if(userChoice.equals("2")) {
                // customize dots and boxes
                System.out.println("You chose Dots and Boxes.");
                // ask user to customize a d&b game
                // height
                int height = InputHandler.getAnIntegerInARange("height of the board", DotsAndBoxes.MIN_R, DotsAndBoxes.MAX_R);
                // width
                int width = InputHandler.getAnIntegerInARange("width of the board", DotsAndBoxes.MIN_C, DotsAndBoxes.MAX_C);
                // number of teams
                int teamCnt = InputHandler.getAnIntegerInARange("number of teams", DotsAndBoxes.MIN_TEAM, DotsAndBoxes.MAX_TEAM);
                // create the slider game and play
                Board board = new Board(height, width);
                DotsAndBoxes db = new DotsAndBoxes(board, teamCnt);
                db.firstGame();
            }else if(userChoice.equalsIgnoreCase("q")) {
                // quit the program
                System.out.println("See you next time!");
                break;
            }else {
                System.out.println("Invalid Choice!");
            }
        }
        scanner.close();
    }
    // main
    public static void main(String[] args) {
        // System.out.println("Testing Game");
        // Board board = new Board(3, 3);
        // Slider slider = new Slider(board, 1);
        // slider.play();
        // slider.printScores();
    }
}
