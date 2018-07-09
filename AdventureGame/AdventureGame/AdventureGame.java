/*
	Katie Vaughan
	CS401, Fall 2016
	Assignment 2 - Adventure Game
*/

import java.util.Random;
import java.util.Scanner;

public class AdventureGame
{
	public static void main(String[] args)
	{
		//Variables
		int charSelection, pathSelect, playAgain;
		int choices[] = {1,2,3};
		int choices2[] = {1,2};
		String charName = "", pathArray[], enemyName = "";
		boolean valid = true;
		
		Scanner keyboard = new Scanner(System.in);
		
		//Outermost do-while loop runs through the game once and then verifies if the user wants to play again
		do	
		{
			//Beginning dialogue and character selection list: 1 - Rogue, 2 - Paladin, 3 - Jackie Chan
			System.out.println("\nAdventure Game - Start!\n");
			System.out.println("Here are the characters.\n" +
							   "1. Rogue\n" +
							   "2. Paladin\n" +
							   "3. Jackie Chan\n");
							   
			//do-while loop verifies that the user entered a valid input				   
			do
			{
				//Prompt user
				System.out.print("Which character do you choose?: ");					
				
				//Get input
				charSelection = keyboard.nextInt();
				
				//Validate input
				valid = inputValidation(choices,charSelection);
			} while (!valid);
			
			//Refer to charName method for more information
			charName = characterName(charSelection);
		
			//Path selection list
			System.out.println("\nYou chose: " + charName + "\n");
			System.out.println("The Evil Wizard must be defeated! He is in The Castle. " +
							   "To get to The Castle, you must travel through either:\n"+
							   "1. The Forest\n" +
							   "2. The Graveyard\n");
			
			//do-while loop verifies that the user entered a valid input
			do
			{
				System.out.print("Which path will you take?: ");
				
				//User input path selection (1 - The Forest, 2 - The Graveyard)
				pathSelect = keyboard.nextInt();
				valid = inputValidation(choices2, pathSelect);
			
			} while (!valid);	
			
			//Refer to selectPath method for more information
			pathArray = selectPath(pathSelect);
			
			//Assigns enemy name to String variable
			enemyName = pathArray[1];
		
			//Main battle sequence method
			battle(charName, enemyName);
			
			//do-while loop verifies that the user entered a valid input
			do
			{
				//Ask user if they would like to play again
				System.out.print("\nWould you like to play again? (1 = Yes, 0 = No): ");
				playAgain = keyboard.nextInt();
				
				switch(playAgain)
				{
					//Input of 1 = Yes --> Send user back to beginning of loop
					case 1:
						valid = true;
						break;
					
					//Input of 0 = No --> Thank the user for playing and end program
					case 0:
						valid = true;
						System.out.println("\nThanks for playing!");
						break;
					
					//Invalid entry
					default:
						valid = false;
						System.out.println("\nThat was an invalid entry, please try again.");
				}
				
			}while(!valid);
		
		}while(playAgain == 1);
		
	}

//--Methods----------------------------------------------------------------------------------------------	
	
	/**
		The inputValidation method determines if the user input matches one of the available choices and warns the user if invalid selection was made.
		@param choices - Array of integers representing the available choices
		@param input - User input
		@return True if the user input matches one of the available choices; False otherwise
	*/
	
	public static boolean inputValidation(int[] choices, int input)
	{
		boolean valid = false;
		
		for(int val : choices)
		{
			if (input == val)
			{
				valid = true;
			}
		}
		if (!valid)
		{
			System.out.println("\nThat was an invalid entry, please try again.\n");
		}
		return valid;				
	}
	
