/*
	Katie Vaughan
	CS401
	Fall 2016
	Assignment 3
*/

import java.util.Random;
import java.util.Scanner;
import java.io.*;

public class AdventureGameV2
{
	public static void main(String[] args) throws IOException
	{
		//Variables
		int charNum, pathNum, playAgain;
		int[] enemyAtts, gameOutput;
		String charName = "", pathArray[], enemyName = "", pathName = "";
		boolean valid = true, continuePlay = false;
		
		Scanner keyboard = new Scanner(System.in);
		
		//Outermost do-while loop runs through the game once and then verifies if the user wants to play again
		do	
		{
			//Beginning dialogue and character selection list: 1 - Rogue, 2 - Paladin, 3 - Jackie Chan
			System.out.println("\nAdventure Game - Start!");
			
			printResults();
			
			//Get character selection from user
			charNum = getCharacter();
			
			//Get character name based on user selection
			charName = characterName(charNum);
			
			//Get path selection from user
			pathNum = getPath();	
			
			//Gets path and enemy name based on user selection
			pathArray = pathName(pathNum);
			
			pathName = pathArray[0];
			
			//Assigns enemy name to String variable
			enemyName = pathArray[1];
			
			enemyAtts = enemyAttributes(enemyName);
			
			System.out.println("\nYou chose: The " + pathName + "\n");
			System.out.println("Once you enter The " + pathArray[0] + ", you encounter " + enemyAtts[4] + " " + pathArray[1] + "s! Time for battle!\n");
		
			//Main battle sequence method
			
			gameOutput = fightMinion(charName, enemyName, enemyAtts);
			saveResults(charName, gameOutput[2], gameOutput[0], pathName, gameOutput[1]);
			
			//Play again?
			continuePlay = playAgain();
		
		}while(continuePlay);
		
	}

//--Methods----------------------------------------------------------------------------------------------	
	
	/**
		The inputValidation method determines if the user input matches one of the available choices and warns the user if invalid selection was made.
		@param choices - Variable-length integer array of allowable choices
		@return A validated user selection
	*/
	
	public static int inputValidation(int... choices)
	{
		boolean valid = false;
		int choice;
		Scanner keyboard = new Scanner(System.in);
		
		do
		{
			choice = keyboard.nextInt();
			for(int val : choices)
			{
				if (choice == val)
				{
					valid = true;
				}
			}
			if (!valid)
			{
				System.out.print("\nThat was an invalid entry, please try again: ");
			}
		}while(!valid);
		
		return choice;			
	}
	
	/**
		The characterName method chooses the correct character name based on user input.
		@param charNum - User selection
		@return A String of the character's name
	*/
	
	public static String characterName(int charNum)
	{
		String name = "";
		
		//Store correct character name in first array element based on user selection
		switch (charNum)
		{
			case 1:
				name = "Rogue";
				break;
			case 2:
				name = "Paladin";
				break;
			case 3:
				name = "Jackie Chan";
				break;
		}
		
		System.out.println("\nYou chose: " + name + "\n");
		return name;	
	}
	
	/**
		The charAttributes method stores the character's HP, strength, and weapon damage min and max values in an array.
		@param charName - Character name
		@return An integer array with the following values: [0] - HP, [1] - Strength, [2] - Weapon Minimum, [3] - Weapon Maximum, [4] - Weapon Number, [5] - Coins
	*/
	
