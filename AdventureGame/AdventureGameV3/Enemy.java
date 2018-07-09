import java.util.Random;

public class Enemy extends Character
{
  //Methods
  public Enemy(Type enemyType)
  {
    super(enemyType);
  }

  public Enemy(String enemyTypeString)
  {
    super(enemyTypeString);
  }

  //Enemy drops coins when defeated
  public int dropCoins()
  {
    Random rand = new Random();
    return rand.nextInt(21) + 30;
  }

  //Kill wizard when spell successfully cast
  public void wizardKO(Enemy wizard)
  {
    int HP = wizard.getHitPoints();
    wizard.decreaseHitPoints(HP);
  }

  //Get the number of goblins to battle
  public static int getNumGoblins()
  {
    Random rand = new Random();
    return rand.nextInt(4) + 2;
  }

  //Get the number of skeletons to battle
  public static int getNumSkeletons()
  {
    Random rand = new Random();
    return rand.nextInt(5) + 3;
  }

}
