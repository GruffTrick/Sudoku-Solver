package uk.ac.aber.cs21120.interfaces;

/**
 * This is the interface for a Sudoku grid, encapsulating a 9x9 board
 */
public interface IGrid {
    /**
     * An exception which is thrown if the coordinates are out of range.
     * Your Grid class should throw this exception when required in its get() and set() methods.
     */
    public class BadCellException extends RuntimeException {
        private int x,y;

        /**
         * Construct the exception giving the bad coordinates
         * @param x column number
         * @param y row number
         */
        public BadCellException(int x,int y){
            this.x = x;
            this.y = y;
        }

        /**
         * return a string describing the problem
         */
        public String getMessage(){
            return String.format("Bad cell coordinates: %d,%d",x,y);
        }
    }

    /**
     * You should throw this exception when you try to set() an invalid digit. Only the values
     * 0-9 are acceptable (0 for empty, 1-9 for actual values).
     */
    public class BadDigitException extends RuntimeException {
        private int val;
        /**
         * Construct the exception
         * @param val the value we tried to set
         */
        public BadDigitException(int val){
            this.val = val;
        }
        /**
         * return a string describing the problem
         */
        public String getMessage(){
            return String.format("Bad cell value: %d",val);
        }
    }

    /**
     * Get the value of the given cell in the grid as a digit from 1-9, or 0 if the cell
     * is empty. BadCellException is thrown if the coordinates are out of range.
     * @param x column number
     * @param y row number
     * @return digit from 1-9, or 0 if cell is empty
     */
    public int get(int x,int y) throws BadCellException;

    /**
     * Set the value of the given cell in the grid.
     * BadCellException is thrown if the coordinates are out of range.
     * @param x column number
     * @param y row number
     * @param val digit from 1-9, or 0 for an empty cell
     */
    public void set(int x,int y,int val) throws BadCellException,BadDigitException;

    /**
     * Return true if the grid is valid. This means that all non-empty cells meet the following conditions:
     * 1) each digit appears only once in each row
     * 2) each digit appears only once in each column
     * 3) each digit appears only once in each 3x3 subgrid
     * Note that empty cells are not counted.
     * @return true if the grid is valid.
     */
    public boolean isValid();
}
