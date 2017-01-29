# Unbeatable Tic-Tac-Toe

This is a coding exercise. It is a command-line game of Tic-Tac-Toe which
supports both human and computer players. The computer player uses a Negamax algorithm with alpha-beta pruning to compute the best possible move and it cannot be defeated.

### Running Tic-Tac-Toe

To run the program, clone the repository and compile the source files with the
command:
```
make
```
Then you can run the game with:
```
make run
```
To run the test suite simply execute:
```
make runtests
```
When you are done, clean it all up with:
```
make clean
```

### Changelog

#### Version 1.0

- Redesigned Board and Players to be stateless.
- Abstracted out the concept of UI for the game.
- Redesigned AI to use Negamax with alpha-beta pruning.
- Improved representation of the concept of a Null Move.
- Refactored the Players' ids according to DRY principle.
- Updated the test suite.

#### Version 0.1

- Uploaded the test suite.
- Removed Computer Player state booleans.
- Added a missing check for valid Move to method for blocking corner.


##
This program was developed by Giacomo Guerci in January 2017 at Imperial
College London. The code is provided without a licence and all the rights are
reserved.