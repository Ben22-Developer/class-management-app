package app.classmanagementappclient.Backend.Logic.MyGenerics;

/**
 *
 * @author BEN
 */
public class MyInt extends MyNumber<Integer>
{
    private final Integer value;

    public MyInt(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public MyNumber<Integer> addTo(MyNumber<Integer> other) {
        return new MyInt(this.value + other.getValue());
    }

    @Override
    public MyNumber<Integer> divideBy(MyNumber<Integer> other) {
        return new MyInt(this.value / other.getValue());
    }

    @Override
    public String toString() {
        return this.getValue().toString();
    }
}

