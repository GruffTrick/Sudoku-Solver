package uk.ac.aber.cs21120.solution;

import uk.ac.aber.cs21120.interfaces.IGrid;
import uk.ac.aber.cs21120.interfaces.ISolver;

public class Solver implements ISolver {

    private final IGrid g;

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
     *
     * @return true if successful
     */
    @Override
    public boolean solve() {
        //For every cell in grid:
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y <9; y++) {
                //Checks if cell is empty
                if (g.get(x,y) == 0) {
                    for (int val = 1; val <=9; val++) {
                        g.set(x, y, val);
                        //checks if value set is valid
                        if (g.isValid()) {
                            //calls solve to backtrack recursively
                            if (solve()) {
                                return true;
                            } else {    //if solution doesn't work, set cell to 0
                                g.set(x, y, 0);
                            }
                        } else {    //if value of cell is not valid, set to 0
                            g.set(x,y,0);
                        }
                    }
                    return false;
                }
            }
        }
        //returns true if solved
        return true;
    }
}
