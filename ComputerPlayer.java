import java.util.ArrayList;


class ComputerPlayer implements Player {

    private char id;
    private TicTacToe game;

    ComputerPlayer(char id, TicTacToe game) {
        this.id = id;
        this.game = game;
    }

    public char getId() {
        return id;
    }

    public Move move() {
        int a = -Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        Board board = game.getBoard();
        int depth = board.getWidth() * board.getWidth();
        return negamax(board, id, depth, a, b).move;
    }

    private ValueMove negamax(Board board, char id, int depth, int a, int b) {
        Move bestMove = new NullMove();
        Integer bestValue = Integer.MIN_VALUE;

        for (Move move : getMoves(board)) {
            Board newBoard = cloneBoard(board);
            newBoard.apply(move, id);

            if (newBoard.isComplete(move)) {
                return new ValueMove(depth, move);
            } else if (newBoard.isFilled()) {
                return new ValueMove(0, move);
            }

            char newId = game.getOpponentId(id);
            int value = -negamax(newBoard, newId, depth-1, -b, -a).value;

            if (value > bestValue) {
                bestMove = move;
                bestValue = value;
            }
            a = Integer.max(a, value);
            if (a >= b) {
                break;
            }
        }
        return new ValueMove(bestValue, bestMove);
    }

    private ArrayList<Move> getMoves(Board board) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int row = 0; row < board.getWidth(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (board.isEmptyAt(row, col)) {
                    moves.add(new Move(row, col));
                }
            }
        }
        return  moves;
    }

    private Board cloneBoard(Board board) {
        Board newBoard = new Board(board.getWidth());
        for (int row = 0; row < board.getWidth(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                Move move = new Move(row, col);
                char mark = board.getMarkAt(row, col);
                newBoard.apply(move, mark);
            }
        }
        return newBoard;
    }

    private class ValueMove {

        private final int value;
        private final Move move;

        private ValueMove(int value, Move move) {
            this.value = value;
            this.move = move;
        }
    }

}