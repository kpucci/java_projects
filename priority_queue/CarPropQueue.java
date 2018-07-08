import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Enumeration;

public class CarPropQueue
{
    public enum PropType {PRICE, MILEAGE}

    private Hashtable<String,Car> carST = new Hashtable<>();
    private ArrayList<CarPrice> priceQ = new ArrayList<>();
    private ArrayList<CarMileage> mileageQ = new ArrayList<>();
    private int numCars;

    public CarPropQueue()
    {
        numCars = 0;
        priceQ.add(new CarPrice("", 0.0));      // Don't use first index
        mileageQ.add(new CarMileage("", 0));    // Don't use first index
    }

    public void remapQueue(PropType prop)
    {
        int nCars = 0;
        if(prop == PropType.PRICE)
        {
            priceQ = new ArrayList<>();
            priceQ.add(new CarPrice("", 0.0));
        }
        else
        {
            mileageQ = new ArrayList<>();
            mileageQ.add(new CarMileage("", 0));
        }

        String key = "";
        Enumeration<String> keys = carST.keys();
        while(keys.hasMoreElements())
        {
            nCars++;
            key = keys.nextElement();

            if(prop == PropType.PRICE)
            {
                priceQ.add(carST.get(key).getPrice());
                swim(PropType.PRICE, nCars);
            }
            else
            {
                mileageQ.add(carST.get(key).getMileage());
                swim(PropType.MILEAGE, nCars);
            }
        }
    }

    public boolean isEmpty()
    {
        return carST.isEmpty();
    }

    public int size()
    {
        return carST.size();
    }

    public void insert(Car car)
    {
        carST.put(car.getVIN(), car);
        numCars++;

        priceQ.add(car.getPrice());
        swim(PropType.PRICE, numCars);

        mileageQ.add(car.getMileage());
        swim(PropType.MILEAGE, numCars);
    }

    public void swim(PropType prop, int index)
    {
        while(index > 1 && isMore(prop, index/2, index))
        {
            exch(prop, index/2, index);
            index = index/2;
        }
    }

    public void sink(PropType prop, int index)
    {
        while(2*index <= numCars)
        {
            int j = 2*index;
            if(j < numCars && isMore(prop, j, j+1))
                j++;
            if(!isMore(prop, j, j+1))
                break;
            exch(prop, index, j);
            index = j;
        }
    }

    private boolean isMore(PropType prop, int i, int j)
    {
        if(prop == PropType.PRICE)
            return priceQ.get(i).compareTo(priceQ.get(j)) > 0;
        return mileageQ.get(i).compareTo(mileageQ.get(j)) > 0;
    }

    private void exch(PropType prop, int i, int j)
    {
        if(prop == PropType.PRICE)
        {
            CarPrice temp = priceQ.get(i);
            priceQ.set(i, priceQ.get(j));
            priceQ.set(j, temp);
        }
        else
        {
            CarMileage temp = mileageQ.get(i);
            mileageQ.set(i, mileageQ.get(j));
            mileageQ.set(j, temp);
        }
    }

    public void updateCarPrice(String vin, double price)
    {
        Car carToUpdate = carST.get(vin);
        carToUpdate.setPrice(price);
        carST.put(carToUpdate.getVIN(), carToUpdate);
        remapQueue(PropType.PRICE);
    }

    public void updateCarMileage(String vin, int mileage)
    {
        Car carToUpdate = carST.get(vin);
        carToUpdate.setMileage(mileage);
        carST.put(carToUpdate.getVIN(), carToUpdate);
        remapQueue(PropType.MILEAGE);
    }

    public void updateCarColor(String vin, String color)
    {
        Car carToUpdate = carST.get(vin);
        carToUpdate.setColor(color);
        carST.put(carToUpdate.getVIN(), carToUpdate);
    }

    public Car removeCar(String vin)
    {
        Car removeCar = carST.remove(vin);
        numCars--;
        remapQueue(PropType.PRICE);
        remapQueue(PropType.MILEAGE);
        return removeCar;
    }

    public Car getMinPrice()
    {
        CarPrice minPrice = priceQ.get(1);
        String vin = minPrice.vin;
        return carST.get(vin);
    }

    public Car getMinPrice(String make, String model)
    {
        CarPropQueue newCPQ = new CarPropQueue();

        String key = "";
        Enumeration<String> keys = carST.keys();
        while(keys.hasMoreElements())
        {
            key = keys.nextElement();
            Car nextCar = carST.get(key);
            if(nextCar.getMake().equals(make) && nextCar.getModel().equals(model))
            {
                newCPQ.insert(nextCar);
            }
        }

        CarPrice minPrice = newCPQ.priceQ.get(1);
        String vin = minPrice.vin;
        return carST.get(vin);
    }

    public Car getMinMileage(String make, String model)
    {
        CarPropQueue newCPQ = new CarPropQueue();

        String key = "";
        Enumeration<String> keys = carST.keys();
        while(keys.hasMoreElements())
        {
            key = keys.nextElement();
            Car nextCar = carST.get(key);
            if(nextCar.getMake().equals(make) && nextCar.getModel().equals(model))
            {
                newCPQ.insert(nextCar);
            }
        }

        CarMileage minMileage = newCPQ.mileageQ.get(1);
        String vin = minMileage.vin;
        return carST.get(vin);
    }

    public Car getMinMileage()
    {
        CarMileage minMileage = mileageQ.get(1);
        String vin = minMileage.vin;
        return carST.get(vin);
    }
}
