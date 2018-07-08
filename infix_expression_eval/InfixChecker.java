package infix_expression_eval;

public class InfixChecker
{
  public static void main(String[] args)
  {
    String[] expressions = {"5+2", "5-2", "5+2-1", "5-1+2", "5+5+5", "5-5-5", "5+65.6-15.6",
                            "5*2", "5/2", "5*2/2", "5/2*2", "5*5*5", "5/2/2", "5*2.5/0.5",
                            "5*2+4", "5/2+4", "5+2/4", "5-2/4", "5*2*3+5", "5/2-1",
                            "5^2", "5^2^2", "5^2+4", "5^2*2", "5/2^2", "5+4/2^2"};
    double[] values = {7, 3, 6, 6, 15, -5, 55, 10, 2.5, 5, 5, 125, 1.25, 25, 14, 6.5, 5.5, 4.5, 35, 1.5, 25, 625, 29, 50, 1.25, 6};

    boolean success = true;
    for(int i = 0; i<expressions.length; i++)
    {
      String expNL = expressions[i] + "\n";
      InfixExpressionEvaluator evaluator = new InfixExpressionEvaluator(expNL);
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
        value = value*100;
        value = (double)Math.round(value);
        value = value/100;
        if(value != values[i])
        {
          success = false;
          System.out.println("Failed at index: " + i);
          System.out.println(expressions[i] + " != " + value);
        }
      }
    }

    if(success)
      System.out.println("Arithmetic checks successful!");

    boolean success2 = true;
    String[] syntaxNoErrors = {"(5+2)", "(5+2)*3", "5+(2*3)", "5*(2-3)", "5^(2+2)", "5*[(2+3)*2]", "(5)"};
    double[] values2 = {7, 21, 11, -5, 625, 50, 5};
    for(int i = 0; i < syntaxNoErrors.length; i++)
    {
      String expNL = syntaxNoErrors[i] + "\n";
      InfixExpressionEvaluator evaluator = new InfixExpressionEvaluator(expNL);
      Double value = null;
      try
      {
        value = evaluator.evaluate();
      }
      catch (ExpressionError e)
      {
        System.out.println("ExpressionError: " + e.getMessage());
      }
      if (value != values2[i])
      {
        success2 = false;
        System.out.println("Failed at index: " + i);
      }
    }

    if(success2)
      System.out.println("Syntax checks successful!");

    String[] syntaxErrors = {"4*[5+2)", "(5+2]", "5)", "()", ")", "*^5", "(5*6^7", "(5+[4*2)]"};
    for(String exp:syntaxErrors)
    {
      System.out.print(exp + " = ");
      String expNL = exp + "\n";
      InfixExpressionEvaluator evaluator = new InfixExpressionEvaluator(expNL);
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
    }
  }
}
