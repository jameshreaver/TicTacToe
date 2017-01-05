import java.util.Scanner;


class HumanPlayer implements Player {

    private char id;
    private Board board;

    HumanPlayer(char id, Board board) {
        this.id = id;
        this.board = board;
    }

    public char getId() {
        return id;
    }

    public Move move() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your move (e.g. B2):");
        String input = scanner.nextLine().toUpperCase();
        Move move = parseMove(input);

        while (!board.isValid(move.getRow(), move.getCol())) {
            System.out.format("%s is an invalid move.%n", input);
            System.out.println("Enter your move (e.g. B2):");
            input = scanner.nextLine().toUpperCase();
            move = parseMove(input);
        }
        return move;
    }

    private Move parseMove(String input) {
        if (input.length() != 2)
            return new Move();
        char rowLetter = input.toCharArray()[0];
        char colNumber = input.toCharArray()[1];
        int row = rowLetter - 'A';
        int col = colNumber - '1';
        return new Move(row, col);
    }

    public void reset() {}
}