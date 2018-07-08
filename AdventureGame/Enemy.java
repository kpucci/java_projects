
//Only need this class because Character is abstract
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

}
