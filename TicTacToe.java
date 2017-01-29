public class TicTacToe {

    private static final int BOARD_WIDTH = 3;
    private static final char PLAYER1_ID = 'X';
    private static final char PLAYER2_ID = 'O';

    private Board board;
    private Player player1;
    private Player player2;
    private UIInterface ui;

    TicTacToe() {
        this.ui = new UICommandLine(this);
        this.board = new Board(BOARD_WIDTH);
        this.player1 = generatePlayer(PLAYER1_ID);
        this.player2 = generatePlayer(PLAYER2_ID);
    }

    public static void main (String[] args) {
        TicTacToe game = new TicTacToe();
        do {
            game.play();
            game.reset();
        } while (game.continues());
    }

    private void play() {
        ui.startup();
        Move move = new NullMove();
        while (!isGameOver(move)) {
            ui.update();
            move = getPlayer().move();
            makeMove(move);
        }
        ui.gameover();
    }

    private void reset() {
        this.board = new Board(BOARD_WIDTH);
    }

    private boolean continues() {
        return ui.playAgain();
    }

    private void makeMove(Move move) {
        board.apply(move, getPlayer().getId());
    }

    private boolean isGameOver(Move move) {
        if (move.isNull()) {
            return false;
        } else if (board.isComplete(move)) {
            return true;
        } else {
            return board.isFilled();
        }
    }

    private Player generatePlayer(char id) {
        return (ui.isHuman(id)) ? new HumanPlayer(id, this)
                                : new ComputerPlayer(id, this);
    }

    Player getPlayer() {
        return (board.getMarks() % 2 == 0) ? player1 : player2;
    }

    char getOpponentId(char id) {
        return (id == PLAYER1_ID) ? PLAYER2_ID : PLAYER1_ID;
    }

    UIInterface getUI() {
        return ui;
    }

    Board getBoard() {
        return board;
    }

}