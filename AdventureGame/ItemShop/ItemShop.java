/*
Katie Vaughan
CS401
Fall 2016
Assignment 1
*/

//Import Scanner
import java.util.Scanner;

public class ItemShop
{
	public static void main(String[] args)
	{
		//Variables
		String userName;
		final int LONG_SWORD = 120, SHORT_SWORD = 90, MACE = 80, MAGIC_RING = 150, HEAL_POTION = 10;
		int userCoins, itemNum, quantity, totalCost = 0, discount = 0, finalCost;
		
		//Initialize new instance of Scanner
		Scanner keyboard = new Scanner(System.in);
		
		//Enter username
		System.out.print("Enter your name: ");
		userName = keyboard.nextLine();
		
		//Enter amount of gold
		System.out.print("Enter your number of gold coins: ");
		userCoins = keyboard.nextInt();
		
		//Item prices
		System.out.println("\nWelcome to The Item Shop, " + userName + "!\n");
		System.out.println("Here's what we have for sale (all prices are in units of gold):\n");
		System.out.println("1. Long Sword\t\t" + LONG_SWORD +
						 "\n2. Short Sword\t\t" + SHORT_SWORD + 
						 "\n3. Mace\t\t\t" + MACE +
						 "\n4. Magic Ring\t\t" + MAGIC_RING +
						 "\n5. Healing Potion\t" + HEAL_POTION + "\n");
		
		//Select item and quantity
		System.out.print("Please enter the item number: ");
		itemNum = keyboard.nextInt();
		System.out.print("Please enter the quantity: ");
		quantity = keyboard.nextInt();
		
		//Switch-case block to handle different item choices and costs
		switch (itemNum)
		{
			case 1:
				totalCost = LONG_SWORD*quantity;
				break;
			case 2:
				totalCost = SHORT_SWORD*quantity;
				break;
			case 3:
				totalCost = MACE*quantity;
				break;
			case 4:
				totalCost = MAGIC_RING*quantity;
				break;
			case 5:
				totalCost = HEAL_POTION*quantity;
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
		if (finalCost <= userCoins)
		{
			System.out.println("Thank you, " + userName + "! Your transaction is complete!" + 
						       " Please stop by again!");
		}
		//Otherwise, tell the user they need more money
		else 
		{
			System.out.println(userName + " - you have insufficient funds! Please come back" + 
							   " with more gold!");
		}
		
		
						   
		
	}
}