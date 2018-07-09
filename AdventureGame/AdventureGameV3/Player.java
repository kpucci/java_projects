import java.util.Random;

public class Player extends Character
{
  //Attributes
  private int coins;
  private Potion[] inventory;

  //Methods
  public Player(Type playerType)
  {
    super(playerType);
    coins = 0;
    inventory = new Potion[5];
  }

  public Player(String playerTypeString)
  {
    super(playerTypeString);
    coins = 0;
    inventory = new Potion[5];
  }

  public void increaseStrength(int strengthIncrease)
  {
    int strength = this.getStrength();
    strength += strengthIncrease;
    this.setStrength(strength);
  }

  public int getCoins()
  {
    return coins;
  }

  public void increaseCoins(int coinIncrease)
  {
    coins += coinIncrease;
  }

  public void decreaseCoins(int coinDecrease)
  {
    coins -= coinDecrease;
  }

  public void addToInventory(Potion potion)
  {
    if (this.getNumOpenSlots() > 0) //Run only if there's an open slot
    {
      for(int i = 0; i < inventory.length; i++) //Loop through inventory
      {
        if(inventory[i] == null) //Fill first open inventory slot with potion's reference variable
        {
          inventory[i] = potion;
          break; //End for loop so that it doesn't fill every open slot
        }
      }
    }
  }

  public void removeFromInventory(int index)
  {
    inventory[index-1] = null; //Set inventory at specified index to null
  }

  public boolean isSlotEmpty(int index)
  {
    return (inventory[index-1] == null);
  }

  public Potion getInventoryItem(int index)
  {
    return inventory[index-1].copy();
  }

  public void displayInventory()
  {
    for(int i = 1; i <= inventory.length; i++)
    {
      if(!this.isSlotEmpty(i))
        System.out.println("[" + i + "] " + inventory[i-1].getName());
      else
        System.out.println("[" + i + "] ");
    }
    System.out.print("\n");
  }

  public int getNumOpenSlots()
  {
    int numOpenSlots = 0; //Variable to keep track of open slots
    for(Potion val:inventory)
    {
      if(val == null)
      {
        numOpenSlots++; //If object at current index is null, increase count by 1.
      }
    }

    return numOpenSlots; //return number of open slots
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

  public void battleWizard(Enemy wizard)
  {
    int userInput, guess;
    Random rand = new Random();

    System.out.println("You have now reached The Castle! Time to battle the Evil Wizard\n");
    System.out.printf("***%s vs The Evil Wizard\n", this.getName());
    while(!wizard.isDefeated() && !this.isDefeated())
    {
      System.out.print("Choose your action:\n" +
                       "1. Attack\n" +
                       "2. Attempt Spell Cast\n\n" +
                       "What would you like to do? ");
      userInput = AdventureGameV3.inputValidation(1,2);

      switch(userInput)
      {
        case 1:
          this.attack(wizard);
          if(!wizard.isDefeated())
            wizard.attack(this);
          break;
        case 2:
          System.out.print("Enter your guess (between 1-5): ");
          guess = AdventureGameV3.inputValidation(1,2,3,4,5);

          if(guess == (rand.nextInt(5)+1))
          {
            System.out.printf("Correct!\n\n %s's spell is cast successfully! The Wizard's HP is now 0!\n\n", this.getName());

            //Need to set Evil Wizard's HP to 0 or below to terminate loop
						wizard.wizardKO(wizard);
          }
          else
          {
            System.out.println("\nSpell failed!\n");
						wizard.attack(this);
          }
          break;
      }
    }
  }

}
