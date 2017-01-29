interface UIInterface {

    void startup();
    void update();
    void gameover();
    boolean playAgain();
    boolean isHuman(char id);
    Move getMove();

}
