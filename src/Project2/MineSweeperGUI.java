package Project2;


import javax.swing.*;
/******************************************************************
 * MineSweeperGUI class to set up the MineSweeper games GUI
 *
 * @author Kyle Russcher
 *
 */
public class MineSweeperGUI {
	public static void main (String[] args)
	{
		JFrame frame = new JFrame ("Mine Sweeper!");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		String size;
		String mines;
		String defaultSize = "10";
		String defaultMines = "10";
		MineSweeperPanel panel;

		do {
			try{
				size = JOptionPane.showInputDialog (null,
						"Enter in the size of" +
								" the board from 3 to 30: ");
				if(size.isEmpty())
					size = defaultSize;
			} catch (Exception e) {
				size = defaultSize;
			}
		} while (!validBoard(size));

		do {
			try{
				mines = JOptionPane.showInputDialog (null,
						"Enter in the number of" +
								" mines you would like: ");
				if(mines.isEmpty())
					mines = defaultMines;
			} catch (Exception e) {
				mines = defaultMines;
			}
		} while (!validMines(size, mines));
		panel = new MineSweeperPanel(size, mines);

		frame.getContentPane().add(panel);
		frame.setSize(Integer.parseInt(size) * 46 + 45,
				Integer.parseInt(size) * 25 + 150);
		frame.setVisible(true);
	}

	/******************************************************************
	 * Static  helper method to determine if the user input board
	 * size is a valid number.
	 *
	 * @param size User input board size to be checked for validity
	 *
	 * @return True if size is not between 3 and 30;
	 * False if size is not in this range.
	 */
	public static boolean validBoard(String size){
		int numSize;
		try {
			numSize = Integer.parseInt(size);
		} catch (Exception e) {
			JOptionPane.showMessageDialog( null,
					"Please enter an integer");
			return false;
		}
		if (numSize < 3 || numSize > 30) {
			JOptionPane.showMessageDialog(null,
					"Please enter a number between 3 and 30");
			return false;
		}
		return true;
	}

	/******************************************************************
	 * Static  helper method to determine if the user input number of
	 * mines is a valid number.
	 *
	 * @param size User input board size used to determine max mines
	 * @param mines User input number of mines. Must be more than 5
	 *              and less than size^2 - 1.
	 *
	 * @return True if mines is between 1 and size^2 - 1;
	 * False if mines is not in this range.
	 */
	public static boolean validMines(String size, String mines){
		int numSize;
		int numMines;
		try {
			numMines = Integer.parseInt(mines);
			numSize = Integer.parseInt(size);
		} catch (Exception e) {
			JOptionPane.showMessageDialog( null,
					"Please enter an integer");
			return false;
		}
		if (numMines > numSize * numSize - 1 || numMines < 1) {
			JOptionPane.showMessageDialog(null,
					"Please enter a number of mines between 1 and "
							+ (numSize * numSize - 1));
			return false;
		}
		return true;
	}
}


