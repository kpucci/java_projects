import java.util.Random;
import java.util.Scanner;

public class AdventureGame2
{
	public static void main(String[] args)
	{
		int charSelect, hp, strength, weaponMin, weaponMax, charArray[], pathArray[], enemyNum;
		int pathSelect;
		String charName = "", enemyName;
		boolean validPath;
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Adventure Game - Start!\n");
		System.out.println("Here are the characters.\n" +
							"1. Rogue\n" +
							"2. Paladin\n" +
							"3. Jackie Chan\n");
		do
		{
			System.out.print("Which character do you choose?: ");					
		
			charSelect = keyboard.nextInt();
			
			charArray = charAttributes(charSelect);
			
		} while (charArray[4] == 0);
		
		
		hp = charArray[0];
		strength = charArray[1];
		weaponMin = charArray[2];
		weaponMax = charArray[3];
		switch (charArray[0])
		{
			case 55:
				charName = "Rogue";
				break;
			case 35:
				charName = "Paladin";
				break;
			case 45:
				charName = "Jackie Chan";
				break;	
		}
		
		System.out.println("\nYou chose: " + charName + "\n");
		
		System.out.println("The Evil Wizard must be defeated! He is in The Castle. " +
						   "To get to The Castle, you must travel through either:\n"+
						   "1. The Forest\n" +
						   "2. The Graveyard\n");
		
		do
		{
			System.out.print("Which path will you take?: ");
			
			pathSelect = keyboard.nextInt();
			
			switch (pathSelect)
			{
				case 1:
					enemyNum = 3;
					enemyName = "Goblin";
					System.out.println("\nYou chose: The Forest\n" +
									   "Once you enter The Forest, " + 
									   "you encounter 3 Goblins! Time for battle!");
					validPath = true;
					break;
				case 2: 
					enemyNum = 5;
					enemyName = "Skeleton";
					System.out.println("\nYou chose: The Graveyard\n" +
								       "Once you enter The Graveyard, " + 
									   "you encounter 5 Skeletons! Time for battle!");
					validPath = true;
					break;
				
				default:
					validPath = false;
					System.out.println("\nThat was an invalid entry, please try again.\n");
			}	
		} while (!validPath);	
		
		for
		
		
		
		
		
	}
	
	public static int[] charAttributes(int charNum)
	{
		int[] charAtts = new int[5];
		charAtts[4] = 1;
		switch (charNum)
		{
		case 1:
			charAtts[0] = 55;
			charAtts[1] = 8;
			charAtts[2] = 1;
			charAtts[3] = 4;
			break;
		case 2:
			charAtts[0] = 35;
			charAtts[1] = 14;
			charAtts[2] = 3;
			charAtts[3] = 7;
			break;
		case 3:
			charAtts[0] = 45;
			charAtts[1] = 10;
			charAtts[2] = 2;
			charAtts[3] = 6;
			break;
		default:
			charAtts[4] = 0;
			System.out.println("\nThat was an invalid entry, please try again.\n");
		}

		return charAtts;
	}

/*	
	public static int[] selectPath(int path, String character)
	{
		path[] = new int[3];
		path[2] = 1;
		int enemyNum, hpRemain;
		switch (path)
		{
			case 1:
				path[0] = 3;
				System.out.println("\nYou chose: The Forest\n");
				System.out.println("Once you enter The Forest, you encounter 3 Goblins! Time for battle!");
				break;
			case 2: 
				enemyNum = 5;
				System.out.println("\nYou chose: The Graveyard\n");
				System.out.println("Once you enter The Graveyard, you encounter 5 Skeletons! Time for battle!");
				break;
			default:
				path[2] = 0;
				System.out.println("\nThat was an invalid entry, please try again.\n");
				return path;
		}
		return path;
		
		
		{			
			hpRemain = battle(enemyNum, character);
		}

		
	}
*/
	
	public static int battle(int battleNum, String name)
	{
		int hpLeft = 100;
		for(int x = 1; x <= enemyInput; x++)
		{
			switch (enemyInput)
			{
				case 3:
					System.out.println("***" + name + " vs Goblin " + x + "***");
					break;
				case 5:
					System.out.println("***" + name + " vs Skeleton " + x + "***")
					break;
			}
		}
		
		
		return hpLeft;
	}

	
}

