package OthelloGame.othello;
import java.util.ArrayList;
import java.util.Random;

/**
 * PlayerRandom makes a move by first determining all possible moves that this
 * player can make, putting them in an ArrayList, and then randomly choosing one
 * of them.
 * 
 * @author arnold
 *
 */
public class PlayerRandom extends Player{
	private Random rand = new Random();

	/** 
	 * Creates a new PlayerRandom and specifies the Othello game it is playing 
	 * and the char that represents the player in the game, specifying
	 * wheather it will be player 1(X) or player 2(O).
	 * 
	 * @param othello the game this player is set to play
	 * @param player the char that will represent this player in the game. 
	 */
	public PlayerRandom(Othello othello, char player) {
		super(othello, player);
	}
	
	/** 
	 * returns the move that this player will make 
	 * according to its stratrgy
	 * @return Move
	 */
	public Move getMove() {
		ArrayList<Move> moves =  this.othello.possibleMoves();
		return moves.get(rand.nextInt(moves.size()));
	}
}
