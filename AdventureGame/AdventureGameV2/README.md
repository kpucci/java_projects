# COE 401 Project 3

## Description
In this assignment, you will enhance the adventure game that you coded in Assignment 2 to create the 2nd version of the game. Store your code in a file named ​AdventureGameV2.java.​

The focus of this assignment is: (1) writing methods; (2) working with arrays; and (3) file input/output.

## New Features
Here is a list of the features that need to be added to your game.

	1. Item Shop
		* The player may visit the item shop before the fight with the wizard. This visit will replace the “item reward” part of the game.
		* The item shop should offer every item that is listed in Assignment 1, with the prices that are given in Assignment 1.
			* One exception:​ Change “Magic Ring” to “Ring of Strength” (the price will remain at 150 gold coins).
		* When the player visits the item shop, she may make more than one purchase.
			* After a successful purchase, allow the player to make another purchase or to leave the shop.
		* The player may only make a purchase if the player has sufficient coins. When the player makes a purchase, subtract the cost of the purchase from the player’s coin total.
		* The purchase of each item will have an effect on the player’s attributes.
			* A weapon purchase will change the player’s min and max weapon damage.
				* Here is each weapon with its damage range.

					| Long Sword	| 3-7 |
					| Short Sword	| 1-4 |
					| Mace			| 2-6 |

				* A “Ring of Strength” purchase will add 5 to the player’s Strength attribute.
				* The purchase of a healing potion will add 10 to the player’s HP.
	2. The goblins and skeletons will drop coins when defeated.
		* Each goblin/skeleton will drop a random number of coins in the range of 30 - 50 coins.
		* When an enemy drops coins, add the number of coins dropped to the player’s coin total.
	3. The number of goblins and skeletons will vary.
		* The number of goblins will be a random number in the range 2 - 5.
		* The number of skeletons will be a random number in the range 3 - 7.
	4. Improve the pacing of the game by prompting the player to hit a key after each minion (i.e. Goblin/Skeleton) fight. So when the player battles multiple minions, instead of simulating all the fights and printing all the outputs together, the first fight will be simulated and its results printed, but then the user will be prompted to hit the Enter key to continue to the next fight.
	5. Save the results of each game when the game ends; also, allow the player to print the stored information for each saved game.
	At the end of a game, save the results of the game to a file in the current directory, and print the message “The game results have been saved!”. Specifically, your program should store the player’s character name, current weapon, current HP, path taken, and won game (“yes” or “no”). ​Each saved game should be appended to the output file so that the user can save one or more games​ (note: you may decide on the format of the output file).
	At the beginning of a game, prompt the player, “Would you like to display the saved game results?”, and if the user decides to display the results, then your program should print the results of each saved game in a table format (see sample output for examples).

## Programming Requirements
* The attribute values for the player’s character (HP, Strength, minimum weapon damage, maximum weapon damage) and the player’s number of coins must be stored in an int[]​ array.
* The attribute values for each enemy (goblin, skeleton, evil wizard) (HP, Strength, minimum weapon damage, maximum weapon damage) must be stored in an int[] array.
* The “item shop” feature must be implemented as a method that has an ​int[] parameter.
	* Call this method: visitItemShop()
* Write and use the following methods.
	* getCharacter()
		* This method will have no parameters and will prompt the player for the choice of a character and will return the number entered.
	* getPath()
		* This method will have no parameters and will prompt the player for the choice of a path and will return the number entered.
	* fightMinion()
		* This method will simulate a battle between the player and either a skeleton or a goblin. You may decide on the parameters and return value for this method.
	* fightWizard()
		* This method will simulate a battle between the player and the wizard. You may decide on the parameters and return value for this method.

* Use the ​printf​ method to display the saved game results in a table format.
* Include a multi-line comment at the top of your source file that includes your name, the class abbreviation (CS401), the semester (Fall 2016), and the assignment name (Assignment 3), with each item on its own line.
* Use good programming style. Specifically, in addition to the multi-line comment described above, ​include at least 10 comments in your code.​
* Global variables are prohibited (except for constants).
