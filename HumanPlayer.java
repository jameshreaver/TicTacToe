class HumanPlayer implements Player {

    private char id;
    private TicTacToe game;

    HumanPlayer(char id, TicTacToe game) {
        this.id = id;
        this.game = game;
    }

    public char getId() {
        return id;
    }

    public Move move() {
        return game.getUI().getMove();
    }

}