package uk.ac.aber.cs21120.solution;

import uk.ac.aber.cs21120.interfaces.IGrid;

public class Grid implements IGrid {

    private final int[][] grid;
    /**
     * Constructor for Grid Class
     */
    public Grid() {
        grid = new int[9][9];
    }

    /**
     * Get the value of the given cell in the grid as a digit from 1-9, or 0 if the cell
     * is empty. BadCellException is thrown if the coordinates are out of range.
     *
     * @param x column number
     * @param y row number
     * @return digit from 1-9, or 0 if cell is empty
     */
    @Override
    public int get(int x, int y) throws BadCellException {
        int cellValue;
        if (x < 0 || x > 8 || y < 0 || y > 8) {     //Throws exception if coordinates are not values between 0 and 8
            throw new BadCellException(x, y);
        }
        if (this.grid[x][y] >= 1 && this.grid[x][y] <= 9) {
            cellValue = this.grid[x][y];
        } else {
            cellValue = 0;
        }
        return cellValue;
    }

    /**
     * Set the value of the given cell in the grid.
     * BadCellException is thrown if the coordinates are out of range.
     *
     * @param x   column number
     * @param y   row number
     * @param val digit from 1-9, or 0 for an empty cell
     */
    @Override
    public void set(int x, int y, int val) throws BadCellException, BadDigitException {
        if (x < 0 || x > 8 || y < 0 || y > 8) { //Throws exception if coordinates are not values between 0 and 8
            throw new BadCellException(x, y);
        }
        if (val < 0 || val > 9) {   //Throws exception if val is not between 1 and 9
            throw new BadDigitException(val);
        }
        this.grid[x][y] = val;
    }

    /**
     * Return true if the grid is valid. This means that all non-empty cells meet the following conditions:
     * 1) each digit appears only once in each row
     * 2) each digit appears only once in each column
     * 3) each digit appears only once in each 3x3 subgrid
     * Note that empty cells are not counted.
     *
     * @return true if the grid is valid.
     */
    @Override
    public boolean isValid() {
        for (int val = 1; val <= 9; val++) {
            //Test horizontal and vertical lines for recurring number
            for (int y = 0; y < 9; y++) {
                for (int x = 0; x < 9; x++){
                    if (!testHorizontal(y, val)  || !testVertical(x, val) || !testSubGrid(x,y,val)){
                        return false;       //returns false if either test returns false
                    }
                }
            }
        }
        //if all tests are passed returns true
        return true;
    }

    /**
     * Tests horizontal line for repeating numbers
     *
     * @param x current x position
     * @param val number to search for
     * @return false if number repeats, otherwise true
     */
    private boolean testVertical(int x, int val) {
        //Tests vertical line.  If value recurs on line returns false.
        int count = 0;
        for (int v = 0; v < 9; v++) {
            if (this.grid[x][v] == val) {
                count++;
            }
            if (count > 1) {
                //System.out.print("Recurring number on vertical line at coordinates: ");
                //System.out.println(x + ", " + y + ",");
                return false;       //return false if number recurs in line
            }
        }
        return true;
    }

    /**
     * Tests horizontal line for repeating numbers
     *
     * @param y current y position
     * @param val number to search for
     * @return false if number repeats in line, otherwise true
     */
    private boolean testHorizontal(int y, int val) {
        int count = 0;
        //Tests horizontal line.  If value recurs on line returns false.
        for (int h = 0; h < 9; h++) {
                if (this.grid[h][y] == val) {
                    count++;
                }
                if (count > 1) {
                    //System.out.print("Recurring number on horizontal line at coordinates: ");
                    //System.out.println(x + ", " + y + ",");
                    return false;       //return false if number recurs in line
                }
            }
        return true;
    }

    /**
     * Tests sub-grid the current cell is inside for repeating number
     *
     * @param x Current x position
     * @param y Current y position
     * @param val Number to search for in sub grid
     * @return False if number repeats, otherwise true
     */
    private boolean testSubGrid(int x, int y, int val) {
        //Use coordinates to find sub-grid bounds
        int h = x - x % 3;
        int v = y - y % 3;

        //Scan sub-grid for recurring numbers
        int count = 0;
        for (int i = h; i < h + 3; i++) {
            for (int j = v; j < v + 3; j++) {
                if (this.grid[i][j] == val) {
                    count++;    //If number is found increment count
                }
                if (count > 1) {
                    //System.out.print("Recurring number in SubGrid at coordinates: ");
                    //System.out.println(x + ", " + y + ",");
                    return false;       //returns false if number recurs in sub-grid
                }
            }
        }
        return true;
    }

    /**
     * Outputs the grid as a string.
     *
     * @return Grid as a string.
     */
    public String toString(){
        StringBuilder b = new StringBuilder();
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                b.append(get(x, y)).append(", ");
            }
            b.append('\n');
        }
        return b.toString();
    }
}