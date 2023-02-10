/**
 *  Randomly generates sudoku puzzles.
 *
 *  Utilises parts of the algorithms at 101Computing.net and sudokuoftheday.com
 *  https://www.101computing.net/sudoku-generator-algorithm/
 *  https://www.sudokuoftheday.com/about/difficulty/
 *
 *
 * @author Gruffudd Trick
 * @version 1
 */

package uk.ac.aber.cs21120.generator;

import uk.ac.aber.cs21120.interfaces.IGrid;
import uk.ac.aber.cs21120.solution.Grid;
import uk.ac.aber.cs21120.solution.Solver;

import java.util.Scanner;
import java.util.Random;

public class Generator {

    /**
     * Builds a new empty grid
     *
     * @return a new empty Grid
     */
    private static IGrid buildGrid(/*int... args*/) {
        IGrid g  = new Grid();
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                g.set(x,y,0);
            }
        }
        return g;
    }

    private static boolean fillCell(IGrid g, int x, int y, int val) {
        g.set(x, y, val);
        if (g.isValid())
            return true;
        g.set(x,y,0);
        return false;
    }

    private static void buildBasePuzzle(IGrid g) {
        Random rn = new Random();
        int i = 0;
        int j = 0;
        //Generate a base sudoku Puzzle to fill in
        while (i < 17) {
            int x = rn.nextInt(9);
            int y = rn.nextInt(9);
            int val = rn.nextInt(10);
            //If cell is empty, attempt to fill cell
            if (g.get(x, y) == 0) {
                if (fillCell(g, x, y, val)) {
                    i++;
                } else {
                    if (!(i < 0)) {
                        i--;
                    }
                }
            }
//            System.out.println(g.toString());
            j++;
            //If runs for too many iterations, builds new clean grid.
            if (j > 50) {
                g = buildGrid();
                System.out.println("Cannot build valid base.\nTrying from scratch");
//                System.out.println(g.toString());
                i = 0;
                j = 0;
            }
        }
    }

    private static void createGaps(IGrid g, int gaps) {
        Random rn = new Random();

        for (int i = 0; i < gaps/2; i++) {
            int x = rn.nextInt(9);
            int y = rn.nextInt(9);
            //Create gaps while also ensuring rotational symmetry
            if (g.get(x,y) !=0) {
                g.set(x, y, 0);
                g.set(8 - x, 8 - y, 0);
            } else {
                if (i != 0) {
                    i--;
                }
            }
        }
    }

    public static void main(String[] args) {
        //Initialisation
        Scanner scanner = new Scanner(System.in);
        IGrid grid = buildGrid();
        Solver s = new Solver(grid);
        int gaps = 44;

        //User input number of gaps (<80)
        System.out.println("What Difficulty would you like?: ");
        System.out.println("1 - Easy\n2 - Intermediate\n3 - Expert");
        String difficulty = scanner.next();
        switch (difficulty){
             case "1":
                 gaps = 34;
                 break;
             case "2":
                 gaps = 40;
                 break;
             case "3":
                 gaps = 54;
                 break;
             default:
                 System.out.print("Incorrect Input!");
        }
            //Build filled grid using solve algorithm
        System.out.println("Building Base grid");
        buildBasePuzzle(grid);
//            System.out.println(grid.toString());
        System.out.println("Populating rest of grid");
        s.solve();
//            System.out.println(grid.toString());
            //Create number of gaps dependant on chosen difficulty
        System.out.println("Creating gaps");
        createGaps(grid, gaps);

        //Finished sudoku puzzle
         System.out.println(grid.toString());
         System.out.println("Done!");

         //Check if the produced puzzle is valid
//        if (grid.isValid()) {
//            System.out.println("Valid");
//        } else {
//            System.out.println("invalid");
//        }
    }

}
