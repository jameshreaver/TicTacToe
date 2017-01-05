import java.util.Scanner;


public class TicTacToe {

    static final int BOARD_SIZE = 3;

    private int turn;
    private Board board;
    private Player playerX;
    private Player playerO;
    private boolean isGameOver;
    private boolean isDraw;

    TicTacToe() {
        this.turn = 0;
        this.board = new Board(BOARD_SIZE);
        this.playerX = generatePlayer('X');
        this.playerO = generatePlayer('O');
    }

    public static void main (String[] args) {
        TicTacToe game = new TicTacToe();
        do {
            game.play();
            game.reset();
        } while (game.playAgain());
    }

    private void play() {
        welcome();
        while (!isGameOver) {
            display();
            Player player = getCurrentPlayer();
            Move move = player.move();
            makeMove(move);
            checkGameOver(move);
            nextTurn();
        }
        gameover();
    }

    private void makeMove(Move move) {
        char id = getCurrentPlayer().getId();
        board.mark(move.getRow(), move.getCol(), id);
    }

    private void checkGameOver(Move move) {
        isGameOver = board.isComplete(move.getRow(), move.getCol());
        isDraw = !isGameOver && turn == BOARD_SIZE * BOARD_SIZE - 1;
        if (isDraw) {
            isGameOver = true;
        }
    }

    private Player getCurrentPlayer() {
        return (turn % 2 == 0) ? playerX : playerO;
    }

    private void nextTurn() {
        if (!isGameOver) {
            turn++;
        }
    }

    private Player generatePlayer(char id) {
        return (isHuman(id)) ? new HumanPlayer(id, board)
                             : new ComputerPlayer(id, board);
    }

    private boolean isHuman(char id) {
        Scanner scanner = new Scanner(System.in);
        System.out.format("Who is playing %c? ", id);
        System.out.println("Enter 'H' for Human or 'C' for Computer:");

        String type = scanner.nextLine().toUpperCase();
        while (!(type.equals("H") || type.equals("C"))) {
            System.out.println("Invalid option.");
            System.out.println("Enter 'H' for Human or 'C' for Computer:");
            type = scanner.nextLine().toUpperCase();
        }
        return type.equals("H");
    }

    private boolean playAgain() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play again?");
        System.out.println("Enter 'Y' for Yes or 'N' for No:");
        String type = scanner.nextLine().toUpperCase();
        return type.equals("Y");
    }

    private void welcome() {
        System.out.println("Welcome to Tic Tac Toe!\n");
    }

    private void display() {
        System.out.println(board);
        System.out.format("Player %c's turn.%n",
                getCurrentPlayer().getId());
    }

    private void gameover() {
        System.out.println(board);
        System.out.println("GAME OVER!");
        if (isDraw) {
            System.out.format("It's a draw.%n%n");
        } else {
            int id = getCurrentPlayer().getId();
            System.out.format("Player %c wins.%n%n", id);
        }
    }

    private void reset() {
        this.turn = 0;
        this.board.initialise();
        this.playerX.reset();
        this.playerO.reset();
        this.isGameOver = false;
        this.isDraw = false;
    }
}
