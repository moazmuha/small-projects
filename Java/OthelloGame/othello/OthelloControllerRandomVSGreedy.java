package OthelloGame.othello;

/**
 * The goal here is to print out the probability that Random wins and Greedy
 * wins as a result of playing 10000 games against each other with P1=Random and
 * P2=Greedy. What is your conclusion, which is the better strategy?
 * @author arnold, Moaz
 *
 */
public class OthelloControllerRandomVSGreedy extends OthelloController {
	static String player1Type = "Random";
	static String player2Type = "Greedy";

	/**
	 * Constructs a new OthelloController with a new Othello game, ready to play
	 * with two users at the console.
	 */
	public OthelloControllerRandomVSGreedy(){
		super(player1Type, player2Type);
	}

	/** 
	 * Have the players play the gmae while the game is not over
	 */
	public void play() {
		
		while (!othello.isGameOver()) {

			Move move = null;
			char whosTurn = othello.getWhosTurn();

			if (whosTurn == OthelloBoard.P1)
				move = player1.getMove();
			if (whosTurn == OthelloBoard.P2)
				move = player2.getMove();

			othello.move(move.getRow(), move.getCol());
		}
	}

	/**
	 * Run main to execute the simulation and print out the two line results.
	 * Output looks like: 
	 * Probability P1 wins=.75 
	 * Probability P2 wins=.20
	 * @param args
	 */
	public static void main(String[] args) {
		
		int p1wins = 0, p2wins = 0, numGames = 10000;
		for(int game=0;game<numGames;game++){
			OthelloControllerRandomVSGreedy oc = new OthelloControllerRandomVSGreedy();
			oc.play();
			if(oc.othello.getWinner()==oc.player1.player){
				p1wins++;
			}else if(oc.othello.getWinner()==oc.player2.player){
				p2wins++;
			}
		}

		System.out.println("Probability P1 wins=" + (float) p1wins / numGames);
		System.out.println("Probability P2 wins=" + (float) p2wins / numGames);
	}
}
