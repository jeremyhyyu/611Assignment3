## Student Information: 
 - Name: Heyang Yu
 - BUID: U26187790

 - Name: Zhaozhan Huang
 - BUID: U03088498

---------------------------------------------------------------------------------------------------------------------

## File Information:
 - Board.java: The class of the game board, necessary info and method are included. The most important method in this
 function is the displayBoard() function, which can print both slider and d&b game board. A new method displayIndexedBoard()
 is implemented to display board for Quoridor.

 - BoardGame.java: The class of board games, which is an abstract but we didn't make it abstract by the key word here.
 Common method of all board games like print scores are implemented here to be inherited by all sub classes. A static 
 method chooseaGame() is also implemented here to select, create and access all board games.

 - Color.java: The class of colors. The ascii code of some colors are listed in this class, this class serves as a helper
 class, static method can be called to print colored message in terminal.

 - DotsAndBoxes.java: The class for the board game dots and boxes, the rule of the game and in-game operations are defined
  in this class, this is a multiple team game, each team is represented by a color and can have multiple players in a team.

 - InputHandler.java: This is the class to handle user inputs in the terminal. I created this class to avoid redundant code. 

 - Main.java: The entry point of the program.
 
 - Piece.java: The class for pieces. A piece have value and color, value is optional as the edge piece has no value.

 - Player.java: The class for players in a game. A player is created in the customize() method of a game and it can only
 be a team member in one team. After winning a game for the team, points are awarded to the player.

 - Quoridor.java: The class for board game Quoridor. Rules and in-game actions are defined and implemented in this class.

 - QuoridorPlayer.java: The sub class of Player, designed for Quoridor, necessary variables like coordinates and wall 
 count are included in this class as player has a pawn and walls in Quoridor.

 - Slider.java: This is the class for the board game slider. The rules and in-game operations are included in this class.

 - Team.java: This is the class for teams. A team is created in the customize() method of a game, there could be multiple
 players in a team.

 - Tile.java: The class for tile (grid on the board). To adapt to both games, there could be 5 pieces in a tile, represent
 center and 4 edges respectively.

---------------------------------------------------------------------------------------------------------------------

## Notes:
1. Slider is a single team single player game, the size of the game board is restricted to 2-5, the level represents the
difficulty of the game, which is actually the step to go back from the initial (finished) state. The award to the player,
which is score, follows score = 100 * level.

2. Dots and boxes is a multiple teams multiple players game, there are at most 8 teams and each team is represent by a color,
there could be at most 4 players in a team, after each game, every team switches its player. After winning a game as a single
winner (owning the largerest number boxes) the player is awarded 100pts, if there are more than 1 winners, all winners are
awarded 50pts.

3. Color and InputHandler classes are created to serve as helper class, only static method and constant are defined in these
two classes.

4. Quoridor is a multi team multi player board game, there are at most 4 teams and each team is represented by a color and 
initialized on a side of the board, there could be at most 4 players in a team, after each game, every team switches its player.
Winner will be awarded by 100 pts.

---------------------------------------------------------------------------------------------------------------------

## Citations
https://chat.openai.com, I finished the Color class with the help of chatGPT.

---------------------------------------------------------------------------------------------------------------------

## How to compile and run
1. Go to the project folder and open the terminal.
2. Run the following instructions:
javac Board.java BoardGame.java Color.java DotsAndBoxes.java InputHandler.java Main.java Piece.java Player.java Slider.java Team.java Tile.java Quoridor.java QuoridorPlayer.java
java Main

---------------------------------------------------------------------------------------------------------------------

## Input/Output Example
In this part, I will only play a single Quoridor game as there's a lot of output.

Welcome to CS611 Board Game!
Which game do you want to play? 1: Slider 2: Dots and Boxes 3: Quoridor Q: Quit
3
You chose Quoridor
Input an integer within 5-9 as the length of side: 5
Input an integer within 2-4 as the number of teams: 2
Team 1, please input your team name: king
Input an integer within 1-4 as the number of player: 1
Team king player 1, please input your player name: jeremy
Team 2, please input your team name: queen
Input an integer within 1-4 as the number of player: 1
Team queen player 1, please input your player name: zhaozhan
  0  1  2  3  4  5
0 +  +  +  +  +  +
         K        
1 +  +  +  +  +  +
                  
2 +  +  +  +  +  +
                  
3 +  +  +  +  +  +
                  
4 +  +  +  +  +  +
         Q        
5 +  +  +  +  +  +
You are Red!
Player jeremy, please choose your action. 1: move your pawn; 2: add a wall; q: quit. Your choice is: 1
  0  1  2  3  4  5
