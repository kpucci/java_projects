package sudoku_solver;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Board
{
  private Cell[][] boardGrid;

  public enum CellType {GIVEN, EMPTY, TEST};

  //No-arg constructor
  public Board()
  {
    boardGrid = new Cell[9][9];
  }

  //Copy constructor
  public Board(Board boardToCopy)
  {
    this.boardGrid = new Cell[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
        this.boardGrid[i][j] = new Cell(boardToCopy.boardGrid[i][j]);
      }
    }
  }

  //Constructor when given an initial board
  public Board(int[][] givenBoard)
  {
    this.boardGrid = new Cell[9][9];
    for(int i = 0; i < 9; i++)
    {
      for(int j = 0; j < 9; j++)
      {
          if(givenBoard[i][j] != 0)
          {
            this.boardGrid[i][j] = new Cell(givenBoard[i][j], CellType.GIVEN);
          }
          else
            this.boardGrid[i][j] = new Cell();
      }
    }
  }

  public boolean fillTrivial()
  {
    boolean result = false;
    for(int i = 0; i<9; i++)
    {
      for(int j = 0; j<9; j++)
      {
        if(this.boardGrid[i][j].possibleVals.size() == 1 && !isGiven(i,j) && this.boardGrid[i][j].value == 0)
        {
          this.boardGrid[i][j].value = this.boardGrid[i][j].possibleVals.get(0);
          updateBoard();
          result = true;
        }
      }
    }
    return result;
  }

  /* public void hiddenSingles()
  {
    boolean isHiddenSingleCol = true;
    boolean isHiddenSingleRow = true;
    boolean isHiddenSingleReg = true;
    for(int i = 0; i<9; i++)
    {
      for(int j = 0; j<9; j++)
      {
        isHiddenSingleCol = true;
        isHiddenSingleRow = true;
        for(int k = 0; k<this.boardGrid[i][j].possibleVals.size(); k++)
        {
          for(int n = 0; n<9; n++)
          {
            for(int m = 0; m<this.boardGrid[n][j].possibleVals.size(); m++)
            {
              if(i != n && boardGrid[i][j].possibleVals.get(k) == boardGrid[n][j].possibleVals.get(m))
              {
                isHiddenSingleCol = false;
              }
            }
          }
          if(isHiddenSingleCol)
          {
            boardGrid[i][j].value = boardGrid[i][j].possibleVals.get(k);
          }
        }
        for(int k = 0; k<this.boardGrid[j][i].possibleVals.size(); k++)
        {
          for(int n = 0; n<9; n++)
          {
            for(int m = 0; m<this.boardGrid[j][n].possibleVals.size(); m++)
            {
              if(i != n && boardGrid[j][i].possibleVals.get(k) == boardGrid[j][n].possibleVals.get(m))
              {
                isHiddenSingleRow = false;
              }
            }
          }
          if(isHiddenSingleRow)
          {
            boardGrid[j][i].value = boardGrid[j][i].possibleVals.get(k);
          }
        }

      }
    }

    // //For rows 0, 3, and 6 (starting rows)
    // for(int x = 0; x<7; x = x+3)
    // {
    //   //For columns 0, 3, and 6 (starting columns)
    //   for(int y = 0; y<7; y = y+3)
    //   {
    //     //For each of the 3 rows in each 3x3 region [(0,1,2)|(3,4,5)|(6,7,8)] going down the board
    //     for(int n = x; n<(x+3); n++)
    //     {
    //       //For each of the 3 columns in each 3x3 region [(0,1,2)|(3,4,5)|(6,7,8)] going across the board
    //       for(int m = y; m<(y+3); m++)
    //       {
    //         for(int k = 0; k<this.boardGrid[n][m].possibleVals.size(); k++)
    //         {
    //           isHiddenSingleReg = true;
    //           //For each next row to compare to in each 3x3 region
    //           for(int i = x; i<(x+3); i++)
    //           {
    //             //For each next column to compare to in each 3x3 region
    //             for(int j = y; j<(y+3); j++)
    //             {
    //               for(int q = 0; q<this.boardGrid[i][j].possibleVals.size(); q++)
    //               {
    //                 if(i != n && m != j && boardGrid[n][m].possibleVals.get(k) == boardGrid[i][j].possibleVals.get(q))
    //                 {
    //                   isHiddenSingleReg = false;
    //                 }
    //               }
    //
    //             }
    //           }
    //           if(isHiddenSingleReg)
    //           {
    //             boardGrid[n][m].value = boardGrid[n][m].possibleVals.get(k);
    //           }
    //         }
    //       }
    //     }
    //   }
    // }
  } */




  public boolean reject()
  {
    updateBoard();
    for(int i = 0; i < 9; i++)
    {
      //For all columns
      for(int j = 0; j < 9; j++)
      {
        if(this.boardGrid[i][j].type != CellType.GIVEN)
        {
          if (this.boardGrid[i][j].possibleVals.size() == 0)
          {
            //System.out.println("Cell[" + i + ", " + j + "] has no possible values --> Solution rejected");
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean isGiven(int row, int col)
  {
    return (this.boardGrid[row][col].type == CellType.GIVEN);
  }

  public boolean isTest(int row, int col)
  {
    return (this.boardGrid[row][col].type == CellType.TEST);
  }

  public boolean isEmpty(int row, int col)
  {
    return (this.boardGrid[row][col].type == CellType.EMPTY);
  }

  public int getCellValue(int row, int col)
  {
    return this.boardGrid[row][col].value;
  }

  public void setCellValue(int val, int row, int col)
  {
    this.boardGrid[row][col].value = val;
    this.boardGrid[row][col].type = CellType.TEST;
  }

  public CellType getCellType(int row, int col)
  {
    return this.boardGrid[row][col].type;
  }

  //Update entire board
  public void updateBoard()
  {
    for(int i = 0; i<9; i++)
    {
      for(int j = 0; j<9; j++)
      {
        updateCell(i, j);
      }
    }
  }

  //Update cell
  public void updateCell(int row, int col)
  {
    resetPossibleVals(row, col);
    if(!isGiven(row,col))
    {
      for(int i = 0; i<9; i++)
      {
        int rowVal = this.boardGrid[row][i].value;
        int colVal = this.boardGrid[i][col].value;
        if(i != col && rowVal != 0)
          removePossibleVal(rowVal, row, col);
        if(i != row && colVal != 0)
          removePossibleVal(colVal, row, col);
      }
      int startRow = (row/3)*3;
      int startCol = (col/3)*3;
      for(int i = startRow; i<(startRow+3); i++)
      {
        for(int j = startCol; j<(startCol+3); j++)
        {
          int regVal = this.boardGrid[i][j].value;
          if(i != row && j != col && regVal != 0)
            removePossibleVal(regVal, row, col);
        }
      }
    }
    else
    {
      boardGrid[row][col].possibleVals = new ArrayList<>();
      boardGrid[row][col].possibleVals.add(boardGrid[row][col].value);
    }
  }

  public List<Integer> getPossibleVals(int row, int col)
  {
    // List<Integer> possibleValsCopy = new ArrayList<>();
    // for(int i = 0; i<boardGrid[row][col].possibleVals.size(); i++)
    // {
    //   possibleValsCopy.add(boardGrid[row][col].possibleVals.get(i));
    // }
    // return possibleValsCopy;
    return this.boardGrid[row][col].possibleVals;
  }

  public void clearCell(int row, int col)
  {
    this.boardGrid[row][col].value = 0;
    this.boardGrid[row][col].type = CellType.EMPTY;
  }

  public void removePossibleVal(int val, int row, int col)
  {
    boolean result = boardGrid[row][col].possibleVals.remove((Integer)val);
  }

  public boolean isBoardFilled()
  {
    for(int i = 0; i<9; i++)
    {
      for(int j = 0; j<9; j++)
      {
        if(boardGrid[i][j].value == 0)
          return false;
      }
    }
    return true;
  }

  //Print board
  public void printBoard()
  {
    if (boardGrid == null)
    {
      System.out.println("No assignment");
      return;
    }
    //System.out.println("First cell address: " + this.boardGrid[0][0]);
    for (int i = 0; i < 9; i++)
    {
      if (i == 3 || i == 6)
      {
        System.out.println("----+-----+----");
      }
      for (int j = 0; j < 9; j++) {
        if (j == 2 || j == 5)
        {
          System.out.print(boardGrid[i][j].value + " | ");
        }
        else
        {
          System.out.print(boardGrid[i][j].value);
        }
      }
      System.out.print("\n");
    }
  }

  public void resetPossibleVals(int row, int col)
  {
    this.boardGrid[row][col].possibleVals = new ArrayList<>();
    for(int i = 0; i<9; i++)
    {
      this.boardGrid[row][col].possibleVals.add(i+1);
    }
  }

  //Cell class
  private class Cell
  {
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
  }
}
