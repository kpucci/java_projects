# CS/COE 445 Project 3

# Motivation
In this assignment, you will use backtracking to solve the Sudoku puzzle. If you are not familiar with this puzzle, review the rules here:
http://www.sudoku.name/rules/en

## Provided files
### Provided Code
The Queens class includes a backtracking solution to the 8 Queens problem. If you run this class with the -t flag, it will test its reject, isFullSolution, extend, next methods.

The Sudoku class includes the basic skeleton of a backtracking solution to the Sudoku puzzle. This class includes the method readBoard, which reads in a board and returns it as a 9 × 9 int array. This array will contain a 0 for all empty cells. It also includes the method printBoard, which prints out a board to System.out.

It is recommended that you use this provided code as a starting point. However, you may choose not to use this provided code, as long as your program reads the same input format and uses the required backtracking techniques.

### Example Inputs
Example Sudoku boards are available for you to test your code in the boards/ directory within your personal Box folder.

## Tasks
You must write a backtracking program to find values for all of the cells in a Sudoku puzzle, without changing any of the cells specified in the original puzzle. You must also satisfy the Sudoku rules: no number may appear twice in any row, column, or region. You must accomplish this using the backtracking techniques discussed and demonstrated in lecture and in Queens.java. That is, you need to build up a solution recursively, one cell at a time, until you determine that the current board assignment is impossible to complete (in which case you will backtrack and try another assignment), or that the current board assignment is complete and valid.

Your program will read in the initial board from a file. This initial board will have several cells already filled. The remaining cells will be empty. The goal is to find the values of all the cells in the puzzle, without changing any of the cells specified in the original puzzle. This file will contain 9 lines of text, each containing 9 characters. Each character will either be a digit, 1 through 9, or a non-numeric character, such as a space or a dot (designating an empty cell).

our class must be named Sudoku, and therefore must be in a file with the name Sudoku.java. It must also be in the package cs445.a3. Your program must be usable from the command line using the following command:

java cs445.a3.Sudoku initial_board.su

If there is a way to solve the puzzle described in initial_board.su, your program should output the completed puzzle. You do not need to find every possible solution, if there are multiple solutions—just one solution will do. If there is no solution, your program should output a message indicating as such.

### Required Methods
As stated above, you must use the techniques we discussed in Lecture 12 for recursive backtracking. As such, you will then need to write the following methods to support your backtracking algorithm.

* isFullSolution, a method that accepts a partial solution and returns true if it is a complete, valid solution.

* reject, a method that accepts a partial solution and returns true if it should be rejected because it can
  never be extended into a complete solution.

* extend, a method that accepts a partial solution and returns another partial solution that includes one
  additional choice added on. This method will return null if there are no more choices to add to the solution.

* next, a method that accepts a partial solution and returns another partial solution in which the most recent
  choice that was added has been changed to its next option. This method will return null if there are no more options for the most recent choice that was made.

### Test Methods

When developing a complex program such as this one, it is important to test your progress as you go. For this reason, in addition to the backtracking-supporting methods above, you will be required to test your methods as you develop them. Re-read starting at Chapter 2.16 to review the concepts behind writing test methods. To test each of the backtracking methods, you need to write the following test methods. For each one, you should create a wide variety of partial solutions that fit as many corner cases as you can think of. Include enough test cases that the correct output convinces you that your method works properly in all situations.

* testIsFullSolution, a method that generates partial solutions and ensures that the isFullSolution method
  correctly determines whether each is a complete solution.

* testReject, a method that generates partial solutions and ensures that the reject method correctly determines
  whether each should be rejected.

* testExtend, a method that generates partial solutions and ensures that the extend method correctly extends
  each with the correct next choice.

* testNext, a method that generates partial solutions and ensures that the, in each, next method correctly
  changes the most recent choice that was added to its next option.

You may either hardcode your test partial solutions, or upload .su files containing the boards you want to test. In either case, when your program is run with the -t flag, your program must run the above four test methods and output the results.
