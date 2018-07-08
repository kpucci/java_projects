package sudoku_solver;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SudokuV3 {

  private static int[][] givenBoard = new int[9][9];

  static void testIsFullSolution()
  {
	//Corner cases:
		//1. Board is null
		//2. Board is full, but wrong
		//3. Board is full and is correct

	//Test case 1:
	int[][] testBoard = null;
	if(isFullSolution(testBoard))
		System.out.println("isFullSolution test 1 failed");
	else
		System.out.println("isFullSolution test 1 passed");

	//Test case 2:
	testBoard = new int[9][9];
	for(int i = 0; i<9; i++)
	{
		for(int j = 0; j<9; j++)
		{
			testBoard[i][j] = i;
		}
	}

	if(isFullSolution(testBoard))
		System.out.println("isFullSolution test 2 failed");
	else
		System.out.println("isFullSolution test 2 passed");

	//Test case 3:
	List<String> easyBoardCompleted = new ArrayList<>();
	int val = 0;
	easyBoardCompleted.add("512689347");
	easyBoardCompleted.add("678431529");
	easyBoardCompleted.add("439527186");
	easyBoardCompleted.add("841293765");
	easyBoardCompleted.add("753146892");
	easyBoardCompleted.add("296875431");
	easyBoardCompleted.add("184762953");
	easyBoardCompleted.add("927358614");
	easyBoardCompleted.add("365914278");

	testBoard = new int[9][9];
    val = 0;
    for (int i = 0; i < 9; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        try
        {
          val = Integer.parseInt(Character.toString(easyBoardCompleted.get(i).charAt(j)));
        }
        catch (Exception e)
        {
          val = 0;
        }
        testBoard[i][j] = val;
      }
    }

	if(isFullSolution(testBoard))
		System.out.println("isFullSolution test 2 passed");
	else
		System.out.println("isFullSolution test 2 failed");
  }

  static void testReject()
  {
	/*Corner cases:
		1. Board is null
		2. Two values are equal in same row
		3. Two values are equal in same column
		4. Two values are equal in same region
		5. Board is incomplete, but doesn't have any wrong values and should not be rejected
		6. Board is complete and is correct solution, so should not be rejected
	*/

	//Easy board:
	List<String> easyBoard = new ArrayList<>();
	easyBoard.add("002680347");
	easyBoard.add("600000500");
	easyBoard.add("409000080");
	easyBoard.add("000093760");
	easyBoard.add("050106090");
	easyBoard.add("096870000");
	easyBoard.add("080000903");
	easyBoard.add("007000004");
	easyBoard.add("365014200");

	int[][] board = new int[9][9];
    int val = 0;
    for (int i = 0; i < 9; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        try
        {
          val = Integer.parseInt(Character.toString(easyBoard.get(i).charAt(j)));
        }
        catch (Exception e)
        {
          val = 0;
        }
        board[i][j] = val;
      }
    }

	//Test case 1:
	int[][] testBoard = null;
	if(reject(testBoard))
		System.out.println("Reject test 1 passed");
	else
		System.out.println("Reject test 1 failed");

	//Test case 2:
	testBoard = copyBoard(board);
	testBoard[0][0] = 8;

	if(reject(testBoard))
		System.out.println("Reject test 2 passed");
	else
		System.out.println("Reject test 2 failed");

	//Test case 3:
	testBoard = copyBoard(board);
	testBoard[0][1] = 5;
	if(reject(testBoard))
		System.out.println("Reject test 3 passed");
	else
		System.out.println("Reject test 3 failed");

	//Test case 4:
	testBoard = copyBoard(board);
	testBoard[0][0] = 9;
	if(reject(testBoard))
		System.out.println("Reject test 4 passed");
	else
		System.out.println("Reject test 4 failed");

	//Test case 5:
	testBoard = copyBoard(board);
	if(reject(testBoard))
		System.out.println("Reject test 5 failed");
	else
		System.out.println("Reject test 5 passed");

	//Test case 6:
	List<String> easyBoardCompleted = new ArrayList<>();
	easyBoardCompleted.add("512689347");
	easyBoardCompleted.add("678431529");
	easyBoardCompleted.add("439527186");
	easyBoardCompleted.add("841293765");
	easyBoardCompleted.add("753146892");
	easyBoardCompleted.add("296875431");
	easyBoardCompleted.add("184762953");
	easyBoardCompleted.add("927358614");
	easyBoardCompleted.add("365914278");

	testBoard = new int[9][9];
    val = 0;
    for (int i = 0; i < 9; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        try
        {
          val = Integer.parseInt(Character.toString(easyBoardCompleted.get(i).charAt(j)));
        }
        catch (Exception e)
        {
          val = 0;
        }
        testBoard[i][j] = val;
      }
    }

	if(reject(testBoard))
		System.out.println("Reject test 6 failed");
	else
		System.out.println("Reject test 5 passed");

  }

  static void testExtend()
  {
	/* Corner cases:
		1. Board is null
		2. Extend value to first space, which is empty
		3. Extend value to first space, which is not empty --> should move to next space
		4. Board is filled
	*/

	//Easy board incomplete:
	List<String> easyBoard = new ArrayList<>();
	int val = 0;
	easyBoard.add("002680347");
	easyBoard.add("600000500");
	easyBoard.add("409000080");
	easyBoard.add("000093760");
	easyBoard.add("050106090");
	easyBoard.add("096870000");
	easyBoard.add("080000903");
	easyBoard.add("007000004");
	easyBoard.add("365014200");

	//Test case 2:
	int[][] eBoard = new int[9][9];
    for (int i = 0; i < 9; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        try
        {
          val = Integer.parseInt(Character.toString(easyBoard.get(i).charAt(j)));
        }
        catch (Exception e)
        {
          val = 0;
        }
        eBoard[i][j] = val;
      }
    }


	//Easy board complete:
	List<String> easyBoardCompleted = new ArrayList<>();
	easyBoardCompleted.add("512689347");
	easyBoardCompleted.add("678431529");
	easyBoardCompleted.add("439527186");
	easyBoardCompleted.add("841293765");
	easyBoardCompleted.add("753146892");
	easyBoardCompleted.add("296875431");
	easyBoardCompleted.add("184762953");
	easyBoardCompleted.add("927358614");
	easyBoardCompleted.add("365914278");

	int[][] eBoardComplete = new int[9][9];
    for (int i = 0; i < 9; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        try
        {
          val = Integer.parseInt(Character.toString(easyBoardCompleted.get(i).charAt(j)));
        }
        catch (Exception e)
        {
          val = 0;
        }
        eBoardComplete[i][j] = val;
      }
    }


	//Test case 1:
	int[][] board = null;
	int[][]	testBoard = extend(board);
	if(testBoard == null)
		System.out.println("Extend test 1 passed");
	else
		System.out.println("Extend test 1 failed");

	//Test case 2:
	board = copyBoard(eBoard);
	testBoard = extend(board);
	if(testBoard[0][0] != 0 && board[0][0] == 0)
		System.out.println("Extend test 2 passed");
	else
		System.out.println("Extend test 2 failed");


	//Test case 3:
	board = copyBoard(eBoard);
	board[0][0] = 5;
	testBoard = extend(board);
	if(testBoard[0][0] == 5 && testBoard[0][1] != 0 && board[0][1] == 0 && board[0][0] == 5)
		System.out.println("Extend test 3 passed");
	else
		System.out.println("Extend test 3 failed");

	//Test case 4:
	board = copyBoard(eBoardComplete);
	testBoard = extend(board);
	if(testBoard == null && board != null)
		System.out.println("Extend test 4 passed");
	else
		System.out.println("Extend test 4 failed");
  }

  static void testNext()
  {
	/* Corner cases:
		1. Board is null
		2. Board hasn't been extended yet
		3. Find next value in cell that can be solved
		4. Find next value in cell that can't be solved with current board
	*/

	//Easy board incomplete:
	List<String> easyBoard = new ArrayList<>();
	int val = 0;
	easyBoard.add("002680347");
	easyBoard.add("600000500");
	easyBoard.add("409000080");
	easyBoard.add("000093760");
	easyBoard.add("050106090");
	easyBoard.add("096870000");
	easyBoard.add("080000903");
	easyBoard.add("007000004");
	easyBoard.add("365014200");

	//givenBoard = new int[9][9];
    for (int i = 0; i < 9; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        try
        {
          val = Integer.parseInt(Character.toString(easyBoard.get(i).charAt(j)));
        }
        catch (Exception e)
        {
          val = 0;
        }
        givenBoard[i][j] = val;
      }
    }

	//Easy board complete:
	List<String> easyBoardCompleted = new ArrayList<>();
	easyBoardCompleted.add("512689347");
	easyBoardCompleted.add("678431529");
	easyBoardCompleted.add("439527186");
	easyBoardCompleted.add("841293765");
	easyBoardCompleted.add("753146892");
	easyBoardCompleted.add("296875431");
	easyBoardCompleted.add("184762953");
	easyBoardCompleted.add("927358614");
	easyBoardCompleted.add("365914278");

	int[][] eBoardComplete = new int[9][9];
    for (int i = 0; i < 9; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        try
        {
          val = Integer.parseInt(Character.toString(easyBoardCompleted.get(i).charAt(j)));
        }
        catch (Exception e)
        {
          val = 0;
        }
        eBoardComplete[i][j] = val;
      }
    }

	//Test case 1:
	int[][] board = null;
	int[][]	testBoard = next(board);
	if(testBoard == null)
		System.out.println("Next test 1 passed");
	else
		System.out.println("Next test 1 failed");

	//Test case 2:
	board = copyBoard(givenBoard);
	testBoard = next(board);
	if(testBoard[0][0] == board[0][0])
		System.out.println("Next test 2 passed");
	else
		System.out.println("Next test 2 failed");

	//Test case 3:
	board = copyBoard(givenBoard);
	board[0][0] = 4;
	testBoard = next(board);
	if(testBoard[0][0] == 5 && board[0][0] == 4)
		System.out.println("Next test 3 passed");
	else
		System.out.println("Next test 3 failed");

	//Test case 4:
	board = copyBoard(eBoardComplete);
	board[0][0] = 8;
	testBoard = next(board);
	if(testBoard == null && board[0][0] == 8)
		System.out.println("Next test 4 passed");
	else
		System.out.println("Next test 4 failed");
  }


  static int[][] solve(int[][] board)
  {
    if (reject(board)) return null;
    if (isFullSolution(board)) return board;
    int[][] attempt = extend(board);
    while (attempt != null)
    {
      int[][] solution = solve(attempt);
      if (solution != null) return solution;
      attempt = next(attempt);
    }
    return null;
  }

  static boolean isFullSolution(int[][] board)
  {
	if(board == null)
		return false;
    //For each row
    for(int i = 0; i < 9; i++)
    {
      //For each column
      for(int j = 0; j < 9; j++)
      {
        //If any array elements are empty, not a full solution
        if(board[i][j] == 0)
          return false;
      }
    }
    //If no elements are empty, check validity of solution
    if(reject(board))
      return false;

    return true;
  }

  static boolean reject(int[][] board)
  {
    //If all rows, columns, and regions are acceptable, don't reject
    if(board == null)
		return true;
	else if(checkBoard(board))
		return false;
    else
		return true;
  }

  static int[][] extend(int[][] board)
  {
    //Copy all values from current board to tempBoard
	if(board == null)
		return null;

    int[][] tempBoard = new int[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
          tempBoard[i][j] = board[i][j];
      }
    }

    //For all rows
    for(int i = 0; i < 9; i++)
    {
      //For all columns
      for(int j = 0; j < 9; j++)
      {
        //Find the next empty cell
        if(tempBoard[i][j] == 0)
        {
          //Set empty cell to first valid option
          tempBoard[i][j] = findNextValid(tempBoard, i, j);

          //Return the new board
          return tempBoard;
        }
      }
    }

    //If no empty cells are found, return null
    return null;
  }

  static int[][] next(int[][] board)
  {
	if(board == null)
		return null;
    //Copy all values from current board to tempBoard
    int[][] tempBoard = new int[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
          tempBoard[i][j] = board[i][j];
      }
    }

    //For all rows
    for(int i = 8; i >= 0; i--)
    {
      //For all columns
      for(int j = 8; j >= 0; j--)
      {
        //Find most recently changed cell
        if(tempBoard[i][j] != givenBoard[i][j])
        {
          //If cell is equal to 9, need to reject to previous cell
          if(tempBoard[i][j] == 9)
          {
            //Change type to empty so it can be changed again in the future
            board[i][j] = 0;
            return null;
          }
          //Otherwise, increment value in the cell and return new board
          else
          {
			int nextVal = findNextValid(tempBoard, i, j);
			if(nextVal != 9)
			{
				tempBoard[i][j] = findNextValid(tempBoard, i, j);
				return tempBoard;
			}
			else
			{
				board[i][j] = 0;
				return null;
			}
          }
        }
      }
    }
    return tempBoard;
  }


  private static boolean checkBoard(int[][] board)
  {
    //(0,0)(0,1)(0,2) | (0,3)(0,4)(0,5) | (0,6)(0,7)(0,8)
    //(1,0)(1,1)(1,2) | (1,3)(1,4)(1,5) | (1,6)(1,7)(1,8)
    //(2,0)(2,1)(2,2) | (2,3)(2,4)(2,5) | (2,6)(2,7)(2,8)
    //----------------------------------------------
    //(3,0)(3,1)(3,2) | (3,3)(3,4)(3,5) | (3,6)(3,7)(3,8)
    //(4,0)(4,1)(4,2) | (4,3)(4,4)(4,5) | (4,6)(4,7)(4,8)
    //(5,0)(5,1)(5,2) | (5,3)(5,4)(5,5) | (5,6)(5,7)(5,8)
    //----------------------------------------------
    //(6,0)(6,1)(6,2) | (6,3)(6,4)(6,5) | (6,6)(6,7)(6,8)
    //(7,0)(7,1)(7,2) | (7,3)(7,4)(7,5) | (7,6)(7,7)(7,8)
    //(8,0)(8,1)(8,2) | (8,3)(8,4)(8,5) | (8,6)(8,7)(8,8)

    //Region 1: (0,0)-(2,2) --> Starting row = 0, Starting column = 0
    //Region 2: (0,3)-(2,5) --> Starting row = 0, Starting column = 3
    //Region 3: (0,6)-(2,8) --> Starting row = 0, Starting column = 6
    //Region 4: (3,0)-(5,2) --> Starting row = 3, Starting column = 0
    //Region 5: (3,3)-(5,5) --> Starting row = 3, Starting column = 3
    //Region 6: (3,6)-(5,8) --> Starting row = 3, Starting column = 6
    //Region 7: (6,0)-(8,2) --> Starting row = 6, Starting column = 0
    //Region 8: (6,3)-(8,5) --> Starting row = 6, Starting column = 3
    //Region 9: (6,6)-(8,8) --> Starting row = 6, Starting column = 6

    //For rows 0, 3, and 6 (starting rows)
    for(int x = 0; x<7; x = x+3)
    {
      //For columns 0, 3, and 6 (starting columns)
      for(int y = 0; y<7; y = y+3)
      {
        //For each of the 3 rows in each 3x3 region [(0,1,2)|(3,4,5)|(6,7,8)] going down the board
        for(int n = x; n<(x+3); n++)
        {
          //For each of the 3 columns in each 3x3 region [(0,1,2)|(3,4,5)|(6,7,8)] going across the board
          for(int m = y; m<(y+3); m++)
          {
            //For each next row to compare to in each 3x3 region
            for(int i = x; i<(x+3); i++)
            {
              //For each next column to compare to in each 3x3 region
              for(int j = y; j<(y+3); j++)
              {
                //If current value = 0, skip it
                if(board[n][m] == 0)
                  break;
                //If the elements being compared aren't the same element
                else if(n != i && m != j)
                {
                  //If the two elements are equal, reject
                  if(board[n][m] == board[i][j])
                  {
                    return false;
                  }
                }
              }
            }
          }
        }
      }
    }

    //For all rows
    for(int r = 0; r<9; r++)
    {
      //For all columns in each row
      for(int i = 0; i<9; i++)
      {
        int j = i + 1;
        while(j<9)
        {
          //If current value is zero, skip it
          if(board[r][i] == 0)
          {
            break;
          }
          //If current value = any other value in row, reject it
          else if(board[r][i] == board[r][j])
          {
            return false;
          }
          //Otherwise, check current value vs next element in row
          else
          {
            j++;
          }
        }
      }
    }

    //For all columns
    for(int c = 0; c<9; c++)
    {
      //For all rows in each column
      for(int i = 0; i < 9; i++)
      {
        int j = i + 1;
        while(j<9)
        {
          //If current value = 0, skip it
          if(board[i][c] == 0)
          {
            break;
          }
          //If current value = any other value in column, reject it
          else if(board[i][c] == board[j][c])
          {
            return false;
          }
          //Otherwise, check current value vs next element in column
          else
          {
            j++;
          }
        }
      }
    }

    //If all regions pass, don't reject
    return true;
  }

  private static int findNextValid(int[][] board, int row, int col)
  {
    int testVal = board[row][col]+1;
    int startRow = (row/3)*3;
    int startCol = (col/3)*3;

    while(testVal < 10)
    {
      if(board[row][0] != testVal && board[row][1] != testVal && board[row][2] != testVal
      && board[row][3] != testVal && board[row][4] != testVal && board[row][5] != testVal
      && board[row][6] != testVal && board[row][7] != testVal && board[row][8] != testVal
      && board[0][col] != testVal && board[1][col] != testVal && board[2][col] != testVal
      && board[3][col] != testVal && board[4][col] != testVal && board[5][col] != testVal
      && board[6][col] != testVal && board[7][col] != testVal && board[8][col] != testVal
      && board[startRow][startCol] != testVal && board[startRow][startCol + 1] != testVal && board[startRow][startCol+2] != testVal
      && board[startRow+1][startCol] != testVal && board[startRow+1][startCol + 1] != testVal && board[startRow+1][startCol+2] != testVal
      && board[startRow+2][startCol] != testVal && board[startRow+2][startCol + 1] != testVal && board[startRow+2][startCol+2] != testVal)
        return testVal;
      else
        testVal++;
    }

    return 9;
  }

  static int[][] copyBoard(int[][] board)
  {
    int[][] tempBoard = new int[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
          tempBoard[i][j] = board[i][j];
      }
    }
    return tempBoard;
  }

  static void printBoard(int[][] board)
  {
    if (board == null)
    {
      System.out.println("No assignment");
      return;
    }
    for (int i = 0; i < 9; i++)
    {
      if (i == 3 || i == 6)
      {
        System.out.println("----+-----+----");
      }
      for (int j = 0; j < 9; j++) {
        if (j == 2 || j == 5)
        {
          System.out.print(board[i][j] + " | ");
        }
        else
        {
          System.out.print(board[i][j]);
        }
      }
      System.out.print("\n");
    }
  }

  static int[][] readBoard(String filename)
  {
    List<String> lines = null;
    try
    {
      lines = Files.readAllLines(Paths.get(filename), Charset.defaultCharset());
    }
    catch (IOException e)
    {
      System.out.println("Couldn't read file");
      return null;
    }
    int[][] board = new int[9][9];
    int val = 0;
    for (int i = 0; i < 9; i++)
    {
      for (int j = 0; j < 9; j++)
      {
        try
        {
          val = Integer.parseInt(Character.toString(lines.get(i).charAt(j)));
        }
        catch (Exception e)
        {
          val = 0;
        }
        board[i][j] = val;
      }
    }
    return board;
  }


  public static void main(String[] args)
  {
    if (args[0].equals("-t"))
    {
      testIsFullSolution();
      testReject();
      testExtend();
      testNext();
    }
    else
    {
      givenBoard = readBoard(args[0]);
      int[][] board = copyBoard(givenBoard);
      printBoard(board);
      System.out.println("Solution:");
      printBoard(solve(board));
    }
  }
}