	/**
		The charName method chooses the correct character name based on user input.
		@param charNum - User selection
		@return A string array with the following values: [0] - name of character, [1] - value for input validation (0 or 1)
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
		
		return name;	
	}
	
	/**
		The charAttributes method stores the character's HP, strength, and weapon damage min and max values in an array.
		@param charName - Character name
		@return An integer array with the following values: [0] - HP, [1] - Strength, [2] - Weapon Minimum, [3] - Weapon Maximum
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
		
		int[] charAtts = new int[4];
		
		//Store the appropriate values in output array depending on character name
		switch (charName)
		{
			case "Rogue":
				charAtts[0] = ROGUE_HP;
				charAtts[1] = ROGUE_STRENGTH;
				charAtts[2] = ROGUE_WMIN;
				charAtts[3] = ROGUE_WMAX;
				break;
			case "Paladin":
				charAtts[0] = PALADIN_HP;
				charAtts[1] = PALADIN_STRENGTH;
				charAtts[2] = PALADIN_WMIN;
				charAtts[3] = PALADIN_WMAX;
				break;
			case "Jackie Chan":
				charAtts[0] = CHAN_HP;
				charAtts[1] = CHAN_STRENGTH;
				charAtts[2] = CHAN_WMIN;
				charAtts[3] = CHAN_WMAX;
				break;
		}

		return charAtts;
	}
	
	/**
		The enemyAttributes method stores the enemy's HP, strength, and weapon damage min and max values in an array.
		@param enemy - Enemy name
		@return An integer array with the following values: [0] - HP, [1] - Strength, [2] - Weapon Minimum, [3] - Weapon Maximum, [4] - Number of enemies
	*/

	public static int[] enemyAttributes(String enemy)
	{
		
		//Goblin attributes
		final int GOBLIN_HP = 25;
		final int GOBLIN_STRENGTH = 4;
		final int GOBLIN_WMIN = 2;
		final int GOBLIN_WMAX = 6;
		final int GOBLIN_NUM = 3;
		
		//Skeleton attributes
		final int SKELETON_HP = 25;
		final int SKELETON_STRENGTH = 3;
		final int SKELETON_WMIN = 1;
		final int SKELETON_WMAX = 4;
		final int SKELETON_NUM = 5;
		
		int[] enemyAtts = new int[5];
		
		//Store the appropriate values in output array depending on enemy name
		switch (enemy)
		{
			case "Goblin":
				enemyAtts[0] = GOBLIN_HP;
				enemyAtts[1] = GOBLIN_STRENGTH;
				enemyAtts[2] = GOBLIN_WMIN;
				enemyAtts[3] = GOBLIN_WMAX;
				enemyAtts[4] = GOBLIN_NUM;
				break;
			case "Skeleton":
				enemyAtts[0] = SKELETON_HP;
				enemyAtts[1] = SKELETON_STRENGTH;
				enemyAtts[2] = SKELETON_WMIN;
				enemyAtts[3] = SKELETON_WMAX;
				enemyAtts[4] = SKELETON_NUM;
				break;	
		}	
		return enemyAtts;
	}
	
	/**
		The selectPath method selects the correct path choice depending on user input.
		@param pathSelection - User input
		@return An String array with the following values: [0] - name of path, [1] - value for input validation ("true" or "false")
	*/
	
	public static String[] selectPath(int pathSelection)
	{
		String path[] = new String[2];
		
		//Path Options: 1 - The Forest, 2 - The Graveyard
		switch (pathSelection)
		{
			//The Forest --> Enemy = Goblin
			case 1:
				path[0] = "The Forest";
				path[1] = "Goblin";
				System.out.println("\nYou chose: The Forest\n");
				System.out.println("Once you enter The Forest, you encounter 3 Goblins! Time for battle!\n");
				break;
			
			//The Graveyard --> Enemy = Skeleton
			case 2: 
				path[0] = "The Graveyard";
				path[1] = "Skeleton";
				System.out.println("\nYou chose: The Graveyard\n");
				System.out.println("Once you enter The Graveyard, you encounter 5 Skeletons! Time for battle!\n");
				break;			
		}
		return path;	
	}
	
	/**
		The battle method simulates an attack sequence between the selected character and the enemies based on path selection.
		@param charName - Character name
		@param enemyName - Enemy name
	*/
	
