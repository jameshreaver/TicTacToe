import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;


public class TicTacToeTests {

    private static final int BOARD_WIDTH = 3;

    private Board board;
    private Player computerFirst;
    private Player computerSecond;
    private Move move;

    @Before
    public void setup() {
        board = new Board(BOARD_WIDTH);
    }

    @Test
    public void testGetSize() {
        assertEquals(board.getWidth(), BOARD_WIDTH);
    }

    @Test
    public void testBoardBoundaries() {
        assertFalse(board.isValid(new Move(0, BOARD_WIDTH)));
        assertFalse(board.isValid(new Move(BOARD_WIDTH, 0)));
        assertFalse(board.isValid(new Move(-1, BOARD_WIDTH)));
        assertFalse(board.isValid(new Move(BOARD_WIDTH, -1)));
    }

    @Test
    public void testApplyX() {
        markX(0, 0);
        markX(0, 1);
        assertEquals(board.getMarkAt(0, 0), 'X');
        assertEquals(board.getMarkAt(0, 1), 'X');
        assertTrue(board.isEmptyAt(1, 0));
    }

    @Test
    public void testApplyO() {
        markO(0, 0);
        markO(0, 1);
        assertEquals(board.getMarkAt(0, 0), 'O');
        assertEquals(board.getMarkAt(0, 1), 'O');
        assertTrue(board.isEmptyAt(1, 0));
    }

    @Test
    public void testGetMarks() {
        markO(0, 0);
        markO(0, 1);
        markX(1, 0);
        markX(1, 1);
        assertEquals(board.getMarks(), 4);
    }

    @Test
    public void testIsFilled() {
        assertFalse(board.isFilled());

        markO(0, 0);
        markX(0, 1);
        assertFalse(board.isFilled());

        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col ++) {
                markX(row, col);
            }
        }
        assertTrue(board.isFilled());
    }

    @Test
    public void markedIsNotValid() {
        markX(0, 0);
        assertFalse(board.isValid(new Move(0, 0)));
    }

    @Test
    public void boardNotComplete() {
        markO(0, 0);
        markO(0, 2);
        markO(1, 0);
        markO(2, 1);
        markO(1, 1);
        assertFalse(board.isComplete(new Move(1, 1)));
    }

    @Test
    public void boardCompleteSameChar() {
        markX(0, 0);
        markX(0, 2);
        markX(2, 0);
        markX(1, 1);
        assertTrue(board.isComplete(new Move(1, 1)));
    }

    @Test
    public void boardNotCompleteDifferentChar() {
        markO(0, 2);
        markO(1, 2);
        markX(2, 2);
        assertFalse(board.isComplete(new Move(1, 2)));
    }


    /* HELPER METHODS */

    private void markX(int row, int col) {
        board.apply(new Move(row, col), 'X');
    }

    private void markO(int row, int col) {
        board.apply(new Move(row, col), 'O');
    }

}