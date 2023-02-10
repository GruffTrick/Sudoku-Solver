package uk.ac.aber.cs21120.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.interfaces.IGrid;
import uk.ac.aber.cs21120.interfaces.ISolver;
import uk.ac.aber.cs21120.solution.Solver;

public class SolverTests {
    /**
     * Internal check that grid is completely filled in
     * @param g the grid
     * @return true if no cell is empty
     */
    private boolean isFilled(IGrid g){
        for(int y=0;y<9;y++){
            for(int x=0;x<9;x++){
                if(g.get(x,y)==0)return false;
            }
        }
        return true;
    }

    /**
     * Test a filled grid is already solved. Like all the tests here, it checks three things:
     * - the return value of solve()
     * - that the grid is full of digits
     * - and that it is a valid grid.
     */
    @Test
    public void testFilled(){
        IGrid g = Examples.getExample(400);
        ISolver s = new Solver(g);
        // check we actually solved it
        Assertions.assertTrue(s.solve());
        // make sure it really is filled in
        Assertions.assertTrue(isFilled(g));
        // and that it's valid
        Assertions.assertTrue(g.isValid());
    }

    /**
     * Test the "trivial unsolved sudoku" - entirely filled in apart from the top-left digit
     */
    @Test
    public void testTopLeftMissing(){
        IGrid g = Examples.getExample(400);
        g.set(0,0,0);
        ISolver s = new Solver(g);
        // check we actually solved it
        Assertions.assertTrue(s.solve());
        // make sure it really is filled in
        Assertions.assertTrue(isFilled(g));
        // and that it's valid
        Assertions.assertTrue(g.isValid());
    }

    /**
     * Test more examples with one gap, which should be trivial to solve.
     * This tests all the possible gap positions.
     */
    @Test
    public void testDigitMissing() {
        for (int i = 1; i < 81; i++) { // start at 1 to ignore the 0,0 case, which is already tested.
            int x = i / 9;
            int y = i % 9;
            IGrid g = Examples.getExample(400);
            g.set(x, y, 0);
            ISolver s = new Solver(g);
            // check we actually solved it
            Assertions.assertTrue(s.solve());
            // make sure it really is filled in
            Assertions.assertTrue(isFilled(g));
            // and that it's valid
            Assertions.assertTrue(g.isValid());
        }
    }

    /**
     * Test an example with two gaps in it, which should be trivial to solve. This only tests two
     * examples.
     */
    @Test
    public void testTwoDigitsMissing(){
        IGrid g = Examples.getExample(400);
        g.set(1,5,0);
        g.set(5,6,0);
        ISolver s = new Solver(g);
        // check we actually solved it
        Assertions.assertTrue(s.solve());
        // make sure it really is filled in
        Assertions.assertTrue(isFilled(g));
        // and that it's valid
        Assertions.assertTrue(g.isValid());
    }

    /**
     * This tests a case with three gaps, which requires a single level of backtracking.
     */
    @Test
    public void testThreeDigitsMissingBacktrack(){
        IGrid g = Examples.getExample(400);
        g.set(0,0,0);
        g.set(0,2,0);
        g.set(5,0,0);
        ISolver s = new Solver(g);
        // check we actually solved it
        Assertions.assertTrue(s.solve());
        // make sure it really is filled in
        Assertions.assertTrue(isFilled(g));
        // and that it's valid
        Assertions.assertTrue(g.isValid());
    }

    /**
     * This tests a case with four gaps, which requires two levels of backtracking.
     */
    @Test
    public void testFourDigitsMissingBacktrack(){
        IGrid g = Examples.getExample(400);
        g.set(0,0,0);
        g.set(0,2,0);
        g.set(0,1,0);
        g.set(7,1,0);
        ISolver s = new Solver(g);
        // check we actually solved it
        Assertions.assertTrue(s.solve());
        // make sure it really is filled in
        Assertions.assertTrue(isFilled(g));
        // and that it's valid
        Assertions.assertTrue(g.isValid());
    }

    /**
     * Test an arbitrary example with 20 gaps
     */
    @Test
    public void test20Gaps(){
        int PUZNUMBER = 2;
        IGrid g = Examples.getExample(PUZNUMBER);
        Assertions.assertEquals(20,Examples.getGapCount(PUZNUMBER));
        ISolver s = new Solver(g);
        // check we actually solved it
        Assertions.assertTrue(s.solve());
        // make sure it really is filled in
        Assertions.assertTrue(isFilled(g));
        // and that it's valid
        Assertions.assertTrue(g.isValid());
    }

    /**
     * Test an arbitrary example with 40 gaps
     */
    @Test
    public void test40Gaps(){
        int PUZNUMBER = 167;
        IGrid g = Examples.getExample(PUZNUMBER);
        Assertions.assertEquals(40,Examples.getGapCount(PUZNUMBER));
        ISolver s = new Solver(g);
        // check we actually solved it
        Assertions.assertTrue(s.solve());
        // make sure it really is filled in
        Assertions.assertTrue(isFilled(g));
        // and that it's valid
        Assertions.assertTrue(g.isValid());
    }

    /**
     * Test an arbitrary example which has to backtrack into a solve which successfully
     * placed a nine. Putting the digit clearing in the wrong place can cause such 9s to
     * not be removed on backtracking.
     */
    @Test
    public void testBacktrackThrough9(){
        int PUZNUMBER = 78;
        IGrid g = Examples.getExample(PUZNUMBER);
        Assertions.assertEquals(29,Examples.getGapCount(PUZNUMBER));
        ISolver s = new Solver(g);
        // check we actually solved it
        Assertions.assertTrue(s.solve());
        // make sure it really is filled in
        Assertions.assertTrue(isFilled(g));
        // and that it's valid
        Assertions.assertTrue(g.isValid());
    }

}
