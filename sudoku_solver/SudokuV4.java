package sudoku_solver;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SudokuV4 {

  private static int[][] givenBoard = new int[9][9];

  static boolean isFullSolution(Cell[][] board)
  {
	for(int i = 0; i<9; i++)
    {
      for(int j = 0; j<9; j++)
      {
        if(board[i][j].value == 0)
          return false;
      }
    }
    return true;
  }

  static boolean isFullSolution(int[][] board)
  {
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
    if(checkBoard(board))
      return false;
    else
    {
      return true;
    }
  }

  static boolean reject(Cell[][] board)
  {
    updateBoard(board);
	/* for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
		  System.out.print("Reject: Possible Values of [" + i + ", " + j + "]: ");
		for(int k = 0; k<board[i][j].possibleVals.size(); k++)
			System.out.print(board[i][j].possibleVals.get(k));
			System.out.println();
	  }
	} */
    for(int i = 0; i < 9; i++)
    {
      //For all columns
      for(int j = 0; j < 9; j++)
      {
        if(board[i][j].type != Cell.CellType.GIVEN)
        {
          if (board[i][j].possibleVals.size() == 0)
          {
            //System.out.println("Cell[" + i + ", " + j + "] has no possible values --> Solution rejected");
            return true;
          }
        }
      }
    }
    return false;
  }

  static int[][] extend(int[][] board)
  {
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
    for(int i = 0; i < 9; i++)
    {
      //For all columns
      for(int j = 0; j < 9; j++)
      {
        //Find the next empty cell
        if(board[i][j] == 0)
        {
          //Set empty cell to initial value of 1
          tempBoard[i][j] = findNextValid(board, i, j);

          //Return the new board
          return tempBoard;
        }
      }
    }

    //If no empty cells are found, return null
    return null;
  }

  static Cell[][] extend(Cell[][] board)
  {
    //Copy all values from current board to tempBoard
	//System.out.println("Extend");
    Cell[][] tempBoard = new Cell[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
          tempBoard[i][j] = new Cell(board[i][j]);
/* 		  System.out.print("Possible Values of [" + i + ", " + j + "]: ");
		  for(int k = 0; k<tempBoard[i][j].possibleVals.size(); k++)
			System.out.print(tempBoard[i][j].possibleVals.get(k));
			System.out.println(); */
      }
    }

    updateBoard(tempBoard);


    for(int i = 0; i < 9; i++)
    {
      //For all columns
      for(int j = 0; j < 9; j++)
      {
        //Find the next empty cell
        if(tempBoard[i][j].value == 0)
        {
          if(tempBoard[i][j].possibleVals.size() == 0)
          {
            tempBoard[i][j].clearCell();
            return null;
          }
          tempBoard[i][j].value = tempBoard[i][j].possibleVals.get(0);
		  tempBoard[i][j].type = Cell.CellType.TEST;
          //System.out.println("Cell [" + i + ", " + j + "] type: " + tempBoard[i][j].type);
          // tempBoard.printBoard();
          return tempBoard;
        }
      }
    }
    return null;
  }

  static int[][] next(int[][] board)
  {
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
        if(board[i][j] != givenBoard[i][j])
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
            tempBoard[i][j] = findNextValid(board, i, j);
            //System.out.println("Next:");
            //printBoard(board);
            return tempBoard;
          }
        }
      }
    }
    return tempBoard;
  }

  static Cell[][] next(Cell[][] board)
  {
    Cell[][] tempBoard = new Cell[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
          tempBoard[i][j] = new Cell(board[i][j]);
      }
    }
    for(int i = 8; i>=0; i--)
    {
      for(int j = 8; j>=0; j--)
      {
        if(tempBoard[i][j].type == Cell.CellType.TEST)
        {
			//System.out.println("Cell [" + i + ", " + j+ "] is TEST");
			if(tempBoard[i][j].possibleVals.size() > 0)
			{
				for(int k = 0; k < tempBoard[i][j].possibleVals.size(); k++)
				{
				  if(tempBoard[i][j].possibleVals.get(k) > tempBoard[i][j].value)
				  {
					tempBoard[i][j].value = tempBoard[i][j].possibleVals.get(k);
					//System.out.println("Next:");
					//printBoard(tempBoard);
					return tempBoard;
				  }

				}
			}
			tempBoard[i][j].value = 0;
			tempBoard[i][j].type = Cell.CellType.EMPTY;
			updateBoard(tempBoard);
        }
      }
    }
	//System.out.println("Next returned null");
    return null;
  }

  static void testIsFullSolution()
  {

  }

  static void testReject()
  {

  }

  static void testExtend()
  {

  }

  static void testNext()
  {

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

  static void printBoard(Cell[][] board)
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
          System.out.print(board[i][j].value + " | ");
        }
        else
        {
          System.out.print(board[i][j].value);
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

  static Cell[][] solve(Cell[][] board)
  {
    if (reject(board)) return null;
    if (isFullSolution(board)) return board;
    Cell[][] attempt = extend(board);
	//printBoard(attempt);
    while (attempt != null)
    {
      Cell[][] solution = solve(attempt);
      if (solution != null) return solution;
      attempt = next(attempt);
    }
    return null;
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

  static Cell[][] copyBoard2(int[][] board)
  {
	Cell[][] boardGrid = new Cell[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
          if(givenBoard[i][j] != 0)
          {
            boardGrid[i][j] = new Cell(givenBoard[i][j], Cell.CellType.GIVEN);
          }
          else
            boardGrid[i][j] = new Cell();
      }
    }
	return boardGrid;
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
      int[][] board1 = copyBoard(givenBoard);
	  Cell[][] board2 = copyBoard2(givenBoard);
      printBoard(board1);
      System.out.println("Solution:");
      printBoard(solve(board1));
    }
  }


  //Cell class
  private static class Cell
  {
	public enum CellType {GIVEN, EMPTY, TEST};

    private int value;
    private List<Integer> possibleVals;
    private CellType type;

    public Cell()
    {
      this(0, CellType.EMPTY);
    }

    public Cell(Cell cellToCopy)
    {
      this.value = cellToCopy.value;
      this.type = cellToCopy.type;
      this.possibleVals = new ArrayList<>();
      for(int i = 0; i<cellToCopy.possibleVals.size(); i++)
      {
        int x = cellToCopy.possibleVals.get(i);
        this.possibleVals.add(x);
      }

    }

    public Cell(CellType type)
    {
      this(0, type);
    }

    public Cell(int val, CellType type)
    {
      this.value = val;
      this.type = type;
      this.possibleVals = new ArrayList<>();
      if(type == CellType.GIVEN)
        possibleVals.add(val);
      else
      {
        for(int i = 0; i<9; i++)
        {
          possibleVals.add(i+1);
        }
      }
    }

	public void removePossibleVal(int val)
	{
		boolean result = possibleVals.remove((Integer)val);
	}

	public void resetPossibleVals()
	{
		if(type != CellType.GIVEN)
		{
			possibleVals = new ArrayList<>();
			for(int i = 0; i<9; i++)
			{
				possibleVals.add(i+1);
			}
		}

	}

	public void clearCell()
	{
		value = 0;
		type = CellType.EMPTY;
	}
  }



  public static void updateBoard(Cell[][] board)
  {
	//System.out.println("Update: ");
    for(int row = 0; row<9; row++)
    {
      for(int col = 0; col<9; col++)
      {
        board[row][col].resetPossibleVals();
		if(board[row][col].type != Cell.CellType.GIVEN)
		{
			//System.out.println("Cell [" + row + ", " + col + "] is GIVEN");
			for(int i = 0; i<9; i++)
			{
				int rowVal = board[row][i].value;
				int colVal = board[i][col].value;
				if(i != col && rowVal != 0)
				{
					//System.out.println("Removing " + rowVal + " from list of possibilities for cell [" + row + ", " + col + "] due to row check");
					board[row][col].removePossibleVal(rowVal);
				}

				if(i != row && colVal != 0)
				{
					//System.out.println("Removing " + colVal + " from list of possibilities for cell [" + row + ", " + col + "] due to column check");
					board[row][col].removePossibleVal(colVal);
				}

			}
			int startRow = (row/3)*3;
			int startCol = (col/3)*3;
			for(int i = startRow; i<(startRow+3); i++)
			{
				for(int j = startCol; j<(startCol+3); j++)
				{
				  int regVal = board[i][j].value;
				  if(i != row && j != col && regVal != 0)
				  {
					board[row][col].removePossibleVal(regVal);
					//System.out.println("Removing " + regVal + " from list of possibilities for cell [" + row + ", " + col + "] due to region check");
				  }

				}
			}
		}
		else
		{
		  board[row][col].possibleVals = new ArrayList<>();
		  board[row][col].possibleVals.add(board[row][col].value);
		}
      }
    }
  }

}
