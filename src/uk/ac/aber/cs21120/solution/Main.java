/**
 * Sudoku solver that solves 400 pre-written puzzles.
 * Program prints out the puzzle id, number of gaps and time taken to solve in milliseconds .
 *
 * @author Gruffudd Trick
 * @version 1
 */

package uk.ac.aber.cs21120.solution;

import uk.ac.aber.cs21120.interfaces.IGrid;
import uk.ac.aber.cs21120.tests.Examples;

public class Main {
    public static void main(String[] args) {
        //Loops through all 400 examples and records solve time in milliseconds
        for (int x = 0; x < 400; x++) {
            IGrid grid = Examples.getExample(x);
            int gaps = Examples.getGapCount(x);
            Solver solver = new Solver(grid);

            long timeStart = System.currentTimeMillis();    //records start time
            solver.solve();
            long timeTaken = System.currentTimeMillis()-timeStart;  //calculates time taken by subtracting start time from current time
            //print out the example number, number of gaps, time taken in milliseconds
            System.out.format("%d,%d,%d%n",x,gaps,timeTaken);
        }
    }
}