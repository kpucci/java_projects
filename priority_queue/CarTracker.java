import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CarTracker
{
    private static CarPropQueue cpq = new CarPropQueue();

    public static int showMenu()
    {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("\nPlease select an option:\n" +
                            "1. Add a car\n" +
                            "2. Update a car\n" +
                            "3. Remove a car\n" +
                            "4. Find lowest price\n" +
                            "5. Find lowest mileage\n" +
                            "6. Find lowest price by make & model\n" +
                            "7. Find lowest mileage by make & model\n" +
                            "8. Exit\n");
        int option = keyboard.nextInt();
        switch(option)
        {
            case 1:
                addCarOption(keyboard);
                break;
            case 2:
                updateCarOption(keyboard);
                break;
            case 3:
                removeCarOption(keyboard);
                break;
            case 4:
                lowestPriceOption();
                break;
            case 5:
                lowestMileageOption();
                break;
            case 6:
                lowestPriceByMakeModelOption(keyboard);
                break;
            case 7:
                lowestMileageByMakeModelOption(keyboard);
                break;
            case 8:
                break;
        }

        return option;
    }

    public static void addCarOption(Scanner keyboard)
    {
        System.out.println("\nEnter the car's VIN: ");
        keyboard.nextLine();
        String vin = keyboard.nextLine();

        System.out.println("\nEnter the car's make: ");
        String make = keyboard.nextLine();

        System.out.println("\nEnter the car's model: ");
        String model = keyboard.nextLine();

        System.out.println("\nEnter the car's price in dollars: ");
        double price = keyboard.nextDouble();

        System.out.println("\nEnter the car's mileage: ");
        int mileage = keyboard.nextInt();

        System.out.println("\nEnter the car's color: ");
        keyboard.nextLine();
        String color = keyboard.nextLine();

        Car carToAdd = new Car(vin, make, model, price, mileage, color);
        cpq.insert(carToAdd);
        // System.out.println("Car set: " + cpq.carST.toString());
        // System.out.println("Price PQ: ");
        // for(CarPrice cp:cpq.priceQ)
        //     System.out.println(cp.toString());
    }

    public static void updateCarOption(Scanner keyboard)
    {
        System.out.println("\nEnter the car's VIN: ");
        keyboard.nextLine();
        String vin = keyboard.nextLine();

        System.out.println("\nWhat would you like to update?\n" +
                            "1. Price\n" +
                            "2. Mileage\n" +
                            "3. Color\n");

        int option = keyboard.nextInt();
        switch(option)
        {
            case 1:
                System.out.println("\nEnter the new price: ");
                double price = keyboard.nextDouble();
                cpq.updateCarPrice(vin,price);
                break;
            case 2:
                System.out.println("\nEnter the new mileage: ");
                int mileage = keyboard.nextInt();
                cpq.updateCarMileage(vin,mileage);
                break;
            case 3:
                System.out.println("\nEnter the new color: ");
                keyboard.nextLine();
                String color = keyboard.nextLine();
                cpq.updateCarColor(vin,color);
                break;
        }
    }

    public static void removeCarOption(Scanner keyboard)
    {
        System.out.println("\nEnter the car's VIN: ");
        keyboard.nextLine();
        String vin = keyboard.nextLine();
        Car carToRemove = cpq.removeCar(vin);
        System.out.println("\nThe following car was removed: " + carToRemove.toString());
    }

    public static void lowestPriceOption()
    {
        Car lowestPrice = cpq.getMinPrice();
        System.out.println("\nCar with the lowest price:" + lowestPrice.toString());
    }

    public static void lowestMileageOption()
    {
        Car lowestMileage = cpq.getMinMileage();
        System.out.println("\nCar with the lowest mileage:" + lowestMileage.toString());
    }

    public static void lowestPriceByMakeModelOption(Scanner keyboard)
    {
        System.out.println("\nEnter the car's make: ");
        keyboard.nextLine();
        String make = keyboard.nextLine();

        System.out.println("\nEnter the car's model: ");
        String model = keyboard.nextLine();

        Car lowestPrice = cpq.getMinPrice(make, model);
        System.out.println("\nCar with the lowest price:" + lowestPrice.toString());
    }

    public static void lowestMileageByMakeModelOption(Scanner keyboard)
    {
        System.out.println("\nEnter the car's make: ");
        keyboard.nextLine();
        String make = keyboard.nextLine();

        System.out.println("\nEnter the car's model: ");
        String model = keyboard.nextLine();

        Car lowestMileage = cpq.getMinMileage(make, model);
        System.out.println("\nCar with the lowest mileage:" + lowestMileage.toString());
    }

    public static void initializeCars(String filename)
    {
        if(filename != "")
        {
            try
            {
                BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line = reader.readLine(); // Absorb first line with info in it
                while((line = reader.readLine()) != null)
                {
                    String[] tokens = line.split(":");
                    String vin = tokens[0];
                    String make = tokens[1];
                    String model = tokens[2];
                    double price = new Double(tokens[3]).doubleValue();;
                    int mileage = Integer.parseInt(tokens[4]);
                    String color = tokens[5];

                    Car carToAdd = new Car(vin,make,model,price,mileage,color);
                    cpq.insert(carToAdd);
                }
                reader.close();
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
    }

    public static void main(String[] args)
    {
        initializeCars(args[0]);

        int option = 0;
        do
        {
            option = showMenu();
        }while(option != 8);
    }
}
