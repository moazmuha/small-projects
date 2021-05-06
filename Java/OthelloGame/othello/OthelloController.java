package OthelloGame.othello;

public abstract class OthelloController{
    protected Othello othello;
    protected Player player1;
    protected Player player2;
    
    
	/** 
	 * Intialize a new controller and create the specified players.
	 * 
	 * @param player1Type a letter identifying the type of player1
	 * @param player2Type a letter identifying the type of player2
	 */
	public OthelloController(String player1Type, String player2Type) {
        this.othello = new Othello();
        if (player1Type == "Human"){
			this.player1 = new PlayerHuman(this.othello, OthelloBoard.P1);
        }else if (player1Type == "Greedy"){
            this.player1 = new PlayerGreedy(this.othello, OthelloBoard.P1);
        }else if (player1Type == "Random"){
            this.player1 = new PlayerRandom(this.othello, OthelloBoard.P1);
        }if (player2Type == "Human"){
            this.player2 = new PlayerHuman(this.othello, OthelloBoard.P2);
        }else if (player2Type == "Greedy"){
            this.player2 = new PlayerGreedy(this.othello, OthelloBoard.P2);
        }else if (player2Type == "Random"){
            this.player2 = new PlayerRandom(this.othello, OthelloBoard.P2);
        }
        
	}

    /** 
	 * Have the players play the game while the game is not over
	 */
    public void play() {
		
		while (!othello.isGameOver()) {
			this.report();

			Move move = null;
			char whosTurn = othello.getWhosTurn();

			if (whosTurn == OthelloBoard.P1)
				move = player1.getMove();
			if (whosTurn == OthelloBoard.P2)
				move = player2.getMove();

			this.reportMove(whosTurn, move);
			othello.move(move.getRow(), move.getCol());
		}
		this.reportFinal();
	}

    
	/** 
	 * Print out a string stating which player's turn it is and the move the player makes.
	 * @param whosTurn the char of the player making the move
	 * @param move the move the player makes
	 */
	protected void reportMove(char whosTurn, Move move) {
		System.out.println(whosTurn + " makes move " + move + "\n");
	}

	/** 
	 * Print out a string represtentation of the board 
	 * including the count of each player's tokens and 
	 * which player will make a move next 
	 */
    protected void report() {
		
		String s = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) + "  " 
				+ othello.getWhosTurn() + " moves next";
		System.out.println(s);
    }
	
	/** 
	 * 
	 * Print out a string representation of the final state of the 
	 * board and number of tokens of each player on the board and which 
	 * player won the game
	 * 
	 */
    protected void reportFinal() {
		
		String s = othello.getBoardString() + OthelloBoard.P1 + ":" 
				+ othello.getCount(OthelloBoard.P1) + " "
				+ OthelloBoard.P2 + ":" + othello.getCount(OthelloBoard.P2) 
				+ "  " + othello.getWinner() + " won\n";
		System.out.println(s);
	}
}