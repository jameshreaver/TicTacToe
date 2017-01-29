import java.util.Arrays;


class Board {

    private static final char EMPTY = ' ';

    private int width;
    private char[][] board;

    Board(int width) {
        this.width = width;
        this.board = new char[getWidth()][getWidth()];
        for (char[] row : board) {
            Arrays.fill(row, EMPTY);
        }
    }

    int getWidth() {
        return width;
    }

    char getMarkAt(int row, int col) {
        return board[row][col];
    }

    boolean isEmptyAt(int row, int col) {
        return board[row][col] == EMPTY;
    }

    boolean isFilled() {
        return getMarks() == getWidth() * getWidth();
    }

    void apply(Move move, char mark) {
        if (isValid(move)) {
            board[move.getRow()][move.getCol()] = mark;
        }
    }

    int getMarks() {
        int marks = 0;
        for (int row = 0; row < getWidth(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                if (!isEmptyAt(row, col)) marks++;
            }
        }
        return marks;
    }

    boolean isValid(Move move) {
        if (move.isNull()) {
            return false;
        }
        int row = move.getRow();
        int col = move.getCol();
        return row >= 0 && row < getWidth() &&
               col >= 0 && col < getWidth() &&
               isEmptyAt(row, col);
    }

    boolean isComplete(Move move) {
        for (Orientation or : Orientation.values()) {
            if (isOrComplete(or, move.getRow(), move.getCol())) {
                return true;
            }
        }
        return false;
    }

    private boolean isOrComplete(Orientation or, int row, int col) {
        char mark = getMarkAt(row, col);
        for (int i = 0; i < getWidth(); i++) {
            char currentMark = getOrChar(or, row, col, i);
            if (currentMark == EMPTY || currentMark != mark) {
                return false;
            }
        }
        return true;
    }

    private char getOrChar(Orientation or, int row, int col, int i) {
        switch (or) {
            case ROW : return board[row][i];
            case COL : return board[i][col];
            case POS : return board[i][getWidth()-i-1];
            case NEG : return board[i][i];
            default  : return EMPTY;
        }
    }

    private enum Orientation {
        ROW, // Horizontal
        COL, // Vertical
        POS, // Positive Diagonal
        NEG  // Negative Diagonal
    }

}