	public static int[] charAttributes(String charName)
	{
		//Rogue attributes
		final int ROGUE_HP = 55;
		final int ROGUE_STRENGTH = 8;
		final int ROGUE_WMIN = 1;
		final int ROGUE_WMAX = 4;
		
		//Paladin attributes
		final int PALADIN_HP = 35;
		final int PALADIN_STRENGTH = 14;
		final int PALADIN_WMIN = 3;
		final int PALADIN_WMAX = 7;
		
		//Jackie Chan attributes
		final int CHAN_HP = 45;
		final int CHAN_STRENGTH = 10;
		final int CHAN_WMIN = 2;
		final int CHAN_WMAX = 6;
		
		int[] charAtts = new int[6];
		
		//Store the appropriate values in output array depending on character name
		switch (charName)
		{
			case "Rogue":
				charAtts[0] = ROGUE_HP;
				charAtts[1] = ROGUE_STRENGTH;
				charAtts[2] = ROGUE_WMIN;
				charAtts[3] = ROGUE_WMAX;
				charAtts[4] = 1; //Weapon 1 = Short Sword
				break;
			case "Paladin":
				charAtts[0] = PALADIN_HP;
				charAtts[1] = PALADIN_STRENGTH;
				charAtts[2] = PALADIN_WMIN;
				charAtts[3] = PALADIN_WMAX;
				charAtts[4] = 2; //Weapon 2 = Long Sword
				break;
			case "Jackie Chan":
				charAtts[0] = CHAN_HP;
				charAtts[1] = CHAN_STRENGTH;
				charAtts[2] = CHAN_WMIN;
				charAtts[3] = CHAN_WMAX;
				charAtts[4] = 3; //Weapon 3 = Jump Kick
				break;
		}
		
		charAtts[5] = 0;

		return charAtts;
	}
	
	/**
		The enemyAttributes method stores the enemy's HP, strength, and weapon damage min and max values in an array.
		@param enemy - Enemy name
		@return An integer array with the following values: [0] - HP, [1] - Strength, [2] - Weapon Minimum, [3] - Weapon Maximum, [4] - Number of enemies
	*/

	public static int[] enemyAttributes(String enemy)
	{
		Random rand = new Random();
		
		//Goblin attributes
		final int GOBLIN_HP = 25;
		final int GOBLIN_STRENGTH = 4;
		final int GOBLIN_WMIN = 2;
		final int GOBLIN_WMAX = 6;
		int goblinNum = rand.nextInt(4) + 2;
		
		//Skeleton attributes
		final int SKELETON_HP = 25;
		final int SKELETON_STRENGTH = 3;
		final int SKELETON_WMIN = 1;
		final int SKELETON_WMAX = 4;
		int skeletonNum =rand.nextInt(5) +3;
		
		//Wizard attributes
		final int WIZARD_HP = 40;
		final int WIZARD_STRENGTH = 8;
		final int WIZARD_WMIN = 4;
		final int WIZARD_WMAX = 10;
		
		int[] enemyAtts = new int[5];
		
		//Store the appropriate values in output array depending on enemy name
		switch (enemy)
		{
			case "Goblin":
				enemyAtts[0] = GOBLIN_HP;
				enemyAtts[1] = GOBLIN_STRENGTH;
				enemyAtts[2] = GOBLIN_WMIN;
				enemyAtts[3] = GOBLIN_WMAX;
				enemyAtts[4] = goblinNum;
				break;
			case "Skeleton":
				enemyAtts[0] = SKELETON_HP;
				enemyAtts[1] = SKELETON_STRENGTH;
				enemyAtts[2] = SKELETON_WMIN;
				enemyAtts[3] = SKELETON_WMAX;
				enemyAtts[4] = skeletonNum;
				break;	
			case "Wizard":
				enemyAtts[0] = WIZARD_HP;
				enemyAtts[1] = WIZARD_STRENGTH;
				enemyAtts[2] = WIZARD_WMIN;
				enemyAtts[3] = WIZARD_WMAX;
				enemyAtts[4] = 1;
				break;
		}	
		return enemyAtts;
	}
	
	/**
		The pathName method selects the correct path choice depending on user input.
		@param pathSelection - User input
		@return A String array with name of path and name of enemy
	*/
	
	public static String[] pathName(int pathSelection)
	{
		String path[] = new String[2];
		
		//Path Options: 1 - The Forest, 2 - The Graveyard
		switch (pathSelection)
		{
			//The Forest --> Enemy = Goblin
			case 1:
				path[0] = "The Forest";
				path[1] = "Goblin";
				break;
			
			//The Graveyard --> Enemy = Skeleton
			case 2: 
				path[0] = "The Graveyard";
				path[1] = "Skeleton";
				break;			
		}
		return path;	
	}
	
