package uk.ac.aber.cs21120.solution;

import uk.ac.aber.cs21120.interfaces.IGrid;
import uk.ac.aber.cs21120.interfaces.ISolver;

public class Solver implements ISolver {

    private final IGrid g;
    private static final int size = 9;

    /**
     * Constructor for the Solver class
     *
     * @param g Grid Object
     */
    public Solver(IGrid g) {
        this.g = g;
    }

    /**
     * The solver method - this will attempt to solve the grid set by the constructor, returning
     * a boolean if successful. It will call itself recursively to solve simpler grids (i.e. with
     * more digits filled in).
     * <p>
     * Time complexity:
     * O(N^m), where N is the number of possibilities for each cell
     * (in this case, 9 for a standard Sudoku puzzle),
     * and m is the number of empty cells.
     *
     * @return true if successful
     */
    @Override
    public boolean solve() {
        // Loop through each cell in the Sudoku grid
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                // Check if the current cell is empty (contains 0)
                if (g.get(row, col) == 0) {
                    // Try filling the cell with values from 1 to size
                    for (int val = 1; val <= size; val++) {
                        // Check if the current value is valid in the current position
                        if (g.isValid()) {
                            // If valid, set the cell to the current value
                            g.set(row, col, val);

                            // Recursively attempt to solve the Sudoku puzzle with the new value
                            if (solve()) {
                                // If the puzzle is solved, propagate the success
                                return true;
                            } else {
                                // If the puzzle cannot be solved with the current value, backtrack
                                g.set(row, col, 0);
                            }
                        }
                    }
                    // If none of the values work for the current cell, backtrack to the previous cell
                    return false;
                }
            }
        }
        // If all cells are filled, the Sudoku puzzle is solved
        return true;
    }
}
