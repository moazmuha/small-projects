package OthelloGame.othello;

/**
 * This controller uses the Model classes to allow the Human player P1 to play
 * the computer P2. The computer, P2 uses a random strategy. 
 * 
 * @author arnold
 *
 */
public class OthelloControllerHumanVSRandom extends OthelloController{
	static String player1Type = "Human";
	static String player2Type = "Random";

	/**
	 * Constructs a new OthelloController with a new Othello game, ready to play
	 * with two users at the console.
	 */
	public OthelloControllerHumanVSRandom(){
		super(player1Type, player2Type);
	}
	
	/**
	 * Run main to play a Human (P1) against the computer P2. 
	 * The computer uses a random strategy, that is, it randomly picks 
	 * one of its possible moves.
	 * The output should be almost identical to that of OthelloControllerHumanVSHuman.

	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloControllerHumanVSRandom oc = new OthelloControllerHumanVSRandom();
		oc.play(); 
	}
}
