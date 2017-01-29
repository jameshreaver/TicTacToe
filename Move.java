class Move {

    private int row;
    private int col;

    Move() {}

    Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    boolean isNull() {
        return false;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

}