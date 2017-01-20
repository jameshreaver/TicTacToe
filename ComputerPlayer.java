class ComputerPlayer implements Player {

    private char id;
    private int size;
    private Board board;
    private Move currentMove;
    private Move previousMove;
    private int turn;

    ComputerPlayer(char id, Board board) {
        this.id = id;
        this.board = board;
        this.size = board.getSize();
        this.currentMove = new Move();
        this.previousMove = new Move();
        this.turn = 0;
    }

    public char getId() {
        return id;
    }

    public Move move() {
        Move move = board.getLastMove();

        if (!isFirstTurn()) {
            tryToWin();
            tryToNotLose();
        } else if (playingFirst()) {
            tryToCorner();
        }

        if (!playingFirst() &&
                previousMove.isCorner()) {
            tryToCentre();
        }

        if (move.isCorner()) {
            opponentToCorner();
        } else if (move.isEdge()) {
            opponentToEdge();
        } else if (move.isCentre()) {
            opponentToCentre();
        }

        moveToAny();
        turn++;

        previousMove = currentMove;
        currentMove = new Move();
        return previousMove;
    }

    private void opponentToCorner() {
        if (playingFirst()) {
            tryToCorner();
        } else {
            if (isFirstTurn()) {
                tryToCentre();
            } else if (previousMove.isCorner()) {
                tryToCorner();
            } else {
                tryToEdge();
            }
        }
    }

    private void opponentToEdge() {
        if (playingFirst()) {
            tryToCentre();
        } else {
            if (isFirstTurn()) {
                tryToCloseCorner();
            } else {
                tryToBlockCorner();
            }
        }
    }

    private void opponentToCentre() {
        if (playingFirst()) {
            tryToOppositeCorner();
        } else {
            tryToCorner();
        }
    }

    private boolean isFirstTurn() {
        return turn == 0;
    }

    private boolean playingFirst() {
        return id == 'X';
    }

    private void tryToWin() {
        if (currentMove.isValid()) return;
        currentMove = winningMoveFor(id, previousMove);
    }

    private void tryToNotLose() {
        if (currentMove.isValid()) return;
        char opponent = (id == 'X') ? 'O' : 'X';
        currentMove = winningMoveFor(opponent, board.getLastMove());
    }

    private void tryToCentre() {
        if (currentMove.isValid()) return;
        int centre = size/2;
        if (board.isValid(centre, centre)) {
            currentMove = new Move(centre, centre);
        }
    }

    private void tryToCorner() {
        if (currentMove.isValid()) return;
        int[] edges = {0, size-1};
        for (int row : edges) {
            for (int col : edges) {
                if (board.isValid(row, col)) {
                    currentMove = new Move(row, col);
                    return;
                }
            }
        }
    }

    private void tryToOppositeCorner() {
        if (currentMove.isValid()) return;
        int row = size - previousMove.getRow()-1;
        int col = size - previousMove.getCol()-1;
        if (board.isValid(row, col)) {
            currentMove = new Move(row, col);
        }
    }

    private void tryToCloseCorner() {
        if (currentMove.isValid()) return;
        int row = board.getLastMove().getRow();
        int col = board.getLastMove().getCol();
        if (row == 0 || row == size-1) {
            if (board.isValid(row, size-1)) {
                currentMove = new  Move(row, size-1);
            } else if (board.isValid(row, 0)){
                currentMove = new  Move(row, 0);
            }
        } else {
            if (board.isValid(size-1, col)) {
                currentMove = new Move(size-1, col);
            } else if (board.isValid(0, col)){
                currentMove = new Move(0, col);
            }
        }
    }

    private void tryToBlockCorner() {
        if (currentMove.isValid()) return;
        int row = board.getLastMove().getRow();
        int col = board.getLastMove().getCol();
        if (row == 0 || row == size-1) {
            if (board.isValid(size-row-1, 0)) {
                if (board.isValid(row, size-1)) {
                    currentMove = new Move(row, size-1);
                }
            } else if (board.isValid(row, 0)) {
                currentMove = new Move(row, 0);
            }
        } else {
            if (board.isValid(0, size-col-1)) {
                if (board.isValid(size-1, col)) {
                    currentMove = new Move(size-1, col);
                }
            } else if (board.isValid(0, col)) {
                currentMove = new Move(0, col);
            }
        }
    }

    private void tryToEdge(){
        if (currentMove.isValid()) return;
        int[] edges = {0, size-1};
        for (int row : edges) {
            for (int col = 1; col < size - 1; col++) {
                if (board.isValid(row, col)) {
                    currentMove = new Move(row, col);
                    return;
                }
            }
        }
        for (int row = 1; row < size-1; row++) {
            for (int col : edges) {
                if (board.isValid(row, col)) {
                    currentMove = new Move(row, col);
                    return;
                }
            }
        }
    }

    private void moveToAny() {
        if (currentMove.isValid()) return;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.isValid(row, col)) {
                    currentMove = new Move(row, col);
                    return;
                }
            }
        }
    }

    private Move winningMoveFor(char id, Move lastMove) {
        int row = lastMove.getRow();
        int col = lastMove.getCol();
        for (Orientation or : Orientation.values()) {
            int marks = 0;
            int nulls = 0;
            int win = 0;
            for (int i = 0; i < size; i++) {
                char mark = board.getChar(row, col, i, or);
                if (mark == id) {
                    marks++;
                } else if (mark == ' ') {
                    nulls++;
                    win = i;
                }
            }
            if (nulls == 1 && marks == size-1) {
                switch (or) {
                    case ROW : return new Move(row, win);
                    case COL : return new Move(win, col);
                    case POS : return new Move(win, size-win-1);
                    case NEG : return new Move(win, win);
                }
            }
        }
        return new Move();
    }

    public void reset() {
        this.turn = 0;
        this.previousMove = new Move();
    }
}