# COE 401 Project 4

## Description
In this assignment, you will refactor the adventure game that you coded in Assignments 2 and 3; more specifically, you will write and use a number of custom Java classes and also make a few gameplay changes. Store your refactored “Adventure Game” code in a file named AdventureGameV3.java​. Store your classes in the files Character.java,​ ​Player.java,​ Enemy.java​, and ​Weapon.java, Potion.java​ and ​ItemShop.java​.

The focus of this assignment is: (1) defining classes; and (2) using objects.

## Changes to the Gameplay
* The player will go through ​both​ the Forest and the Graveyard, in that order.
* Remove the “save game” feature.
* The player will have an ​inventory​ that can store up to 5 potions.
	* The inventory will be implemented as an array of Potion​ objects.
* The item shop will offer the following items (each weapon has the same cost and
attributes as it has in Assignment 3):
	* Long Sword
	* Short Sword
	* Mace
	* 2 types of Healing Potions - Minor Healing (5 Gold) and Healing (10 Gold)
		* A Minor Healing Potion adds 5 HP; a Healing Potion adds 10
	* 2 types of Strength Potions - Minor Strength (20 Gold) and Strength (40 Gold)
		* A Minor Strength Potion adds 2 to strength; a Strength Potion adds 5
* Before visiting the Graveyard and before battling the Wizard, the player will have the option to do one or more of the following:
	* View inventory
	* Drink a potion
	* Visit the Item Shop
* The player’s inventory has 5 slots; each slot can hold 1 potion. When the player chooses to view the inventory, display a row for each slot, with the row number in brackets and the item name to the right of that (display the empty string if the slot is empty).

For example, assume that my player has 3 items, with an item in slots 1, 3 and 4. Then my inventory display would look like the following:

	[1] Healing Potion
	[2]
	[3] Minor Strength Potion
	[4] Minor Healing Potion
	[5]

* When the player purchases a potion, the potion will n​ ot​ be applied and will instead be added to the player’s inventory. The player can drink the potion when given the option to do so; drinking the potion will apply its effect.
	* When a player drinks a potion, the potion should be removed from the inventory.
	* When adding a new item to the inventory, you may add it in any open slot.
* Check out the example output for a demonstration of these new features in action.

## Class Descriptions

### Character​ (​abstract​ class)
	* Public enum
		* Name: ​Type
		* Values: ​ROGUE, PALADIN, JACKIE_CHAN, GOBLIN, SKELETON, WIZARD
	* Attributes
		* name (String)
		* hitPoints (int)
		* strength (int)
		* weapon (Weapon)
	* Methods
		* Character(characterType)
			* This method is the only constructor of the ​Character​ class. Based on the value of characterType​, set the proper ​String for the name, ​int ​for the initial hit points, int ​for the strength attribute, and reference to a ​Weapon​ object for the weapon.
		* getName()
			* This method should return the name as a String​.
		* getHitPoints()
			* This method should return the number of hit points.
		* getStrength()
			* This method should return the value of the strength attribute.
		* setStrength(strength)
			* This method will set the player’s strength to the value that is passed to the method.
		* setWeapon(weapon)
			* This method will set the player’s weapon to be the Weapon ​object that is passed to the method.
		* attack(opponent)
			* This method will simulate an attack against the Character​ object that is passed to the method.
		* increaseHitPoints(pointIncrease)
			* This method will add the number of points passed to the hit points.
		* decreaseHitPoints(pointDecrease)
			* This method will subtract the number of points passed from the hit points.
		* isDefeated()
			* This method will return t​rue​ if the player’s hit points value is 0 or less; otherwise, it will return ​false​.

