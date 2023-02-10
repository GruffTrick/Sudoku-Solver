package uk.ac.aber.cs21120.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import uk.ac.aber.cs21120.interfaces.IGrid;
import uk.ac.aber.cs21120.solution.Grid;

public class GridTests {

    /**
     * Utility method to build a grid
     * @param args a list of x,y,val triplets
     * @return a new grid
     */
    private IGrid buildGrid(int... args){
        IGrid b = new Grid();
        for (int i = 0; i < args.length; i += 3) {
            int x = args[i];
            int y = args[i + 1];
            int val = args[i + 2];
            //System.out.format("Writing %d,%d = %d\n",x,y,val);
            b.set(x, y, val);
        }
//        Grid g = (Grid)b;
//        System.out.println(g.toString2());
        return b;
    }

    /**
     * Test that an empty grid is valid.
     */
    @Test
    public void testEmptyValid() {
        IGrid  b = buildGrid();
        Assertions.assertTrue(b.isValid());
    }

    /**
     * Test that grids throw the required exception when we try to write outside the bounds
     */
    @Test
    public void testCellExceptions(){
        Assertions.assertThrows(IGrid.BadCellException.class,()->{
           IGrid b = buildGrid(0,9,1);
        });
        Assertions.assertThrows(IGrid.BadCellException.class,()->{
            IGrid b = buildGrid(9,0,1);
        });
        Assertions.assertThrows(IGrid.BadCellException.class,()->{
            IGrid b = buildGrid(-1,0,1);
        });
        Assertions.assertThrows(IGrid.BadCellException.class,()->{
            IGrid b = buildGrid(0,-1,1);
        });
    }

    /**
     * Test that grids throw the required exception when we try to write an invalid digit
     */
    @Test
    public void testDigitExceptions(){
        Assertions.assertThrows(IGrid.BadDigitException.class,()->{
            IGrid b = buildGrid(5,5,-1);
        });
        Assertions.assertThrows(IGrid.BadDigitException.class,()->{
            IGrid b = buildGrid(5,5,10);
        });
    }

    /**
     * Build grids with a single digit in them and make sure they're valid
     */
    @Test
    public void testWithOneDigit(){
        IGrid  b = buildGrid(5,6,1);
        Assertions.assertTrue(b.isValid());
        b = buildGrid(0,0,2);
        Assertions.assertTrue(b.isValid());
        b = buildGrid(8,8,7);
        Assertions.assertTrue(b.isValid());
    }

    /**
     * Create grids with one row filled with valid digits and make sure they're valid.
     * Tries all rows.
     */
    @Test
    public void testRowsWithGoodDigits(){
        for(int i=0;i<9;i++) {
            IGrid b = buildGrid(0,i,1,1,i,2,2,i,3,3,i,4,4,i,5,5,i,6,6,i,7,7,i,8,8,i,9);
            Assertions.assertTrue(b.isValid());
        }
    }

    /**
     * Create grids with one column filled with valid digits and make sure they're valid.
     * Tries all columns.
     */
    @Test
    public void testColsWithGoodDigits(){
        for(int i=0;i<9;i++) {
            IGrid b = buildGrid(i,0,1,i,1,2,i,2,3,i,3,4,i,4,5,i,5,6,i,6,7,i,7,8,i,8,9);
            Assertions.assertTrue(b.isValid());
        }
    }

    /**
     * Place a "1" at the opposite corners - this is to test that duplicate digits are allowed
     * on the grid, provided they aren't in the same row, column or subgrid.
     */
    @Test public void corners() {
        IGrid b = buildGrid(0, 0, 1, 8, 8, 1);
        Assertions.assertTrue(b.isValid());
        b = buildGrid(8, 0, 1, 0, 8, 1);
        Assertions.assertTrue(b.isValid());
    }

    /**
     * Another test of multiple digits, this time we run through the entire example set -
     * these should all be valid puzzles! Includes two filled puzzles.
     */
    @Test public void examplesValid(){
        for(int i=0;i<402;i++){
            IGrid b = Examples.getExample(i);
            Assertions.assertTrue(b.isValid());
        }
    }

    /**
     * Create grids with one 3x3 subgrid filled with valid digits and make sure they're valid.
     * Tries all subgrids.
     */
    @Test void testSubGridsWithGoodDigits(){
        for(int x=0;x<9;x+=3){
            for(int y=0;y<9;y+=3){
                IGrid b = buildGrid(x,y,1,
                        x+1,y,2,
                        x+2,y,3,
                        x,y+1,4,
                        x+1,y+1,5,
                        x+2,y+1,6,
                        x,y+2,7,
                        x+1,y+2,8,
                        x+2,y+2,9);
                Assertions.assertTrue(b.isValid());
            }
        }
    }

    /**
     * Build a grid for each row which has two of the same digit in it. Tries different positions
     * for each row.
     */
    @Test
    public void testBadRows(){
        for(int i=0;i<9;i++) {
            // the weird maths in the x-coordinate of the second item is to give an offset which
            // varies, but never wraps round to 9 again (to avoid writing the digit in the same place).
            IGrid b = buildGrid(i,i,i+1,((i/2)*2+2)%9,i,i+1);
            Assertions.assertFalse(b.isValid());
        }
    }

    /**
     * Build a grid for each column which has two of the same digit in it. Tries different positions
     * for each column.
     */
    @Test
    public void testBadCols(){
        for(int i=0;i<9;i++) {
            IGrid b = buildGrid(i,i,i+1,i,((i/2)*2+2)%9,i+1);
            Assertions.assertFalse(b.isValid());
        }
    }

    /**
     * Build a grid for each subgrid which has two of the same digit in it. Tries different positions
     * for each subgrid.
     * This test is a bit awkward, because we don't want the bad digits to be in the same row
     * or column (we're not testing that).
     *
     * This code is pretty horrific - the intention is to try as many combinations of cells within
     * each subgrid as possible. Some trial and error was involved.
     */

    @Test
    public void testBadSubgrids() {
        int t=0; // a counter
        int[] off={-2,-1,1,2};
        for(int i=0;i<16;i++) {
            for (int x = 0; x < 9; x += 3) {
                for (int y = 0; y < 9; y += 3) {
                    int dig = (t++ % 9) + 1; // cycle through the digits, placing 2 of the same for each grid
                    int x1 = (x+i)%3; // x position of first cell determined by outer i loop
                    int y1 = ((y+i)/3)%3; // y position of first cell determined by outer i loop
                    int offx = off[i%4]; // for each outer loop value we cycle through possible offsets for the second position.
                    int offy = off[i/4];
                    int x2 = ((x1+offx)+9)%3; // we have to make sure the resulting value is still in the 0-3 range.
                    int y2 = ((y1+offy)+9)%3;
                    // and create the grid with the two points
                    IGrid b = buildGrid(x1 + x, y1 + y, dig, x2+x, y2+y, dig);
                    Assertions.assertFalse(b.isValid());
                }
            }
        }
    }
}
