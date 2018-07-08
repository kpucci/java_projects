# CS/COE 445 Project 2

## Motivation:
In lecture, we discussed an stack algorithm for converting an infix expression to a postfix expression, and another for evaluating postfix expressions. We briefly described how one might pipeline these algorithms to evaluate an infix expression in a single pass using two stacks, without generating the full postfix expression in between. In this assignment, you will be implementing this combined algorithm. Your completed program will evaluate an infix arithmetic expression using two stacks.

You are provided with the skeleton of InfixExpressionEvaluator. This class accepts input from an InputStream and parses it into tokens. When it detects an invalid token, it throws an ExpressionError to end execution. To facilitate ease of use, this class also contains a main method. This method instantiates an object of type InfixExpressionEvaluator to read from System.in, then evaluates whatever expression is typed. It also catches any potential ExpressionErrors and prints the reason for the error.

InfixExpressionEvaluator uses composition to store the operator and operand stacks, and calls several private helper methods to manipulate these stacks when processing various tokens. You will need to complete these helper methods and add error checking to ensure the expression is valid.

## Tasks:
### Implement helper methods, 70 points
As tokens are parsed, helper methods are called to process them. In the included code, these helper methods do not do anything. You must implement the following methods to process the various types of tokens.

* processOperand(double): Each time the evaluator encounters an operand, it passes it (as a double) to this
  method, which should process it by manipulating the operand and/or operator stack according to the infix-to-postfix and postfix-evaluation algorithms.

* processOperator(char): Each time the evaluator encounters an operator, it passes it (as a char) to this method,
  which should process it by manipulating the operand and/or operator stack according to the infix-to-postfix and postfix-evaluation algorithms.
  Each of the following operators must be supported. Follow standard operator precedence. You can assume that - is always the binary subtraction operator.

* processOpenBracket(char): Each time the evaluator encounters an open bracket, it passes it (as a char) to this
  method, which should process it by manipulating the operand and/or operator stack according to the infix-to-postfix and postfix-evaluation algorithms.
  You must support both round brackets () and square brackets []. These brackets can be used interchangeably, but must be nested properly—a ( cannot be closed with a ], and vice-versa.

* processCloseBracket(char): Each time the evaluator encounters a close bracket, it passes it (as a char) to
  this method, which should process it by manipulating the operand and/or operator stack according to the infix-to-postfix and postfix-evaluation algorithms.

* processRemainingOperators(): When the evaluator encounters the end of the expression, it calls this method to
  handle the remaining operators on the operator stack

### Error checking, 30 points
This task requires that you modify your program to account for errors in the input expression. The provided code throws ExpressionError when encountering an unknown token (for instance, &. You must modify your program to throw this exception (with an appropriate message) whenever the expression is invalid.

This requires careful consideration of all the possible syntax errors. What if an operand follows another operand? An operator following an open bracket? What about brackets that do not nest properly? All such syntax errors must be handled using ExpressionError.
