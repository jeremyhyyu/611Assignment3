/* 
 * Team.java
 * By Heyang Yu (jhyyu@bu.edu)
 * This class is the class of team.
 * There can be multi players in a team.
*/

import java.util.ArrayList;
import java.util.List;

public class Team {
    private int playerCnt;
    private List<Player> players;
    private String teamName;
    private int teamNo;
    private int currentPlayerIndex;
    // constructor
    public Team(String name, int playerCnt) {
        this.teamName = name;
        this.playerCnt = playerCnt;
        this.players = new ArrayList<>();
    }
    // getters
    public String getTeamName() {
        return this.teamName;
    }
    public int getPlayerCount() {
        return this.playerCnt;
    }
    public int getTeamNo() {
        return this.teamNo;
    }
    public List<Player> getPlayers() {
        return this.players;
    }
    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }
    public Player getCurrentPlayer() {
        return this.players.get(currentPlayerIndex);
    }
    // setters
    public void setTeamNo(int teamNo) {
        this.teamNo = teamNo;
    }
    public void setCurrentPlayerIndex(int index) {
        if(index < 0 || index > this.playerCnt) {
            System.out.println("Invalid input!");
        }else{
            this.currentPlayerIndex = index;
        }
    }
    // methods
    // add a new player to the team
    public void addPlayer(Player newPlayer) {
        players.add(newPlayer);
        playerCnt++;
    }
}
