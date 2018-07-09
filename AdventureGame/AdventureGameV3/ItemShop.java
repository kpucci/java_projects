import java.util.Scanner;

public class ItemShop
{
  //Private constants
  private static final int SHORT_SWORD_COST = 90;
  private static final int LONG_SWORD_COST = 120;
  private static final int MACE_COST = 80;
  private static final int STRENGTH_COST = 40;
  private static final int MINOR_STRENGTH_COST = 20;
  private static final int HEALING_COST = 10;
  private static final int MINOR_HEALING_COST = 5;

  //Methods
  public static void visitItemShop(Player player)
  {
    //Variables
    int itemNum, quantity, weaponMin, weaponMax, buyAgain;
    int totalCost = 0, discount = 0, finalCost = 0;
    String itemName = null, outputString = null;
    boolean buy = false;
    Potion[] potions;

    Scanner keyboard = new Scanner(System.in);

    do //Do while the user still wants to buy items
    {
      //Dialogue
      System.out.println("\nWelcome to The Item Shop!\n");
      System.out.printf("You currently have %d gold.\n\n", player.getCoins());

      System.out.println("Here's what we have for sale (all prices are in units of gold):\n");
      System.out.printf("1. %-25s%-3d\n", "Long Sword", LONG_SWORD_COST);
      System.out.printf("2. %-25s%-3d\n", "Short Sword", SHORT_SWORD_COST);
      System.out.printf("3. %-25s%-3d\n", "Mace", MACE_COST);
      System.out.printf("4. %-25s%-3d\n", "Minor Healing Potion", MINOR_HEALING_COST);
      System.out.printf("5. %-25s%-3d\n", "Healing Potion", HEALING_COST);
      System.out.printf("6. %-25s%-3d\n", "Minor Strength Potion", MINOR_STRENGTH_COST);
      System.out.printf("7. %-25s%-3d\n", "Strength Potion", STRENGTH_COST);

      System.out.print("\nPlease enter the item number: ");
      itemNum = AdventureGameV3.inputValidation(1,2,3,4,5,6,7);

      System.out.print("Please enter the quantity: ");
      quantity = keyboard.nextInt();

      //Switch-case block to handle different item choices and costs
      switch (itemNum)
      {
        case 1:
          itemName = "Long Sword";
          totalCost = LONG_SWORD_COST;
          weaponMin = Weapon.LONG_SWORD_MIN;
          weaponMax = Weapon.LONG_SWORD_MAX;

          Weapon longSword = new Weapon(itemName,weaponMin,weaponMax);
          player.setWeapon(longSword);

          if (quantity > 1)
          {
            System.out.println("\nYou can only buy one weapon at a time! Quantity was changed to 1.");
            quantity = 1;
          }

          outputString = "Your weapon damage is now: " + longSword.getMinDamage() + " - " + longSword.getMaxDamage();

          break;
        case 2:
          itemName = "Short Sword";
          totalCost = SHORT_SWORD_COST;
          weaponMin = Weapon.SHORT_SWORD_MIN;
          weaponMax = Weapon.SHORT_SWORD_MAX;

          Weapon shortSword = new Weapon(itemName,weaponMin,weaponMax);
          player.setWeapon(shortSword);

          if (quantity > 1)
          {
            System.out.println("\nYou can only buy one weapon at a time! Quantity was changed to 1.");
            quantity = 1;
          }

          outputString = "Your weapon damage is now: " + shortSword.getMinDamage() + " - " + shortSword.getMaxDamage();

          break;
        case 3:
          itemName = "Mace";
          totalCost = MACE_COST;
          weaponMin = Weapon.MACE_MIN;
          weaponMax = Weapon.MACE_MAX;

          Weapon mace = new Weapon(itemName,weaponMin,weaponMax);
          player.setWeapon(mace);

          if (quantity > 1)
          {
            System.out.println("\nYou can only buy one weapon at a time! Quantity was changed to 1.");
            quantity = 1;
          }

          outputString = "Your weapon damage is now: " + mace.getMinDamage() + " - " + mace.getMaxDamage();

          break;
        case 4:
          itemName = "Minor Healing Potion";

          if(player.getNumOpenSlots() == 0)
          {
            System.out.println("\nYou don't have any open slots!");
            quantity = 0;
            break;
          }
          else if(quantity > player.getNumOpenSlots())
          {
            quantity = player.getNumOpenSlots();
            System.out.printf("\nYou only have %d open slots. Quantity reduced to %d\n", quantity, quantity);
          }

          totalCost = MINOR_HEALING_COST*quantity;
          potions = new Potion[quantity];

          for(int i = 0; i < quantity; i++)
          {
            potions[i] = new Potion(Potion.Type.MINOR_HEALING);
            player.addToInventory(potions[i]);
          }

          outputString = quantity > 1? "Your potions have been added to your inventory":"Your potion has been added to your inventory";


          break;
        case 5:
          itemName = "Healing Potion";

          if(player.getNumOpenSlots() == 0)
          {
            System.out.println("\nYou don't have any open slots!");
            quantity = 0;
            break;
          }
          else if(quantity > player.getNumOpenSlots())
          {
            quantity = player.getNumOpenSlots();
            System.out.printf("\nYou only have %d open slots. Quantity reduced to %d\n", quantity, quantity);
          }

          totalCost = HEALING_COST*quantity;
          potions = new Potion[quantity];

          for(int i = 0; i < quantity; i++)
          {
            potions[i] = new Potion(Potion.Type.HEALING);
            player.addToInventory(potions[i]);
          }

          outputString = quantity > 1?"Your potions have been added to your inventory":"Your potion has been added to your inventory";


          break;
        case 6:
          itemName = "Minor Strength Potion";

          if(player.getNumOpenSlots() == 0)
          {
            System.out.println("\nYou don't have any open slots!");
            quantity = 0;
            break;
          }
          else if(quantity > player.getNumOpenSlots())
          {
            quantity = player.getNumOpenSlots();
            System.out.printf("\nYou only have %d open slots. Quantity reduced to %d\n", quantity, quantity);
          }

          totalCost = MINOR_STRENGTH_COST*quantity;
          potions = new Potion[quantity];

          for(int i = 0; i < quantity; i++)
          {
            potions[i] = new Potion(Potion.Type.MINOR_STRENGTH);
            player.addToInventory(potions[i]);
          }

          outputString = quantity > 1?"Your potions have been added to your inventory":"Your potion has been added to your inventory";

          break;
        case 7:
          itemName = "Strength Potion";

          if(player.getNumOpenSlots() == 0)
          {
            System.out.println("\nYou don't have any open slots!");
            quantity = 0;
            break;
          }
          else if(quantity > player.getNumOpenSlots())
          {
            quantity = player.getNumOpenSlots();
            System.out.printf("\nYou only have %d open slots. Quantity reduced to %d\n", quantity, quantity);
          }

          totalCost = STRENGTH_COST*quantity;
          potions = new Potion[quantity];

          for(int i = 0; i < quantity; i++)
          {
            potions[i] = new Potion(Potion.Type.STRENGTH);
            player.addToInventory(potions[i]);
          }

          outputString = quantity > 1?"Your potions have been added to your inventory":"Your potion has been added to your inventory";

          break;
      }

      if(quantity > 0)
      {
        //10% discount for 3 or more items
        if (quantity >= 3)
        {
          discount = totalCost/10;
        }

        //Calculate and display final cost of items with discount included
        finalCost = totalCost - discount;

        System.out.printf("\nTotal Cost: %d\n" +
                          "Discount: %d\n" +
                          "Final Cost: %d\n\n", totalCost, discount, finalCost);

        if (finalCost <= player.getCoins())
        {
          player.decreaseCoins(finalCost);
          System.out.println("The transaction is successful!\nYour remaining funds: " + player.getCoins());

          System.out.println("Thank you, your transaction is complete!");
          System.out.printf("\nYou purchased: %d %s\n", quantity, itemName);
          System.out.println(outputString);

        }
        //Otherwise, tell the user they need more money
        else
        {
          System.out.println("You have insufficient funds! Please come back" +
                             " with more gold!");
        }
      }

      //Ask user if they would like to buy another item
      System.out.print("\nWould you like to purchase another item? (1 = Yes, 0 = No): ");
      buyAgain = AdventureGameV3.inputValidation(0,1);

      switch(buyAgain)
      {
        //Input of 1 = Yes --> Send user back to beginning of loop
        case 1:
          buy = true;
          break;

        //Input of 0 = No
        case 0:
          buy = false;
          System.out.println("Goodbye! Please stop by again!\n");
          break;
      }
    }while(buy);
  }

}
