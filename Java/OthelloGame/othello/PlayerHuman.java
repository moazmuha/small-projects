package OthelloGame.othello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * A HumanPLayer is Player that makes move in an Othello game depdinding on user input.
 * 
 * @author arnold, Moaz
 *
 */
public class PlayerHuman extends Player{
	
	private static final String INVALID_INPUT_MESSAGE = "Invalid number, please enter 1-8";
	private static final String IO_ERROR_MESSAGE = "I/O Error";
	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

	
	/** 
	 * Creates a new PlayerHuman and specifies the Othello game it is playing 
	 * and the char that represents the player in the game, specifying
	 * wheather it will be player 1(X) or player 2(O).
	 * 
	 * @param othello the game this player is set to play
	 * @param player the char that will represent this player in the game.
	 */
	public PlayerHuman(Othello othello, char player) {
		super(othello, player);
	}

	
	/** 
	 * returns the move that this player will make
	 * @return Move
	 */
	public Move getMove() {
		
		int row = getMove("row: ");
		int col = getMove("col: ");
		return new Move(row, col);
	}

	
	/** 
	 * Takes in user input to determine the row and column
	 * of the move that this player will make. 
	 * 
	 * @param message row:  or col: 
	 * @return int if an exception is called -1 is returned
	 */
	private int getMove(String message) {
		
		int move, lower = 0, upper = 7;
		while (true) {
			try {
				System.out.print(message);
				String line = PlayerHuman.stdin.readLine();
				move = Integer.parseInt(line);
				if (lower <= move && move <= upper) {
					return move;
				} else {
					System.out.println(INVALID_INPUT_MESSAGE);
				}
			} catch (IOException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
				break;
			} catch (NumberFormatException e) {
				System.out.println(INVALID_INPUT_MESSAGE);
			}
		}
		return -1;
	}
}
