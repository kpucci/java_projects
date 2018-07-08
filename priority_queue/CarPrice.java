public class CarPrice
{
    public String vin = "";
    public double price;

    public CarPrice(String vin, double price)
    {
        this.vin = vin;
        this.price = price;
    }

    public int compareTo(CarPrice priceToCompare)
    {
        if(this.price < priceToCompare.price)
            return -1;
        if(this.price > priceToCompare.price)
            return 1;
        return 0;
    }

    @Override
    public String toString()
    {
        return "" + this.price;
    }
}
