package OthelloGame.othello;

public abstract class Player{
    public Othello othello;
    public char player;

    
    /** 
     * Intialize a new Player who is playing game othello 
     * and is represented by character player.
     * 
     * @param othello othello game 
     * @param player character represting the Player
     */
    public Player(Othello othello, char player) {
		this.othello = othello;
		this.player = player;
    }
    
    /** 
	  * Get this Player's next move.
	  */
    abstract public Move getMove();


}