	public static void battle(String charName, String enemyName)
	{
		//Character battle variables
		int charHP = 0, charStrength, charWeaponMin, charWeaponMax, charDamage, charATK;
		
		//Enemy battle variables
		int enemyHP, enemyStrength, enemyWeaponMin, enemyWeaponMax, battleNum, enemyDamage, enemyATK;
		
		int[] charStats = new int[4];
		int[] enemyStats = new int[5];
		int[] itemArray;
		
		//The win variable determines if the battle sequence should continue to the next enemy or not
		boolean win = true;
		
		Random rand = new Random();
		
		//See charAttributes() and enemyAttributes() methods for more information
		charStats = charAttributes(charName);
		enemyStats = enemyAttributes(enemyName);
		
		//Character battle values from charAttributes() method output
		charStrength = charStats[1];
		charWeaponMin = charStats[2];
		charWeaponMax = charStats[3];
		charHP = charStats[0];
		
		//Enemy battle values from enemyAttributes() method output
		enemyStrength = enemyStats[1];
		enemyWeaponMin = enemyStats[2];
		enemyWeaponMax = enemyStats[3];
		enemyHP = enemyStats[0];
		
		//Number of enemies to battle (3 goblins or 5 skeletons)
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
						}
					}

				}
				
			}
			
		}
		
		//If the character won against all enemies, continue to item shop and boss battle against Evil Wizard
		if(win)
		{
			//Tell user what their remaining HP is
			System.out.println("--" + charName + " wins the battle!--\n\n" +
							   "Your HP is now: " + charHP);
			
			//Prompt user to select an item: 1 - Healing Potion, 2 - Ring of Strength
			itemArray = item(charHP, charStrength);
		
			//Battle sequence against Evil Wizard
			bossBattle(charName, itemArray[0], itemArray[1]);	
		}	
	}

	/**
		The item method allows the player to select a reward item for defeating the Goblins or Skeletons.
		@param hp - Remaining character HP
		@param strength - Character strength
		@return An integer array with the following values: [0] - New HP, [1] - New strength
	*/
	
	public static int[] item(int hp, int strength)
	{
		int itemNum;
		boolean valid = true;
		int[] stats = new int[2];
		Scanner keyboard = new Scanner(System.in);	
		
		//Item list: 1 - Healing potion increases HP by 10
		//			 2 - Ring of Strength increases strength by 5
		System.out.print("\nPlease choose a reward.\n" + 
							 "1. Healing Potion\n" + 
							 "2. Ring of Strength\n\n");
		
		//do-while loop verifies that the user entered a valid input
		do
		{
			//Prompt user to select item
			System.out.print("Which item do you choose?: ");
			itemNum = keyboard.nextInt();
			
			//Update character stats based on selection
			switch (itemNum)
			{
				//Healing Potion
				case 1: 
					stats[0] = hp + 10;
					stats[1] = strength;
					System.out.println("\nYou chose: Healing Potion.\n\nYour HP has increased to " + hp + " + 10 = " + stats[0] + "!\n");
					valid = true;
					break;
				
				//Ring of Strength
				case 2: 
					stats[0] = hp;
					stats[1] = strength + 5;
					System.out.println("\nYou chose: Ring of Strength\n\nYour Strength has increased to " + strength + " + 5 = " + stats[1] + "!\n");
					valid = true;
					break;
				
				//Invalid input
				default:
					valid = false;
					System.out.println("\nThat was an invalid entry, please try again.\n");
			}
		}while(!valid);
		
		return stats;	
	}
	
	/**
		The bossBattle method simulates an attack sequence between the player and The Evil Wizard.
		@param name - Character name
		@param hp - Remaining character HP
		@param strength - Character strength
	*/
	
	public static void bossBattle(String name, int hp, int strength)
	{
		//Character battle variables
		int charHP, charStrength, charWeaponMin, charWeaponMax, charDamage, charATK;	
		
		//Evil Wizard battle variables
		int bossNum, bossDamage, bossATK;
		
		//Variables for spell cast
		int spellGuess, spellRand, action;
		
		int[] charStats = new int[4];
		int[] enemyStats = new int[5];
		
		//Input validation variable
		boolean valid = true;
		
		int bossHP = 40;
		final int BOSS_STRENGTH = 8;
		final int BOSS_WMIN = 4;
		final int BOSS_WMAX = 10;
		
		//Character battle stats
		charStats = charAttributes(name);
		charWeaponMin = charStats[2];
		charWeaponMax = charStats[3];
		charHP = hp;
		charStrength = strength;
		
		Scanner keyboard = new Scanner(System.in);
		
		Random rand = new Random();
		
		System.out.println("You have now reached The Castle! Time to battle The Evil Wizard!\n");
		System.out.println("***" + name + " vs The Evil Wizard***");
		
		//Continue battling as long as both character and Evil Wizard are still alive
		while(charHP > 0 && bossHP > 0)
		{
			//User can choose to either attack or attempt to cast a spell
			System.out.println("\nChoose your action:\n" +
							   "1. Attack\n" +
							   "2. Attempt Spell Cast\n");
			
			//do-while loop verifies that the user entered a valid input
			do
			{
			//--Character attacks first--
				
				//Prompt user to select an action
				System.out.print("What would you like to do?: ");
				action = keyboard.nextInt();
				
				//Damage factor = random value between weapon min and weapon max
				charDamage = rand.nextInt(charWeaponMax - charWeaponMin + 1) + charWeaponMin;
				bossDamage = rand.nextInt(BOSS_WMAX - BOSS_WMIN + 1) + BOSS_WMIN;
				
				//Attack Value (ATK) = strength + damage
				charATK = charStrength + charDamage;
				bossATK = BOSS_STRENGTH + bossDamage;

				switch (action)
				{
					//Attack Evil Wizard
					case 1:
						valid = true;
						System.out.println("\n" + name + " attacks with ATK = " + charStrength + " + " + charDamage + " = " + charATK);
						System.out.print("Wizard HP is now " + bossHP + " - " + charATK + " = " );
						//Boss's HP is decreased by amount equal to character ATK value
						bossHP -= charATK;
						System.out.println(bossHP);
						
						//If Evil Wizard's HP drops to 0 or below, user wins and game is over
						if(bossHP <= 0)
						{
							System.out.println("\n" + name + " defeated The Evil Wizard!\n\n" +
											   "--" + name + " wins the battle!--\n\n" +
											   "You win! Congratulations!");
						}
						
						//If the Evil Wizard is still alive, he attacks
						else
						{
							System.out.println("\nWizard attacks with ATK = " + BOSS_STRENGTH + " + " + bossDamage + " = " + bossATK);
							System.out.print(name + " HP is now " + charHP + " - " + bossATK + " = " );
							//Character's HP is decreased by amount equal to Evil Wizard ATK value
							charHP -= bossATK;
							System.out.println(charHP);
							
							//If the character's HP drops to 0 or below, user loses and game over
							if (charHP <= 0)
							{
								System.out.println("\n--" + name + " was defeated in battle!--\n\nGAME OVER");
							}
						}
						break;
					
					//Attempt to cast a spell
					case 2:
						valid = true;
						
						//Program creates a random number between 1-5
						spellRand = rand.nextInt(5)+1;
						
						//Prompt user to guess a number between 1-5
						System.out.print("\nEnter your guess (between 1-5): ");
						spellGuess = keyboard.nextInt();
						
						//If the user's guess matches the program's random number selection, the spell cast is successful and the Evil Wizard instantly dies
						if(spellGuess == spellRand)
						{
							System.out.println("Correct!\n\n" +
											   name + "'s spell is cast successfully! The Wizard's HP is now 0!\n\n" +
											   "--" + name + " wins the battle!--\n\n" +
											   "You win! Congratulations!");
							
							//Need to set Evil Wizard's HP to 0 or below to terminate loop
							bossHP = -1;
						}
						
						//If the user guesses incorrectly, the spell fails and the Evil Wizard attacks 
						else
						{
							System.out.println("\nSpell failed!\n");
							System.out.println("Wizard attacks with ATK = " + BOSS_STRENGTH + " + " + bossDamage + " = " + bossATK);
							System.out.print(name + " HP is now " + charHP + " - " + bossATK + " = " );
							//Character's HP is decreased by amount equal to Evil Wizard ATK value
							charHP -= bossATK;
							System.out.println(charHP);
						
							//If the character's HP drops to 0 or below, user loses and game over
							if (charHP <= 0)
							{
								System.out.println("\n--" + name + " was defeated in battle!--\n\nGAME OVER");
							}
						}
						break;
					
					//Invalid input
					default:
						valid = false;
						System.out.println("\nThat was an invalid entry, please try again.\n");
				}
			}while(!valid);	
		}
	}
}

