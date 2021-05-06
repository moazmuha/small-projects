package OthelloGame.othello;

import java.util.ArrayList;

/**
 * Keep track of all of the tokens on the board. This understands some
 * interesting things about an Othello board, what the board looks like at the
 * start of the game, what the players tokens look like ('X' and 'O'), whether
 * given coordinates are on the board, whether either of the players have a move
 * somewhere on the board, what happens when a player makes a move at a specific
 * location (the opposite players tokens are flipped).
 * 
 * Othello makes use of the OthelloBoard.
 * 
 * @author arnold
 *
 */
public class OthelloBoard {
	
	public static final char EMPTY = ' ', P1 = 'X', P2 = 'O', BOTH = 'B';
	private int dim = 8;
	private char[][] board;

	
	/** 
	 * Create a OthelloBoard with the dimensions dim by dim,
	 * which is empty at every spot except for the 4 middle spots
	 * where there are 2 of each player's tokens.
	 * @param dim The dimension of the board
	 */
	public OthelloBoard(int dim) {
		this.dim = dim;
		board = new char[this.dim][this.dim];
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				this.board[row][col] = EMPTY;
			}
		}
		int mid = this.dim / 2;
		this.board[mid - 1][mid - 1] = this.board[mid][mid] = P1;
		this.board[mid][mid - 1] = this.board[mid - 1][mid] = P2;
	}

	
	/** 
	 * @return the dimesion of this OthelloBoard
	 */
	public int getDimension() {
		return this.dim;
	}

	/**
	 * @param player either P1 or P2
	 * @return P2 or P1, the opposite of player
	 */
	public static char otherPlayer(char player) {
		if(player==P1){
			return P2;
		}if(player==P2){
			return P1;
		}
		return EMPTY;
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return P1,P2 or EMPTY, EMPTY is returned for an invalid (row,col)
	 */
	public char get(int row, int col) {
		if (!validCoordinate(row, col)){
			return EMPTY;
		}
		return this.board[row][col];
	}

	/**
	 * 
	 * @param row starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @return whether (row,col) is a position on the board. Example: (6,12) is not
	 *         a position on the board.
	 */
	private boolean validCoordinate(int row, int col) { 
		if (row>this.dim-1||col>this.dim-1||row<0||col<0){
			return false;
		}return true;
	}

	/**
	 * Check if there is an alternation of P1 next to P2, starting at (row,col) in
	 * direction (drow,dcol). That is, starting at (row,col) and heading in
	 * direction (drow,dcol), you encounter a sequence of at least one P1 followed
	 * by a P2, or at least one P2 followed by a P1. The board is not modified by
	 * this method. Why is this method important? If
	 * alternation(row,col,drow,dcol)==P1, then placing P1 right before (row,col),
	 * assuming that square is EMPTY, is a valid move, resulting in a collection of
	 * P2 being flipped.
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1, if there is an alternation P2 ...P2 P1, or P2 if there is an
	 *         alternation P1 ... P1 P2 in direction (dx,dy), EMPTY if there is no
	 *         alternation
	 */
	private char alternation(int row, int col, int drow, int dcol) {
		if (this.validCoordinate(row, col)&&this.get(row,col)!=EMPTY&&!(drow==0&&dcol==0)){
			char player = this.get(row, col);
			int moveRow = row+drow;
			int moveCol = col+dcol;
			while (this.validCoordinate(moveRow, moveCol)&&this.get(moveRow,moveCol) != EMPTY){
				if (this.get(moveRow, moveCol) == otherPlayer(player)){
					return otherPlayer(player);
				} 
				moveRow += drow;
				moveCol += dcol;	
			}
		}return EMPTY;
	}

	/**
	 * flip all other player tokens to player, starting at (row,col) in direction
	 * (drow, dcol). Example: If (drow,dcol)=(0,1) and player==O then XXXO will
	 * result in a flip to OOOO
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow   the row direction, in {-1,0,1}
	 * @param dcol   the col direction, in {-1,0,1}
	 * @param player Either OthelloBoard.P1 or OthelloBoard.P2, the target token to
	 *               flip to.
	 * @return the number of other player tokens actually flipped, -1 if this is not
	 *         a valid move in this one direction, that is, EMPTY or the end of the
	 *         board is reached before seeing a player token.
	 */
	private int flip(int row, int col, int drow, int dcol, char player) {
		if (this.validCoordinate(row, col)){
			int numFlipped = 0;
			if ((alternation(row, col, drow, dcol))== otherPlayer(player)){
				return numFlipped;
			}if ((alternation(row, col, drow, dcol)==player)){
				this.board[row][col] = player;
				numFlipped += 1;
				int moveRow = row+drow;
				int moveCol = col+dcol;
				while (this.validCoordinate(moveRow, moveCol)&&this.get(moveRow, moveCol)==otherPlayer(player)){
							this.board[moveRow][moveCol] = player;
							numFlipped += 1;
					 		moveRow+=drow;
					 		moveCol+=dcol;
					 	}return numFlipped;
			}
		}return -1;
	}

	
	/** 
	 * Return the number of tokens player will flip in direction 
	 * (drow, dcol) if it places its token at the specified (row,col)
	 * 
	 * @param row the row player places its token
	 * @param col the column player places its token
	 * @param drow the row direction player flips token in
	 * @param dcol the column direction player flips token in
	 * @param player the player checking its tokens flipped
	 * @return number of tokens that would be flipped  
	 */
	private int numFlip(int row, int col, int drow, int dcol, char player) {
		if (this.validCoordinate(row, col)){
			int numFlipped = 0;
			if ((alternation(row, col, drow, dcol))== otherPlayer(player)){
				return numFlipped;
			}if ((alternation(row, col, drow, dcol)==player)){
				numFlipped += 1;
				int moveRow = row+drow;
				int moveCol = col+dcol;
				while (this.validCoordinate(moveRow, moveCol)&&this.get(moveRow, moveCol)==otherPlayer(player)){
							numFlipped += 1;
					 		moveRow+=drow;
					 		moveCol+=dcol;
					 	}return numFlipped;
			}
		}return -1;
	}
	

	/**
	 * Return which player has a move (row,col) in direction (drow,dcol).
	 * 
	 * @param row  starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col  starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param drow the row direction, in {-1,0,1}
	 * @param dcol the col direction, in {-1,0,1}
	 * @return P1,P2,EMPTY
	 */
	private char hasMove(int row, int col, int drow, int dcol) {
		if(this.validCoordinate(row, col)&&this.board[row][col]==EMPTY){
			char move = alternation(row+drow, col+dcol, drow, dcol);
			return move;
		}
		return EMPTY;
	}

	/**
	 * 
	 * @return whether P1,P2 or BOTH have a move somewhere on the board, EMPTY if
	 *         neither do.
	 */
	public char hasMove() {
		boolean p1HasMove = false;
		boolean p2HasMove = false;
		int[][] alternation =  {{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{-1,1},{1,-1}};
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				if (this.board[row][col]==EMPTY){
					for (int[] direction : alternation) {
						char move = this.hasMove(row, col, direction[0], direction[1]);
						if (p1HasMove&&p2HasMove){
							return BOTH;
						}
						if (!p1HasMove&&move==P1){
							p1HasMove = true;	
						}if(!p2HasMove&&move==P2){
							p2HasMove = true;
						}
					}
				}
			}
		}
		if(p1HasMove){return P1;}
		if(p2HasMove){return P2;}
		return EMPTY;
	}
	
		
	/** 
	 * Return a Move object which specifies a (row, column) which 
	 * will be the best possible move for the current player resulting 
	 * in the most tokens flipped.
	 * 
	 * @param player the player checking its best possible move
	 * @return Move, a coordinate where the player's token should be placed for the best move
	 */
	public Move bestMove(char player){
		if (this.hasMove()==BOTH||this.hasMove()==player){
			int[][] alternation =  {{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{-1,1},{1,-1}};
			int x=-1;
			int y=-1;
			int maxFlipped = -1;
			for (int row = 0; row < this.dim; row++) {
				for (int col = 0; col < this.dim; col++) {
					if (this.get(row,col)==EMPTY){
						int flipped = 0;
						for (int[] direction : alternation){ 
							if (this.hasMove(row, col, direction[0], direction[1])==player){
								int flips = this.numFlip(row+direction[0],col+direction[1],direction[0],direction[1],player);
								if (flips>0){
									flipped += flips;
								}
							}
						}
						if(flipped>maxFlipped){
							maxFlipped=flipped;
							x = row;
							y = col;
						}
					}
				}
			}return new Move(x,y);
		}return null;
	}

	
	/**
	 * Create and return and ArrayList of all the Moves player can make. 
	 * 
	 * @param player the player chechking all of its possible moves
	 * @return ArrayList<Move>, An arraylist of all the possible moves the current player can make.
	 */
	public ArrayList<Move> possibleMoves(char player){
		ArrayList<Move> moves = new ArrayList<Move>();
		if (this.hasMove()==BOTH||this.hasMove()==player){
			int[][] alternation =  {{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{-1,1},{1,-1}};
			for (int row = 0; row < this.dim; row++) {
				for (int col = 0; col < this.dim; col++) {
					if (this.get(row,col)==EMPTY){
						for (int[] direction : alternation){ 
							if (this.hasMove(row, col, direction[0], direction[1])==player){
								Move move = new Move(row, col);
								moves.add(move);
								break; //we don't want to add the same move twice so we break 
								}
							}
						}
					}
				}
			}return moves;
		}
		
		

	/**
	 * Make a move for player at position (row,col) according to Othello rules,
	 * making appropriate modifications to the board. Nothing is changed if this is
	 * not a valid move.
	 * 
	 * @param row    starting row, in {0,...,dim-1} (typically {0,...,7})
	 * @param col    starting col, in {0,...,dim-1} (typically {0,...,7})
	 * @param player P1 or P2
	 * @return true if player moved successfully at (row,col), false otherwise
	 */
	public boolean move(int row, int col, char player) {
		int[][] alternation =  {{0,1},{0,-1},{1,0},{-1,0},{1,1},{-1,-1},{-1,1},{1,-1}};
		boolean move = false;
		if (this.validCoordinate(row, col)&&this.board[row][col]==EMPTY){
			for(int[] direction: alternation){
				if (hasMove(row, col, direction[0], direction[1])==player){
					move = true;
					this.flip(row+direction[0],col+direction[1],direction[0],direction[1],player);
				}
			}
		}if(move){this.board[row][col]=player;}
		return move;
	}

	/**
	 * 
	 * @param player P1 or P2
	 * @return the number of tokens on the board for player
	 */
	public int getCount(char player) {
		int count = 0;
		for (int row = 0; row < this.dim; row++) {
			for (int col = 0; col < this.dim; col++) {
				if (this.board[row][col] == player){
					count += 1; 
				} 
			}
		}
		return count;
	}

	/**
	 * @return a string representation of this, just the play area, with no
	 *         additional information. DO NOT MODIFY THIS!!
	 */
	public String toString() {
		String s = "";
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';

		s += " +";
		for (int col = 0; col < this.dim; col++) {
			s += "-+";
		}
		s += '\n';

		for (int row = 0; row < this.dim; row++) {
			s += row + "|";
			for (int col = 0; col < this.dim; col++) {
				s += this.board[row][col] + "|";
			}
			s += row + "\n";

			s += " +";
			for (int col = 0; col < this.dim; col++) {
				s += "-+";
			}
			s += '\n';
		}
		s += "  ";
		for (int col = 0; col < this.dim; col++) {
			s += col + " ";
		}
		s += '\n';
		return s;
	}

	/**
	 * A quick test of OthelloBoard.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		OthelloBoard ob = new OthelloBoard(8);
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));
		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				ob.board[row][col] = P1;
			}
		}
		System.out.println(ob.toString());
		System.out.println("getCount(P1)=" + ob.getCount(P1));
		System.out.println("getCount(P2)=" + ob.getCount(P2));

		// Should all be blank
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		for (int row = 0; row < ob.dim; row++) {
			for (int col = 0; col < ob.dim; col++) {
				if (row == 0 || col == 0) {
					ob.board[row][col] = P2;
				}
			}
		}

		System.out.println(ob.toString());

		// Should all be P2 (O) except drow=0,dcol=0
		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("alternation=" + ob.alternation(4, 4, drow, dcol));
			}
		}

		// Can't move to (4,4) since the square is not empty
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));

		ob.board[4][4] = EMPTY;
		ob.board[2][4] = EMPTY;

		System.out.println(ob.toString());

		for (int drow = -1; drow <= 1; drow++) {
			for (int dcol = -1; dcol <= 1; dcol++) {
				System.out.println("direction=(" + drow + "," + dcol + ")");
				System.out.println("hasMove at (4,4) in above direction =" + ob.hasMove(4, 4, drow, dcol));
			}
		}
		System.out.println("who has a move=" + ob.hasMove());
		System.out.println("Trying to move to (4,4) move=" + ob.move(4, 4, P2));
		System.out.println(ob.toString());

	}
}
