package app.classmanagementappclient.Backend.Logic.MyGenerics;

/**
 *
 * @author BEN
 */
public class MyDouble extends MyNumber<Double>
{
    private final Double value;

    public MyDouble(Double value) {
        this.value = value;
    }

    @Override
    public Double getValue() {
        return this.value;
    }    
    
    @Override
    public MyNumber<Double> addTo(MyNumber<Double> other) {
        return new MyDouble (this.value + other.getValue());
    }

    @Override
    public MyNumber<Double> divideBy(MyNumber<Double> other) {
        return new MyDouble (this.value / other.getValue());
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}

