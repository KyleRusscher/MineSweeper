package Project2;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
/******************************************************************
 * MineSweeperPanel class that is the display for minesweeper
 *
 * @author Kyle Russcher
 *
 */
public class MineSweeperPanel extends JPanel {
	/** 2d array that holds all the JButtons for each cell */
	private JButton[][] board;

	/** Quit button that resets the game */
	private JButton quitButton;

	/** Displays the wins and losses for the current match */
	private JLabel wins, losses;

	/** JPanels that hold all the content of the MineSweeper
	 * game */
	private JPanel bottom, center, north;

	/** place to hold an instance of a cell */
	private Cell iCell;

	/** Hold the boardsize from the game */
	private int boardSize;

	/** Holds the number of miens from the game */
	private int numMines;

	/** holds all the mouse actions */
	private FlagListener mouse = new FlagListener();

	/**  */
	private MineSweeperGame game;  // model

	/******************************************************************
	 * Constructor for the MineSweeperPanel class.
	 */
	public MineSweeperPanel(String boardSize, String numMines) {
		this.boardSize = Integer.parseInt(boardSize);
		this.numMines = Integer.parseInt(numMines);

		bottom = new JPanel();
		center = new JPanel();
		north = new JPanel();

		game = new MineSweeperGame(this.boardSize, this.numMines);


		// create the board
		center.setLayout(new GridLayout(this.boardSize,
				this.boardSize));
		bottom.setLayout(new GridLayout(2, 1));
		board = new JButton[this.boardSize][this.boardSize];

		/* quit button implementation */
		quitButton = new JButton("Quit");
		quitButton.addMouseListener(mouse);
		bottom.add(quitButton);

		/* wins and losses label implementation */
		wins = new JLabel("Wins: 0");
		losses = new JLabel("Losses: 0");
		north.add(new JLabel("Mine Sweeper!!"), BorderLayout.NORTH);
		add(losses, BorderLayout.NORTH);
		add(wins, BorderLayout.NORTH);

		for (int row = 0; row < this.boardSize; row++)
			for (int col = 0; col < this.boardSize; col++) {
				board[row][col] = new JButton("");
				board[row][col].addMouseListener(mouse);
				center.add(board[row][col]);
			}

		displayBoard();


		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);


	}


	/******************************************************************
	 * Displays each cell on a panel in its correct state from the
	 * game and cell class.
	 *
	 */
	private void displayBoard() {

		for (int r = 0; r < boardSize; r++)
			for (int c = 0; c < boardSize; c++) {
				iCell = game.getCell(r, c);

				board[r][c].setText("    ");
				if (iCell.isMine())
					board[r][c].setText("!");

				if (iCell.isFlagged())
					board[r][c].setText("*");

				if (iCell.isExposed()) {
					board[r][c].setEnabled(false);
					board[r][c].setText("" + game.getMineCount(r, c));

				} else
					board[r][c].setEnabled(true);
			}
	}

	/******************************************************************
	 * Class that implements the MouseListener class to access the
	 * right click action for flag placement and the left click to
	 * select a cell.
	 *
	 */
	public class FlagListener implements MouseListener {
		/******************************************************************
		 * If the right mouse button was clicked on a cell, it will be
		 * marked as flagged by the game class. If the left mouse button
		 * is clicked then the source will be selected.
		 */
		public void mouseReleased(MouseEvent e) {
			for (int r = 0; r < boardSize; r++)
				for (int c = 0; c < boardSize; c++){
					if (board[r][c] == e.getSource()){
						iCell = game.getCell(r, c);
						if(e.getButton() == MouseEvent.BUTTON3 &&
								!iCell.isFlagged() && !iCell.isExposed()){
							iCell.setFlagged(true);
						} else if (e.getButton() == MouseEvent.BUTTON3 &&
								iCell.isFlagged() && !iCell.isExposed()){
							iCell.setFlagged(false);
						} else if (e.getButton() == MouseEvent.BUTTON1 &&
								!iCell.isFlagged() && !iCell.isExposed()){
							game.select(r,c);
						}
					}
					if(quitButton == e.getSource()){
						game.reset();
					}
					displayBoard();

					if (game.getGameStatus() == GameStatus.Lost) {
						displayBoard();
						JOptionPane.showMessageDialog(null,
								"You Lose \n The game will reset");
						game.increaseLosses();
						losses.setText("Losses: " + game.getLosses());
						game.reset();
						displayBoard();

					}

					if (game.getGameStatus() == GameStatus.Won) {
						JOptionPane.showMessageDialog(null,
								"You Win: all mines have been" +
										" found!\n The game will reset");
						game.increaseWins();
						wins.setText("Wins: " + game.getWins());
						game.reset();
						displayBoard();
					}
				}
			displayBoard();
		}

		/******************************************************************
		 * Abstract method overriding.
		 */
		public void mousePressed(MouseEvent e) {}
		/******************************************************************
		 * Abstract method overriding.
		 */
		public void mouseClicked(MouseEvent e) {}
		/******************************************************************
		 * Abstract method overriding.
		 */
		public void mouseEntered(MouseEvent e) {}
		/******************************************************************
		 * Abstract method overriding.
		 */
		public void mouseExited(MouseEvent e) {}
	}
}



