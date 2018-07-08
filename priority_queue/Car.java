public class Car
{
    private String vin = "";
    private String make = "";
    private String model = "";
    private CarPrice price;
    private CarMileage mileage;
    private String color = "";

    public Car()
    {
        this.price = null;
        this.mileage = null;
    }

    public Car(String vin, String make, String model, double price, int mileage, String color)
    {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.price = new CarPrice(this.vin, price);
        this.mileage = new CarMileage(this.vin, mileage);
        this.color = color;
    }

    public String getVIN()
    {
        return this.vin;
    }

    public String getMake()
    {
        return this.make;
    }

    public String getModel()
    {
        return this.model;
    }

    public CarPrice getPrice()
    {
        return this.price;
    }

    public CarMileage getMileage()
    {
        return this.mileage;
    }

    public String getColor()
    {
        return this.color;
    }

    public void setVIN(String vin)
    {
        this.vin = vin;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public void setPrice(double price)
    {
        this.price = new CarPrice(this.vin, price);
    }

    public void setMileage(int mileage)
    {
        this.mileage = new CarMileage(this.vin, mileage);
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    @Override
    public String toString()
    {
        return "\nVIN: " + this.vin +
               "\nMake: " + this.make +
               "\nModel: " + this.model +
               "\nPrice: " + this.price.toString() +
               "\nMileage: " + this.mileage.toString() +
               "\nColor: " + this.color;
    }
}
