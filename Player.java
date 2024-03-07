/*
 * Player.java
 * By Heyang Yu (jhyyu@bu.edu)
 * This class is for player
 * A player is only allowed to be a team member of one team
 */

public class Player {
    private String name;
    private int score; // get 100 points after winning one game
    private int teamNo; // a player should only belong to one team
    // constructor
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }
    // getters
    public String getName() {
        return this.name;
    }
    public int getScore() {
        return this.score;
    }
    public int getTeamNo() {
        return this.teamNo;
    }
    // setters
    public void setScore(int score) {
        this.score = score;
    }
    public void setTeamNo(int teamNo) {
        this.teamNo = teamNo;
    }
}
