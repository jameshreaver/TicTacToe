import java.util.Arrays;


class Board {

    private int size;
    private char[][] board;
    private Move lastMove;

    Board(int size) {
        this.size = size;
        initialise();
    }

    void initialise() {
        this.lastMove = new Move();
        this.board = new char[size][size];
        for (char[] row : board) {
            Arrays.fill(row, ' ');
        }
    }

    void mark(int row, int col, char mark) {
        assert isValid(row, col);
        board[row][col] = mark;
        lastMove = new Move(row, col);
    }

    boolean isValid(int row, int col) {
        if (row >= 0 && row < size &&
            col >= 0 && col < size) {
            return board[row][col] == ' ';
        } else {
            return false;
        }
    }

    boolean isComplete(int row, int col) {
        for (Orientation or : Orientation.values()) {
            if (isOrComplete(or, row, col)) {
                return true;
            }
        }
        return false;
    }

    private boolean isOrComplete(Orientation or, int row, int col) {
        boolean containsX = false;
        boolean containsO = false;
        for (int i = 0; i < size; i++) {
            switch (getChar(row, col, i, or)) {
                case 'X': containsX = true; break;
                case 'O': containsO = true; break;
                default : return false;
            }
        }
        return containsX ^ containsO;
    }

    char getChar(int row, int col, int i, Orientation or) {
        switch (or) {
            case ROW : return board[row][i];
            case COL : return board[i][col];
            case POS : return board[i][size-i-1];
            case NEG : return board[i][i];
            default  : return ' ';
        }
    }

    Move getLastMove() {
        return lastMove;
    }

    int getSize() {
        return size;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("   ");
        for (int i = 0; i < size; i++) {
            sb.append(i+1);
            sb.append("  ");
        }
        sb.append('\n');
        for (int i = 0; i < size; i++) {
            sb.append((char)('A'+i));
            sb.append(' ');
            for (int j = 0; j < size; j++) {
                sb.append('[');
                sb.append(board[i][j]);
                sb.append(']');
            }
            sb.append('\n');
        }
        sb.append('\n');
        return sb.toString();
    }
}