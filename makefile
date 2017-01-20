JUNIT = ./junit.jar
HAMCREST = ./hamcrest-core.jar
JUNITCORE = org.junit.runner.JUnitCore

CLASS = TicTacToe
TESTS = TicTacToeTests

all :
	javac *.java -cp .:$(JUNIT)

run :
	java $(CLASS)

runtests :
	java -cp .:$(JUNIT):$(HAMCREST) $(JUNITCORE) $(TESTS)

clean :
	rm *.class