	/**
		The fightMinion method simulates an attack sequence between the selected character and the enemies based on path selection.
		@param charName - Character name
		@param enemyName - Enemy name
		@param enemyStats - Enemy stats
	*/
	
	public static int[] fightMinion(String charName, String enemyName, int[] enemyStats)
	{
		//Output variable
		int[] fightResults = new int[3];
		int[] wizardResults;
		
		//Character battle variables
		int charHP = 0, charStrength, charWeaponMin, charWeaponMax, charDamage, charATK, charCoins, coinNum = 0;
		
		//Enemy battle variables
		int enemyHP, enemyStrength, enemyWeaponMin, enemyWeaponMax, battleNum, enemyDamage, enemyATK;
		
		int[] charStats = new int[6];
		int[] itemArray;
		
		//The win variable determines if the battle sequence should continue to the next enemy or not
		boolean win = true;
		
		Random rand = new Random();
		
		//See charAttributes() and enemyAttributes() methods for more information
		charStats = charAttributes(charName);
		
		//Character battle values from charAttributes() method output
		charHP = charStats[0];
		charStrength = charStats[1];
		charWeaponMin = charStats[2];
		charWeaponMax = charStats[3];
		charCoins = charStats[5];
		
		//Enemy battle values from enemyAttributes() method output
		enemyHP = enemyStats[0];
		enemyStrength = enemyStats[1];
		enemyWeaponMin = enemyStats[2];
		enemyWeaponMax = enemyStats[3];
		
		//Number of enemies to battle
		battleNum = enemyStats[4];
		
		//Battle sequence for each enemy
		for(int x = 1; x <= battleNum; x++)
		{
			if(win)
			{
				System.out.println("***" + charName + " vs " + enemyName + " " + x + "***");
				enemyHP = enemyStats[0];
				//While loop continues as long as both the character and enemy are still alive
				while(charHP > 0 && enemyHP > 0)
				{		
				//--Character attacks first--
					
					//Damage factor = random value between weapon min and weapon max
					charDamage = rand.nextInt(charWeaponMax-charWeaponMin+1)+charWeaponMin;
					enemyDamage = rand.nextInt(enemyWeaponMax-enemyWeaponMin+1)+enemyWeaponMin;
					
					//Attack Value (ATK) = strength + damage
					charATK = charStrength + charDamage;
					enemyATK = enemyStrength + enemyDamage;
					
					System.out.println(charName + " attacks with ATK = " + charStrength + " + " + charDamage + " = " + charATK);
					System.out.print(enemyName + " HP is now " + enemyHP + " - " + charATK + " = " );
					//Enemy's HP is decreased by an amount equal to character's attack value for this round
					enemyHP -= charATK;
					System.out.println(enemyHP + "\n");
					
					//If the enemy's HP drops to 0 or below, character has defeated enemy
					if(enemyHP <= 0)
					{
						System.out.println(charName + " defeated " + enemyName + " " + x + "!\n");
						coinNum = rand.nextInt(21) + 30;
						System.out.println(charName + " gains " + coinNum + " gold coins!");
						pressEnter();
					}
					
					//If the enemy is still alive, it attacks
					else
					{
						System.out.println(enemyName + " attacks with ATK = " + enemyStrength + " + " + enemyDamage + " = " + enemyATK);
						System.out.print(charName + " HP is now " + charHP + " - " + enemyATK + " = " );
						//Character's HP is decreased by amount equal to enemy's attack value for this round
						charHP -= enemyATK;
						System.out.println(charHP + "\n");
						
						//If the character's HP drops to 0 or below, game over
						if (charHP <= 0)
						{
							win = false;
							System.out.println("--" + charName + " was defeated in battle!--\n\nGAME OVER");
							
							fightResults[0] = charHP;
							fightResults[1] = 0;
							fightResults[2] = charStats[4];
						}
					}

				}
				
				charCoins += coinNum;
				
			}
			
		}
		
		//Update character HP and coins
		charStats[0] = charHP;
		charStats[5] = charCoins;
		
		//If the character won against all enemies, continue to item shop and boss battle against Evil Wizard
		if(win)
		{
			//Tell user what their remaining HP is
			System.out.println("--" + charName + " wins the battle!--\n\n" +
							   "Your HP is now: " + charHP);
			
			//Prompt user to select an item
			itemArray = visitItemShop(charStats);
			
			fightResults[2] = itemArray[4];
		
			//Battle sequence against Evil Wizard
			wizardResults = fightWizard(charName, itemArray);

			fightResults[0] = wizardResults[0];
			fightResults[1] = wizardResults[1];
		}	
		
		return fightResults;
	}

