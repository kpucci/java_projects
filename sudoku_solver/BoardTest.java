package sudoku_solver;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BoardTest
{
  public static void main(String[] args)
  {
    int[][] givenBoard = readBoard(args[0]);
    Board board = new Board(givenBoard);
    board.printBoard();
    board.updateBoard();
    //board.hiddenSingles();
    //board.updateBoard();
    //System.out.println();
    //board.printBoard();
    //testUpdate(board);
    /* while(!board.reject() && board.fillTrivial())
    {
      if(isFullSolution(board))
        break;
    } */
    //System.out.println();
    //board.printBoard();
    System.out.println("Solution:");
    if(!isFullSolution(board))
    {
      System.out.println("Solving board...");
      Board solvedBoard = solve(board);
      if(solvedBoard != null)
        solvedBoard.printBoard();
      else
        System.out.println("No Solution Found");
    }
    else
    {
      board.printBoard();
    }
  }

  static void testUpdate(Board board)
  {
    //board.updateBoard();
    for(int i = 0; i<9; i++)
    {
      for(int j = 0; j<9; j++)
      {
        System.out.print("Possible values of cell[" + i + ", " + j + "]: ");
        List<Integer> cellVals = board.getPossibleVals(i,j);
        for(int val: cellVals)
          System.out.print(val + " ");
        System.out.println();
      }
    }
  }

  static Board solve(Board board)
  {
    if (board.reject()) return null;
    if (isFullSolution(board)) return board;
    Board attempt = extend(board);
    while (attempt != null)
    {
      Board solution = solve(attempt);
      if (solution != null) return solution;
      attempt = next(attempt);
    }
    return null;
  }

  static boolean isFullSolution(Board board)
  {
    //Check if board is filled
    if(!board.isBoardFilled())
      return false;
    //If no elements are empty, check validity of solution
    return !board.reject();
	//return true;
  }

  static Board extend(Board board)
  {
    //Copy all values from current board to tempBoard
    Board tempBoard = new Board(board);
    tempBoard.updateBoard();
    for(int i = 0; i < 9; i++)
    {
      //For all columns
      for(int j = 0; j < 9; j++)
      {
        //Find the next empty cell
        if(tempBoard.getCellValue(i,j) == 0)
        {
          List<Integer> possibleVals = tempBoard.getPossibleVals(i,j);
          if(possibleVals.size() == 0)
          {
            tempBoard.clearCell(i,j);
            return null;
          }
          tempBoard.setCellValue(possibleVals.get(0), i, j);
          // System.out.println("Extend:");
          // tempBoard.printBoard();
          return tempBoard;
        }
      }
    }
    return null;
  }

  static Board next(Board board)
  {
    Board tempBoard = new Board(board);
    for(int i = 8; i>=0; i--)
    {
      for(int j = 8; j>=0; j--)
      {
        if(tempBoard.isTest(i,j))
        {
          List<Integer> possibleVals = tempBoard.getPossibleVals(i,j);
          if(possibleVals.size() > 0)
          {
            for(int k = 0; k < possibleVals.size(); k++)
            {
              if(possibleVals.get(k) > tempBoard.getCellValue(i,j))
              {
                tempBoard.setCellValue(possibleVals.get(k), i, j);
                // System.out.println("Next:");
                // tempBoard.printBoard();
                return tempBoard;
              }

            }
          }
          tempBoard.clearCell(i,j);
          tempBoard.updateBoard();
        }
      }
    }
    return null;
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
}
