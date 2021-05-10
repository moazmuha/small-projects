package OthelloGame.othello;

/**
 * PlayerGreedy makes a move by considering all possible moves that the player
 * can make. Each move leaves the player with a total number of tokens.
 * getMove() returns the first move which maximizes the number of
 * tokens owned by this player. In case of a tie, between two moves,
 * (row1,column1) and (row2,column2) the one with the smallest row wins. In case
 * both moves have the same row, then the smaller column wins.
 * 
 * Example: Say moves (2,7) and (3,1) result in the maximum number of tokens for
 * this player. Then (2,7) is returned since 2 is the smaller row.
 * 
 * Example: Say moves (2,7) and (2,4) result in the maximum number of tokens for
 * this player. Then (2,4) is returned, since the rows are tied, but (2,4) has
 * the smaller column.
 * 
 * See the examples supplied in the assignment handout.
 * 
 * @author arnold, Moaz
 *
 */

public class PlayerGreedy extends Player{
	
	/** 
	 * Creates a new PlayerGreedy and specifies the Othello game it is playing 
	 * and the char that represents the player in the game, specifying
	 * wheather it will be player 1(X) or player 2(O).
	 * 
	 * @param othello the game this player is set to play
	 * @param player the char that will represent this player in the game. 
	 */
	public PlayerGreedy(Othello othello, char player) {
		super(othello, player);
	}

	/** 
	 * returns the move that this player will make 
	 * according to its stratrgy
	 * @return Move
	 */
	public Move getMove() {
		return this.othello.bestMove();
	}
}
