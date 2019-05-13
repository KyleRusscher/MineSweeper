package Project2;

/******************************************************************
 * Cell class that holds info for each cell in the minesweeper
 * game
 *
 * @author Kyle Russcher
 *
 */
public class Cell {
    /** Value for the number of adjacent mines to a cell. False
      * otherwise.  */
    private int mineCount;

    /** true if user has flagged a cell */
    private boolean isFlagged;

    /** True if cells has been shown / exposed.  False
      * otherwise */
    private boolean isExposed;

    /** True if cell is a mine. False if cell is not a mine */
    private boolean isMine;

    public Cell(boolean exposed, boolean mine) {
        isExposed = exposed;
        isMine = mine;
    }


    /******************************************************************
     * Getter method for isExposed to determine if square has been
     * exposed yet
     *
     * @return returns true if the square has been exposed; false if
     * the square has not been exposed
     *
     */
    public boolean isExposed() {
        return isExposed;
    }

    /******************************************************************
     * Setter method for isExposed to set if a square is exposed / has
     * been clicked yet
     *
     * @param exposed true if square is exposed and false if square
     * is not exposed yet
     */
    public void setExposed(boolean exposed) {
        isExposed = exposed;
    }

    /******************************************************************
     * Getter method for isMine to determine if square is a mine
     *
     * @return returns true if the square is a mine; false if
     * the square is not a mine
     *
     */
    public boolean isMine() {
        return isMine;
    }

    /******************************************************************
     * Setter method for isMine to set if a square is a mine or not
     *
     * @param mine true if square is a mine and false if square is
     * not a mine
     */
    public void setMine(boolean mine) {
        isMine = mine;
    }

    /******************************************************************
     * Getter method for getMineCount to determine the number of mines
     * adjacent to the specified cell
     *
     * @return returns the integer number of adjacent mines
     *
     */
    public int getMineCount() {
        return mineCount;
    }

    /******************************************************************
     * Setter method for mineCount to set the number of adjacent mines
     * to the specified cell
     *
     * @param mineCount sets the value for the number of adjacent mines
     */
    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    /******************************************************************
     * Getter method for isFlagged to determine if square has been
     * flagged by the user
     *
     * @return returns true if the square is flagged; false if
     * the square is not flagged
     *
     */
    public boolean isFlagged() {
        return isFlagged;
    }

    /******************************************************************
     * Setter method for isFlagged to set if a square is currently
     * flagged by the user
     *
     * @param flagged true if square is flagged and false if square
     * is not flagged
     *
     */
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }
}
