package app.classmanagementappclient.Backend.Logic.MyGenerics;

/**
 *
 * @author BEN
 * @param <T>
 */
public abstract class MyNumber<T extends Number>
{
    public abstract MyNumber<T> addTo (MyNumber<T> other);
    public abstract MyNumber<T> divideBy (MyNumber<T> other);
    public abstract T getValue();
    
    /**
     *
     * @param type
     * @param num
     * @return
     */
    public static MyNumber getNumber (int type, Object...value) 
    {
        MyNumber number = null;
        switch (type) {
            case 1 -> {
                int arg = value.length == 0 ? 0 : (Integer) value[0];
                number = new MyInt(arg);
            }
            case 2 -> {
                double arg = value.length == 0 ? 0.0 : (Double) value[0];
                number = new MyDouble(arg);
            }            
        }
        return number;
    }
}
