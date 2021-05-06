package OthelloGame.othello;
/**
 * Capture a Move object which is a coordinate. Keeps
 * track of row and column of coordinate.
 * 
 * @author Moaz
 *
 */
public class Move {
	private int row, col;

	
	/** 
	 * Construct a Move object with the given row and column.
	 * 
	 * @param row the row of the Move coordinate
	 * @param col the column of the Move coordinate
	 */
	public Move(int row, int col) {
		this.row = row;
		this.col = col;
	}

	
	/** 
	 * @return the row of this Move coordinate
	 */
	public int getRow() {
		return row;
	}

	
	/** 
	 * @return the column of this Move coordinate
	 */
	public int getCol() {
		return col;
	}

	
	/** 
	 * @return the String representation of this Move object
	 */
	public String toString() {
		return "(" + this.row + "," + this.col + ")";
	}
}
