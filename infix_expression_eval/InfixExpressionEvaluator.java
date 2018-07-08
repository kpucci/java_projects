package infix_expression_eval;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.Scanner;

/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 */
public class InfixExpressionEvaluator
{
    enum TokenType {OPERATOR, OPERAND, OPEN_BRACKET, CLOSE_BRACKET, EOL}

    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operatorStack;
    StackInterface<Double> operandStack;

    TokenType currTokenType, prevTokenType;


    public InfixExpressionEvaluator(String input)
    {
      // Initialize the tokenizer to read from the given InputStream
      tokenizer = new StreamTokenizer(new StringReader(input));

      // StreamTokenizer likes to consider - and / to have special meaning.
      // Tell it that these are regular characters, so that they can be parsed
      // as operators
      tokenizer.ordinaryChar('-');
      tokenizer.ordinaryChar('/');

      // Allow the tokenizer to recognize end-of-line, which marks the end of
      // the expression
      tokenizer.eolIsSignificant(true);

      // Initialize the stacks
      operatorStack = new ArrayStack<Character>();
      operandStack = new ArrayStack<Double>();

      currTokenType = null;
      prevTokenType = null;
    }

    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input)
    {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                        new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();

        currTokenType = null;
        prevTokenType = null;
    }

    /**
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws ExpressionError
    {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try
        {
            tokenizer.nextToken();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL)
        {
            prevTokenType = currTokenType;
            // Consider possible token types
            switch (tokenizer.ttype)
            {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                    currTokenType = TokenType.OPERAND;
                    processOperand((double)tokenizer.nval);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                    // If the token is any of the above characters, process it
                    // as an operator
                    currTokenType = TokenType.OPERATOR;
                    processOperator((char)tokenizer.ttype);
                    break;
                case '(':
                case '[':
                case '{':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    currTokenType = TokenType.OPEN_BRACKET;
                    processOpenBracket((char)tokenizer.ttype);
                    break;
                case ')':
                case ']':
                case '}':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    currTokenType = TokenType.CLOSE_BRACKET;
                    processCloseBracket((char)tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new ExpressionError("Unrecognized token: " +
                                    tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new ExpressionError("Unrecognized token: " +
                                    String.valueOf((char)tokenizer.ttype));
            }
            // Read the next token, again converting any potential IO exception
            try
            {
                tokenizer.nextToken();
            }
            catch(IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        prevTokenType = currTokenType;
        currTokenType = TokenType.EOL;
        // Almost done now, but we may have to process remaining operators in
        // the operators stack
        processRemainingOperators();

        // Return the result of the evaluation
        return operandStack.pop();
    }

    /**
     * This method is called when the evaluator encounters an operand. It
     * manipulates operatorStack and/or operandStack to process the operand
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operand the operand token that was encountered
     */
    void processOperand(double operand)
    {
      checkSyntax();
      operandStack.push(operand);
    }

    /**
     * This method is called when the evaluator encounters an operator. It
     * manipulates operatorStack and/or operandStack to process the operator
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param operator the operator token that was encountered
     */
    void processOperator(char operator)
    {
      checkSyntax();
      if(operatorStack.isEmpty())
        operatorStack.push(operator);
      else
      {
        char topOperator = operatorStack.peek();
        while(!operatorStack.isEmpty() && getType(topOperator) != TokenType.OPEN_BRACKET && !checkPrecedence(operator, topOperator))
        {
          char op = operatorStack.pop();
          double b = operandStack.pop();
          double a = operandStack.pop();
          operandStack.push(operate(a,b,op));
          if(!operatorStack.isEmpty())
            topOperator = operatorStack.peek();
        }
        operatorStack.push(operator);
      }
    }

    /**
     * This method is called when the evaluator encounters an open bracket. It
     * manipulates operatorStack and/or operandStack to process the open bracket
     * according to the Infix-to-Postfix and Postfix-evaluation algorithms.
     * @param openBracket the open bracket token that was encountered
     */
    void processOpenBracket(char openBracket)
    {
      checkSyntax();
      //The following if-statement handles cases such as "5(4+6)" and
      //"(5+4)(6-9)" by inserting an implicit * before the open bracket to make
      //it "5*(4+6)" and "(5+4)*(6-9)"
      if(prevTokenType == TokenType.OPERAND || prevTokenType == TokenType.CLOSE_BRACKET)
        operatorStack.push('*');
      operatorStack.push(openBracket);
    }

    /**
     * This method is called when the evaluator encounters a close bracket. It
     * manipulates operatorStack and/or operandStack to process the close
     * bracket according to the Infix-to-Postfix and Postfix-evaluation
     * algorithms.
     * @param closeBracket the close bracket token that was encountered
     */
    void processCloseBracket(char closeBracket)
    {
      checkSyntax();
      while(!operatorStack.isEmpty() && !isPaired(operatorStack.peek(),closeBracket))
      {
        double a = 0;
        double b = 0;
        if(!operandStack.isEmpty())
          b = operandStack.pop();
        if(!operandStack.isEmpty())
          a = operandStack.pop();
        char operator = operatorStack.pop();
        if(getType(operator) == TokenType.OPEN_BRACKET)
          throw new ExpressionError("Mismatched brackets");
        else
          operandStack.push(operate(a,b,operator));
      }
      if(!operatorStack.isEmpty())
      {
        operatorStack.pop();
      }
      else
        throw new ExpressionError("Mismatched brackets");

    }

    /**
     * This method is called when the evaluator encounters the end of an
     * expression. It manipulates operatorStack and/or operandStack to process
     * the operators that remain on the stack, according to the Infix-to-Postfix
     * and Postfix-evaluation algorithms.
     */
    void processRemainingOperators()
    {
      checkSyntax();
      while(!operatorStack.isEmpty())
      {
        if(getType(operatorStack.peek()) == TokenType.OPEN_BRACKET)
          throw new ExpressionError("Mismatched brackets");
        else
        {
          double b = operandStack.pop();
          double a = operandStack.pop();
          char operator = operatorStack.pop();
          operandStack.push(operate(a,b,operator));
        }
      }
    }

    //Check operator precedence
    private static boolean checkPrecedence(char operator1, char operator2)
    {
      if(operator1 == '^')
        return true;
      else if(operator1 == operator2 || operator1 == '+' || operator1 == '-')
        return false;
      else if(operator1 == '*')
      {
        if(operator2 == '/' || operator2 == '^')
          return false;
        else
          return true;
      }
      else if(operator1 == '/')
      {
        if(operator2 == '*' || operator2 == '^')
          return false;
        else
          return true;
      }
      else
      {
        return false;
      }
    }

    //Check if brackets match
    private static boolean isPaired(char openBracket, char closeBracket)
    {
      boolean paired = false;
      switch(openBracket)
      {
        case '(':
          if(closeBracket == ')')
            paired = true;
          break;
        case '[':
          if(closeBracket == ']')
            paired = true;
          break;
        case '{':
          if(closeBracket == '}')
            paired = true;
          break;
        default:
          break;
      }
      return paired;
    }

    //Perform operation
    private static double operate(double a, double b, char operator)
    {
      if(operator == '+')
        return (a+b);
      else if(operator == '-')
        return (a-b);
      else if(operator == '*')
        return (a*b);
      else if(operator == '/')
        return (a/b);
      else if(operator == '^')
        return (Math.pow(a,b));
      else
        return 0;
    }

    //Syntax error checks
    private void checkSyntax()
    {
      String errMsg = null;

      if(currTokenType == TokenType.OPERATOR && !(prevTokenType == TokenType.OPERAND
        || prevTokenType == TokenType.OPEN_BRACKET || prevTokenType == TokenType.CLOSE_BRACKET))
        errMsg = "Misplaced operator";
      else if(currTokenType == TokenType.OPERAND && prevTokenType == TokenType.OPERAND)
        errMsg = "Misplaced operand";
      else if((currTokenType == TokenType.OPERATOR || currTokenType == TokenType.CLOSE_BRACKET) && prevTokenType == null)
        errMsg = "Incomplete expression";
      // 2. Can't have empty brackets
      else if(currTokenType == TokenType.CLOSE_BRACKET && (prevTokenType != TokenType.OPERAND && prevTokenType != TokenType.CLOSE_BRACKET))
        errMsg = "Misplaced or empty bracket";
      // 3. Can't have operand directly after a close bracket
      else if(currTokenType == TokenType.OPERAND && prevTokenType == TokenType.CLOSE_BRACKET)
        errMsg = "Misplaced operand";
      // 6. Can't be just a single operator or bracket
      else if(currTokenType == TokenType.EOL && (prevTokenType == TokenType.OPEN_BRACKET || prevTokenType == TokenType.OPERATOR))
        errMsg = "Misplaced operator or bracket";

      if(errMsg != null)
        throw new ExpressionError(errMsg);
    }

    //Get type of token
    private static TokenType getType(char token)
    {
      switch(token)
      {
        case '+': case '-': case '*': case '/': case '^':
          return TokenType.OPERATOR;
        case '(': case '[': case '{':
          return TokenType.OPEN_BRACKET;
        case ')': case ']': case '}':
          return TokenType.CLOSE_BRACKET;
        default:
          return TokenType.OPERAND;
      }
    }

    //For debugging purposes
    private static void pressEnter()
  	{
  		Scanner keyboard = new Scanner(System.in);
  		keyboard.nextLine();
  	}


    /**
     * Creates an InfixExpressionEvaluator object to read from System.in, then
     * evaluates its input and prints the result.
     * @param args not used
     */
    public static void main(String[] args)
    {
      System.out.println("Infix expression:");
      InfixExpressionEvaluator evaluator =
                      new InfixExpressionEvaluator(System.in);
      Double value = null;
      try
      {
          value = evaluator.evaluate();
      }
      catch (ExpressionError e)
      {
          System.out.println("ExpressionError: " + e.getMessage());
      }
      if (value != null)
      {
          System.out.println(value);
      }
      else
      {
          System.out.println("Evaluator returned null");
      }
    }

}
