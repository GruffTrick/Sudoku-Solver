package uk.ac.aber.cs21120.interfaces;

/**
 * This is the interface for the Sudoku Solver. It contains the single method solve(), which
 * returns whether the grid was solved or not. There should also be a constructor taking an IGrid
 * object: ISolver(IGrid g). Without this my tests will not work.
 * Use the constructor to set a "grid" member variable which the solve() method will use.
 */
public interface ISolver {
    /**
     * The solver method - this will attempt to solve the grid set by the constructor, returning
     * a boolean if successful. It will call itself recursively to solve simpler grids (i.e. with
     * more digits filled in).
     * @return true if successful
     */
    boolean solve();
}
