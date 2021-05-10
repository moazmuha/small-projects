package OthelloGame.othello;

import java.util.ArrayList;
import java.util.Random;

/**
 * Capture an Othello game. This includes an OthelloBoard as well as knowledge
 * of how many moves have been made, whosTurn is next (OthelloBoard.P1 or
 * OthelloBoard.P2). It knows how to make a move using the board and can tell
 * you statistics about the game, such as how many tokens P1 has and how many
 * tokens P2 has. It knows who the winner of the game is, and when the game is
 * over.
 * 
 * See the following for a short, simple introduction.
 * https://www.youtube.com/watch?v=Ol3Id7xYsY4
 * 
 * @author arnold, Moaz
 *
 */
public class Othello {
	public static final int DIMENSION = 8; // This is an 8x8 game
	private char whosTurn = OthelloBoard.P1; // P1 moves first!
	private int numMoves = 0;
	private OthelloBoard board = new OthelloBoard(DIMENSION);

	/**
	 * return P1,P2 or EMPTY depending on who moves next.
	 * 
	 * @return P1, P2 or EMPTY
	 */
	public char getWhosTurn() {
		return this.whosTurn;
	}

	
	/** 
	 * 
	 * @return Move, a cooridnate of the best move the current player can make.
	 */
	public Move bestMove(){
		return board.bestMove(this.getWhosTurn());
	}

	
	/** 
	 * @return ArrayList<Move>, an arraylist of all possible moves the current player can make.
	 */
	public ArrayList<Move> possibleMoves(){
		return board.possibleMoves(this.getWhosTurn());
	}

	/**
	 * Attempt to make a move for P1 or P2 (depending on whos turn it is) at
	 * position row, col. A side effect of this method is modification of whos turn
	 * and the move count.
	 * 
	 * @param row
	 * @param col
	 * @return whether the move was successfully made.
	 */
	public boolean move(int row, int col) {
		if (this.whosTurn!=OthelloBoard.EMPTY){
			boolean move = board.move(row, col, this.whosTurn);
			if (move){
				char moves = board.hasMove();
				if (this.whosTurn==OthelloBoard.P1&&(moves==OthelloBoard.BOTH||moves==OthelloBoard.P2)) {
					this.whosTurn = OthelloBoard.P2;
				}else if (this.whosTurn==OthelloBoard.P2&&(moves==OthelloBoard.BOTH||moves==OthelloBoard.P1)) {
					this.whosTurn = OthelloBoard.P1;
				}else if (this.isGameOver()){
					this.whosTurn= OthelloBoard.EMPTY;
				}
				this.numMoves += 1;
			}return move;
		}
		return false;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens for player on the board
	 */
	public int getCount(char player) {
		return board.getCount(player);
	}

	/**
	 * Returns the winner of the game.
	 * 
	 * @return P1, P2 or EMPTY for no winner, or the game is not finished.
	 */
	public char getWinner() {
		if (isGameOver()){
			if (board.getCount(OthelloBoard.P1)>board.getCount(OthelloBoard.P2)){
				return OthelloBoard.P1;
			}if (board.getCount(OthelloBoard.P1)<board.getCount(OthelloBoard.P2)){
				return OthelloBoard.P2;
			}
		}
		return OthelloBoard.EMPTY;
	}

	/**
	 * 
	 * @return whether the game is over (no player can move next)
	 */
	public boolean isGameOver() {
		if (board.hasMove() == OthelloBoard.EMPTY){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return a string representation of the board.
	 */
	public String getBoardString() {
		return board.toString();
	}

	/**
	 * run this to test the current class. We play a completely random game. DO NOT
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Random rand = new Random();

		Othello o = new Othello();
		System.out.println(o.getBoardString());
		while (!o.isGameOver()) {
			int row = rand.nextInt(8);
			int col = rand.nextInt(8);

			if (o.move(row, col)) {
				System.out.println("makes move (" + row + "," + col + ")");
				System.out.println(o.getBoardString() + o.getWhosTurn() + " moves next");
			}
		}
	}
}