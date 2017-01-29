import java.util.Scanner;

class UICommandLine implements UIInterface {

    private TicTacToe game;
    private Scanner scanner;

    UICommandLine(TicTacToe game) {
        this.game = game;
        this.scanner = new Scanner(System.in);
    }

    public void startup() {
        System.out.println("Welcome to Tic Tac Toe!\n");
    }

    public void update() {
        System.out.println(stringify(game.getBoard()));
        System.out.format("Player %c's turn.%n%n",
                game.getPlayer().getId());
    }

    public void gameover() {
        System.out.println(stringify(game.getBoard()));
        System.out.println("GAME OVER!\n");
    }

    public boolean playAgain() {
        System.out.println("Do you want to play again?");
        System.out.println("Enter 'Y' for Yes or 'N' for No:");
        String type = scanner.nextLine().toUpperCase();
        return type.equals("Y");
    }

    public boolean isHuman(char id) {
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

    public Move getMove() {
        System.out.println("Enter your move (e.g. B2):");
        String input = scanner.nextLine().toUpperCase();
        Move move = parseMove(input);

        while (!game.getBoard().isValid(move)) {
            System.out.format("%s is an invalid move.%n", input);
            System.out.println("Enter your move (e.g. B2):");
            input = scanner.nextLine().toUpperCase();
            move = parseMove(input);
        }
        return move;
    }

    private Move parseMove(String input) {
        if (input.length() != 2)
            return new NullMove();
        char rowLetter = input.toCharArray()[0];
        char colNumber = input.toCharArray()[1];
        int row = rowLetter - 'A';
        int col = colNumber - '1';
        return new Move(row, col);
    }

    private String stringify(Board board) {
        StringBuilder sb = new StringBuilder();
        sb.append("   ");
        for (int i = 0; i < board.getWidth(); i++) {
            sb.append(i+1);
            sb.append("  ");
        }
        sb.append('\n');
        for (int i = 0; i < board.getWidth(); i++) {
            sb.append((char)('A'+i));
            sb.append(' ');
            for (int j = 0; j < board.getWidth(); j++) {
                sb.append('[');
                sb.append(board.getMarkAt(i, j));
                sb.append(']');
            }
            sb.append('\n');
        }
        sb.append('\n');
        return sb.toString();
    }

}