0 +  +  +  +  +  +
      2  K  3     
1 +  +  +  +  +  +
         1        
2 +  +  +  +  +  +
                  
3 +  +  +  +  +  +
                  
4 +  +  +  +  +  +
         Q        
5 +  +  +  +  +  +
Input an integer within 1-3 as the move: 1
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
         K        
2 +  +  +  +  +  +
                  
3 +  +  +  +  +  +
                  
4 +  +  +  +  +  +
         Q        
5 +  +  +  +  +  +
You are Blue!
Player zhaozhan, please choose your action. 1: move your pawn; 2: add a wall; q: quit. Your choice is: 2
Where would you like to add the wall? Please input xy coordinate, x for row and y for column.
Input an integer within 1-4 as the X coordinate: 2
Input an integer within 1-4 as the Y coordinate: 2
Please input the direction of the wall. 1: horizontal; 2: vertical; q: return. Your choice is: 1
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
         K        
2 +  +--+--+  +  +
                  
3 +  +  +  +  +  +
                  
4 +  +  +  +  +  +
         Q        
5 +  +  +  +  +  +
You are Red!
Player jeremy, please choose your action. 1: move your pawn; 2: add a wall; q: quit. Your choice is: 1
  0  1  2  3  4  5
0 +  +  +  +  +  +
         1        
1 +  +  +  +  +  +
      2  K  3     
2 +  +--+--+  +  +
                  
3 +  +  +  +  +  +
                  
4 +  +  +  +  +  +
         Q        
5 +  +  +  +  +  +
Input an integer within 1-3 as the move: 3
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
            K     
2 +  +--+--+  +  +
                  
3 +  +  +  +  +  +
                  
4 +  +  +  +  +  +
         Q        
5 +  +  +  +  +  +
You are Blue!
Player zhaozhan, please choose your action. 1: move your pawn; 2: add a wall; q: quit. Your choice is: 1
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
            K     
2 +  +--+--+  +  +
                  
3 +  +  +  +  +  +
         1        
4 +  +  +  +  +  +
      2  Q  3     
5 +  +  +  +  +  +
Input an integer within 1-3 as the move: 1
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
            K     
2 +  +--+--+  +  +
                  
3 +  +  +  +  +  +
         Q        
4 +  +  +  +  +  +
                  
5 +  +  +  +  +  +
You are Red!
Player jeremy, please choose your action. 1: move your pawn; 2: add a wall; q: quit. Your choice is: 1
  0  1  2  3  4  5
0 +  +  +  +  +  +
            1     
1 +  +  +  +  +  +
         3  K  4  
2 +  +--+--+  +  +
            2     
3 +  +  +  +  +  +
         Q        
4 +  +  +  +  +  +
                  
5 +  +  +  +  +  +
Input an integer within 1-4 as the move: 2
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
                  
2 +  +--+--+  +  +
            K     
3 +  +  +  +  +  +
         Q        
4 +  +  +  +  +  +
                  
5 +  +  +  +  +  +
You are Blue!
Player zhaozhan, please choose your action. 1: move your pawn; 2: add a wall; q: quit. Your choice is: 1
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
                  
2 +  +--+--+  +  +
         1  K     
3 +  +  +  +  +  +
      3  Q  4     
4 +  +  +  +  +  +
         2        
5 +  +  +  +  +  +
Input an integer within 1-4 as the move: 4
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
                  
2 +  +--+--+  +  +
            K     
3 +  +  +  +  +  +
            Q     
4 +  +  +  +  +  +
                  
5 +  +  +  +  +  +
You are Red!
Player jeremy, please choose your action. 1: move your pawn; 2: add a wall; q: quit. Your choice is: 1
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
            1     
2 +  +--+--+  +  +
         3  K  4  
3 +  +  +  +  +  +
            Q     
4 +  +  +  +  +  +
            2     
5 +  +  +  +  +  +
Input an integer within 1-4 as the move: 2
  0  1  2  3  4  5
0 +  +  +  +  +  +
                  
1 +  +  +  +  +  +
                  
2 +  +--+--+  +  +
                  
3 +  +  +  +  +  +
            Q     
4 +  +  +  +  +  +
            K     
5 +  +  +  +  +  +
Team 1 player jeremy won!
Name            Team            Score          
jeremy          king            100            
zhaozhan        queen           0              
Do you want to play one more game? (y/n): n
Bye!
Which game do you want to play? 1: Slider 2: Dots and Boxes 3: Quoridor Q: Quit
q
See you next time!