	/**
		The visitItemShop method allows the player to select a reward item for defeating the Goblins or Skeletons.
		@param stats - Character stats
		@return An integer array with updated character stats
	*/
	
	public static int[] visitItemShop(int[] stats)
	{
		String itemName = "", outputString = "";
		
		int itemNum, weaponNum = 0, hpBoost, newHP = 0, weaponMin = 0, weaponMax = 0, strengthBoost, newStrength = 0, coins = 0, quantity, totalCost = 0, finalCost = 0, discount = 0, buyAgain;
		int[] newStats = new int[6];
		
		for (int index = 0; index < stats.length; index++)
					newStats[index] = stats[index];

		final int LONG_SWORD_COST = 120, LONG_SWORD_MIN = 3, LONG_SWORD_MAX = 7;
		final int SHORT_SWORD_COST = 90, SHORT_SWORD_MIN = 1, SHORT_SWORD_MAX = 4;
		final int MACE_COST = 80, MACE_MIN = 2, MACE_MAX = 6;
		final int RING_COST = 150, RING_OF_STRENGTH = 5;
		final int POTION_COST = 10, HEAL_POTION = 10;
		
		boolean valid = true, buy = false;
				
		Scanner keyboard = new Scanner(System.in);	
		
		do
		{
			//Item prices
			System.out.println("\nWelcome to The Item Shop!\n");
			System.out.println("You currently have " + newStats[5] + " gold.");
			System.out.println("Here's what we have for sale (all prices are in units of gold):\n");
			System.out.println("1. Long Sword\t\t" + LONG_SWORD_COST +
							 "\n2. Short Sword\t\t" + SHORT_SWORD_COST + 
							 "\n3. Mace\t\t\t" + MACE_COST +
							 "\n4. Ring of Strength\t" + RING_COST +
							 "\n5. Healing Potion\t" + POTION_COST + "\n");
			
			//Select item and quantity
			System.out.print("Please enter the item number: ");
			itemNum = inputValidation(1,2,3,4,5);
			
			System.out.print("Please enter the quantity: ");
			quantity = keyboard.nextInt();
			
			//Switch-case block to handle different item choices and costs
			switch (itemNum)
			{
				case 1:
					itemName = "Long Sword";
					totalCost = LONG_SWORD_COST;
					weaponMin = LONG_SWORD_MIN;
					weaponMax = LONG_SWORD_MAX;
					hpBoost = 0;
					newHP = newStats[0] + hpBoost;
					strengthBoost = 0;
					newStrength = newStats[1] + strengthBoost;
					weaponNum = 2;
					
					if (!(quantity == 1))
					{
						System.out.println("You can only buy one weapon at a time! Quantity was changed to 1.");
						quantity = 1;
					}
					
					outputString = "Your weapon damage is now: " + weaponMin + " - " + weaponMax;
					
					break;
				case 2:
					itemName = "Short Sword";
					totalCost = SHORT_SWORD_COST;
					weaponMin = SHORT_SWORD_MIN;
					weaponMax = SHORT_SWORD_MAX;
					hpBoost = 0;
					newHP = newStats[0] + hpBoost;
					strengthBoost = 0;
					newStrength = newStats[1] + strengthBoost;
					weaponNum = 1;
					
					if (!(quantity == 1))
					{
						System.out.println("You can only buy one weapon at a time! Quantity was changed to 1.");
						quantity = 1;
					}
					
					outputString = "Your weapon damage is now: " + weaponMin + " - " + weaponMax;
					
					break;
				case 3:
					itemName = "Mace";
					totalCost = MACE_COST;
					weaponMin = MACE_MIN;
					weaponMax = MACE_MAX;
					hpBoost = 0;
					newHP = newStats[0] + hpBoost;
					strengthBoost = 0;
					newStrength = newStats[1] + strengthBoost;
					weaponNum = 4; //Weapon 4 = Macee;
					
					if (!(quantity == 1))
					{
						System.out.println("You can only buy one weapon at a time! Quantity was changed to 1.");
						quantity = 1;
					}
					
					outputString = "Your weapon damage is now: " + weaponMin + " - " + weaponMax;
					
					break;
				case 4:
					itemName = "Ring of Strength";
					totalCost = RING_COST*quantity;
					weaponMin = newStats[2];
					weaponMax = newStats[3];
					hpBoost = 0;
					newHP = newStats[0] + hpBoost;
					strengthBoost = RING_OF_STRENGTH*quantity;
					newStrength = newStats[1] + strengthBoost;
					weaponNum = newStats[4];
					
					outputString = "Your strength has increased to " + newStats[1] + " + " + strengthBoost + " = " + newStrength + "!";
					
					break;
				case 5:
					itemName = "Healing Potion";
					totalCost = POTION_COST*quantity;
					weaponMin = stats[2];
					weaponMax = stats[3];
					hpBoost = HEAL_POTION*quantity;
					newHP = newStats[0] + hpBoost;
					strengthBoost = 0;
					newStrength = newStats[1] + strengthBoost;
					weaponNum = newStats[4];
					
					outputString = "Your HP has increased to " + newStats[0] + " + " + hpBoost + " = " + newHP + "!";
					
					break;		
			}
			
			//10% discount for 3 or more items
			if (quantity >= 3)
			{
				discount = totalCost/10;
			}
			
			//Calculate and display final cost of items with discount included
			finalCost = totalCost - discount;
			System.out.print("\nTotal cost: " + totalCost + " gold\n" + 
							 "Discount: " + discount + " gold\n" + 
							 "Final Cost: " + finalCost + " gold\n\n");
							 
			//If the final cost is less than or equal to the number of coins, the transaction is processed
			if (finalCost <= newStats[5])
			{
				System.out.println("Thank you, your transaction is complete!");
				System.out.println("\nYou purchased: " + quantity + " X " + itemName);
				System.out.println(outputString);
				newStats[0] = newHP;
				newStats[1] = newStrength;
				newStats[2] = weaponMin;
				newStats[3] = weaponMax;
				newStats[4] = weaponNum;
				newStats[5] -= finalCost;
			}
			//Otherwise, tell the user they need more money
			else 
			{
				System.out.println("You have insufficient funds! Please come back" + 
								   " with more gold!");
			}
			

			//Ask user if they would like to buy another item
			System.out.print("\nWould you like to buy another item? (1 = Yes, 0 = No): ");
			buyAgain = inputValidation(0,1);
				
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
		
		return newStats;
		
	}
	
	/**
		The fightWizard method simulates an attack sequence between the player and The Evil Wizard.
		@param charName - Character name
		@param charStats - Character stats
	*/
	
	public static int[] fightWizard(String charName, int[] charStats)
	{	
		//Output variable
		int[] gameResults = new int[3];
		
		//Character battle variables
		int charHP, charStrength, charWeaponMin, charWeaponMax, charDamage, charATK;	
		
		//Evil Wizard battle variables
		int bossNum, bossDamage, bossATK;
		
		//Variables for spell cast
		int spellGuess, spellRand, action;	
		
		//Wizard battle stats
		int[] wizardStats = enemyAttributes("Wizard");
		int bossHP = wizardStats[0];
		int bossStrength = wizardStats[1];
		int bossWMIN = wizardStats[2];
		int bossWMAX = wizardStats[3];
		
		//Character battle stats
		charHP = charStats[0];
		charStrength = charStats[1];
		charWeaponMin = charStats[2];
		charWeaponMax = charStats[3];
		gameResults[2] = charStats[4];
		
		//Input validation variable
		boolean valid = true;

		Random rand = new Random();
		
		System.out.println("You have now reached The Castle! Time to battle The Evil Wizard!\n");
		System.out.println("***" + charName + " vs The Evil Wizard***");
		
		//Continue battling as long as both character and Evil Wizard are still alive
		while(charHP > 0 && bossHP > 0)
		{
			//User can choose to either attack or attempt to cast a spell
			System.out.println("\nChoose your action:\n" +
							   "1. Attack\n" +
							   "2. Attempt Spell Cast\n");
			
			//--Character attacks first--
				
			//Prompt user to select an action
			System.out.print("What would you like to do?: ");
			action = inputValidation(1,2);
				
			//Damage factor = random value between weapon min and weapon max
			charDamage = rand.nextInt(charWeaponMax - charWeaponMin + 1) + charWeaponMin;
			bossDamage = rand.nextInt(bossWMAX - bossWMIN + 1) + bossWMIN;
				
			//Attack Value (ATK) = strength + damage
			charATK = charStrength + charDamage;
			bossATK = bossStrength + bossDamage;

			switch (action)
			{
				//Attack Evil Wizard
				case 1:
					System.out.println("\n" + charName + " attacks with ATK = " + charStrength + " + " + charDamage + " = " + charATK);
					System.out.print("Wizard HP is now " + bossHP + " - " + charATK + " = " );
					//Boss's HP is decreased by amount equal to character ATK value
					bossHP -= charATK;
					System.out.println(bossHP);
						
					//If Evil Wizard's HP drops to 0 or below, user wins and game is over
					if(bossHP <= 0)
					{
						System.out.println("\n" + charName + " defeated The Evil Wizard!\n\n" +
											   "--" + charName + " wins the battle!--\n\n" +
											   "You win! Congratulations!");
											   
						gameResults[0] = charHP;
						gameResults[1] = 1;
						
						return gameResults;
					}
						
					//If the Evil Wizard is still alive, he attacks
					else
					{
						System.out.println("\nWizard attacks with ATK = " + bossStrength + " + " + bossDamage + " = " + bossATK);
						System.out.print(charName + " HP is now " + charHP + " - " + bossATK + " = " );
						//Character's HP is decreased by amount equal to Evil Wizard ATK value
						charHP -= bossATK;
						System.out.println(charHP);
							
						//If the character's HP drops to 0 or below, user loses and game over
						if (charHP <= 0)
						{
							System.out.println("\n--" + charName + " was defeated in battle!--\n\nGAME OVER");
							
						gameResults[0] = charHP;
						gameResults[1] = 0;
						
						return gameResults;
						}
					}
					break;
					
				//Attempt to cast a spell
				case 2:
						
					//Program creates a random number between 1-5
					spellRand = rand.nextInt(5)+1;
						
					//Prompt user to guess a number between 1-5
					System.out.print("\nEnter your guess (between 1-5): ");
					spellGuess = inputValidation(1,2,3,4,5);
						
					//If the user's guess matches the program's random number selection, the spell cast is successful and the Evil Wizard instantly dies
					if(spellGuess == spellRand)
					{
						System.out.println("Correct!\n\n" +
											charName + "'s spell is cast successfully! The Wizard's HP is now 0!\n\n" +
											   "--" + charName + " wins the battle!--\n\n" +
											   "You win! Congratulations!");
							
						//Need to set Evil Wizard's HP to 0 or below to terminate loop
						bossHP = -1;
						gameResults[0] = charHP;
						gameResults[1] = 1;
						
						return gameResults;
					}
						
					//If the user guesses incorrectly, the spell fails and the Evil Wizard attacks 
					else
					{
						System.out.println("\nSpell failed!\n");
						System.out.println("Wizard attacks with ATK = " + bossStrength + " + " + bossDamage + " = " + bossATK);
						System.out.print(charName + " HP is now " + charHP + " - " + bossATK + " = " );
						//Character's HP is decreased by amount equal to Evil Wizard ATK value
						charHP -= bossATK;
						System.out.println(charHP);
						
						//If the character's HP drops to 0 or below, user loses and game over
						if (charHP <= 0)
						{
							System.out.println("\n--" + charName + " was defeated in battle!--\n\nGAME OVER");
							
						gameResults[0] = charHP;
						gameResults[1] = 0;
						
						return gameResults;
						}
					}
					break;
			}	
		}
		return gameResults;
	}
	
	public static int getCharacter()
	{
		
		int charSelection;
		boolean valid = false;
		
		System.out.println("\nHere are the characters.\n" +
							   "1. Rogue\n" +
							   "2. Paladin\n" +
							   "3. Jackie Chan\n");				   

		//Prompt user
		System.out.print("Which character do you choose?: ");					
				
		//Get input
		charSelection = inputValidation(1,2,3);
		
		return charSelection;
		
	}
	
	public static int getPath()
	{
		int pathSelect;
		
		//Path selection list
		System.out.println("The Evil Wizard must be defeated! He is in The Castle. " +
						   "To get to The Castle, you must travel through either:\n"+
						   "1. The Forest\n" +
						   "2. The Graveyard\n");
			

		System.out.print("Which path will you take?: ");
			
		//User input path selection (1 - The Forest, 2 - The Graveyard)
		pathSelect = inputValidation(1,2);
					
		return pathSelect;
	}
		
	/**
		The playAgain method asks the user if they would like to play the game again.
		@return A boolean variable indicating whether the user wants to play again.
	*/
	
	public static boolean playAgain()
	{
		boolean valid = false, play = false;
		int playAgain;
		
		//Ask user if they would like to play again
		System.out.print("\nWould you like to play again? (1 = Yes, 0 = No): ");
		playAgain = inputValidation(0,1);
				
		switch(playAgain)
		{
			//Input of 1 = Yes --> Send user back to beginning of loop
			case 1:
				valid = true;
				play = true;
				break;
			
			//Input of 0 = No --> Thank the user for playing and end program
			case 0:
				valid = true;
				play = false;
				System.out.println("\nThanks for playing!");
				break;
		}

		return play;
	}
	
	/**
		The pressEnter method forces the program to pause and wait for the user to press enter before continuing.
	*/
	
	public static void pressEnter()
	{
		System.out.println("Press ENTER to continue...");
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
	}
	
	
	public static void saveResults(String charName, int weapon, int HP, String path, int outcome) throws IOException
	{
		
		String gameWon = "", weaponName = "";
		
		FileWriter fwriter = new FileWriter("GameData.txt", true);
		PrintWriter outputFile = new PrintWriter(fwriter);
		
		switch (weapon)
		{
			case 1:
				weaponName = "Short Sword";
				break;
			case 2: 
				weaponName = "Long Sword";
				break;
			case 3:
				weaponName = "Jump Kick";
				break;
			case 4:
				weaponName = "Mace";
				break;
		}
		
		switch (outcome)
		{
			case 0:
				gameWon = "No";
				break;
			case 1:
				gameWon = "Yes";
				break;
		}
		
		outputFile.println(charName);
		outputFile.println(weaponName);
		outputFile.println(HP);
		outputFile.println(path);
		outputFile.println(gameWon);
		
		outputFile.close();
		
		System.out.println("\nThe game results have been saved!");
		
	}

	/**
		The printResults method checks if the GameData file exists, and if it does, prompts the user if they would like to print the data stored in the file.
	*/
	
	public static void printResults() throws IOException
	{
		
		File resultsFile = new File("GameData.txt");
		if (resultsFile.exists())
		{
			int userInput, lineNum;
			String[] line = new String[5];
		
			System.out.print("\nWould you like to display the saved game results? (1 = Yes, 0 = No): ");
			userInput = inputValidation(0,1);
		
			if (userInput == 1)
			{
				Scanner inputFile = new Scanner(resultsFile);
			
				System.out.println("\nCharacter\tWeapon\t\tHP\tPath\t\tGame success?");
				System.out.println("---------\t------\t\t--\t----\t\t------------");
				
				while (inputFile.hasNext())
				{
					lineNum = 0;
					while(lineNum < 5)
					{
						line[lineNum] = inputFile.nextLine();
						lineNum++;
					}
					
					System.out.printf("%-16s%-16s%-8s%-16s%-8s\n", line[0],line[1],line[2],line[3],line[4]);
					
				}
			
				inputFile.close();
			}
		}
		
	}
	
}
