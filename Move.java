class Move {

    private static final int BOARD_SIZE = 3;

    private int row;
    private int col;

    Move() {
        this.row = -1;
        this.col = -1;
    }

    Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    boolean isValid() {
        return row >= 0 && row < BOARD_SIZE &&
               col >= 0 && col < BOARD_SIZE;
    }

    boolean isEdge() {
        return (col == 0 || col == BOARD_SIZE-1 ||
                row == 0 || row == BOARD_SIZE-1)
                && !isCorner();
    }

    boolean isCentre() {
        return row == BOARD_SIZE/2 &&
               col == BOARD_SIZE/2;
    }

    boolean isCorner() {
        if (row == 0 || row == BOARD_SIZE-1) {
            return (col == 0 || col == BOARD_SIZE-1);
        } else {
            return false;
        }
    }
}