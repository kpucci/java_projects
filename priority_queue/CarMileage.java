public class CarMileage
{
    public String vin = "";
    public int mileage;

    public CarMileage(String vin, int mileage)
    {
        this.vin = vin;
        this.mileage = mileage;
    }

    public int compareTo(CarMileage mileageToCompare)
    {
        if(this.mileage < mileageToCompare.mileage)
            return -1;
        if(this.mileage > mileageToCompare.mileage)
            return 1;
        return 0;
    }

    @Override
    public String toString()
    {
        return "" + this.mileage;
    }
}
