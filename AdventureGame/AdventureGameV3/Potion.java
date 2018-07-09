public class Potion
{
  public enum Type {MINOR_HEALING, HEALING, MINOR_STRENGTH, STRENGTH};

  //Attributes
  private String name;
  private Type type;

  //Methods

  /**
    The Potion constructor sets the type of potion and the name based on the passed enumerator
    @para potionType - Type of potion
  */

  public Potion(Type potionType)
  {
    switch (potionType)
    {
      case MINOR_HEALING:
        type = Type.MINOR_HEALING;
        name = "Minor Healing Potion";
        break;
      case HEALING:
        type = Type.HEALING;
        name = "Healing Potion";
        break;
      case MINOR_STRENGTH:
        type = Type.MINOR_STRENGTH;
        name = "Minor Strength Potion";
        break;
      case STRENGTH:
        type = Type.STRENGTH;
        name = "Strength Potion";
        break;
    }
  }

  /**
    The getName method returns the name of the potion.
    @return Potion name
  */

  public String getName()
  {
    return name; //This is okay because Strings are immutable
  }

  /**
    The drink method simulates the player drinking a potion.
    @param player - Player reference variable
  */

  public void drink(Player player)
  {
    switch(type)
    {
      case MINOR_HEALING:
        player.increaseHitPoints(5); //Increase player hit points
        System.out.printf("\nYou drank a Minor Healing potion! Your HP is now: " +
                          "%d + %d = %d\n\n", player.getHitPoints()-5, 5, player.getHitPoints());
        break;
      case HEALING:
        player.increaseHitPoints(10); //Increase player hit points
        System.out.printf("\nYou drank a Healing potion! Your HP is now: %d + %d " +
                          "= %d\n\n", player.getHitPoints()-10, 10, player.getHitPoints());
        break;
      case MINOR_STRENGTH:
        player.increaseStrength(2); //Increase strength
        System.out.printf("\nYou drank a Minor Strength Potion! Your strength is" +
                          "now: %d + %d = %d\n\n", player.getStrength()-2, 2, player.getStrength());
        break;
      case STRENGTH:
        player.increaseStrength(5); //Increase strength
        System.out.printf("\nYou drank a Strength Potion! Your strength is now:" +
                          " %d + %d = %d\n\n", player.getStrength()-5, 5, player.getStrength());
        break;
    }
  }

  /**
    The copy method creates a copy of the potion object.
    @return Potion object with same field values as copied object
  */

  public Potion copy()
  {
    Potion potionCopy = new Potion(type);
    return potionCopy;
  }

}
