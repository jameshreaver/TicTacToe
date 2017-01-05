CLASS = TicTacToe

all :
	javac *.java

run :
	java $(CLASS)

clean :
	rm *.class