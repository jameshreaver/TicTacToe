import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;

public class TicTacToeTests {

    private static final int BOARD_SIZE = 3;

    private Board board;
    private Player computerFirst;
    private Player computerSecond;
    private Move move;

    @Before
    public void setup() {
        board = new Board(BOARD_SIZE);
        computerFirst = new ComputerPlayer('X', board);
        computerSecond = new ComputerPlayer('O', board);
    }

    /* TESTS FOR BOARD */

    @Test
    public void testGetSize() {
        assertEquals(board.getSize(), BOARD_SIZE);
    }

    @Test
    public void testGetLastMove() {
        board.mark(BOARD_SIZE-1, BOARD_SIZE-1, 'X');
        Move move = board.getLastMove();
        assertEquals(move.getRow(), BOARD_SIZE-1);
        assertEquals(move.getCol(), BOARD_SIZE-1);
    }

    @Test
    public void testBoardBoundaries() {
        assertFalse(board.isValid(0, BOARD_SIZE));
        assertFalse(board.isValid(BOARD_SIZE, 0));
        assertFalse(board.isValid(-1, BOARD_SIZE));
        assertFalse(board.isValid(BOARD_SIZE, -1));
    }

    @Test
    public void markedIsNotValid() {
        board.mark(0, 0, 'X');
        assertFalse(board.isValid(0, 0));
    }

    @Test
    public void boardNotComplete() {
        board.mark(0, 0, 'O');
        board.mark(0, 2, 'O');
        board.mark(1, 0, 'O');
        board.mark(2, 1, 'O');
        board.mark(1, 1, 'O');
        assertFalse(board.isComplete(1, 1));
    }

    @Test
    public void boardCompleteSameChar() {
        board.mark(0, 0, 'X');
        board.mark(0, 2, 'X');
        board.mark(2, 0, 'X');
        board.mark(1, 1, 'X');
        assertTrue(board.isComplete(1, 1));
    }

    @Test
    public void boardNotCompleteDifferentChar() {
        board.mark(0, 2, '0');
        board.mark(1, 2, '0');
        board.mark(2, 2, 'X');
        assertFalse(board.isComplete(1, 2));
    }

    @Test
    public void testInitialise() {
        board.mark(0, 2, '0');
        board.mark(1, 2, '0');
        board.initialise();
        Move move = board.getLastMove();

        assertEquals(board.getSize(), BOARD_SIZE);
        assertFalse(board.isValid(move.getRow(), move.getCol()));
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                assertTrue(board.isValid(row, col));
            }
        }
    }


    /* TESTS FOR COMPUTER PLAYER */

    @Test
    public void testGetIdFirst() {
        assertEquals(computerFirst.getId(), 'X');
    }

    @Test
    public void testGetIdSecond() {
        assertEquals(computerSecond.getId(), 'O');
    }

    // Computer first

    @Test
    public void playCornerFirst() {
        move = computerFirst.move();
        assertTrue(move.isCorner());
    }

    @Test
    public void replyToCornerWithCorner() {
        move = computerFirst.move();
        board.mark(move.getRow(), move.getCol(), 'X');
        board.mark(BOARD_SIZE-1, BOARD_SIZE-1, 'O');
        move = computerFirst.move();
        assertTrue(move.isCorner());
    }

    @Test
    public void replyToEdgeWithCentre() {
        move = computerFirst.move();
        board.mark(move.getRow(), move.getCol(), 'X');
        board.mark(0, 1, 'O');
        move = computerFirst.move();
        assertTrue(move.isCentre());
    }

    @Test
    public void replyToCentreWithOppositeCorner() {
        move = computerFirst.move();
        int row = move.getRow();
        int col = move.getCol();
        board.mark(row, col, 'X');
        board.mark(BOARD_SIZE/2, BOARD_SIZE/2, 'O');
        move = computerFirst.move();
        assertTrue(move.isCorner());
        assertFalse(board.isValid(BOARD_SIZE-row, BOARD_SIZE-col));
    }

    @Test
    public void testToNotLose() {
        move = computerFirst.move();
        board.mark(move.getRow(), move.getCol(), 'X');

        board.mark(BOARD_SIZE/2, BOARD_SIZE/2, 'O');
        move = computerSecond.move();
        board.mark(move.getRow(), move.getCol(), 'X');

        board.mark(0, 1, 'O');
        move = computerSecond.move();
        board.mark(move.getRow(), move.getCol(), 'X');

        assertTrue(move.isEdge());
        assertFalse(board.isComplete(move.getRow(), move.getCol()));
    }

    // Human first

    @Test
    public void testCornerAndCorner() {
        board.mark(0, BOARD_SIZE-1, 'X');
        move = computerSecond.move();
        assertTrue(move.isCentre());

        board.mark(move.getRow(), move.getCol(), 'O');
        board.mark(BOARD_SIZE-1, 0, 'X');
        move = computerSecond.move();
        assertTrue(move.isEdge());
    }

    @Test
    public void testCornerAndEdge() {
        board.mark(0, BOARD_SIZE-1, 'X');
        move = computerSecond.move();
        assertTrue(move.isCentre());

        board.mark(move.getRow(), move.getCol(), 'O');
        board.mark(1, 0, 'X');
        move = computerSecond.move();
        assertTrue(move.isCorner());
    }

    @Test
    public void testHumanToEdge() {
        board.mark(0, 1, 'X');
        move = computerSecond.move();
        assertTrue(move.isCorner());
        assertEquals(move.getRow(), 0);

        board.mark(move.getRow(), move.getCol(), 'O');
        board.mark(1, 0, 'X');
        move = computerSecond.move();
        assertTrue(move.isCentre());
    }

    @Test
    public void testHumanToCentre() {
        board.mark(BOARD_SIZE/2, BOARD_SIZE/2, 'X');
        move = computerSecond.move();
        assertTrue(move.isCorner());

        board.mark(move.getRow(), move.getCol(), 'O');
        board.mark(BOARD_SIZE-1, BOARD_SIZE-1, 'X');
        move = computerSecond.move();
        assertTrue(move.isCorner());
    }

    @Test
    public void testToWin() {
        board.mark(1, 0, 'X');
        move = computerSecond.move();
        board.mark(move.getRow(), move.getCol(), 'O');

        board.mark(0, 0, 'X');
        move = computerSecond.move();
        board.mark(move.getRow(), move.getCol(), 'O');

        board.mark(BOARD_SIZE-1, 1, 'X');
        move = computerSecond.move();
        board.mark(move.getRow(), move.getCol(), 'O');

        assertTrue(move.isCorner());
        assertTrue(board.isComplete(move.getRow(), move.getCol()));
    }

}