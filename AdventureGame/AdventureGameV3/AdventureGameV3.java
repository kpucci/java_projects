/*
	Katie Vaughan
	CS401
	Fall 2016
	Assignment 4 - Adventure Game
*/

import java.util.Random;
import java.util.Scanner;

public class AdventureGameV3
{
	public static void main(String[] args)
	{
		//Variables
		int numGoblins;
		int numSkeletons;
		boolean continuePlay = false;
		Player player = null;
		Enemy wizard = null;

		do //Do if user wants to continue playing
		{
			numGoblins = Enemy.getNumGoblins();
			numSkeletons = Enemy.getNumSkeletons();
			//Beginning dialogue
			System.out.println("\nAdventure Game - Start!\n\nHere are the characters.\n" +
												 "1. Rogue\n" +
												 "2. Paladin\n" +
												 "3. Jackie Chan\n");
			System.out.print("Which character do you choose?: ");

			//Create a player
			player = getPlayer();

			//Dialogue
			System.out.println("The Evil Wizard must be defeated! He is in The Castle. " +
												 "To get to The Castle, you must travel through the The " +
												 "Forest and then through The Graveyard. Let's go!");


			System.out.printf("\nOnce you enter The Forest, you encounter %d Goblins! Time for battle!\n\n", numGoblins);

			//Fight goblins
			minionBattle(player,numGoblins,Character.Type.GOBLIN);

			//If the player was defeated, stop the game and ask if user would like to play again
			if(player.isDefeated())
			{
				System.out.printf("--%s was defeated in battle!--\n\nGAME OVER\n\n", player.getName());
				continuePlay = playAgain();
				continue;
			}

			//Will only get to this point if the player won the battle. Did this so
			//the rest of the code doesn't have to be enclosed in an else statement
			//and so that I didn't have to return a Boolean from the function
			System.out.printf("--%s wins the battle!--\n\n", player.getName());
			System.out.printf("Your HP is: %d\n\n", player.getHitPoints());

			//Display menu of choices for player
			playerMenu(player); //Let user decide what to do next

			System.out.printf("\nOnce you enter The Graveyard, you encounter %d Skeletons! Time for battle!\n\n", numSkeletons);
			//Fight skeletons
			minionBattle(player,numSkeletons, Character.Type.SKELETON);

			//If the player was defeated, stop the game and ask if user would like to play again
			if(player.isDefeated())
			{
				System.out.printf("--%s was defeated in battle!--\n\nGAME OVER\n\n", player.getName());
				continuePlay = playAgain();
				continue;
			}

			System.out.printf("--%s wins the battle!--\n\n", player.getName());
			System.out.printf("Your HP is: %d\n\n", player.getHitPoints());

			//Display menu of choices for player
			playerMenu(player);

			//Create wizard object
			wizard = new Enemy(Character.Type.WIZARD);

			//Fight wizard
			player.battleWizard(wizard);

			//If the player was defeated, stop the game and ask if user would like to play again
			if(player.isDefeated())
			{
				System.out.printf("--%s was defeated in battle!--\n\nGAME OVER\n\n", player.getName());
				continuePlay = playAgain();
				continue;
			}

			System.out.printf("--%s wins the battle!--\n\n", player.getName());
			System.out.println("You win! Congratulations!\n\nGAME OVER\n");

			//Ask user to play again
			continuePlay = playAgain();

		}while(continuePlay);
	}

	//Methods---------------------------------------------------------------------
	/**
		The inputValidation method determines if the user input matches one of the
		available choices and warns the user if invalid selection was made.
		NOTE: Want to add ability to catch exception if something other than a number was entered
		@param choices - Variable-length integer array of allowable choices
		@return A validated user selection
	*/

	public static int inputValidation(int... choices)
	{
		//Variables
		boolean valid = false;
		int choice;

		//Create scanner object
		Scanner keyboard = new Scanner(System.in);

		do //Keep making user re-enter integers if they don't match one of the given options
		{
			//Get user input
			choice = keyboard.nextInt();

			//Step through array of allowable choices
			for(int val : choices)
			{
				//If user input matches an item in the array, set valid to true to exit loop
				if (choice == val)
				{
					valid = true;
				}
			}
			//If a match wasn't found, tell the user to try again
			if (!valid)
			{
				System.out.print("\nThat was an invalid entry, please try again: ");
			}
		}while(!valid);

		//Return valid user input
		return choice;
	}

	/**
		The getPlayer method initializes a Player object based on user selection.
		@return A Player reference variable
	*/

