import java.util.Random;

public class Player extends Character
{
  //Attributes
  private int coins;

  //Methods
  public Player(Type playerType)
  {
    super(playerType);
  }

  public Player(String playerTypeString)
  {
    super(playerTypeString);
  }

  public void increaseStrength(int strengthIncrease)
  {
    int strength = this.getStrength();
    strength += strengthIncrease;
    this.setStrength(strength);
  }

  public void battleMinion(Enemy minion)
  {
    //Continue battle as long as both the player and minion are still alive
    while(!minion.isDefeated() && !this.isDefeated())
    {
      //Player attacks first
      this.attack(minion);

      //If minion wasn't defeated, it attacks player
      if(!minion.isDefeated())
        minion.attack(this);
    }
  }

}