### Player ​(​extends​ ​Character ​class)
	* Attributes
		* coins (int)
		* inventory (Potion[])
	* Methods
		* Player(playerType)
			* This method is the only constructor of the ​Player​ class. In this method, call the superclass constructor with the value playerType​, initialize coins​ to 0, and set​ inventory​ to a new array of 5 ​Potion​ objects.
		* increaseStrength(strengthIncrease)
			* This method will add the passed value to the player’s strength attribute.
		* getCoins()
			* This method will return the number of coins. ■ increaseCoins(coins)
			* This method will add the number of coins passed to the player’s coin total.
		* decreaseCoins(coins)
			* This method will subtract the number of coins passed from the player’s coin total.
		* addToInventory(potion)
			* This method will take a Potion​ object and add it to the player’s inventory if and only if there is an open slot; do nothing if the attempt fails.
		* removeFromInventory(index)
			* This method will take an index in the range of 1 - 5 and return a reference to the ​Potion​ object that resides at that index. If no Potion​ object is at the specified index, then return null​.
		* displayInventory()
			* This method will print out the player’s inventory in the format specified in the “Changes to the Gameplay” section.
		* getNumOpenSlots()
			* This method will return the number of open slots in the player’s inventory. Use this method to determine if a player may add potions to the inventory.
		* battleMinion(enemy)
			* This method will simulate a battle against the Enemy​ object that is passed to the method, where that object is a goblin or skeleton.​
		* battleWizard(enemy)
			* This method will simulate a battle against the Enemy object that is passed to the method, where that object is a wizard​.

### Enemy ​(​extends​ ​Character ​class)
	* Methods
		* Enemy(enemyType)
			* This method is the only constructor of the ​Enemy​ class. In this method, call the superclass constructor with the value enemyType.
		* dropCoins()
			* This method will return a positive integer that represents the number of coins that the enemy dropped (this will be a random int​ that is in the range of 30 - 50).
		* getNumGoblins()
			* This is a static​ method that will return a random ​int​ that is in the range 2 - 5.
		* getNumSkeletons()
			* This is a static​ method that will return a random ​int​ that is in the range 3 - 7.

### Weapon
	* Public Constants
		* Add all of the weapon-damage-related constants (e.g. SHORT_SWORD_MIN​) to this class as public, static constants. You will then be able to reference a constant in your main method by preceding the name of the constant with the name of the class (e.g. Weapon.SHORT_SWORD_MIN ​).
	* Attributes
		* name (String)
		* minDamage (int)
		* maxDamage (int)
	* Methods
		* Weapon(name, minDamage, maxDamage)
			* This method is the only constructor of the Weapon class. Pass to this method a ​String​ for the name, an ​int​ for the minimum damage, and an int ​for the maximum damage.
		* getName()
			* This method should return the name as a String​.
		* getMinDamage()
			* This method will return the minimum damage of the weapon.
		* getMaxDamage()
			* This method will return the maximum damage of the weapon.
			* getDamage()
				* This method will return a random int ​that is in the range of the minimum and maximum damage values.

### Potion
	* Public enum
		* Name: ​Type
		* Values: ​MINOR_HEALING, HEALING, MINOR_STRENGTH, STRENGTH
	* Attributes
		* name (String)
		* type (Type)
	* Methods
		* Potion(potionType)
			* This method is the only constructor of the ​Potion​ class. In this method, initialize type ​to the passed in value, then set name based on type ​(valid names are: Minor Healing Potion, Healing Potion, Minor Strength Potion, Strength Potion)
		* getName()
			* This method should return the name as a String​.
		* drink(player)
			* This method should apply the effect of the potion, based on the potion’s type (e.g. If it’s a Minor Strength potion, then it should add 2 to the player’s strength) (note that a reference to the player is being passed to this method - use this reference to call the proper method in the ​Player​ class)

### ItemShop
	* Private Constants
		* Add all of the weapon-cost-related constants to this class as private, static constants.
	* Methods
		* visitItemShop(player)
			* This static​ method will simulate a visit to the item shop for the player.

## Programming Requirements
* Implement the gameplay that defines the Adventure Game from Assignment 3 and include the changes and new features that are listed above in the “Changes to the Gameplay” section.
* Define and properly use all of the classes described above.
* Attributes for the player, enemies and weapons must be stored in objects of the proper class, not in primitive variables or arrays.
* Every method of every class should be called a​ t least once​ in your AdventureGameV3​ class or in one of your other classes.
* Include a multi-line comment at the top of your source file that includes your name, the class (CS401), the semester (Fall 2016), and the assignment name (Assignment 4), with each item on its own line.
* Use good programming style. Specifically, in addition to the multi-line comment described above, ​include at least 5 comments in each class file.​
* Global variables are prohibited (except for constants).