	public static Player getPlayer()
	{
		int playerChoice; //Variable to store user input
		Player player = null; //Declare a Player object. Need to do it here to specify correct scope
		playerChoice = inputValidation(1,2,3); //Get valid user input

		switch (playerChoice) //Switch based on selection
		{
			//Initialize player object with correct enum. Note that fully-qualified name is required or it won't work
			case 1: //Rogue
				player = new Player(Character.Type.ROGUE);
				break;
			case 2:
				player = new Player(Character.Type.PALADIN);
				break;
			case 3:
				player = new Player(Character.Type.JACKIE_CHAN);
				break;
		}

		//Print name of player selected by user
		System.out.println("\nYou chose: " + player.getName() + "\n");

		//Return the Player reference variable
		return player;
	}

	/**
		The minionBattle method simulates a looped-style battle with multiple goblins/skeletons.
		@param player - Player reference variable
		@param numEnemy - Number of enemies to battle
		@param enemyType - Enumerator specifying what type of minion to battle
	*/

	public static void minionBattle(Player player, int numEnemy, Character.Type enemyType)
	{
		Enemy minion = null;

		//Cycle through minion battles
		for(int i = 1; i <= numEnemy; i++)
		{
			minion = new Enemy(enemyType);
			System.out.printf("***%s vs %s %d***\n", player.getName(), minion.getName(),i);
			player.battleMinion(minion);

			if(!player.isDefeated())
	    {
	      int coinNum = minion.dropCoins(); //Get number of dropped coins from defeated goblin
	      player.increaseCoins(coinNum); //Increase player's coins
	      System.out.printf("%s defeated %s %d!\n", player.getName(), minion.getName(), i);
	      System.out.printf("%s gains %d gold coins\n\n", player.getName(), coinNum);
	    }
			else
				break;
			pressEnter(); //Press enter to continue
		}
	}

	/**
		The playerMenu method shows the user the following list of options:
			1. View inventory
			2. Drink a potion
			3. Visit the Item Shop
			4. Continue
		The method will perform whatever option the player chooses.
		@param player - Player reference variable
	*/

	public static void playerMenu(Player player)
	{
		int userInput = 0, potionChoice = 0; //Initialize variables
		Potion potionToDrink = null; //Initialize potion object to null

		//Continue running through the loop as long as the player doesn't choose to continue
		while (userInput != 4)
		{
			//Dialogue
			System.out.print("What would you like to do now?\n" +
												 "1. View inventory\n" +
												 "2. Drink a potion\n" +
												 "3. Visit the Item Shop\n" +
												 "4. Continue\n\n" +
												 "Enter your choice here: ");

			userInput = inputValidation(1,2,3,4); //Get user input

			//Switch based on user input
			switch (userInput)
			{
				case 1: //View inventory
					player.displayInventory();
					break;

				case 2: //Drink a potion

					//Only allow user to drink a potion if there's one available in inventory
					if(player.getNumOpenSlots() != 5)
					{
						boolean valid = false; //Initialize boolean variable

						System.out.print("Which potion would you like to drink? ");

						//While loop ensures the user doesn't choose an empty slot
						while(!valid)
						{
							potionChoice = AdventureGameV3.inputValidation(1,2,3,4,5); //Get valid user input

							//If user chooses an empty slot, make them choose again
							if (player.isSlotEmpty(potionChoice))
							{
								System.out.print("\nYou don't have a potion in that slot! Please try again: ");
								valid = false;
							}
							//If user chooses a filled slot, drink potion
							else
							{
								potionToDrink = player.getInventoryItem(potionChoice); //Get selected potion
								potionToDrink.drink(player); //Drink potion
								player.removeFromInventory(potionChoice); //Remove the selected potion from inventory
								valid = true;
							}
						}
					}
					//Tell the user they don't have any potions if all slots are empty
					else
				  	System.out.println("\nYou don't have any potions!\n");

					break;

				case 3: //Visit item shop
					ItemShop.visitItemShop(player);
					break;

				case 4: //Continue
					break;
			}
		}
	}

	/**
		The pressEnter method pauses the game until the user presses Enter.
	*/

	public static void pressEnter()
	{
		System.out.println("Press ENTER to continue...");
		Scanner keyboard = new Scanner(System.in);
		keyboard.nextLine();
	}

	/**
		The playAgain method asks the user if they would like to play the game again.
		@return Boolean indicating the users choice
	*/

	public static boolean playAgain()
	{
		//Variables
		boolean valid = false, play = false;
		int playAgain;

		//Ask user if they would like to play again
		System.out.print("\nWould you like to play again? (1 = Yes, 0 = No): ");
		playAgain = inputValidation(0,1); //Get valid user input

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
				System.out.println("\nThanks for playing!\n");
				break;
		}

		return play;
	}

}
