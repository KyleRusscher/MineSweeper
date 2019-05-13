package Project2;


import java.util.ArrayList;
import java.util.Random;


/******************************************************************
 * MineSweeperGame class that controls all game logic for the
 * minesweeper panel.
 *
 * @author Kyle Russcher
 *
 */
public class MineSweeperGame {
	/** 2d array that holds each cell on the board */
	private Cell[][] board;

	/** Hold the status of the game (Win, Loss, NotOverYer) */
	private GameStatus status;

	/** Holds the value for the width and height of the board */
	private int boardSize;

	/** Holds the value for the number of mines on the board */
	private int numMines;

	/** Number of wins in the current game */
	private int wins = 0;

	/** Number of losses in the current game */
	private int losses = 0;

	/******************************************************************
	 * Constructor for the MineSweeperGame class.
	 *
	 */
	public MineSweeperGame(int size, int numMines) {
		boardSize = size;
		status = GameStatus.NotOverYet;
		board = new Cell[boardSize][boardSize];
		this.numMines = numMines;
		setEmpty();
		layMines (this.numMines);
		
	}

	/******************************************************************
	 * Sets all the cells on the board to not exposed and not a mine.
	 * used when starting a game or resetting a game after win / loss
	 *
	 */
	private void setEmpty() {
		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++) {
				board[r][c] = new Cell(false, false);
				board[r][c].setMineCount(0);
			}
	}

	/******************************************************************
	 * Gets the cell object at a given row and column.
	 *
	 * @param row row position of the desired cell.
	 * @param col column position of the desired cell.
	 *
	 * @return returns the desired cell object
	 *
	 */
	public Cell getCell(int row, int col) {
		return board[row][col];
	}

	/******************************************************************
	 * Logic used when a cell is clicked. Sets cell to exposed if it
	 * is not flagged and not a mine.  Also if the targeted cell has
	 * zero adjacent cells that are mines, it calls the reveal zeros
	 * method.
	 *
	 * @param row The row of the selected cell
	 * @param col the column of the selected cell
	 *
	 */
	public void select(int row, int col) {
		if(board[row][col].isFlagged())
			return;

		board[row][col].setExposed(true);
	    board[row][col].setMineCount(getMineCount(row,col));
		if (board[row][col].getMineCount() == 0)
			revealZeros(row, col);

		if (board[row][col].isMine())   // did I lose
			status = GameStatus.Lost;
		else if (gameWin())
			status = GameStatus.Won;   // did I win

	}

	/******************************************************************
	 * Used to determine if the player has won.  It checks every cell
	 * and if it finds a cell that is not yet exposed and is also not
	 * a mine, the player has not yet won.
	 *
	 * @return Returns true if all cells are either mines or set to
	 * exposed. Returns false if there is a non mine cell that is yet
	 * to be exposed.
	 *
	 */
	public boolean gameWin(){
		for(int row = 0; row < boardSize; row++){
			for(int col = 0; col < boardSize; col++){
				if(!board[row][col].isMine() &&
						!board[row][col].isExposed())
					return false;
			}
		}
		return true;
	}

	/******************************************************************
	 * Gets the current status of the game. Win, Loss, NotOverYet.
	 *
	 * @return Returns Win if the game is won, lost if the game is
	 * lost, and NotOverYet if the game is still in progress
	 *
	 */
	public GameStatus getGameStatus() {
		return status;
	}

	/******************************************************************
	 * Calls the setEmpty and layMines method which resets the board
	 * with new mine positions.  Also sets the status of the game to
	 * NotOverYet.
	 *
	 */
	public void reset() {
		status = GameStatus.NotOverYet;
		setEmpty();
		layMines (numMines);
	}

	/******************************************************************
	 * Randomly sets the mines on the new board.
	 *
	 */
	private void layMines(int mineCount) {
		int i = 0;

		Random random = new Random();
		while (i < mineCount) {
			int c = random.nextInt(boardSize);
			int r = random.nextInt(boardSize);

			if (!board[r][c].isMine()) {
				board[r][c].setMine(true);
				i++;
			}
		}
	}

	/******************************************************************
	 * Reveals all the connected cells also containing zeros
	 * iteratively by adding zero cells to a queue.  Once all items
	 * in the queue are taken care of, the method exits.
	 *
	 * @param r The row of the cell in question.
	 * @param c The column of the cell in question.
	 */
	public void revealZeros(int r, int c){
		ArrayList<int[]> queue = new ArrayList<>();
		queue.add(new int[]{r, c});
		while(queue.size() > 0){
			int[] positions = getStartingPositions(queue.get(0)[0],
					queue.get(0)[1]);
			int endRow = positions[3];
			int endCol = positions[2];

			for(int startRow = positions[1]; startRow <= endRow;
				startRow++){
				for(int startCol = positions[0]; startCol <= endCol;
					startCol++){
					if(getMineCount(startRow, startCol) == 0 &&
							!board[startRow][startCol].isExposed()){
						board[startRow][startCol].setExposed(true);
						queue.add(new int[]{startRow, startCol});
					} else
						board[startRow][startCol].setExposed(true);
				}
			}
			queue.remove(0);
		}
	}


	/******************************************************************
	 * Reveals all the connected cells also containing zeros
	 * recursively.  Accomplished by checking each cell for adjacent
	 * zeros.  If adjacent cell is a zero then the same method is
	 * called recursively to apply same method to that cell.
	 *
	 *
	 * @param r The row of the cell in question.
	 * @param c The column of the cell in question.
	 */
	public void revealZerosRecursive(int r, int c){
		int[] positions = getStartingPositions(r, c);
		int endRow = positions[3];
		int endCol = positions[2];

		for(int startRow = positions[1]; startRow <= endRow;
			startRow++){
			for(int startCol = positions[0]; startCol <= endCol;
				startCol++){
				if(getMineCount(startRow, startCol) == 0 &&
						!board[startRow][startCol].isExposed()){
					board[startRow][startCol].setExposed(true);
					revealZeros(startRow, startCol);
				} else
					board[startRow][startCol].setExposed(true);
			}
		}
	}

	/******************************************************************
	 * Checks all adjacent cells to determine how many mines are
	 * touching the entered cell.
	 *
	 * @param r The row of the cell in question.
	 * @param c The column of the cell in question.
	 *
	 * @return Returns the number of adjacent mines to the input cell.
	 */
	public int getMineCount(int r, int c){
		int[] positions = getStartingPositions(r,c);
		int count = 0;
		int endRow = positions[3];
		int endCol = positions[2];
		int startRow = positions[1];
		int startCol = positions[0];


		int tempCol = startCol;
		for(; startRow <= endRow; startRow++){
			for(startCol = tempCol; startCol <= endCol; startCol++){
				if(board[startRow][startCol].isMine()){
					count++;
				}
			}
		}

		return count;
	}

	/******************************************************************
	 * Helper method to determien where on the board the selected cell
	 * is located.  If it is at an edge, the starting position will
	 * be altered.
	 *
	 * @param r The row of the cell in question.
	 * @param c The column of the cell in question.
	 *
	 * @return Returns the array of starting positions for the loops.
	 */
	public int[] getStartingPositions(int r, int c){
		int endRow;
		int endCol;
		int startRow;
		int startCol;
		if(r <= 0){
			if(c <= 0){
				startRow = r;
				startCol = c;
				endRow = r + 1;
				endCol = c + 1;
			} else if(c >= board.length - 1){
				startRow = r;
				startCol = c - 1;
				endRow = r + 1;
				endCol = c;
			} else {
				startRow = r;
				startCol = c - 1;
				endRow = r + 1;
				endCol = c + 1;
			}
		} else if(r >= board.length - 1){
			if(c <=0){
				startRow = r - 1;
				startCol = c;
				endRow = r;
				endCol = c + 1;
			} else if(c >= board.length - 1){
				startRow = r - 1;
				startCol = c - 1;
				endRow = r;
				endCol = c;
			} else {
				startRow = r - 1;
				startCol = c - 1;
				endRow = r;
				endCol = c + 1;
			}
		} else if(c <= 0){
			startRow = r - 1;
			startCol = c;
			endRow = r + 1;
			endCol = c + 1;
		} else if(c >= board.length - 1){
			startRow = r - 1;
			startCol = c - 1;
			endRow = r + 1;
			endCol = c;
		} else {
			startRow = r - 1;
			startCol = c - 1;
			endRow = r + 1;
			endCol = c + 1;
		}
		int[] answer = {startCol, startRow, endCol, endRow};
		return answer;
	}

	/******************************************************************
	 * Getter method to get the number of wins in the current game.
	 *
	 *
	 * @return Returns the current win count
	 */
	public int getWins(){
		return wins;
	}

	/******************************************************************
	 * Getter method to get the number of losses in the current game.
	 *
	 *
	 * @return Returns the current loss count
	 */
	public int getLosses(){
		return losses;
	}

	/******************************************************************
	 * Method for increasing the win count by 1
	 *
	 */
	public void increaseWins(){
		wins++;
	}

	/******************************************************************
	 * Method for increasing the loss count by 1
	 *
	 */
	public void increaseLosses(){
		losses++;
	}

}



